package ctr.custumview.wedget.image;

/**
 * @User chentanrong
 * @Date 2020/3/20
 * @Desc
 **/
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.media.ExifInterface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import ctr.common.wedge.gesture.MoveGestureDetector;

public class RegionImageView extends View{


    public RegionImageView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    private BitmapRegionDecoder mDecoder;
    /**
     * 图片的宽度和高度
     */
    private int mImageWidth, mImageHeight;
    /**
     * view高宽
     */
    private int mViewWidth , mViewHeight ;
    /**
     * 缩放值
     */
    private float mScale=1f  , mCurrentScale =1f;
    /**
     * 绘制的区域
     */
    private volatile Rect mRect = new Rect();
    /**
     * 缩放器
     */
    private volatile Matrix  mMatrix = new Matrix();
    /**
     * 放大几倍
     */
    private int mMultiple =6;


    private MoveGestureDetector mDetector;

    private ScaleGestureDetector scaleGestureDetector;


    private static final BitmapFactory.Options options = new BitmapFactory.Options();

    static
    {
        options.inPreferredConfig = Bitmap.Config.RGB_565;
    }
    public static void calculateInSampleSize(BitmapFactory.Options options, String filePath) {
        if (options.outHeight == -1 || options.outWidth == -1) {
            try {
                ExifInterface exifInterface = new ExifInterface(filePath);
                int tempHeight = exifInterface.getAttributeInt(ExifInterface.TAG_IMAGE_LENGTH
                        , ExifInterface.ORIENTATION_NORMAL);
                int tempWidth = exifInterface.getAttributeInt(ExifInterface.TAG_IMAGE_WIDTH
                        , ExifInterface.ORIENTATION_NORMAL);

                options.outWidth = tempHeight;
                options.outHeight = tempWidth;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mViewWidth = w;
        mViewHeight = h;
        mRect.top = 0;
        mRect.left = 0;
        mRect.right = mImageWidth;
        mRect.bottom = mImageHeight;

        float v = Float.valueOf(mViewWidth) / mViewHeight;
        float i= Float.valueOf(mImageWidth) / mImageHeight;
        if(v <i){
            mScale = Float.valueOf(mViewWidth)/mImageWidth;
        }else {
            mScale = Float.valueOf(mViewHeight)/mImageHeight;
        }
        mCurrentScale = mScale;
    }

    public void setInputStream(File file)    {
    FileInputStream is = null;
        try
        {
          is=  new FileInputStream(file);
            mDecoder = BitmapRegionDecoder.newInstance(is, false);
            mDecoder.getHeight();

            mImageWidth =  mDecoder.getWidth();
            mImageHeight = mDecoder.getHeight();
            requestLayout();
            invalidate();
        } catch (IOException e)
        {
            e.printStackTrace();
        } finally
        {
            try
            {
                if (is != null) is.close();
            } catch (Exception e)
            {
            }
        }
    }
    public void recycle(){
        if(bitmap!=null){
            bitmap.recycle();
        }
    }


    public void init()
    {
        mDetector = new MoveGestureDetector(getContext(), new MoveGestureDetector.SimpleMoveGestureDetector()
        {
            @Override
            public boolean onMove(MoveGestureDetector detector)
            {
                int moveX = (int) detector.getMoveX();
                int moveY = (int) detector.getMoveY();
                mRect.offset(-moveX, -moveY);
                handleBorder();
                invalidate();
                return true;
            }
        });

        scaleGestureDetector=new ScaleGestureDetector(getContext(),new ScaleGestureDetector.SimpleOnScaleGestureListener(){
            @Override
            public boolean onScale(ScaleGestureDetector detector) {
                //获取与上次事件相比，得到的比例因子
                float scaleFactor = detector.getScaleFactor();
                mCurrentScale*=scaleFactor;
                if(mCurrentScale>mScale*mMultiple){
                    mCurrentScale = mScale*mMultiple;
                }else if(mCurrentScale<=mScale){
                    mCurrentScale = mScale;
                }
                mRect.right = mRect.left+(int)(mViewWidth/mCurrentScale);
                mRect.bottom = mRect.top+(int)(mViewHeight/mCurrentScale);
                handleBorder();
                invalidate();
                return true;
            }
            @Override
            public boolean onScaleBegin(ScaleGestureDetector detector) {
                return true;
            }
        });
    }


    private void handleBorder(){
        if(mRect.bottom>mImageHeight){
            mRect.bottom = (int) mImageHeight;
            mRect.top = (int) (mImageHeight-mViewHeight/mCurrentScale);
        }
        if(mRect.right>mImageWidth){
            mRect.right = (int) mImageWidth;
            mRect.left = (int) (mImageWidth-mViewWidth/mCurrentScale);
        }
        if(mRect.top<0){
            mRect.top = 0;
            mRect.bottom = (int) (mViewHeight/mCurrentScale);
        }
        if(mRect.left<0){
            mRect.left = 0;
            mRect.right = (int) (mViewWidth/mCurrentScale);
        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent event)    {
        boolean b = mDetector.onTouchEvent(event);
        boolean b1 = scaleGestureDetector.onTouchEvent(event);
        return b||b1;
    }
    Bitmap bitmap;

    @Override
    protected void onDraw(Canvas canvas)
    {
        //只获取mReact区域中的图像绘制到画布上
  /*      if(bitmap!=null)
        options.inBitmap = bitmap;*/
        bitmap= mDecoder.decodeRegion(mRect, options);
        mMatrix.setScale(mCurrentScale,mCurrentScale);
        canvas.drawBitmap(bitmap,mMatrix,null);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mViewWidth= getMeasuredWidth();
        mViewHeight = getMeasuredHeight();


    }


}