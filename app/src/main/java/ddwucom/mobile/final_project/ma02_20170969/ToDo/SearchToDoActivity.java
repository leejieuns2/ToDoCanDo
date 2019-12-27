package ddwucom.mobile.final_project.ma02_20170969.ToDo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import ddwucom.mobile.final_project.ma02_20170969.R;

public class SearchToDoActivity extends AppCompatActivity {

	ToDoDBHelper helper;
	CalendarView calendarView;
	Date searchDate;
	ListView lvSearchToDo = null;
	CustomChoiceListViewAdapter adapter;
	Cursor cursor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_todo);
		
		helper = new ToDoDBHelper(this);
		lvSearchToDo = findViewById(R.id.lvSearchToDo);
		adapter = new CustomChoiceListViewAdapter( this, R.layout.todo_adapter_layout, null);

		//CalendarView 인스턴스 만들기
		calendarView = findViewById(R.id.calendarView);

		//CalendarView Listener 생성
		calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
			@Override
			public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
				// TODO Auto-generated method stub
				searchDate = new Date();
				searchDate.setTime(view.getDate());
				String date_text = new SimpleDateFormat("yyyy년 MM월 dd일", Locale.getDefault()).format(searchDate);
				Toast.makeText(SearchToDoActivity.this, date_text, Toast.LENGTH_SHORT).show();

				SQLiteDatabase db = helper.getReadableDatabase();
				/*like 을 사용하여 입력한 문자를 포함한 모든 이름 검색*/
				cursor = db.rawQuery("select * from " + ToDoDBHelper.TABLE_NAME + " where " + ToDoDBHelper.COL_DUE + "=?", new String[] { date_text });


			/*검색 결과를 하나만 표시하므로 ContactDto 사용
			여러 개의 검색 결과를 표시하여야 할 경우 결과 개수 만큼 dto 생성 후 ArrayList 등에 저장 필요*/
				ToDoDto item = new ToDoDto();

//			String createSql = "create table " + TABLE_NAME + " ( " +  COL_ID + " integer primary key autoincrement,"
//					+ COL_NAME + " TEXT, " + COL_IMAGE + " INTEGER, " + COL_SUB + " TEXT, " + COL_LINK + " TEXT, " + COL_EX + " TEXT, " + COL_CAT + " TEXT);";

				while(cursor.moveToNext()) {
					item.setId(cursor.getInt(cursor.getColumnIndex(helper.COL_ID)));
					item.setDueDate(cursor.getString(cursor.getColumnIndex(helper.COL_DUE)));
					item.setTitle(cursor.getString(cursor.getColumnIndex(helper.COL_TITLE)));
					item.setMemo(cursor.getString(cursor.getColumnIndex(helper.COL_MEMO)));
					item.setCategory(cursor.getString(cursor.getColumnIndex(helper.COL_CAT)));
				}

				/*필요에 따라 리스트뷰로 대체*/
				//tvSearchResult.setText(item.toString() + "\n"); 리스트뷰로 교체해야함
				lvSearchToDo.setAdapter(adapter);

				//		리스트 뷰 클릭 처리
				lvSearchToDo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
						Intent intent = new Intent(SearchToDoActivity.this, UpdateToDoActivity.class);
						intent.putExtra("id", id);
						startActivity(intent);
					}
				});


				//		리스트 뷰 롱클릭 처리
				lvSearchToDo.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
					@Override
					public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

						final long targetId = id;	// id 값을 다이얼로그 객체 내부에서 사용하기 위하여 상수로 선언
						TextView tvTitle = view.findViewById(R.id.tvTitle);	// 리스트 뷰의 클릭한 위치에 있는 뷰 확인

						String dialogMessage = "'" + tvTitle.getText().toString() + "' DELETE?";	// 클릭한 위치의 뷰에서 문자열 값 확인

						new AlertDialog.Builder(SearchToDoActivity.this).setTitle("삭제 확인")
								.setMessage(dialogMessage)
								.setPositiveButton("삭제", new DialogInterface.OnClickListener() {

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
								.setNegativeButton("취소", null)
								.show();
						return true;
					}
				});
				cursor.close();
				helper.close();
			}
		});
	}


	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.btnClose:
			finish();
			break;
		}
	}

	private void readAllContacts() {
//        DB에서 데이터를 읽어와 Adapter에 설정
		SQLiteDatabase db = helper.getReadableDatabase();
		cursor = db.rawQuery("select * from " + ToDoDBHelper.TABLE_NAME, null);

		adapter.changeCursor(cursor);
		helper.close();
	}
	
	
}
