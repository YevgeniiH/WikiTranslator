package ua.cn.stu.gaponAlexeenko.wikitranslator;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseEntity extends SQLiteOpenHelper {
	public static final String TABLE_CURRENCY = "translator";
	public static final String DATABASE = "translatorDb";
	public static final int VERSION = 1;
	
	public class ConvertStructure{
		private int _id;
		public String name;
		public String designation;
		
		public int getId(){
			return _id;
		}
	}
	
	public DatabaseEntity(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}
	

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE translator (" +
				   "_id 		INTEGER PRIMARY KEY AUTOINCREMENT," +
				   "name 		TEXT 	NOT NULL," +
				   "designation	TEXT	NOT NULL);"
				);
		ContentValues values = new ContentValues();
		values.put("name", 	"Рус"); values.put("designation", "RU");
		db.insert(TABLE_CURRENCY, null, values);
		values.put("name", 	"Англ"); values.put("designation", "EN");
		db.insert(TABLE_CURRENCY, null, values);
		values.put("name", 	"Итал"); values.put("designation", "IT");
		db.insert(TABLE_CURRENCY, null, values);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS translator;");
		onCreate(db);
	}

}
