package me.amundeep.typeking;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class GameScreen extends Activity implements OnClickListener, OnEditorActionListener{

	/*
	 * Display Colors:
	 * #9A12B3 - [Default]
	 * #26A65B - Correct! +50
	 * #FF0000 - Incorrect! -50
	 */
	Context context = this;
	
	String[] testWords = {"amazing", "horrible", "swagger", "unbelievable", "working"};
	int wordCount = 0;
	int numWords = 0;
	int points = 0;
	int time = 30; //Change this if necessary
	boolean toggleStop = false;
	
	Button bStop;
	EditText etDisplay, etInput, etTime, etStatusBar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gamescreen);
		
		etStatusBar = (EditText) findViewById(R.id.etStatusBar);
		bStop = (Button) findViewById(R.id.bStop);
		bStop.setOnClickListener(this);
		etTime = (EditText) findViewById(R.id.etTime);
		etDisplay = (EditText) findViewById(R.id.etDisplay);
		
		etInput = (EditText) findViewById(R.id.etInput);
		etInput.setImeActionLabel("Submit", KeyEvent.KEYCODE_ENTER);
		etInput.setOnEditorActionListener(this);
		
	}

	//"Click 'back' to exit!" prompt user "are you sure" on back pressed...
	
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.bStop:
			if(toggleStop == false){  //IF GAME HAS NOT STARTED
				bStop.setText("STOP");
				etInput.requestFocus();
				launchLevel(); //Start Level
			}else{                    //IF GAME IS IN PROGRESS
				bStop.setText("BEGIN");
			}
			break;
		}
	}
	
	//When "Submit" is pressed on the keyboard
	@Override
	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		Log.i("EditorAction", "" + actionId);
		if (actionId == 66) {
			Log.i("EditorAction", "Returned true");
			checkWord();
			return true; //Keep keyboard open
	    } else {
	    	Log.i("EditorAction", "Returned false");
	    	return false;
	    }
	}
	
	public void checkWord(){
		if(etInput.getText().toString().trim().equals(etDisplay.getText().toString())){ //If correct
			displayMessage("Correct! +50 points", 1);
			wordCount++;
			if(wordCount < numWords){ //If still words left
				etDisplay.setText(testWords[wordCount]);
			}else{ //Game ends
				displayMessage("Congratulations, your score was: " + points, 0);
			}
		}else{
			displayMessage("Incorrect. -50 points", 2);
		}
		
		etInput.setText(""); //Clear input ET
		
	}
	
	public void launchLevel(){
		//Get words from file to array
		numWords = testWords.length;
		
		//Make counter and start with first word on etDisplay
		etDisplay.setText(testWords[wordCount]); //Show first word from list.
		
	}
	
	public void displayMessage(String s, int id){
		/*
		 * ID Reference:
		 * 0 - Default - #9A12B3
		 * 1 - Correct - #26A65B
		 * 2 - Incorrect - #FF0000
		 */
		etStatusBar.setText(s);
		switch(id){
		case 0: //Default
			etStatusBar.setTextColor(Color.parseColor("#9A12B3"));
			break;
		case 1: //Correct
			etStatusBar.setTextColor(Color.parseColor("#26A65B"));
			break;
		case 2: //Incorrect
			etStatusBar.setTextColor(Color.parseColor("#FF0000"));
			break;
		}
	}
	
	@Override
	public void onBackPressed() {
		
		AlertDialog alertDialog = new AlertDialog.Builder(this).create(); //Read Update
		alertDialog.setTitle("Quit Level");
        alertDialog.setMessage("Are you sure you want to quit this level and choose another one?");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes",new DialogInterface.OnClickListener() {
	           public void onClick(DialogInterface dialog, int which) {
	        	   Intent levels = new Intent("me.amundeep.typeking.LEVELPACKS");
	        	   startActivity(levels);
	           }
	    });
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "No",new DialogInterface.OnClickListener() {
	           public void onClick(DialogInterface dialog, int which) {
	        	   etInput.requestFocus();
	           }
	    });
        
        alertDialog.show();
	}
	
	
}
