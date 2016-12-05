package com.tenny.mystory;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.support.annotation.IntDef;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class TextViewVertical extends View {

    public static final int START_LEFT = 1;
    public static final int START_RIGHT = 2;

    @IntDef({START_LEFT, START_RIGHT})
    @Retention(RetentionPolicy.SOURCE)
    public @interface START_ORIENTATION {
    }

    private float textSize = 56;
    private int textColor = Color.BLACK;
    private String text = "";
    private int start = START_LEFT;

    Paint paint;
    int width;
    int height = -1;

    public TextViewVertical(Context context) {
        super(context);
        init();
    }

    public TextViewVertical(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TextViewVertical);
        int count = typedArray.getIndexCount();
        for (int i = 0; i < count; i++) {
            int index = typedArray.getIndex(i);
            if (index == R.styleable.TextViewVertical_v_start) {
                start = typedArray.getInt(index, START_LEFT);
            } else if (index == R.styleable.TextViewVertical_v_text) {
                text = typedArray.getString(index);
            } else if (index == R.styleable.TextViewVertical_v_textColor) {
                textColor = typedArray.getColor(index, Color.BLACK);
            } else if (index == R.styleable.TextViewVertical_v_textSize) {
                textSize = typedArray.getDimension(index, 16);
            }
        }
        init();
    }

    private void init() {
        paint = new Paint();
        if (textSize > 0) {
            paint.setTextSize(textSize);
        }
        paint.setColor(textColor);
        paint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int h = measureHeight(heightMeasureSpec);
        if (height == -1) {
            height = h;
        } else {
            if (height > h) {
                height = h;
            }
        }
        width = measureWidth(widthMeasureSpec);
        setMeasuredDimension(width, height);
    }

    public void setTypeface(Typeface typeface) {
        paint.setTypeface(typeface);
        invalidate();
    }

    /**
     * 设置文字尺寸
     *
     * @param textSize
     */
    public void setTextSize(float textSize) {
        this.textSize = textSize;
        invalidate();
    }

    /**
     * 设置文字颜色
     *
     * @param textColor
     */
    public void setTextColor(int textColor) {
        this.textColor = textColor;
        invalidate();
    }

    /**
     * 设置文字
     *
     * @param text
     */
    public void setText(String text) {
        this.text = text;
        invalidate();
    }

    /**
     * 设置文字起始方向
     *
     * @param start
     */
    public void setStart(@START_ORIENTATION int start) {
        this.start = start;
        invalidate();
    }


    private int measureWidth(int measureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {

            result = specSize;
        } else {
            return (int) measureTextWidth();
        }
        return result;
    }

    private int measureHeight(int measureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = (int) (getOneWordHeight() * text.length());
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    private float measureTextWidth() {
        if (getColNum() == 1) {
            return getOneWordWidth() + getPaddingLeft() + getPaddingRight();
        }

        return getOneWordWidth() * getColNum() + getPaddingLeft() + getPaddingRight();
    }

    private float getTextBaseLine(RectF rect) {
        Paint.FontMetricsInt metricsInt = paint.getFontMetricsInt();
        return (rect.top + rect.bottom - metricsInt.top - metricsInt.bottom) / 2;
    }


    private int getColNum() {


        int oneRowWordCount = getColWordCount();

        int colNum = text.length() / oneRowWordCount;
        if (text.length() % oneRowWordCount > 0) {
            colNum++;
        }
        return colNum;
    }

    private float getOneWordWidth() {
        return paint.measureText("我") + dip2px(getContext(), 3);
    }

    private float getOneWordHeight() {
        Paint.FontMetricsInt metricsInt = paint.getFontMetricsInt();
        return (metricsInt.bottom - metricsInt.top);
    }

    private int getColWordCount() {

        int oneLineWordCount = (int) (height / getOneWordHeight());
        return oneLineWordCount;
    }

    public int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int oneLineWordCount = getColWordCount();
        int currentCol = 0;
        float w = getOneWordWidth();
        float h = getOneWordHeight();

        int colNum = getColNum();
        for (int i = 0; i < text.length(); i++) {
            int j = i % oneLineWordCount;
            if (colNum == 1) {
                j = i;
            }
            RectF rectF;
            if (start == START_LEFT) {
                rectF = new RectF(currentCol * w, j * h, currentCol * w + w, j * h + h);
            } else {
                rectF = new RectF((width - (currentCol + 1) * w), j * h, (width - (currentCol + 1) * w) + w, j * h + h);
            }
            float baseline = getTextBaseLine(rectF);
            canvas.drawText(String.valueOf(text.charAt(i)), rectF.centerX(), baseline, paint);
            if (colNum > 1) {
                currentCol = (i + 1) / oneLineWordCount;
            }
        }
    }
}