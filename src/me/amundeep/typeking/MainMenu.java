package me.amundeep.typeking;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;


public class MainMenu extends Activity{

	Context context = this;
	
	
	ImageView ivStart, ivInstructions, ivAbout, ivOptions;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_mainmenu);

		
		
		ivStart = (ImageView) findViewById(R.id.ivStart);
		ivInstructions = (ImageView) findViewById(R.id.ivInstructions);
		ivAbout = (ImageView) findViewById(R.id.ivAbout);
		ivOptions = (ImageView) findViewById(R.id.ivOptions);
		
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
				
				String VERSION = "n/a";
				try {
					VERSION = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
				} catch (NameNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				AlertDialog alertDialog = new AlertDialog.Builder(context).create(); //Read Update
				alertDialog.setTitle("About Type King");
		        alertDialog.setMessage("Developed and Created by Amundeep Singh\n\nVersion: " + 
		        					   VERSION + "\n\nKeyboard Key Icon by Arthur Shlain through TheNounProject");
		        
		        //Removed because ruined the dialog look with a default button there...
//		        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Close",new DialogInterface.OnClickListener() {
//			           public void onClick(DialogInterface dialog, int which) {}
//			    });
		        
		        alertDialog.show();
			}
		});
		
		ivOptions.setOnClickListener(new OnClickListener() {
			
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
