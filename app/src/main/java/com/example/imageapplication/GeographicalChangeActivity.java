package com.example.imageapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;

public class GeographicalChangeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new GeographicalChangeActivity.MyGraphicView(this));
    }
    private static class MyGraphicView extends View {

        public MyGraphicView(Context context) {
            super(context);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            Bitmap picture = BitmapFactory.decodeResource(getResources(),
                    R.drawable.pic7);

            //그림의 중앙 because 그림의 중앙을 기준으로 돌기 떄문에
            int cenX = this.getWidth() / 2;
            int cenY = this.getHeight() / 2;
            //그림의 시작위치
            int picX = (this.getWidth()-picture.getWidth())/2;
            int picY = (this.getHeight()-picture.getHeight())/2;

            //canvas.rotate(45, cenX, cenY);
            //canvas.drawBitmap(picture, picX, picY, null);

           // canvas.translate(-150, 200);
           // canvas.drawBitmap(picture, picX, picY, null);

            //canvas.scale(2,2,cenX,cenY);
            //canvas.drawBitmap(picture, picX, picY, null);

           //canvas.skew(0.3f, 0.3f);
           //canvas.drawBitmap(picture, picX, picY, null);

            Paint paint = new Paint();
            paint.setColor(Color.GRAY);
            BlurMaskFilter bMask;//블러링
            EmbossMaskFilter eMask;//엠보싱

            bMask = new BlurMaskFilter(30, BlurMaskFilter.Blur.NORMAL);
            paint.setMaskFilter(bMask);
            canvas.drawBitmap(picture, picX, picY, paint);
            picture.recycle();

            eMask = new EmbossMaskFilter(new float[] {10,3,3}, 0.5f, 5, 10);
            paint.setMaskFilter(eMask);
            canvas.drawCircle(cenX, cenY, 150, paint);

        }
    }
}