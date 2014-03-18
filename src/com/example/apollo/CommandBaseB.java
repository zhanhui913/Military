package com.example.apollo;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.NavUtils;

public class CommandBaseB extends ListActivity {

	public final static String RECRUIT_INFO = "com.example.apollo.INFO";
	
	// the class that opens or creates the database and makes SQL calls to it
	DatabaseManager db;
	
	//Values that will be added to the database and that is retrieved from previous activity
	String country,base;
	
	//For the listView
	ArrayList<String> listItems=new ArrayList<String>();
	MyCustomAdapter adapter;
	
	//For the hidden buttons
	ImageButton deleteButton, doneButton;
	
	//Boolean to tell whether the user is in delete list state or not
	String deleteState="false";
	
	//ArrayList of boolean status of whether the item is selected or not
	ArrayList<ListViewBoolean> status;
	ListViewBoolean b;
	
	//Blue color
	int blueColor = Color.rgb(92, 172, 238);
	//White color
	int whiteColor = Color.rgb(255,255,255);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//Remove title bar (must be placed before the setContentVIew())
		//this.requestWindowFeature(Window.FEATURE_NO_TITLE);
				
		setContentView(R.layout.activity_command_base_b);

		
		//This adapter basically create the listView using listView_text as each of its rows
        adapter=new MyCustomAdapter(this,R.layout.listview_text,listItems);
        setListAdapter(adapter);

        //Create database
        db = new DatabaseManager(this);
        
        //Get country and base name
        getCountryBase();
        
        //Fetch items from database and add to the list base on country and base
		addList();
        
		//Initially all listItems' status are false
		status=new ArrayList<ListViewBoolean>();
		for(int i =0;i< listItems.size();i++){		
			b=new ListViewBoolean();
			status.add(b);
			status.get(i).setBoolean("false");
		}
		
