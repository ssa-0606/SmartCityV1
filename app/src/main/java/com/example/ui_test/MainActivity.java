package com.example.ui_test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.example.ui_test.adapter.NewsItemAdapter;
import com.example.ui_test.adapter.ServiceAdapter;
import com.example.ui_test.pojo.ItemNewsCategory;
import com.example.ui_test.pojo.ItemNewsList;
import com.example.ui_test.pojo.SmartCityService;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;


public class MainActivity extends AppCompatActivity {

    //首页轮播图集合
    private List<String> imgs;
    private ViewFlipper viewFlipper;
    //首页服务数据集合
    private List<SmartCityService> services;
    private RecyclerView recyclerViewService;
    //新闻种类数据集合
    private List<ItemNewsCategory> newsCategories;
    //新闻详细信息数据集合
    private List<ItemNewsList> newsLists;


    private TabLayout tabLayout;
    private RecyclerView recyclerViewNews;


    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 1:
                    viewFlipper.removeAllViews();
                    for (String img : imgs) {
                        ImageView imageView = new ImageView(getBaseContext());
                        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        Glide.with(getBaseContext()).load(img).into(imageView);
                        viewFlipper.addView(imageView);
                    }
                    break;
                case 2:
                    recyclerViewService.setLayoutManager(new GridLayoutManager(MainActivity.this,6));
                    recyclerViewService.setAdapter(new ServiceAdapter(services,R.layout.service_item));
                    break;
                case 3:
                    for (int i = 0; i < newsCategories.size(); i++) {
                        tabLayout.addTab(tabLayout.newTab().setText(newsCategories.get(i).getName()).setTag(newsCategories.get(i).getId()));
                    }
                    recyclerViewNews.setLayoutManager(new GridLayoutManager(getBaseContext(),1));
                    recyclerViewNews.setAdapter(new NewsItemAdapter(R.layout.news_item,newsCategories.get(0).getNewsLists()));
                    break;
                case 4:
                    int position = (int)msg.obj;
                    recyclerViewNews.setLayoutManager(new GridLayoutManager(getBaseContext(),1));
                    recyclerViewNews.setAdapter(new NewsItemAdapter(R.layout.news_item,newsCategories.get(position).getNewsLists()));
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //get轮播组件
        viewFlipper = (ViewFlipper) findViewById(R.id.vf);
        //get服务recyclerview
        recyclerViewService = (RecyclerView) findViewById(R.id.recycler_view);
        //get新闻种类tab
        tabLayout = (TabLayout) findViewById(R.id.tab_out);
        recyclerViewNews = (RecyclerView) findViewById(R.id.news_recycler);

        //网络中获取轮播图
        // TODO: 2022/4/9 封装轮播图对象，包括点击图片跳转详情页所需要的id
        getLunBo();

        //网络中获取全部服务（主页中）
        // TODO: 2022/4/9 服务排序（数值越大，优先级越高）;第二行最后一个是显示全部服务; 点击跳转服务各自领域页面
        getService();

        getNews();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Message message = new Message();
                message.what = 4;
                message.obj = tab.getPosition();
                handler.sendMessage(message);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }


    public void getLunBo(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url("http://124.93.196.45:10001/prod-api/api/rotation/list?type=2").build();
                Call call = client.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {

                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        String url = response.body().string();
                        try {
                            JSONObject jsonObject = new JSONObject(url);
                            JSONArray rows = jsonObject.getJSONArray("rows");
                            imgs = new ArrayList<>();
                            for(int i=0;i< rows.length();i++){
                                JSONObject jsonObject1 = rows.getJSONObject(i);
                                String img = "http://124.93.196.45:10001"+jsonObject1.getString("advImg");
                                imgs.add(img);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        handler.sendEmptyMessage(1);
                    }
                });



            }
        });
        thread.start();
    }

    public void getService(){
        Thread thread1 = new Thread(()->{
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url("http://124.93.196.45:10001/prod-api/api/service/list").build();
            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {

                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    String url = response.body().string();
                    services = new ArrayList<>();
                    try {
                        JSONObject jsonObject = new JSONObject(url);
                        JSONArray rows = jsonObject.getJSONArray("rows");
                        SmartCityService smartCityService;
                        for (int i = 0; i < rows.length(); i++) {
                            JSONObject jsonObject1 = rows.getJSONObject(i);
                            int id = jsonObject1.getInt("id");
                            String serviceName = jsonObject1.getString("serviceName");
                            String serviceDesc = jsonObject1.getString("serviceDesc");
                            String serviceType = jsonObject1.getString("serviceType");
                            String imgUrl = jsonObject1.getString("imgUrl");
                            int sort = jsonObject1.getInt("sort");
                            smartCityService = new SmartCityService(id,serviceName,serviceDesc,serviceType,"http://124.93.196.45:10001"+imgUrl,sort);
//                            Log.d("TAG1", "onResponse: "+smartCityService);
                            services.add(smartCityService);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    handler.sendEmptyMessage(2);
                }
            });
        });
        thread1.start();
    }

    public void getNews(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url("http://124.93.196.45:10001/prod-api/press/category/list").build();
                Call call = client.newCall(request);
                try {
                    ResponseBody body = call.execute().body();
                    String data = body.string();
                    JSONObject jsonObject = new JSONObject(data);
                    JSONArray jsonNewsCategories = jsonObject.getJSONArray("data");
                    newsCategories = new ArrayList<>();
                    for (int i = 0; i < jsonNewsCategories.length(); i++) {
                        JSONObject jsonNewsCategory = jsonNewsCategories.getJSONObject(i);
                        int id = jsonNewsCategory.getInt("id");
                        String name = jsonNewsCategory.getString("name");
                        int sort = jsonNewsCategory.getInt("sort");
                        Request request1 = new Request.Builder().url("http://124.93.196.45:10001/prod-api/press/press/list?type="+id).build();
                        Call call1 = client.newCall(request1);
                        String jsonNews = call1.execute().body().string();
                        JSONObject jsonObject1 = new JSONObject(jsonNews);
                        JSONArray jsonNewsItems = jsonObject1.getJSONArray("rows");
                        newsLists = new ArrayList<>();
                        for (int j = 0; j < jsonNewsItems.length(); j++) {
                            JSONObject jsonNewsItem = jsonNewsItems.getJSONObject(j);
                            int newsId = jsonNewsItem.getInt("id");
                            String title = jsonNewsItem.getString("title");
                            String cover = jsonNewsItem.getString("cover");
                            String content = jsonNewsItem.getString("content");
                            int commentNum = jsonNewsItem.getInt("commentNum");
                            int likeNum = jsonNewsItem.getInt("likeNum");
                            int readNum = jsonNewsItem.getInt("readNum");
                            String publishDate = jsonNewsItem.getString("publishDate");
                            newsLists.add(new ItemNewsList(newsId,title,"http://124.93.196.45:10001"+cover,content,commentNum,likeNum,readNum,publishDate));
                        }
                        newsCategories.add(new ItemNewsCategory(id,name,sort,newsLists));
                    }
                    Log.d("TAG1", "run: "+newsCategories);
                    handler.sendEmptyMessage(3);
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }

            }
        });
        thread.start();
    }



}