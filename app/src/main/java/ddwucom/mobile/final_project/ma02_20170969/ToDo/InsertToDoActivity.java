
package ddwucom.mobile.final_project.ma02_20170969.ToDo;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import ddwucom.mobile.final_project.ma02_20170969.*;

public class InsertToDoActivity extends AppCompatActivity {

	public static final String TAG = "InsertToDoActivity";
	private DatePickerDialog.OnDateSetListener dateCallbackMethod;

	ToDoDBHelper helper;
	TextView tvDueDate;
	EditText etTitle;
	EditText etCategory;
	EditText etMemo;
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
		helper = new ToDoDBHelper(this);
		tvDueDate = (TextView) findViewById(R.id.tvDueDate);
		tvDueDate.setText(date_text);

		etTitle = (EditText) findViewById(R.id.etTodoTitle);
		etCategory = (EditText) findViewById(R.id.etTodoCat);
		etMemo = (EditText) findViewById(R.id.etToDoMemo);

		this.InitializeListener();
	}

	public void InitializeListener()
	{
		dateCallbackMethod = new DatePickerDialog.OnDateSetListener()
		{
			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
			{
				tvDueDate.setText(year + "년" + (monthOfYear + 1) + "월" + dayOfMonth + "일");
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
        }
	}

	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btnNewToDoSave:
				SQLiteDatabase db = helper.getWritableDatabase();

				String todoDate = tvDueDate.getText().toString();
				String title = etTitle.getText().toString();
				String category = etCategory.getText().toString();
				String memo = etMemo.getText().toString();

				//			DB 메소드를 사용할 경우
				ContentValues row = new ContentValues();
				row.put(helper.COL_DUE, todoDate);
				row.put(helper.COL_TITLE, title);
				row.put(helper.COL_CAT, category);
				row.put(helper.COL_MEMO, memo);

				long result = db.insert(ToDoDBHelper.TABLE_NAME, null, row);

				//			SQL query를 직접 사용할 경우
				//			db.execSql("insert into " + ContactDBHelper.TABLE_NAME
				//									  + " values(null, '" + name + "', '" + link +"', '" + category "');", null);

				helper.close();

				if (result > 0) {
					etTitle.setText("");
					etCategory.setText("");
					etMemo.setText("");
					//				ivImage.setImageResource(R.drawable.youtube_video);
					Toast.makeText(this, "저장 완료", Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(this, "저장 실패", Toast.LENGTH_SHORT).show();
				}
				finish();
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
		}
	}
}
