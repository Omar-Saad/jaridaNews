package com.news.akhbar;

import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity  implements PopupMenu.OnMenuItemClickListener {

    BottomNavigationView navigationView;
    Fragment fragment;
  //  Toolbar toolbar;
    TextView popupBtn;
    TinyDB tinyDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tinyDB = new TinyDB(MainActivity.this);
         CommonTasks.changeLanguage(MainActivity.this ,new String[]{ tinyDB.getString("lang")} , new String[]{ tinyDB.getString("country")});
        setContentView(R.layout.activity_main);



        popupBtn = findViewById(R.id.popupBtn);
       // tv_head=findViewById(R.id.tv_head);
        if (!isNetworkConnected()){
            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setIcon(R.drawable.nowifi);
            alertDialog.setTitle(R.string.no_internet);

            alertDialog.setPositiveButton(R.string.single_option_dialog_postive, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });


            alertDialog.show();
        }

        bottomNav();


       // toolbar = findViewById(R.id.toolbar);

         // setSupportActionBar(toolbar);
        //  toolbar.setTitle(" ");


    }

    void bottomNav(){
        navigationView = findViewById(R.id.bottom_nav);
     // navigationView.setSelectedItemId(R.id.home_nav);
        fragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment , fragment).commit();

        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {


            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()){

                    case R.id.home_nav:
                        fragment = new HomeFragment();
                        popupBtn.setText(R.string.top_news);
                        popupBtn.setVisibility(View.VISIBLE);

                    //    popupBtn.setText(R.string.top_news);



                        break;
                    case R.id.fav_nav:
                        fragment = new FavouriteFragment();
                        popupBtn.setVisibility(View.GONE);


                        //  popupBtn.setText(R.string.fav);
                       // popupBtn.setOnClickListener(null);
                       // popupBtn.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);


                        break;
                    case R.id.dep_nav:
                        fragment = new DepartmentFragment();
                        popupBtn.setVisibility(View.GONE);



                        break;
                    case R.id.settings_nav:
                        fragment = new SettingFragment();
                        popupBtn.setVisibility(View.GONE);

                      //  toolbar.setTitle(R.string.action_settings);


                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment , fragment).commit();



                return true;
            }
        });

    }

    private boolean isNetworkConnected() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;
        ConnectivityManager cm = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }


    public void showpopup(View v){
        PopupMenu popupMenu = new PopupMenu(this,v);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.popup_menu);
        popupMenu.show();

    }


    @Override
    public boolean onMenuItemClick(MenuItem item)
    {

        switch (item.getItemId()){

            case R.id.top_news_nav:
                fragment = new HomeFragment();


                break;
            case R.id.sports_news_nav:
                fragment = new SportsFragment();

                break;
            case R.id.tech_news_nav:
                fragment = new TechnologyFragment();


                break;
            case R.id.world_news_nav:
                fragment = new WorldFragment();

             //   toolbar.setTitle(R.string.action_settings);


                break;
        }
        popupBtn.setText(item.getTitle());
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment , fragment).commit();
        return true;
    }



}