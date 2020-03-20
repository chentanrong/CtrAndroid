package ctr.custumview.wedget.wave;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.LinearInterpolator;
import ctr.common.base.BaseView;

/**
 * 波浪
 */
public class WaveView extends BaseView {


    private Integer waveLength = 100;//波长
    private float waveHeightRadio = 0.5f;//高度比率
    private Integer xw = 0;//相位
    private Integer waveHeight = 0;//波浪位置高度
    private Integer waveSize = 50;//波浪尺寸高度


    private Path path = new Path();
    float moveY = 0;
    float startY = 0f;

    public WaveView(Context context) {
        this(context, null, 0);
    }

    public WaveView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.parseColor("#FF3891"));
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        waveHeight = Float.valueOf(h * waveHeightRadio).intValue();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                moveY = event.getY() - startY;
                waveHeight += (Float.valueOf(moveY).intValue());
                startY= event.getY();
                invalidate();
                break;
            case MotionEvent.ACTION_UP:

                break;
            case MotionEvent.ACTION_CANCEL:

                break;
            case MotionEvent.ACTION_OUTSIDE:

                break;
        }
        return  true;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.parseColor("#FF3891"));
        drawWave(canvas, xw);
        mPaint.setColor(Color.parseColor("#66FF3891"));
        drawWave(canvas, (xw *2) % waveLength);

    }

    private void drawWave(Canvas canvas, Integer xw) {
        path.reset();
        path.moveTo(-waveLength + xw, waveHeight);
        int num = (getWidth() + xw) / waveLength + 2;
        for (int i = 0; i < num; i++) {
            path.rQuadTo(waveLength / 4, -waveSize, waveLength / 2, 0);
            path.rQuadTo(waveLength / 4, waveSize, waveLength / 2, 0);
        }
        path.lineTo(mWidth, mHeight);
        path.lineTo(0, mHeight);
        path.close();
        canvas.drawPath(path, mPaint);
    }

    public void startAnimation() {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, waveLength);
        valueAnimator.setDuration(2000);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                xw = (Integer) animation.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.start();
    }
}
