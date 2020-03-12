package com.example.kevbilliards.rulesets;


import com.example.kevbilliards.model.Ball;

public class CollisionCalculator {

    public boolean collisionCheck(Ball a, Ball b){

        double length = getDistance(a,b);
        if (length<=2*a.getRadius()){
            return true;
        }
        else return false;
    }

    public void collisionResolver(Ball a, Ball b){
        //  FIRST, roll back collision to be just touching, i.e. 2*radius, point of contact
                //      --> adjust proportionally based on the ratio of the 2 speeds!
        //  NEXT, calc the collision angle, i.e. angle of the line that intersects both centres of mass

        //initial reused variables
        double relativeSpeedX= (int)(a.getSpeed()*Math.sin(a.getAngle())-b.getSpeed()*Math.sin(b.getAngle()));
        double relativeSpeedY= (int)(a.getSpeed()*Math.cos(a.getAngle())-b.getSpeed()*Math.cos(b.getAngle()));

        //double relativeSpeed= a.getSpeed()*b.getSpeed()*Math.cos(a.getAngle()-b.getAngle());
        double relativeSpeed = Math.sqrt(relativeSpeedX*relativeSpeedX + relativeSpeedY*relativeSpeedY);
                //dont THINK i need relativeAngle? just wanna know how to divide up ratios of a vs b?


        adjustPosition(a,b,relativeSpeed);
        double collisionAngle= getCollisionAngle(a,b);
        transferMomentum(a,b,collisionAngle);


    }

    public void adjustPosition(Ball a, Ball b,double relSpeed){
        //  find pixel overlap,     then find total relative velocity, (will prob need in separate x and y values),
        //  [then "roll back" the latest movement change for each]  NOPE! ignore
        // then take away (crossoverDistance)*(Va)/(Va+Vb) from each ball position  (i think?)

        // dont think the above will work well (but havent checked)
        //  how about i find relative vel, then.... peel back by overlap/2? [NO! thatll go on forever!]
        //      instead, find rel vel, then shortest point perpendicular, and KNOW the hypotenuse = 2R,
        //      so can calc how far back from shortest point to move?       DONT FORGET THIS IS STILL REL VEL! INCORRECT!

        double overlap = 2*a.getRadius()-getDistance(a,b);
        int overlapTimeslice= (int)(overlap*1000/relSpeed);         //doublecheck?!
        do{a.moveBall(-overlapTimeslice);
            b.moveBall(-overlapTimeslice);
        }           while (getDistance(a,b)<2*a.getRadius());


/*        double overlap = 2*a.getRadius()-getDistance(a,b);
        int tempX= (int)(overlap*Math.sin(a.getAngle()));
        int tempY=(int)(overlap*Math.cos(a.getAngle()))
        a.setPosition(a.getPosX()-tempX,a.getPosY()-tempY);


        b.setPosition(b.getPosX()-(int)(overlap*Math.sin(b.getAngle())*(b.getSpeed()/)),b.getPosY()-(int)(overlap*Math.cos(b.getAngle())));
  */
    }

    public double getCollisionAngle(Ball a, Ball b){
        double collisionAngle =   (Math.atan2(b.getPosX()-a.getPosX(),b.getPosY()-a.getPosY()) + Math.PI);
        return collisionAngle;
    }

    public void transferMomentum(Ball a, Ball b, double collisionAngle){
        //  so for a one-static-ball, we treat A's speed/momentum as hypotenuse. Angle made = collisionAngle,
        // and sin()= untransferred momentum while cos= transferred.
        //
        double collisionForce= a.getSpeed()*Math.cos(collisionAngle-a.getAngle());
    //    double collisionForce= a.getSpeed()*Math.cos(collisionAngle-a.getAngle()) - b.getSpeed()*Math.cos(collisionAngle-b.getAngle());
                    //1st is simplification. does 2nd appropriately get collision force?


        // is it enough to find "overall collision force", then "take it away from both current balls"?
        //      i.e. I think this will auto-subtract from stronger, and add to weaker?

        //a.setSpeed(a.getSpeed()*collisionForce*Math.cos(collisionAngle-a.getAngle()));
        //b.setSpeed(b.getSpeed()*collisionForce*Math.cos(collisionAngle-b.getAngle()));

        //a.setSpeed(a.getSpeed()*Math.sin(collisionAngle-a.getAngle()));
        a.setSpeed(Math.sqrt(Math.pow(a.getSpeed(),2)-Math.pow(collisionForce,2)));
        b.setSpeed(collisionForce);

        b.setAngle(collisionAngle);
        a.setAngle(collisionAngle-90);

    }

    public double getDistance(Ball a,Ball b){
        int xa,xb,ya,yb;
        xa=a.getPosX();
        xb=b.getPosX();
        ya=a.getPosY();
        yb=b.getPosY();
        return Math.sqrt( ( ( xb - xa ) * ( xb - xa ) ) + ( ( yb - ya ) * ( yb - ya ) ) );
    }

    public void collision(Ball a, Ball b){
        double collisionAngle =   (Math.atan2(b.getPosX()-a.getPosX(),b.getPosY()-a.getPosY()) + Math.PI);
        System.out.println("CollisionAngle= "+collisionAngle);



        b.setAngle(collisionAngle);
        //a.setSpeed(0);
        if(Math.abs(collisionAngle)>Math.abs(a.getAngle())) {
            a.setAngle(collisionAngle - Math.PI);
        }
        else{
            a.setAngle(collisionAngle+ Math.PI);
        }

/*       // double angleDiff= Math.abs(collisionAngle)-Math.abs(a.getAngle());                //messing up cos as raw data, its gonna be >180 degs.   so use b_angle!
        double angleDiff= Math.abs(b.getAngle())-Math.abs(a.getAngle());
        //b.setSpeed(a.getSpeed());
        b.setSpeed(a.getSpeed()*Math.cos(angleDiff));
        a.setSpeed(a.getSpeed()*Math.sin(angleDiff));
        System.out.println("a="+a.getSpeed()+",b="+b.getSpeed()+", anglediff="+angleDiff);

*/                              //the above is misbehaving and acting weirdly. whatever: for now give 2/3rds of initial momentum from 1st to 2nd
        //      (of course this is stupid idea.... (a) what if ball B is the moving one? ball order is not determined by speed.
        //      (b) what if theyre heading for each other? why should ball B get more power?
        b.setSpeed(a.getSpeed()*.5);
        a.setSpeed(a.getSpeed()*.5);

        // b.moveBall(20);
    }


}
