package com.example.administrator.kdc.Community.CommunityPostDetails;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.kdc.Community.AdapterViewHolder.BaseActivity;
import com.example.administrator.kdc.Community.AdapterViewHolder.CommonAdapter;
import com.example.administrator.kdc.Community.AdapterViewHolder.ViewHolder;
import com.example.administrator.kdc.Community.CommunityPostComment.CommunityPost_comment_Activity;
import com.example.administrator.kdc.Community.MyXListView.NoScrollListview;
import com.example.administrator.kdc.Community.ServletURL.URL;
import com.example.administrator.kdc.R;
import com.example.administrator.kdc.utils.MyApplication;
import com.example.administrator.kdc.vo.Post_comment_tbl;
import com.example.administrator.kdc.vo.Post_reply_tbl;
import com.example.administrator.kdc.vo.Post_tbl;
import com.example.administrator.kdc.vo.Usershow_tbl;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class ComunityPost_details_Activity extends BaseActivity {//帖子详情
    @InjectView(R.id.return_community)
    ImageView returnCommunity;
    @InjectView(R.id.tv_postcomunityname)
    TextView tvPostcomunityname;
    @InjectView(R.id.user_image)//头像
    ImageView userImage;
    @InjectView(R.id.user_name)
    public TextView userName;
    @InjectView(R.id.post_endrelytime)
    public TextView postEndrelytime;
    @InjectView(R.id.post_click)
    public TextView postClick;
    @InjectView(R.id.post_name)
    public TextView postName;
    @InjectView(R.id.post_text)
    public TextView postText;
    @InjectView(R.id.sendRelypost_toPost)
    Button sendRelypost_toPost;
    @InjectView(R.id.community_post_image)
    GridView communityPostImage;//评论者的图片展示
    public static int post_id;
    @InjectView(R.id.listview_firstrely)
    NoScrollListview listviewFirstrely;
    public NoScrollListview listviewsecondrely;
    public List<Post_comment_tbl> post_comment_tbls = new ArrayList<Post_comment_tbl>();
    public CommonAdapter<Post_comment_tbl> commentAdapter;
    // public CommonAdapter<Post_reply_tbl> replyCommonAdapter;
    public Button btncomment;//评论者回复按钮
    public Button tvgetall;//点击查看更多
    public static String comment = "";//回复内容为空
    //public static
    Usershow_tbl usershowTbl = null;
    @InjectView(R.id.commentEdit)
    EditText commentEdit;//输入框
    @InjectView(R.id.commentButton)
    Button commentButton;//发送输入框内容
    @InjectView(R.id.commentLinear)
    LinearLayout commentLinear;//输入框架
    @InjectView(R.id.main_bottom2)//我要回复框架
    LinearLayout mainBottom2;
    static int count=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comunity_postdetails_listview);
        ButterKnife.inject(this);
        Intent intent = getIntent();//获取上一个界面传过来的对象
        post_id = intent.getIntExtra("post_id", 0);
        Log.i("post_id", "onCreate: " + post_id);
        getData();
    }
    @Override
    public void initView() {
    }

    @Override
    public void initEvent() {
    }

    @Override
    public void getData() {
        getPost_tbl(post_id);
        getData1(post_id);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 1:
                if(resultCode==RESULT_OK){
                    int post_id1= data.getIntExtra("post_id",0);
                    Log.i("onActivityResult:", "onActivityResult: "+post_id1);
                    getPost_tbl(post_id1);
                    getData1(post_id1);
                }
                break;
            default:
        }
    }
    public void getData1(int post_id) {//查询所有的评论
        RequestParams requestParams1 = new RequestParams(URL.url + "Post_commentAndreply_tbl_getAll_Servlet");
        requestParams1.addQueryStringParameter("post_id", post_id + "");
        x.http().get(requestParams1, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        Gson gson = new Gson();
                        Type type = new TypeToken<List<Post_comment_tbl>>() {
                        }.getType();
                        List<Post_comment_tbl> post_comment_tbls1 = gson.fromJson(result, type);//解析成List<Product>
                        post_comment_tbls.clear();
                        post_comment_tbls.addAll(post_comment_tbls1);
                        Log.i("xUtils_Activity", "成功22222" + post_comment_tbls);
                        if (commentAdapter == null) {
                            commentAdapter = new CommonAdapter<Post_comment_tbl>(ComunityPost_details_Activity.this, post_comment_tbls, R.layout.listview_first_commentpost_item) {
                                @Override
                                public void convert(ViewHolder viewHolder, Post_comment_tbl post_comment_tbl, final int position) {
                                    TextView tvcommentusername = viewHolder.getViewById(R.id.post_usernamecomment);//评论人姓名
                                    tvcommentusername.setText(post_comment_tbl.getUsershow_tbl().getUsershow_name());
                                    TextView tvcommentendtime = viewHolder.getViewById(R.id.post_endrelytimecomment);//时间
                                    tvcommentendtime.setText(post_comment_tbl.getPost_comment_date() + "");
                                    TextView tvcommenttext = viewHolder.getViewById(R.id.post_textcomment);//内容
                                    tvcommenttext.setText(post_comment_tbl.getPost_comment_text());
                                    final String post_comment_username = post_comment_tbl.getUsershow_tbl().getUsershow_name();
                                    final int post_comment_id = post_comment_tbl.getPost_comment_id();//评论人id
                                    tvgetall=viewHolder.getViewById(R.id.tv_getAll);//查看更多
                                    btncomment = viewHolder.getViewById(R.id.commenttopost);//回复按钮
                                    btncomment.setOnClickListener(new View.OnClickListener() {//回复按钮//对评论的人进行回复
                                        @Override
                                        public void onClick(View view) {
                                            mainBottom2.setVisibility(View.GONE);//我要回复比较隐藏
                                            commentLinear.setVisibility(View.VISIBLE);//输入框显示
                                            commentEdit.requestFocus();//显示键盘
                                            onFocusChange(true);//键盘显示
                                            if (post_comment_username!=null) {
                                                commentEdit.setHint("回复 " + post_comment_username + ":");
                                                //输入框提示文字
                                                final String string2 = commentEdit.getHint().toString();
                                                commentEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                                                    @Override
                                                    public void onFocusChange(View arg0, boolean hasFocus) {
                                                        if (hasFocus) {
                                                            commentEdit.setHint(null);//点击时清楚提示文字
                                                        } else {
                                                            commentEdit.setHint(string2);//未点击时显示提示文字
                                                        }
                                                    }
                                                });
                                            }
                                            commentButton.setOnClickListener(new View.OnClickListener() {//点击发送数据//输入框布局发送按钮
                                                @Override
                                                public void onClick(View v) {
                                                    String reply_text = commentEdit.getText().toString();//发表的内容
                                                    if (reply_text.equals("")) {
                                                        Toast.makeText(getApplicationContext(), "回复内容不能为空", Toast.LENGTH_SHORT).show();
                                                    } else {
                                                        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                                                        String post_reply_date = sDateFormat.format(new Date());//时间
                                                        //private int post_reply_type;//1表示对评论进行回复，2表示对回复进行回复
                                                        Log.i("commentButton", "onClick: " + 111111);
                                                        addData(((MyApplication) ComunityPost_details_Activity.this.getApplication()).getUser().getUser_id(), reply_text, ((MyApplication) ComunityPost_details_Activity.this.getApplication()).getUser().getUser_id(), post_reply_date, post_comment_id, 1);//接下来发送到服务器
                                                        Log.i("commentButton", "onClick: " + 222222);
                                                    }
                                                }
                                            });
                                        }
                                    });//回复按钮
                                    listviewsecondrely = viewHolder.getViewById(R.id.listview_secondrely);//二级回复
                                    CommonAdapter<Post_reply_tbl> replyCommonAdapter;
                                    final List<Post_reply_tbl> post_reply_tbls1 = post_comment_tbl.getPost_reply_tbl();
                                    final List<Post_reply_tbl> post_reply_tbls = new ArrayList<Post_reply_tbl>();
//                                    if(count>0){
//                                        if (post_reply_tbls1.size() > 0) {
//                                            tvgetall.setVisibility(View.VISIBLE);
//                                            post_reply_tbls.addAll(post_reply_tbls1);
//                                            tvgetall.setHint("没有更多了");
//                                        } else {
//                                            tvgetall.setVisibility(View.GONE);
//                                        }
//                                    }else {
                                        if (post_reply_tbls1.size() > 0) {
                                            tvgetall.setVisibility(View.VISIBLE);
                                            if (post_reply_tbls1.size() > 3) {
                                                post_reply_tbls.addAll(post_reply_tbls1.subList(0, 3));
                                            } else {
                                                post_reply_tbls.addAll(post_reply_tbls1);
                                            }
                                        } else {
                                            tvgetall.setVisibility(View.GONE);
                                        }
                                   // }

                                    replyCommonAdapter = null;
                                    if (replyCommonAdapter == null) {
                                        replyCommonAdapter = new CommonAdapter<Post_reply_tbl>(ComunityPost_details_Activity.this, post_reply_tbls, R.layout.listview_second_relypost_item) {
                                            @Override
                                            public void convert(ViewHolder viewHolder, Post_reply_tbl post_reply_tbl, int position) {
                                                TextView tvReplytoComment = viewHolder.getViewById(R.id.post_rely_content);
                                                int reply_id = post_reply_tbl.getPost_reply_id();
                                                int reply_user_id = post_reply_tbl.getReply_user_id();
                                                String post_reply_text = post_reply_tbl.getPost_reply_text();
                                                String post_reply_date = post_reply_tbl.getPost_reply_date() + "";
                                                int post_comment_id = post_reply_tbl.getPost_comment_id();
                                                String reply_user_name = post_reply_tbl.getUsershow_tbl().getUsershow_name();
                                                int post_reply_type = post_reply_tbl.getPost_reply_type();
                                                if (post_reply_type > 1) {
                                                    //对回复人进行回复时显示如下//用户名+回复+回复者名+ ：+内容+时
                                                    int reply_user2_id = post_reply_tbl.getReply_user2_id();
                                                    RequestParams requestParams2 = new RequestParams(URL.url + "ShowUser_tbl_Servlet");
                                                    requestParams2.addQueryStringParameter("user_id", reply_user2_id + "");
                                                    Log.i("xUtils_Activity", "成功222222222222" + 22222222 + reply_user2_id);
                                                    x.http().get(requestParams2, new CommonCallback<String>() {
                                                                @Override
                                                                public void onSuccess(String result) {
                                                                    Log.i("xUtils_Activity", "成功222222222222result" + result);//gson如何转集合
                                                                    Gson gson = new Gson();
                                                                    usershowTbl = gson.fromJson(result, Usershow_tbl.class);
                                                                    Log.i("xUtils_Activity", "成功222222222222usershowTbl" + usershowTbl);
                                                                }

                                                                @Override
                                                                public void onError(Throwable ex, boolean isOnCallback) {
                                                                    Log.i("xUtils_Activity", "onError: get" + ex.getMessage());
                                                                }

                                                                @Override
                                                                public void onCancelled(CancelledException cex) {
                                                                    Log.i("xUtils_Activity", "onCancelled: get" + cex);
                                                                }

                                                                @Override
                                                                public void onFinished() {
                                                                    Log.i("xUtils_Activity", "onFinished: get");
                                                                }
                                                            }
                                                    );
                                                    Log.i("xUtils_Activity", "成功222222222222" + 3000000);
                                                    if (usershowTbl != null) {
                                                        Log.i("xUtils_Activity", "成功222222222222" + 4000000 + usershowTbl);
                                                        String reply_user2_name = usershowTbl.getUsershow_name();
                                                        //String reply_user2_name = "的两极分化";
                                                        SpannableString ss1;
                                                        ss1 = new SpannableString(reply_user_name + "回复" + reply_user2_name + "：" + post_reply_text + "  " + post_reply_date);
                                                        ss1.setSpan(new ForegroundColorSpan(Color.BLUE), 0, reply_user_name.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                                                        ss1.setSpan(new ForegroundColorSpan(Color.BLUE), reply_user_name.length() + 2, reply_user_name.length() + reply_user2_name.length() + 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                                                        tvReplytoComment.setText(ss1);//显示框
                                                    }
                                                } else {
                                                    //对评论人人进行回复时显示如下//用户名+ ：+内容+时间
                                                    SpannableString ss2;
                                                    ss2 = new SpannableString(reply_user_name + "：" + post_reply_text + "  " + post_reply_date);
                                                    ss2.setSpan(new ForegroundColorSpan(Color.BLUE), 0, reply_user_name.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                                                    ss2.setSpan(new ForegroundColorSpan(Color.BLUE), reply_user_name.length(), reply_user_name.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                                                    tvReplytoComment.setText(ss2);//显示框
                                                }
                                            }
                                };
                                listviewsecondrely.setAdapter(replyCommonAdapter);
                                listviewsecondrely.setOnItemClickListener(new AdapterView.OnItemClickListener() {//点击回复评论的人进行回复//回复列表点击事件
                                    @Override
                                    public void onItemClick(AdapterView<?> adapterView, View view, final int position, long id) {
                                        Post_reply_tbl post_reply_tbl = post_reply_tbls.get(position);
                                        final int reply_user_id2 = post_reply_tbl.getReply_user_id();//要回复的人的id
                                        //addData2(reply_user_id2);//查询获取数据用户
                                        String reply_user2_name = post_reply_tbl.getUsershow_tbl().getUsershow_name();//要回复人姓名
                                        final int post_comment_id = post_reply_tbl.getPost_comment_id();
                                        mainBottom2.setVisibility(View.GONE);//我要回复比较隐藏
                                        commentLinear.setVisibility(View.VISIBLE);//输入框显示
                                        commentEdit.requestFocus();
                                        onFocusChange(true);//键盘显示
                                        if (reply_user2_name != null) {
                                            commentEdit.setHint("回复 " + reply_user2_name + ":");
                                            //输入框提示文字
                                            final String string1 = commentEdit.getHint().toString();
                                            commentEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                                                @Override
                                                public void onFocusChange(View arg0, boolean hasFocus) {
                                                    if (hasFocus) {
                                                        commentEdit.setHint(null);//点击时清楚提示文字
                                                    } else {
                                                        commentEdit.setHint(string1);//未点击时显示提示文字
                                                    }
                                                }
                                            });
                                        }
                                        commentButton.setOnClickListener(new View.OnClickListener() {//点击发送数据//输入框布局发送按钮
                                            @Override
                                            public void onClick(View v) {
                                                String reply_text = commentEdit.getText().toString();
                                                if (reply_text.equals("")) {
                                                    Toast.makeText(getApplicationContext(), "回复内容不能为空", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                                                    String post_reply_date = sDateFormat.format(new Date());
                                                    //private int post_reply_type;//1表示对评论进行回复，2表示对回复进行回复
                                                    addData(((MyApplication) ComunityPost_details_Activity.this.getApplication()).getUser().getUser_id(), reply_text, reply_user_id2, post_reply_date, post_comment_id, 2);//发送到服务器
                                                }
                                            }
                                        });
                                    }
                                });
                            } else {
                                        replyCommonAdapter.notifyDataSetChanged();
                                    }
                                    final CommonAdapter<Post_reply_tbl> finalReplyCommonAdapter = replyCommonAdapter;
                                    tvgetall.setOnClickListener(new View.OnClickListener() {//点击查看更多
                                        @Override
                                        public void onClick(View v) {
                                            getData();
                                            post_reply_tbls.clear();
                                            post_reply_tbls.addAll(post_reply_tbls1);//更改数据源
                                            finalReplyCommonAdapter.notifyDataSetChanged();//保持原来位置
                                            count+=1;
                                        }
                                    });
                                }
                            };
                            listviewFirstrely.setAdapter(commentAdapter);
                        } else {
                            commentAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        Log.i("xUtils_Activity", "onError: get" + ex.getMessage());
                    }

                    @Override
                    public void onCancelled(CancelledException cex) {
                        Log.i("xUtils_Activity", "onCancelled: get" + cex);
                    }

                    @Override
                    public void onFinished() {
                        Log.i("xUtils_Activity", "onFinished: get");
                    }
                }
        );
    }
    public void addData(int reply_user_id, String post_reply_text, int reply_user2_id, String post_reply_date, int post_comment_id, int post_reply_type) {
        //有参数方法//在评论者的内容及有关进行回复
        String url = URL.url + "Post_reply_tbl_insert_Servlet";
        //private int post_reply_type;//1表示对评论进行回复，2表示对回复进行回复
        Log.i("commentButton", "onClick: "+333333);
        RequestParams requestParams = new RequestParams(url);
        requestParams.addQueryStringParameter("reply_user_id", reply_user_id + "");//或名称
        requestParams.addQueryStringParameter("post_reply_text", post_reply_text);
        requestParams.addQueryStringParameter("reply_user2_id", reply_user2_id + "");//listview点击事件才有
        requestParams.addQueryStringParameter("post_reply_date", post_reply_date + "");
        requestParams.addQueryStringParameter("post_reply_type", post_reply_type + "");
        requestParams.addQueryStringParameter("post_comment_id", post_comment_id + "");
        Log.i("commentButton", "onClick: "+444444);
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("commentButton", "onClick: "+result);
                //getData1();
                commentLinear.setVisibility(View.GONE);
                mainBottom2.setVisibility(View.VISIBLE);//我要回复比较隐藏
                commentEdit.clearFocus();//取消焦点
                onFocusChange(false);//键盘隐藏
                commentEdit.setText("");//清空输入框
                //getData1(post_id);
                getData();
                Toast.makeText(getApplicationContext(), "回复成功", Toast.LENGTH_SHORT).show();
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
    public void getPost_tbl(int post_id) {//查询某个用户的信息
        //查询所有的评论的回复
        RequestParams requestParams2 = new RequestParams(URL.url + "Post_tbl_getOnePost_servlet");
        Log.i("xUtils_Activity", "成功222222222222" + 1111111);
        requestParams2.addQueryStringParameter("post_id",post_id + "");
        Log.i("xUtils_Activity", "成功222222222222" + 22222222+post_id);
        x.http().get(requestParams2, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        Log.i("xUtils_Activity", "成功222222222222result" + result);//gson如何转集合
                        Gson gson = new Gson();
                        Post_tbl post_tbl = gson.fromJson(result, Post_tbl.class);
                        Log.i("xUtils_Activity", "成功222222222222usershowTbl" + post_tbl);
                        postClick.setText(post_tbl.getPost_click() + "");//帖子点击量
                        postName.setText(post_tbl.getPost_name());//帖子标题
                        postText.setText(post_tbl.getPost_text());//帖子内容
                        postEndrelytime.setText(post_tbl.getPost_time() + "");//帖子发表时间
                        userName.setText(post_tbl.getUsershow_tbl().getUsershow_name());//帖子发表人昵称
                        String useriamege=post_tbl.getUsershow_tbl().getUsershow_head();//用户头像
                        String userpicture=post_tbl.getPost_picture();//用户发表的图片//字符串类型
                        Gson gson1=new Gson();
                        Type type=new TypeToken<List<String>>(){}.getType();
                        List<String> userspicture=gson1.fromJson(userpicture,type);//图片集
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        Log.i("xUtils_Activity", "onError: get" + ex.getMessage());
                    }

                    @Override
                    public void onCancelled(CancelledException cex) {
                        Log.i("xUtils_Activity", "onCancelled: get" + cex);
                    }

                    @Override
                    public void onFinished() {
                        Log.i("xUtils_Activity", "onFinished: get");
                    }
                }
        );
    }
    private void onFocusChange(boolean hasFocus) {//判断输入键盘显示与否
        final boolean isFocus = hasFocus;
        (new Handler()).postDelayed(new Runnable() {
            @TargetApi(Build.VERSION_CODES.CUPCAKE)
            public void run() {
                InputMethodManager imm = (InputMethodManager)
                        commentEdit.getContext().getSystemService(INPUT_METHOD_SERVICE);
                if (isFocus) {
                    //显示输入法
                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                } else {
                    //隐藏输入法
                    imm.hideSoftInputFromWindow(commentEdit.getWindowToken(), 0);
                }
            }
        }, 100);
    }
    @Override
    public void onBackPressed() {//返回键点击事件
        //super.onBackPressed();
        //判断控件是否显示commentLinear.getVisibility()==View.VISIBLE
        if(commentLinear.getVisibility()==View.VISIBLE){//如果显示
            commentLinear.setVisibility(View.GONE);//输入框//隐藏
            mainBottom2.setVisibility(View.VISIBLE);//显示
        }
        return;
    }
    @OnClick({R.id.return_community, R.id.sendRelypost_toPost})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.return_community://返回社区
                finish();
                break;
            case R.id.sendRelypost_toPost://评论者对帖子进行评论//跳转到回复界面
                Intent intent2 = new Intent(this, CommunityPost_comment_Activity.class);
                intent2.putExtra("post_id",post_id);
                startActivityForResult(intent2,1);
                break;
        }
    }

}