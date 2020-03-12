package com.example.kevbilliards.model;

import android.graphics.Bitmap;

public class Ball {
    int id;
    public Bitmap ballbmp;
    private static int RADIUS=50;
    private int posX;
    private int posY;
    private double speed;
    private double angle;
    private Pocket pocketed;

    public Ball(int id, Bitmap d, int posX, int posY) {
        setID(id);
        setColor(d);
        setPosition(posX,posY);
        setSpeed(0);
        setPocketed(null);
    }

    public int getID(){return id;}
    public void setID(int id){this.id=id;}

    public void setColor(Bitmap b){
        ballbmp=b;
    }

    public Bitmap getBmp(){
        return this.ballbmp;
    }

    public void setPosition(int x, int y){
        this.posX=x;
        this.posY=y;
    }

    public Pocket getPocketed(){return pocketed;}
    public void setPocketed(Pocket pocket){this. pocketed=pocket;}

    public int getPosX(){
        return this.posX;
    }
    public int getPosY(){
        return this.posY;
    }
    public int getRadius(){
        return RADIUS;
    }

    public void setSpeed(double speed){
        this.speed=speed;
    }
    public void setAngle (double a){
        while(a<-Math.PI || a>Math.PI){
            if (a<-Math.PI) {
                a += 2 * Math.PI;
            }
            else if(a>Math.PI){
                a-=2*Math.PI;
            }
        }
        if (a>=-Math.PI && a<Math.PI)  {             //better to use RADIANS
              this.angle=a;
        }
    }
    public double getSpeed (){return this.speed;}
    public double getAngle() {return this.angle;}


    public void moveBall(int timeslice){
        setPosition(getPosX()-(int)(getSpeed()*Math.sin(getAngle())*timeslice/1000), getPosY()-(int)(getSpeed()*Math.cos(getAngle())*timeslice/1000));
    }
    public void applyFriction(int timeslice){
        setSpeed(getSpeed()-4);         //currently doesnt use timeslice
    }
}
