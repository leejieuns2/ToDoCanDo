package ddwucom.mobile.final_project.ma02_20170969.FavPlace;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class FavPlaceDBHelper extends SQLiteOpenHelper {

	private final String TAG = "FavPlaceDBHelper";

	private final static String DB_NAME = "fav_db";
	public final static String TABLE_NAME = "fav_table";
	public final static String COL_ID = "_id";
	public final static String COL_NAME = "favName";
	public final static String COL_PLACE = "favPlace";

	public FavPlaceDBHelper(Context context) {
		super(context, DB_NAME, null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String createSql = "create table " + TABLE_NAME + " ( " +  COL_ID + " integer primary key autoincrement,"
				+ COL_NAME + " TEXT, " + COL_PLACE + " TEXT);";
		Log.d(TAG, createSql);
		db.execSQL(createSql);

		db.execSQL("insert into " + TABLE_NAME + " values (null, '집', '서울 성북구')");
		db.execSQL("insert into " + TABLE_NAME + " values (null, '학교', '동덕여자대학교')");
		db.execSQL("insert into " + TABLE_NAME + " values (null, '본가', '경기도 고양시')");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(db);
	}
}


