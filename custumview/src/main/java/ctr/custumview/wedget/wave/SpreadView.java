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
    private Integer delay=100;
    private Integer distance=4;
    private Integer fade=5;

    private int cs=0;
    private float curX;
    private float curY;


    int[] radius={40,80,120,160,200};
    int[] alphas={250,200,150,100,50};
    List<CircleAttr> circleList=new ArrayList<>();

    CircleAttr center;
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
        circleList.add(new CircleAttr(radius[0],alphas[0]));
        circleList.add(new CircleAttr(radius[1],alphas[1]));
        circleList.add(new CircleAttr(radius[2],alphas[2]));
        circleList.add(new CircleAttr(radius[3],alphas[3]));
        circleList.add(new CircleAttr(radius[4],alphas[4]));
        center=new CircleAttr(40,250);

        spreadPaint = new Paint();
        spreadPaint.setAntiAlias(true);
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
            attr.alpha=attr.alpha-fade;
            attr.radius=attr.radius+distance;

        }
        for(int i=0;i<circleList.size();i++){
            CircleAttr attr = circleList.get(i);
            spreadPaint.setAlpha(attr.alpha);
            canvas.drawCircle(attr.x,attr.y,attr.radius,spreadPaint);
        }
        cs++;
        if(cs==10){
            for(int i=0;i<circleList.size();i++){
                CircleAttr attr = circleList.get(i);
                attr.alpha=alphas[i];
                attr.radius=radius[i];
            }
            cs=0;
        }
        spreadPaint.setAlpha(center.alpha);
        canvas.drawCircle(center.x,center.y,center.radius,spreadPaint);
        postInvalidateDelayed(delay);
    }

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
}
