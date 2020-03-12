package com.example.kevbilliards.activity;


import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.kevbilliards.rulesets.BallPotRuleset;
import com.example.kevbilliards.rulesets.MovementRuleset;
import com.example.kevbilliards.model.Cue;
import com.example.kevbilliards.model.Table;
import com.example.kevbilliards.view.TableView2;

public class PoolActivity extends Activity implements View.OnTouchListener {

    TableView2 ourSurfaceView;
    MovementRuleset movementRuleset;
    BallPotRuleset bpr;
    //GameRuleset gameRuleset;
    Table myTable;
    double x,y;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myTable= new Table (Resources.getSystem().getDisplayMetrics().widthPixels, Resources.getSystem().getDisplayMetrics().heightPixels, getResources(),bpr);

        ourSurfaceView = new TableView2(this,myTable);
        //gameRuleset = new GameRuleset(myTable.getballs());
        movementRuleset = new MovementRuleset(myTable);
        setContentView(ourSurfaceView);
        ourSurfaceView.setOnTouchListener(this);

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        ourSurfaceView.postInvalidate();

        x = event.getX();
        y = event.getY();
        myTable.setTouchUpX((int)x);myTable.setTouchUpY((int)y);
        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN :
                myTable.setTouchDownX((int)x);myTable.setTouchDownY((int)y);
                break;
            case MotionEvent.ACTION_UP :
               if(myTable.cueStrike()) {
                   sendToast(myTable.cue);
                   //TODO      send coords to the relevant table class, THEN reset variables

                  startMovement();
               }
                x=y=0;
                break;
        }

        return true;		//will make onTouch apparently loop, allowing "drag" updating!
    }

    public void startMovement(){
        movementRuleset.startBallHit(myTable.getBall(0),myTable.cue.getPower(),myTable.cue.getAngle());
       // movementRulset.startThread();

        while (movementRuleset.isMoving(myTable.getballs())) {
            movementRuleset.update();
            ourSurfaceView.update();
        }
        System.out.println("MovementRuleset STOPPED");
        myTable.getBall(0).setSpeed(0);
    }

    public void sendToast(Cue c){
        StringBuilder sb = new StringBuilder();
        sb.append("CUESTRIKE: power= "+c.getPower()+", degs (RADIANS!) ="+c.getAngle());
        Toast.makeText(this, sb.toString(),
                Toast.LENGTH_LONG).show();

    }
}