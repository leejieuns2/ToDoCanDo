package ddwucom.mobile.final_project.ma02_20170969.ToDo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ToDoDBHelper extends SQLiteOpenHelper {

	private final String TAG = "ToDoDBHelper";

	private final static String DB_NAME = "todo_db";
	public final static String TABLE_NAME = "todo_table";
	public final static String COL_ID = "_id";
	public final static String COL_DATE = "todoDate";
	public final static String COL_TIME = "todoTime";
	public final static String COL_TITLE = "title";
	public final static String COL_LOC = "location";
	public final static String COL_CAT = "category";
	public final static String COL_MEMO = "memo";
	public final static String COL_LINK = "link";

	public ToDoDBHelper(Context context) {
		super(context, DB_NAME, null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String createSql = "create table " + TABLE_NAME + " ( " +  COL_ID + " integer primary key autoincrement,"
				+ COL_DATE + " TEXT, " + COL_TIME + " TEXT, " + COL_TITLE + " TEXT, " +  COL_LOC + " TEXT, " +
				COL_CAT + " TEXT, " + COL_MEMO + " TEXT, " + COL_LINK + " TEXT);";
		Log.d(TAG, createSql);
		db.execSQL(createSql);

		db.execSQL("insert into " + TABLE_NAME + " values (null, '2019년 11월 8일', '하루종일', '모바일응용 과제', '동덕여자대학교 인문관',  '과제', '빨리하자', 'https://www.youtube.com/channel/UC9kmlDcqksaOnCkC_qzGacA')");
        db.execSQL("insert into " + TABLE_NAME + " values (null, '2019년 11월 9일', '하루종일', '알고리즘 과제', '동덕여자대학교 인문관', '과제', 'Clock', 'https://www.youtube.com/channel/UC9JU9mfYpg4KqxNZtD91GQg')");
        db.execSQL("insert into " + TABLE_NAME + " values (null, '2019년 11월 10일', '23시 00분', '데베프 팀플', '동덕여자대학교 3층 랩실', '과제', '주 2일이상 만남', 'https://www.youtube.com/channel/UCPWFxcwPliEBMwJjmeFIDIg')");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(db);
	}
}


