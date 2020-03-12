package com.example.kevbilliards.model;

public class Pocket {
    private static int RADIUS = 60;
    private int posX;
    private int posY;

    public Pocket(int posX, int posY) {
        setX(posX);
        setY(posY);
    }

    public void setX(int x){
        this.posX=x;
    }
    public int getX(){
        return posX;
    }
    public void setY(int y){
        this.posY=y;
    }
    public int getY(){
        return posY;
    }

    public int getRadius(){
        return RADIUS;
    }

}
