package me.amundeep.typeking;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class LevelPacks extends Activity{

	Context context;
	ListView lvLevelPacks;
	int numLinesTemp = 0;
	public int numLevelsForFile = 0;
	String[] levels;
	ArrayList<String> completeArray;
	ArrayList<Integer> hsArray;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_levelpacks);
		
		context = this;
		
		lvLevelPacks = (ListView) findViewById(R.id.lvLevelPacks);

		completeArray = new ArrayList<String>();
		hsArray = new ArrayList<Integer>();
		
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
				numLinesTemp++;
				Log.i("LevelPacks", "Level " + numLinesTemp);
				
				l = numLinesReader.readLine();
			}
			NUM_LEVELS = numLinesTemp;
			Log.i("LevelPacks", "Total: " + NUM_LEVELS);
		} catch (IOException ex) {
			String err = (ex.getMessage()==null)?"SD Card failed":ex.getMessage();
			Log.e("sdcard-err2:",err); 
		}
		
		//Create an array of blank strings (not null) that is number of levels from levels.txt
		levels = new String[NUM_LEVELS];
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
				if(line.startsWith("Level"))
					numLevelsForFile++;
				levels[counter] = line.trim();
			} catch (IOException ex) {
				String err = (ex.getMessage()==null)?"SD Card failed":ex.getMessage();
				Log.e("sdcard-err2:",err);
			}
			counter++;
		}
		
		
		//WRITE TO FILE FOR FIRST TIME (check happens in method)
		createFile("progress.txt");
		
		readFile("progress.txt");
		
		ArrayAdapter<String> adapter = new MyAdapter<String>(this,
	              android.R.layout.simple_list_item_2, android.R.id.text1, levels);
		
		lvLevelPacks.setAdapter(adapter);
		
		lvLevelPacks.setOnItemClickListener(new OnItemClickListener(){     //WHEN A LEVEL IS CHOSEN

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {  
				// ListView Clicked item index
                int itemPosition = position;
                
                // ListView Clicked item value
                String itemValue = (String) lvLevelPacks.getItemAtPosition(position);
                   
                // Show Alert 
//                Toast.makeText(getApplicationContext(),
//                   "Position: "+itemPosition+"  ListItem : " +itemValue , Toast.LENGTH_SHORT)
//                   .show();
                
                Intent openGame = new Intent("me.amundeep.typeking.GAMESCREEN");
                openGame.putExtra("level", "level_" + (itemPosition + 1));
                openGame.putExtra("levelNum", (itemPosition + 1));
                startActivity(openGame);
                
			}
		
		});
		
		
	}
	
	public void createFile(String fileName){
        FileOutputStream fos = null;
        OutputStreamWriter myOutWriter = null;
        try {
            final File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/type-king/" );

            if (!dir.exists()) {
                dir.mkdirs(); 
            }

            final File myFile = new File(dir, fileName);

            if (!myFile.exists()) {    
                myFile.createNewFile();
                fos = new FileOutputStream(myFile);
                myOutWriter = new OutputStreamWriter(fos);
                
                myOutWriter.append(getWipedProgressString(numLevelsForFile));
                
                myOutWriter.close();
                fos.close();
            } 

        } catch (IOException ex) {
        	String err = (ex.getMessage()==null)?"SD Card failed":ex.getMessage();
			Log.e("sdcard-err2:",err); 
        }
    }
	
	public void readFile(String fileName){
		File sdcard = Environment.getExternalStorageDirectory();

		//Get the text file
		File file = new File(sdcard.getAbsolutePath() + "/type-king/" + fileName);

		//Read text from file

		try {
		    BufferedReader br = new BufferedReader(new FileReader(file));
		    String line;

		    while ((line = br.readLine()) != null) {
		        String[] parts = line.split(" ");
		        completeArray.add(parts[1]);
		        hsArray.add(Integer.parseInt(parts[2].trim()));
		    }
		    br.close();
		}
		catch (IOException ex) {
			String err = (ex.getMessage()==null)?"SD Card failed":ex.getMessage();
			Log.e("sdcard-err2:",err); 
		}
	}
	
	public String getWipedProgressString(int linesForFile){
		String ans = "";
		
		for(int i = 1; i <= linesForFile; i++){
			ans += i + " " + "i 0\n";
		}
		
		return ans;
	}
	
	class MyAdapter<String> extends ArrayAdapter<String>{
		
		String[] myValues;
		
		public MyAdapter(Context context, int resource, int tvResource, String[] values){
			super(context, resource, tvResource, values);
			
			myValues = values.clone();
		}
		
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {  
			View view = super.getView(position, convertView, parent);
			
			boolean completion = false;
			
			TextView text1 = (TextView) view.findViewById(android.R.id.text1);
			TextView text2 = (TextView) view.findViewById(android.R.id.text2);
			
			text1.setText(myValues[position].toString());
			
			if(position >= completeArray.size()){ //ACCOUNTS FOR THE "RANDOM LEVEL" TAB
				text2.setText("Generates a random level");
			}else if(completeArray.get(position).equals("i") && hsArray.get(position) == 0){
				text2.setText("Level Incomplete"); //NEED TO GRAB USER DATA TO SEE IF LEVEL IS COMPLETE.  IF IT IS, FOLLOW FORMAT BELOW
			}else if(completeArray.get(position).equals("c") && hsArray.get(position) != 0){
				completion = true;
				text2.setText("Level Complete - High Score: " + hsArray.get(position));
			}
			
			/*
			 * "Level Complete - High Score: 1250"
			 */
			
			if(!completion){
				if (position < 10) {
					view.setBackgroundColor(Color.parseColor("#F5AB35"));  
				} else if(position < 20) {
					view.setBackgroundColor(Color.parseColor("#E67E22"));  
				} else if(position < 30){
					view.setBackgroundColor(Color.parseColor("#C0392B"));
				} else {
					view.setBackgroundColor(Color.parseColor("#663399"));
				}
			}else
				view.setBackgroundColor(Color.parseColor("#1E824C"));
			return view;  
		}
		
	}

	
	@Override
	public void onBackPressed() {
		Intent gameModes = new Intent("me.amundeep.typeking.GAMEMODES");
		startActivity(gameModes);
	}
	
	
}
