package com.gangzi.onedaybest.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by dan on 2017/8/23.
 */

public class MyCustomerView extends View{

    private Paint mPaint;
    private int width,height;

    public MyCustomerView(Context context) {
        super(context);
    }

    public MyCustomerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyCustomerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //width=getMeasuredWidth();
        //height=getMeasuredHeight();
       // width=MeasureSpec.getSize(widthMeasureSpec);
        //height=MeasureSpec.getSize(heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width=w;
        height=h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint=new Paint();
        canvas.translate(width/2,height/2);
        RectF rect = new RectF(-100,-100,100,100);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.RED);
        canvas.drawRect(rect,mPaint);
        for (int i=0;i<10;i++){
            canvas.scale(0.9f,0.9f);
            //mPaint.setColor(Color.GREEN);
            canvas.drawRect(rect,mPaint);
        }
    }
}
