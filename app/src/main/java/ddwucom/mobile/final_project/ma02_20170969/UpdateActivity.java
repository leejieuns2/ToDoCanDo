package ddwucom.mobile.final_project.ma02_20170969;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class UpdateActivity extends AppCompatActivity {

    final static String TAG = "ToDoDBHelper";
    final static int BLOG_LINK_CODE = 100;

    EditText etTodoDate;
    EditText etTodoTime;
    EditText etTitle;
    EditText etLocation;
    EditText etCategory;
    EditText etMemo;
    EditText etLink;

    ToDoDBHelper helper;
    long id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        etTodoDate = findViewById(R.id.etUpdateDate);
        etTodoTime = findViewById(R.id.etUpdateTime);
        etTitle = findViewById(R.id.etUpdateTitle);
        etLocation = findViewById(R.id.etUpdateLocation);
        etCategory = findViewById(R.id.etUpdateCategory);
        etLink =  findViewById(R.id.etUpdateLink);
        etMemo = findViewById(R.id.etUpdateMemo);

        helper = new ToDoDBHelper(this);

        id = getIntent().getLongExtra("id", 0);
    }

    @Override
    protected void onResume() {
        super.onResume();

        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.rawQuery( "select * from " + ToDoDBHelper.TABLE_NAME + " where " + ToDoDBHelper.COL_ID + "=?", new String[] { String.valueOf(id) });
        while (cursor.moveToNext()) {
            etTodoDate.setText( cursor.getString( cursor.getColumnIndex(helper.COL_DATE) ) );
            etTodoTime.setText( cursor.getString( cursor.getColumnIndex(helper.COL_TIME) ) );
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
            case R.id.btnUpdateToDoSave:
                /*id 를 기준으로 화면의 값으로 DB 업데이트*/
                SQLiteDatabase db = helper.getWritableDatabase();
                ContentValues row = new ContentValues();

                row.put(helper.COL_DATE, etTodoDate.getText().toString());
                row.put(helper.COL_TIME, etTodoTime.getText().toString());
                row.put(helper.COL_TITLE, etTitle.getText().toString());
                row.put(helper.COL_LOC, etLocation.getText().toString());
                row.put(helper.COL_MEMO, etMemo.getText().toString());
                row.put(helper.COL_LINK, etLink.getText().toString());
                row.put(helper.COL_CAT, etCategory.getText().toString());

                String whereClause = ToDoDBHelper.COL_ID + "=?";
                String[] whereArgs = new String[] { String.valueOf(id) };

                int result = db.update(ToDoDBHelper.TABLE_NAME, row, whereClause, whereArgs);
                helper.close();

                String msg = result > 0 ? "Updated!" : "Failed!";
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

                break;
            case R.id.btnSBActivity:
                Intent intent = new Intent(this, BlogSearchActivity.class);
                startActivityForResult(intent, BLOG_LINK_CODE);
                break;
            case R.id.btnUpdateToDoClose:
                finish();
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
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
