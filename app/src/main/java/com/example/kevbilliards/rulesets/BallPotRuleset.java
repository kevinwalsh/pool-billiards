package com.example.kevbilliards.rulesets;


import com.example.kevbilliards.model.Ball;
import com.example.kevbilliards.model.Pocket;

public class BallPotRuleset {


//    GameRuleset gameRuleset;
    public BallPotRuleset()
    {
  //      gameRuleset=gr;
    }


    public void setBallPocketed(Ball ball, Pocket pocket){
            ball.setSpeed(0);
        ball.setPosition(0,0);
        checkBallRules(ball);
            System.out.println("POCKETED");
            ball.setPocketed(pocket);

    }

    private void checkBallRules(Ball ball){
        if(ball.getID()==0){                      //white ball
            checkWhiteRule(ball);
        }
        else if (ball.getID()==14){               //black ball
            checkBlackRule(ball);
        }
        else {
            checkWrongBallRule(ball);
        }
    }

    public void checkWhiteRule(Ball ball){
        System.out.println("BallPotRuleset: FOUL!      White Potted");

    }
    public void checkBlackRule(Ball ball){
        System.out.println("BallPotRuleset: FOUL!      Black Potted");
        System.out.println("---------------END GAME HERE----------------");
    }
    public void checkWrongBallRule(Ball ball){
        if(ball.getID()>0 && ball.getID()<8){
            System.out.println("BallPotRuleset: FOUL!      Red Ball Potted (ID between 1 and 7)");
        }

    }
}
