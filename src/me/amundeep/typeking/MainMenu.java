package me.amundeep.typeking;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;


public class MainMenu extends Activity{

	ImageView ivStart, ivInstructions, ivAbout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_mainmenu);

		ivStart = (ImageView) findViewById(R.id.ivStart);
		ivInstructions = (ImageView) findViewById(R.id.ivInstructions);
		ivAbout = (ImageView) findViewById(R.id.ivAbout);
		
		ivStart.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Log.i("Button", "working!");
			}
			
		});
		
		ivInstructions.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		ivAbout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
//		AlertDialog alertDialog = new AlertDialog.Builder(this).create(); //Read Update
//        alertDialog.setTitle("Exit Game");
//        alertDialog.setMessage("Are you sure you want to quit and return to the main menu?");
//		
//		alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No",new DialogInterface.OnClickListener() {
//	           public void onClick(DialogInterface dialog, int which) {
//	              // here you can add functions
//	           }
//	        });
//		
		
//		//Get Screen Resolution
//		DisplayMetrics displaymetrics = new DisplayMetrics();
//		getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
//		int height = displaymetrics.heightPixels;
//		int width = displaymetrics.widthPixels;
		
//		LayoutParams params = new LayoutParams(width - (width/8), height/4);
//		tvTitle.setLayoutParams(params);
//		bStart.setLayoutParams(params);
//		bInstructions.setLayoutParams(params);
//		bAbout.setLayoutParams(params);

	}
	
	@Override
	public void onBackPressed() {
		
	}

	
	
}
