package me.amundeep.typeking;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class GameScreen extends Activity implements OnClickListener, OnEditorActionListener{

	Context context = this;
	
	InputMethodManager imm;
	
	int wordCount = 0;
	int numWords = 0;
	int points = 0;
	
	//Mechanics
	boolean levelComplete = false;
	
	//Level Selected
	String levelSelect;
	ArrayList<String> levelWords;
	
	//Timer elements
	int time = 30; //Change this if necessary
	long milliTime = 30000;
	long milliCountdown = 1000;
	public Handler handler;
	public Runnable timer;
	boolean isRunning = false;
	
	//Button state
	int toggleStop = 0;
	String[] toggleText = {"BEGIN", "PAUSE/QUIT", "RESTART/QUIT", "NEW LEVEL/QUIT"};
	
	//UI Elements
	Button bStop;
	EditText etDisplay, etInput, etTime, etStatusBar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gamescreen);
		
		Intent i = getIntent();
		levelSelect = i.getExtras().getString("level");
//		Log.i("Level", levelSelect);
		
		levelWords = new ArrayList<String>();
		
		InputStream inNumLines = null;
		InputStream inWords = null;
		
		try {
			inNumLines = this.getAssets().open("levelpacks/" + levelSelect + ".txt");
			inWords = this.getAssets().open("levelpacks/" + levelSelect + ".txt");
		} catch (IOException ex) {
			String err = (ex.getMessage()==null)?"SD Card failed":ex.getMessage();
			Log.e("sdcard-err2:",err);  
		}
		
		BufferedReader numLinesReader = new BufferedReader(new InputStreamReader(inWords));
		
		int counter = 0;
		
		try {
			String l = numLinesReader.readLine();
			while(l != null){
				String temp;
				
				if(counter == 0)
					temp = l;
				else
					temp = numLinesReader.readLine();
				
				Log.i("GameScreen", "Word Index: " + numWords + "  |  Word: " + temp);
				l = temp;
				if(l != null){
					numWords++;
					levelWords.add(temp);
				}
				counter++;
			}
			Log.i("GameScreen", "Total: " + numWords);
		} catch (IOException ex) {
			String err = (ex.getMessage()==null)?"SD Card failed":ex.getMessage();
			Log.e("sdcard-err2:",err); 
		}
		
		
		for(String s : levelWords){
			Log.i("GameScreen", "Word Array: " + s);
		}
		
		etStatusBar = (EditText) findViewById(R.id.etStatusBar);
		bStop = (Button) findViewById(R.id.bStop);
		bStop.setOnClickListener(this);
		
		//Timer Elements
		etTime = (EditText) findViewById(R.id.etTime);
		etTime.setText("Time: " + time);
		handler = new Handler();
		timer = new Runnable(){

			@Override
			public void run() {
				if(!isRunning){
					return;
				}
				
				if(time > 0){
					time--;
					etTime.setText("Time: " + time);
				} else {
					gameOver(); //GAME IS OVER WHEN TIME RUNS OUT
					isRunning = false;
				}
				handler.postDelayed(timer, milliCountdown);
			}
			
		};
		
		etDisplay = (EditText) findViewById(R.id.etDisplay);
		
		etInput = (EditText) findViewById(R.id.etInput);
		etInput.setImeActionLabel("Submit", KeyEvent.KEYCODE_ENTER);
		etInput.setOnEditorActionListener(this);
		etInput.setFocusable(false);
		etInput.setFocusableInTouchMode(false);
		imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
	}
	
	
	//TIMER METHODS
	public void startTimer(){
		isRunning = true;
		timer.run();
	}
	
	public void stopTimer(){
		isRunning = false;
		handler.removeCallbacks(timer);
		time = 30;
		etTime.setText("Time: " + time);
	}
	
	public void pauseTimer(){
		isRunning = false;
		handler.removeCallbacks(timer);
	}
	
	public void resumeTimer(){
		isRunning = true;
		timer.run();
	}
	
	
	//TOGGLE BUTTON GUIDE:
	
	/*
	 * 0 - "BEGIN" - Start Game
	 * 1 - "PAUSE/QUIT" - Pause/Quit game
	 * 2 - "RESTART/QUIT" - Game over, time ran out.
	 * 3 - "NEW LEVEL/QUIT" - Completed, choose new level or quit.
	 * 
	 */
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.bStop:
			if(toggleStop == 0){  //IF GAME HAS NOT STARTED
				toggleStop = 1; //IN LEVEL STATE
				bStop.setText(toggleText[toggleStop]);
				displayMessage("Type the words that appear.", 0);
				launchLevel(); //Start Level
				startTimer();; //Start timer
			}else if(toggleStop == 1){                    //IF GAME IS IN PROGRESS
				pauseTimer();
				AlertDialog alertDialog = new AlertDialog.Builder(this).create();
				alertDialog.setTitle("Level Paused");
		        alertDialog.setMessage("The level is paused.  Resume or quit to Main Menu");
		        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Quit to Menu",new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int which) {
			        	   toggleStop = 0;
			        	   bStop.setText(toggleText[toggleStop]);
			        	   stopTimer();
			        	   Intent menu = new Intent("me.amundeep.typeking.MAINMENU");
			        	   startActivity(menu);
			           }
			    });
		        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Resume Level",new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int which) {
			        	   resumeTimer();
			        	   etInput.requestFocus();
			           }
			    });
		        alertDialog.setCancelable(false);
		        alertDialog.setCanceledOnTouchOutside(false);
		        alertDialog.show();
		        
			}else if(toggleStop == 2){                  //GAME OVER, RESTART/QUIT
				AlertDialog alertDialog = new AlertDialog.Builder(this).create();
				alertDialog.setTitle("Game Over");
		        alertDialog.setMessage("Unfortunately, you couldn't complete the level.  Restart the current level or quit to Main Menu");
		        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Quit to Menu",new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int which) {
			        	   toggleStop = 0;
			        	   bStop.setText(toggleText[toggleStop]);
			        	   stopTimer();
			        	   Intent menu = new Intent("me.amundeep.typeking.MAINMENU");
			        	   startActivity(menu);
			           }
			    });
		        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Restart Level",new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int which) {
			        	   toggleStop = 1; //IN LEVEL STATE
			        	   bStop.setText(toggleText[toggleStop]);
			        	   etInput.requestFocus();
			        	   launchLevel(); //Start Level
			        	   startTimer();; //Start timer
			           }
			    });
