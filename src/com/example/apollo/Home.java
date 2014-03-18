package com.example.apollo;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

public class Home extends Activity {

	// the class that opens or creates the database and makes SQL calls to it
	DatabaseManager db;
	
	int army=0,air=0,navy=0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//Remove title bar (must be placed before the setContentVIew())
		//this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        
		setContentView(R.layout.activity_home);
		
		//Create database
		db = new DatabaseManager(this);
		
		updateTotalPersonnelCount();
		
		
	}
	
	public void checkTime(){
		long start = System.nanoTime();    		

		//Method to check time
		
    	long elapsedTime = System.nanoTime() - start;
    	float millisecond = (float)elapsedTime/(1000000);
    	System.out.println("Time took "+millisecond+" milliseconds");
    	
	}
	

	public void updateTotalPersonnelCount(){
		
    	ArrayList<ArrayList<Object>> data = db.getAllRowsAsArrays();
 
    	// iterate the ArrayList, count the number of army, air force and navy total personnel
    	for (int position=0; position < data.size(); position++){
    		ArrayList<Object> row = data.get(position);
    		if(row.get(6).toString().equals("Army")){
    			army++;
    		}else if(row.get(6).toString().equals("Air Force")){
    			air++;
    		}else if(row.get(6).toString().equals("Navy")){
    			navy++;
    		}
    	}

    	//Changing the number in the text
    	TextView armyText = (TextView)findViewById(R.id.home_col_army_number);
    	armyText.setText(army+"");
    	
    	TextView airText = (TextView)findViewById(R.id.home_col_air_number);
    	airText.setText(air+"");
    	
    	TextView navyText = (TextView)findViewById(R.id.home_col_navy_number);
    	navyText.setText(navy+"");
    	
    	//Close connection
    	db.getSQL().close();
	}
	
	@Override
	protected void onRestart() {
		army=0;air=0;navy=0;
	    super.onRestart();  // Always call the superclass method first
	    
	    // Restart connection to SQL Database
	    db = new DatabaseManager(this);

	    //Refresh the count list
	    updateTotalPersonnelCount();
	}	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent;
		switch (item.getItemId()) {
		case R.id.menu_buy_asset:
			Toast.makeText(Home.this, "Buy Asset is Selected", Toast.LENGTH_SHORT).show();
			return true;
		case R.id.menu_recruit:
			Toast.makeText(Home.this, "Recruit is Selected", Toast.LENGTH_SHORT).show();
			intent = new Intent(this, Recruit.class);
			startActivity(intent);
			return true;
		case R.id.menu_asset_statistic:
			Toast.makeText(Home.this, "Asset Statistic is Selected", Toast.LENGTH_SHORT).show();
			return true;
		case R.id.menu_personnel_statistic:
			Toast.makeText(Home.this, "Personnel Statistic is Selected", Toast.LENGTH_SHORT).show();
			return true;
		case R.id.menu_command_post:
			Toast.makeText(Home.this, "Command Post is Selected", Toast.LENGTH_SHORT).show();
			intent = new Intent(this, CommandBaseA.class);
			startActivity(intent);
			return true;
		case R.id.menu_declare_war:
			Toast.makeText(Home.this, "Declare War is Selected", Toast.LENGTH_SHORT).show();
			return true;
		case R.id.menu_research:
			Toast.makeText(Home.this, "Research is Selected", Toast.LENGTH_SHORT).show();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
}
