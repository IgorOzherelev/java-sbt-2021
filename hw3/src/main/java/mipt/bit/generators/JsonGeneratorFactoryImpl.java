package mipt.bit.generators;

import mipt.bit.utils.JsonGeneratorFactoryUtils;
import net.openhft.compiler.CompilerUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

public class JsonGeneratorFactoryImpl<T> implements JsonGeneratorFactory<T> {
    private final Class<?> clazz;
    private String castedInstanceName = "";

    private boolean isLastField = false;

    public JsonGeneratorFactoryImpl(Class<?> clazz) {
        this.clazz = clazz;
    }

    @Override
    @SuppressWarnings({"unckecked"})
    public JsonGenerator<T> createJsonGenerator() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        String className = clazz.getName();
        Class<?> generatorClazz;

        StringBuilder jsonGeneratorBuilder = new StringBuilder();
        Field[] declaredFields = JsonGeneratorFactoryUtils.getDeclaredFields(clazz);
        castedInstanceName = String.valueOf(className.toLowerCase().charAt(0));
        String generatorName = className.substring(className.lastIndexOf(".") + 1) + "JsonGenerator";

        startJsonGeneratorClass(jsonGeneratorBuilder, className, generatorName);

        addFieldsSupportToJsonGenerator(jsonGeneratorBuilder, declaredFields);

        finishJsonGeneratorClass(jsonGeneratorBuilder);

        //System.out.println(jsonGeneratorBuilder);
        generatorClazz = loadJsonGeneratorClass(jsonGeneratorBuilder, generatorName);

        return (JsonGenerator<T>) generatorClazz.getDeclaredConstructor().newInstance();
    }

    // todo Strings flexible support, inner objects
    private void addFieldsSupportToJsonGenerator(StringBuilder jsonGeneratorBuilder, Field[] declaredFields)
            throws NoSuchMethodException {
        Class<?> fieldType;
        String declaredFieldName;
        for (int i = 0; i < declaredFields.length; i++) {
            declaredFields[i].setAccessible(true);
            if (i == declaredFields.length - 1)
                isLastField = true;
            fieldType = declaredFields[i].getType();
            if (JsonGeneratorFactoryUtils.checkIfHasGetter(clazz, declaredFields[i])) {
                declaredFieldName = declaredFields[i].getName();
                if (JsonGeneratorFactoryUtils.isPrimitiveOrStringWrapper(fieldType)) {
                    addPrimitiveOrStringSupportToJsonGenerator(jsonGeneratorBuilder, declaredFieldName);
                } else if (fieldType.isArray()) { // array field only for primitives
                    addArraySupportToJsonGenerator(jsonGeneratorBuilder, declaredFieldName);
                } else if (Collection.class.isAssignableFrom(fieldType)) { // collection field
                    addCollectionSupportToJsonGenerator(jsonGeneratorBuilder, declaredFieldName);
                }
            }
        }
    }

    private void addCollectionSupportToJsonGenerator(StringBuilder jsonGeneratorBuilder, String declaredFieldName) {
        String getterFieldFunctionName = JsonGeneratorFactoryUtils.getGetterName(declaredFieldName);

        addKeyFieldNameToJson(jsonGeneratorBuilder, declaredFieldName);

        jsonGeneratorBuilder
                .append(".append(").append(castedInstanceName)
                .append(".").append(getterFieldFunctionName).append("()")
                .append(".toString()").append(")");

        endJsonStringField(jsonGeneratorBuilder);
    }

    private void addArraySupportToJsonGenerator(StringBuilder jsonGeneratorBuilder, String declaredFieldName) {
        String getterFieldFunctionName = JsonGeneratorFactoryUtils.getGetterName(declaredFieldName);

        addKeyFieldNameToJson(jsonGeneratorBuilder, declaredFieldName);

        jsonGeneratorBuilder
                .append(".append(")
                .append("java.util.Arrays.toString(")
                .append(castedInstanceName).append(".")
                .append(getterFieldFunctionName).append("()")
                .append(")").append(")");

        endJsonStringField(jsonGeneratorBuilder);
    }

    private void addPrimitiveOrStringSupportToJsonGenerator(StringBuilder jsonGeneratorBuilder,
                                                            String declaredPrimitiveFieldName) {
        String getterFieldFunctionName = JsonGeneratorFactoryUtils.getGetterName(declaredPrimitiveFieldName);

        addKeyFieldNameToJson(jsonGeneratorBuilder, declaredPrimitiveFieldName);

        jsonGeneratorBuilder
                .append(".append(").append("\"").append("\\")
                .append("\"").append("\"").append("+")
                .append(castedInstanceName).append(".")
                .append(getterFieldFunctionName).append("()")
                .append("+").append("\"").append("\\")
                .append("\"").append("\"").append(")"); // closed jsonBuilder append

        endJsonStringField(jsonGeneratorBuilder);
    }

    private void endJsonStringField(StringBuilder jsonGeneratorBuilder) {
        if (isLastField) {
            jsonGeneratorBuilder
                    .append(".append(").append("\"").append("\\n")
                    .append("}").append("\"").append(")");
        } else {
            jsonGeneratorBuilder
                    .append(".append(").append("\"")
                    .append(",\\n\\t").append("\"")
                    .append(")");
        }
    }

    private void addKeyFieldNameToJson(StringBuilder jsonGeneratorBuilder, String declaredFieldName) {
        jsonGeneratorBuilder
                .append(".append(").append("\"").append("\\")
                .append("\"").append(declaredFieldName)
                .append("\\").append("\"").append(":\"")
                .append(")"); // closed jsonBuilder append
    }

    private Class<?> loadJsonGeneratorClass(StringBuilder jsonGeneratorBuilder, String generatorName) throws ClassNotFoundException {
        String path = PACKAGE + "." + generatorName;

        return (Class<?>) CompilerUtils.CACHED_COMPILER
                .loadFromJava(path.trim(), jsonGeneratorBuilder.toString());
    }

    private void finishJsonGeneratorClass(StringBuilder jsonGeneratorBuilder) {
        jsonGeneratorBuilder.append(";").append("\n");
        jsonGeneratorBuilder.append("\n\treturn jsonBuilder.toString();\n");
        jsonGeneratorBuilder.append("}"); // close toJsonString
        jsonGeneratorBuilder.append("}"); //close class
    }

    private void startJsonGeneratorClass(StringBuilder jsonGeneratorBuilder, String className, String generatorName) {
        jsonGeneratorBuilder.append(PACKAGE_IMPORT);
        jsonGeneratorBuilder
                .append(Tokens.PUBLIC.getTokenString()).append(Tokens.CLASS.getTokenString())
                .append(generatorName).append(Tokens.GENERIC_T.getTokenString())
                .append(Tokens.IMPLEMENTS.getTokenString()).append(Tokens.JSON_GENERATOR.getTokenString())
                .append("{").append(Tokens.NEXT_LINE.getTokenString());

        jsonGeneratorBuilder
                .append(Tokens.OVERRIDE.getTokenString()).append(Tokens.PUBLIC.getTokenString())
                .append(Tokens.STRING.getTokenString()).append(Tokens.TO_JSON_STRING_FUNCTION.getTokenString());

        jsonGeneratorBuilder
                .append("StringBuilder jsonBuilder = new StringBuilder();\n\t")
                .append(className).append(" ").append(castedInstanceName)
                .append(" = ").append("(")
                .append(className).append(")").append(" instance;\n\t");

        jsonGeneratorBuilder.append("jsonBuilder.append(\"{\\n\\t\")"); // started jsonBuilder
    }
}
