package com.example.tust.tmdbmovieviewer.Activity;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.AbsListView;
import android.widget.ListView;

import com.example.tust.tmdbmovieviewer.Adapter.MovieTMDbAdapter;
import com.example.tust.tmdbmovieviewer.R;
import com.example.tust.tmdbmovieviewer.api.MovieDBClient;
import com.example.tust.tmdbmovieviewer.api.WebServiceClient;
import com.example.tust.tmdbmovieviewer.model.Request.WebServiceRequest;
import com.example.tust.tmdbmovieviewer.model.Response.MoviesResponse;


import java.util.HashMap;
import java.util.Map;

public class PopularActivity extends ListActivity {

    private MoviesResponse movies;
    private WebServiceClient client = MovieDBClient.getInstance();
    private ListView moviesListView;
    private MovieTMDbAdapter moviesAdapter;
    private String defaultRequestUrl = "/3/movie/popular";

    private ProgressDialog pDialog;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list1);
        moviesListView = getListView();
        new LoadPopular().execute();
    }
    class LoadPopular extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(PopularActivity.this);
            pDialog.setMessage("Loading data ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }


    protected String doInBackground(String... args) {

        movies = getInitialMovies();
        moviesAdapter = new MovieTMDbAdapter(getApplicationContext(), movies, defaultRequestUrl, client);


        return null;
    }


    protected void onPostExecute(String file_url) {
        pDialog.dismiss();

        runOnUiThread(new Runnable() {
            public void run() {

                moviesListView.setAdapter(moviesAdapter);
                moviesListView.setOnScrollListener(new AbsListView.OnScrollListener() {

                    @Override
                    public void onScrollStateChanged(AbsListView view, int scrollState) {
                    }

                    @Override
                    public void onScroll(AbsListView view, int firstVisibleItem,
                                         int visibleItemCount, int totalItemCount) {

                        if (totalItemCount > 0) {
                            int lastInScreen = firstVisibleItem + visibleItemCount;
                            if ((lastInScreen == totalItemCount) && (lastInScreen != movies.getTotalResults())) {
                                Log.i("ListView", "End");
                                moviesAdapter.nextPage();
                                moviesAdapter.updateMovies();
                                moviesAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                });
            }
        });

    }

}
    private MoviesResponse getInitialMovies() {
        Map<String, String> parameters =  new HashMap<String, String>();



        WebServiceRequest request = new WebServiceRequest();
        request.setHost(MovieDBClient.HOST);
        request.setParameters(parameters);
        request.setRequestUrl(defaultRequestUrl);

        MoviesResponse movies = client.sendRequest(request, MoviesResponse.class);
        Log.i("Movies", movies.toString());
        return movies;
    }
}