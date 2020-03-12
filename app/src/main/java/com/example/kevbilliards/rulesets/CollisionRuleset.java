package com.example.kevbilliards.rulesets;


import com.example.kevbilliards.model.Ball;

public class CollisionRuleset {

    CollisionCalculator collisionCalculator;

    public CollisionRuleset(){
        System.out.println("CollisionRuleset created");
        collisionCalculator= new CollisionCalculator();
    }

    public void checkAll(Ball[] balls){
        for (int i=0;i<balls.length-1;i++){
            for (int j=i+1;j<balls.length;j++){
                if(collisionCalculator.collisionCheck(balls[i],balls[j])){
                    collisionCalculator.collisionResolver(balls[i],balls[j]);
                }
            }
        }
    }

    public void checkAll_Old(Ball[] balls){
        int[] x= new int[balls.length];
        int[] y = new int[balls.length];
        double r= balls[0].getRadius();

        for(int i=0;i<balls.length;i++){
            x[i]=balls[i].getPosX();
            y[i]=balls[i].getPosY();
        }

        for(int i=0;i<balls.length-1;i++){
            for (int j=i+1; j<balls.length;j++){
                double length = Math.sqrt( ( ( x[j] - x[i] ) * ( x[j] - x[i] ) ) + ( ( y[j] - y[i] ) * ( y[j] - y[i] ) ) );

                if(length<2*r){
     /*             System.out.println("collision between balls "+i+" and "+j);
                    System.out.println("BEFORE: i=" +balls[i].getAngle()+" , and j="+balls[j].getAngle());


                    double collisionAngle =   (Math.atan2(x[j]-x[i],y[j]-y[i]));
                    balls[i].setAngle(collisionAngle-balls[i].getAngle());
                    //balls[i].setSpeed(balls[i].getSpeed());
                    balls[j].setAngle(collisionAngle);
                    balls[j].setSpeed(balls[i].getSpeed());

                    balls[i].setSpeed(0);

                    System.out.println("AFTER: i=" +balls[i].getAngle()+" , and j="+balls[j].getAngle());
                    System.out.println("CollisionAngle="+collisionAngle);


                    //extra bit, dodgy workaround. move balls apart!
                    balls[i].moveBall(40);
                    balls[j].moveBall(40);
       */
                    //collision(balls[i],balls[j]);
                    collisionCalculator.collisionResolver(balls[i],balls[j]);
                }
            }
        }
    }

}
