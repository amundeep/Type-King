package me.amundeep.typeking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class LevelPacks extends Activity{

	ListView lvLevelPacks;
	int numLines = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_levelpacks);
		
		lvLevelPacks = (ListView) findViewById(R.id.lvLevelPacks);
//		String[] levels = new String[] { "Android List View", 
//                "Adapter implementation",
//                "Simple List View In Android",
//                "Create List View Android", 
//                "Android Example", 
//                "List View Source Code", 
//                "List View Array Adapter", 
//                "Android Example List View",
//                "List View Array Adapter", 
//                "List View Array Adapter", 
//                "List View Array Adapter", 
//                "List View Array Adapter", 
//                "List View Array Adapter", 
//                "List View Array Adapter", 
//                "List View Array Adapter", 
//                "List View Array Adapter", 
//                "List View Array Adapter", 
//                "List View Array Adapter", 
//               };
		
		InputStream in = null;
		InputStream in2 = null;
		
		int NUM_LEVELS = 0;
		
		try {
			in = this.getAssets().open("levels.txt");
			in2 = this.getAssets().open("levels.txt");
		} catch (IOException ex) {
			String err = (ex.getMessage()==null)?"SD Card failed":ex.getMessage();
			Log.e("sdcard-err2:",err);  
		}
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		
		try {
			String l = reader.readLine();
			while(l != null){
				Log.i("hello", "oh god " + numLines);
				numLines++;
				l = reader.readLine();
			}
			NUM_LEVELS = numLines;
			Log.i("hi", "" + NUM_LEVELS);
		} catch (IOException ex) {
			String err = (ex.getMessage()==null)?"SD Card failed":ex.getMessage();
			Log.e("sdcard-err2:",err); 
		}
		
		//Create an array of blank strings (not null) that is number of levels from levels.txt
		String[] levels = new String[NUM_LEVELS];
		for(int i = 0; i < NUM_LEVELS; i++){
			levels[i] = "";
		}
		
		//Second buffered reader to read level names.
		BufferedReader reader2 = new BufferedReader(new InputStreamReader(in2));
		
		String line = "";
		int counter = 0;
		
		//Loop to read Level names from levels.txt
		while(counter < levels.length){
			try {
				line = reader2.readLine();
				levels[counter] = line.trim();
			} catch (IOException ex) {
				String err = (ex.getMessage()==null)?"SD Card failed":ex.getMessage();
				Log.e("sdcard-err2:",err);
			}
			counter++;
		}
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
	              android.R.layout.simple_list_item_1,  levels);
		
		lvLevelPacks.setAdapter(adapter);
		
		lvLevelPacks.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				// TODO Auto-generated method stub
				// ListView Clicked item index
                int itemPosition = position;
                
                // ListView Clicked item value
                String  itemValue = (String) lvLevelPacks.getItemAtPosition(position);
                   
                // Show Alert 
                Toast.makeText(getApplicationContext(),
                   "Position: "+itemPosition+"  ListItem : " +itemValue , Toast.LENGTH_LONG)
                   .show();
                
                Intent openGame = new Intent("me.amundeep.typeking.GAMESCREEN");
                startActivity(openGame);
                
			}
		
		});
		
		
	}

	
	
}
