package com.base.app.property;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.ViewGroup;


import com.base.app.AbsRecyclerViewHolder;
import com.base.app.util.ClassUtil;

import java.lang.reflect.Constructor;


public class PropertyRecyclerAdapter extends AbsRecyclerAdapter<PropertyItem, AbsRecyclerViewHolder<PropertyItem>> {

    public PropertyRecyclerAdapter(Context context) {
        super(context);
        setModelFilter(new IModelFilter<PropertyItem>() {
            @Override
            public boolean filter(PropertyItem propertyItem) {
                return propertyItem.isVisible();
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        PropertyItem item = getItem(position);
        return item.getViewType();
    }

    @Override
    public void onViewRecycled(@NonNull AbsRecyclerViewHolder<PropertyItem> holder) {
        super.onViewRecycled(holder);
    }

    @NonNull
    @Override
    public AbsRecyclerViewHolder<PropertyItem> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Class<? extends AbsRecyclerViewHolder<PropertyItem>> zClass = PropertyRegister.getHolderClass(viewType);
        Constructor<? extends AbsRecyclerViewHolder<PropertyItem>> constructor = ClassUtil.findConstructor(zClass, Context.class, ViewGroup.class);
        Context context = getContext();
        if (context != null && constructor != null) {
            try {
                return constructor.newInstance(context, parent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            constructor = ClassUtil.findConstructor(zClass, ViewGroup.class);
            if (constructor == null) {
                return null;
            }
            try {
                return constructor.newInstance(parent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}