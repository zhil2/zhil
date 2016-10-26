package com.example.administrator.kdc.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.kdc.R;
import com.example.administrator.kdc.vo.Usershow_tbl;

import java.util.List;

public class VenuesfriendAdapter extends BaseAdapter {//maplist的适配器

    String a,b;
    Usershow_tbl upload;
    List<Usershow_tbl> venuesList;
    Context context;
    public VenuesfriendAdapter(List<Usershow_tbl> venues, Context context){
        this.venuesList=venues;
        this.context=context;
    }
    @Override
    public int getCount() {
        return venuesList.size();
    }
    @Override
    public Object getItem(int position) {
        return venuesList.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent){

        ViewHolder viewHolder=null;
        if(convertView==null){
            convertView=  LayoutInflater.from(context).inflate(R.layout.list_frimends,null);
            viewHolder=new ViewHolder();
            //找到View的控件
            TextView user_name=(TextView) convertView.findViewById(R.id.tv_name);
            TextView user_sex=(TextView) convertView.findViewById(R.id.tv_sex);
            TextView user_credit=(TextView) convertView.findViewById(R.id.tv_credit);
            TextView user_live=(TextView) convertView.findViewById(R.id.tv_live);


            //将view的控件对象保存到viewholder;
            viewHolder.user_name=user_name;
            viewHolder.user_sex=user_sex;
            viewHolder.user_credit=user_credit;
            viewHolder.user_live=user_live;

            //将convertView和viewHolder建立关联
            convertView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }
        //获取position位置的item，显示的学生信息
        upload=venuesList.get(position);


     /*   if(upload.getVenues_type()==1){
           b="支持预约的付费场地";
        }*/
        //给控件赋值
        viewHolder.user_name.setText("昵称："+upload.getUsershow_name());
        viewHolder.user_sex.setText("性别："+upload.getUsershow_sex());
        viewHolder.user_credit.setText("信誉："+upload.getUsershow_credit());
        viewHolder.user_live.setText("运动爱好：篮球");

        return convertView;
    }
    class ViewHolder{
        TextView user_name;
        TextView user_sex;
        TextView user_credit;
        TextView user_live;
    }
}
