package cz.jalasoft.app;

import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.MethodInfo;

final class ByteCodeModification {

    static CtMethod copyMethod(CtClass type, CtMethod method) throws CannotCompileException {

        CtMethod newMethod = CtNewMethod.copy(method, type, null);

        MethodInfo info = method.getMethodInfo();
        info.getAttributes().forEach(attr -> {
            newMethod.getMethodInfo().addAttribute(attr);
        });

        return newMethod;
    }

    static void removeAnnotations(CtMethod method) {
        method.getMethodInfo().removeAttribute(AnnotationsAttribute.visibleTag);
    }
}
