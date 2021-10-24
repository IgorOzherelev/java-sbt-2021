package mipt.bit;

import java.io.IOException;
import java.net.URL;

public class URLCustomClassLoader extends ClassLoader {
    private final URLClassFilesWrapper urlClassFilesWrapper;

    public URLCustomClassLoader(URL[] urls, int[] classesNameLengths) throws IOException {
        urlClassFilesWrapper = new URLClassFilesWrapper(urls, classesNameLengths);
    }

    @Override
    public Class<?> findClass(String name) {
        byte[] classBytes = urlClassFilesWrapper.getClassFileBytesByName(name);

        return defineClass(name, classBytes, 0, classBytes.length);
    }
}
