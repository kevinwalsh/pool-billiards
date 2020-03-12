package com.example.kevbilliards.rulesets;

import com.example.kevbilliards.model.Ball;
import com.example.kevbilliards.model.Table;

import static android.os.SystemClock.uptimeMillis;

public class MovementRuleset {



    private boolean isRunning=false;
    Table table;
    long lastUpdated;
    CollisionRuleset CollisionRuleset;

    public MovementRuleset(Table table){
        this.table=table;
        lastUpdated= uptimeMillis();
        CollisionRuleset = new CollisionRuleset();
    }

    public void update(){
        int timeslice =(int) (uptimeMillis()-lastUpdated);          //lastupdated changed both here, AND at starting ballHit()
        moveBalls(table.getballs(),timeslice);
        lastUpdated=uptimeMillis();

        CollisionRuleset.checkAll(table.getballs());
    }

    public boolean isMoving(Ball[] balls){
        boolean movement=false;
        for (int i=0;i<balls.length;i++) {
            if(balls[i].getSpeed()>0){
                movement=true;
            }
        }
        return movement;
    }

    private void moveBalls(Ball[] balls,int timeslice){

            for (int i=0;i<balls.length;i++) {
            if(balls[i].getSpeed()>0){
                table.moveBall(balls[i], timeslice);
            }
        }
    }

    public void startBallHit(Ball ball,double speed, double angle){
        ball.setSpeed(speed);
        ball.setAngle(angle);

        lastUpdated=uptimeMillis();
    }





}
