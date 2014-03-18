package com.example.apollo;
 
import java.util.ArrayList;
 
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
 
public class DatabaseManager
{
	// the Activity or Application that is creating an object from this class.
	Context context;
 
	// a reference to the database used by this application/object
	private SQLiteDatabase db;
 
	// These constants are specific to the database.
	private final String DB_NAME = "database_name";
	private final int DB_VERSION = 1;
 
	// These constants are specific to the database table.
	private final String TABLE_NAME = "personnel_table";
	private final String TABLE_ROW_ID = "id";
	private final String TABLE_ROW_COUNTRY = "table_row_country";
	private final String TABLE_ROW_BASE = "table_row_base";	
	private final String TABLE_ROW_FNAME = "table_row_fname";
	private final String TABLE_ROW_LNAME = "table_row_lname";
	private final String TABLE_ROW_GENDER = "table_row_gender";
	private final String TABLE_ROW_DIVISION = "table_row_division";
	private final String TABLE_ROW_ROLE = "table_row_role";
	private final String TABLE_ROW_JOB = "table_row_job";
	private final String TABLE_ROW_RANK = "table_row_rank";
	
	public DatabaseManager(Context context)
	{
		this.context = context;
 
		// create or open the database
		CustomSQLiteOpenHelper helper = new CustomSQLiteOpenHelper(context);
		this.db = helper.getWritableDatabase();
	}
 
	public SQLiteDatabase getSQL(){
		return this.db;
	}
 
 
	/**********************************************************************
	 * ADDING A ROW TO THE DATABASE TABLE
	 * 
	 * This is an example of how to add a row to a database table
	 * using this class.  You should edit this method to suit your
	 * needs.
	 * 
	 * the key is automatically assigned by the database
	 * @param rowStringCountry the value for the row's first column (country)
	 * @param rowStringBase the value for the row's second column (base) 
	 * @param rowStringFname the value for the row's third column (first name)
	 * @param rowStringLname the value for the row's forth column (last name)
	 * @param rowStringGender the value for the row's fifth column (gender)
	 * @param rowStringDivision the value for the row's sixth column (division)
	 * @param rowStringRole the value for the row's seventh column (role)
	 * @param rowStringJob the value for the row's eighth column (job)
	 * @param rowStringRank the value for the row's ninth column (rank)
	 *  
	 */
	public void addRow(String rowStringCountry, String rowStringBase,String rowStringFname, String rowStringLname, String rowStringGender, String rowStringDivision, String rowStringRole, String rowStringJob, String rowStringRank)
	{
		// this is a key value pair holder used by android's SQLite functions
		ContentValues values = new ContentValues();
		values.put(TABLE_ROW_COUNTRY, rowStringCountry);
		values.put(TABLE_ROW_BASE, rowStringBase);
		values.put(TABLE_ROW_FNAME, rowStringFname);
		values.put(TABLE_ROW_LNAME, rowStringLname);
		values.put(TABLE_ROW_GENDER, rowStringGender);
		values.put(TABLE_ROW_DIVISION, rowStringDivision);
		values.put(TABLE_ROW_ROLE, rowStringRole);
		values.put(TABLE_ROW_JOB, rowStringJob);
		values.put(TABLE_ROW_RANK, rowStringRank);
		
		// ask the database object to insert the new data 
		try{db.insert(TABLE_NAME, null, values);}
		catch(Exception e)
		{
			Log.e("DB ERROR", e.toString());
			e.printStackTrace();
		}
	}
 
 
 
	/**********************************************************************
	 * DELETING A ROW FROM THE DATABASE TABLE
	 * 
	 * This is an example of how to delete a row from a database table
	 * using this class. In most cases, this method probably does
	 * not need to be rewritten.
	 * 
	 * @param rowID the SQLite database identifier for the row to delete.
	 */
	public void deleteRow(long rowID)
	{
		// ask the database manager to delete the row of given id
		try {db.delete(TABLE_NAME, TABLE_ROW_ID + "=" + rowID, null);}
		catch (Exception e)
		{
			Log.e("DB ERROR", e.toString());
			e.printStackTrace();
		}
	}
 
