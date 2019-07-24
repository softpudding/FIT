package com.example.fitmvp.view.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.net.Uri;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.fitmvp.R;

public class ShareView extends FrameLayout {
    private final int IMAGE_WIDTH = 720;
    private final int IMAGE_HEIGHT = 1280;
    private TextView tvInfo;
    private Uri uriview;
    public ShareView(@NonNull Context context) {
        super(context);
        init();
    }

    public Uri getUriview() {
        return uriview;
    }
    public void setUriview(Uri uriview) {
        this.uriview = uriview;
    }

    private void init() {
        View layout = View.inflate(getContext(), R.layout.photo_show, this);
        tvInfo = (TextView) layout.findViewById(R.id.show1_name);
    }

    /**
     * 设置相关信息
     * @param info
     */
    public void setInfo(String info) {
        tvInfo.setText(info);
    }
        /**
         * 生成图片
         */
        public Bitmap createImage(Bitmap bitmap,String string){
            //由于直接new出来的view是不会走测量、布局、绘制的方法的，所以需要我们手动去调这些方法，不然生成的图片就是黑色的。
            int widthMeasureSpec = MeasureSpec.makeMeasureSpec(IMAGE_WIDTH, MeasureSpec.EXACTLY);
            int heightMeasureSpec = MeasureSpec.makeMeasureSpec(IMAGE_HEIGHT, MeasureSpec.EXACTLY);
//
//            measure(widthMeasureSpec, heightMeasureSpec);
//            layout(0, 0, IMAGE_WIDTH, IMAGE_HEIGHT);
//            Bitmap bitmap = Bitmap.createBitmap(IMAGE_WIDTH, IMAGE_HEIGHT, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            textPaint.setColor(Color.BLACK);
            textPaint.setStyle(Paint.Style.FILL);
            textPaint.setAntiAlias(true);
            textPaint.setTextAlign(Paint.Align.CENTER);
//            canvas.drawText("店铺名称",'2.4',"20",textPaint);
//            Bitmap qrBitmap =QRCodeUtil.createQRCodeBitmapMargin(s, qrCodeSize, 0);
//            Rect mSrcRect = new Rect(left, imgMarginTop, left + qrCodeWidth, bottom);
//            canvas.drawBitmap(qrBitmap, null, mSrcRect, new Paint(Paint.ANTI_ALIAS_FLAG));
            draw(canvas);

            return bitmap;
        }
    }

