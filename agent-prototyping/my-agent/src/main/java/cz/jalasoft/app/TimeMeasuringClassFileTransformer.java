package cz.jalasoft.app;

import javassist.*;

import java.util.function.Predicate;

public final class TimeMeasuringClassFileTransformer extends GenericClassFileTransformer {

    public TimeMeasuringClassFileTransformer(Predicate<String> classNamePredicate) {
        super(classNamePredicate);
    }

    @Override
    protected void modifyMethod(CtClass type, CtMethod method) throws CannotCompileException, NotFoundException {

        CtMethod newMethod = ByteCodeModification.copyMethod(type, method);
        ByteCodeModification.removeAnnotations(method);

        String meteredMethodName = method.getName() + "_metered";
        method.setName(meteredMethodName);

        String invocationFrame = type.getName() + "." + newMethod.getName() + "(...)";
        newMethod.setBody(meteringMethodBody(invocationFrame, meteredMethodName));

        type.addMethod(newMethod);
    }

    private String meteringMethodBody(String invocationFrame, String meteredMethodName) {
        StringBuilder bodyBuilder = new StringBuilder();
        bodyBuilder.append("{\n");
        bodyBuilder.append("long beforeMillis = System.currentTimeMillis();\n");
        bodyBuilder.append("try {\n");
        bodyBuilder.append("return ($r) " + meteredMethodName + "($$);\n");
        bodyBuilder.append("} finally {\n");
        bodyBuilder.append("long afterMillis = System.currentTimeMillis();\n");
        bodyBuilder.append("long totalMillis = afterMillis - beforeMillis;\n");
        bodyBuilder.append("System.out.println(\"" + invocationFrame + ": \" + totalMillis + \"ms.\\n\");\n");
        bodyBuilder.append("}\n");
        bodyBuilder.append("}\n");

        return bodyBuilder.toString();
    }
}
