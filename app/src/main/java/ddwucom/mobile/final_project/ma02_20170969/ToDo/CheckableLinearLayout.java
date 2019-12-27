package ddwucom.mobile.final_project.ma02_20170969.ToDo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Checkable;
import android.widget.Toast;

import ddwucom.mobile.final_project.ma02_20170969.R;

public class CheckableLinearLayout extends LinearLayout implements Checkable {

    // 만약 CheckBox가 아닌 View를 추가한다면 아래의 변수 사용 가능.
    // private boolean mIsChecked ;

    public CheckableLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        // mIsChecked = false ;
    }

    @Override
    public boolean isChecked() {
        CheckBox cb = (CheckBox) findViewById(R.id.cbToDo) ;

        return cb.isChecked() ;
        // return mIsChecked ;
    }

    @Override
    public void toggle() {
        CheckBox cb = (CheckBox) findViewById(R.id.cbToDo) ;

        setChecked(cb.isChecked() ? false : true) ;
        // setChecked(mIsChecked ? false : true) ;
    }

    @Override
    public void setChecked(boolean checked) {
        CheckBox cb = (CheckBox) findViewById(R.id.cbToDo) ;

        if (cb.isChecked() != checked) {
            cb.setChecked(checked) ;
        }
        // CheckBox 가 아닌 View의 상태 변경.
//        checkBox.setOnClickListener(new CheckBox.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(AllToDoActivity.this, "checked!", Toast.LENGTH_SHORT).show();
//				if( ((CheckBox)v).isChecked()) {
//					final long targetId = id;	// id 값을 다이얼로그 객체 내부에서 사용하기 위하여 상수로 선언
//					TextView tvTitle = view.findViewById(R.id.tvTitle);	// 리스트 뷰의 클릭한 위치에 있는 뷰 확인
//
//					String dialogMessage = "'" + tvTitle.getText().toString() + "' DELETE?";	// 클릭한 위치의 뷰에서 문자열 값 확인
//
//					new AlertDialog.Builder(AllToDoActivity.this).setTitle("삭제 확인")
//							.setMessage(dialogMessage)
//							.setPositiveButton("삭제", new DialogInterface.OnClickListener() {
//
//								//							삭제 수행
//								@Override
//								public void onClick(DialogInterface dialog, int which) {
//									SQLiteDatabase db = helper.getWritableDatabase();
//
//									String whereClause = ToDoDBHelper.COL_ID + "=?";
//									String[] whereArgs = new String[] { String.valueOf(targetId) };
//
//									db.delete(ToDoDBHelper.TABLE_NAME, whereClause, whereArgs);
//									helper.close();
//									readAllContacts();		// 삭제 상태를 반영하기 위하여 전체 목록을 다시 읽음
//								}
//							})
//							.setNegativeButton("취소", null)
//							.show();
//				}
//            }
//        }) ;
    }
}
