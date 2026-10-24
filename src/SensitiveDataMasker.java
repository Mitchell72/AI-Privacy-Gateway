import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SensitiveDataMasker {

    private final List<String> knownNames = Arrays.asList(
            "Jordan Lee",
            "Alex Carter",
            "Morgan Smith"
    );

    public MaskingResult mask(String input, String requestId) {

        Map<String, String> placeholderMap = new LinkedHashMap<>();
        Map<String, String> valueToPlaceholder = new HashMap<>();

        Map<String, Integer> counters = new HashMap<>();
        counters.put("NAME", 0);
        counters.put("EMAIL", 0);
        counters.put("STUDENT_ID", 0);

        String maskedText = input;

        // Structured pattern: email address
        Pattern emailPattern = Pattern.compile(
                "[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}"
        );

        maskedText = maskPattern(
                maskedText,
                emailPattern,
                "EMAIL",
                requestId,
                placeholderMap,
                valueToPlaceholder,
                counters
        );

        // Structured pattern: 9-digit student ID
        Pattern studentIdPattern = Pattern.compile("\\b\\d{9}\\b");

        maskedText = maskPattern(
                maskedText,
                studentIdPattern,
                "STUDENT_ID",
                requestId,
                placeholderMap,
                valueToPlaceholder,
                counters
        );

        // Term-list matching for synthetic student names
        for (String name : knownNames) {
            if (maskedText.contains(name)) {

                String placeholder = valueToPlaceholder.get(name);

                if (placeholder == null) {
                    int number = counters.get("NAME") + 1;
                    counters.put("NAME", number);

                    placeholder =
                            "[[" + requestId + ":NAME:" + number + "]]";

                    valueToPlaceholder.put(name, placeholder);
                    placeholderMap.put(placeholder, name);
                }

                maskedText = maskedText.replace(name, placeholder);
            }
        }

        return new MaskingResult(maskedText, placeholderMap);
    }

    private String maskPattern(
            String text,
            Pattern pattern,
            String category,
            String requestId,
            Map<String, String> placeholderMap,
            Map<String, String> valueToPlaceholder,
            Map<String, Integer> counters) {

        Matcher matcher = pattern.matcher(text);
        StringBuffer result = new StringBuffer();

        while (matcher.find()) {

            String value = matcher.group();
            String placeholder = valueToPlaceholder.get(value);

            if (placeholder == null) {
                int number = counters.get(category) + 1;
                counters.put(category, number);

                placeholder =
                        "[[" + requestId + ":" + category + ":" + number + "]]";

                valueToPlaceholder.put(value, placeholder);
                placeholderMap.put(placeholder, value);
            }

            matcher.appendReplacement(
                    result,
                    Matcher.quoteReplacement(placeholder)
            );
        }

        matcher.appendTail(result);

        return result.toString();
    }
}