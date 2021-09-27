package mipt.bit.utils;

public final class Utils {
    public static void checkNotNull(String name, Object obj) {
        if (obj == null) {
            throw new IllegalArgumentException("Provided null " + name + " object");
        }
    }
}
