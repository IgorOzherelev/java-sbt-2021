package mipt.bit;

public class URLClassFileDataDecoder {
    public static byte[] addMagicNumberToClassBytes(byte[] classBytes) {
        byte[] magicBytes = {(byte) 0xCA, (byte) 0xFE, (byte) 0xBA, (byte) 0xBE};
        byte[] classFileBytes = new byte[magicBytes.length + classBytes.length];

        System.arraycopy(magicBytes, 0, classFileBytes, 0, magicBytes.length);
        System.arraycopy(classBytes, 0, classFileBytes, magicBytes.length, classBytes.length);
        return classFileBytes;
    }

    public static void decode(byte[] bytes) {
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) (bytes[i] - 1);
        }
    }
}