		handleLongClick();
	}

	/**
	 * Fetching the country and base name from previous activity that called this activity
	 */
	public void getCountryBase(){
		String a = getIntent().getStringExtra(CommandBaseA.COUNTRY_BASE);
		String b = getIntent().getStringExtra(Recruit.RECRUIT);
		
		if(a!=null){
			System.out.println("A");
			String aa[] = a.split(" ",2);
			country = aa[0];
			base = aa[1];			
		}
		if (b!=null){
			System.out.println("B");
			String bb[] = b.split(" ",2);
			country = bb[0];
			base = bb[1];				
		}
		
		//Set the title as the country's base name
		TextView label = (TextView)findViewById(R.id.country_label);
		label.setText(country+" : "+base);
		
	}
	
    @Override 
    public void onListItemClick(ListView l, View v, int position, long id) {
    	if(deleteState.equals("false")){
    		long start2 = System.nanoTime();   
        	
    		//Get the ID from itemList so that we can directly call getRowAsArray(ID) which saves time from calling GetAllRowAsArray(ID)
    		String arr[] = listItems.get(position).split(" ", 2);
    		int firstWord = Integer.parseInt(arr[0]);
    		ArrayList<Object> row= db.getRowAsArray(firstWord);
    		System.out.println("ID = "+firstWord+" "+row.get(3).toString()+" "+row.get(4).toString());
    		
        	long elapsedTime2 = System.nanoTime() - start2;
        	float millisecond2 = (float)elapsedTime2/(1000000);
        	System.out.println("Time took "+millisecond2+" milliseconds");
        	
        	
        	//Create an intent to store data so that the next activity that will be called can receive the data
        	Intent intent = new Intent(this, RecruitInfo.class);
        	
        	String info = row.get(0).toString()+";"+row.get(1).toString()+";"+row.get(2).toString()+";"+row.get(3).toString()+";"+row.get(4).toString()+";"+row.get(5).toString()+";"+row.get(6).toString()+";"+row.get(7).toString()+";"+row.get(8).toString()+";"+row.get(9).toString();
        	intent.putExtra(RECRUIT_INFO, info);
        	startActivity(intent);    		
    	}
    	else if(deleteState.equals("true")){
    		toggle(v,position);
    	}
    }
	
	/**
	 * Handles long click in the list view
	 */
	private void handleLongClick(){
		//To enable long clicks
		this.getListView().setLongClickable(true);

		this.getListView().setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

				//Un-hides the hidden buttons
				handleHiddenButton();
				
				//Entering deleteState
				deleteState="true";
				
				toggle(view,position);

				//Returns true if you have handle the event and should stop here
				//Returns false if you have not handle the event and will proceed to any other on-click listeners
				return true;
			}
		});		
	}
    
    /**
     * Act as my custom checkbox
     * @param position
     */
    public void toggle(View v,int position){
    	if(status.get(position).getBoolean().equals("false")){
    		v.setBackgroundColor(blueColor);
    		status.get(position).setBoolean("true");
    	}
    	else if(status.get(position).getBoolean().equals("true")){
    		v.setBackgroundColor(whiteColor);
    		status.get(position).setBoolean("false");
    	}
    }
    
    /**
     * Adds to the list view according to the information from the database.
     */
    private void addList(){
    	// collect the current row information from the database and store it in a two dimensional ArrayList
    	ArrayList<ArrayList<Object>> data = db.getBaseRowAsArray(country,base);
    	ArrayList<Object> row;
    	System.out.println("Before adding listItems size is "+listItems.size());
    	// iterate the ArrayList, create new rows each time and add them to list view
    	for (int position=0; position < data.size(); position++){
    		row = data.get(position);
    		System.out.println("Name = "+row.get(3).toString()+" "+row.get(4).toString());
    		listItems.add(row.get(0).toString()+" "+row.get(3).toString()+" "+row.get(4).toString()); 
    	}
    	adapter.notifyDataSetChanged();
    	
    	System.out.println("ListItems after adding size ="+listItems.size());	
    }
	
    /**
     * Deletes every row from the list view
     */
    private void deleteList(){
    	//Every time I delete from listItems, the listItems.size() gets smaller
    	//So the integer i should remain at 0 to remove everything
    	for(int i=0;i<listItems.size();i++){
    		listItems.remove(i);
    		i--;
    	}
    }
    
    /**
     * Refresh the list entirely
     */
    private void refreshList(){
    	deleteList();
    	addList();
    }
    
	/**
	 * First it will set the deleteButton and doneButton to visible,
	 * then it handles the listener for deleteButton & doneButton 
	 */
	public void handleHiddenButton(){
		deleteButton = (ImageButton)findViewById(R.id.imageButton_delete);
		deleteButton.setVisibility(View.VISIBLE);
		deleteButton.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				handleDelete();
			}
		});
		doneButton = (ImageButton)findViewById(R.id.imageButton_done);
		doneButton.setVisibility(View.VISIBLE);
		doneButton.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				handleDone();
			}
		});
	}

	/**
	 * Handles the hidden delete button
	 */
	public void handleDelete(){
		//Start deleting selected rows if any is selected

		// collect the current row information from the database and store it in a two dimensional ArrayList
		ArrayList<ArrayList<Object>> data = db.getAllRowsAsArrays();

		//Go through the boolean list and delete all that is true
		for(int i=0;i<listItems.size();i++){
			//If the boolean is true, that means the user has selected it for deletion
			if(status.get(i).getBoolean().equals("true")){
				ArrayList<Object> row = data.get(i);
				db.deleteRow(Long.parseLong(row.get(0).toString()));
				//Show user which recruit is discharged from military
				Toast.makeText(CommandBaseB.this,
						"Discharged : "+row.get(3).toString()+" "+row.get(4).toString(), Toast.LENGTH_SHORT).show();
			}
		}

		//Refresh the list
		refreshList();

		//Once deleted items, deleteState will be false 
		deleteState="false";

		//Call onRestart to change view's color back to white and all the booleans back to false
		onRestart();

		//Change visibility back to invisible
		deleteButton.setVisibility(View.INVISIBLE);
		doneButton.setVisibility(View.INVISIBLE);
	}
	
	/**
	 * Handles the hidden done button
	 */
	public void handleDone(){
		   //Once done, deleteState will be false 
		   deleteState="false";
		   
		   //Call onRestart to change view's color back to white and all the booleans back to false
		   onRestart();
		   
		   //Change visibility back to invisible
		   deleteButton.setVisibility(View.INVISIBLE);
		   doneButton.setVisibility(View.INVISIBLE);
	}
    
    /***************************************************************************************************************************/
    //Everything below this are standard override functions
	


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.command_base_b, menu);
		return true;
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
			
		case R.id.menu_save:
	           Toast.makeText(CommandBaseB.this, "Save is Selected", Toast.LENGTH_SHORT).show();
	            return true;
	            
		case R.id.menu_delete:
				//Un-hides the hidden button
				handleHiddenButton();	
				Toast.makeText(CommandBaseB.this, "Delete is Selected", Toast.LENGTH_SHORT).show();
	            deleteState="true";
				return true;
				
		case R.id.menu_research:
				calls();
				Toast.makeText(CommandBaseB.this, "Research is Selected", Toast.LENGTH_SHORT).show();
	            return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	//Testing only
	public void calls(){
    	ArrayList<ArrayList<Object>> data = db.getBaseRowAsArray("Canada","Toronto");
    	System.out.println("Calling from calls() at CommandBaseB");
    	System.out.println("Whats in the database\n");
    	// iterate the ArrayList, create new rows each time and add them to listview
    	for (int position=0; position < data.size(); position++){
    		ArrayList<Object> row = data.get(position);
    		System.out.println(row.get(0).toString()+" "+row.get(1).toString()+" "+row.get(2).toString()+" "+row.get(3).toString()+" "+row.get(4).toString()+" "+row.get(5).toString()+" "+row.get(6).toString()+" "+row.get(7).toString()+" "+row.get(8).toString()+" "+row.get(9).toString());	   		
    	}
    	System.out.println("\nEnd of whats in the database");
	}
	
	
	@Override
	protected void onStop(){
		super.onStop();  // Always call the superclass method first
		
		//Must always close connection to database when leaving the page
		db.getSQL().close();
	}
	
	@Override
	protected void onRestart() {
	    super.onRestart();  // Always call the superclass method first
	    
	    // Restart connection to SQL Database
	    db = new DatabaseManager(this);

	    //Reinitialize adapter
        adapter= new MyCustomAdapter(this,R.layout.listview_text,listItems);
        setListAdapter(adapter);

        //Refresh the table once the user has complete editing a specific recruit
        refreshList();
    	
        //Change boolean list back to false
		for(int i =0;i< listItems.size();i++){		
			status.get(i).setBoolean("false");
		}
	}	
	
	
	/********************************************************************************************************/
	//Custom ArrayAdapter
	public class MyCustomAdapter extends ArrayAdapter<String> {

		public MyCustomAdapter(Context context, int textViewResourceId, ArrayList<String> objects) {
			super(context, textViewResourceId, objects);
			// TODO Auto-generated constructor stub
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
	    	// collect the current row information from the database and store it in a two dimensional ArrayList
	    	ArrayList<ArrayList<Object>> data = db.getBaseRowAsArray(country, base);
	    	ArrayList<Object> rows = data.get(position);
			
			//return super.getView(position, convertView, parent);
			LayoutInflater inflater=getLayoutInflater();
			View row=inflater.inflate(R.layout.listview_text, parent, false);
			
	    	//Set the rank title onto the listView row
	    	TextView label_rank=(TextView)row.findViewById(R.id.list_rank);
	    	label_rank.setText(rows.get(9).toString());	
	    	
	    	//Set the names onto the listView row
	    	TextView label_name=(TextView)row.findViewById(R.id.list_content);
	    	label_name.setText(rows.get(3).toString()+" "+rows.get(4).toString());
	    	
	    	//Set the job title onto the listView row
	    	TextView label_job=(TextView)row.findViewById(R.id.list_job);
	    	label_job.setText(rows.get(8).toString());   
	    	
	    	//Set the country name
	    	TextView label_country = (TextView)row.findViewById(R.id.list_country);
	    	label_country.setText(rows.get(1).toString());
	    	
	    	//Set the base name
	    	TextView label_base = (TextView)row.findViewById(R.id.list_base);
	    	label_base.setText(rows.get(2).toString());
	    	
	    	
	    	//Set the image according to each recruit's rank
			ImageView icon=(ImageView)row.findViewById(R.id.insignia);			

			icon.setImageResource(changeRankImage(rows.get(6).toString(),rows.get(9).toString()));
			return row;
		}
		
		/**
		 * Set the rank image for the recruit
		 * Depending on the recruit's rank and division, his rank image will change
		 * @param div
		 * @param rank
		 */
		public int changeRankImage(String div, String rank){
			int value=0;
			if(div.equals("Army")){
				if(rank.equals("Private Basic")){
					value=R.drawable.army_e0;
				}
				else if(rank.equals("Private")){
					value=R.drawable.army_e1;
				}
				else if(rank.equals("Corporal")){
					value=R.drawable.army_e2;
				}
				else if(rank.equals("Master Corporal")){
					value=R.drawable.army_e3;
				}
				else if(rank.equals("Sergeant")){
					value=R.drawable.army_e4;
				}
				else if(rank.equals("Warrant Officer")){
					value=R.drawable.army_e5;
				}
				else if(rank.equals("Master Warrant Officer")){
					value=R.drawable.army_e6;
				}
				else if(rank.equals("Chief Warrant Officer")){
					value=R.drawable.army_e7;
				}
				else if(rank.equals("Officer Cadet")){
					value=R.drawable.army_o0;
				}
				else if(rank.equals("Second Lieutenant")){
					value=R.drawable.army_o1;
				}
				else if(rank.equals("Lieutenant")){
					value=R.drawable.army_o2;
				}
				else if(rank.equals("Captain")){
					value=R.drawable.army_o3;
				}
				else if(rank.equals("Major")){
					value=R.drawable.army_o4;
				}
				else if(rank.equals("Lieutenant Colonel")){
					value=R.drawable.army_o5;
				}
				else if(rank.equals("Colonel")){
					value=R.drawable.army_o6;
				}
			}else if(div.equals("Air Force")){
				if(rank.equals("Private Basic")){
					value=R.drawable.air_e0;
				}
				else if(rank.equals("Private")){
					value=R.drawable.air_e1;
				}
				else if(rank.equals("Corporal")){
					value=R.drawable.air_e2;
				}
				else if(rank.equals("Master Corporal")){
					value=R.drawable.air_e3;
				}
				else if(rank.equals("Sergeant")){
					value=R.drawable.air_e4;
				}
				else if(rank.equals("Warrant Officer")){
					value=R.drawable.air_e5;
				}
				else if(rank.equals("Master Warrant Officer")){
					value=R.drawable.air_e6;
				}
				else if(rank.equals("Chief Warrant Officer")){
					value=R.drawable.air_e7;
				}
				else if(rank.equals("Officer Cadet")){
					value=R.drawable.air_o0;
				}
				else if(rank.equals("Second Lieutenant")){
					value=R.drawable.air_o1;
				}
				else if(rank.equals("Lieutenant")){
					value=R.drawable.air_o2;
				}
				else if(rank.equals("Captain")){
					value=R.drawable.air_o3;
				}
				else if(rank.equals("Major")){
					value=R.drawable.air_o4;
				}
				else if(rank.equals("Lieutenant Colonel")){
					value=R.drawable.air_o5;
				}
				else if(rank.equals("Colonel")){
					value=R.drawable.air_o6;
				}
			}else if(div.equals("Navy")){
				if(rank.equals("Ordinary Seaman")){
					value=R.drawable.navy_e0;
				}
				else if(rank.equals("Able Seaman")){
					value=R.drawable.navy_e1;
				}
				else if(rank.equals("Leading Seaman")){
					value=R.drawable.navy_e2;
				}
				else if(rank.equals("Master Seaman")){
					value=R.drawable.navy_e3;
				}
				else if(rank.equals("Petty Officer 2nd Class")){
					value=R.drawable.navy_e4;
				}
				else if(rank.equals("Petty Officer 1st Class")){
					value=R.drawable.navy_e5;
				}
				else if(rank.equals("Chief Petty Officer 2nd Class")){
					value=R.drawable.navy_e6;
				}
				else if(rank.equals("Chief Petty Officer 1st Class")){
					value=R.drawable.navy_e7;
				}
				else if(rank.equals("Naval Cadet")){
					value=R.drawable.navy_o0;
				}
				else if(rank.equals("Acting Sub-Lieutenant")){
					value=R.drawable.navy_o1;
				}
				else if(rank.equals("Sub-Lieutenant")){
					value=R.drawable.navy_o2;
				}
				else if(rank.equals("Lieutenant")){
					value=R.drawable.navy_o3;
				}
				else if(rank.equals("Lieutenant Commander")){
					value=R.drawable.navy_o4;
				}
				else if(rank.equals("Commander")){
					value=R.drawable.navy_o5;
				}
				else if(rank.equals("Captain")){
					value=R.drawable.navy_o6;
				}
			}
			
			return value;
		}
	}
	
	
	
}