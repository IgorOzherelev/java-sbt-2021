package mipt.bit.utlis;

public final class CommonUtils {
    public static void checkInputDataArrayLengths(int... arrayLengths) {
        for (int i = 1; i < arrayLengths.length; i++) {
            if (arrayLengths[i] != arrayLengths[0]) {
                throw new IllegalArgumentException("At least one provided array has the different length");
            }
        }
    }

    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }
}
