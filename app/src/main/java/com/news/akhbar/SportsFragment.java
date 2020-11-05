package com.news.akhbar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class SportsFragment extends Fragment {


    String sprts[];
    SwipeRefreshLayout swipeRefreshLayout;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, null);

        return view;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TinyDB tinyDB = new TinyDB(getActivity());
        CommonTasks.changeLanguage(getActivity() ,new String[]{ tinyDB.getString("lang")} , new String[]{ tinyDB.getString("country")});
    }



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        swipeRefreshLayout = view.findViewById(R.id.swipe);
     //   ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.nav_sports);
        String api = SplashActivity.api_key;

        sprts = new String[]{"https://newsapi.org/v2/top-headlines?country=eg&category=sports&apiKey="+api

                , "https://newsapi.org/v2/top-headlines?country=ae&category=sports&apiKey="+api
                , "https://newsapi.org/v2/top-headlines?country=sa&category=sports&apiKey="+api,
                "https://newsapi.org/v2/top-headlines?country=gb&category=sports&apiKey="+api,
                "https://newsapi.org/v2/top-headlines?country=us&category=sports&apiKey="+api};
        connectToApi(sprts);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                connectToApi(sprts);
            }
        });

    }

    void connectToApi(String... url)
    {
        MyAsyncTaskgetNews  newsApi = new MyAsyncTaskgetNews(getActivity());
        newsApi.execute(url);

    }
}

