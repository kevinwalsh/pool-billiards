package com.example.kevbilliards.rulesets;

import com.example.kevbilliards.model.Ball;
import com.example.kevbilliards.model.Pocket;
import com.example.kevbilliards.model.Table;


public class PocketCheck {

    private Pocket[] pockets;
    static int pocketRadius;
    BallPotRuleset BallPotRuleset;


    public PocketCheck(Pocket[] pockets,BallPotRuleset bpc){
        this.pockets=pockets;
        pocketRadius=pockets[0].getRadius();
        this.BallPotRuleset=bpc;
    }

    public boolean pocketCheck(Ball ball, String wall){

        //       if i use "wall" string, i can check only relevant pockets, so its quicker

        Boolean b=false;
        switch(wall){
            case "LEFT": if(singlePocketCheck(pockets[0],ball) || singlePocketCheck(pockets[2],ball) || singlePocketCheck(pockets[4],ball)){
                b=true;
            }
                break;
            case "RIGHT":
                if(singlePocketCheck(pockets[1],ball) || singlePocketCheck(pockets[3],ball) || singlePocketCheck(pockets[5],ball)) {
                    b = true;
                }
                break;
            case "TOP":
                if(singlePocketCheck(pockets[0],ball) || singlePocketCheck(pockets[1],ball) ) {
                    b = true;
                }break;
            //          Note: "middle" not needed in this case, as it is covered by Left and Right wall cases!
            case "BOTTOM":
                if(singlePocketCheck(pockets[4],ball) ||  singlePocketCheck(pockets[5],ball)) {
                    b = true;
                }
                break;
        }

        return b;
    }
    private boolean singlePocketCheck(Pocket pocket, Ball ball){
        //for all 6 pockets, just check if both x's and y's are less than pocketRadius apart? itll make a square but fuckit?
        //          OR, triangle! if deltaX +deltaY less than radius.   (Not using for now)

        //sticking with square for now
        if(Math.abs(ball.getPosX()-pocket.getX())<pocketRadius && Math.abs(ball.getPosY()-pocket.getY())<pocketRadius){
            BallPotRuleset.setBallPocketed(ball,pocket);
            return true;
        }
        else return false;
    }
/*
    private boolean singlePocketCheck(int i, int x, int y){
        //for all 6 pockets, just check if both x's and y's are less than pocketRadius apart? itll make a square but fuckit?
        //          OR, triangle! if deltaX +deltaY less than radius.   (Not using for now)

                        //sticking with square for now
        if(Math.abs(x-pockets[i].getX())<pocketRadius && Math.abs(y-pockets[i].getY())<pocketRadius){
            BallPotRuleset.setBallPocketed(ball,pockets[i]);
            return true;
        }
        else return false;
    }
    */


  /*  public boolean pocketCheckOld(int x, int y) {
        // I wonder which is less efficient- passing an "entire" ball object (which is prob just a pointer), or 2 small ints?
        if (x < border+ pocketRadius || x>border+tableWidth-pocketRadius) {
            if (y < border+ pocketRadius) {
                return true;
            }
            else if (y>tableLength+border-pocketRadius){            //bottom
                return true;
            }
            else if (y>tableLength/2+border-pocketRadius && y<tableLength/2+border+pocketRadius){
                return true;
            }
            else {
                return false;
            }
        } else {
            return false;
        }
    }

*/

}
