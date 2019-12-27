package ddwucom.mobile.final_project.ma02_20170969.ToDo;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import ddwucom.mobile.final_project.ma02_20170969.R;

public class UpdateToDoActivity extends AppCompatActivity {

    final static String TAG = "UpdateToDoActivity";
    private DatePickerDialog.OnDateSetListener dateCallbackMethod;

    TextView tvDueDate;
    EditText etTitle;
    EditText etCategory;
    EditText etMemo;

    ToDoDBHelper helper;
    long id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_todo);
//      DBHelper 생성
        helper = new ToDoDBHelper(this);

        tvDueDate = (TextView) findViewById(R.id.tvUpdateDueDate);
        etTitle = findViewById(R.id.etUpdateTodoTitle);
        etCategory = findViewById(R.id.etUpdateTodoCat);
        etMemo = findViewById(R.id.etUpdateTodoMemo);

        helper = new ToDoDBHelper(this);

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
                tvDueDate.setText(year + "년" + (monthOfYear + 1) + "월" + dayOfMonth + "일");
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();

        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.rawQuery( "select * from " + ToDoDBHelper.TABLE_NAME + " where " + ToDoDBHelper.COL_ID + "=?", new String[] { String.valueOf(id) });
        while (cursor.moveToNext()) {
            tvDueDate.setText( cursor.getString( cursor.getColumnIndex(helper.COL_DUE) ) );
            etTitle.setText( cursor.getString( cursor.getColumnIndex(helper.COL_TITLE) ) );
            etCategory.setText( cursor.getString( cursor.getColumnIndex(helper.COL_CAT) ) );
            etMemo.setText( cursor.getString( cursor.getColumnIndex(helper.COL_MEMO) ) );
        }
        cursor.close();
        helper.close();
    }



    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btnUpdateToDoSave:
                /*id 를 기준으로 화면의 값으로 DB 업데이트*/
                SQLiteDatabase db = helper.getWritableDatabase();
                ContentValues row = new ContentValues();

                row.put(helper.COL_DUE, tvDueDate.getText().toString());
                row.put(helper.COL_TITLE, etTitle.getText().toString());
                row.put(helper.COL_MEMO, etMemo.getText().toString());
                row.put(helper.COL_CAT, etCategory.getText().toString());

                String whereClause = ToDoDBHelper.COL_ID + "=?";
                String[] whereArgs = new String[] { String.valueOf(id) };

                int result = db.update(ToDoDBHelper.TABLE_NAME, row, whereClause, whereArgs);
                helper.close();

                String msg = result > 0 ? "Updated!" : "Failed!";
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnUpdateToDoClose:
                finish();
                break;
        }
        finish();
    }

    public void OnClickHandler(View v)
    {
        Calendar calendar = new GregorianCalendar(Locale.KOREA);
        switch (v.getId()) {
            case(R.id.btnUpdateDateDialog):
                DatePickerDialog datePickerDialog = new DatePickerDialog(this, dateCallbackMethod, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

                datePickerDialog.show();
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
