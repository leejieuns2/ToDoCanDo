package ddwucom.mobile.final_project.ma02_20170969.Cal;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import ddwucom.mobile.final_project.ma02_20170969.R;

public class CustomCursorAdapter extends CursorAdapter {

    LayoutInflater inflater;
    Cursor cursor;
    CalDBHelper helper;

    public CustomCursorAdapter(Context context, int layout, Cursor c) {
        super(context, c, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        cursor = c;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View listItemLayout = inflater.inflate(R.layout.custom_adapter_layout, parent, false);
        return listItemLayout;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView tvTime = (TextView)view.findViewById(R.id.tvTime);
        TextView tvDate = (TextView)view.findViewById(R.id.tvDate);
        TextView tvTitle = (TextView)view.findViewById(R.id.tvTitle);
        TextView tvCategory = (TextView)view.findViewById(R.id.tvCategory);

        // 나중에 완성시 ViewHolder 적용하기

        tvTitle.setText(cursor.getString(cursor.getColumnIndex(helper.COL_TITLE)));
        tvDate.setText(cursor.getString(cursor.getColumnIndex(helper.COL_DATE)));
        tvTime.setText(cursor.getString(cursor.getColumnIndex(helper.COL_TIME)));
        tvCategory.setText(cursor.getString(cursor.getColumnIndex(helper.COL_CAT)));
    }


}
