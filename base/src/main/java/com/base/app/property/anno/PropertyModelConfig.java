package com.base.app.property.anno;


import com.base.app.util.ClassUtil;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;


public class PropertyModelConfig {
    private Class<?> modelClass;
    private Map<String, PropertyFieldConfig> configHashMap = new HashMap<String, PropertyFieldConfig>();

    public PropertyModelConfig(Class<?> modelClass) {
        this.modelClass = modelClass;
        for (Field field : ClassUtil.getFields(modelClass, PropertyField.class)) {
            PropertyFieldConfig propertyFieldConfig = new PropertyFieldConfig(field);
            configHashMap.put(field.getName(), propertyFieldConfig);
        }
    }

    public void setValue(Object modelObject, String key, Object value) {
        configHashMap.get(key).setValue(modelObject, value);
    }

    public Object getValue(Object modelObject, String key) {
        return configHashMap.get(key).getValue(modelObject);
    }

    public Map<String, PropertyFieldConfig> getConfigHashMap() {
        return configHashMap;
    }

    public Class<?> getModelClass() {
        return modelClass;
    }
}
