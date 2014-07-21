package me.amundeep.typeking;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class GameScreen extends Activity implements OnClickListener{

	/*
	 * Display Colors:
	 * #9A12B3 - [Default]
	 * #26A65B - Correct! +50
	 * #FF0000 - Incorrect! -50
	 */
	
	String[] testWords = {"amazing", "horrible", "swagger", "unbelievable", "working"};
	int wordCount = 0;
	
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
//		etInput.setOnKeyListener(this);
		etInput.setOnEditorActionListener(new OnEditorActionListener() {
			
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				Log.i("EditorAction", "" + actionId);
				if (actionId == 66) {
					// your additional processing... 
					Log.i("EditorAction", "Returned true");
					wordCount++;
					checkWord();
					return true;
			    } else {
			    	Log.i("EditorAction", "Returned false");
			    	return false;
			    }
			}

		});
		
	}

	//"Click 'back' to exit!" prompt user "are you sure" on back pressed...
	
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.bStop:
			etInput.requestFocus();
			launchLevel(); //Start Level
			break;
		}
	}
	
//	@Override
//	public boolean onKey(View v, int keyCode, KeyEvent event) {
//		if( (event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)){
////			Toast.makeText(getApplicationContext(), "Hey", Toast.LENGTH_SHORT).show();
//			wordCount++;
//			etInput.requestFocus();
//			checkWord();
//			return true;
//		}
//		return false;
//	}
	
	public void checkWord(){
		etDisplay.setText(testWords[wordCount]);
	}
	
	public void launchLevel(){
		//Get words from file to array
		
		//Make counter and start with first word on etDisplay
		checkWord();
		
	}
	
	
}
