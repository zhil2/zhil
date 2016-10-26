package com.example.administrator.kdc.Community.CommunityMainLayout.SportsGridview;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.kdc.R;
import com.example.administrator.kdc.vo.Sportstype_tbl;

import java.util.List;

/**
 * Created by kskjf on 2016/10/25.
 */
public class SportsGridviewAdapter extends BaseAdapter{
   // public LinearLayout communitygridview;
   LinearLayout communitygridview;
    Sportstype_tbl sportstype_tbl;
    List<Sportstype_tbl> sportstype_tbls;
    Context context;
    public SportsGridviewAdapter(List<Sportstype_tbl> sportstype_tbls, Context context){
        this.sportstype_tbls=sportstype_tbls;
        this.context=context;
    }
    @Override
    public int getCount() {
        Log.i("SportsGridviewAdapter", "getCount2341241: "+sportstype_tbls);
        return sportstype_tbls.size();
    }

    @Override
    public Object getItem(int position) {
        return sportstype_tbls.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if(convertView==null){
            convertView=  LayoutInflater.from(context).inflate(R.layout.community_main_griditem,null);
            viewHolder=new ViewHolder();
            //找到View的控
            communitygridview= (LinearLayout) convertView.findViewById(R.id.community_gridview);
            ImageView tvimage = (ImageView) convertView.findViewById(R.id.iv_communityimage_item);
            TextView tvname= (TextView) convertView.findViewById(R.id.tv_communityname_item);
              viewHolder.communitygridview=communitygridview;
              viewHolder.tvimage=tvimage;
              viewHolder.tvname=tvname;
            //将view的控件对象保存到viewholder;

            //将convertView和viewHolder建立关联
            convertView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }
        //获取position位置的item，显示的学生信息
        sportstype_tbl=sportstype_tbls.get(position);
        viewHolder.tvname.setText(sportstype_tbl.getSportstype_name());
        Log.i("sportstype_tbl", "getView: "+sportstype_tbl.getSportstype_name());
       // String imageurl=sportstype_tbl.getSportstype_picture();//图片集
       // Log.i("sportstype_tbl", "getView: "+imageurl);

        if (clickTemp == position) {
            communitygridview.setBackgroundColor(Color.BLUE);//点击了
          } else {
            communitygridview.setBackgroundColor(Color.GRAY);;//未点击
          }

            return convertView;
        }

        private int clickTemp = -1;
        public void setSelection(int position) {
            clickTemp = position;
        }//选择
    class ViewHolder{
        ImageView tvimage;
        TextView tvname;
        LinearLayout communitygridview;
    }
}