//		        alertDialog.setCancelable(false);
//		        alertDialog.setCanceledOnTouchOutside(false);
		        alertDialog.show();
			}else{                                   //COMPLETE, NEW LEVEL/QUIT
				AlertDialog alertDialog = new AlertDialog.Builder(this).create();
				alertDialog.setTitle("Level Complete");
		        alertDialog.setMessage("Choose a new level or quit to Main Menu.");
		        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Quit to Menu",new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int which) {
			        	   toggleStop = 0;
			        	   bStop.setText(toggleText[toggleStop]);
			        	   stopTimer();
			        	   Intent menu = new Intent("me.amundeep.typeking.MAINMENU");
			        	   startActivity(menu);
			           }
			    });
		        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Choose Level",new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int which) {
			        	   toggleStop = 0; //BEGINNING STATE
			        	   bStop.setText(toggleText[toggleStop]);
			        	   Intent levels = new Intent("me.amundeep.typeking.LEVELPACKS");
			        	   startActivity(levels);
			           }
			    });
//		        alertDialog.setCancelable(false);
//		        alertDialog.setCanceledOnTouchOutside(false);
		        alertDialog.show();
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
	
	//Every time "Submit" is pressed.
	public void checkWord(){
		
		String input = etInput.getText().toString().trim().toLowerCase(Locale.getDefault()); //USER INPUT
		String display = etDisplay.getText().toString().toLowerCase(Locale.getDefault());  //ACTUAL WORD
		
//		Log.i("GameScreen", "Empty ET?: " + etInput.getText());
		
		if(etInput.getText().toString().equals("") || etInput.getText().toString() == null){
			displayMessage("Please enter some text to input.", 2);
			return;
		}
		
		if(input.equals(display)){ //If correct
			displayMessage("Correct! +50 points", 1);
			wordCount++;
			if(wordCount < numWords){ //If still words left
				etDisplay.setText(levelWords.get(wordCount));
			}else{ //Level completed
				levelComplete();
			}
		}else{
			displayMessage("Incorrect. -50 points", 2);
		}
		
		etInput.setText(""); //Clear input ET
		
	}
	
	public void launchLevel(){
		etInput.setFocusable(true);
		etInput.setFocusableInTouchMode(true);
		etInput.requestFocus();
		imm.showSoftInput(etInput, InputMethodManager.SHOW_IMPLICIT);
		
		wordCount = 0;
		
		//Make counter and start with first word on etDisplay
		etDisplay.setText(levelWords.get(wordCount)); //Show first word from list.
		
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
	
	//GAME OVER
	public void gameOver(){
		pauseTimer();
		etTime.setText("Time: 0");
		displayMessage("Game Over!  Restart, quit to Main Menu, or go back to choose a new level.", 2);
		
		etInput.setFocusable(false);
		etInput.setFocusableInTouchMode(false);
		imm.hideSoftInputFromWindow(etInput.getWindowToken(), 0);
		
		time = 30;
		toggleStop = 2;
		bStop.setText(toggleText[toggleStop]);
	}
	
	//LEVEL COMPLETE
	public void levelComplete(){
		pauseTimer();
		etDisplay.setText("");
		displayMessage("Congratulations, your score was: " + points, 0);
		
		etInput.setFocusable(false);
		etInput.setFocusableInTouchMode(false);
		imm.hideSoftInputFromWindow(etInput.getWindowToken(), 0);
		
		levelComplete = true;
		toggleStop = 3;
		bStop.setText(toggleText[toggleStop]);
		wordCount = 0;
		
	}
	
	//Used when chosen level is NOT completed yet
	public void chooseNewLevel(){
		AlertDialog alertDialog = new AlertDialog.Builder(this).create();
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
	
	
	
	@Override
	public void onBackPressed() {
		if(levelComplete){
			Intent levels = new Intent("me.amundeep.typeking.LEVELPACKS");
			startActivity(levels);
		}else{
			chooseNewLevel();
		}
	}
	
	
}
