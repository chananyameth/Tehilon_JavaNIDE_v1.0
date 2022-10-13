package com.chananya.tehilon;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.app.*;
import android.os.*;
import android.widget.*;
import android.widget.AdapterView.*;
import android.view.*;
import android.graphics.*;
import java.io.*;
import com.chananya.tehilon.R;

public class MainActivity extends Activity {
  //views
  private ListView listView;
  private TextView title;
  private Spinner spinner;
  private Button resizeB;
  
  //other
  private String[] pesukim;
  private MyAdapter1 adapter;
  private LayoutInflater inflater;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    connectViews();
    readFiles(1);
    initializeVars();
    setListeners();
  }

  private void connectViews() {
    listView = (ListView) findViewById(R.id.listView);
    spinner = (Spinner) findViewById(R.id.spinner);
    title = (TextView) findViewById(R.id.title);
    resizeB = (Button) findViewById(R.id.resizeB);
  }

  private void readFiles(int versionKind) {
    try {
      InputStream in;
      switch (versionKind) {
        case 1:
          in = getApplicationContext().getAssets().open("psalms_he_nikkud_taamei_hamikra.csv");
          break;
        case 0:
        default:
          in = getApplicationContext().getAssets().open("psalms_he_nikkud.csv");
          break;
      }
      int size = in.available();
      byte[] buffer = new byte[size];
      in.read(buffer);
      in.close();
      pesukim = (new String(buffer)).split("\n");
    } catch (Exception e) {
      toast("Can't open the file");
    }
  }

  private void initializeVars() {
    inflater = this.getLayoutInflater();
  
    String[] items = {"KeterYG","TaameyAshkenaz", "TaameyDavidCLM", "TaameyFrankCLM"}; 
    spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items));
    title.setText("תהילון");
  
    adapter = new MyAdapter1(MainActivity.this, pesukim, TypeFaceId.KeterYG);
    listView.setAdapter(adapter);
    listView.setFastScrollEnabled(true);
    listView.setFastScrollAlwaysVisible(true);
  }

  private void setListeners() {
	spinner.setOnItemSelectedListener(new OnItemSelectedListener()
    {
      @Override
	  public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id)
	  {
       	adapter.setTypeFace(getTypeFaceId(position));
       	adapter.notifyDataSetChanged();
      }
      @Override
      public void onNothingSelected(AdapterView<?> parentView){}
    });
    
    resizeB.setOnClickListener(new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			toast("clicked");
			AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
			View dialogView = inflater.inflate(R.layout.font_dialog, null);
			builder.setView(dialogView);
			
			Spinner fonts = (Spinner) findViewById(R.id.fonts);
    		SeekBar textSize = (SeekBar) findViewById(R.id.textSize);
    		TextView example = (TextView) dialogView.findViewById(R.id.exampleText);
    		
    		String[] items = {"KeterYG","TaameyAshkenaz", "TaameyDavidCLM", "TaameyFrankCLM"}; 
    		//fonts.setAdapter(new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, items));
    
    		example.setTypeface(getTypeFaceFromNum(0));//-----arbitrary!!!!!
			example.setTextSize(30);
			example.setText(pesukim[0].substring(11,75));
			
			AlertDialog alertDialog = builder.create();
			alertDialog.show();
		}
	});
	  
    /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    	@Override
    	public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
    	{
    		// TODO Auto-generated method stub

    		toast("clicked");

    	}
    });*/
  }
  
  private TypeFaceId getTypeFaceId(int n)
  {
  	switch(n)
  	{
          case 0:	return TypeFaceId.KeterYG;
          case 1:	return TypeFaceId.TaameyAshkenaz;
		  case 2:	return TypeFaceId.TaameyDavidCLM;
		  case 3:	return TypeFaceId.TaameyFrankCLM;
            
		  default:	return TypeFaceId.KeterYG;
	  }
  }
  private Typeface getTypeFaceFromNum(int n)
  {
 	 switch(n)
  	{
          case 0:	return Typeface.createFromAsset(this.getAssets(), "fonts/KeterYG-Medium.ttf");
          case 1:	return Typeface.createFromAsset(this.getAssets(), "fonts/TaameyAshkenaz-Medium.ttf");
		  case 2:	return Typeface.createFromAsset(this.getAssets(), "fonts/TaameyDavidCLM-Medium.ttf");
		  case 3:	return Typeface.createFromAsset(this.getAssets(), "fonts/TaameyFrankCLM-Medium.ttf");
            
		  default:	return Typeface.createFromAsset(this.getAssets(), "fonts/KeterYG-Medium.ttf");
	  }
  }

  private void toast(String s) {
    Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
  }

  private void toast_assets_list() {
	try {
      for (int i = 0; i < getAssets().list("").length; ++i) toast(getAssets().list("")[i]);
    }
    catch (Exception e) {
    }
  }
}
