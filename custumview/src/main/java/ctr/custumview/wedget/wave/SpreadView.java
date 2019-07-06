package ctr.custumview.wedget.wave;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import ctr.custumview.R;

import java.util.ArrayList;
import java.util.List;

public class SpreadView extends View {

    private Context mContext;

    private Paint spreadPaint;
    private Integer delay=500;
    private Integer distance=50;
    private Integer fade=50;

    private float curX;
    private float curY;
    class CircleAttr{
        public Integer radius=0;
        public Integer alpha=255;
        public float x=0;
        public float y=0;

        public CircleAttr(Integer radius, Integer alpha) {
            this.radius = radius>300?300:radius;
            this.alpha = alpha<0?0:alpha;
        }
    }

    List<CircleAttr> circleList=new ArrayList<>();


    public SpreadView(Context context) {
        this(context,null,0);
    }

    public SpreadView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SpreadView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.spreadView);
        delay=typedArray.getInt(R.styleable.spreadView_delay_milliseconds,delay);
        int spreadColor = typedArray.getColor(R.styleable.spreadView_spread_color, ContextCompat.getColor(context, R.color.colorAccent));
        typedArray.recycle();

        //最开始不透明且扩散距离为0
        circleList.add(new CircleAttr(255,0));
        spreadPaint = new Paint();
        spreadPaint.setAntiAlias(true);
        spreadPaint.setAlpha(255);
        spreadPaint.setColor(spreadColor);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        curX=w/2;
        curY=h/2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(curX,curY);
        for(int i=0;i<circleList.size();i++){
            CircleAttr attr = circleList.get(i);
            spreadPaint.setAlpha(attr.alpha);
            canvas.drawCircle(attr.x,attr.y,attr.radius,spreadPaint);
        }

        if (circleList.size() >= 8) {
            circleList.remove(0);
            CircleAttr attr = circleList.get(circleList.size() - 1);
            circleList.add(new CircleAttr(attr.radius+distance,attr.alpha-fade));
        }

        postInvalidateDelayed(delay);
    }
}
