package me.amundeep.typeking;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class GameModes extends Activity {

	Context context;
	Button bStandard, bHardcore;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gamemodes);
		context = this;
		
		bStandard = (Button) findViewById(R.id.bStandard);
		bHardcore = (Button) findViewById(R.id.bHardcore);
		
		bStandard.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				//GO TO STANDARD LEVEL PACKS
				//TRANSFER DATA TO LEVELPACKS INTENT SAYING IT'S STANDARD MODE
			}
			
		});
		
		bHardcore.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent i = new Intent("me.amundeep.typeking.LEVELPACKS");
				startActivity(i);
			}
		});
		
		
		
		
	}

	
	@Override
	public void onBackPressed() {
		Intent mainMenu = new Intent("me.amundeep.typeking.MAINMENU");
		startActivity(mainMenu);
	}
	
}
