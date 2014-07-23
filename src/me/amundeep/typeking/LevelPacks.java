package me.amundeep.typeking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class LevelPacks extends Activity{

	Context context;
	ListView lvLevelPacks;
	int numLines = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_levelpacks);
		
		context = this;
		
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
		
		BufferedReader numLinesReader = new BufferedReader(new InputStreamReader(in));
		
		try {
			String l = numLinesReader.readLine();
			while(l != null){
				Log.i("LevelPacks", "Level " + numLines);
				numLines++;
				l = numLinesReader.readLine();
			}
			NUM_LEVELS = numLines;
			Log.i("LevelPacks", "Total: " + NUM_LEVELS);
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
		BufferedReader reader = new BufferedReader(new InputStreamReader(in2));
		
		String line = "";
		int counter = 0;
		
		//Loop to read Level names from levels.txt
		while(counter < levels.length){
			try {
				line = reader.readLine();
				levels[counter] = line.trim();
			} catch (IOException ex) {
				String err = (ex.getMessage()==null)?"SD Card failed":ex.getMessage();
				Log.e("sdcard-err2:",err);
			}
			counter++;
		}
		
		ArrayAdapter<String> adapter = new MyAdapter<String>(this,
	              android.R.layout.simple_list_item_1,  levels);
		
		lvLevelPacks.setAdapter(adapter);
		
		lvLevelPacks.setOnItemClickListener(new OnItemClickListener(){     //WHEN A LEVEL IS CHOSEN

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {  
				// ListView Clicked item index
                int itemPosition = position;
                
                // ListView Clicked item value
                String  itemValue = (String) lvLevelPacks.getItemAtPosition(position);
                   
                // Show Alert 
//                Toast.makeText(getApplicationContext(),
//                   "Position: "+itemPosition+"  ListItem : " +itemValue , Toast.LENGTH_SHORT)
//                   .show();
                
                Intent openGame = new Intent("me.amundeep.typeking.GAMESCREEN");
                openGame.putExtra("level", "level_" + (itemPosition + 1));
                startActivity(openGame);
                
			}
		
		});
		
		
	}
	
	class MyAdapter<String> extends ArrayAdapter<String>{
		
		public MyAdapter(Context context, int resource, String[] values){
			super(context, resource, values);
		}
		
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {  
			View view = super.getView(position, convertView, parent);  
			if (position < (numLines/3)) {
				view.setBackgroundColor(Color.parseColor("#F5AB35"));  
			} else if(position < ((2*numLines)/3)) {
				view.setBackgroundColor(Color.parseColor("#E67E22"));  
			} else{
				view.setBackgroundColor(Color.parseColor("#C0392B"));
			}

			return view;  
		}
		
	}

	
	@Override
	public void onBackPressed() {
		Intent mainMenu = new Intent("me.amundeep.typeking.MAINMENU");
		startActivity(mainMenu);
	}
	
	
}
