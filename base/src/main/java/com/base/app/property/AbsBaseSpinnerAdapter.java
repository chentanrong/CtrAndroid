
package com.base.app.property;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.base.R;


public abstract class AbsBaseSpinnerAdapter<MODEL> extends AbsBaseAdapter<MODEL> {


    public AbsBaseSpinnerAdapter(Context context) {
        super(context);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = getInflater();
            view = getNewDropDownView(inflater, position, parent);
            onDropDownViewCreated(view);
        }
        bindDropDownView(position, view);
        return view;
    }

    protected View getNewDropDownView(LayoutInflater inflater, int position, ViewGroup parent) {
        int id = getDropDownViewResId(position);
        if (id > 0) {
            return inflater.inflate(id, parent, false);
        }
        return null;
    }

    @LayoutRes
    protected int getDropDownViewResId(int position) {
        return 0;
    }

    protected void onDropDownViewCreated(View view) {
        IViewHolder<MODEL> viewHolder = getNewDropDownHolder(view);
        view.setTag(R.id.tag_holder, viewHolder);
    }

    protected IViewHolder<MODEL> getNewDropDownHolder(View view) {
        return null;
    }

    protected void bindDropDownView(int position, View view) {
        MODEL model = getItem(position);
        Object object = view.getTag(R.id.tag_holder);
        if (object instanceof IViewHolder<?>) {
            IViewHolder<MODEL> viewHolder = (IViewHolder<MODEL>) object;
            viewHolder.bind(position, model);
        }
    }
}
