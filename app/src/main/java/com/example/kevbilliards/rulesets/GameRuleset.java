package com.example.kevbilliards.rulesets;


import com.example.kevbilliards.model.Ball;
import com.example.kevbilliards.model.Pocket;


public class GameRuleset {

    /*  really unsure about this class but it could prob be handy
        - for MVC, should probably make a GameModel/GameStats class too
        - main motivation is to have somewhere "global" to refer to important global stats?
        -should i store balls/pockets here? definitely activeBalls list, derived from existingBalls
    * */


    //      **      N.B.    "should never remove a [BALL] from a list while iterating over it, (i.e. a For loop)
                        //  (if NECESSARY, can more safely get around this using an Iterator)
                        //  (though probably enough for my needs to either:
                                        //  (a) give Ball the extra property,   Boolean active = false
                                        //  (b) cycle list once each time, and adjust list size at end when loop finished

    public GameRuleset() {

    }

}