	/**********************************************************************
	 * UPDATING A ROW IN THE DATABASE TABLE
	 * 
	 * This is an example of how to update a row in the database table
	 * using this class.  You should edit this method to suit your needs.
	 * 
	 * @param rowID the SQLite database identifier for the row to update.
	 * @param rowStringCountry the value for the row's first column (country)
	 * @param rowStringBase the value for the row's second column (base) 
	 * @param rowStringFname the value for the row's third column (first name)
	 * @param rowStringLname the value for the row's forth column (last name)
	 * @param rowStringGender the value for the row's fifth column (gender)
	 * @param rowStringDivision the value for the row's sixth column (division)
	 * @param rowStringRole the value for the row's seventh column (role)
	 * @param rowStringJob the value for the row's eighth column (job)
	 * @param rowStringRank the value for the row's ninth column (rank)
	 */ 
	public void updateRow(long rowID, String rowStringCountry, String rowStringBase, String rowStringFname, String rowStringLname, String rowStringGender, String rowStringDivision, String rowStringRole, String rowStringJob, String rowStringRank)
	{
		// this is a key value pair holder used by android's SQLite functions
		ContentValues values = new ContentValues();
		values.put(TABLE_ROW_COUNTRY, rowStringCountry);
		values.put(TABLE_ROW_BASE, rowStringBase);		
		values.put(TABLE_ROW_FNAME, rowStringFname);
		values.put(TABLE_ROW_LNAME, rowStringLname);
		values.put(TABLE_ROW_GENDER, rowStringGender);
		values.put(TABLE_ROW_DIVISION, rowStringDivision);
		values.put(TABLE_ROW_ROLE, rowStringRole);
		values.put(TABLE_ROW_JOB, rowStringJob);
		values.put(TABLE_ROW_RANK, rowStringRank);
		
		System.out.println("Updating index = "+rowID);
		
		// ask the database object to update the database row of given rowID
		try {db.update(TABLE_NAME, values, TABLE_ROW_ID + "=" + rowID, null);}
		catch (Exception e)
		{
			Log.e("DB Error", e.toString());
			e.printStackTrace();
		}
	}
 
