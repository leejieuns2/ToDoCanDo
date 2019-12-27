package ddwucom.mobile.final_project.ma02_20170969.ToDo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.util.SparseLongArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import ddwucom.mobile.final_project.ma02_20170969.Cal.CalDBHelper;
import ddwucom.mobile.final_project.ma02_20170969.R;

public class AllToDoActivity extends AppCompatActivity {

	final static String TAG = "AllToDoActivity";

	ListView lvToDo = null;
	ToDoDBHelper helper;
	Cursor cursor;
	CustomChoiceListViewAdapter adapter;
	CheckBox checkBox;
	Date currentTime;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_all_todo);
		lvToDo = (ListView)findViewById(R.id.lvToDo);
		checkBox = (CheckBox)findViewById(R.id.cbToDo);
		helper = new ToDoDBHelper(this);

//		  SimpleCursorAdapter 객체 생성
		adapter = new CustomChoiceListViewAdapter( this, R.layout.todo_adapter_layout, null);

		lvToDo.setAdapter(adapter);

//		리스트 뷰 클릭 처리
		lvToDo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				final long targetId = id;	// id 값을 다이얼로그 객체 내부에서 사용하기 위하여 상수로 선언
				SQLiteDatabase db = helper.getReadableDatabase();

				ToDoDto toDoDto = new ToDoDto();
				toDoDto.setId(id);
				Cursor cursor = db.rawQuery( "select * from " + ToDoDBHelper.TABLE_NAME + " where " + ToDoDBHelper.COL_ID + "=?", new String[] { String.valueOf(id) });

				while (cursor.moveToNext()) {
					toDoDto.setDueDate( cursor.getString( cursor.getColumnIndex(helper.COL_DUE) ) );
					toDoDto.setTitle( cursor.getString( cursor.getColumnIndex(helper.COL_TITLE) ) );
					toDoDto.setCategory( cursor.getString( cursor.getColumnIndex(helper.COL_CAT) ) );
					toDoDto.setMemo( cursor.getString( cursor.getColumnIndex(helper.COL_MEMO) ) );
				}

				String dialogMessage = 	"TODO : " + toDoDto.getTitle() + "\n" +
						"DUEDATE : " + toDoDto.getDueDate() + "\n" +
						"CATEGORY : " + toDoDto.getCategory() + "\n" +
						"MEMO : " + toDoDto.getMemo() + "\n" ; // 클릭한 위치의 뷰에서 문자열 값 확인
				new AlertDialog.Builder(AllToDoActivity.this).setTitle("ABOUT TODO")
						.setMessage(dialogMessage)
						.setPositiveButton("확인", null)
						.setNegativeButton("삭제", new DialogInterface.OnClickListener() {
								//							삭제 수행
								@Override
								public void onClick(DialogInterface dialog, int which) {
									SQLiteDatabase db = helper.getWritableDatabase();

									String whereClause = ToDoDBHelper.COL_ID + "=?";
									String[] whereArgs = new String[] { String.valueOf(targetId) };

									db.delete(ToDoDBHelper.TABLE_NAME, whereClause, whereArgs);
									helper.close();
									readAllContacts();		// 삭제 상태를 반영하기 위하여 전체 목록을 다시 읽음
								}
							})
						.show();
			}
		});

//		리스트 뷰 롱클릭 처리
		lvToDo.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(AllToDoActivity.this, UpdateToDoActivity.class);
				intent.putExtra("id", id);
				startActivity(intent);
				return true;
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		readAllContacts();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
//        cursor 사용 종료
		if (cursor != null) cursor.close();
	}


	private void readAllContacts() {
//        DB에서 데이터를 읽어와 Adapter에 설정
		SQLiteDatabase db = helper.getReadableDatabase();
		currentTime = Calendar.getInstance().getTime();
		String date_text = new SimpleDateFormat("yyyy년 MM월 dd일", Locale.getDefault()).format(currentTime);
		Log.d(TAG, date_text);
		cursor = db.rawQuery("select * from " + ToDoDBHelper.TABLE_NAME, null);

		adapter.changeCursor(cursor);
		helper.close();
	}
}



