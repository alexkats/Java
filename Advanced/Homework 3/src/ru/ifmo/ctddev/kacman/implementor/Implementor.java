package ru.ifmo.ctddev.kacman.implementor;

import info.kgeorgiy.java.advanced.implementor.ImplerException;
import info.kgeorgiy.java.advanced.implementor.JarImpler;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.*;
import java.lang.reflect.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;

/**
 * Implementation of {@link info.kgeorgiy.java.advanced.implementor.Impler}.
 * It can implement interfaces and classes.
 * For every method returns just default values.
 * <p>
 * It will generate implementation and write it to the file
 * for the given class name or type token.
 * <p>
 * All public methods from the interface or class, including those which are inherited
 * from super-interfaces and super-classes, which do not have valid implementations,
 * will be added to implementation and will return default value.
 *
 * @author Alexey Katsman
 */

public class Implementor implements JarImpler {

    /**
     * {@link #implement(Class, java.io.File)} or {@link #implementJar(Class, java.io.File)} is invoked with parameters
     * from command line.
     *
     * @param args arguments from command line. For {@link #implement(Class, java.io.File)} the only argument
     *             is name (with package, if it exists) of a class we should generate
     *             implementation for. For {@link #implementJar(Class, java.io.File)} the first argument is "-jar", second is name
     *             (with package, if it exists) of a class we should generate implementation for, the third one is name of a
     *             directory we would like to put result jar in.
     * @throws ImplerException if one of the following conditions is met:
     *                         <ol>
     *                         <li>We can't find the passed class
     *                         <li><code>args</code> are incorrect
     *                         <li>Implementation of the class couldn't be done without errors
     *                         </ol>
     */
    public static void main(String[] args) throws ImplerException {
        try {
            if (args == null || (args.length != 1 && args.length != 3) || args[0] == null) {
                throw new ImplerException("Usage exception");
            }
        } catch (ImplerException e) {
            System.err.println("Wrong arguments");
            return;
        }

        try {
            if (args.length == 3 && (!args[0].equals("-jar") || args[1] == null || args[2] == null)) {
                throw new ImplerException("Usage exception");
            }
        } catch (ImplerException e) {
            System.err.println("Wrong arguments");
            return;
        }

        try {
            if (args.length == 1) {
                new Implementor().implement(Class.forName(args[0]), new File("."));
            } else {
                new Implementor().implementJar(Class.forName(args[1]), new File(args[2]));
            }
        } catch (ClassNotFoundException e) {
            System.err.println("Can't find/open interface");
        } catch (ImplerException e) {
            System.err.println("Can't implement interface");
        }
    }

    /**
     * Returns default values for primitive types, <code>null</code>
     * for other types and nothing for <code>void</code>.
     *
     * @param type class token we want to get default value for.
     * @return default value of this type.
     */
    private static String getDefaultType(Class type) {
        if (type.equals((Class) void.class)) {
            return "";
        } else if (!type.isPrimitive()) {
            return " null";
        } else if (type.equals((Class) boolean.class)) {
            return " false";
        } else {
            return " 0";
        }
    }

    @Override
    public void implement(Class<?> aClass, File root) throws ImplerException {
        implementToFile(aClass, root);
    }

