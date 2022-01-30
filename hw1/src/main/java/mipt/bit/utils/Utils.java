package mipt.bit.utils;

public final class Utils {
    public static void checkNotNull(String paramName, Object obj) {
        if (obj == null) {
            throw new IllegalArgumentException(String.format("Argument \"%s\" is null", paramName));
        }
    }

    public static void checkNotNull(Object obj) {
        if (obj == null) {
            throw new IllegalArgumentException("Argument is null");
        }
    }
}
