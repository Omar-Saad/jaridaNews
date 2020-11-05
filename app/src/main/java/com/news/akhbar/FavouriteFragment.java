package com.news.akhbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;

public class FavouriteFragment extends Fragment implements ListAdapter.OnArticleListner {

    RecyclerView listView;
    ArrayList titles;
    ArrayList images;
    ArrayList urls;
    ArrayList content;
    SwipeRefreshLayout swipeRefreshLayout;
    TinyDB tinyDB;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, null);
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         tinyDB = new TinyDB(getActivity());
        CommonTasks.changeLanguage(getActivity() ,new String[]{ tinyDB.getString("lang")} , new String[]{ tinyDB.getString("country")});
    }



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        swipeRefreshLayout = view.findViewById(R.id.swipe);
        swipeRefreshLayout.setRefreshing(false);
        // ((AppCompatActivity)getActivity()).getSupportActionBar().hide();

        listView = view.findViewById(R.id.listView);
     //   tinyDB = new TinyDB(getActivity());
        // Toast.makeText(getActivity(), "url :"+title, Toast.LENGTH_SHORT).show();
        titles = new ArrayList();
        images = new ArrayList();
        urls = new ArrayList();
        content = new ArrayList();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                favHandling();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        favHandling();

    }

    @Override
    public void onArticleClickListner(int position) {
        Intent intent = new Intent(getActivity(), ArticleViewActivity.class);
        intent.putExtra("articleContent", content.get(position).toString());
        intent.putExtra("url", urls.get(position).toString());
        intent.putExtra("imgURL", images.get(position).toString());
        intent.putExtra("title", titles.get(position).toString());
        //   Toast.makeText(activity, "url : "+images.get(position), Toast.LENGTH_SHORT).show();

        getActivity().startActivity(intent);

    }

    void favHandling() {

        titles = tinyDB.getListString("titles");
        images = tinyDB.getListString("images");
        urls = tinyDB.getListString("url");
        content = tinyDB.getListString("content");

        if (titles.size() == 0) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
            alertDialog.setIcon(R.drawable.ic_baseline_favorite_24);
            alertDialog.setTitle(R.string.single_option_dialog_title_fav);
            alertDialog.setPositiveButton(R.string.single_option_dialog_postive, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });


            alertDialog.show();

        } else {


        }
        listView.setAdapter(new ListAdapter(getActivity(), titles, images, urls, content, this));
        listView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }


}
