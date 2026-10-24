import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("AI Privacy Gateway - Advising Summary");
        System.out.println("Enter advising notes:");

        String notes = scanner.nextLine();

        String requestId = "r1";

        SensitiveDataMasker masker = new SensitiveDataMasker();
        MaskingResult maskingResult =
                masker.mask(notes, requestId);

        System.out.println("\nMasked Request:");
        System.out.println(maskingResult.getMaskedText());

        MockProvider provider = new MockProvider();

        String providerResponse =
                provider.generateResponse(
                        maskingResult.getMaskedText()
                );

        ResponseProcessor processor =
                new ResponseProcessor();

        try {

            String finalOutput =
                    processor.validateAndRestore(
                            providerResponse,
                            maskingResult.getPlaceholderMap()
                    );

            System.out.println("\nFinal Output:");
            System.out.println(finalOutput);

        } catch (IllegalArgumentException error) {

            System.out.println("\nERROR:");
            System.out.println(error.getMessage());
        }

        scanner.close();
    }
}