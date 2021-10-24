package mipt.bit.utlis;

public final class CommonUtils {
    public static void checkInputDataArrayLengths(int... arrayLengths) {
        for (int i = 1; i < arrayLengths.length; i++) {
            if (arrayLengths[i] != arrayLengths[0]) {
                throw new IllegalArgumentException("At least one provided array has the different length");
            }
        }
    }
}
