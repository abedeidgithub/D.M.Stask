package com.example.abedeid.dmstask;

import android.content.SharedPreferences;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.abedeid.dmstask.Adapters.RepoAdapter;
import com.example.abedeid.dmstask.EndLess.EndlessRecyclerViewScrollListener;
import com.example.abedeid.dmstask.Services.WebService;
import com.example.abedeid.dmstask.obj.Repo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Home extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RepoAdapter adapter;
    private List<Repo> RepoList;
    LinearLayoutManager linearLayoutManager;
    int Page_num=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        RepoList=new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        linearLayoutManager = new LinearLayoutManager(Home.this);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                Page_num++;
                getRepoOfPage(Page_num);
            }
        });

        getRepoOfPage(Page_num);
        // refresh
        final SwipeRefreshLayout swipeRefreshLayout= (SwipeRefreshLayout)findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override

            public void onRefresh() {
                doYourUpdate();
            }

            private void doYourUpdate() {
                Toast.makeText(Home.this, "Clear Data ", Toast.LENGTH_SHORT).show();
                SharedPreferences sharedPreferences=getSharedPreferences("shared perfrances", MODE_PRIVATE);
                sharedPreferences.edit().clear().commit();
                Page_num=0;
                getRepoOfPage(Page_num);
                swipeRefreshLayout.setRefreshing(false);
            }

        });


    }
    private void savedata(ArrayList<Repo> repoList) {
        SharedPreferences sharedPreferences=getSharedPreferences("shared perfrances", MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        Gson gson=new Gson();
        String json=gson.toJson(repoList);
        editor.putString("list",json);
        editor.apply();

    }

    private ArrayList<Repo> loaddataLocal(){
        SharedPreferences sharedPreferences=getSharedPreferences("shared perfrances", MODE_PRIVATE);
        Gson gson=new Gson();
        String json=sharedPreferences.getString("list",null);
        Type type=new TypeToken<ArrayList<Repo>>() {}.getType();
        return gson.fromJson(json,type);

    }


    private void getRepoOfPage(int page_num){
        adapter = new RepoAdapter(RepoList, Home.this);
        recyclerView.setAdapter(adapter);
        WebService.getInstance().getApi().get_repo_data("Square",page_num+"","10").enqueue(new Callback<ArrayList<Repo>>() {
            @Override
            public void onResponse(Call<ArrayList<Repo>> call, Response<ArrayList<Repo>> response) {

                RepoList.addAll(response.body());
                savedata(response.body());
                adapter = new RepoAdapter(RepoList, Home.this);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<ArrayList<Repo>> call, Throwable t) {
                Log.e("aqaaa",t.getMessage());
                RepoList.addAll( loaddataLocal());
                adapter.notifyItemChanged(adapter.getItemCount(),RepoList.size()-1);
            }
        });

    }
}
