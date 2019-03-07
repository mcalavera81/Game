package poc.utils;

public class Assert {

    public static void requireNotNull(Object object, String message){
        if(object == null){
            throw  new IllegalArgumentException(message);
        }
    }


    public static void requireText(String str, String message) {
        if (!StringUtils.hasText(str)) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void requiresTrue(boolean predicate, String message) {
        if (!predicate) {
            throw new IllegalArgumentException(message);
        }
    }
}
