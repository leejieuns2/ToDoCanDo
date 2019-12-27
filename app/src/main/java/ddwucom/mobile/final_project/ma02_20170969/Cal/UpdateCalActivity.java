package ddwucom.mobile.final_project.ma02_20170969.Cal;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import ddwucom.mobile.final_project.ma02_20170969.BlogAPI.BlogSearchActivity;
import ddwucom.mobile.final_project.ma02_20170969.FavPlace.AllFavPlaceActivity;
import ddwucom.mobile.final_project.ma02_20170969.R;

public class UpdateCalActivity extends AppCompatActivity {

    final static String TAG = "UpdateCalActivity";
    private DatePickerDialog.OnDateSetListener dateCallbackMethod;
    private TimePickerDialog.OnTimeSetListener timeCallbackMethod;
    final static int BLOG_LINK_CODE = 100;
    final static int MAP_LINK_CODE = 200;
    final static int FAV_LINK_CODE = 300;

    TextView tvTodoDate;
    TextView tvTodoTime;
    EditText etTitle;
    EditText etLocation;
    EditText etCategory;
    EditText etMemo;
    EditText etLink;

    CalDBHelper helper;
    long id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_cal);
//      DBHelper 생성
        helper = new CalDBHelper(this);
        tvTodoDate = (TextView) findViewById(R.id.tvUpdateDate);
        tvTodoTime = (TextView) findViewById(R.id.tvUpdateTime);
        etTitle = findViewById(R.id.etUpdateTitle);
        etLocation = findViewById(R.id.etUpdateLocation);
        etCategory = findViewById(R.id.etUpdateCategory);
        etLink =  findViewById(R.id.etUpdateLink);
        etMemo = findViewById(R.id.etUpdateMemo);

        helper = new CalDBHelper(this);

        id = getIntent().getLongExtra("id", 0);
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

    @Override
    protected void onResume() {
        super.onResume();

        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.rawQuery( "select * from " + CalDBHelper.TABLE_NAME + " where " + CalDBHelper.COL_ID + "=?", new String[] { String.valueOf(id) });
        while (cursor.moveToNext()) {
            tvTodoDate.setText( cursor.getString( cursor.getColumnIndex(helper.COL_DATE) ) );
            tvTodoTime.setText( cursor.getString( cursor.getColumnIndex(helper.COL_TIME) ) );
            etTitle.setText( cursor.getString( cursor.getColumnIndex(helper.COL_TITLE) ) );
            etLink.setText( cursor.getString( cursor.getColumnIndex(helper.COL_LINK) ) );
            etCategory.setText( cursor.getString( cursor.getColumnIndex(helper.COL_CAT) ) );
            etMemo.setText( cursor.getString( cursor.getColumnIndex(helper.COL_MEMO) ) );
            etLocation.setText( cursor.getString( cursor.getColumnIndex(helper.COL_LOC ) ) );
        }
        cursor.close();
        helper.close();
    }



    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btnUpdateCalSave:
                /*id 를 기준으로 화면의 값으로 DB 업데이트*/
                SQLiteDatabase db = helper.getWritableDatabase();
                ContentValues row = new ContentValues();

                row.put(helper.COL_DATE, tvTodoDate.getText().toString());
                row.put(helper.COL_TIME, tvTodoTime.getText().toString());
                row.put(helper.COL_TITLE, etTitle.getText().toString());
                row.put(helper.COL_LOC, etLocation.getText().toString());
                row.put(helper.COL_MEMO, etMemo.getText().toString());
                row.put(helper.COL_LINK, etLink.getText().toString());
                row.put(helper.COL_CAT, etCategory.getText().toString());

                String whereClause = CalDBHelper.COL_ID + "=?";
                String[] whereArgs = new String[] { String.valueOf(id) };

                int result = db.update(CalDBHelper.TABLE_NAME, row, whereClause, whereArgs);
                helper.close();

                String msg = result > 0 ? "Updated!" : "Failed!";
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                finish();
                break;
            case R.id.btnSBActivity:
                Intent intent = new Intent(this, BlogSearchActivity.class);
                startActivityForResult(intent, BLOG_LINK_CODE);
                break;
            case R.id.btnFavActivity:
                Intent favIntent = new Intent(this, AllFavPlaceActivity.class);
                startActivityForResult(favIntent, FAV_LINK_CODE);
            case R.id.btnUpdateCalClose:
                finish();
                break;
        }
    }

    public void OnClickHandler(View v)
    {
        Calendar calendar = new GregorianCalendar(Locale.KOREA);
        switch (v.getId()) {
            case(R.id.btnUpdateDateDialog):
                DatePickerDialog datePickerDialog = new DatePickerDialog(this, dateCallbackMethod, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
                break;
            case(R.id.btnUpdateTimeDialog):
                TimePickerDialog timePickerDialog = new TimePickerDialog(this, timeCallbackMethod, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
                timePickerDialog.show();
                break;
        }


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
            case FAV_LINK_CODE:
                if(resultCode == RESULT_OK) {
                    String resultData = data.getStringExtra("result_data");
                    Toast.makeText(this, "자주 가는 위치에서 가져왔음 !", Toast.LENGTH_SHORT).show();
                    etLocation.setText(resultData);
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
