// 과제02
// 작성일: 2019. 11. 10
// 작성자: 02분반 20170969 이지은

package ddwucom.mobile.final_project.ma02_20170969;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import ddwucom.mobile.final_project.ma02_20170969.Cal.AllCalendarActivity;
import ddwucom.mobile.final_project.ma02_20170969.Cal.InsertCalActivity;
import ddwucom.mobile.final_project.ma02_20170969.Cal.SearchCalActivity;
import ddwucom.mobile.final_project.ma02_20170969.ToDo.*;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        imgManager.clearSaveFilesOnInternal();
    }

    public void onClick(View v) {
        Intent intent = null;

        switch (v.getId()) {
            case R.id.btnOpenCal:
                intent = new Intent(this, AllCalendarActivity.class);
                break;
            case R.id.btnAddCal:
                intent = new Intent(this, InsertCalActivity.class);
                break;
            case R.id.btnSearchCal:
                intent = new Intent(this, SearchCalActivity.class);
                break;
            case R.id.btnAddToDo:
                intent = new Intent(this, InsertToDoActivity.class);
                break;
            case R.id.btnTodayToDo:
                intent = new Intent(this, TodayToDoActivity.class);
                break;
            case R.id.btnAllToDo:
                intent = new Intent(this, AllToDoActivity.class);
                break;
            case R.id.btnSearchToDo:
                intent = new Intent(this, SearchToDoActivity.class);
                break;
        }

        if (intent != null) startActivity(intent);
    }
}
