package com.krishnan.kuruk;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	
	String anna_univ_gate="Anna Univ Main Gate";
	String red_build_sbi="Red Building SBI";
	String exam_center="Examination Center";
	String vivek_audi="Vivekanda Auditorium";
	String canteen="Canteen";
	String alumini="Alumini Center";
	String mba_audi="MBA Audi";
	String health_center="Health Center";
	
	TextView tx;
	
	Map<String, Integer> index;
	Map<Integer, String> rindex;
	Map<String, String[]> nhop;
	
	DBHandler db;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		db=new DBHandler(getApplicationContext(),getResources());
		//Index Map//
		index=new HashMap();
		index.put(anna_univ_gate, 1);
		index.put(red_build_sbi, 2);
		index.put(exam_center, 3);
		index.put(vivek_audi, 4);
		index.put(canteen, 5);
		index.put(alumini, 6);
		index.put(mba_audi, 7);
		index.put(health_center, 8);
		
		//Reverse Index//
		rindex=new HashMap<Integer, String>();
		rindex.put(1,anna_univ_gate);
		rindex.put(2,red_build_sbi);
		rindex.put(3,exam_center);
		rindex.put(4,vivek_audi);
		rindex.put(5,canteen);
		rindex.put(6,alumini);
		rindex.put(7,mba_audi);
		rindex.put(8,health_center);
		
		//Next hop --> Locations that are reachable directly from a particular point
		nhop=new HashMap<String, String[]>();
		nhop.put(anna_univ_gate, new String[] {red_build_sbi,exam_center});
		nhop.put(red_build_sbi,new String[] {exam_center,vivek_audi,canteen,anna_univ_gate});
		nhop.put(exam_center,new String[] {anna_univ_gate,canteen,vivek_audi,red_build_sbi});
		nhop.put(vivek_audi, new String[] {mba_audi,canteen,red_build_sbi,exam_center,health_center});
		nhop.put(canteen, new String[] {red_build_sbi,vivek_audi,exam_center});
		nhop.put(alumini,new String[] {mba_audi,vivek_audi,health_center});
		nhop.put(mba_audi,new String[] {alumini,health_center,vivek_audi});
		nhop.put(health_center,new String[] {mba_audi,vivek_audi,alumini});
		
		Button getD=(Button)findViewById(R.id.button1);
		tx=(TextView)findViewById(R.id.textView3);
		final Spinner from=(Spinner)findViewById(R.id.spinner1);
		final Spinner to=(Spinner)findViewById(R.id.spinner2);
		getD.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String afrom=from.getSelectedItem().toString();
				String ato=to.getSelectedItem().toString();
				dijikstra(afrom,ato);
			}
		});
		
		Button google=(Button)findViewById(R.id.button2);
		google.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) 
			{
				ConnectionDetector cd=new ConnectionDetector(v.getContext());
				if(cd.isConnectingToInternet())
				{
					String afrom=from.getSelectedItem().toString();
					String ato=to.getSelectedItem().toString();
					Intent i=new Intent(v.getContext(), MapActivity.class);
					i.putExtra("lat", afrom);
					i.putExtra("lon", ato);
					startActivity(i);
				}
				else
				{
					showAlertDialog(v.getContext(), "Internet Connection", "No Internet Connection", false);
				}
			}
		});
	}
	
	@SuppressWarnings("deprecation")
	public void showAlertDialog(Context context, String title, String message, Boolean status) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
 
        // Setting Dialog Title
        alertDialog.setTitle(title);
 
        // Setting Dialog Message
        alertDialog.setMessage(message);
 
        // Setting OK Button
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
 
        // Showing Alert Message
        alertDialog.show();
    }
	//Dijikstra part -> Finding shortest path b/w A & B
	public void dijikstra(String afrom, String ato)
	{
		System.out.println(afrom);
		System.out.println(ato);
		int from=index.get(afrom);
		System.out.println(ato);
		int to=index.get(ato);
		int cost[]=new int[9];
		int path[]=new int[9];
		int visited[]=new int[9];
		int i,j,idx,flag,min,min_index;
		Stack<String> pa=new Stack<String>();
		pa.clear();
		
		//Intializing
		for(i=1;i<cost.length;i++)
		{
			cost[i]=50;
			path[i]=-1;
			visited[i]=0;
		}
		System.out.println("Passed");
		cost[from]=0;
		i=from;
		path[i]=i;
		while(i != to)
		{
			flag=0;
			visited[i]=1;
			System.out.println(i);
			String[] nextHop=nhop.get(rindex.get(i));
			System.out.println(nextHop);
			
			//Computing Efficient cost
			for(j=0;j<nextHop.length;j++)
			{
				idx=index.get(nextHop[j]);
				System.out.println(idx);
				if(visited[idx] == 0)
					if( cost[idx] > (cost[i]+1) )
					{
						cost[idx]=cost[i]+1;
						path[idx]=i;
					}
				if(idx == to)
					flag=1;
			}
			if(flag == 1)
				break;
			for(j=1,min=50,min_index=50;j<cost.length;j++)
			{
				if(visited[j] == 0 && min > cost[j])
				{
					min=cost[j];
					min_index=j;
				}
			}
			i=min_index;
		}
		
		//Finding the path
		String temp="";
		j=to;
		pa.push(ato);
		while(j!=from)
		{
			pa.push(rindex.get(path[j]));
			temp=temp+rindex.get(path[j]);
			j=path[j];
		}
		String troute="";
		while(pa.size() > 1)
		{
			System.out.println(troute);
			String t_from=pa.pop();
			String t_to=pa.peek();
			troute=troute + db.getRoute(t_from, t_to)+" \n \n ";
		}
		Intent disp=new Intent(this, DispActivity.class);
		disp.putExtra("route",troute);
		startActivity(disp);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch(item.getItemId())
		{
			case R.id.menu_map:
				Toast.makeText(MainActivity.this,"Clicked Map Button",Toast.LENGTH_LONG).show();
				Intent i=new Intent(this, MapActivity.class);
				startActivity(i);
				return true;
			case R.id.image_map:
				Intent j=new Intent(this, ImageActivity.class);
				startActivity(j);
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}

	}
	
	private class ConnectionDetector {
	     
	    private Context _context;
	     
	    public ConnectionDetector(Context context){
	        this._context = context;
	    }
	 
	    public boolean isConnectingToInternet(){
	        ConnectivityManager connectivity = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
	          if (connectivity != null) 
	          {
	              NetworkInfo[] info = connectivity.getAllNetworkInfo();
	              if (info != null) 
	                  for (int i = 0; i < info.length; i++) 
	                      if (info[i].getState() == NetworkInfo.State.CONNECTED)
	                      {
	                          return true;
	                      }
	 
	          }
	          return false;
	    }
	}

}
