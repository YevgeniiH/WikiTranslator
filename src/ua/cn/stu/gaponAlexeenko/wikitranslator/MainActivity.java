package ua.cn.stu.gaponAlexeenko.wikitranslator;

import java.util.Vector;
import ua.cn.stu.gaponAlexeenko.wikitranslator.DatabaseEntity;
import ua.cn.stu.gaponAlexeenko.wikitranslator.TranslateService;
import ua.cn.stu.gaponAlexeenko.wikitranslator.R;
import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	DatabaseEntity dbHelper;
	SQLiteDatabase db;
	
	Vector<String> vals;
	Vector<String> names;
	
	Button btnGo;
	ProgressBar bar;
	EditText edtIn;
	TextView txtOut;
	Spinner spinnerIn;
	Spinner spinnerOut;
	
	void init(){
		spinnerIn = (Spinner) findViewById(R.id.spinnerIn);
		spinnerOut = (Spinner) findViewById(R.id.spinnerOut);
		edtIn = (EditText) findViewById(R.id.edtIn);
		txtOut = (TextView) findViewById(R.id.txtOut);
		bar = (ProgressBar) findViewById(R.id.progressBar);
		bar.setVisibility(View.INVISIBLE);
		btnGo = (Button) findViewById(R.id.btnGo);
		btnGo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				bar.setVisibility(View.VISIBLE);
				
				String from = vals.get(spinnerIn.getSelectedItemPosition());
				String to = vals.get(spinnerOut.getSelectedItemPosition());
				String value = edtIn.getText().toString();
				try{
					String result = TranslateService.convert(from, to, value);
					txtOut.setText(result);;
				} catch (Exception e){
					Toast.makeText(MainActivity.this, "Провеьте подключение к интернету!", Toast.LENGTH_SHORT).show();
				}
				bar.setVisibility(View.INVISIBLE);
			}
		});
		
		Cursor query = db.query(DatabaseEntity.TABLE_CURRENCY, null, null, null, null, null, "name");
		vals = new Vector<String>();
		names = new Vector<String>();
		
		while (query.moveToNext()){
			int iName = query.getColumnIndex("name");
			int iDesignation = query.getColumnIndex("designation");
			
			String name = query.getString(iName);
			String designation = query.getString(iDesignation);
			names.add(name);
			vals.add(designation);
		}
	
		String[] s = new String[names.size()];
		names.toArray(s);
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, s);
		spinnerIn.setAdapter(adapter);
		spinnerOut.setAdapter(adapter);
		
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		dbHelper = new DatabaseEntity(this, DatabaseEntity.DATABASE, null, DatabaseEntity.VERSION);
		db = dbHelper.getReadableDatabase();
		
		init();
	}
}
