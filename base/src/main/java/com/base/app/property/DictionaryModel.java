package com.base.app.property;

public class DictionaryModel extends AbsStoreModel<String, String> {
    private String code;
    private String name;
    private String separator=":";
    private boolean storeName;
    private String dropDownString;

    public String getSeparator() {
        return separator;
    }

    public void setSeparator(String separator) {
        this.separator = separator;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStoreData() {
        return isStoreName() ? name : code;
    }

    public String getUIData() {
        return name;
    }

    public boolean isStoreName() {
        return storeName;
    }

    public void setStoreName(boolean storeName) {
        this.storeName = storeName;
    }

    public String getDropDownString() {
        return dropDownString;
    }

    public void setDropDownString(String dropDownString) {
        this.dropDownString = dropDownString;
    }

    @Override
    public String getDropDownUIData() {
        if (dropDownString != null) return dropDownString;
        else return code + separator + name;
    }
}
