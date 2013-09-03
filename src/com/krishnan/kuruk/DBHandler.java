package com.krishnan.kuruk;

import java.io.ByteArrayOutputStream;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;

public class DBHandler extends SQLiteOpenHelper{
	private static final int db_version=1;
	private static final String db_name="kuruk9_6";
	private static final String tab_name="kmaps";
	private static final String from="afrom";
	private static final String to="ato";
	private static final String route="route";
	private static final String map_tab="cmap";
	private static final String img="img";
	String anna_univ_gate="Anna Univ Main Gate";
	String red_build_sbi="Red Building SBI";
	String exam_center="Examination Center";
	String vivek_audi="Vivekanda Auditorium";
	String canteen="Canteen";
	String alumini="Alumini Center";
	String mba_audi="MBA Audi";
	String health_center="Health Center";
	Context context;
	Resources resource;
	
	public DBHandler(Context context,Resources res)
	{
		super(context, db_name, null, db_version);
		this.context=context;
		this.resource=res;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String stmt="CREATE TABLE " + tab_name + "(" + from +" TEXT, " + to + " TEXT, " + route + " TEXT )";
		db.execSQL(stmt);
		stmt="CREATE TABLE " + map_tab + "(" + img + " BLOB )";
		db.execSQL(stmt);
		//Inserting KMap as Image(BLOB)
		ContentValues img_values =new ContentValues();
		Bitmap bit=BitmapFactory.decodeResource(context.getResources(), R.drawable.kmap);
		System.out.println(bit);
		//Bitmap b=((BitmapDrawable)context.getResources().getDrawable(R.drawable.map)).getBitmap();
		ByteArrayOutputStream bos=new ByteArrayOutputStream();
		bit.compress(Bitmap.CompressFormat.PNG, 100, bos);
	    byte[] aimg=bos.toByteArray();
	    img_values.put(img,aimg);
	    db.insert(map_tab, null, img_values);
		
		//Inserting routes for nexthop
		ContentValues values=new ContentValues();
	//From anna-univ gate
		values.put(from,anna_univ_gate);
		values.put(to, red_build_sbi);
		values.put(route, "1.Walk Straight u will reach Arignar Anna Silai \n 2. Walk Straight further" +
				" to reach Reb Building, SBI Bank" );
		db.insert(tab_name, null, values);
		
		values.put(to,exam_center);
		values.put(route,"1. Take Left and Walk Straight to reach Examination Center");
		db.insert(tab_name, null, values);
		
	// From Red-Building SBI
		values.put(from,red_build_sbi);
		values.put(to, anna_univ_gate);
		values.put(route, "1.Walk Straight u will reach Arignar Anna Silai \n 2. Walk Straight further" +
				" to reach Reb Building, SBI Bank" );
		db.insert(tab_name, null, values);
		
		values.put(to, exam_center);
		values.put(route, "1. Walk Straight you will meet cross road. \n 2. Take Right and Walk Straight" +
				" to reach Examination Center" );
		db.insert(tab_name, null, values);
		
		values.put(to, vivek_audi);
		values.put(route, "1. Walk Straight you will meet cross road. \n 2. Take Left and Walk Straight" +
				" to reach Vivek Audi" );
		db.insert(tab_name, null, values);
		
		values.put(to, canteen);
		values.put(route, "1. Walk Straight you will meet cross road. \n 2. Walk Straight" +
				" to reach Canteen" );
		db.insert(tab_name, null, values);
		
	//From Vivek Auditorium
		values.put(from,vivek_audi);
		values.put(to, mba_audi);
		values.put(route, "1. Walk Straight and Take First Left to reach MBA Auditorium. " );
		db.insert(tab_name, null, values);
		
		values.put(to, canteen);
		values.put(route, "1. Walk Straight and Take first Right to reach Canteen. " );
		db.insert(tab_name, null, values);
		
		values.put(to, red_build_sbi);
		values.put(route, "1. Walk Straight and Take First Right to reach MBA Auditorium. " );
		db.insert(tab_name, null, values);
		
		values.put(to, exam_center);
		values.put(route, "1. Walk Straight to reach Examination Center. " );
		db.insert(tab_name, null, values);
		
		values.put(to, health_center);
		values.put(route, "1. Walk Straight and take first right. \n 2. Walk Straight and Take Last before left." +
				"\n 3. Walk Straight to reach Health Center " );
		db.insert(tab_name, null, values);
	//From Canteen
		values.put(from,canteen);
		values.put(to, red_build_sbi);
		values.put(route, "1. Walk Straight and to reach Red Building SBI. " );
		db.insert(tab_name, null, values);
		
		values.put(to, vivek_audi);
		values.put(route, "1. Walk Straight and take Right. \n 2. Walk Straight to reach Vivekanda Auditorium. " );
		db.insert(tab_name, null, values);
		
		values.put(to, exam_center);
		values.put(route, "1. Walk Straight and take Left. \n 2. Walk Straight to reach Vivekanda Auditorium. " );
		db.insert(tab_name, null, values);
	//From Alumini
		values.put(from,alumini);
		values.put(to, vivek_audi);
		values.put(route, "1. Walk Straight and to Vivekanda Auditorium. " );
		db.insert(tab_name, null, values);
		
		values.put(to, health_center);
		values.put(route, "1. Walk Straight and take Left. \n 2. Walk Straight to take last before left. " +
				"\n 3. Walk Straight to reach Health Center ");
		db.insert(tab_name, null, values);
		
		values.put(to, mba_audi);
		values.put(route, "1. Walk Straight and to reach MBA Auditorium. " );
		db.insert(tab_name, null, values);
		
	//From Mba Audi	
		values.put(from,mba_audi);
		values.put(to, vivek_audi);
		values.put(route, "1. Walk Straight and to reach Vivekanda Auditorium. " );
		db.insert(tab_name, null, values);
		
		values.put(to, health_center);
		values.put(route, "1. Walk Straight and take Right. \n 2. Walk Straight to take last before left. " +
				"\n 3. Walk Straight to reach Health Center ");
		db.insert(tab_name, null, values);
		
		values.put(to,alumini);
		values.put(route, "1. Walk Straight and to Alumini Center. " );
		db.insert(tab_name, null, values);
	//From Health Center
		values.put(from,health_center);
		values.put(to, vivek_audi);
		values.put(route, "1. Walk Straight and take Right. \n 2. Walk Straight to take last right. " +
				"\n 3. Walk Straight to Vivekanda Auditorium ");
		db.insert(tab_name, null, values);
		
		values.put(to, alumini);
		values.put(route, "1. Walk Straight and take Right. \n 2. Walk Straight to take last left. " +
				"\n 3. Walk Straight to reach Alumini Center ");
		db.insert(tab_name, null, values);
		
		values.put(from,health_center);
		values.put(to, mba_audi);
		values.put(route, "1. Walk Straight and take Right. \n 2. Walk Straight to take last right. " +
				"\n 3. Walk Straight to MBA Auditorium ");
		db.insert(tab_name, null, values);	
	//From Exam Center
		values.put(from,exam_center);
		values.put(to, red_build_sbi);
		values.put(route, "1. Walk Straight you will meet cross road. \n 2. Take Right and Walk Straight" +
				" to reach Red Building SBI" );
		db.insert(tab_name, null, values);
		
		values.put(to, canteen);
		values.put(route, "1. Walk Straight you will meet cross road. \n 2. Take Left and Walk Straight" +
				" to reach Canteen" );
		db.insert(tab_name, null, values);
		
		values.put(to, vivek_audi);
		values.put(route, "1. Walk Straight you will meet cross road. \n 2. Walk Straight further" +
				" to reach Vivekanda Auditorium" );
		db.insert(tab_name, null, values);
		
	}
	
	public String getRoute(String aFrom, String aTo)
	{
		SQLiteDatabase db=this.getReadableDatabase();
		String query="SELECT * FROM " + tab_name + " WHERE "+from + "='" + aFrom +"' AND " + to +
				"='" + aTo + "'";
		Cursor cursor=db.rawQuery(query, null);
		String tname = "";
		if(cursor != null)
		{
			cursor.moveToFirst();
			tname=cursor.getString(2);
		}
		else
		{
			tname="Error";
		}
		cursor.close();
		db.close();
		return tname;
	}
	
	public Bitmap getImage()
	{
		SQLiteDatabase db=this.getReadableDatabase();
		String query="SELECT * FROM "+map_tab;
		Cursor cursor=db.rawQuery(query, null);
		if(cursor!= null)
			cursor.moveToFirst();
		byte[] img1=cursor.getBlob(0);
		Bitmap b1=BitmapFactory.decodeByteArray(img1, 0, img1.length);
		db.close();
		return b1;
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

}
