package ddwucom.mobile.final_project.ma02_20170969.FavPlace;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import ddwucom.mobile.final_project.ma02_20170969.R;

public class PlaceCursorAdapter extends CursorAdapter {

    LayoutInflater inflater;
    Cursor cursor;
    FavPlaceDBHelper helper;

    public PlaceCursorAdapter(Context context, int layout, Cursor c) {
        super(context, c, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        cursor = c;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View listItemLayout = inflater.inflate(R.layout.place_adapter_layout, parent, false);
        return listItemLayout;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView tvFavName = (TextView)view.findViewById(R.id.tvFavName);
        TextView tvFavPlace = (TextView)view.findViewById(R.id.tvFavPlace);
        // 나중에 완성시 ViewHolder 적용하기

        tvFavName.setText(cursor.getString(cursor.getColumnIndex(helper.COL_NAME)));
        tvFavPlace.setText(cursor.getString(cursor.getColumnIndex(helper.COL_PLACE)));
    }
}
