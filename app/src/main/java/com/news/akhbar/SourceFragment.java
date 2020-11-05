package com.news.akhbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

public class SourceFragment extends Fragment implements View.OnClickListener {


    CardView card1;
    CardView card2;
    CardView card3;
    CardView card4;
    CardView card5;
    CardView card6;
    CardView card7;
    CardView card8;
    // Fragment fragment;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_source, null);
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
        // ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.app_name);

        cardDeclaration(view);
        // Toast.makeText(getActivity(), "source", Toast.LENGTH_SHORT).show();

    }

    void cardDeclaration(View view) {
        card1 = view.findViewById(R.id.cardyoum7);
        card1.setOnClickListener(this);
        card2 = view.findViewById(R.id.cardmasrawy);
        card2.setOnClickListener(this);
        card3 = view.findViewById(R.id.cardmasryyoum);
        card3.setOnClickListener(this);
        card4 = view.findViewById(R.id.cardahram);
        card4.setOnClickListener(this);
        card5 = view.findViewById(R.id.cardcnn);
        card5.setOnClickListener(this);
        card6 = view.findViewById(R.id.cardbbc);
        card6.setOnClickListener(this);
        card7 = view.findViewById(R.id.cardarabya);
        card7.setOnClickListener(this);
        card8 = view.findViewById(R.id.cardsada);
        card8.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        String url = "";
        switch (v.getId()) {

            case R.id.cardyoum7:
                url = "https://www.youm7.com";
                break;
            case R.id.cardmasrawy:
                url = "https://www.masrawy.com";
                break;
            case R.id.cardmasryyoum:
                url = "https://www.almasryalyoum.com";
                break;
            case R.id.cardahram:
                url = "http://gate.ahram.org.eg";
                break;
            case R.id.cardcnn:
                url = "https://www.cnn.com";
                break;
            case R.id.cardbbc:
                url = "https://www.bbc.com";
                break;
            case R.id.cardarabya:
                url = "https://www.alarabiya.net/";
                break;

            case R.id.cardsada:
                url = "https://www.elbalad.news/";
                break;


        }

        Intent intent = new Intent(getActivity(), WebActivity.class);
        intent.putExtra("url", url);
        startActivity(intent);

    }


}