package com.base.app.util;

import android.util.Log;

import java.lang.reflect.Field;
import java.util.Date;

public class ObjectUtil {
    public static boolean equals(Object a, Object b) {
        return (a == b) || (a != null && a.equals(b));
    }

    /**
     * 对象拷贝，字段名相同并且字段类型赋值
     *
     * @param srcObj 源对象
     * @param desObj 目标对象
     * @throws IllegalAccessException
     */
    public static void copyObjWidthSameField(Object srcObj, Object desObj) throws IllegalAccessException {

        Class<?> srcClass = srcObj.getClass();
        Field[] srcFields = srcClass.getDeclaredFields();
        Class<?> desClass = desObj.getClass();
        Field[] desFields = desClass.getDeclaredFields();
        for (Field srcFiled : srcFields) {
            String srcName = srcFiled.getName().toLowerCase();
            for (Field desField : desFields) {
                String desName = desField.getName().toLowerCase();
                if (srcName.equals(desName)) {
                    desField.setAccessible(true);
                    srcFiled.setAccessible(true);
                    Object srcValue = srcFiled.get(srcObj);
                    Object desValue = convertTypeValue(desField.getType(), srcValue);
                    if (desValue != null) {
                            desField.set(desObj, desValue);
                        }

                    break;
                }
            }
        }

    }

    /**
     * 将一个基本数据类型对象转换成传入的基本类型的对象
     *
     * @param type 目标类型
     * @param src  源对象
     * @return 目标类型的对象
     */
    public static Object convertTypeValue(Class<?> type, Object src) {
        if(src==null||type==null){
            return null;
        }
        String canonicalName = type.getCanonicalName();
        if (canonicalName == null)
            return null;
        String srcStr = String.valueOf(src);
        Object desObj = null;
        try {
            switch (canonicalName) {
                case "java.lang.String":
                    desObj = srcStr;
                    break;
                case "java.lang.Integer":
                    desObj = Integer.valueOf(srcStr);
                    break;
                case "java.lang.Long":
                    desObj = Long.valueOf(srcStr);
                    break;
                case "java.lang.Boolean":
                    if ("是".equals(srcStr) || "true".equals(srcStr) || "1".equals(srcStr)) {
                        desObj = true;
                    } else if ("否".equals(srcStr) || "false".equals(srcStr) || "0".equals(srcStr)) {
                        desObj = false;
                    }
                    break;
                case "java.lang.Float":
                    desObj = Float.valueOf(srcStr);
                    break;
                case "java.lang.Short":
                    desObj = Short.valueOf(srcStr);
                    break;
                case "java.lang.Byte":
                    desObj = Byte.valueOf(srcStr);
                    break;
                case "java.lang.Double":
                    desObj = Double.valueOf(srcStr);
                    break;
                case "java.util.Date":
                    desObj =new Date(srcStr);
                    break;
            }
        }catch (Exception e){
            Log.e(ObjectUtil.class.getCanonicalName(),"类型转换出错");
            e.printStackTrace();
        }
        return desObj;
    }
}
