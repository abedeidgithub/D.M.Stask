package com.example.abedeid.dmstask.API;

import com.example.abedeid.dmstask.obj.Repo;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by abed eid on 08/05/2019.
 */

public interface API {
    @GET("{user}/repos")
    Call<ArrayList<Repo>>get_repo_data(@Path("user") String user, @Query("page") String page, @Query("per_page") String per_page);
//    @GET("student.php")
//    Call<ArrayList<Repo>>get_repo_data();
}