	/**********************************************************************
	 * RETRIEVING A ROW FROM THE DATABASE TABLE
	 * 
	 * This is an example of how to retrieve a row from a database table
	 * using this class.  You should edit this method to suit your needs.
	 * 
	 * @param rowID the id of the row to retrieve
	 * @return an array containing the data from the row
	 */
	public ArrayList<Object> getRowAsArray(long rowID)
	{
		// create an array list to store data from the database row.
		// I would recommend creating a JavaBean compliant object 
		// to store this data instead.  That way you can ensure
		// data types are correct.
		ArrayList<Object> rowArray = new ArrayList<Object>();
		Cursor cursor;
 
		try
		{
			// this is a database call that creates a "cursor" object.
			// the cursor object store the information collected from the
			// database and is used to iterate through the data.
			cursor = db.query
			(
					TABLE_NAME,
					new String[] { TABLE_ROW_ID, TABLE_ROW_COUNTRY, TABLE_ROW_BASE, TABLE_ROW_FNAME, TABLE_ROW_LNAME, TABLE_ROW_GENDER, TABLE_ROW_DIVISION, TABLE_ROW_ROLE, TABLE_ROW_JOB, TABLE_ROW_RANK},
					TABLE_ROW_ID + "=" + rowID,
					null, null, null, null, null
			);
 
			// move the pointer to position zero in the cursor.
			cursor.moveToFirst();
 
			// if there is data available after the cursor's pointer, add
			// it to the ArrayList that will be returned by the method.
			if (!cursor.isAfterLast())
			{
				do
				{
					rowArray.add(cursor.getLong(0));
					rowArray.add(cursor.getString(1));
					rowArray.add(cursor.getString(2));
					rowArray.add(cursor.getString(3));
					rowArray.add(cursor.getString(4));
					rowArray.add(cursor.getString(5));
					rowArray.add(cursor.getString(6));
					rowArray.add(cursor.getString(7));
					rowArray.add(cursor.getString(8));
					rowArray.add(cursor.getString(9));
				}
				while (cursor.moveToNext());
			}
 
			// let java know that you are through with the cursor.
			cursor.close();
		}
		catch (SQLException e) 
		{
			Log.e("DB ERROR", e.toString());
			e.printStackTrace();
		}
 
		// return the ArrayList containing the given row from the database.
		return rowArray;
	}

	
	/**********************************************************************
	 * RETRIEVING ROWS FROM THE DATABASE TABLE BASED ON COUNTRY & BASE 
	 * 
	 * 
	 * @param String C (Country)
	 * @param String B (Base)
	 * @return an array containing the data from the row
	 */
	public ArrayList<ArrayList<Object>> getBaseRowAsArray(String C,String B)
	{
		// create an ArrayList that will hold all of the data collected from
		// the database.
		ArrayList<ArrayList<Object>> dataArrays = new ArrayList<ArrayList<Object>>();
 
		// this is a database call that creates a "cursor" object.
		// the cursor object store the information collected from the
		// database and is used to iterate through the data.
		Cursor cursor;
 
		try
		{
			// ask the database object to create the cursor.
			cursor = db.query(
					TABLE_NAME,
					new String[]{TABLE_ROW_ID, TABLE_ROW_COUNTRY, TABLE_ROW_BASE,TABLE_ROW_FNAME, TABLE_ROW_LNAME, TABLE_ROW_GENDER, TABLE_ROW_DIVISION, TABLE_ROW_ROLE, TABLE_ROW_JOB, TABLE_ROW_RANK},
			          TABLE_ROW_COUNTRY + "=?" + " and "  +
			          TABLE_ROW_BASE + "=?", 
			          new String[] {C,B},
					
					null, null, null, null
			);
 
			// move the cursor's pointer to position zero.
			cursor.moveToFirst();
 
			// if there is data after the current cursor position, add it
			// to the ArrayList.
			if (!cursor.isAfterLast())
			{
				do
				{
					ArrayList<Object> dataList = new ArrayList<Object>();
 
					dataList.add(cursor.getLong(0));
					dataList.add(cursor.getString(1));
					dataList.add(cursor.getString(2));
					dataList.add(cursor.getString(3));
					dataList.add(cursor.getString(4));
					dataList.add(cursor.getString(5));
					dataList.add(cursor.getString(6));
					dataList.add(cursor.getString(7));
					dataList.add(cursor.getString(8));
					dataList.add(cursor.getString(9));					
					dataArrays.add(dataList);
				}
				// move the cursor's pointer up one position.
				while (cursor.moveToNext());
				
				//Should i be closing cursor to avoid leak?
				cursor.close();
			}
		}
		catch (SQLException e)
		{
			Log.e("DB Error", e.toString());
			e.printStackTrace();
		}
 
		// return the ArrayList that holds the data collected from
		// the database.
		return dataArrays;
	}
 
	/**********************************************************************
	 * RETRIEVING ALL ROWS FROM THE DATABASE TABLE
	 * 
	 * This is an example of how to retrieve all data from a database
	 * table using this class.  You should edit this method to suit your
	 * needs.
	 * 
	 * the key is automatically assigned by the database
	 */
 
