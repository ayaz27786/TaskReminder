package work.scheduler.android;

import android.app.AlertDialog;
import android.app.ExpandableListActivity;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import android.widget.AdapterView.AdapterContextMenuInfo;
public class TaskReminder extends ExpandableListActivity {
    private NotesDbAdapter mDbHelper;
   // Activity activity = null;

    private static final int DELETE_ID= 1;
    private static final int DETAIL_ID =2;
	private static final int UPDATE_ID = 0;
      static String  Timediscr;
      static String  Discription;
      static String Datediscr; 
      static long row1;
     static int flag=0;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // = this;

        setContentView(R.layout.main);
        
        
       
     

       mDbHelper = new NotesDbAdapter(this);
        mDbHelper.open();
        fillData();
Ex
        /*ListView lv = getListView();
        this.registerForContextMenu(lv);*/

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainmenu, menu);
        return true;
    }
    
    
    public boolean onOptionsItemSelected(MenuItem item) {
    	flag=0;
    	Intent i =new Intent(TaskReminder.this,AddEditExpense.class);
    	startActivity(i);
		return true;
	}
	private void fillData() {
	        Cursor notesCursor = mDbHelper.fetchAllNotes();
	        startManagingCursor(notesCursor);
	        
	        // Create an array to specify the fields we want to display in the list (only TITLE)
	        String[] from = new String[]{NotesDbAdapter.KEY_TIME,NotesDbAdapter.KEY_DATE,NotesDbAdapter.KEY_DISCRIPTION};
	    
	        int[] to = new int[]{R.id.timetext,R.id.datetext,R.id.desctext};
	           
	       SimpleCursorAdapter notes =   
	    	                            new SimpleCursorAdapter(this, R.layout.row, notesCursor, from, to);
	      
	       setListAdapter(notes);
	       
	    }
	 @Override
		protected void onDestroy() {
			super.onDestroy();
			if ( mDbHelper != null) {
				 mDbHelper.close();
			}
		}
	 @Override
	 public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
	        super.onCreateContextMenu(menu, v, menuInfo);
//	        MenuInflater inflater = getMenuInflater();
//	        inflater.inflate(R.menu.context_menu, menu);

	        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
	        long itemID = info.position;
	        menu.setHeaderTitle("Action" + itemID);
	        menu.add(0, DETAIL_ID, 0, "DETAILS");

	        menu.add(0, DELETE_ID, 1, "DELETE");
	        
	        menu.add(0, UPDATE_ID, 1, "UPDATE");
	    }
	    @Override
	    public boolean onContextItemSelected(MenuItem item) {
	        switch(item.getItemId()) {
	            case DELETE_ID:
	                AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
	                mDbHelper.deleteNote(info.id);
	                fillData();
	                return true;
	            case UPDATE_ID:
	            	 AdapterContextMenuInfo info1 = (AdapterContextMenuInfo) item.getMenuInfo();
	            	 row1=info1.id;
	            	 flag =1;
	            	Intent i =new Intent(TaskReminder.this,AddEditExpense.class);
	            	startActivity(i);
	        		return true;
		               
	      
            case DETAIL_ID:
               
                AlertDialog.Builder alt_bld = new AlertDialog.Builder(TaskReminder.this);
                alt_bld.setTitle("Details");
		    	alt_bld.setMessage("Date/Time: " +Datediscr+", "+Timediscr+ "\r\n"+"Discription:"+Discription)
		    	.setCancelable(false)
		    	.setPositiveButton("OK", new DialogInterface.OnClickListener() {
		    	public void onClick(DialogInterface dialog, int id) {
		   	}
	  	});
		    	AlertDialog alert = alt_bld.create();
		    	
		    	alert.show();
               
                return true;
        }
	        return super.onContextItemSelected(item);
	    }
	   
	    @Override
	    protected void onListItemClick(ListView l, View v, int position, long thisID)
	    {
	        super.onListItemClick(l, v, position, thisID);
	       
	        l.showContextMenuForChild(v);   

	        Cursor cursor = (Cursor)getListAdapter().getItem(position);
	        Timediscr = cursor.getString(cursor.getColumnIndex(NotesDbAdapter.KEY_TIME));
	        Discription = cursor.getString(cursor.getColumnIndex(NotesDbAdapter.KEY_DISCRIPTION));
	        Datediscr = cursor.getString(cursor.getColumnIndex(NotesDbAdapter.KEY_DATE));

	       
	    }
	     
	  

	   
}