package edu.up.cs301.pig;

import edu.up.cs301.game.GameHumanPlayer;
import edu.up.cs301.game.GameMainActivity;
import edu.up.cs301.game.R;
import edu.up.cs301.game.infoMsg.GameInfo;
import edu.up.cs301.game.infoMsg.StartGameInfo;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.nfc.Tag;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.widget.Toast;

import static android.os.SystemClock.sleep;

/**
 * A GUI for a human to play Pig. This default version displays the GUI but is incomplete
 *
 *
 * @author Andrew M. Nuxoll, modified by Steven R. Vegdahl
 * @version February 2016
 */
public class PigHumanPlayer extends GameHumanPlayer implements OnClickListener {

	/* instance variables */
	private int turnChanged = -1;
	private int lastTotal = 0;
    String[] names = null;

    // These variables will reference widgets that will be modified during play
    private TextView    playerScoreTextView = null;
    private TextView    playerNameTextView = null;
    private TextView    oppScoreTextView    = null;
    private TextView    oppNameTextView = null;
    private TextView    turnTotalTextView   = null;
    private TextView    messageTextView     = null;
    private ImageButton dieImageButton      = null;
    private Button      holdButton          = null;

    // the android activity that we are running
    private GameMainActivity myActivity;

    public static final int[] die_array = {R.drawable.face1, R.drawable.face2, R.drawable.face3,
            R.drawable.face4, R.drawable.face5, R.drawable.face6};

    /**
     * constructor does nothing extra
     */
    public PigHumanPlayer(String name) {
        super(name);
    }

    /**
     * Returns the GUI's top view object
     *
     * @return
     * 		the top object in the GUI's view heirarchy
     */
    public View getTopView() {
        return myActivity.findViewById(R.id.top_gui_layout);
    }

    /**
     * callback method when we get a message (e.g., from the game)
     *
     * @param info
     * 		the message
     */
    @Override
    public void receiveInfo(GameInfo info) {
        if (info instanceof PigGameState) {
            PigGameState state = (PigGameState) info;
            int score, oppScore;
            if (super.playerNum == 0) {
                score = state.getScore_p1();
                oppScore = state.getScore_p2();
            } else {
                score = state.getScore_p2();
                oppScore = state.getScore_p1();
            }

            playerScoreTextView.setText(Integer.toString(score));
            oppScoreTextView.setText(Integer.toString(oppScore));

            turnTotalTextView.setText(Integer.toString(state.getTotal()));

            dieImageButton.setImageResource(die_array[state.getDie() - 1]);
            if (super.playerNum == state.getTurn()) dieImageButton.setBackgroundColor(Color.RED);
            else dieImageButton.setBackgroundColor(Color.GRAY);

            if (turnChanged == -1) turnChanged = state.getTurn();
            else if (turnChanged != state.getTurn()) {
                Toast toast = null;
                CharSequence playerTxt = "Player " + Integer.toString(turnChanged + 1);
                if (state.getDie() == 1) {
                    CharSequence txt = playerTxt + " rolled a one! Oh no!";
                     toast = Toast.makeText(myActivity.getApplicationContext(), txt , Toast.LENGTH_LONG);
                }
                else {
                    CharSequence txt = playerTxt + " reached a score of " + lastTotal;
                    toast = Toast.makeText(myActivity.getApplicationContext(), txt , Toast.LENGTH_LONG);
                }
                toast.show();
                turnChanged = state.getTurn();
            }

            lastTotal = state.getTotal();


        }
        /*else if(info instanceof StartGameInfo) {
            names = ((StartGameInfo) info).getPlayerNames();
            this.playerNameTextView.setText(names[super.playerNum]);
            this.oppNameTextView.setText(names[1 - super.playerNum]);
        }*/
        else {
            super.flash(Color.RED, 100);
        }
    }//receiveInfo

    /**
     * this method gets called when the user clicks the die or hold button. It
     * creates a new PigRollAction or PigHoldAction and sends it to the game.
     *
     * @param button
     * 		the button that was clicked
     */
    public void onClick(View button) {
        if (button == holdButton){
            super.game.sendAction(new PigHoldAction(this));
        } else if (button == dieImageButton){
            super.game.sendAction(new PigRollAction(this));
        }
    }// onClick

    /**
     * callback method--our game has been chosen/rechosen to be the GUI,
     * called from the GUI thread
     *
     * @param activity
     * 		the activity under which we are running
     */
    public void setAsGui(GameMainActivity activity) {

        // remember the activity
        myActivity = activity;

        // Load the layout resource for our GUI
        activity.setContentView(R.layout.pig_layout);

        //Initialize the widget reference member variables
        this.playerScoreTextView = (TextView)activity.findViewById(R.id.yourScoreValue);
        this.playerNameTextView = (TextView) activity.findViewById(R.id.yourScoreText);
        this.oppScoreTextView    = (TextView)activity.findViewById(R.id.oppScoreValue);
        this.oppNameTextView = (TextView) activity.findViewById(R.id.oppScoreText);
        this.turnTotalTextView   = (TextView)activity.findViewById(R.id.turnTotalValue);
        this.messageTextView     = (TextView)activity.findViewById(R.id.messageTextView);
        this.dieImageButton      = (ImageButton)activity.findViewById(R.id.dieButton);
        this.holdButton          = (Button)activity.findViewById(R.id.holdButton);




        //Listen for button presses
        dieImageButton.setOnClickListener(this);
        holdButton.setOnClickListener(this);

    }//setAsGui

    @Override
    protected void initAfterReady() {
        this.playerNameTextView.setText(allPlayerNames[super.playerNum]);
        this.oppNameTextView.setText(allPlayerNames[1 - super.playerNum]);
    }

}// class PigHumanPlayer
