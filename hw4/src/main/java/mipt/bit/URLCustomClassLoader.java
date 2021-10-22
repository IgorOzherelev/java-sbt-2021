package mipt.bit;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static mipt.bit.utlis.CommonUtils.checkInputDataArrayLengths;
import static mipt.bit.utlis.CommonUtils.hexStringToByteArray;

public class URLCustomClassLoader extends ClassLoader {
    private final Map<String, byte[]> classBytesMap = new HashMap<>();

    public URLCustomClassLoader(URL[] urls, int[] classesNameLengths) throws IOException {
        initClassBytesMap(urls, classesNameLengths);
    }

    private void initClassBytesMap(URL[] urls, int[] classesNameLengths) throws IOException {
        checkInputDataArrayLengths(urls.length, classesNameLengths.length);

        for (int i = 0; i < urls.length; i++) {
            storeClassBytesFromURL(urls[i], classesNameLengths[i]);
        }
    }

    @Override
    public Class<?> findClass(String name) {
        byte[] classBytes = classBytesMap.get(name);

        return defineClass(name, classBytes, 0, classBytes.length);
    }

    private void storeClassBytesFromURL(URL classURL, int classNameLength) throws IOException {
        try (InputStream inputStream = classURL.openStream()) {
            byte[] bytes = inputStream.readAllBytes();

            decode(bytes);
            storeClassBytesIntoMap(bytes, classNameLength);
        }
    }

    private void storeClassBytesIntoMap(byte[] bytes, int classNameLength) {
        String className = new String(Arrays.copyOfRange(bytes, 0, classNameLength), StandardCharsets.UTF_8).intern();
        // копируем со сдвигом в 4 байта, тк магическое число(4 байта) некорректное
        byte[] classBytes = Arrays.copyOfRange(bytes, classNameLength + 4, bytes.length);
        byte[] classFileBytes = addMagicNumberToClassBytes(classBytes);

        classBytesMap.putIfAbsent(className, classFileBytes);
    }

    private byte[] addMagicNumberToClassBytes(byte[] classBytes) {
        // добавляем правильное волшебное число
        byte[] magicBytes = hexStringToByteArray("CAFEBABE");
        byte[] classFileBytes = new byte[magicBytes.length + classBytes.length];

        System.arraycopy(magicBytes, 0, classFileBytes, 0, magicBytes.length);
        System.arraycopy(classBytes, 0, classFileBytes, magicBytes.length, classBytes.length);
        return classFileBytes;
    }

    private void decode(byte[] bytes) {
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) (bytes[i] - 1);
        }
    }
}
