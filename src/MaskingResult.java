import java.util.Map;

public class MaskingResult {
    private final String maskedText;
    private final Map<String, String> placeholderMap;

    public MaskingResult(String maskedText, Map<String, String> placeholderMap) {
        this.maskedText = maskedText;
        this.placeholderMap = placeholderMap;
    }

    public String getMaskedText() {
        return maskedText;
    }

    public Map<String, String> getPlaceholderMap() {
        return placeholderMap;
    }
}