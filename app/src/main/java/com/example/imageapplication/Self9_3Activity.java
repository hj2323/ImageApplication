package com.example.imageapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.EmbossMaskFilter;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class Self9_3Activity extends AppCompatActivity {
    ImageButton ibZoomin, ibZoomout, ibRotate, ibBright, ibDark, ibBlur, ibEmbos;
    MyGraphicView2 graphicView;
    static float scaleX=1, scaleY=1; //축적으로 사용될 전역변수2개를 선언한다
    static float angle = 0; //전역변수를 선언한다.
    static float color = 1; //색상 배수로 사용될 전역변수를 선언한다.
    static float blur = 1;
    static float embos = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self9_3);
        setTitle("미니 포토샵(수정)");

        LinearLayout pictureLayout = (LinearLayout) findViewById(R.id.pictureLayout);
        graphicView = (MyGraphicView2) new MyGraphicView2(this);
        pictureLayout.addView(graphicView);
        //layout의 pictureLayout을 인플레이트한 후 MyGraphicView형 클래스 변수를 첨부한다.
        //결국 아래쪽 레이아웃에는 MyGraphicView에서 설정한 내용이 출력된다.

        clickIcons();
    }

    private void clickIcons(){
        ibZoomin = (ImageButton) findViewById(R.id.ibZoomin);
        ibZoomin.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                scaleX = scaleX + 0.2f;
                scaleY = scaleY + 0.2f;
                //확대를 위해서는 onDraw()메소드를 다시 호출해야 하는데
                //뷰의 invalidate()메소드는 onDraw()를 자동으로 호출해준다.
                graphicView.invalidate();
            }
        });
        ibZoomout = (ImageButton) findViewById(R.id.ibZoomout);
        ibZoomout.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                scaleX = scaleX - 0.2f;
                scaleY = scaleY - 0.2f;
                graphicView.invalidate();
            }
        });
        ibRotate = (ImageButton) findViewById(R.id.ibRotate);
        ibRotate.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                angle = angle +20;
                graphicView.invalidate();
            }
        });
        ibBright = (ImageButton) findViewById(R.id.ibBright);
        ibBright.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                color = color +0.2f;
                graphicView.invalidate();
            }
        });
        ibDark = (ImageButton) findViewById(R.id.ibDark);
        ibDark.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                color = color -0.2f;
                graphicView.invalidate();
            }
        });
        ibBlur = (ImageButton) findViewById(R.id.ibBlur);
        ibBlur.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(blur == 0)blur=1;
                else blur=0;
                graphicView.invalidate();
            }
        });
        ibEmbos = (ImageButton) findViewById(R.id.ibEmbos);
        ibEmbos.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(embos == 0)embos=1;
                else embos=0;
                graphicView.invalidate();
            }
        });

    }

    private static class MyGraphicView2 extends View {
        public MyGraphicView2(Context context) {
            super(context);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            int cenX = this.getWidth()/2;
            int cenY = this.getHeight()/2;
            //get center of screen(view) and set scale of canvas with value on static variable
            canvas.scale(scaleX, scaleY, cenX, cenY);
            canvas.rotate(angle, cenX, cenY);

            Paint paint = new Paint();
            float[] array = { color ,0   ,0     ,0  ,0  ,
                    0   ,color,0    ,0  ,0  ,
                    0   ,0     ,color,0 ,0  ,
                    0   ,0     ,0   ,1  ,0 };
            ColorMatrix cm = new ColorMatrix(array);
            paint.setColorFilter(new ColorMatrixColorFilter(cm));

            if(blur == 0) {
                BlurMaskFilter bMask;
                bMask = new BlurMaskFilter(30, BlurMaskFilter.Blur.NORMAL);
                paint.setMaskFilter(bMask);
            }

            if(embos == 0){
                EmbossMaskFilter eMask;
                eMask = new EmbossMaskFilter(new float[] {10,3,3}, 0.5f, 5 , 10);
            }

            Bitmap picture = BitmapFactory.decodeResource(getResources(), R.drawable.renoir07);
            //그림의 시작위치
            int picX = (this.getWidth()-picture.getWidth())/2;
            int picY = (this.getHeight()-picture.getHeight())/2;
            canvas.drawBitmap(picture, picX, picY, paint);
            picture.recycle();


        }
    }
}