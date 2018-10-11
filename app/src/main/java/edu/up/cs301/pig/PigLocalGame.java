package edu.up.cs301.pig;

import edu.up.cs301.game.GamePlayer;
import edu.up.cs301.game.LocalGame;
import edu.up.cs301.game.actionMsg.GameAction;
import edu.up.cs301.game.infoMsg.GameState;

import android.util.Log;

import java.util.Random;

/**
 * class PigLocalGame controls the play of the game
 *
 * @author Andrew M. Nuxoll, modified by Steven R. Vegdahl
 * @version February 2016
 */
public class PigLocalGame extends LocalGame {
    private PigGameState pigGameState;
    private Random rand;

    /**
     * This ctor creates a new game state
     */
    public PigLocalGame() {
        super();
        this.pigGameState = new PigGameState();
        rand = new Random();
    }

    /**
     * can the player with the given id take an action right now?
     */
    @Override
    protected boolean canMove(int playerIdx) {
        return pigGameState.getTurn() == playerIdx;
    }

    /**
     * This method is called when a new action arrives from a player
     *
     * @return true if the action was taken or false if the action was invalid/illegal.
     */
    @Override
    protected boolean makeMove(GameAction action) {
        if(action instanceof PigHoldAction ) {
            switch(pigGameState.getTurn()) {
                case (0):
                    pigGameState.setScore_p1(pigGameState.getScore_p1() + pigGameState.getTotal());
                    break;
                case (1):
                    pigGameState.setScore_p2(pigGameState.getScore_p2() + pigGameState.getTotal());
                    break;
            }

            pigGameState.swapTurn();
            pigGameState.setTotal(0);
            return true;
        }
        else if (action instanceof PigRollAction) {
            int dieVal = 1 + rand.nextInt(6);
            pigGameState.setDie(dieVal);

            if(dieVal > 1) {
                pigGameState.setTotal(pigGameState.getTotal() + dieVal);
            }
            else {
                pigGameState.setTotal(0);
                pigGameState.swapTurn();
            }

            return true;
        }
        else {
            return false;
        }
    }//makeMove

    /**
     * send the updated state to a given player
     */
    @Override
    protected void sendUpdatedStateTo(GamePlayer p) {
        p.sendInfo(new PigGameState(pigGameState));
    }//sendUpdatedSate

    /**
     * Check if the game is over
     *
     * @return
     * 		a message that tells who has won the game, or null if the
     * 		game is not over
     */
    @Override
    protected String checkIfGameOver() {
        if(pigGameState.getScore_p1() >= 50)
            return String.format("Player, %s, has won with a score of %s", super.playerNames[0],
                    pigGameState.getScore_p1());
        else if(pigGameState.getScore_p2() >= 50)
            return String.format("Player, %s, has won with a score of %s", super.playerNames[1],
                pigGameState.getScore_p2());
        return null;
    }

}// class PigLocalGame
