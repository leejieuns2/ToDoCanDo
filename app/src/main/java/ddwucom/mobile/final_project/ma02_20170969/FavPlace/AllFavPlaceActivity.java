package ddwucom.mobile.final_project.ma02_20170969.FavPlace;

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
import ddwucom.mobile.final_project.ma02_20170969.ToDo.AllToDoActivity;
import ddwucom.mobile.final_project.ma02_20170969.ToDo.UpdateToDoActivity;

public class AllFavPlaceActivity extends AppCompatActivity {

	final static String TAG = "AllFavPlaceActivity";

	ListView lvFavPlace = null;
	FavPlaceDBHelper helper;
	Cursor cursor;
	PlaceCursorAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fav_place);
		lvFavPlace = (ListView)findViewById(R.id.lvFavPlace);

		helper = new FavPlaceDBHelper(this);

//		  SimpleCursorAdapter 객체 생성
		adapter = new PlaceCursorAdapter( this, R.layout.place_adapter_layout, null);

		lvFavPlace.setAdapter(adapter);

		//		리스트 뷰 클릭 처리
		lvFavPlace.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent();
				TextView tvPlace = view.findViewById(R.id.tvFavPlace);
				String location = tvPlace.getText().toString();
				intent.putExtra("result_data", location);
				setResult(RESULT_OK, intent);
				finish();
			}
		});

//		리스트 뷰 롱클릭 처리
		lvFavPlace.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

				final long targetId = id;	// id 값을 다이얼로그 객체 내부에서 사용하기 위하여 상수로 선언
				TextView tvName = view.findViewById(R.id.tvFavName);	// 리스트 뷰의 클릭한 위치에 있는 뷰 확인

				String dialogMessage = "'" + tvName.getText().toString() + "' DELETE?";	// 클릭한 위치의 뷰에서 문자열 값 확인

				new AlertDialog.Builder(AllFavPlaceActivity.this).setTitle("삭제 확인")
						.setMessage(dialogMessage)
						.setPositiveButton("삭제", new DialogInterface.OnClickListener() {

							//							삭제 수행
							@Override
							public void onClick(DialogInterface dialog, int which) {
								SQLiteDatabase db = helper.getWritableDatabase();

								String whereClause = FavPlaceDBHelper.COL_ID + "=?";
								String[] whereArgs = new String[] { String.valueOf(targetId) };

								db.delete(FavPlaceDBHelper.TABLE_NAME, whereClause, whereArgs);
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

	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btnInsertFavPlace:
				Intent intent = new Intent(this, InsertFavPlaceActivity.class);
				startActivity(intent);
		}
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
		setResult(RESULT_CANCELED);
		if (cursor != null) cursor.close();
	}

	private void readAllContacts() {
//        DB에서 데이터를 읽어와 Adapter에 설정
		SQLiteDatabase db = helper.getReadableDatabase();
		cursor = db.rawQuery("select * from " + FavPlaceDBHelper.TABLE_NAME, null);

		adapter.changeCursor(cursor);
		helper.close();
	}
}



