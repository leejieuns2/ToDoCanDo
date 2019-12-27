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
	public final static String COL_DUE = "DueDate";
	public final static String COL_TITLE = "title";
	public final static String COL_CAT = "category";
	public final static String COL_MEMO = "memo";



	public ToDoDBHelper(Context context) {
		super(context, DB_NAME, null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String createSql = "create table " + TABLE_NAME + " ( " +  COL_ID + " integer primary key autoincrement,"
				+ COL_DUE + " TEXT, " + COL_TITLE + " TEXT, " + COL_CAT + " TEXT, " + COL_MEMO + " TEXT);";
		Log.d(TAG, createSql);
		db.execSQL(createSql);

		db.execSQL("insert into " + TABLE_NAME + " values (null, '2019년 12월 24일', '데베프 최종보고서', '과제', '이클래스 제출')");
		db.execSQL("insert into " + TABLE_NAME + " values (null, '2019년 12월 27일', '학교 일찍 오기', '생활', '과제 끝내야돼')");
		db.execSQL("insert into " + TABLE_NAME + " values (null, '2019년 12월 27일', '특별근로', '알바', '10-4시')");
		db.execSQL("insert into " + TABLE_NAME + " values (null, '2019년 12월 27일', '일찍 자기', '생활', '드디어 일찍 잘 수 있다,,,,')");
		db.execSQL("insert into " + TABLE_NAME + " values (null, '2019년 12월 27일', '모바일응용 플젝', '과제', '이클래스 제출')");
		db.execSQL("insert into " + TABLE_NAME + " values (null, '2020년 1월 3일', '알고리즘 최종과제', '과제', '과제서버 제출')");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(db);
	}
}


