package com.base.app.property.anno;

import android.content.Context;


import com.base.app.AbsRecyclerViewHolder;
import com.base.app.util.ClassUtil;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by sunjie on 2016/6/12.
 */
public class PropertyFieldConfig {
    private String fieldName;
    private int labelResId;
    private String labelName;
    private boolean valid;
    private boolean visible;
    private int index;
    private boolean useGetSet;
    private Class<? extends AbsRecyclerViewHolder> holderClass;
    private boolean canBeNull;

    private Field field;
    private Method fieldGetMethod;
    private Method fieldSetMethod;

    public PropertyFieldConfig(Field field) {
        this.field = field;
        PropertyField anno = field.getAnnotation(PropertyField.class);
        if (anno == null) {
            throw new RuntimeException("field is not PropertyField");
        }
        fieldName = field.getName();
        labelResId = anno.labelResId();
        labelName = anno.label();
        index = anno.index();
        visible = anno.visible();
        valid = anno.valid();
        useGetSet = anno.useGetSet();
        if (useGetSet) {
            fieldGetMethod = ClassUtil.findGetMethod(field, false);
            fieldSetMethod = ClassUtil.findSetMethod(field, false);
        }
        canBeNull = anno.canBeNull();
        holderClass = anno.holderClass();
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public int getLabelResId() {
        return labelResId;
    }

    public void setLabelResId(int labelResId) {
        this.labelResId = labelResId;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean isUseGetSet() {
        return useGetSet;
    }

    public void setUseGetSet(boolean useGetSet) {
        this.useGetSet = useGetSet;
    }

    public Class<? extends AbsRecyclerViewHolder> getHolderClass() {
        return holderClass;
    }

    public void setHolderClass(Class<? extends AbsRecyclerViewHolder> holderClass) {
        this.holderClass = holderClass;
    }

    public boolean isCanBeNull() {
        return canBeNull;
    }

    public void setCanBeNull(boolean canBeNull) {
        this.canBeNull = canBeNull;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public Method getFieldGetMethod() {
        return fieldGetMethod;
    }

    public void setFieldGetMethod(Method fieldGetMethod) {
        this.fieldGetMethod = fieldGetMethod;
    }

    public Method getFieldSetMethod() {
        return fieldSetMethod;
    }

    public void setFieldSetMethod(Method fieldSetMethod) {
        this.fieldSetMethod = fieldSetMethod;
    }

    public boolean setValue(Object setObject, Object value) {
        if (value != null) {
            Class<?> type = field.getType();
            value = ConverterUtil.to(type, value);
        }
        boolean flag = false;
        if (isUseGetSet()) {
            Method method = getFieldSetMethod();
            if (!method.isAccessible()) {
                method.setAccessible(true);
            }
            try {
                method.invoke(setObject, value);
                flag = true;
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                e.printStackTrace();
            }
        } else {
            Field field = getField();
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }
            try {
                field.set(setObject, value);
                flag = true;
            } catch (IllegalAccessException | IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
        return flag;
    }

    public Object getValue(Object modelObject) {
        Object object = null;
        if (isUseGetSet()) {
            Method method = getFieldGetMethod();
            if (!method.isAccessible()) {
                method.setAccessible(true);
            }
            try {
                object = method.invoke(modelObject);
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                e.printStackTrace();
            }
        } else {
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }
            try {
                object = field.get(modelObject);
            } catch (IllegalAccessException | IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
        return object;
    }

    public PropertyItem toPropertyItem(PropertyItem propertyItem) {
        return toPropertyItem(null, propertyItem);
    }

    public PropertyItem toPropertyItem(Context context, PropertyItem propertyItem) {
        propertyItem.setKey(fieldName);
        String label = labelName;
        if (context != null && labelResId > 0) {
            label = context.getResources().getString(labelResId);
        }

        propertyItem.setLabel(label == null || label.isEmpty() ? fieldName : label);
        propertyItem.setEnable(valid);
        propertyItem.setVisible(visible);
        propertyItem.setNotnull(!canBeNull);
        propertyItem.setIndex(index);
        Class<?> holderClass = getHolderClass();
        Class<?> type = field.getType();
        propertyItem.setValueType(type);
        if (holderClass == AbsRecyclerViewHolder.class) {
            if (CharSequence.class.isAssignableFrom(type)) {
                holderClass = EditTextHolder.class;
            } else if (Boolean.class.isAssignableFrom(type)) {
                holderClass = EditBooleanHolder.class;
            } else if (Number.class.isAssignableFrom(type)) {
                holderClass = EditNumberHolder.class;
            } else if (Enum.class.isAssignableFrom(type)) {
                holderClass = EditTextHolder.class;
            }
        }
        int viewType = PropertyRegister.registerHolder(holderClass);
        propertyItem.setViewType(viewType);
        return propertyItem;
    }

}
