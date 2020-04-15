package com.base.app.property.anno;



import com.base.app.AbsRecyclerViewHolder;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by sunjie on 2016/6/12.
 */
@Target(FIELD)
@Retention(RUNTIME)
public @interface PropertyField {
    int index() default 0;

    int labelResId() default 0;

    String label() default "";

    boolean visible() default true;

    boolean valid() default true;

    boolean canBeNull() default true;

    boolean useGetSet() default false;

    Class<? extends AbsRecyclerViewHolder> holderClass() default AbsRecyclerViewHolder.class;
}
