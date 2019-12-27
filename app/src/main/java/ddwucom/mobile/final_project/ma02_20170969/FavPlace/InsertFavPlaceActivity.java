// 과제명: DB 사용 앱 개발하기
// 분반: 02 분반
// 학번: 20170969 성명: 이지은
// 제출일: 2019년 9월 26일

package ddwucom.mobile.final_project.ma02_20170969.FavPlace;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import ddwucom.mobile.final_project.ma02_20170969.R;

public class InsertFavPlaceActivity extends AppCompatActivity {

	public static final String TAG = "InsertFavPlaceActivity";
	final static int MAP_LINK_CODE = 200;
	FavPlaceDBHelper helper;
	EditText etName;
	EditText etPlace;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_insert_fav);

//      DBHelper 생성
		helper = new FavPlaceDBHelper(this);

		etName = (EditText) findViewById(R.id.etFavName);
		etPlace = (EditText) findViewById(R.id.etFavPlace);
	}

	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btnNewFavSave:
				SQLiteDatabase db = helper.getWritableDatabase();

				String name = etName.getText().toString();
				String place = etPlace.getText().toString();
				//			DB 메소드를 사용할 경우
				ContentValues row = new ContentValues();
				row.put(helper.COL_NAME, name);
				row.put(helper.COL_PLACE, place);

				long result = db.insert(FavPlaceDBHelper.TABLE_NAME, null, row);
				helper.close();

				if (result > 0) {
					etName.setText("");
					etPlace.setText("");
					//				ivImage.setImageResource(R.drawable.youtube_video);
					Toast.makeText(this, "저장 완료", Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(this, "저장 실패", Toast.LENGTH_SHORT).show();
				}
				break;
			case R.id.btnNewFavClose:
				finish();
				break;
		}
		finish();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
		switch (requestCode) {
			case MAP_LINK_CODE:
				if(resultCode == RESULT_OK) {
					String resultData = data.getStringExtra("result_data");
					Toast.makeText(this, "Search Success !", Toast.LENGTH_SHORT).show();
					etPlace.setText(resultData);
				}
				break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
}
