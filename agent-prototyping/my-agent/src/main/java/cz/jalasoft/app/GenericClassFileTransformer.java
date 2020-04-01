package cz.jalasoft.app;

import javassist.*;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Predicate;

public abstract class GenericClassFileTransformer implements ClassFileTransformer {

    private final ClassPool pool;
    private final Collection<ClassLoader> registeredLoaders;
    private final Predicate<String> classNamePredicate;

    public GenericClassFileTransformer(Predicate<String> classNamePredicate) {

        this.classNamePredicate = classNamePredicate;

        ClassPool systemPathPool = new ClassPool();
        systemPathPool.appendSystemPath();

        this.pool = new ClassPool(systemPathPool);
        this.registeredLoaders = new ArrayList<>();
    }

    protected final ClassPool pool() {
        return pool;
    }

    @Override
    public byte[] transform(ClassLoader classLoader, String s, Class<?> aClass, ProtectionDomain protectionDomain, byte[] bytes) throws IllegalClassFormatException {

        if (s == null) {
            return null;
        }

        ///System.out.println(s);
           // System.out.println("Chci naloadovat " + s);

        if (classLoader == null) {
            return null;
        }

        try {
            String className = className(s);


            if (!classNamePredicate.test(className)) {
                return null;
            }

            registerClassPath(className, bytes, classLoader);

            CtClass type = pool.getOrNull(className);
            System.out.println("Naloadoval jsem " + className);

            if (type == null) {
                //System.out.println("Trida" + className + " nenaloadovana....");
                return null;
            }

            modifyClass(type);

            //System.out.println("Puvodni  bytes: " + bytes.length);

            byte[] byteCode = type.toBytecode();
            type.detach();
            return byteCode;

        } catch (Exception exc) {
            System.out.println(s + "se nepovedla");
            exc.printStackTrace();
            return null;
        }
    }

    private String className(String pathToClass) {
        return pathToClass.replace("/", ".");
    }

    private void registerClassPath(String className, byte[] bytes, ClassLoader loader) {

        /*if (bytes != null) {
            pool.insertClassPath(new ByteArrayClassPath(className, bytes));
            return;
        }*/

        synchronized (registeredLoaders) {
            if (!registeredLoaders.contains(loader)) {
                registeredLoaders.add(loader);
                System.out.println("registruju " + loader);
                pool.insertClassPath(new LoaderClassPath(loader));
            }
        }
    }

    protected void modifyClass(CtClass type) throws CannotCompileException, NotFoundException {
        CtMethod[] methods = type.getDeclaredMethods();

        for(CtMethod m : methods) {
            if (m.getName().startsWith("lambda$")) {
                System.out.println("Preskakuju " + m.getName());
                continue;
            }

            System.out.println("Modifkuju " + m.getName());

            modifyMethod(type, m);
        }
    }

    protected void modifyMethod(CtClass type, CtMethod method) throws CannotCompileException, NotFoundException {}
}
