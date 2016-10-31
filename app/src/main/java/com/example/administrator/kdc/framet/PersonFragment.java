package com.example.administrator.kdc.framet;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.kdc.R;
import com.example.administrator.kdc.activity.AllOrderActivity;
import com.example.administrator.kdc.utils.NetUtil;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/9/18.
 */
public class PersonFragment extends BaseFragment {


    @InjectView(R.id.imb_user)//头像
    ImageButton imbUser;
    @InjectView(R.id.tv_user)
    TextView tvUser;
    @InjectView(R.id.textView21)
    TextView textView21;
    @InjectView(R.id.textView22)
    TextView textView22;
    @InjectView(R.id.person_center_ll_header)
    RelativeLayout personCenterLlHeader;
    @InjectView(R.id.imageView5)
    ImageView imageView5;
    @InjectView(R.id.tv_personinfo)
    TextView tvPersoninfo;
    @InjectView(R.id.rl1)
    RelativeLayout rl1;
    @InjectView(R.id.imageView6)
    ImageView imageView6;
    @InjectView(R.id.tv_yuyue)
    TextView tvYuyue;
    @InjectView(R.id.rl2)
    RelativeLayout rl2;
    @InjectView(R.id.imageView7)
    ImageView imageView7;
    @InjectView(R.id.tv_zhaoji)
    TextView tvZhaoji;
    @InjectView(R.id.rl3)
    RelativeLayout rl3;
    @InjectView(R.id.imageView8)
    ImageView imageView8;
    @InjectView(R.id.tv_qiandao)
    TextView tvQiandao;
    @InjectView(R.id.rl4)
    RelativeLayout rl4;
    @InjectView(R.id.imageView9)
    ImageView imageView9;
    @InjectView(R.id.tv_shezhi)
    TextView tvShezhi;
    @InjectView(R.id.rl5)
    RelativeLayout rl5;
    @InjectView(R.id.imageView10)
    ImageView imageView10;
    @InjectView(R.id.tv_tuichu)
    TextView tvTuichu;
    @InjectView(R.id.rl6)
    RelativeLayout rl6;
    private File file;
    private Uri imageUri;
    String items[]={"相册选择","拍照"};
    public static final int SELECT_PIC=11;
    public static final int TAKE_PHOTO=12;
    public static final int CROP=13;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_person_center, null);
        ButterKnife.inject(this, v);
        if(Environment.getExternalStorageState().equals( Environment.MEDIA_MOUNTED) ){
            //目录，文件名Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
            file=new File(Environment.getExternalStorageDirectory(),getPhotoFileName());
            imageUri= Uri.fromFile(file);
        }
        return v;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initEvent() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
    private String getPhotoFileName() {//文件名
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        return sdf.format(date) + ".png";
    }
    public void crop(Uri uri){
        //  intent.setType("image/*");
        //裁剪
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");

        intent.putExtra("crop", "true");

        intent.putExtra("aspectX",1);
        intent.putExtra("aspectY", 1);

        intent.putExtra("outputX", 200);
        intent.putExtra("outputY", 200);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, CROP);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        //
        switch (requestCode){
            case SELECT_PIC:
                //相册选择
                if (data != null) {
                    crop(data.getData());
                }
                break;
            case TAKE_PHOTO:
                crop(Uri.fromFile(file));
                //Uri.fromFile(file);
                break;
            case CROP:
                if (data != null) {
                    Bundle extras = data.getExtras();
                    if (extras != null) {

                        Bitmap bitmap = extras.getParcelable("data");
                        showImage(bitmap);
                    }
                }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
    //显示图片，上传服务器
    public void showImage(Bitmap bitmap){
        imbUser.setImageBitmap(bitmap);//iv显示图片
        saveImage(bitmap);//保存文件
        uploadImage();//上传服务器
    }
    //将bitmap保存在文件中
    public void saveImage(Bitmap bitmap){
        FileOutputStream fos=null;
        try {
            fos=new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        bitmap.compress(Bitmap.CompressFormat.JPEG,50,fos);
    }
    @OnClick({R.id.imb_user, R.id.rl1, R.id.rl2, R.id.rl3, R.id.rl4, R.id.rl5, R.id.rl6})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imb_user://用户头像
                //修改头像
                new AlertDialog.Builder(getActivity()).setTitle("选择").setItems(items,new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        switch(which){
                            case 0:
                                //相册选择
                                Intent intent = new Intent(Intent.ACTION_PICK, null);
                                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                        "image/*");
                                startActivityForResult(intent, SELECT_PIC);
                                break;
                            case 1:
                                //拍照:
                                Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                intent2.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                                startActivityForResult(intent2,TAKE_PHOTO);
                                break;
                        }
                    }
                }).show();
                break;
            case R.id.rl1:

                break;
            case R.id.rl2:
                Intent intent=new Intent(getActivity(), AllOrderActivity.class);
                startActivity(intent);
                break;
            case R.id.rl3:
                break;
            case R.id.rl4:
                break;
            case R.id.rl5:
                break;
            case R.id.rl6:
                break;
        }
    }
    //上传图片
    public void uploadImage(){//上传头像
        RequestParams requestParams=new RequestParams(NetUtil.url+"UploadImageServlet3");
        requestParams.setMultipart(true);
        requestParams.addBodyParameter("file",file);
        requestParams.addBodyParameter("name","dshhhh");
        Log.i("ModifyPersonInfo", "uploadImage: "+file);
        File file1=new File(Environment.getExternalStorageDirectory(),"b3.png");
        requestParams.addBodyParameter("file",file1);
        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("ModifyPersonInfo", "onSuccess: ");
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
            }
            @Override
            public void onCancelled(CancelledException cex) {
            }
            @Override
            public void onFinished() {
            }
        });
    }
}