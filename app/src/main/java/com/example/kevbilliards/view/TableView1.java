package com.example.kevbilliards.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.kevbilliards.R;
import com.example.kevbilliards.model.Table;

import java.util.Random;

public class TableView1 extends SurfaceView  {

        SurfaceHolder ourHolder;

        Table table;
        int timedelay;
    Canvas canvas;

        public TableView1(Context context, Table inputTable, int timedelay) {
            // TODO Auto-generated constructor stub
            super(context);
            ourHolder = getHolder();
            table=inputTable;
            this.timedelay=timedelay;
            //System.out.println("from tableView, table border= "+table.getBorder());
            setWillNotDraw(false);
            //ourHolder.unlockCanvasAndPost(canvas);
        }

        public void update(){
            onDraw(canvas);
        }

        protected void onDraw(Canvas canvas){
            canvas = ourHolder.lockCanvas();
            System.out.println("ondraw called");
            Paint myPaint= new Paint();
            drawTable(canvas,myPaint);

            // canvas.drawBitmap(BitmapFactory.decodeResource(getRootView().getResources(), R.drawable.brownball3),table.getPocket(0).getX(),table.getPocket(0).getY(),null);

            drawBalls(canvas, myPaint);

            if(table.getTouchUpX()!=0) {
                drawCue(canvas, myPaint);
            }
            ourHolder.unlockCanvasAndPost(canvas);
        }

        public void drawTable(Canvas canvas, Paint myPaint){
            canvas.drawRGB(150,150,50);
            myPaint.setColor(Color.GREEN);
            canvas.drawRect((float)table.getBorder(),(float)table.getBorder(),(float)table.getTableWidth()+table.getBorder(),
                    (float)table.getTableLength()+table.getBorder(), myPaint);

            myPaint.setColor(Color.BLACK);
            for(int i=0; i<6;i++) {
                canvas.drawCircle(table.getPocket(i).getX(), table.getPocket(i).getY(), table.getPocket(i).getRadius(), myPaint);
            }
        }

        public void drawBalls(Canvas canvas, Paint myPaint){
            myPaint.setColor(Color.WHITE);
            canvas.drawCircle(table.getBall(0).getPosX(), table.getBall(0).getPosY(), table.getBall(0).getRadius(),myPaint);
            myPaint.setColor(Color.RED);
            canvas.drawCircle(table.getBall(1).getPosX(), table.getBall(1).getPosY(), table.getBall(1).getRadius(),myPaint);
            myPaint.setColor(Color.YELLOW);
            canvas.drawCircle(table.getBall(2).getPosX(), table.getBall(2).getPosY(), table.getBall(2).getRadius(),myPaint);
        }

        public void drawCue(Canvas canvas, Paint myPaint){
            myPaint.setColor(Color.RED);
            myPaint.setStrokeWidth(10);
           // canvas.drawLine(table.getTouchDownX(), table.getTouchDownY(), table.getTouchUpX(), table.getTouchUpY(), myPaint);
            canvas.drawLine(table.getBall(0).getPosX(), table.getBall(0).getPosY(),
                    table.getBall(0).getPosX()+table.getTouchUpX()-table.getTouchDownX(), table.getBall(0).getPosY()+table.getTouchUpY()-table.getTouchDownY(), myPaint);
            myPaint.setARGB(90,10,10,10);
            canvas.drawCircle(table.getBall(0).getPosX(),table.getBall(0).getPosY(),300,myPaint);
            //   myPaint.setStrokeWidth(1);

        }
    }


