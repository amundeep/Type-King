package me.amundeep.typeking;
import android.app.Activity;
import android.os.Bundle;

public class GameScreen extends Activity{

	/*
	 * Display Colors:
	 * #9A12B3 - [Default]
	 * #26A65B - Correct! +50
	 * #FF0000 - Incorrect! -50
	 */
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gamescreen);
		
	}

	
	
}
