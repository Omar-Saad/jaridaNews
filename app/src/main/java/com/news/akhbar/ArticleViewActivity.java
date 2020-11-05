package com.news.akhbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.squareup.picasso.Picasso;

public class ArticleViewActivity extends AppCompatActivity {

    ImageView imageView;
    TextView textView;
    Button button;
    TextView tvTitle;
    ToggleButton fav;
    ImageView share;
    TinyDB tinyDB;
    private InterstitialAd mInterstitialAd;


    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tinyDB = new TinyDB(ArticleViewActivity.this);
        CommonTasks.changeLanguage(ArticleViewActivity.this ,new String[]{ tinyDB.getString("lang")} , new String[]{ tinyDB.getString("country")});
        setContentView(R.layout.activity_article_view);

       //ad
        ///put your ad id

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-6129503049169576/3455662182");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        //end
        imageView = findViewById(R.id.img_article_conrnent);
        textView =findViewById(R.id.tv_article_contnent);
        button = findViewById(R.id.btn_read_more);
        tvTitle = findViewById(R.id.tv_article_title);
        share = findViewById(R.id.share_article);
        fav = findViewById(R.id.fav_article);
        final String imageURL = getIntent().getStringExtra("imgURL");
        final String contnent = getIntent().getStringExtra("articleContent");
        final String url = getIntent().getStringExtra("url");
         final String title = getIntent().getStringExtra("title");
         tvTitle.setText(title);
        Picasso.get().load(imageURL).into(imageView);
        if(contnent==null)
        textView.setText("");
        else
            textView.setText(contnent);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    //Log.d("TAG", "The interstitial wasn't loaded yet.");
                }
                mInterstitialAd.setAdListener(new AdListener() {

                    @Override

                    public void onAdClosed() {
                        mInterstitialAd.loadAd(new AdRequest.Builder().build());
                        startActivity(new Intent(ArticleViewActivity.this , WebActivity.class).putExtra("url",url));

                    }


                });



            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonTasks.share(ArticleViewActivity.this , url);
            }
        });
        if(CommonTasks.isFva(ArticleViewActivity.this , title))
            fav.setBackgroundResource(R.drawable.ic_baseline_favorite_24);

        fav.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    CommonTasks.addFavourite(ArticleViewActivity.this, title, imageURL, url, contnent);
                    fav.setBackgroundResource(R.drawable.ic_baseline_favorite_24);
                }
                else {                fav.setBackgroundResource(R.drawable.ic_baseline_favorite_border_24);

                CommonTasks.removeFavourite(ArticleViewActivity.this, title, imageURL, url, contnent);
                }


            }
        });


    }

    @Override
    public void onBackPressed() {
        CommonTasks.inAppReview(ArticleViewActivity.this);
        super.onBackPressed();
    }
}