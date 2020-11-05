package com.news.akhbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;


public class SettingFragment extends Fragment {

    Button shareBtn;
    Button contactBtn;
    Button rateBtn;
    Button languageBtn;
    TinyDB tinyDB;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings ,null);
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
        shareBtn = view.findViewById(R.id.btn_share);
        contactBtn = view.findViewById(R.id.btn_contact);
        rateBtn = view.findViewById(R.id.btn_rate);
      //  tinyDB = new TinyDB(getActivity());
        rateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id="+getActivity().getPackageName())));

            }
        });
        languageBtn = view.findViewById(R.id.btn_language);
       // ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.app_name);

        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonTasks.share(getActivity() , "  https://play.google.com/store/apps/details?id="+getActivity().getPackageName());


            }
        });
        contactBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonTasks.contact(getActivity());
            }
        });

        languageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              //  CommonTasks.changeLanguage(getActivity());
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                alertDialog.setIcon(R.drawable.global);
                alertDialog.setTitle(R.string.single_option_dialog_title);
                final String[] country = new String[1];
                final String[] lang = new String[1];
                alertDialog.setSingleChoiceItems(getActivity().getResources().getStringArray(R.array.language), -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which)
                        {
                            case 0:
                                country[0] = "us";
                                lang[0] = "en";
                                break;
                            case 1:
                                country[0] = "eg";
                                lang[0] = "ar";
                                break;


                        }
                    }
                });
                alertDialog.setPositiveButton(R.string.single_option_dialog_postive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //create a string for country
                        CommonTasks.changeLanguage(getActivity(),lang,country);
                        tinyDB.putString("lang",lang[0]);
                        tinyDB.putString("country",country[0]);
                        getActivity().startActivity(getActivity().getIntent());
                        getActivity().finish();



                    }
                });



                alertDialog.show();


            }
        });

    }
}
