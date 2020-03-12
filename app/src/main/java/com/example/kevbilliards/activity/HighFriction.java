package com.example.kevbilliards.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;

import com.example.kevbilliards.R;

public class HighFriction extends Activity implements OnTouchListener {

    MyGFXVariableSurface ourSurfaceView;
    float x, y, x0, y0, x1, y1;
    float dx, dy, scaledX, scaledY, animX, animY;
    Bitmap gball2, rball2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ourSurfaceView = new MyGFXVariableSurface(this);
        x =y =x0=y0 =x1= y1 = 0;
        dx=dy= scaledX= scaledY= animX = animY= 0;

        gball2 = BitmapFactory.decodeResource(getResources(), R.drawable.greenball75);
        rball2 = BitmapFactory.decodeResource(getResources(), R.drawable.redball);
        ourSurfaceView.setOnTouchListener(this);
        setContentView(ourSurfaceView);
    }

    @Override
    protected void onPause() {
        super.onPause();
        ourSurfaceView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ourSurfaceView.resume();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        x = event.getX();
        y = event.getY();
        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN :
                x0=x;y0=y;
                x1=y1= dx= dy = animX = animY = scaledX=scaledY =0;
                break;
            case MotionEvent.ACTION_UP :
                x1=x;y1=y;
                dx=x1-x0; dy=y1-y0;
                scaledX=dx;scaledY=dy;
                x=y=0;
                break;
        }
        return true;        //to allow "drag" touch action
    }

    public class MyGFXVariableSurface extends SurfaceView implements Runnable {

        Thread ourThread = null;
        SurfaceHolder ourHolder;
        boolean isRunning = false;

        public MyGFXVariableSurface(Context context) {
            super(context);
            ourHolder = getHolder();
        }

        public void pause() {
            isRunning = false;
            while (true) {
                try {
                    ourThread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            }
            ourThread = null;
        }

        public void resume() {
            isRunning = true;
            ourThread = new Thread(this);
            ourThread.start();
        }

        @Override
        public void run() {
            while (isRunning) {
                if (!ourHolder.getSurface().isValid()) {
                    continue;
                }

                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                checkwalls();
                Canvas canvas = ourHolder.lockCanvas(); // lock so that other
               canvas.drawRGB(2, 150, 150);
                if(x!=0 && y!=0){
                    canvas.drawBitmap(rball2, x-rball2.getWidth()/2, y-rball2.getHeight()/2, null);
                }
                if(x0!=0 && y0!=0){
                    canvas.drawBitmap(rball2, x0-rball2.getWidth()/2, y0-rball2.getHeight()/2, null);
                }
                if(x1!=0 && y1!=0){
                    canvas.drawBitmap(rball2, x1-rball2.getWidth()/2 -animX, y1-rball2.getHeight()/2 -animY, null);
                    canvas.drawBitmap(gball2, x1-gball2.getWidth()/2, y1-gball2.getHeight()/2, null);
                }
                animX= animX+scaledX/30; animY = animY +scaledY/30;
                if(scaledX>0){scaledX--;}else if(scaledX<0){scaledX++;}
                if(scaledY>0){scaledY--;} else if(scaledY<0){scaledY++;}
                // using "++" has weird effects cos one axis will reach 0 before other;
                // therefore ball will inexplicably change direction
                scaledX= (float) (scaledX*0.99); scaledY= (float) (scaledY*0.99);
                //  it is still happening with this, but less pronounced.
                // ultimately will have to use magnitude & direction!!!

                ourHolder.unlockCanvasAndPost(canvas); // unlock canvas & display onscreen
            }
        }

       public void checkwalls(){
            if ((x1-animX)<10 || (x1-animX)>ourHolder.getSurfaceFrame().width()) {scaledX=-scaledX;}
            if ((y1-animY)<10 || (y1-animY)>ourHolder.getSurfaceFrame().height()){scaledY=-scaledY;}
            //	if(x<10) {animX=-animX;}	//still broken!!
        }


    }
}

