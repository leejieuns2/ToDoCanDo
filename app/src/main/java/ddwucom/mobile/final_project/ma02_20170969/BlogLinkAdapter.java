package ddwucom.mobile.final_project.ma02_20170969;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class BlogLinkAdapter extends BaseAdapter {

    public static final String TAG = "BlogLinkAdapter";

    private LayoutInflater inflater;
    private Context context;
    private int layout;
    private ArrayList<NaverBlogDto> list;

    public BlogLinkAdapter(Context context, int resource, ArrayList<NaverBlogDto> list) {
        this.context = context;
        this.layout = resource;
        this.list = list;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public NaverBlogDto getItem(int position) {
        return list.get(position);
    }


    @Override
    public long getItemId(int position) {
        return list.get(position).get_id();
    }

    public void setList(ArrayList<NaverBlogDto> list) {
        this.list = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Log.d(TAG, "getView with position : " + position);
        View view = convertView;
        ViewHolder viewHolder = null;

        if (view == null) {
            view = inflater.inflate(layout, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tvTitle = view.findViewById(R.id.tvTitle);
            viewHolder.tvdesc = view.findViewById(R.id.tvDescription);
            viewHolder.tvBlogLink = view.findViewById(R.id.tvBlogLink);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)view.getTag();
        }

        NaverBlogDto dto = list.get(position);

        viewHolder.tvTitle.setText(dto.getTitle());
        viewHolder.tvdesc.setText(dto.getDescription());
        viewHolder.tvBlogLink.setText(dto.getBlogLink());
        return view;
    }

//    ※ findViewById() 호출 감소를 위해 필수로 사용할 것
    static class ViewHolder {
        public TextView tvTitle = null;
        public TextView tvBlogLink = null;
        public TextView tvdesc = null;
    }
}
