package com.example.kevbilliards.model;

public class Cue {
    //public Bitmap ballbmp;          //leave out for now

    private boolean gotValues=false;
    public void setReadable(boolean b){gotValues=b;}
    public boolean readable(){return gotValues;}

    private int x0, y0,x1,y1;
    public int getX0(){return x0;}
    public void setX0(int x) {this.x0=x;}
    public int getY0(){return y0;}
    public void setY0(int y) {this.y0=y;}
    public int getX1(){return x1;}
    public void setX1(int x) {this.x1=x;}
    public int getY1(){return y1;}
    public void setY1(int y) {this.y1=y;}

    private static final int MIN_POWER = 300;      // will likely be need to be set according to screensize/ pixel density!

    private double angle;
    public void setAngle (double a){
        //if (a>=-180 && a<180)  {
        while(a<-Math.PI || a>Math.PI){
            if (a<-Math.PI) {
                a += 2 * Math.PI;
            }
            else if(a>Math.PI){
                a-=2*Math.PI;
            }
        }
        if (a>=-Math.PI && a<Math.PI)  {
                this.angle=a;
            //this.angle=Math.toRadians(a);
        }
    }

    public double getAngle(){return angle;}
    //              or other idea, maybe have cue only temporary? and just set 4x coordinates to calc power + angle?
    private double power;
    public double getPower(){return power;}
    public boolean cueStrike(double x1, double y1, double x2, double y2){
        double length = Math.sqrt( ( ( x2 - x1 ) * ( x2 - x1 ) ) + ( ( y2 - y1 ) * ( y2 - y1 ) ) );
        if(length > MIN_POWER){
            power=length;
           setAngle((Math.atan2(x2-x1,y2-y1)));         //edit: better to have it in radians anyway!
            return true;
        }
        else{return false;}
    }

    public Cue(int x0, int y0, int x1, int y1){
        setAngle(Math.toDegrees(Math.atan2(y1-y0,x1-x0)));
        double length = Math.sqrt(((x1 - x0)*(x1-x0))+((y1 - y0)*(y1-y0)));
        if (length>MIN_POWER){
            power=length;            // TODO: fix cue strike power.
                                        // very weak, I thought I fixed this.
                                        // from memory, it may vary wildly based on screen resolution
                                        // (since its just diff between co-ordinates)
                                        // SOLN: Could try (length/screenlength)*powerfactor.
                                        // will need to also adjust friction.
        }

    }

    public Cue() {
        setAngle(0);
        power=0;
    }

}
