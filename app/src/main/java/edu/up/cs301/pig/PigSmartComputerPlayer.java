package edu.up.cs301.pig;

import java.util.Random;

import edu.up.cs301.game.GameComputerPlayer;
import edu.up.cs301.game.actionMsg.GameAction;
import edu.up.cs301.game.infoMsg.GameInfo;
import edu.up.cs301.game.infoMsg.GameState;
import edu.up.cs301.game.util.Tickable;

/**
 * An AI for Pig
 *
 * @author Team Rocket
 * @version October 2018
 */
public class PigSmartComputerPlayer extends GameComputerPlayer {

    /**
     * ctor does nothing extra
     */
    public PigSmartComputerPlayer(String name) {
        super(name);
    }

    /**
     * callback method--game's state has changed
     *
     * @param info
     * 		the information (presumably containing the game's state)
     */
    @Override
    protected void receiveInfo(GameInfo info) {
        if(info instanceof PigGameState) {

            PigGameState state = (PigGameState) info;
            if(state.getTurn() != super.playerNum) return;

            sleep(1000);

            int score, oppScore;
            if (super.playerNum == 0){
                score = state.getScore_p1();
                oppScore = state.getScore_p2();
            } else {
                score = state.getScore_p2();
                oppScore = state.getScore_p1();
            }

            if (50 - score < state.getTotal()) super.game.sendAction(new PigHoldAction(this));
            else if (oppScore > 30) super.game.sendAction(new PigRollAction(this));
            else if(state.getTotal() >= 20) super.game.sendAction(new PigHoldAction(this));
            else super.game.sendAction(new PigRollAction(this));
        }
    }//receiveInfo

}
