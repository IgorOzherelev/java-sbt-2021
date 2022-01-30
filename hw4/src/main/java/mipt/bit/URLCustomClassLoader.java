package mipt.bit;

import java.net.URL;

public class URLCustomClassLoader extends ClassLoader {
    private final URL[] urls;
    private final int[] classesNameLengths;

    public URLCustomClassLoader(URL[] urls, int[] classesNameLengths) {
        this.urls = urls;
        this.classesNameLengths = classesNameLengths;
    }

    @Override
    public Class<?> findClass(String name) throws ClassNotFoundException {
        URLClassFilesWrapper urlClassFilesWrapper = new URLClassFilesWrapper(urls, classesNameLengths);
        byte[] classBytes = urlClassFilesWrapper.getClassFileBytesByName(name);

        if (classBytes == null || classBytes.length == 0) {
            throw new ClassNotFoundException("Not founded class with name " + name);
        }

        return defineClass(name, classBytes, 0, classBytes.length);
    }
}
