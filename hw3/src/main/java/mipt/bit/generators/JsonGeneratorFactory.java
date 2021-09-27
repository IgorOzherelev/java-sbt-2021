package mipt.bit.generators;

import java.lang.reflect.InvocationTargetException;

public interface JsonGeneratorFactory<T> {
    String PACKAGE_IMPORT = "package mipt.bit.generators;\n";
    String PACKAGE = " mipt.bit.generators";

    JsonGenerator<T> createJsonGenerator() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException;
}
