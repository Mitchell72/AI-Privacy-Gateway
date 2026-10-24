public class GatewayTests {

    private static int passed = 0;
    private static int failed = 0;

    public static void main(String[] args) {

        testNormalInput();
        testRepeatedValues();
        testNoSensitiveInformation();
        testInvalidPlaceholder();
        testDifferentEntities();

        System.out.println("\nTests Passed: " + passed);
        System.out.println("Tests Failed: " + failed);
    }

    private static void check(boolean condition, String testName) {
        if (condition) {
            System.out.println("PASS: " + testName);
            passed++;
        } else {
            System.out.println("FAIL: " + testName);
            failed++;
        }
    }

    private static void testNormalInput() {

        SensitiveDataMasker masker = new SensitiveDataMasker();

        String input =
                "Met with Jordan Lee, student ID 123456789. " +
                        "Email: jordan.lee@example.com.";

        MaskingResult result = masker.mask(input, "r1");

        boolean correct =
                result.getMaskedText().contains("[[r1:NAME:1]]")
                        && result.getMaskedText().contains("[[r1:STUDENT_ID:1]]")
                        && result.getMaskedText().contains("[[r1:EMAIL:1]]");

        check(correct, "Normal Input");
    }

    private static void testRepeatedValues() {

        SensitiveDataMasker masker = new SensitiveDataMasker();

        String input =
                "Jordan Lee met with the advisor. Jordan Lee asked about registration.";

        MaskingResult result = masker.mask(input, "r2");

        int first = result.getMaskedText().indexOf("[[r2:NAME:1]]");
        int second = result.getMaskedText().indexOf("[[r2:NAME:1]]", first + 1);

        check(first >= 0 && second >= 0, "Repeated Sensitive Values");
    }

    private static void testNoSensitiveInformation() {

        SensitiveDataMasker masker = new SensitiveDataMasker();

        String input =
                "Discussed registration requirements for next semester.";

        MaskingResult result = masker.mask(input, "r3");

        check(
                result.getPlaceholderMap().isEmpty(),
                "No Sensitive Information"
        );
    }

    private static void testInvalidPlaceholder() {

        SensitiveDataMasker masker = new SensitiveDataMasker();
        MockProvider provider = new MockProvider();
        ResponseProcessor processor = new ResponseProcessor();

        String input = "Met with Jordan Lee about registration.";

        MaskingResult result = masker.mask(input, "r1");

        String response =
                provider.generateInvalidResponse(result.getMaskedText());

        boolean errorCaught = false;

        try {
            processor.validateAndRestore(
                    response,
                    result.getPlaceholderMap()
            );
        } catch (IllegalArgumentException error) {
            errorCaught = true;
        }

        check(errorCaught, "Invalid Placeholder Response");
    }

    private static void testDifferentEntities() {

        SensitiveDataMasker masker = new SensitiveDataMasker();

        String input =
                "Jordan Lee and Alex Carter both attended advising.";

        MaskingResult result = masker.mask(input, "r4");

        boolean correct =
                result.getMaskedText().contains("[[r4:NAME:1]]")
                        && result.getMaskedText().contains("[[r4:NAME:2]]");

        check(correct, "Different Entities");
    }
}