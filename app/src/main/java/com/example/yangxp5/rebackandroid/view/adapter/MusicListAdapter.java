package com.example.yangxp5.rebackandroid.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.yangxp5.rebackandroid.R;
import com.example.yangxp5.rebackandroid.model.MusicInfo;

/**
 * Created by yangxp5 on 2015/12/14.
 */
public class MusicListAdapter extends RecyclerView.Adapter<MusicListAdapter.ViewHolder>{

    private MusicInfo[] dataList;
    private OnItemClickListener mOnItemClickListener;

    public MusicListAdapter(MusicInfo[] dataList) {
        this.dataList = dataList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_music_list,viewGroup,false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
        MusicInfo musicInfo = dataList[i];
        viewHolder.tv_music_title.setText(musicInfo.getTitle());
        viewHolder.tv_music_artist.setText(musicInfo.getArtist());
        viewHolder.tv_music_count.setText(i+1+"");

        if (mOnItemClickListener != null){
            viewHolder.rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onclick(view,i);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return dataList.length;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        View rootView;
        TextView tv_music_title;
        TextView tv_music_artist;
        TextView tv_music_count;

        public ViewHolder(View itemView) {
            super(itemView);
            this.rootView = itemView;
            tv_music_title = (TextView) itemView.findViewById(R.id.tv_music_title);
            tv_music_artist = (TextView) itemView.findViewById(R.id.tv_music_artist);
            tv_music_count = (TextView) itemView.findViewById(R.id.tv_music_count);
        }
    }

    public interface OnItemClickListener{
        void onclick(View view, int position);
    }

    public void setItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }
}
