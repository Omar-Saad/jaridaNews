package com.news.akhbar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

public class DepartmentFragment extends Fragment implements View.OnClickListener {

    CardView card1;
    CardView card2;
    CardView card3;
    CardView card4;
    CardView card5;
    CardView card6;
    Fragment fragment;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_department, null);
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
    //    ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.app_name);


        cardDeclaration(view);

    }

    void cardDeclaration(View view) {
        card1 = view.findViewById(R.id.card1);
        card1.setOnClickListener(this);
        card2 = view.findViewById(R.id.card2);
        card2.setOnClickListener(this);
        card3 = view.findViewById(R.id.card3);
        card3.setOnClickListener(this);
        card4 = view.findViewById(R.id.card4);
        card4.setOnClickListener(this);
        card5 = view.findViewById(R.id.card5);
        card5.setOnClickListener(this);
        card6 = view.findViewById(R.id.card6);
        card6.setOnClickListener(this);


    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.card1:
                fragment = new HomeFragment();
                break;
            case R.id.card2:
                fragment = new SportsFragment();
                break;
            case R.id.card3:
                fragment = new WorldFragment();
                break;
            case R.id.card4:
                fragment = new TechnologyFragment();
                break;
            case R.id.card5:
                fragment = new SourceFragment();
                break;
            case R.id.card6:
                fragment = new SettingFragment();
                break;

        }
        getFragmentManager().beginTransaction().replace(R.id.fragment , fragment).addToBackStack(null).commit();    }



}