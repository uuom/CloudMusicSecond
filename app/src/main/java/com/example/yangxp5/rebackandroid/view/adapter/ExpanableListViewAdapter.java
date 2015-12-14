package com.example.yangxp5.rebackandroid.view.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.yangxp5.rebackandroid.R;
import com.example.yangxp5.rebackandroid.model.SongList;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Map;

/**
 * Created by yangxp5 on 2015/12/11.
 */
public class ExpanableListViewAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> groupList;
    private Map<String,List<SongList>> dataList;

    public ExpanableListViewAdapter(Context context,List<String> groupList, Map<String, List<SongList>> dataList) {
        this.groupList = groupList;
        this.dataList = dataList;
        this.context = context;
    }

    @Override
    public int getGroupCount() {
        return groupList.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return dataList.get(groupList.get(i)).size();
    }

    @Override
    public Object getGroup(int i) {
        return groupList.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return dataList.get(groupList.get(i)).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        return getGenericView(groupList.get(i));
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {

        SongList sl = dataList.get(groupList.get(i)).get(i1);

        ViewHolder vh;
        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.item_song_list,viewGroup,false);
            vh = new ViewHolder(view);
            view.setTag(vh);
        }else{
            vh = (ViewHolder) view.getTag();
        }

        vh.tv_songList_name.setText(sl.getName());
        vh.tv_songList_songNumber.setText(sl.getSongNumber()+"首");
        vh.tv_songList_founder.setText(sl.getFounder());

        return view;
    }

    class ViewHolder {
        TextView tv_songList_name;
        TextView tv_songList_songNumber;
        TextView tv_songList_founder;

        public ViewHolder(View view){
            tv_songList_name = (TextView) view.findViewById(R.id.tv_songList_name);
            tv_songList_songNumber = (TextView) view.findViewById(R.id.tv_songList_songNumber);
            tv_songList_founder = (TextView) view.findViewById(R.id.tv_songList_founder);
        }
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }

    //创建组/子视图
    public TextView getGenericView(String s) {
        // Layout parameters for the ExpandableListView
        AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
                ViewGroup.LayoutParams.FILL_PARENT, 40);

        TextView text = new TextView(context);
        text.setLayoutParams(lp);
        // Center the text vertically
        text.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
        // Set the text starting position
        text.setPadding(50, 0, 0, 0);

        text.setText(s);
        return text;
    }
}
