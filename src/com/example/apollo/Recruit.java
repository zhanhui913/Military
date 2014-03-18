package com.example.apollo;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class Recruit extends Activity {

	String country,base,fname,lname,gender,division,role,rank,job;
	public final static String RECRUIT = "com.example.apollo.RECRUIT";
	
	// the class that opens or creates the database and makes SQL calls to it
	DatabaseManager db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//Remove title bar (must be placed before the setContentVIew())
		//this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		//This code is to ensure that when this activity is created, a keyboard would not appear
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		
		setContentView(R.layout.activity_recruit);
		
		handleCountrySpinner();
		
		handleDivisionSpinner();
	}

	/**
	 * Handles the Country spinner, will affect Base spinner based on country selected
	 */
	public void handleCountrySpinner(){
    	Spinner spinnerCountry = (Spinner) findViewById(R.id.recruit_spinner_country);
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
		final Spinner spinnerBase = (Spinner) findViewById(R.id.recruit_spinner_base);
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
     * Called when the user selects a gender, sets gender string to M or F
     * @param view
     */
    public void onRadioButtonGenderClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        
        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.recruit_radio_male:
                if (checked)
                	gender = "M";
                break;
            case R.id.recruit_radio_female:
                if (checked)
                	gender = "F";
                break;
        }
    }
    
    /**
     * Called when the user selects a role, sets role string to Enlist or Officer
     * @param view
     */
    public void onRadioButtonRoleClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        
        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.recruit_radio_officer:
                if (checked)
                	role = "Officer";
                	setJobSpinner(division,role);
                break;
            case R.id.recruit_radio_enlist:
                if (checked)
                	role = "Enlist";
                setJobSpinner(division,role);
                break;
        }
    }
	
    /**
     * Handles division spinner 
     */
    public void handleDivisionSpinner(){
    	Spinner spinner = (Spinner) findViewById(R.id.recruit_spinner_division);
    	// Create an ArrayAdapter using the string array and a default spinner layout
    	ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
    	        R.array.array_division, android.R.layout.simple_spinner_item);
    	// Specify the layout to use when the list of choices appears
    	adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    	// Apply the adapter to the spinner
    	spinner.setAdapter(adapter);   	

    	//To listen to user's selection of Division spinner
    	spinner.setOnItemSelectedListener(new OnItemSelectedListener(){
    		@Override
    		public void onItemSelected(AdapterView<?> arg0,View view,int pos,long id){
    			int location=pos;
    			switch(location){
					case 0:
						division = "";
						setJobSpinner(division,role);
						break;
    				case 1:
    					division = "Army";
    					setJobSpinner(division,role);
    					break;
    				case 2:
    					division = "Air Force";
    					setJobSpinner(division,role);
    					break;
    				case 3:
    					division = "Navy";
    					setJobSpinner(division,role);
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
     * Depending on user's selection on Role and Division, 
     * a different list of jobs will appear on the Jobs spinner
     * @param division
     * @param role
     */
    public void setJobSpinner(String division,String role){
    	int jobs = 0;
    	if(division.equals("")){
    		jobs = R.array.array_empty;
    	}
    	else if(division.equals("Army")){
    		if(role.equals("Enlist")){
    			jobs = R.array.array_army_enlist_jobs;
    			rank = "Private Basic";
    		}
    		else if(role.equals("Officer")){
    			jobs = R.array.array_army_officer_jobs;
    			rank = "Officer Cadet";
    		}
    		else
    			jobs = R.array.array_empty;
    	}
    	else if(division.equals("Air Force")){
    		if(role.equals("Enlist")){
    			jobs = R.array.array_airforce_enlist_jobs;
    			rank = "Private Basic";
    		}
    		else if(role.equals("Officer")){
    			jobs = R.array.array_airforce_officer_jobs;
    			rank = "Officer Cadet";
    		}
    		else
    			jobs = R.array.array_empty;
    	}
    	else if(division.equals("Navy")){
    		if(role.equals("Enlist")){
    			jobs = R.array.array_navy_enlist_jobs;
    			rank = "Ordinary Seaman";
    		}
    		else if(role.equals("Officer")){
    			jobs = R.array.array_navy_officer_jobs;
    			rank = "Naval Cadet";
    		}
    		else
    			jobs = R.array.array_empty;
    	}
		final Spinner spinnerJob = (Spinner) findViewById(R.id.recruit_spinner_jobs);
	   	ArrayAdapter<CharSequence> adapterJob = ArrayAdapter.createFromResource(this,
    	        jobs, android.R.layout.simple_spinner_item);
    	// Specify the layout to use when the list of choices appears
    	adapterJob.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    	// Apply the adapter to the spinner
    	spinnerJob.setAdapter(adapterJob); 
    	
    	//To listen to user's selection of job spinner
    	spinnerJob.setOnItemSelectedListener(new OnItemSelectedListener(){
    		@Override
    		public void onItemSelected(AdapterView<?> arg0,View view,int pos,long id){
    			job = spinnerJob.getSelectedItem().toString();
    		}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}	
    	});
    }
    
    /** 
     * Called when the user clicks the Recruit button 
     * Will not proceed to next page if not all information are filled
     * Will prompt you to complete all information
     * @param view
     */
    @SuppressWarnings("deprecation")
	public void recruit(View view) {
    	EditText editText = (EditText) findViewById(R.id.recruit_first_name);
    	fname = editText.getText().toString();
    	editText = (EditText) findViewById(R.id.recruit_last_name);
    	lname = editText.getText().toString();
    	
    	if(!country.equals("") && !base.equals("") && !fname.equals("") && !lname.equals("") && !gender.equals("") && !division.equals("") && !role.equals("")){
        	Intent intent = new Intent(this, CommandBaseB.class); 
        	String info = country+" "+base;
        	intent.putExtra(RECRUIT, info);
        	
        	addToDatabase();
        	
        	startActivity(intent);
    	}
    	else{
    		AlertDialog myAlertDialog = new AlertDialog.Builder(Recruit.this).create();
    		myAlertDialog.setTitle("Alert");
    		myAlertDialog.setMessage("Please fill in every information");
    		myAlertDialog.setButton("OK", new DialogInterface.OnClickListener() {
    			public void onClick(DialogInterface arg0, int arg1) {
    				// do something when the OK button is clicked
    			}});
    		myAlertDialog.show();
    	}
    }
    
    /**
     * Add newly recruited user to the database
     */
    public void addToDatabase(){
    	// this try catch block returns better error reporting to the log
    	try{
    		// create the database manager object
    		db = new DatabaseManager(this);

    		db.addRow(country, base, fname, lname, gender, division, role, job, rank);

    		db.getSQL().close();
    	}catch(Exception e){
    		Log.e("ERROR", e.toString());
    		e.printStackTrace();
    	}
    }
    
    /**
     * Called when user clicks the Clear button
     * Will clear all information and set to default
     * @param view
     */
    public void clear(View view){
    	country="";base="";fname="";lname="";gender="";role="";division="";job="";rank="";
    	
    	Spinner country = (Spinner) findViewById(R.id.recruit_spinner_country);
    	country.setSelection(0);
    	
    	Spinner base = (Spinner) findViewById(R.id.recruit_spinner_base);
    	base.setSelection(0);
    	
    	EditText editText = (EditText) findViewById(R.id.recruit_first_name);
    	editText.setText("");
    	editText = (EditText)findViewById(R.id.recruit_last_name);
    	editText.setText("");
    	
    	RadioGroup genderGroup= (RadioGroup) findViewById(R.id.recruit_gender);
    	genderGroup.clearCheck();
    	
    	RadioGroup roleGroup= (RadioGroup) findViewById(R.id.recruit_role);
    	roleGroup.clearCheck();   	
    	
    	Spinner division = (Spinner) findViewById(R.id.recruit_spinner_division);
    	division.setSelection(0);
    	
    	Spinner job = (Spinner) findViewById(R.id.recruit_spinner_jobs);
    	job.setSelection(0);
    }
  
    /**
     * Called when user clicks the View Base button
     * Will bring user to that specific command base with all personnel located at that base
     * Will prompt user to select base if no base is selected
     * @param view
     */
    @SuppressWarnings("deprecation")
	public void viewBase(View view){	
    	if(!country.equals("") && !base.equals("")){
        	Intent intent = new Intent(this, CommandBaseB.class);
        	String info = country+" "+base;
        	System.out.println("Recruit = "+country+" ");
        	intent.putExtra(RECRUIT, info);
        	startActivity(intent);
    	}
    	else{
    		AlertDialog myAlertDialog = new AlertDialog.Builder(Recruit.this).create();
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
		getMenuInflater().inflate(R.menu.recruit, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent;
		switch (item.getItemId()) {
		case R.id.menu_home:
			Toast.makeText(Recruit.this, "Home is Selected", Toast.LENGTH_SHORT).show();
			intent = new Intent(this,Home.class);
			startActivity(intent);
			return true;
		case R.id.menu_buy_asset:
			Toast.makeText(Recruit.this, "Buy Asset is Selected", Toast.LENGTH_SHORT).show();
			return true;
		case R.id.menu_asset_statistic:
			Toast.makeText(Recruit.this, "Asset Statistic is Selected", Toast.LENGTH_SHORT).show();
			return true;
		case R.id.menu_personnel_statistic:
			Toast.makeText(Recruit.this, "Personnel Statistic is Selected", Toast.LENGTH_SHORT).show();
			return true;
		case R.id.menu_command_post:
			Toast.makeText(Recruit.this, "Command Post is Selected", Toast.LENGTH_SHORT).show();
			intent = new Intent(this, CommandBaseA.class);
			startActivity(intent);
			return true;
		case R.id.menu_declare_war:
			Toast.makeText(Recruit.this, "Declare War is Selected", Toast.LENGTH_SHORT).show();
			return true;
		case R.id.menu_research:
			Toast.makeText(Recruit.this, "Research is Selected", Toast.LENGTH_SHORT).show();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
}