    /**
     * Does exactly the same as {@link #implement(Class, java.io.File)},
     * but returns file with implementation in addition to generating.
     *
     * @param token type token we need to generate implementation for
     * @param root  root directory
     * @return file implementation was written in
     * @throws ImplerException in case of impossibility to generate implementation
     */
    private File implementToFile(Class<?> token, File root) throws ImplerException {
        if (token == null || token.isPrimitive() || Modifier.isFinal(token.getModifiers())) {
            throw new ImplerException();
        }

        File outputFile = new File(root.getAbsolutePath() + File.separator + createRelativePath(token));
        if (!outputFile.exists()) {
            if (outputFile.getParentFile() != null) {
                outputFile.getParentFile().mkdirs();
            }
        }

        try (BufferedWriter output = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(outputFile), StandardCharsets.UTF_8))) {
            addPackage(output, token);
            addHeader(output, token);
            addConstructors(output, token);
            addMethods(output, token);
        } catch (FileNotFoundException e) {
            throw new ImplerException("Couldn't create\\open file " + outputFile.getAbsolutePath() + " cause: " + e.getMessage());
        } catch (IOException e) {
            throw new ImplerException("Error during writing class's code");
        }
        return outputFile;
    }

    /**
     * Write declaration of <code>package</code> to the generating class
     *
     * @param output    output stream to which it should write a directive
     * @param baseClass class we should generate implementation for
     * @throws IOException if an I/O error occurs during writing to the file
     */
    private void addPackage(BufferedWriter output, Class baseClass) throws IOException {
        if (baseClass.getPackage() != null) {
            String packageName = baseClass.getPackage().getName();
            output.write("package " + packageName + ";");
            output.newLine();
            output.newLine();
        }
    }

    /**
     * Write declaration of the class to the generating class.
     * <p>
     * If <code>baseClass</code> is pointed to <code>class</code>
     * the generated declaration contain word <code>extends</code>,
     * if <code>baseClass</code> is pointed to <code>interface</code>,
     * word <code>implements</code> respectively.
     *
     * @param output    stream we should write declaration into
     * @param baseClass class we should generate implementation for
     * @throws IOException if an I/O error occurs during writing to the file
     */
    private void addHeader(BufferedWriter output, Class baseClass) throws IOException {
        output.write("public class " + baseClass.getSimpleName() + "Impl" + (baseClass.isInterface() ? " implements " : " extends ") + baseClass.getSimpleName() + " {");
        output.newLine();
    }

    /**
     * Adds constructor to the generating class, if <code>baseClass</code>
     * is a class.
     * If <code>baseClass</code> has default constructor, nothing is added
     * (because Java generates its own default constructor during compilation in this case).
     * <p>
     * Only one of the <code>public</code> or <code>protected</code> constructors
     * will be added, thus we can invoke it from derived class with <code>super</code>.
     * If <code>baseClass</code> doesn't have any of those required constructor,
     * exception will be thrown.</p>
     *
     * @param output    output stream we should write declaration into
     * @param baseClass class we should generate implementation for
     * @throws ImplerException if no possible constructors have been found
     * @throws IOException     if an I/O error occurs during writing to the file
     */
    private void addConstructors(BufferedWriter output, Class baseClass) throws IOException, ImplerException {
        if (baseClass.isInterface()) {
            return;
        }

        Constructor<?> constructors[] = baseClass.getDeclaredConstructors();
        for (Constructor aConstructor : constructors) {
            if (!Modifier.isPrivate(aConstructor.getModifiers()) && aConstructor.getParameters().length == 0) {
                return;
            }
        }

        for (Constructor aConstructor : constructors) {
            if (!Modifier.isPrivate(aConstructor.getModifiers())) {
                Parameter parameters[] = aConstructor.getParameters();
                Type exceptions[] = aConstructor.getGenericExceptionTypes();

                if (Modifier.isPublic(aConstructor.getModifiers())) {
                    output.write("\tpublic ");
                } else if (Modifier.isProtected(aConstructor.getModifiers())) {
                    output.write("\tprotected ");
                }

                output.write(baseClass.getSimpleName() + "Impl(");

                if (parameters.length != 0) {
                    output.write(parameters[0].toString());
                    for (int i = 1; i < parameters.length; i++)
                        output.write(", " + parameters[i].toString());
                }
                output.write(")");
                if (exceptions.length != 0) {
                    output.write(" throws " + exceptions[0].getTypeName());
                    for (int i = 1; i < exceptions.length; i++)
                        output.write(", " + exceptions[i].getTypeName());
                }
                output.write(" {\n\t\t super(");
                if (parameters.length != 0) {
                    output.write(parameters[0].getName());
                    for (int i = 1; i < parameters.length; i++)
                        output.write(", " + parameters[i].getName());
                }
                output.write(");\n\t}\n");
                return;
            }
        }
        throw new ImplerException();
    }

    /**
     * Collects all classes and interfaces which are super to <tt>aClass</tt>
     * to the {@link java.util.List} <tt>list</tt>.
     *
     * @param aClass class for which dfs must be run.
     * @param list   {@link java.util.List} to collect all classes and interfaces in dfs tour.
     */
    private void dfsMethods(Class aClass, List<Method> list) {
        if (aClass == null) {
            return;
        }
        list.addAll(Arrays.asList(aClass.getDeclaredMethods()));
        dfsMethods(aClass.getSuperclass(), list);
        for (Class i : aClass.getInterfaces()) {
            dfsMethods(i, list);
        }
    }

    /**
     * Returns String associated with <tt>aMethod</tt>.
     *
     * @param aMethod {@link java.lang.reflect.Method} to be associated with {@link java.lang.String}.
     * @return {@link java.lang.String} associated with <tt>aMethod</tt>.
     */
    private static String getMethodWithArguments(Method aMethod) {
        String ans = aMethod.getName();
        for (Class p : aMethod.getParameterTypes()) {
            ans += "/" + p.getCanonicalName();
        }
        return ans;
    }

    public Implementor() {
        super();
    }

    /**
     * Writes {@link java.lang.reflect.Method} <tt>aMethod</tt>.
     *
     * @param aMethod {@link java.lang.reflect.Method} to be written.
     * @param output  output stream we should write declaration into
     * @throws IOException if an I/O error occurs during writing to the file
     */
    private void writeMethod(Method aMethod, BufferedWriter output) throws IOException {
        output.write("\t");
        String modifiers = Modifier.toString(((aMethod.getModifiers() & Modifier.methodModifiers()) & (~Modifier.ABSTRACT)));
        if (!"".equals(modifiers)) {
            output.write(modifiers + " ");
        }

        if (aMethod.getReturnType().isArray()) {
            output.write(aMethod.getReturnType().getComponentType().getName() + "[] ");
        } else {
            output.write(aMethod.getReturnType().getName() + " ");
        }
        output.write(aMethod.getName() + "(");

        for (int i = 0; i < aMethod.getParameterTypes().length; i++) {
            if (i > 0) {
                output.write(", ");
            }
            if (aMethod.getParameterTypes()[i].isArray()) {
                output.write(aMethod.getParameterTypes()[i].getComponentType().getName() + "[] a" + i);
            } else {
                output.write(aMethod.getParameterTypes()[i].getName() + " a" + i);
            }
        }
        output.write(") ");

        for (int i = 0; i < aMethod.getExceptionTypes().length; i++) {
            if (i > 0) {
                output.write(", ");
            } else {
                output.write("throws ");
            }
            output.write(aMethod.getExceptionTypes()[i].getName());
        }
        if (Modifier.isNative(aMethod.getModifiers())) {
            output.write(";\n\n");
            return;
        }
        output.write(" {\n");

        output.write("\t\treturn " + getDefaultType(aMethod.getReturnType()) + ";");
        getDefaultType(aMethod.getReturnType().getClass());
        output.write("\n\t}\n\n");
    }

    /**
     * Writes declaration and damp implementation of methods of interface or class
     * methods, which were specified by {@link #dfsMethods(Class, List)}. It writes by invoking
     * <code>writeMethod</code>.
     *
     * @param output    output stream we should write declaration into
     * @param baseClass class we should generate implementation for
     * @throws IOException     if can't create or modify file in <tt>root</tt> directory.
     * @throws ImplerException if <tt>baseClass</tt> can't be expanded and can't be realized.
     */
    private void addMethods(BufferedWriter output, Class baseClass) throws ImplerException, IOException {
        HashMap<String, ArrayList<Method>> methods = new HashMap<>();
        HashSet<String> methodsNames = new HashSet<>();

        List<Method> methodList = new ArrayList<>();
        dfsMethods(baseClass, methodList);

        for (Method method : methodList) {
            if (!methods.containsKey(getMethodWithArguments(method))) {
                methods.put(getMethodWithArguments(method), new ArrayList<>());
            }
            methods.get(getMethodWithArguments(method)).add(method);
            methodsNames.add(getMethodWithArguments(method));
        }

        for (String methodName : methodsNames) {
            ArrayList<Method> sameMethods = methods.get(methodName);

            boolean hasNotAbstract = false;
            for (Method method : sameMethods) {
                if (!Modifier.isAbstract(method.getModifiers())) {
                    hasNotAbstract = true;
                    break;
                }
            }
            if (!hasNotAbstract) {
                writeMethod(sameMethods.get(0), output);
            }
        }

        output.write("}");
    }

    /**
     * Returns a full relative path for implementation of class <code>aClass</code>
     *
     * @param aClass token of class to be implemented
     * @return relative path for implementation
     */
    private String createRelativePath(Class<?> aClass) {
        String packageName = aClass.getPackage().getName();
        return packageName.replaceAll("\\.", "/") + "/" + aClass.getSimpleName() + "Impl.java";
    }

    @Override
    public void implementJar(Class<?> aClass, File jarFile) throws ImplerException {
        if (!jarFile.exists() && !jarFile.mkdirs()) {
            throw new ImplerException("Couldn't create specified directories: " + jarFile.getAbsolutePath());
        }

        File implementedFile = implementToFile(aClass, jarFile);

        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        if ((compiler.run(null, null, null, implementedFile.getAbsolutePath())) != 0) {
            throw new ImplerException("Error compiling generated class");
        }

        Manifest manifest = new Manifest();
        manifest.getMainAttributes().put(Attributes.Name.MANIFEST_VERSION, "1.0");
        File classFile = new File(replaceLastSubstr(implementedFile.getAbsolutePath(), ".java", ".class"));
        try (JarOutputStream target = new JarOutputStream(
                new FileOutputStream(jarFile.getAbsolutePath() + "/" + aClass.getSimpleName() + "Impl.jar"), manifest);
             InputStream input = new BufferedInputStream(new FileInputStream(classFile))
        ) {

            String name = replaceLastSubstr(createRelativePath(aClass), ".java", ".class");
            JarEntry entry = new JarEntry(name);
            entry.setTime(System.currentTimeMillis());
            target.putNextEntry(entry);
            int count;
            byte[] buffer = new byte[1024];
            while ((count = input.read(buffer)) >= 0) {
                target.write(buffer, 0, count);
            }
            target.closeEntry();
        } catch (IOException e) {
            throw new ImplerException("Error during writing to jar file " + e.getMessage());
        }
    }

    /**
     * Replaces the last occurrence of <code>toCut</code> with the
     * given replacement <code>toPaste</code>.
     *
     * @param text    the string where to replace the last occurrence
     * @param toCut   the occurrence which should be replaced
     * @param toPaste the string to be substituted instead
     * @return the modified <tt>String</tt>
     */
    private String replaceLastSubstr(String text, String toCut, String toPaste) {
        int lastIndex = text.lastIndexOf(toCut);
        if (lastIndex < 0) {
            return text;
        }
        return text.substring(0, lastIndex) + toPaste;
    }
}