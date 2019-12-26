// 과제명: DB 사용 앱 개발하기
// 분반: 02 분반
// 학번: 20170969 성명: 이지은
// 제출일: 2019년 9월 26일

package ddwucom.mobile.final_project.ma02_20170969.ToDo;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import ddwucom.mobile.final_project.ma02_20170969.BlogAPI.BlogSearchActivity;
import ddwucom.mobile.final_project.ma02_20170969.R;
import ddwucom.mobile.final_project.ma02_20170969.SearchMapActivity;

public class InsertToDoActivity extends AppCompatActivity {

	public static final String TAG = "InsertToDoActivity";
	final static int BLOG_LINK_CODE = 100;
	final static int MAP_LINK_CODE = 200;

	ToDoDBHelper helper;
	EditText etTodoDate;
	EditText ettodoTime;
	EditText etTitle;
	EditText etLocation;
	EditText etCategory;
	EditText etMemo;
	EditText etLink;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_insert_todo);

//      DBHelper 생성
		helper = new ToDoDBHelper(this);

		etTodoDate = (EditText) findViewById(R.id.etDate);
		ettodoTime = (EditText) findViewById(R.id.etTime);
		etTitle = (EditText) findViewById(R.id.etTitle);
		etLocation = (EditText) findViewById(R.id.etLocation);
		etCategory = (EditText) findViewById(R.id.etCategory);
		etLink = (EditText) findViewById(R.id.etLink);
		etMemo = (EditText) findViewById(R.id.etMemo);
	}

	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btnNewToDoSave:
				SQLiteDatabase db = helper.getWritableDatabase();

				String todoDate = etTodoDate.getText().toString();
				String todoTime = ettodoTime.getText().toString();
				String title = etTitle.getText().toString();
				String location = etLocation.getText().toString();
				String category = etCategory.getText().toString();
				String link = etLink.getText().toString();
				String memo = etMemo.getText().toString();

				//			DB 메소드를 사용할 경우
				ContentValues row = new ContentValues();
				row.put(helper.COL_DATE, todoDate);
				row.put(helper.COL_TIME, todoTime);
				row.put(helper.COL_TITLE, title);
				row.put(helper.COL_LINK, link);
				row.put(helper.COL_CAT, category);
				row.put(helper.COL_LOC, location);
				row.put(helper.COL_MEMO, memo);

				long result = db.insert(ToDoDBHelper.TABLE_NAME, null, row);

				//			SQL query를 직접 사용할 경우
				//			db.execSql("insert into " + ContactDBHelper.TABLE_NAME
				//									  + " values(null, '" + name + "', '" + link +"', '" + category "');", null);

				helper.close();

				if (result > 0) {
					etTitle.setText("");
					etLocation.setText("");
					etCategory.setText("");
					etMemo.setText("");
					etLink.setText("");
					//				ivImage.setImageResource(R.drawable.youtube_video);
					Toast.makeText(this, "저장 완료", Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(this, "저장 실패", Toast.LENGTH_SHORT).show();
				}
				break;
			case R.id.btnSLActivity:
				Intent mapIntent = new Intent(this, SearchMapActivity.class);
				startActivityForResult(mapIntent, MAP_LINK_CODE);
				Log.d(TAG, "SearchMapActivity start!");
				break;
			case R.id.btnSBActivity:
				Intent blogIntent = new Intent(this, BlogSearchActivity.class);
				startActivityForResult(blogIntent, BLOG_LINK_CODE);
				break;
			case R.id.btnNewToDoClose:
				finish();
				break;
		}
		finish();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
		switch (requestCode) {
			case BLOG_LINK_CODE:
				if(resultCode == RESULT_OK) {
					String resultData = data.getStringExtra("result_data");
					Toast.makeText(this, "Search Success !", Toast.LENGTH_SHORT).show();
					etLink.setText(resultData);
				}
				break;
			case MAP_LINK_CODE:
				if(resultCode == RESULT_OK) {
					String resultData = data.getStringExtra("result_data");
					Toast.makeText(this, "Search Success !", Toast.LENGTH_SHORT).show();
					etLocation.setText(resultData);
				}
				break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
}
