package ddwucom.mobile.final_project.ma02_20170969.Cal;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import ddwucom.mobile.final_project.ma02_20170969.R;

public class AllCalendarActivity extends AppCompatActivity {

	final static String TAG = "AllCalendarActivity";

	ListView lvCal = null;
	CalDBHelper helper;
	Cursor cursor;
	CustomCursorAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_all_cal);
		lvCal = (ListView)findViewById(R.id.lvCal);

		helper = new CalDBHelper(this);

//		  SimpleCursorAdapter 객체 생성
		adapter = new CustomCursorAdapter( this, R.layout.custom_adapter_layout, null);

		lvCal.setAdapter(adapter);

//		리스트 뷰 클릭 처리
		lvCal.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(AllCalendarActivity.this, UpdateCalActivity.class);
				intent.putExtra("id", id);
				startActivity(intent);
			}
		});


//		리스트 뷰 롱클릭 처리
		lvCal.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

				final long targetId = id;	// id 값을 다이얼로그 객체 내부에서 사용하기 위하여 상수로 선언
				TextView tvTitle = view.findViewById(R.id.tvTitle);	// 리스트 뷰의 클릭한 위치에 있는 뷰 확인

				String dialogMessage = "'" + tvTitle.getText().toString() + "' DELETE?";	// 클릭한 위치의 뷰에서 문자열 값 확인

				new AlertDialog.Builder(AllCalendarActivity.this).setTitle("삭제 확인")
						.setMessage(dialogMessage)
						.setPositiveButton("삭제", new DialogInterface.OnClickListener() {

							//							삭제 수행
							@Override
							public void onClick(DialogInterface dialog, int which) {
								SQLiteDatabase db = helper.getWritableDatabase();

								String whereClause = CalDBHelper.COL_ID + "=?";
								String[] whereArgs = new String[] { String.valueOf(targetId) };

								db.delete(CalDBHelper.TABLE_NAME, whereClause, whereArgs);
								helper.close();
								readAllContacts();		// 삭제 상태를 반영하기 위하여 전체 목록을 다시 읽음
							}
						})
						.setNegativeButton("취소", null)
						.show();
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
		cursor = db.rawQuery("select * from " + CalDBHelper.TABLE_NAME, null);

		adapter.changeCursor(cursor);
		helper.close();
	}
}



