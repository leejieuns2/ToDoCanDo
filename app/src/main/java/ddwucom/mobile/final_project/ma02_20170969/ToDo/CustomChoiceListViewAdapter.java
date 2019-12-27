package ddwucom.mobile.final_project.ma02_20170969.ToDo;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CursorAdapter;
import android.widget.TextView;

import ddwucom.mobile.final_project.ma02_20170969.R;

public class CustomChoiceListViewAdapter extends CursorAdapter {

    LayoutInflater inflater;
    Cursor cursor;
    ToDoDBHelper helper;

    public CustomChoiceListViewAdapter(Context context, int layout, Cursor c) {
        super(context, c, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        cursor = c;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View listItemLayout = inflater.inflate(R.layout.todo_adapter_layout, parent, false);
        return listItemLayout;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView tvDueDate = (TextView)view.findViewById(R.id.tvDueDate);
        TextView tvTitle = (TextView)view.findViewById(R.id.tvTitle);

        // 나중에 완성시 ViewHolder 적용하기
        tvTitle.setText(cursor.getString(cursor.getColumnIndex(helper.COL_TITLE)));
        tvDueDate.setText(cursor.getString(cursor.getColumnIndex(helper.COL_DUE)));
    }
}
