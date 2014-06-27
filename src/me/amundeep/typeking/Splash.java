package me.amundeep.typeking;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class Splash extends Activity {
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_splash);
	
		Thread timer = new Thread(){
			public void run(){
				try{
					sleep(2000);
				}catch(InterruptedException e){
					e.printStackTrace();
				}finally {
					Intent openMainMenu = new Intent("me.amundeep.typeking.MAINMENU");
					startActivity(openMainMenu);
					Log.i("Startup", "Type King successfully opened!");
				}
			}
		};
		timer.start();
	}
}
