package com.example.kevbilliards.rulesets;

import com.example.kevbilliards.model.Ball;
import com.example.kevbilliards.model.Pocket;
import com.example.kevbilliards.model.Table;

public class WallCheck {

    private PocketCheck pocketCheck;
    private int tableLength,tableWidth,border;

    public WallCheck(int tableLength,int tableWidth, int border,Pocket[] pockets,BallPotRuleset bpc){
        pocketCheck = new PocketCheck(pockets,bpc);
        this.tableLength=tableLength;
        this.tableWidth=tableWidth;
        this.border=border;
    }

    public void wallCheck(Ball ball){
        Boolean hit=false;
        String wall="none";

                            // 26-9-17, thought of (unlikelyish) prob with this- if ball moves huge amount in single frame,
                            // could have been in line to hit TOP wall, and THEN LEFT wall after. yet, we default check left
                            // wall first and assume that only one wall will be hit per frame... should work, but bad algorithm.

        if (ball.getPosX()-ball.getRadius()<border){
            wall="LEFT";
            hit=true;
            ball.setAngle(-ball.getAngle());
            ball.setPosition(border+ball.getRadius(),ball.getPosY());
            System.out.println("Wallcheck HIT horiz LEFT");
        }
        else if(ball.getPosX()+ball.getRadius()>border +tableWidth){
            wall="RIGHT";
            hit=true;
            ball.setAngle(-ball.getAngle());
            ball.setPosition(tableWidth+border-ball.getRadius(),ball.getPosY());
            System.out.println("Wallcheck HIT horiz RIGHT");
        }
        else if (ball.getPosY()-ball.getRadius()<border){
            wall="TOP";
            hit=true;
            System.out.println("Wallcheck HIT vert TOP");
            ball.setPosition(ball.getPosX(),border+ball.getRadius());
            ball.setAngle(Math.PI -ball.getAngle());
        }
        else if (ball.getPosY()+ball.getRadius()>border +tableLength){
            wall="BOTTOM";
            hit=true;
            System.out.println("Wallcheck HIT vert BOTTOM");
            ball.setAngle(Math.PI -ball.getAngle());
            ball.setPosition(ball.getPosX(),tableLength+border-ball.getRadius());

        }

        if(hit==true){
            if(pocketCheck.pocketCheck(ball,wall)){
                //pocketCheck.ballPocketed(ball);
                                //nah, just put ballPocketed logic all inside pocketCheck.
                                // if pocketed, i must identify WHICH pocket (for black), check for fouls,
                                  // and tell main controller to remove this ball from play
            }

        }

/*
        if (ball.getPosX()-ball.getRadius()<border || ball.getPosX()+ball.getRadius()>border +tableWidth){
            if(pocketCheck.pocketCheck(ball.getPosX(),ball.getPosY())){
                // if true, then no need to do rest of wallCheck.
                // instead, do a ballPotCheck?
                ball.setSpeed(0);
                System.out.println("POCKETED");
            }
            else {
                ball.setAngle(-ball.getAngle());
                System.out.println("Wallcheck HIT horiz");
            }
        }

        else if(ball.getPosY()-ball.getRadius()<border || ball.getPosY()+ball.getRadius()>border +tableLength)  {
            if(pocketCheck.pocketCheck(ball.getPosX(),ball.getPosY())){
                // skip rest of wallCheck
                ball.setSpeed(0);
                System.out.println("POCKETED");
            }
            else{
                double tempAngle = Math.PI -ball.getAngle();
                System.out.println("Wallcheck HIT vert");
                ball.setAngle(tempAngle);
            }
        }
*/


    }


}
