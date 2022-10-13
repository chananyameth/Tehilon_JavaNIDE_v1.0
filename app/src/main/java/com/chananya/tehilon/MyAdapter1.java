package com.chananya.tehilon;

//It's java class ListViewAdpter
//for layout you have to create XML file(listviewlayout.xml)
//inside XMl file create 1 ImageView and 1 TextView

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.SectionIndexer;
import android.graphics.*;
import java.util.*;
import android.widget.*;

public class MyAdapter1 extends BaseAdapter implements SectionIndexer
{
	private Context context;
	private String[] lines;
	private HashMap<String, Integer> map;
	private LayoutInflater inflater;
	
	private Typeface tf;

	public MyAdapter1(Context context, String[] _lines, TypeFaceId tfi)
	{
		this.context = context;
		this.lines = _lines;
		
		setTypeFace(tfi);

		map = new LinkedHashMap<String, Integer>();
		for (int x = 0; x < lines.length; x++) {
			int perek_no = Integer.parseInt(lines[x].substring(7,lines[x].indexOf(":")));
			
			// HashMap will prevent duplicates
            map.putIfAbsent(no_to_letters(perek_no), x);
        }
	}

	public int getCount()
	{
		return lines.length;
	}
	public Object getItem(int position)
	{
		return null;
	}
	public long getItemId(int position)
	{
		return position;
	}
	public View getView(int position, View convertView, ViewGroup parent)
	{
		if(convertView == null)
		{
			inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.listview_item, parent, false);
		}
		
		//Psalms X:Y,pasuk goes on and on..
		String title = lines[position].split(",")[0];
		String pasuk = lines[position].split(",")[1];
		int perek_no = Integer.parseInt(title.substring(7,title.indexOf(":")));
		int pasuk_no = Integer.parseInt(title.substring(title.indexOf(":")+1));
				
		TextView index;
		index = (TextView) convertView.findViewById(R.id.index);
		index.setTypeface(tf);
		index.setText(" " + no_to_letters(perek_no) + "," + no_to_letters(pasuk_no) + ":");
		
		TextView txtemp;
		txtemp = (TextView) convertView.findViewById(R.id.text);
		txtemp.setTypeface(tf);
		txtemp.setText(pasuk);

		return convertView;
	}
	
	//-----Section Indexer-----
	@Override
	public Object[] getSections()
	{
		return map.keySet().toArray();
	}

	@Override
	public int getPositionForSection(int p1)
	{
		return map.get(no_to_letters(p1+1));
	}

	@Override
	public int getSectionForPosition(int p1)
	{
		String title = lines[p1].split(",")[0];
		int perek_no = Integer.parseInt(title.substring(7,title.indexOf(":")));
		
		return perek_no - 1;
	}
	
	
	//-----Helpers-----
	public void setTypeFace(TypeFaceId tfi)
	{
		switch(tfi)
		{
		case KeterYG:
			tf = Typeface.createFromAsset(context.getAssets(), "fonts/KeterYG-Medium.ttf");
			break;
		case TaameyAshkenaz:
			tf = Typeface.createFromAsset(context.getAssets(), "fonts/TaameyAshkenaz-Medium.ttf");
			break;
		case TaameyDavidCLM:
			tf = Typeface.createFromAsset(context.getAssets(), "fonts/TaameyDavidCLM-Medium.ttf");
			break;
		case TaameyFrankCLM:
			tf = Typeface.createFromAsset(context.getAssets(), "fonts/TaameyFrankCLM-Medium.ttf");
			break;
		default:
			tf = Typeface.createFromAsset(context.getAssets(), "fonts/KeterYG-Medium.ttf");
			break;
		}
	}
	
	//converts number to hebrew letters
	private String no_to_letters(int n)
	{
		char[] let = {'א','ב','ג','ד','ה','ו','ז','ח','ט','י','כ','ל','מ','נ','ס','ע','פ','צ','ק','ר','ש','ת'};
		int[] val = {1,2,3,4,5,6,7,8,9,10,20,30,40,50,60,70,80,90,100,200,300,400};
		String ret = "";
		int index = 21;
		while(n > 0){
			if(n == 16){
				ret += "טז";
				n -= 16;
			}
			else if(n == 15){
				ret += "טו";
				n -= 15;
			}
			else if(n >= val[index]){
				ret += let[index];	
				n -= val[index];
			}
			else
				index--;
		}
		/*String last = ret.substring(ret.length()-1, ret.length());
		switch(last){
			case "כ": ret = ret.substring(0, ret.length()-1) + "ך"; break;
			case "מ": ret = ret.substring(0, ret.length()-1) + "ם"; break;
			case "נ": ret = ret.substring(0, ret.length()-1) + "ן"; break;
			case "פ": ret = ret.substring(0, ret.length()-1) + "ף"; break;
			case "צ": ret = ret.substring(0, ret.length()-1) + "ץ"; break;
		}*/
		return ret;
	}
}

enum TypeFaceId
{
	KeterYG,
	TaameyAshkenaz,
	TaameyDavidCLM,
	TaameyFrankCLM	
}