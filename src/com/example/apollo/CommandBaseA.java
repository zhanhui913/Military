package com.example.apollo;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

public class CommandBaseA extends Activity {
	public final static String COUNTRY_BASE = "com.example.apollo.COUNTRY_BASE";
	String country,base;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//Remove title bar (must be placed before the setContentVIew())
		//this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.activity_command_base_a);
		
		handleCountrySpinner();
	}
	
	/**
	 * Handles the Country spinner, will affect Base spinner based on country selected
	 */
	public void handleCountrySpinner(){
    	Spinner spinnerCountry = (Spinner) findViewById(R.id.commandBase_spinner_country);
    	// Create an ArrayAdapter using the string array and a default spinner layout
    	ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
    	        R.array.array_country, android.R.layout.simple_spinner_item);
    	// Specify the layout to use when the list of choices appears
    	adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    	// Apply the adapter to the spinner
    	spinnerCountry.setAdapter(adapter);   	

    	//To listen to user's selection of Division spinner
    	spinnerCountry.setOnItemSelectedListener(new OnItemSelectedListener(){
    		@Override
    		public void onItemSelected(AdapterView<?> arg0,View view,int pos,long id){
    			int location=pos;
    			switch(location){
					case 0:
						country = "";
						setBaseSpinner(country);
						break;
    				case 1:
    					country = "Canada";
    					setBaseSpinner(country);
    					break;
    				case 2:
    					country = "USA";
    					setBaseSpinner(country);
    					break;
    				case 3:
    					country = "England";
    					setBaseSpinner(country);
    					break;
    				case 4:
    					country = "China";
    					setBaseSpinner(country);
    					break;
    			}
    		}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}
    	});	
	}
	
	/**
	 * Sets the different list of Bases in that particular country
	 * @param country
	 */
	public void setBaseSpinner(String country){
	  	int bases = 0;
    	if(country.equals("")){
    		bases = R.array.array_empty;
    	}
    	else if(country.equals("Canada")){
    		bases = R.array.array_canada_base;
    	}
    	else if(country.equals("USA")){
    		bases = R.array.array_usa_base;
    	}
    	else if(country.equals("England")){
    		bases = R.array.array_england_base;
    	}
    	else if(country.equals("China")){
    		bases = R.array.array_china_base;
    	}
		final Spinner spinnerBase = (Spinner) findViewById(R.id.commandBase_spinner_base);
	   	ArrayAdapter<CharSequence> adapterJob = ArrayAdapter.createFromResource(this,
    	        bases, android.R.layout.simple_spinner_item);
    	// Specify the layout to use when the list of choices appears
    	adapterJob.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    	// Apply the adapter to the spinner
    	spinnerBase.setAdapter(adapterJob); 
    	
    	//To listen to user's selection of job spinner
    	spinnerBase.setOnItemSelectedListener(new OnItemSelectedListener(){
    		@Override
    		public void onItemSelected(AdapterView<?> arg0,View view,int pos,long id){
    			base = spinnerBase.getSelectedItem().toString();
    		}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}	
    	});
	}
	
	/**
	 * Go the selected Base's personnel table
	 * @param view
	 */
	@SuppressWarnings("deprecation")
	public void viewBase(View view){
    	if(!country.equals("") && !base.equals("")){
        	Intent intent = new Intent(this, CommandBaseB.class);
        	String info = country+" "+base;
        	intent.putExtra(COUNTRY_BASE, info);
        	startActivity(intent);
    	}
    	else{
    		AlertDialog myAlertDialog = new AlertDialog.Builder(CommandBaseA.this).create();
    		myAlertDialog.setTitle("Alert");
    		myAlertDialog.setMessage("Please select a base");
    		myAlertDialog.setButton("OK", new DialogInterface.OnClickListener() {
    			public void onClick(DialogInterface arg0, int arg1) {
    				// do something when the OK button is clicked
    			}});
    		myAlertDialog.show();
    	}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.command_base, menu);
		return true;
	}

}
