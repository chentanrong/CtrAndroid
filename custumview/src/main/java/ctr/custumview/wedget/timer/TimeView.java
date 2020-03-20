package ctr.custumview.wedget.timer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.sql.Timestamp;
import java.util.Timer;
import java.util.TimerTask;

import ctr.common.base.BaseView;

/**
 *  时钟
 */
public class TimeView extends BaseView {

    private Context mContext;


    public TimeView(Context context) {
        this(context, null, 0);
    }

    public TimeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TimeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
    }
    

    private float mSecondDegree;//秒针的度数
    private float mMinDegree;//秒针的度数
    private float mHourDegree;//秒针的度数
    private Timer mTimer = new Timer();
    private TimerTask task = new TimerTask() {
        @Override
        public void run() {//具体的定时任务逻辑
            if (mSecondDegree == 360) {//因为圆一圈为360度，所以走满一圈角度清零
                mSecondDegree = 0;
            }
            if (mMinDegree == 360) {//因为圆一圈为360度，所以走满一圈角度清零
                mMinDegree = 0;
            }
            if (mHourDegree == 360) {//因为圆一圈为360度，所以走满一圈角度清零
                mHourDegree = 0;
            }
            mSecondDegree = mSecondDegree +6;//秒针
            mMinDegree = mMinDegree + 0.1f;//分针
            mHourDegree = mHourDegree + 1f / 120;//时针
            postInvalidate();
        }
    };

    public void start() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        mMinDegree = timestamp.getMinutes() * 6f;
        mHourDegree = timestamp.getHours()%12 * 30f;
        mSecondDegree = timestamp.getSeconds() * 6f;
        mTimer.schedule(task, 0, 1000);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(Math.min(mWidth, mHeight), Math.min(mWidth, mHeight));
    }
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, getWidth() / 3, mPaint);
        canvas.drawPoint(getWidth() / 2, getHeight() / 2, mPaint);

        mPaint.setStrokeWidth(1);
        canvas.translate(getWidth() / 2, getHeight() / 2);

        int kdLength = 10;
        int secLength = 20;
        int minLength = 30;

        for (int i = 0; i < 360; i++) {
            if (i % 30 == 0) {
                canvas.drawLine(getWidth() / 3 - minLength, 0, getWidth() / 3, 0, mPaint);
            } else if (i % 6 == 0) {
                canvas.drawLine(getWidth() / 3 - secLength, 0, getWidth() / 3, 0, mPaint);
            } else {
                canvas.drawLine(getWidth() / 3 - kdLength, 0, getWidth() / 3, 0, mPaint);
            }
            canvas.rotate(1);
        }
        mPaint.setTextSize(25);
        mPaint.setStyle(Paint.Style.FILL);
        for (int i = 0; i < 12; i++) {
            if (i == 0) {
                drawNum(canvas, i * 30, 12 + "", mPaint);
            } else {
                drawNum(canvas, i * 30, i + "", mPaint);
            }
        }

        int secPointLength = 180;
        canvas.save();
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(2);
        canvas.rotate(mSecondDegree);
        canvas.drawLine(0, 0, 0, -secPointLength, mPaint);
        canvas.restore();

        int minPointLength = 120;
        canvas.save();
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(4);
        canvas.rotate(mMinDegree);
        canvas.drawLine(0, 0, 0, -minPointLength, mPaint);
        canvas.restore();

        int hourPointLength = 80;
        canvas.save();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(7);
        canvas.rotate(mHourDegree);
        canvas.drawLine(0, 0, 0, -hourPointLength, mPaint);
        canvas.restore();
        super.onDraw(canvas);
    }

    private void drawNum(Canvas canvas, int degree, String text, Paint paint) {
        Rect textBound = new Rect();
        paint.getTextBounds(text, 0, text.length(), textBound);
        canvas.rotate(degree);
        canvas.translate(0, 50 - getWidth() / 3);//这里的50是坐标中心距离时钟最外边框的距离，当然你可以根据需要适当调节
        canvas.rotate(-degree);
        canvas.drawText(text, -textBound.width() / 2,
                textBound.height() / 2, paint);
        canvas.rotate(degree);
        canvas.translate(0, getWidth() / 3 - 50);
        canvas.rotate(-degree);
    }
}
