// 과제명: DB 사용 앱 개발하기
// 분반: 02 분반
// 학번: 20170969 성명: 이지은
// 제출일: 2019년 9월 26일

package ddwucom.mobile.final_project.ma02_20170969.Cal;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import ddwucom.mobile.final_project.ma02_20170969.BlogAPI.BlogSearchActivity;
import ddwucom.mobile.final_project.ma02_20170969.FavPlace.AllFavPlaceActivity;
import ddwucom.mobile.final_project.ma02_20170969.R;
import ddwucom.mobile.final_project.ma02_20170969.MapAPI.SearchMapActivity;

public class InsertCalActivity extends AppCompatActivity {

	public static final String TAG = "InsertToDoActivity";
	private DatePickerDialog.OnDateSetListener dateCallbackMethod;
    private TimePickerDialog.OnTimeSetListener timeCallbackMethod;
	final static int BLOG_LINK_CODE = 100;
	final static int MAP_LINK_CODE = 200;
    final static int FAV_LINK_CODE = 300;

	CalDBHelper helper;
	TextView tvTodoDate;
	TextView tvTodoTime;
	EditText etTitle;
	EditText etLocation;
	EditText etCategory;
	EditText etMemo;
	EditText etLink;
	Date currentTime;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_insert_todo);
		currentTime = Calendar.getInstance().getTime();
		String date_text = new SimpleDateFormat("yyyy년 MM월 dd일", Locale.getDefault()).format(currentTime);
        String time_text = new SimpleDateFormat("hh시 mm분", Locale.getDefault()).format(currentTime);

		Log.d(TAG, date_text + ", " + time_text);

//      DBHelper 생성
		helper = new CalDBHelper(this);
		tvTodoDate = (TextView) findViewById(R.id.tvDate);
		tvTodoDate.setText(date_text);
		tvTodoTime = (TextView) findViewById(R.id.tvTime);
		tvTodoTime.setText(time_text);

		etTitle = (EditText) findViewById(R.id.etTitle);
		etLocation = (EditText) findViewById(R.id.etLocation);
		etCategory = (EditText) findViewById(R.id.etCategory);
		etLink = (EditText) findViewById(R.id.etLink);
		etMemo = (EditText) findViewById(R.id.etMemo);

		this.InitializeListener();
	}

	public void InitializeListener()
	{
		dateCallbackMethod = new DatePickerDialog.OnDateSetListener()
		{
			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
			{
				tvTodoDate.setText(year + "년" + (monthOfYear + 1) + "월" + dayOfMonth + "일");
			}
		};

        timeCallbackMethod = new TimePickerDialog.OnTimeSetListener()
        {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute)
            {
                tvTodoTime.setText(hourOfDay + "시" + minute + "분");
            }
        };
	}

	public void OnClickHandler(View v)
	{
        Calendar calendar = new GregorianCalendar(Locale.KOREA);
	    switch (v.getId()) {
            case(R.id.btnDateDialog):
                DatePickerDialog datePickerDialog = new DatePickerDialog(this, dateCallbackMethod, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

                datePickerDialog.show();
                break;
            case(R.id.btnTimeDialog):
                TimePickerDialog timePickerDialog = new TimePickerDialog(this, timeCallbackMethod, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
                timePickerDialog.show();
                break;
        }


	}

	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btnNewToDoSave:
				SQLiteDatabase db = helper.getWritableDatabase();

				String todoDate = tvTodoDate.getText().toString();
				String todoTime = tvTodoTime.getText().toString();
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

				long result = db.insert(CalDBHelper.TABLE_NAME, null, row);

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
				finish();
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
            case R.id.btnFavActivity:
                Intent favIntent = new Intent(this, AllFavPlaceActivity.class);
                startActivityForResult(favIntent, FAV_LINK_CODE);
                break;
			case R.id.btnNewToDoClose:
				finish();
				break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
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
					String location = data.getStringExtra("address");
					Toast.makeText(this, "Search Success ! , " + data.getStringExtra("name"), Toast.LENGTH_SHORT).show();
					etLocation.setText(location);
				}
				break;
            case FAV_LINK_CODE:
                if(resultCode == RESULT_OK) {
					String location = data.getStringExtra("result_data");
                    Toast.makeText(this, "자주 가는 위치에서 가져왔음 !", Toast.LENGTH_SHORT).show();
                    etLocation.setText(location);
                }
                break;
		}
	}
}
