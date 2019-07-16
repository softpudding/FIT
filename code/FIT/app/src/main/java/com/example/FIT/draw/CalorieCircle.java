package com.example.FIT.draw;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.FIT.R;

// 绘制首页热量记录的环形图
public class CalorieCircle extends View {
    private Context context;

    private static final String TAG = "CirStatisticGraph";

    // 中心点
    class CenterPoint {
        float x;
        float y;
    }

    // 宽度、高度、中心坐标、半径、画笔粗细参数
    private float boundsWidth;
    private float boundsHeigh;
    private CenterPoint centerPoint = new CenterPoint();
    private float radius;
    private float paintWidth;
    private float genPaintWidth;

    // 画笔
    private Paint defaultPaint;
    private Paint genPaint;
    private Paint progressTextPaint;

    // 进度
    private int curProgress;
    private int targetProgress;
    // private boolean complete;

    // 构造函数
    public CalorieCircle(Context context) {
        this(context, null);
    }

    public CalorieCircle(Context context,  @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public CalorieCircle(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.initialize();
    }

    public void setCurProgress(int value){
        curProgress = value;
    }

    public void setTargetProgress(int value){
        targetProgress = value;
    }

    // 初始化
    private void initialize() {

        // 底环画笔
        defaultPaint = new Paint();
        defaultPaint.setColor(getResources().getColor(R.color.circleBg));
        defaultPaint.setStyle(Paint.Style.STROKE);
        defaultPaint.setStrokeWidth(paintWidth);
        defaultPaint.setAntiAlias(true);

        // 比重环画笔
        genPaint = new Paint();
        genPaint.setStyle(Paint.Style.STROKE);
        genPaint.setStrokeWidth(genPaintWidth);
        genPaint.setAntiAlias(true);

        // 中心进度文本和评级画笔
        progressTextPaint = new Paint();
        progressTextPaint.setColor(Color.WHITE);
        progressTextPaint.setStyle(Paint.Style.STROKE);
        progressTextPaint.setStrokeWidth(0);
        progressTextPaint.setTypeface(Typeface.DEFAULT_BOLD);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // 绘制区域的宽高
        float scale = context.getResources().getDisplayMetrics().density;
        // boundsWidth = getWidth();
        // boundsHeigh = getHeight();
        boundsWidth = 386 * scale + 0.5f;
        boundsHeigh = 250 * scale + 0.5f;
        centerPoint.x = boundsWidth / 2;
        centerPoint.y = boundsHeigh / 2;

        radius = boundsHeigh * 1 / 3.5f;
        paintWidth = 50;
        genPaintWidth = paintWidth / 2;
        initialize();
    }



    @SuppressLint("DrawAllocation")
    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 底环（灰色）
        canvas.drawCircle(centerPoint.x, centerPoint.y, radius, defaultPaint);

        // 环的外层半径
        float sroundRadius = radius + paintWidth / 2 - genPaintWidth / 2;


        RectF oval = new RectF(centerPoint.x - sroundRadius, centerPoint.y
                - sroundRadius, centerPoint.x + sroundRadius, centerPoint.y
                + sroundRadius);
        // 卡路里量
        // 小于目标值时弧长根据目标值和当前值比例动态变化
        if (curProgress <= targetProgress) {
            genPaint.setColor(getResources().getColor(R.color.circleGreen));
            int sweepAngle = 360 * curProgress / targetProgress;
            canvas.drawArc(oval, 135, sweepAngle, false, genPaint);
        }
        // 大于目标值后更换弧线颜色为粉红色
        else {
            genPaint.setColor(getResources().getColor(R.color.circleRed));
            canvas.drawArc(oval, 135, 360, false, genPaint);
        }


        // 环中心数值文本（动态迭加的）
        int curValue = curProgress;
        progressTextPaint.setTextSize(60);
        float ww = progressTextPaint.measureText(curValue + " CAL");
        canvas.drawText(curValue + " CAL", centerPoint.x - ww / 2,
                centerPoint.y, progressTextPaint);
    }

}