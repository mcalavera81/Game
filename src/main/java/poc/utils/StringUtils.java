package poc.utils;

public class StringUtils {

    public static boolean hasText(CharSequence str) {
        return (str != null && str.length() > 0 && containsText(str));
    }


    private static boolean containsText(CharSequence str) {

        return str.chars()
                .mapToObj(i -> (char) i)
                .reduce(Boolean.TRUE, (acc, character) -> {
                            boolean b = !Character.isWhitespace(character);
                            return acc && b;
                        },
                        (acc1, acc2) -> acc1 && acc2);

    }
}
