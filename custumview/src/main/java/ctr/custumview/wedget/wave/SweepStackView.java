package ctr.custumview.wedget.wave;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.*;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import ctr.common.base.BaseView;
import ctr.custumview.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * 摇奖
 */
public class SweepStackView extends BaseView {

    private Integer mCenter;
    private Integer mRadius;
    private String[] colors = {"#603891", "#609891", "#300891", "#900831"};
    private String[] texts = {"上家喝一杯", "下家喝一杯", "本人喝一杯", "跳过", "真心话", "大冒险", "唱首歌吧", "跳个舞吧",
            "上家唱首歌", "下家唱首歌", "上家跳个舞", "下家跳个舞", "上家大冒险", "下家大冒险"};
    private Integer[] images = {R.drawable.jp1};
    private List<Bitmap> bitmaps = new ArrayList<>();
    private Integer Num = 12;
    private float startAngle = 0;
    private float sweepAngle = 0;

    private float mTextSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SHIFT, 40, getResources().getDisplayMetrics());
    private Paint mTextPaint;
    private ObjectAnimator animator;

    private ResultLisenter mlistener;
    private Bitmap[] mBitmaps = new Bitmap[texts.length];

    public SweepStackView(Context context) {
        this(context, null, 0);
    }

    public SweepStackView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SweepStackView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.parseColor("#333891"));

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setTextSize(mTextSize);

        for (Integer i : images) {
            bitmaps.add( BitmapFactory.decodeResource(getResources(), i));
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mCenter = Math.min(mWidth, mHeight) / 2;
        mRadius = (Math.min(mWidth, mHeight) - getPaddingStart() * 2) / 2;
        setMeasuredDimension(Math.min(mWidth, mHeight), Math.min(mWidth, mHeight));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.translate(mCenter, mCenter);
        mPaint.setColor(Color.parseColor("#333891"));
        canvas.drawCircle(0, 0, mRadius, mPaint);
        int padding=mRadius/8;
        RectF rectF = new RectF(padding - mRadius, padding - mRadius, mRadius -padding, mRadius - padding);
        sweepAngle = 360 / Num;
        float startAngle=this.startAngle;
        for (int i = 0; i < Num; i++) {
            mPaint.setColor(Color.parseColor(colors[i % colors.length]));
            canvas.drawArc(rectF, startAngle, sweepAngle, true, mPaint);
            drawText(canvas, texts[i], rectF,startAngle);
            startAngle += sweepAngle;
        }
        startAngle=this.startAngle;
        for (int i = 0; i < Num; i++) {
            drawBitmap(canvas, startAngle, bitmaps.get(i%bitmaps.size()),texts[i]);
            startAngle += sweepAngle;
        }
        //指针
        Path pointPath = new Path();
        pointPath.moveTo(0,-mRadius+padding);
        pointPath.lineTo(padding/2,-mRadius+padding/2);
        pointPath.lineTo(-padding/2,-mRadius+padding/2);
        pointPath.close();
        canvas.drawPath(pointPath,mTextPaint);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startAnimation();
                break;
            case MotionEvent.ACTION_MOVE:

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

    private void drawBitmap(Canvas canvas, Float startAngle, Bitmap bitmap, String text) {
        int imageWidth = 10;
        RectF rectF = new RectF(-imageWidth, -imageWidth, imageWidth, imageWidth);
        float rotateAngle = startAngle + sweepAngle / 2;
        canvas.rotate(rotateAngle);
        canvas.translate(0, mRadius / 3);
        canvas.drawBitmap(bitmap, null, rectF, null);
        canvas.translate(0, -mRadius / 3);
        canvas.rotate(-rotateAngle);
    }

    private void drawText(Canvas canvas, String mString, RectF rectF,float startAngle) {
        Path path = new Path();
        path.addArc(rectF, startAngle, sweepAngle);
        float textWidth = mTextPaint.measureText(mString);

        int hOffset = (int) (mRadius * 2 * Math.PI / Num / 2 - textWidth / 2);

        int l = (int) ((360 / Num) * Math.PI * mRadius / 180);
        if (textWidth > l * 4 / 5) {
            int index = mString.length() / 2;
            String startText = mString.substring(0, index);
            String endText = mString.substring(index, mString.length());

            float startTextWidth = mTextPaint.measureText(startText);
            float endTextWidth = mTextPaint.measureText(endText);
            //水平偏移
            hOffset = (int) (mRadius * 2 * Math.PI / Num / 2 - startTextWidth / 2);
            int endHOffset = (int) (mRadius * 2 * Math.PI / Num / 2 - endTextWidth / 2);
            //文字高度
            int h = (int) ((mTextPaint.ascent() + mTextPaint.descent()) * 1.5);

            //根据路径绘制文字
            canvas.drawTextOnPath(startText, path, hOffset, mRadius / 6, mTextPaint);
            canvas.drawTextOnPath(endText, path, endHOffset, mRadius / 6 - h, mTextPaint);
        } else {
            //根据路径绘制文字
            canvas.drawTextOnPath(mString, path, hOffset, mRadius / 6, mTextPaint);
        }

    }
    public void startAnimation() {
        final double rotateToPosition= Math.random() * 360;
        int toDegree = Double.valueOf(360f *2 + rotateToPosition).intValue();
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, toDegree);
        valueAnimator.setDuration(2000);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.setRepeatMode(ValueAnimator.REVERSE);
        valueAnimator.setRepeatCount(1);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                startAngle = (Integer) animation.getAnimatedValue()%360;
                invalidate();
            }
        });
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (mlistener != null) {
                    int position = Double.valueOf(rotateToPosition / (360 / Num)).intValue();
                    mlistener.value(position,texts[position - 1]);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {


            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        valueAnimator.start();
    }
    public void rotate() {

        float rotateToPosition=Math.round(360);

        float toDegree = 360f *2 + rotateToPosition;

        animator = ObjectAnimator.ofFloat(SweepStackView.this, "rotation", 0, toDegree);
        animator.setDuration(5000);
        animator.setRepeatCount(0);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.setAutoCancel(true);
        animator.start();

        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                //指针指向的方向为270度
                if (mlistener != null) {
                    int position=0;
                    mlistener.value(position,texts[position - 1]);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {


            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    public interface ResultLisenter{
        void value(int position,String msg);
    }

    public void setMlistener(ResultLisenter mlistener) {
        this.mlistener = mlistener;
    }
}
