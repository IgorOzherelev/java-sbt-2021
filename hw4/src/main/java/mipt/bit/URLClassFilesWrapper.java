package mipt.bit;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static mipt.bit.URLClassFileDataDecoder.addMagicNumberToClassBytes;
import static mipt.bit.URLClassFileDataDecoder.decode;
import static mipt.bit.utlis.CommonUtils.checkInputDataArrayLengths;

public class URLClassFilesWrapper {
    private final Map<String, byte[]> classBytesMap = new HashMap<>();

    public URLClassFilesWrapper(URL[] urls, int[] classesNameLengths) throws IOException {
        initClassBytesMap(urls, classesNameLengths);
    }

    public byte[] getClassFileBytesByName(String name) {
        return classBytesMap.get(name);
    }

    private void initClassBytesMap(URL[] urls, int[] classesNameLengths) throws IOException {
        checkInputDataArrayLengths(urls.length, classesNameLengths.length);

        for (int i = 0; i < urls.length; i++) {
            storeClassBytesFromURL(urls[i], classesNameLengths[i]);
        }
    }

    public void storeClassBytesFromURL(URL classURL, int classNameLength) throws IOException {
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
}
