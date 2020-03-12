package com.example.kevbilliards.view;

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

import java.util.Random;

public class TestSurfaceView extends SurfaceView implements Runnable {

        Thread ourThread = null;
        SurfaceHolder ourHolder;
        boolean isRunning = false;

        public TestSurfaceView(Context context) {
            // TODO Auto-generated constructor stub
            super(context);
            ourHolder = getHolder();
        }

        public void pause() {
            isRunning = false;
            while (true) {
                try {
                    ourThread.join();
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
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
                } // continue: skips code and jumps to end of loop/brackets

                try {
                    Thread.sleep(500);                       //used to be 20
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                Canvas canvas = ourHolder.lockCanvas(); // lock so that other
                                                // classes cant edit canvas
                    canvas.drawRGB(2, 150, 150);
                    Bitmap bb = BitmapFactory.decodeResource(getResources(), R.drawable.blueball3);
                    int width = ourHolder.getSurfaceFrame().width();
                    int height = ourHolder.getSurfaceFrame().height();
                    Random r= new Random();
                    canvas.drawBitmap(bb, width/2,r.nextInt(height),null);


                ourHolder.unlockCanvasAndPost(canvas); // unlock canvas, and
                // display onscreen

            }

        }

    }


