package com.base.app.property;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.base.R;
import com.base.app.property.holder.AbsViewHolder;


/**
 * Created by sunjie on 2016/9/6.
 */
public class StoreDataAdapter<S, U, MODEL extends IStoreModel<S, U>> extends AbsStoreDataAdapter<S, U, MODEL> {
    public StoreDataAdapter(Context context) {
        super(context);
    }

    @Override
    protected IViewHolder<MODEL> getNewHolder(@NonNull View view) {
        return new StoreModelHolder<S, U, MODEL>(view);
    }

    @Override
    protected IViewHolder<MODEL> getNewDropDownHolder(View view) {
        return new StoreModelDropDownHolder<S, U, MODEL>(view);
    }

    @Override
    @LayoutRes
    protected int getViewResId(int position) {
        return R.layout.multi_spinner_item;
    }

    @LayoutRes
    protected int getDropDownViewResId(int position) {
        return R.layout.support_simple_spinner_dropdown_item;
    }

    public static class StoreModelDropDownHolder<S, U, MODEL extends IStoreModel<S, U>> extends AbsViewHolder<MODEL> {
        TextView text;

        public StoreModelDropDownHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(android.R.id.text1);
        }

        @Override
        public void bind(int position, MODEL model) {
            super.bind(position, model);
            U ud = model.getDropDownUIData();
            text.setText(ud == null ? null : ud.toString());
        }
    }

    public static class StoreModelHolder<S, U, MODEL extends IStoreModel<S, U>> extends AbsViewHolder<MODEL> {
        TextView text;

        public StoreModelHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(android.R.id.text1);
        }

        @Override
        public void bind(int position, MODEL model) {
            super.bind(position, model);
            U ud = model.getUIData();
            text.setText(ud == null ? null : ud.toString());
        }
    }
}
