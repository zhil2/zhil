package com.example.administrator.kdc.Community.Search;//搜索，并保存历史搜索记录

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.kdc.Community.AdapterViewHolder.CommonAdapter;
import com.example.administrator.kdc.Community.AdapterViewHolder.ViewHolder;
import com.example.administrator.kdc.Community.CommunityLayout.CommunityHome;
import com.example.administrator.kdc.Community.ServletURL.URL;
import com.example.administrator.kdc.R;
import com.example.administrator.kdc.vo.Community_tbl;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class Community_SearchActivity extends Activity {

    @InjectView(R.id.search_return_maincommunity)
    ImageView searchReturnMaincommunity;
    @InjectView(R.id.bt_searchcommuinty)
    Button btSearchcommuinty;
    private EditText et_searchcomunity;
    private TextView tv_searchcomunity_history;
    private MySearchListView searchListView;
    private TextView tv_clearcomunity;
    private Community_RecordSQLiteOpenHelper helper = new Community_RecordSQLiteOpenHelper(this);
    private SQLiteDatabase db;
    public BaseAdapter adapter;
    public List<Community_tbl> community_tbls = new ArrayList<Community_tbl>();
    public CommonAdapter<Community_tbl> Adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.community_search);
        ButterKnife.inject(this);
        // 初始化控件
        initView();

        // 清空搜索历史
        tv_clearcomunity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteData();
                queryData("");
            }
        });

        // 搜索框的键盘搜索键点击回调
        et_searchcomunity.setOnKeyListener(new View.OnKeyListener() {// 输入完后按键盘上的搜索键

            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {// 修改回车键功能
                    // 先隐藏键盘
                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                            getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    // 按完搜索键后将当前查询的关键字保存起来,如果该关键字已经存在就不执行保存
                    boolean hasData = hasData(et_searchcomunity.getText().toString().trim());
                    if (!hasData) {
                        insertData(et_searchcomunity.getText().toString().trim());
                        queryData("");
                    }
                    // TODO 根据输入的内容模糊查询商品，并跳转到另一个界面，由你自己去实现
                    //Toast.makeText(Community_SearchActivity.this, "clicked!", Toast.LENGTH_SHORT).show();

                }
                return false;
            }
        });

        // 搜索框的文本变化实时监听
        et_searchcomunity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().trim().length() == 0) {
                    tv_searchcomunity_history.setText("搜索历史");
                } else {
                    tv_searchcomunity_history.setText("搜索结果");
                }
                String tempName = et_searchcomunity.getText().toString();
                // 根据tempName去模糊查询数据库中有没有数据
                //queryData(tempName);
                selectToSportstype(tempName);

            }
        });

        searchListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = (TextView) view.findViewById(android.R.id.text1);
                String name = textView.getText().toString();
                et_searchcomunity.setText(name);
                selectToSportstype(name);
                //Toast.makeText(Community_SearchActivity.this, name, Toast.LENGTH_SHORT).show();
                // TODO 获取到item上面的文字，根据该关键字跳转到另一个页面查询，由你自己去实现
            }
        });
        queryData("");
    }

    /**
     * 插入数据
     */
    private void insertData(String tempName) {
        db = helper.getWritableDatabase();
        db.execSQL("insert into comunity_records(comunity_name) values('" + tempName + "')");
        db.close();
    }

    /**
     * 模糊查询数据
     */
    private void queryData(String tempName) {
        Cursor cursor = helper.getReadableDatabase().rawQuery(
                "select comunity_id as _id,comunity_name from comunity_records where comunity_name like '%" + tempName + "%' order by comunity_id desc ", null);
        // 创建adapter适配器对象
        adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, cursor, new String[]{"comunity_name"},
                new int[]{android.R.id.text1}, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        // 设置适配器
        searchListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    /**
     * 检查数据库中是否已经有该条记录
     */
    private boolean hasData(String tempName) {
        Cursor cursor = helper.getReadableDatabase().rawQuery(
                "select comunity_id as _id,comunity_name from comunity_records where comunity_name =?", new String[]{tempName});
        //判断是否有下一个
        return cursor.moveToNext();
    }

    /**
     * 清空数据
     */
    private void deleteData() {
        db = helper.getWritableDatabase();
        db.execSQL("delete from comunity_records");
        db.close();
    }

    /**
     * 查询数据从社区表
     */
    private void selectToSportstype(String tempName) {
        String url4 = URL.url + "Community_getByName_Servlet";
        RequestParams requestParams4 = new RequestParams(url4);
        requestParams4.addQueryStringParameter("community_name", tempName);
        x.http().get(requestParams4, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        if(result.equals(""))
                        {
                            Toast.makeText(Community_SearchActivity.this, "...搜索中..." ,Toast.LENGTH_SHORT).show();
                        }else {
                            Log.i("xUtils_Activity", "成功" + result);//gson如何转集合
                            Gson gson = new Gson();
                            Type type = new TypeToken<List<Community_tbl>>() {
                            }.getType();
                            community_tbls = gson.fromJson(result, type);//解析成List<Product>
                            Log.i("community_tbls", "onSuccess: " + community_tbls);
                            if (community_tbls.size() > 0) {
                                Adapter = new CommonAdapter<Community_tbl>(Community_SearchActivity.this, community_tbls, R.layout.community_record_item) {
                                    @Override
                                    public void convert(ViewHolder viewHolder, Community_tbl community_tbl, int position) {
                                        TextView tvname = viewHolder.getViewById(R.id.record);
                                        tvname.setText(community_tbl.getCommunity_name());
                                    }
                                };
                                searchListView.setAdapter(Adapter);
                                searchListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                                        Intent intent = new Intent(Community_SearchActivity.this, CommunityHome.class);
                                        Community_tbl communityTbl = community_tbls.get(position);
                                        Bundle bundle = new Bundle();
                                        bundle.putParcelable("sports_tbl", communityTbl);
                                        intent.putExtras(bundle);
                                        startActivity(intent);
                                        finish();
                                    }
                                });
                                Adapter.notifyDataSetChanged();
                                Log.i("xUtils_Activity", "onSuccess: get post_tblstrach" + result);

                                Log.i("xUtils_Activity", "onSuccess:String listJson=result" + result);
                            } else {
                                Toast.makeText(Community_SearchActivity.this, "你所搜索的社区还没有创建！", Toast.LENGTH_SHORT).show();
                            }
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

    private void initView() {
        et_searchcomunity = (EditText) findViewById(R.id.et_searchcommuinty);
        tv_searchcomunity_history = (TextView) findViewById(R.id.tv_searhcomunity_history);
        searchListView = (MySearchListView) findViewById(R.id.search_listView);
        tv_clearcomunity = (TextView) findViewById(R.id.tv_clearcomunity);

        // 调整EditText左边的搜索按钮的大小
        Drawable drawable = getResources().getDrawable(R.drawable.serchpost);
        drawable.setBounds(0, 0, 60, 60);// 第一0是距左边距离，第二0是距上边距离，60分别是长宽
        et_searchcomunity.setCompoundDrawables(drawable, null, null, null);// 只放左边
    }

    @OnClick({R.id.search_return_maincommunity, R.id.bt_searchcommuinty})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search_return_maincommunity://返回上一级
                finish();
                break;
            case R.id.bt_searchcommuinty:
                boolean hasData = hasData(et_searchcomunity.getText().toString().trim());
                if (!hasData) {
                    insertData(et_searchcomunity.getText().toString().trim());
                    queryData("");
                }
                //Toast.makeText(Community_SearchActivity.this, "clicked!", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
