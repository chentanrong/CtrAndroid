
package com.base.app.util;

import org.apache.commons.lang3.ClassUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ClassUtil {
    public static <T> Constructor<T> findNoArgConstructor(Class<T> dataClass) {
        Constructor<T>[] constructors;
        try {
            @SuppressWarnings("unchecked")
            Constructor<T>[] consts = (Constructor<T>[]) dataClass
                    .getDeclaredConstructors();
            // i do this [grossness] to be able to move the Suppress inside the
            // method
            constructors = consts;
        } catch (Exception e) {
            throw new IllegalArgumentException(
                    "Can't lookup declared constructors for " + dataClass, e);
        }
        for (Constructor<T> con : constructors) {
            if (con.getParameterTypes().length == 0) {
                if (!con.isAccessible()) {
                    try {
                        con.setAccessible(true);
                    } catch (SecurityException e) {
                        throw new IllegalArgumentException(
                                "Could not open access to constructor for "
                                        + dataClass);
                    }
                }
                return con;
            }
        }
        if (dataClass.getEnclosingClass() == null) {
            throw new IllegalArgumentException(
                    "Can't find a no-arg constructor for " + dataClass);
        } else {
            throw new IllegalArgumentException(
                    "Can't find a no-arg constructor for " + dataClass
                            + ".  Missing static on inner class?");
        }
    }

    public static <T> Constructor<T> findConstructor(Class<T> dataClass,
                                                     Class<?>... parameterTypes) {
        Constructor<T>[] constructors;
        try {
            @SuppressWarnings("unchecked")
            Constructor<T>[] consts = (Constructor<T>[]) dataClass
                    .getDeclaredConstructors();
            // i do this [grossness] to be able to move the Suppress inside the
            // method
            constructors = consts;
        } catch (Exception e) {
            throw new IllegalArgumentException(
                    "Can't lookup declared constructors for " + dataClass, e);
        }
        Constructor<T> constructor = null;
        for (Constructor<T> con : constructors) {
            boolean flag = true;
            if (con.getParameterTypes().length == parameterTypes.length) {
                Class<?>[] classs = con.getParameterTypes();
                for (int i = 0; i < classs.length; i++) {
                    if (!classs[i].isAssignableFrom(parameterTypes[i])) {
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    constructor = con;
                    break;
                } else {
                    continue;
                }
            }
        }
        if (constructor != null && !constructor.isAccessible()) {
            try {
                constructor.setAccessible(true);
            } catch (SecurityException e) {
                throw new IllegalArgumentException(
                        "Could not open access to constructor for " + dataClass);
            }
        }
        return constructor;
    }

    public static List<Field> getAllFieldsUntil(final Class<?> cls, final Class<?> untilCls) {
//        Validate.isTrue(cls != null, "The class must not be null");
        final List<Field> allFields = new ArrayList<Field>();
        Class<?> currentClass = cls;
        while (currentClass != null && currentClass != untilCls) {
            final Field[] declaredFields = currentClass.getDeclaredFields();
            for (final Field field : declaredFields) {
                allFields.add(field);
            }
            currentClass = currentClass.getSuperclass();
        }
        return allFields;
    }

    public static List<Field> getFieldsWithoutAnnotation(List<Field> fields, final Class<? extends Annotation> annotationCls) {
        List<Field> resultFields = new ArrayList<>(fields.size());
        for (Field field : fields) {
            if (field.getAnnotation(annotationCls) == null) {
                resultFields.add(field);
            }
        }
        return resultFields;
    }

    public static Field[] getFields(Class<?> zClass) {
        return zClass.getDeclaredFields();
    }

    public static Field[] getFields(Class<?> zClass, Class<? extends Annotation> annoClass) {
        List<Field> list = new ArrayList<Field>();
        Field[] fields = getFields(zClass);
        for (Field field : fields) {
            if (field.isAnnotationPresent(annoClass)) {
                list.add(field);
            }
        }
        return list.toArray(new Field[]{});
    }

    public static <T> T newInstance(Class<T> zClass, Object... parameters) {
        Object[] parameters2 = parameters;
        // no arg
        if (parameters2 == null || parameters2.length == 0) {
            try {
                return zClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        // have args
        Class<?>[] parameterTypes = new Class<?>[parameters2.length];
        for (int i = 0; i < parameters2.length; i++) {
            if (parameters2[i] == null) {
                parameterTypes[i] = Void.TYPE;
            } else {
                parameterTypes[i] = parameters2[i].getClass();
            }
        }
        Constructor<T> constructor = findConstructor(zClass, parameterTypes);
        if (constructor == null) {
            return null;
        }
        try {
            return constructor.newInstance(parameters);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Method findGetMethod(Field field, boolean throwExceptions) {
        String methodName = getGetMethodName(field);
        Method fieldGetMethod;
        try {
            fieldGetMethod = field.getDeclaringClass().getMethod(methodName);
        } catch (Exception e) {
            if (throwExceptions) {
                throw new IllegalArgumentException(
                        "Could not find appropriate get method for " + field);
            } else {
                return null;
            }
        }
        if (fieldGetMethod.getReturnType() != field.getType()) {
            if (throwExceptions) {
                throw new IllegalArgumentException("Return type of get method "
                        + methodName + " does not return " + field.getType());
            } else {
                return null;
            }
        }
        return fieldGetMethod;
    }

    public static Method findSetMethod(Field field, boolean throwExceptions) {
        String methodName = getSetMethodName(field);
        Method fieldSetMethod;
        try {
            fieldSetMethod = field.getDeclaringClass().getMethod(methodName,
                    field.getType());
        } catch (Exception e) {
            if (throwExceptions) {
                throw new IllegalArgumentException(
                        "Could not find appropriate set method for " + field);
            } else {
                return null;
            }
        }
        if (fieldSetMethod.getReturnType() != void.class) {
            if (throwExceptions) {
                throw new IllegalArgumentException("Return type of set method "
                        + methodName + " returns "
                        + fieldSetMethod.getReturnType() + " instead of void");
            } else {
                return null;
            }
        }
        return fieldSetMethod;
    }

    public static String getSetMethodName(Field field) {
        return methodFromField(field, "set");
    }

    public static String getGetMethodName(Field field) {
        if (isAssignable(field.getType(), Boolean.class)) {
            return methodFromField(field, "is");
        } else {
            return methodFromField(field, "get");
        }
    }

    private static String methodFromField(Field field, String prefix) {
        return prefix + field.getName().substring(0, 1).toUpperCase()
                + field.getName().substring(1);
    }

    public static boolean isAssignable(Class<?> cls, Class<?> toClass) {
        return ClassUtils.isAssignable(cls, toClass);
    }
}
