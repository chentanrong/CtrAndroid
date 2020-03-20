package ctr.custumview.wedget.signture;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import ctr.common.base.BaseView;


/**
 * @User chentanrong
 * @Date 2020/1/14
 * @Desc  手签板
 **/
public class SigntureView extends BaseView {
    Path path = new Path();
    Canvas canvas = null;

    public SigntureView(Context context) {
        this(context, null, 0);
    }

    public SigntureView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SigntureView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(6);
        mPaint.setColor(Color.parseColor("#000000"));
    }


    @Override
    protected void onDraw(Canvas canvas) {

        canvas.drawPath(path, mPaint);
        this.canvas = canvas;
        super.onDraw(canvas);
    }

    public void clear() {
        path.reset();
        invalidate();
    }

    public void confirm(File des) {
        if (canvas != null) {
            canvas.save();
            Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            canvas.drawPath(path, mPaint);
            FileOutputStream fos = null;
            try {
                if(!des.exists()){
                    des.createNewFile();
                }
                fos = new FileOutputStream(des);
                bitmap.compress(Bitmap.CompressFormat.PNG, 50, fos);
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                float x = event.getX();
                float y = event.getY();
                path.moveTo(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                float xMove = event.getX();
                float yMove = event.getY();
                path.lineTo(xMove, yMove);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                float xup = event.getX();
                float yup = event.getY();
                path.lineTo(xup, yup);
                invalidate();
                break;
        }

        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(mWidth, mHeight);
    }
}
