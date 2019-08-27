package ctr.custumview.layout;

import android.content.Context;
import android.graphics.Rect;
import android.text.Layout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.Map;

public class FlowLayout extends ViewGroup {
    public FlowLayout(Context context) {
        super(context);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //遍历去调用所有子元素的measure方法（child.getMeasuredHeight()才能获取到值，否则为0）
        measureChildren(widthMeasureSpec,heightMeasureSpec);
        int measureWidth=0;
        int measureHeight=0;

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        Map<String, Integer> compute = compute(widthSize-getPaddingRight());

        if(widthMode==MeasureSpec.EXACTLY){  //对应width 为match_parent
            measureWidth=widthSize;
        }else if(widthMode==MeasureSpec.AT_MOST){  //对应width 为wrap-content
            measureHeight=compute.get("allChildWidth");
        }
        if(heightMode==MeasureSpec.EXACTLY){  //对应height 为match_parent
            measureHeight=heightSize;
        }else if(heightMode==MeasureSpec.AT_MOST){  //对应height 为wrap-content
            measureHeight=compute.get("allChildHeight");
        }
        //设置布局真实高宽
        setMeasuredDimension(measureWidth,measureHeight);
    }

    private Map<String, Integer> compute(int flowWidth) {

        int rowNum=0;
        int rowsWidth=getPaddingLeft();
        int columnHeight=getPaddingTop();
        int rowsMaxHeight=0;
        for(int i=0;i<getChildCount();i++){
            View child = getChildAt(i);
            int measuredHeight = child.getMeasuredHeight();
            int measuredWidth = child.getMeasuredWidth();
            MarginLayoutParams layoutParams = (MarginLayoutParams) child.getLayoutParams();
            int childWidth= layoutParams.leftMargin+layoutParams.rightMargin+measuredWidth;
            int childHeight=layoutParams.topMargin+layoutParams.bottomMargin+measuredHeight;
            rowsMaxHeight=Math.max(rowsMaxHeight,childHeight);

            if(rowsWidth+childWidth>flowWidth){
                rowsWidth=getPaddingLeft();
                columnHeight+=rowsMaxHeight;
                rowsMaxHeight=childHeight;
                rowNum++;
            }
            rowsWidth+=childWidth;
            child.setTag(new Rect(rowsWidth-childWidth+layoutParams.leftMargin,columnHeight+layoutParams.topMargin,
                    rowsWidth-layoutParams.rightMargin,columnHeight+childHeight-layoutParams.bottomMargin));

        }
        Map<String, Integer> flowMap = new HashMap<>();
        if(rowNum>1){
            flowMap.put("allChildWidth", rowsWidth);
        }else{
            flowMap.put("allChildWidth", flowWidth);
        }
        flowMap.put("allChildHeight", columnHeight+rowsMaxHeight+getPaddingBottom());
        return flowMap;
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i=0;i<getChildCount();i++){
            View child=getChildAt(i);
            Rect tag = (Rect) child.getTag();
            child.layout(tag.left,tag.top,tag.right,tag.bottom);
        }

    }
    
}
