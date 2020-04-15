
package com.base.app.property.holder;

import android.support.annotation.CallSuper;
import android.support.annotation.IdRes;
import android.view.View;

import com.base.app.property.AbsBaseAdapter;

import org.jetbrains.annotations.NotNull;

public abstract class AbsViewHolder<MODEL> implements AbsBaseAdapter.IViewHolder<MODEL> {
    private View itemView;
    private int position;
    private MODEL model;

    public AbsViewHolder(@NotNull View itemView) {
        this.itemView = itemView;
    }

    public <T extends View> T findViewById(@IdRes int id) {
        return itemView.findViewById(id);
    }

    @NotNull
    public View getItemView() {
        return itemView;
    }

    @Override
    @CallSuper
    public void bind(int position, MODEL model) {
        this.position = position;
        this.model = model;
    }

    @Override
    public void unbind() {
        model = null;
    }

    public int getPosition() {
        return position;
    }

    public MODEL getModel() {
        return model;
    }
}

