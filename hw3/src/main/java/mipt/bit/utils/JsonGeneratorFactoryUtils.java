package mipt.bit.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

public final class JsonGeneratorFactoryUtils {
    public static Field[] getDeclaredFields(Class<?> clazz) {
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            declaredField.setAccessible(true);
        }

        return declaredFields;
    }

    public static boolean checkIfHasGetter(Class<?> clazz, Field declaredField) throws NoSuchMethodException {
        String getterName = getGetterName(declaredField.getName());
        Set<String> methodsNames = new HashSet<>();
        Method[] methods = clazz.getDeclaredMethods();

        for (int i = 0; i < methods.length; i++) {
            methodsNames.add(methods[i].getName());
        }

        return methodsNames.contains(getterName);
    }

    public static String[] getDeclaredFieldNames(Class<?> clazz) {
        Field[] declaredFields = getDeclaredFields(clazz);
        String[] names = new String[declaredFields.length];
        for (int i = 0; i < declaredFields.length; i++) {
            declaredFields[i].setAccessible(true);
            names[i] = declaredFields[i].getName();
        }

        return names;
    }

    public static String getGetterName(String fieldName) {
        String tmpFieldName = fieldName;
        tmpFieldName = tmpFieldName.replace(tmpFieldName.charAt(0),
                String.valueOf(tmpFieldName.charAt(0)).toUpperCase().charAt(0));

        return "get" + tmpFieldName;
    }

    public static boolean isPrimitiveOrStringWrapper(Class<?> type) {
        return type.isPrimitive() || type == Double.class || type == Float.class || type == Long.class ||
                type == Integer.class || type == Short.class || type == Character.class ||
                type == Byte.class || type == Boolean.class || type == String.class;
    }
}
