package com.example.apollo;

import java.util.ArrayList;
import java.util.StringTokenizer;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
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

public class RecruitInfo extends Activity {

	String country,base,fname,lname,gender,division,role,job,rank;
	
	Spinner jobSpinner,divisionSpinner,rankSpinner;
	
	ArrayAdapter<CharSequence> divisionAdapter,jobAdapter,rankAdapter;
	
	EditText editText_fname,editText_lname;
	
	DatabaseManager db;

	//Index for the selected recruit according to Database (starts at position 1 not 0)
	String index;
	
	//TO make sure onItemSelected is not called when activity is created
	int counter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//Remove title bar (must be placed before the setContentVIew())
		//this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.activity_recruit_info);
		
		//This code is to ensure that when this activity is created, a keyboard would not appear
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		
		//Create database
		db = new DatabaseManager(this);
		
		//Put values back into fields
		putValue();

		handleDivisionSpinner();
		
		//Whenever this activity is created, counter integer sets to zero
		counter=0;
	}

	/**
	 * Put all information into its corresponding fields
	 */
	public void putValue(){
		getValues();
	    
		putNames();
	    
		putDivision();
	    
		putGender();
		
		putRole();
		
		//No need for putJob() and putRank() because setJobAndRankSpinner() takes care of initial set up and handles changes
	}
	
	/**
	 * Get information such as first name,last name,gender,role,division & job from previous activitiy's intent
	 */
	public void getValues(){
		//Get values
		Intent intent = getIntent();
		String message = intent.getStringExtra(CommandBaseB.RECRUIT_INFO);
	    StringTokenizer st = new StringTokenizer(message, "=;"); 
	    while(st.hasMoreTokens()) { 
	    	index= st.nextToken();
	    	country= st.nextToken();
	    	base = st.nextToken();
	    	fname = st.nextToken(); 
	    	lname = st.nextToken();
	    	gender = st.nextToken();
	    	division = st.nextToken();
	    	role = st.nextToken();
	    	job = st.nextToken();
	    	rank = st.nextToken();
	    } 
	}
	
	
	/**
	 * Put the recruit's first and last name into its corresponding editText field
	 */
	public void putNames(){
	    editText_fname = (EditText)findViewById(R.id.recruitInfo_first_name);
	    editText_fname.setText(fname);
	    editText_lname = (EditText)findViewById(R.id.recruitInfo_last_name);
	    editText_lname.setText(lname);
	}
	
	/**
	 * Put the recruit's division into the division's spinner value
	 */
	public void putDivision(){
	    divisionSpinner = (Spinner)findViewById(R.id.recruitInfo_division);
    	// Create an ArrayAdapter using the string array and a default spinner layout
    	divisionAdapter = ArrayAdapter.createFromResource(this,
    	        R.array.array_division, android.R.layout.simple_spinner_item);
    	// Specify the layout to use when the list of choices appears
    	divisionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    	// Apply the adapter to the spinner
    	divisionSpinner.setAdapter(divisionAdapter);   
	    
	    if(division.equals("Army")){
	    	divisionSpinner.setSelection(1);
	    }else if(division.equals("Air Force")){
	    	divisionSpinner.setSelection(2);
	    }else if(division.equals("Navy")){
	    	divisionSpinner.setSelection(3);
	    }
	}
	
	/**
	 * Put the recruit's gender into the gender's Radio Button
	 */
	public void putGender(){
    	RadioGroup genderGroup= (RadioGroup) findViewById(R.id.recruitInfo_gender);
    	if(gender.equals("M")){
    		genderGroup.check(R.id.recruitInfo_male);
    	}
    	else if(gender.equals("F")){
    		genderGroup.check(R.id.recruitInfo_female); 		
    	}
	}
	
	/**
	 * Put the recruit's role into the role's Radio Button
	 */
	public void putRole(){
    	RadioGroup roleGroup= (RadioGroup) findViewById(R.id.recruitInfo_role);
    	if(role.equals("Enlist")){
    		roleGroup.check(R.id.recruitInfo_enlist);
    	}
    	else if(role.equals("Officer")){
    		roleGroup.check(R.id.recruitInfo_officer); 		
    	}
	}
	
	/*********************************************************************************************************************************************************/
	//Everything below this code are codes that handles the changes the user make
	
	/**
     * called when the user selects a gender
     * @param view
     */
    public void onRadioButtonGenderClickedRecruit(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        
        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.recruitInfo_male:
                if (checked)
                	gender = "M";
                break;
            case R.id.recruitInfo_female:
                if (checked)
                	gender = "F";
                break;
        }
    }
	
    /**
     * called when the user selects a role
     * @param view
     */
    public void onRadioButtonRoleClickedRecruit(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        
        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.recruitInfo_officer:
                if (checked)
                		role = "Officer";
                		setJobAndRankSpinner(division,role);
                	break;
            case R.id.recruitInfo_enlist:
                if (checked)
                		role = "Enlist";
                		setJobAndRankSpinner(division,role);
                	break;
        }
    }
	
    /**
     * Handles division spinner
     */
    public void handleDivisionSpinner(){
    	//To listen to user's changes in selection of Division spinner
    	divisionSpinner.setOnItemSelectedListener(new OnItemSelectedListener(){
    		@Override
    		public void onItemSelected(AdapterView<?> arg0,View view,int pos,long id){
    			int location=pos;
    			switch(location){
    			case 0:
    				division = "";
    				setJobAndRankSpinner(division,role);
    				break;
    			case 1:
    				division = "Army";
    				setJobAndRankSpinner(division,role);
    				break;
    			case 2:
    				division = "Air Force";
    				setJobAndRankSpinner(division,role);
    				break;
    			case 3:
    				division = "Navy";
    				setJobAndRankSpinner(division,role);
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
     * and a different list of ranks will appear on the Ranks spinner
     * @param division
     * @param role
     */
    public void setJobAndRankSpinner(String division,String role){
    	jobSpinner = (Spinner) findViewById(R.id.recruitInfo_jobs);
    	rankSpinner = (Spinner)findViewById(R.id.recruitInfo_rank);
    	
    	int jobs = 0;
    	int ranks =0;
    	if(division.equals("")){
    		jobs = R.array.array_empty;
    	}
    	else if(division.equals("Army")){
    		if(role.equals("Enlist")){
    			jobs = R.array.array_army_enlist_jobs;
    			ranks = R.array.array_rank_enlist_army_and_airforce;
    		}
    		else if(role.equals("Officer")){
    			jobs = R.array.array_army_officer_jobs;
    			ranks = R.array.array_rank_officer_army_and_airforce;
    		}
    		else
    			jobs = R.array.array_empty;
    	}
    	else if(division.equals("Air Force")){
    		if(role.equals("Enlist")){
    			jobs = R.array.array_airforce_enlist_jobs;
    			ranks = R.array.array_rank_enlist_army_and_airforce;
    		}
    		else if(role.equals("Officer")){
    			jobs = R.array.array_airforce_officer_jobs;
    			ranks = R.array.array_rank_officer_army_and_airforce;
    		}
    		else
    			jobs = R.array.array_empty;
    	}
    	else if(division.equals("Navy")){
    		if(role.equals("Enlist")){
    			jobs = R.array.array_navy_enlist_jobs;
    			ranks = R.array.array_rank_enlist_navy;
    		}
    		else if(role.equals("Officer")){
    			jobs = R.array.array_navy_officer_jobs;
    			ranks = R.array.array_rank_officer_navy;
    		}
    		else
    			jobs = R.array.array_empty;
    	}

		jobAdapter = ArrayAdapter.createFromResource(this,jobs, android.R.layout.simple_spinner_item);
    	// Specify the layout to use when the list of choices appears
    	jobAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    	// Apply the adapter to the spinner
    	jobSpinner.setAdapter(jobAdapter);  
  
    	//To listen to user's selection of job spinner
    	jobSpinner.setOnItemSelectedListener(new OnItemSelectedListener(){
    		@Override
    		public void onItemSelected(AdapterView<?> arg0,View view,int pos,long id){
    			//WHen activity is first instantiated, it will by default call this.
    			//Do not change job value when the activity is first instantiated
    			if(counter>0){
    				job = jobSpinner.getSelectedItem().toString();
    			}
    			counter++;
    		}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}
			
    	});
    	//Now that the specific job array is done setting up along with touch input.
    	//Can finally put the correct job value 
    	findSpecificJob(division,role,job);
    	
    	
    	rankAdapter = ArrayAdapter.createFromResource(this,ranks,android.R.layout.simple_spinner_item); 
    	rankAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    	rankSpinner.setAdapter(rankAdapter);    
    	
    	//Listen to user's selection of rank spinner
    	rankSpinner.setOnItemSelectedListener(new OnItemSelectedListener(){
			@Override
			public void onItemSelected(AdapterView<?> arg, View view,int pos, long id) {
    			//WHen activity is first instantiated, it will by default call this.
    			//Do not change job value when the activity is first instantiated
    			if(counter>0){
    				rank = rankSpinner.getSelectedItem().toString();
    			}
    			counter++;
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
    	});
    	
    	//Now that rank spinner is done setting up along with touch input
    	//Can finally set the correct rank value
    	findSpecificRank(division,role,rank);
    }
	
    
	/**
	 * Set the job spinner to a specific job(J) according to division(D) & role(R)
	 * @param d
	 * @param r
	 * @param j
	 */
	public void findSpecificJob(String d,String r,String j){
		if(d.equals("Army")){
			if(r.equals("Officer")){
				if(j.equals("Armoured Officer")){
					jobSpinner.setSelection(0);
				}
				if(j.equals("Artillery Officer")){
					jobSpinner.setSelection(1);
				}
				if(j.equals("Bioscience Officer")){
					jobSpinner.setSelection(2);
				}
				if(j.equals("Chaplain")){
					jobSpinner.setSelection(3);
				}
				if(j.equals("Dental Officer")){
					jobSpinner.setSelection(4);
				}
				if(j.equals("Engineer Officer")){
					jobSpinner.setSelection(5);
				}
				if(j.equals("Infantry Officer")){
					jobSpinner.setSelection(6);
				}
				if(j.equals("Intelligence Officer")){
					jobSpinner.setSelection(7);
				}
				if(j.equals("Legal Officer")){
					jobSpinner.setSelection(8);
				}
				if(j.equals("Logistics Officer")){
					jobSpinner.setSelection(9);
				}
				if(j.equals("Medical Officer")){
					jobSpinner.setSelection(10);
				}
				if(j.equals("Military Police Officer")){
					jobSpinner.setSelection(11);
				}
				if(j.equals("Nursing Officer")){
					jobSpinner.setSelection(12);
				}
				if(j.equals("Signals Officer")){
					jobSpinner.setSelection(13);
				}
			}else if(r.equals("Enlist")){
				if(j.equals("Ammunition Technician")){
					jobSpinner.setSelection(0);
				}
				if(j.equals("Armoured Soldier")){
					jobSpinner.setSelection(1);
				}
				if(j.equals("Artillery Soldier")){
					jobSpinner.setSelection(2);
				}
				if(j.equals("Combat Engineer")){
					jobSpinner.setSelection(3);
				}
				if(j.equals("Cook")){
					jobSpinner.setSelection(4);
				}
				if(j.equals("Infantry Soldier")){
					jobSpinner.setSelection(5);
				}
				if(j.equals("Intelligence Operator")){
					jobSpinner.setSelection(6);
				}
				if(j.equals("Medical Technician")){
					jobSpinner.setSelection(7);
				}
				if(j.equals("Military Police")){
					jobSpinner.setSelection(8);
				}
			}
		}else if(d.equals("Air Force")){
			if(r.equals("Officer")){
				if(j.equals("Aerospace Control Officer")){
					jobSpinner.setSelection(0);
				}
				if(j.equals("Air Combat Officer")){
					jobSpinner.setSelection(1);
				}
				if(j.equals("Bioscience Officer")){
					jobSpinner.setSelection(2);
				}
				if(j.equals("Chaplain")){
					jobSpinner.setSelection(3);
				}
				if(j.equals("Intelligence Officer")){
					jobSpinner.setSelection(4);
				}
				if(j.equals("Legal Officer")){
					jobSpinner.setSelection(5);
				}
				if(j.equals("Logistics Officer")){
					jobSpinner.setSelection(6);
				}
				if(j.equals("Medical Officer")){
					jobSpinner.setSelection(7);
				}
				if(j.equals("Military Police Officer")){
					jobSpinner.setSelection(8);
				}
				if(j.equals("Nursing Officer")){
					jobSpinner.setSelection(9);
				}
				if(j.equals("Pilot")){
					jobSpinner.setSelection(10);
				}
			}else if(r.equals("Enlist")){
				if(j.equals("Aerospace Control Operator")){
					jobSpinner.setSelection(0);
				}
				if(j.equals("Avionics Technician")){
					jobSpinner.setSelection(1);
				}
				if(j.equals("Cook")){
					jobSpinner.setSelection(2);
				}
				if(j.equals("Intelligence Operator")){
					jobSpinner.setSelection(3);
				}
				if(j.equals("Medical Technician")){
					jobSpinner.setSelection(4);
				}
				if(j.equals("Military Police")){
					jobSpinner.setSelection(5);
				}
			}	
		}else if(d.equals("Navy")){
			if(r.equals("Officer")){
				if(j.equals("Bioscience Officer")){
					jobSpinner.setSelection(0);
				}
				if(j.equals("Chaplain")){
					jobSpinner.setSelection(1);
				}
				if(j.equals("Intelligence Officer")){
					jobSpinner.setSelection(2);
				}
				if(j.equals("Legal Officer")){
					jobSpinner.setSelection(3);
				}
				if(j.equals("Logistics Officer")){
					jobSpinner.setSelection(4);
				}
				if(j.equals("Maritime Officer")){
					jobSpinner.setSelection(5);
				}
				if(j.equals("Medical Officer")){
					jobSpinner.setSelection(6);
				}
				if(j.equals("Military Police Officer")){
					jobSpinner.setSelection(7);
				}
				if(j.equals("Naval Combat Officer")){
					jobSpinner.setSelection(8);
				}
				if(j.equals("Nursing Officer")){
					jobSpinner.setSelection(9);
				}
			}else if(r.equals("Enlist")){
				if(j.equals("Boatswain")){
					jobSpinner.setSelection(0);
				}
				if(j.equals("Communicator")){
					jobSpinner.setSelection(1);
				}
				if(j.equals("Cook")){
					jobSpinner.setSelection(2);
				}
				if(j.equals("Hull Technician")){
					jobSpinner.setSelection(3);
				}
				if(j.equals("Intelligence Operator")){
					jobSpinner.setSelection(4);
				}
				if(j.equals("Marine Engineer")){
					jobSpinner.setSelection(5);
				}
				if(j.equals("Medical Technician")){
					jobSpinner.setSelection(6);
				}
				if(j.equals("Military Police")){
					jobSpinner.setSelection(7);
				}
				if(j.equals("Naval Communicator")){
					jobSpinner.setSelection(8);
				}
				if(j.equals("Sonar Operator")){
					jobSpinner.setSelection(9);
				}
				if(j.equals("Steward")){
					jobSpinner.setSelection(10);
				}
			}
		}		
	}
    
	/**
	 * Set the rank spinner to a specific rank(RA) according to division(D) & role(RO)
	 * @param d
	 * @param ro
	 * @param ra
	 */
    public void findSpecificRank(String d,String ro,String ra){
    	if(d.equals("Army")||d.equals("Air Force")){
    		if(ro.equals("Enlist")){
    			if(ra.equals("Private Basic")){
    				rankSpinner.setSelection(0);
    			}
    			if(ra.equals("Private")){
    				rankSpinner.setSelection(1);
    			}
    			if(ra.equals("Corporal")){
    				rankSpinner.setSelection(2);
    			}
    			if(ra.equals("Master Corporal")){
    				rankSpinner.setSelection(3);
    			}
    			if(ra.equals("Sergeant")){
    				rankSpinner.setSelection(4);
    			}
    			if(ra.equals("Warrant Officer")){
    				rankSpinner.setSelection(5);
    			}
    			if(ra.equals("Master Warrant Officer")){
    				rankSpinner.setSelection(6);
    			}
    			if(ra.equals("Chief Warrant Officer")){
    				rankSpinner.setSelection(7);
    			}
    		}
    		else if(ro.equals("Officer")){
    			if(ra.equals("Officer Cadet")){
    				rankSpinner.setSelection(0);
    			}
    			if(ra.equals("Second Lieutenant")){
    				rankSpinner.setSelection(1);
    			}
    			if(ra.equals("Lieutenant")){
    				rankSpinner.setSelection(2);
    			}
    			if(ra.equals("Captain")){
    				rankSpinner.setSelection(3);
    			}
    			if(ra.equals("Major")){
    				rankSpinner.setSelection(4);
    			}
    			if(ra.equals("Lieutenant Colonel")){
    				rankSpinner.setSelection(5);
    			}
    			if(ra.equals("Colonel")){
    				rankSpinner.setSelection(6);
    			}
    		}
    	}
    	else if(d.equals("Navy")){
    		if(ro.equals("Enlist")){
    			if(ra.equals("Ordinary Seaman")){
    				rankSpinner.setSelection(0);
    			}
    			if(ra.equals("Able Seaman")){
    				rankSpinner.setSelection(1);
    			}
    			if(ra.equals("Leading Seaman")){
    				rankSpinner.setSelection(2);
    			}
    			if(ra.equals("Master Seaman")){
    				rankSpinner.setSelection(3);
    			}
    			if(ra.equals("Petty Officer 2nd Class")){
    				rankSpinner.setSelection(4);
    			}
    			if(ra.equals("Petty Officer 1st Class")){
    				rankSpinner.setSelection(5);
    			}
    			if(ra.equals("Chief Petty Officer 2nd Class")){
    				rankSpinner.setSelection(6);
    			}
    			if(ra.equals("Chief Petty Officer 1st Class")){
    				rankSpinner.setSelection(7);
    			}
    		}
    		else if(ro.equals("Officer")){
    			if(ra.equals("Naval Cadet")){
    				rankSpinner.setSelection(0);
    			}
    			if(ra.equals("Acting Sub-Lieutenant")){
    				rankSpinner.setSelection(1);
    			}
    			if(ra.equals("Sub-Lieutenant")){
    				rankSpinner.setSelection(2);
    			}
    			if(ra.equals("Lieutenant")){
    				rankSpinner.setSelection(3);
    			}
    			if(ra.equals("Lieutenant Commander")){
    				rankSpinner.setSelection(4);
    			}
    			if(ra.equals("Commander")){
    				rankSpinner.setSelection(5);
    			}
    			if(ra.equals("Captain")){
    				rankSpinner.setSelection(6);
    			}
    		}
    	}
    }
    
    
	/**
	 * When the user save changes made to the recruit's information
	 * It will update the row in the database according the recruit's ID that is hidden
	 * @param view
	 */
	public void save(View view){
    	try{
    		// ask the database manager to update the row based on the information found in the corresponding user entry fields
    		db.updateRow(Long.parseLong(index), country, base, editText_fname.getText().toString(), editText_lname.getText().toString(), gender, division, role, job, rank);
 
    		// request the table be updated
			updateTable();
    	}
    	catch (Exception e)
    	{
    		Log.e("Update Error", e.toString());
    		e.printStackTrace();
    	}
    	
    	//Finish this activity, go back to previous one
    	finish();
	}
	
	/**
	 * When the user discharge a recruit, it will remove him/her from the database
	 * @param view
	 */
	public void discharge(View view){
    	try{
    		// ask the database manager to delete the row based on the information found in the corresponding user entry fields
    		db.deleteRow(Long.parseLong(index));
    		// request the table be updated
			updateTable();
			
			Toast.makeText(RecruitInfo.this,"Discharged : "+fname+" "+lname, Toast.LENGTH_SHORT).show();
    	}
    	catch (Exception e)
    	{
    		Log.e("Update Error", e.toString());
    		e.printStackTrace();
    	}
    	
    	//Finish this activity, go back to previous one
    	finish();
	}
	
    /**
     * Does nothing but print out the information in the database
     */
    private void updateTable(){
    	// collect the current row information from the database and
    	// store it in a two dimensional ArrayList
    	ArrayList<ArrayList<Object>> data = db.getAllRowsAsArrays();
    	System.out.println("Calling from updateTable() at RecruitInfo");
    	System.out.println("Whats in the database\n");
    	// iterate the ArrayList, create new rows each time and add them to listview
    	for (int position=0; position < data.size(); position++){
    		ArrayList<Object> row = data.get(position);
    		System.out.println(row.get(0).toString()+" "+row.get(1).toString()+" "+row.get(2).toString()+" "+row.get(3).toString()+" "+row.get(4).toString()+" "+row.get(5).toString()+" "+row.get(6).toString()+" "+row.get(7).toString()+" "+row.get(8).toString()+" "+row.get(9).toString());	   		
    	}
    	System.out.println("\nEnd of whats in the database");
    }   
    
    /***************************************************************************************************************************/
    //Everything below this are standard override functions
    
	@Override
	protected void onStop(){
		super.onStop();  // Always call the superclass method first
		
		//Must always close connection to database
		db.getSQL().close();
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.recruit_info, menu);
		return true;
	}
}