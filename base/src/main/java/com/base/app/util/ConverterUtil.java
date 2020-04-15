
package com.base.app.util;

/**
 * @author sunjie
 * @datetime 2016年3月25日下午4:26:41
 */
public class ConverterUtil {
    @SuppressWarnings("unchecked")
    public static <M> M to(Class<? extends M> type, Object object) {
        if (type == null || type.isInstance(object)) {
            return (M) object;
        } else if (String.class.isAssignableFrom(type)) {
            return (M) toStr(object);
        } else if (Boolean.class.isAssignableFrom(type)) {
            return (M) toBoolean(object);
        } else if (Number.class.isAssignableFrom(type)) {
            return (M) toNumber((Class<? extends Number>) type, object);
        }
        return (M) object;
    }

    @SuppressWarnings("unchecked")
    public static Number toNumber(Class<? extends Number> type, Object object) {
        if (Integer.class.isAssignableFrom(type)) {
            return toInteger(object);
        } else if (Float.class.isAssignableFrom(type)) {
            return toFloat(object);
        } else if (Double.class.isAssignableFrom(type)) {
            return toDouble(object);
        } else if (Long.class.isAssignableFrom(type)) {
            return toLong(object);
        } else if (Byte.class.isAssignableFrom(type)) {
            return toByte(object);
        } else if (Short.class.isAssignableFrom(type)) {
            return toShort(object);
        } else if (object instanceof Number) {
            return (Number) object;
        }
        return null;
    }

    public static Boolean toBoolean(Object object) {
        if (object instanceof Boolean) {
            return (Boolean) object;
        } else if (object instanceof String) {
            String booleanStr = (String) object;
            return "true".equalsIgnoreCase(booleanStr) || "1".equalsIgnoreCase(booleanStr)
                    || "yes".equalsIgnoreCase(booleanStr);
        } else if (object instanceof Number) {
            Number booleanNum = (Number) object;
            return booleanNum.intValue() > 0;
        }
        return null;
    }

    public static Byte toByte(Object object) {
        if (object instanceof Byte) {
            return (Byte) object;
        } else if (object instanceof Number) {
            Number booleanNum = (Number) object;
            return booleanNum.byteValue();
        } else if (object instanceof String) {
            try {
                return Byte.parseByte((String) object);
            } catch (Exception ignore) {
            }
        }
        return null;
    }

    public static Short toShort(Object object) {
        if (object instanceof Short) {
            return (Short) object;
        } else if (object instanceof Number) {
            Number booleanNum = (Number) object;
            return booleanNum.shortValue();
        } else if (object instanceof String) {
            try {
                return Short.parseShort((String) object);
            } catch (Exception ignore) {
            }
        }
        return null;
    }


    public static Integer toInteger(Object object) {
        if (object instanceof Integer) {
            return (Integer) object;
        } else if (object instanceof Number) {
            Number booleanNum = (Number) object;
            return booleanNum.intValue();
        } else if (object instanceof String) {
            try {
                return Integer.parseInt((String) object);
            } catch (Exception ignore) {
            }
        }
        return null;
    }

    public static Long toLong(Object object) {
        if (object instanceof Long) {
            return (Long) object;
        } else if (object instanceof Number) {
            Number booleanNum = (Number) object;
            return booleanNum.longValue();
        } else if (object instanceof String) {
            try {
                return Long.parseLong((String) object);
            } catch (Exception ignore) {
            }
        }
        return null;
    }

    public static Float toFloat(Object object) {
        if (object instanceof Float) {
            return (Float) object;
        } else if (object instanceof Number) {
            Number booleanNum = (Number) object;
            return booleanNum.floatValue();
        } else if (object instanceof String) {
            try {
                return Float.parseFloat((String) object);
            } catch (Exception ignore) {
            }
        }
        return null;
    }

    public static Double toDouble(Object object) {
        if (object instanceof Double) {
            return (Double) object;
        } else if (object instanceof Number) {
            Number booleanNum = (Number) object;
            return booleanNum.doubleValue();
        } else if (object instanceof String) {
            try {
                return Double.parseDouble((String) object);
            } catch (Exception ignore) {
            }
        }
        return null;
    }

    public static String toStr(Object object) {
        return object != null ? object.toString() : null;
    }
}
