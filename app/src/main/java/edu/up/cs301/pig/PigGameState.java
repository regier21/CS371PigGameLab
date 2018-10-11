package edu.up.cs301.pig;

import java.util.Random;

import edu.up.cs301.game.infoMsg.GameState;

public class PigGameState extends GameState {

    private int turn;
    private int score_p1;
    private int score_p2;
    private int total;
    private int die;

    public PigGameState(){
        turn = new Random().nextBoolean() ? 0 : 1;
        score_p1 = 0;
        score_p2 = 0;
        total = 0;
        die = 1;
    }

    public PigGameState(PigGameState state){
        this.turn = state.turn;
        this.score_p1 = state.score_p1;
        this.score_p2 = state.score_p2;
        this.total = state.total;
        this.die = state.die;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public void swapTurn(){
        this.turn = 1 - this.turn;
    }

    public int getScore_p1() {
        return score_p1;
    }

    public void setScore_p1(int score_p1) {
        this.score_p1 = score_p1;
    }

    public int getScore_p2() {
        return score_p2;
    }

    public void setScore_p2(int score_p2) {
        this.score_p2 = score_p2;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getDie() {
        return die;
    }

    public void setDie(int die) {
        this.die = die;
    }
}
