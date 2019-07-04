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
    // private int targetProgress = 88;
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

    // 初始化
    private void initialize() {

        // 底环画笔
        defaultPaint = new Paint();
        defaultPaint.setColor(Color.argb(0xEE, 0x8E, 0x8E, 0x8E));
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
        //boundsWidth = getWidth();
        //boundsHeigh = getHeight();
        boundsWidth = 412 * scale + 0.5f;
        boundsHeigh = 216 * scale + 0.5f;
        centerPoint.x = boundsWidth / 2;
        centerPoint.y = boundsHeigh / 2;

        radius = boundsHeigh * 1 / 3;
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

        // 卡路里量（红色）
        genPaint.setColor(Color.argb(0xEE, 0xFF, 0x35, 0x9A));
        RectF oval1 = new RectF(centerPoint.x - sroundRadius, centerPoint.y
                - sroundRadius, centerPoint.x + sroundRadius, centerPoint.y
                + sroundRadius);
        // 固定角度的弧
        // 应该改为弧长根据数值动态变化
        canvas.drawArc(oval1, -90, 300, false, genPaint);

        // 环中心数值文本（动态迭加的）
        int curValue = curProgress;
        progressTextPaint.setTextSize(60);
        float ww = progressTextPaint.measureText(curValue + " CAL");
        canvas.drawText(curValue + " CAL", centerPoint.x - ww / 2,
                centerPoint.y, progressTextPaint);

        /*
        // 评级提示
        progressTextPaint.setTextSize(25);
        float w = 0;
        String text = "";
        if (curValue == 0) {
            text = "没有记录";
            w = progressTextPaint.measureText(text);
            complete = false;
        } else if (curValue < targetProgress) {
            // 这里不知道写什么比较好
            text = "...";
            w = progressTextPaint.measureText(text);
        } else if (curValue >= targetProgress) {
            text = "热量摄入有点多啦";
            w = progressTextPaint.measureText(text);
            complete = true;
            postInvalidate();
        }
        canvas.drawText(text, centerPoint.x - w / 2, centerPoint.y + 40,
                progressTextPaint);
         */

    }

    /*
    //启动进度动画

    public void start() {
        curProgress = 0;
        if (targetProgress == 0) {
            targetProgress = 88;
        }
        final Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                curProgress++;
                if (curProgress == targetProgress) {
                    timer.cancel();
                }
                postInvalidate();
            }
        };
        timer.schedule(timerTask, 0, 20);
    }

    // 点击评分区域，进行评分
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float x = event.getX();
        float y = event.getY();

        if (x > centerPoint.x - radius && x < centerPoint.x + radius
                && y > centerPoint.y - radius && y < centerPoint.y + radius) {
            Log.i(TAG, ">>>");
            start();
        }
        return super.onTouchEvent(event);
    }
    */

}