	public ArrayList<ArrayList<Object>> getAllRowsAsArrays()
	{
		// create an ArrayList that will hold all of the data collected from
		// the database.
		ArrayList<ArrayList<Object>> dataArrays = new ArrayList<ArrayList<Object>>();
 
		// this is a database call that creates a "cursor" object.
		// the cursor object store the information collected from the
		// database and is used to iterate through the data.
		Cursor cursor;
 
		try
		{
			// ask the database object to create the cursor.
			cursor = db.query(
					TABLE_NAME,
					new String[]{TABLE_ROW_ID, TABLE_ROW_COUNTRY, TABLE_ROW_BASE,TABLE_ROW_FNAME, TABLE_ROW_LNAME, TABLE_ROW_GENDER, TABLE_ROW_DIVISION, TABLE_ROW_ROLE, TABLE_ROW_JOB, TABLE_ROW_RANK},
					null, null, null, null, null
			);
 
			// move the cursor's pointer to position zero.
			cursor.moveToFirst();
 
			// if there is data after the current cursor position, add it
			// to the ArrayList.
			if (!cursor.isAfterLast())
			{
				do
				{
					ArrayList<Object> dataList = new ArrayList<Object>();
 
					dataList.add(cursor.getLong(0));
					dataList.add(cursor.getString(1));
					dataList.add(cursor.getString(2));
					dataList.add(cursor.getString(3));
					dataList.add(cursor.getString(4));
					dataList.add(cursor.getString(5));
					dataList.add(cursor.getString(6));
					dataList.add(cursor.getString(7));
					dataList.add(cursor.getString(8));
					dataList.add(cursor.getString(9));					
					dataArrays.add(dataList);
				}
				// move the cursor's pointer up one position.
				while (cursor.moveToNext());
				
				//Should i be closing cursor to avoid leak?
				cursor.close();
			}
		}
		catch (SQLException e)
		{
			Log.e("DB Error", e.toString());
			e.printStackTrace();
		}
 
		// return the ArrayList that holds the data collected from
		// the database.
		return dataArrays;
	}
 
 
 
 
	/**********************************************************************
	 * THIS IS THE BEGINNING OF THE INTERNAL SQLiteOpenHelper SUBCLASS.
	 * 
	 * I MADE THIS CLASS INTERNAL SO I CAN COPY A SINGLE FILE TO NEW APPS 
	 * AND MODIFYING IT - ACHIEVING DATABASE FUNCTIONALITY.  ALSO, THIS WAY 
	 * I DO NOT HAVE TO SHARE CONSTANTS BETWEEN TWO FILES AND CAN
	 * INSTEAD MAKE THEM PRIVATE AND/OR NON-STATIC.  HOWEVER, I THINK THE
	 * INDUSTRY STANDARD IS TO KEEP THIS CLASS IN A SEPARATE FILE.
	 *********************************************************************/
 
	/**
	 * This class is designed to check if there is a database that currently
	 * exists for the given program.  If the database does not exist, it creates
	 * one.  After the class ensures that the database exists, this class
	 * will open the database for use.  Most of this functionality will be
	 * handled by the SQLiteOpenHelper parent class.  The purpose of extending
	 * this class is to tell the class how to create (or update) the database.
	 * 
	 * @author Randall Mitchell
	 *
	 */
	private class CustomSQLiteOpenHelper extends SQLiteOpenHelper
	{
		public CustomSQLiteOpenHelper(Context context)
		{
			super(context, DB_NAME, null, DB_VERSION);
		}
 
		@Override
		public void onCreate(SQLiteDatabase db)
		{
			// This string is used to create the database.  It should
			// be changed to suit your needs.
			String newTableQueryString = "create table " +
										TABLE_NAME +
										" (" +
										TABLE_ROW_ID + " integer primary key autoincrement not null," +
										TABLE_ROW_COUNTRY + " text," +
										TABLE_ROW_BASE + " text," +
										TABLE_ROW_FNAME + " text," +
										TABLE_ROW_LNAME + " text," +
										TABLE_ROW_GENDER + " text," +
										TABLE_ROW_DIVISION + " text," +
										TABLE_ROW_ROLE + " text," +
										TABLE_ROW_JOB + " text," +
										TABLE_ROW_RANK + " text" +
										");";
			// execute the query string to the database.
			db.execSQL(newTableQueryString);
		}
 
 
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
		{
			// NOTHING TO DO HERE. THIS IS THE ORIGINAL DATABASE VERSION.
			// OTHERWISE, YOU WOULD SPECIFIY HOW TO UPGRADE THE DATABASE.
		}
	}
}