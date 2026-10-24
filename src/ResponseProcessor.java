import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ResponseProcessor {

    private static final Pattern PLACEHOLDER_PATTERN =
            Pattern.compile("\\[\\[[^\\]]+\\]\\]");

    public String validateAndRestore(
            String response,
            Map<String, String> placeholderMap) {

        Matcher matcher = PLACEHOLDER_PATTERN.matcher(response);

        while (matcher.find()) {
            String placeholder = matcher.group();

            if (!placeholderMap.containsKey(placeholder)) {
                throw new IllegalArgumentException(
                        "Invalid placeholder returned: " + placeholder
                );
            }
        }

        String restoredText = response;

        for (Map.Entry<String, String> entry : placeholderMap.entrySet()) {
            restoredText = restoredText.replace(
                    entry.getKey(),
                    entry.getValue()
            );
        }

        return restoredText;
    }
}