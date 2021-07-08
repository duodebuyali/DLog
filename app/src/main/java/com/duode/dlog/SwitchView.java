package com.duode.dlog;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

/**
 * @author hekang
 * @des
 * @date 2021/4/26 17:57
 */

public class SwitchView extends View {
    public SwitchView(Context context) {
        super(context);
        init(context, null, 0, 0);
    }

    public SwitchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public SwitchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SwitchView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    private int mOffColor = Color.parseColor("#efefef");
    private int mOnColor = Color.parseColor("#6f60dd");


    private int mFillerSolidColor = Color.WHITE;
    private int mFillerStrokeColor = Color.GRAY;

    private int mFillerStrokeWidth = (int) dp2px(1f);

    //圆球的半径
    private int mFillerRadius = (int) dp2px(12f);
    //圆球静止时相对于左右的间隔
    private int mFillerPadding = (int) dp2px(2f);

    @SuppressLint("ResourceType")
    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
       /* if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs,
                    R.styleable.SwitchView, defStyleAttr, defStyleRes);

            mOffColor = ta.getColor(R.styleable.SwitchView_off_color, mOffColor);
            mOnColor = ta.getColor(R.styleable.SwitchView_on_color, mOnColor);
            mFillerRadius = ta.getInt(R.styleable.SwitchView_filler_radius, mFillerRadius);
            mFillerPadding = ta.getInt(R.styleable.SwitchView_filler_padding, mFillerPadding);

            mFillerSolidColor = ta.getColor(R.styleable.SwitchView_filler_solid_color, mFillerSolidColor);
            mFillerStrokeColor = ta.getColor(R.styleable.SwitchView_filler_stroke_color, mFillerStrokeColor);
            mFillerStrokeWidth = ta.getInt(R.styleable.SwitchView_filler_stroke_width, mFillerStrokeWidth);
            mFillerPadding = ta.getInt(R.styleable.SwitchView_filler_padding, mFillerPadding);
            mFillerRadius = ta.getInt(R.styleable.SwitchView_filler_radius, mFillerRadius);

            ta.recycle();
        }
*/
        mBgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBgPaint.setStyle(Paint.Style.FILL);

        //先绘制边框
        mFillerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mFillerPaint.setStyle(Paint.Style.STROKE);
        mFillerPaint.setColor(mFillerStrokeColor);
        mFillerPaint.setStrokeWidth(mFillerStrokeWidth);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            setLayerType(LAYER_TYPE_SOFTWARE, null);
        }
    }

    private Paint mBgPaint;
    private Paint mFillerPaint;

    private RectF mRectF;

    private int mWidth;
    private int mHeight;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);
    }

    private float mRoundRadius;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;

        if (mRectF == null) {
            mRectF = new RectF(getLeft(), getTop(), getLeft() + w, getTop() + h);
        } else {
            mRectF.set(getLeft(), getTop(), getLeft() + w, getTop() + h);
        }
        //绘制的是左右圆形，中间矩形，圆角是高度的一半
        mRoundRadius = mHeight * .5f;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (canvas == null) {
            return;
        }
        Log.e("SwitchView", "onDraw");
        drawBg(canvas);
//        drawFiller(canvas);

    }

    private void drawBg(Canvas canvas) {
        //这里的颜色值，需要根据当前的位置计算
//        mBgPaint.setColor(mOnColor);
//        canvas.drawRoundRect(mRectF, mRoundRadius, mRoundRadius, mBgPaint);
        mBgPaint.setColor(Color.RED);
        Log.e("SwitchView", "drawBg--mRectF:" + mRectF);
        canvas.drawRect(mRectF, mBgPaint);
    }

    /**
     * 横向滑动的距离
     */
    private int mDx;

    private void drawFiller(Canvas canvas) {
        float x = getLeft() + mFillerPadding + mFillerRadius + mDx;
        //先绘制边框
        canvas.drawCircle(x, getTop() + mRoundRadius, mFillerRadius, mFillerPaint);

        //绘制内容
        mFillerPaint.setStyle(Paint.Style.FILL);
        mFillerPaint.setColor(mFillerSolidColor);
        x -= mFillerStrokeWidth;
        canvas.drawCircle(x, getTop() + mRoundRadius, mFillerRadius - mFillerStrokeWidth, mFillerPaint);
    }

    private static float dp2px(float dp) {
        Resources r = Resources.getSystem();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
    }
}
