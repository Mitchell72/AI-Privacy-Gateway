public class MockProvider {

    public String generateResponse(String maskedNotes) {
        return "Advising Summary:\n"
                + maskedNotes
                + "\n\nAction Items:\n"
                + "- Review registration requirements.\n"
                + "- Complete any required forms before registration.";
    }

    public String generateInvalidResponse(String maskedNotes) {
        return maskedNotes.replace(
                "[[r1:NAME:1]]",
                "[[r1:NAME:99]]"
        );
    }
}