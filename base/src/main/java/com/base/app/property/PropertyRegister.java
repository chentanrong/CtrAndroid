package com.base.app.property;

import java.util.ArrayList;
import java.util.List;

public class PropertyRegister {
    private final static List<Class> classList = new ArrayList<>();

    public static int registerHolder(Class holderClass) {
        int index = classList.indexOf(holderClass);
        if (index < 0) {
            classList.add(holderClass);
            index = classList.size() - 1;
        }
        return index;
    }

    public static void unregisterHolder(Class holderClass) {
        classList.remove(holderClass);
    }

    public static Class getHolderClass(int viewType) {
        return classList.get(viewType);
    }

    public static int getViewType(Class holderClass) {
        return classList.indexOf(holderClass);
    }
}
