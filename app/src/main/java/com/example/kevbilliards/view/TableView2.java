package com.example.kevbilliards.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.kevbilliards.model.Ball;
import com.example.kevbilliards.model.Table;


import java.util.Random;

import static android.os.SystemClock.uptimeMillis;

public class TableView2 extends SurfaceView  {

    SurfaceHolder ourHolder;

    Table table;
    Canvas canvas;
    Bitmap bg;
    long lastUpdated;

    public TableView2(Context context, Table inputTable) {
        super(context);
        ourHolder = getHolder();
        table=inputTable;
        setWillNotDraw(false);
        createBackground();
        lastUpdated= uptimeMillis();
    }

    private void createBackground(){
        bg= Bitmap.createBitmap(Resources.getSystem().getDisplayMetrics().widthPixels, Resources.getSystem().getDisplayMetrics().heightPixels, Bitmap.Config.RGB_565);
        Canvas c = new Canvas();
        c.setBitmap(bg);
        drawTable(c);
    }

    public void update(){
        if (uptimeMillis() -2 >lastUpdated){
            onDraw(canvas);
            System.out.println("TableView2: old lastupdated record=" +lastUpdated+"; timediff="+(uptimeMillis()-lastUpdated));
            lastUpdated=uptimeMillis();
        }
       /* else{
            System.out.println("too soon since last redraw");
        }   */
    }

    protected void onDraw(Canvas canvas){
        canvas = ourHolder.lockCanvas();
        Paint myPaint= new Paint();
       // drawTable(canvas,myPaint);
        canvas.drawBitmap(bg,0,0,null);

        // canvas.drawBitmap(BitmapFactory.decodeResource(getRootView().getResources(), R.drawable.brownball3),table.getPocket(0).getX(),table.getPocket(0).getY(),null);

        //drawBalls(canvas, myPaint);
        drawBallsBmp(canvas,table.getballs());

        if(table.getTouchUpX()!=0) {
            drawCue(canvas, myPaint);
        }

            ourHolder.unlockCanvasAndPost(canvas);
    }

    public void drawTable(Canvas canvas){
        Paint myPaint = new Paint();
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

    public void drawBallsBmp(Canvas canvas,Ball[] balls){
        for (int i=0;i<balls.length;i++){
            if(balls[i].getPocketed()==null) {
                canvas.drawBitmap(balls[i].getBmp(), balls[i].getPosX() - balls[i].getRadius(), balls[i].getPosY() - balls[i].getRadius(), null);
            }
        }
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
