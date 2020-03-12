package com.example.kevbilliards.model;

import android.content.res.Resources;
import android.graphics.BitmapFactory;

import com.example.kevbilliards.R;
import com.example.kevbilliards.rulesets.BallPotRuleset;
import com.example.kevbilliards.rulesets.WallCheck;

public class Table {

    int border=100;         //spacing between screen edge and "table edge"
    int tableWidth;
    int tableLength;            //probably dont need these
    Pocket[] pockets;
    Ball[] balls;
    public Cue cue;
    public Resources res;

    private int touchDownX,touchDownY, touchUpX, touchUpY=0;         //for onTouch coordinates
    public int getTouchDownX(){return touchDownX;}
    public void setTouchDownX(int value){ touchDownX=value; }
    public int getTouchDownY(){return touchDownY;}
    public void setTouchDownY(int value){ touchDownY=value; }
    public int getTouchUpX(){return touchUpX;}
    public void setTouchUpX(int value){ touchUpX=value; }
    public int getTouchUpY(){return touchUpY;}
    public void setTouchUpY(int value){ touchUpY=value; }

    WallCheck wallCheck;

    public Table (int screenWidth, int screenLength, Resources r, BallPotRuleset bpc){
        res = r;

        this.tableWidth=screenWidth-border-border;
        this.tableLength=screenLength-border-border;

     cue = null;

     pockets = new Pocket[6] ;
        pockets[0] = new Pocket(border,border);
        pockets[1] =new Pocket(border+tableWidth,border);
        pockets[2] =new Pocket(border,screenLength/2);
        pockets[3] =new Pocket(border+tableWidth,screenLength/2);
        pockets[4] =new Pocket(border,border+tableLength);
        pockets[5] =new Pocket(border+tableWidth,border+tableLength);

        bpc = new BallPotRuleset();
        wallCheck= new WallCheck(getTableLength(),tableWidth,border,pockets,bpc);

        balls= new Ball[4];
        balls[0] = new Ball(0,BitmapFactory.decodeResource(r,R.drawable.white50), screenWidth/2, screenLength*2/3);
        balls[1] = new Ball(1,BitmapFactory.decodeResource(r,R.drawable.red50),screenWidth*4/10, screenLength/3);
        balls[2] = new Ball(8,BitmapFactory.decodeResource(r,R.drawable.yellow50), screenWidth*6/10, screenLength/3);
        balls[3] = new Ball(14,BitmapFactory.decodeResource(r,R.drawable.black50), screenWidth/2, screenLength/4);
    }

    public int getBorder(){
        return border;
    }

    public int getTableWidth(){
        return tableWidth;
    }
    public int getTableLength(){
        return tableLength;
    }

    public Pocket getPocket(int index){
        return pockets[index];
    }
    public Ball getBall(int index){
        return balls[index];
    }
    public Ball[] getballs(){ return balls;}

    public boolean cueStrike(double x1, double y1, double x2, double y2){
        cue = new Cue((int)x1,(int)y1,(int)x2,(int)y2);
        return cue.cueStrike(x1,y1,x2,y2);
    }
    public boolean cueStrike(){
        cue = new Cue(touchDownX,touchDownY,touchUpX,touchUpY);
        boolean b= cue.cueStrike((double)touchDownX,(double)touchDownY,(double)touchUpX,(double)touchUpY);
        touchDownX=touchDownY=touchUpX=touchUpY=0;
        return b;
    }

    public void moveBall (Ball ball, int timeslice){
        ball.moveBall(timeslice);
        wallCheck.wallCheck(ball);
        ball.applyFriction(timeslice);
    }

}
