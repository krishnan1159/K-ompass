package com.krishnan.kuruk;

import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.android.gms.maps.model.LatLng;


@SuppressLint({ "SetJavaScriptEnabled", "JavascriptInterface" })
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class MapActivity extends Activity {

	//private GoogleMap map=null;
	private Map<String, LatLng> latlng;
	String anna_univ_gate="Anna Univ Main Gate";
	String red_build_sbi="Red Building SBI";
	String exam_center="Examination Center";
	String vivek_audi="Vivekanda Auditorium";
	String canteen="Canteen";
	String alumini="Alumini Center";
	String mba_audi="MBA Audi";
	String health_center="Health Center";
	LatLng from_c,to_c;
	double[] s;
	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
	// Maping the position and its Latitude 
		latlng=new HashMap<String, LatLng>();
		latlng.put(anna_univ_gate,new LatLng(13.008147,80.234964));
		latlng.put(red_build_sbi,new LatLng(13.010695,80.235906));
		latlng.put(exam_center,new LatLng(13.008763,80.236418));
		latlng.put(vivek_audi,new LatLng(13.01158,80.23644));
		latlng.put(canteen,new LatLng(13.010663,80.236751));
		latlng.put(alumini,new LatLng(13.013047,80.236456));
		latlng.put(mba_audi,new LatLng(13.012574,80.236429));
		latlng.put(health_center,new LatLng(13.013569,80.239317));
		
		String lat=getIntent().getExtras().getString("lat");
		String lon=getIntent().getExtras().getString("lon");
		
		from_c=latlng.get(lat);
		to_c=latlng.get(lon);
		String url="file:///android_asset/www/index.html";
		System.out.println(url);
		Log.d("StartLat",Double.toString(from_c.latitude));
		Log.d("StartLng",Double.toString(from_c.longitude));
		Log.d("EndLat",Double.toString(to_c.latitude));
		Log.d("EndLng",Double.toString(to_c.longitude));
		s=new double[]{from_c.latitude,from_c.longitude,to_c.latitude,to_c.longitude};
		for(int i=0;i<s.length;i++)
			System.out.println(s[i]);
		WebView wv = (WebView)findViewById(R.id.my_webview);
		wv.getSettings().setJavaScriptEnabled(true);
        wv.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        wv.addJavascriptInterface(new Position(), "position");
        wv.loadData("","text/html", null);
        if(!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
        {
        	url="file://"+Environment.getExternalStorageDirectory()+"/android_asset/www/index.html";
        }
        wv.setWebViewClient(new WebViewClient() {
        	ProgressDialog pd = null;
        	@Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        	@Override
      	  public void onPageStarted(WebView view, String url, Bitmap favicon) {
      	   super.onPageStarted(view, url, favicon);
      	   pd = new ProgressDialog(MapActivity.this);
      	   pd.setTitle("Please wait");
      	   pd.setMessage("Map is loading..");
      	   pd.setCanceledOnTouchOutside(false);
      	   pd.show();
      	  }
        	
        	@Override
        	public void onPageFinished(WebView view,String url)
        	{
        		super.onPageFinished(view, url);
        		pd.dismiss();
        	}
        	
        });
        wv.loadUrl(url);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.map, menu);
		return true;
	}
	
	final class Position{
		@JavascriptInterface
		public double getStartLat()
		{
			return s[0];
		}
		@JavascriptInterface
		public double getStartLng()
		{
			return s[1];
		}
		@JavascriptInterface
		public double getEndLat()
		{
			return s[2];
		}
		@JavascriptInterface
		public double getEndLng()
		{
			return s[3];
		}
	}
	
}
