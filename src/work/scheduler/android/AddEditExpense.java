package work.scheduler.android;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

public class AddEditExpense extends Activity{
  static String mStuffresults;
  int typeBar;    
  ProgressDialog progDialog;
  

	EditText updatedate;
	EditText updateTime;
	EditText discription;
	final int DATE_DIALOG_ID = 1;
	     final int TIME_DIALOG_ID = 2;
		 private int year, month, day;
		 private int mHour;
		    private int mMinute;
		

	   private NotesDbAdapter mDbHelper;
       static String Date;
       static String Time;
       static String Discripton;
       static String  freqSelected;
       
	 public void onCreate(Bundle savedInstanceState)
	    {
	        super.onCreate(savedInstanceState);
	        
	        setContentView(R.layout.addeditexpence);
	        String[] Freq = 
	        {
	        "Ones","Hour","Daily","Weekly","Monthly"
	        };
	        ArrayAdapter<String> adapter = 
	            new ArrayAdapter<String> (this, 
	            android.R.layout.simple_dropdown_item_1line,Freq);
	        final Spinner  sp = (Spinner)findViewById(R.id.freq);
	        sp.setAdapter(adapter);
	        sp.setOnItemSelectedListener(
	  		      new  AdapterView.OnItemSelectedListener() {           
	  		      @Override
	  		       public void onItemSelected(AdapterView<?> parent, 
	  		       View view, int position, long id) 
	  		       {
	  		           freqSelected = (String)sp.getSelectedItem();
	  		           Log.i("dsfasf", freqSelected);
	  		       }
	  		            @Override
	  					public void onNothingSelected(AdapterView<?> parent)
	  		            {
	  						
	  					}
	  	 });
	        mDbHelper = new NotesDbAdapter(this);
	        mDbHelper.open();
		    updateTime=(EditText)findViewById(R.id.updatetime);
		    updateTime.setOnClickListener(new View.OnClickListener()
	    	{
	    	public void onClick(View v) {

	    		showDialog(TIME_DIALOG_ID);
	    
	    	}});
		    discription=(EditText)findViewById(R.id.discription);

		    Button  AddData=(Button)findViewById(R.id.senddata);
		    AddData.setOnClickListener(new View.OnClickListener()
	    	{
	    	public void onClick(View v) {
                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);                 
                int iUniqueId = (int) (System.currentTimeMillis() & 0xfffffff);

				    Date= updatedate.getText().toString();
				    Time= updateTime.getText().toString();
				    Discripton= discription.getText().toString();
				    //---get current date and time---
	                Calendar calendar = Calendar.getInstance();       
	                //---sets the time for the alarm to trigger---
	                calendar.set(Calendar.YEAR, year);
	                calendar.set(Calendar.MONTH, month);
	                calendar.set(Calendar.DAY_OF_MONTH, day);                 
	                calendar.set(Calendar.HOUR_OF_DAY, mHour);
	                calendar.set(Calendar.MINUTE, mMinute);
	                calendar.set(Calendar.SECOND, 0);
                    Log.i("********####",year+"  ,"+month+" , "+day+" , "+mHour+" , "+mMinute+"----"+ calendar.getTimeInMillis());
            
	                Intent intent = new Intent(AddEditExpense.this, TimeAlarm.class);
	                

	                Bundle b12 = new Bundle();
	                mStuffresults=Discripton;
	                b12.putString("serverresponse", mStuffresults);
	                intent.setAction("" + Math.random());
	                intent.putExtras(b12);
	          	    PendingIntent displayIntent = PendingIntent.getBroadcast(AddEditExpense.this,iUniqueId,
	          	    intent, PendingIntent.FLAG_UPDATE_CURRENT);
	            	    alarmManager.set(AlarmManager.RTC_WAKEUP,
	          			calendar.getTimeInMillis(), displayIntent);
	          	  if(freqSelected.equalsIgnoreCase("Ones")){
		            	alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), displayIntent);
//		          			
	          	  }
	          	  if(freqSelected.equalsIgnoreCase("Hour")){
		            	alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),60*60*1000, displayIntent);
		            

	          	  }

	          	  if(freqSelected.equalsIgnoreCase("Daily")){
		            	alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),60*60*24*1000, displayIntent);

	          	  }

	          	  if(freqSelected.equalsIgnoreCase("Weekly")){
		            	alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),7*24*60*60*1000, displayIntent);

	          	  }
	         
//	          	

	          	  
	          		if(TaskReminder.flag==0){ 
	          			mDbHelper.createNote(Time,Date,Discripton);
	          			 }
	          		else{
	          			 mDbHelper.updateNote(TaskReminder.row1, Time,Date,Discripton);
	          
	          		}
	          		typeBar = 0;
                    showDialog(typeBar);
           finish();
          
	    	}

			

			});
		    
	        
	    updatedate=(EditText)findViewById(R.id.updatedate);
		     updatedate.setOnClickListener(new View.OnClickListener()
		    	{
		    	public void onClick(View v) {

		    		showDialog(DATE_DIALOG_ID);
		    
		    	}});
		     final Calendar cal = Calendar.getInstance();
		        year = cal.get(Calendar.YEAR);
		        month = cal.get(Calendar.MONTH);
		        day = cal.get(Calendar.DAY_OF_MONTH);
		    	SimpleDateFormat date_format = new SimpleDateFormat("yyyyMMMdd");
		    	System.out.println(date_format.format(cal.getTime()));
		        updateDate();
		        
		        
		        mHour = cal.get(Calendar.HOUR_OF_DAY);
		        mMinute = cal.get(Calendar.MINUTE);
		       

		        // display the current date
		        updateDisplay();
		    

}

	 private void updateDisplay() {
		 updateTime.setText(
		        new StringBuilder()
		                .append(mHour).append(":")
		                .append(mMinute));
		}

		

	private void updateDate() {
			StringBuilder str=new StringBuilder().append(day).append('-')
			.append(month).append('-').append(year);
			updatedate.setText(str);
		}
  private DatePickerDialog.OnDateSetListener dateListener = 
			new DatePickerDialog.OnDateSetListener() {

				@Override
				public void onDateSet(DatePicker view, int yr, int monthOfYear,
						int dayOfMonth) {
					year =yr;
					month = monthOfYear;
					day = dayOfMonth;
					updateDate();
				}
		};
		private TimePickerDialog.OnTimeSetListener mTimeSetListener =
		    new TimePickerDialog.OnTimeSetListener() {
		        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
		            mHour = hourOfDay;
		            mMinute = minute;
		            updateDisplay();
		        }
		    };
		    
		protected Dialog onCreateDialog(int id){
			switch(id) {
			case DATE_DIALOG_ID:
				return new DatePickerDialog(this, dateListener, year, month, day);
				
				
			case TIME_DIALOG_ID:
	            return new TimePickerDialog(this,
	                    mTimeSetListener, mHour, mMinute, false);
			case 0:                      // Spinner
                progDialog = new ProgressDialog(this);
                progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progDialog.setMessage("Loading...");
              
             
                return progDialog;
			
			}
			return null;
			
		
		
		}}
