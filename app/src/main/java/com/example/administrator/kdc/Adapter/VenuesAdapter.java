package com.example.administrator.kdc.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.kdc.R;
import com.example.administrator.kdc.utils.ImageLoader;
import com.example.administrator.kdc.vo.Venues_tbl;

import java.util.List;

public class VenuesAdapter extends BaseAdapter {//maplist的适配器
    String url2;
    ImageLoader myImageLoader;
    String a,b;
    Venues_tbl upload;
    List<Venues_tbl> venuesList;
    Context context;
    public VenuesAdapter(List<Venues_tbl> venues, Context context){
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
            convertView=  LayoutInflater.from(context).inflate(R.layout.list,null);
            viewHolder=new ViewHolder();
            //找到View的控件
            TextView venues_name=(TextView) convertView.findViewById(R.id.tv_name);
            TextView address_id=(TextView) convertView.findViewById(R.id.tv_add);
            TextView venues_type=(TextView) convertView.findViewById(R.id.tv_type);
            TextView venues_ceiling=(TextView) convertView.findViewById(R.id.tv_current);
            TextView venues_yes=(TextView) convertView.findViewById(R.id.tv_yes);
            TextView venues_no=(TextView) convertView.findViewById(R.id.tv_no);
            ImageView venues_image=(ImageView) convertView.findViewById(R.id.imageView);

            //将view的控件对象保存到viewholder;
            viewHolder.venues_name=venues_name;
            viewHolder.address_id=address_id;
            viewHolder.venues_type=venues_type;
            viewHolder.venues_ceiling=venues_ceiling;
            viewHolder.venues_yes=venues_yes;
            viewHolder.venues_no=venues_no;
            viewHolder.venues_image=venues_image;
            //将convertView和viewHolder建立关联
            convertView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }
        //获取position位置的item，显示的学生信息
        upload=venuesList.get(position);



        switch (upload.getVenues_type()){
            case 1:b="支持预约的付费场地";
                break;
            case 2:b="不支持预约的付费场地";
                break;
            case 3:b="免费的野场地";
                break;
        }



        viewHolder.venues_name.setText(upload.getVenues_name());
        viewHolder.address_id.setText("地点："+upload.getAddress_tbl().getAddress_city()+""+upload.getAddress_tbl().getAddress_county()+""+upload.getAddress_tbl().getAddress_town()+""+upload.getAddress_tbl().getAddress_show());
        viewHolder.venues_type.setText(b+"");
        viewHolder.venues_ceiling.setText("     人数："+upload.getVenues_ceiling()+"/"+upload.getVenues_current());
        viewHolder.venues_yes.setText("     赞："+upload.getVenues_yes()+"");
        viewHolder.venues_no.setText("     踩："+upload.getVenues_no()+"");



        url2=upload.getVenuesshow_tbl().getVenuesshow_portrait();
    //    Log.d("BBBBB","url2"+url2);
        myImageLoader = new ImageLoader(context);
        myImageLoader.showImageByUrl(url2, viewHolder.venues_image);

        return convertView;
    }
    class ViewHolder{
        TextView venues_name;
        TextView venues_ceiling;
        TextView address_id;
        TextView venues_type;
        TextView venues_yes;
        TextView venues_no;
        ImageView venues_image;
    }
}
