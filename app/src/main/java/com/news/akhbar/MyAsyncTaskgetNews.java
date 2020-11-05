package com.news.akhbar;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MyAsyncTaskgetNews extends AsyncTask<String, String, String>  implements ListAdapter.OnArticleListner {

   ArrayList<String> titles;
   ArrayList<String> images;
   ArrayList<String> urls;
   ArrayList<String> content;
   RecyclerView listView;
   SwipeRefreshLayout swipeRefreshLayout;
   Activity activity ;
   String home[];
    ListAdapter adapter;
    private InterstitialAd mInterstitialAd;
    private static int AD_COUNT;

    public MyAsyncTaskgetNews( Activity activity) {

        titles = new ArrayList();
        images = new ArrayList();
        urls = new ArrayList();
        content = new ArrayList<>();
        this.activity = activity;
        listView = activity.findViewById(R.id.listView);
        swipeRefreshLayout = activity.findViewById(R.id.swipe);


       adapter = new ListAdapter(activity , titles , images , urls ,content, this);
       listView.setAdapter(adapter);
        listView.setLayoutManager(new LinearLayoutManager(activity));
        AD_COUNT = 0;
        mInterstitialAd = new InterstitialAd(activity);
        ///Ad id
        mInterstitialAd.setAdUnitId("ca-app-pub-6129503049169576/8231262552");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());


    }

    @Override
    protected void onPreExecute() {
        //before works
    }
    @Override
    protected String  doInBackground(String... params) {
        // TODO Auto-generated method stub



        for (int i=0 ;i<params.length;i++) {

        try {
            String NewsData;
            //define the url we have to connect with
                URL url = new URL(params[i]);
                //make connect with url and send request
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                //waiting for 7000ms for response
                urlConnection.setConnectTimeout(10000);//set timeout to 10 seconds
            //  urlConnection.connect();
            urlConnection.addRequestProperty("User-Agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
          // urlConnection.



                try {
                    //getting the response data

                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                 //   Log.e("err",""+in);

                    //convert the stream to string
                    NewsData = ConvertInputToStringNoChange(in);


                    //send to display data
                    publishProgress(NewsData);

                } finally {

                   // Log.e("url" , "out1");
                    //end connection
                    urlConnection.disconnect();
                }

        }catch (Exception ex){
            ex.printStackTrace();
       //     Log.e("url" , "out2");
        }}
        return null;
    }
    protected void onProgressUpdate(String... progress) {


        try {
            //display response data
              //Toast.makeText(context,progress[0],Toast.LENGTH_LONG).show();
            JSONObject jsonObject = new JSONObject(progress[0]);
            JSONArray articles = jsonObject.getJSONArray("articles");
            for (int i=0 ;i<articles.length();i++) {
                JSONObject article = articles.getJSONObject(i);
                String title = article.getString("title");
                String urlToImg = article.getString("urlToImage");
               String url  =article.getString("url");
               String cont = article.getString("description");


               if(!urlToImg.equalsIgnoreCase("null")  &&!title.equalsIgnoreCase("null")){
                titles.add(title);
                images.add(urlToImg);
               urls.add(url);
               content.add(cont);
             //  Log.e("iss",""+urlToImg);
               }

            }


           // listView.setAdapter;
            adapter.notifyDataSetChanged();





           // progressDialog.dismiss();


        } catch (Exception ex) {
        }

    }

    protected void onPostExecute(String  result2){
     // Toast.makeText(activity, "Done "+titles.size(), Toast.LENGTH_SHORT).show();
        //textView.setText(result2);

       // swipeRefreshLayout.setRefreshing(false);
        swipeRefreshLayout.setRefreshing(false);
        //Toast.makeText(activity, "size : "+titles.size(), Toast.LENGTH_SHORT).show();
    }
    public static String ConvertInputToStringNoChange(InputStream inputStream) {

        BufferedReader bureader=new BufferedReader( new InputStreamReader(inputStream));
        String line ;
        String linereultcal="";

        try{
            while((line=bureader.readLine())!=null) {

                linereultcal+=line;

            }
            inputStream.close();


        }catch (Exception ex){}

        return linereultcal;
    }


    @Override
    public void onArticleClickListner(int position) {
        AD_COUNT++;
        final Intent intent = new Intent(activity , ArticleViewActivity.class);
        intent.putExtra("articleContent",content.get(position));
        intent.putExtra("url",urls.get(position));
        intent.putExtra("imgURL",images.get(position));
        intent.putExtra("title",titles.get(position));


        if(AD_COUNT == 3 && mInterstitialAd.isLoaded()){
            AD_COUNT = 0;
                mInterstitialAd.show();

            mInterstitialAd.setAdListener(new AdListener() {

                @Override

                public void onAdClosed() {
                    mInterstitialAd.loadAd(new AdRequest.Builder().build());
                    activity.startActivity(intent);

                }

            });



        }
        else {
            activity.startActivity(intent);
            if(AD_COUNT>=3)
                AD_COUNT = 0;

        }




    }





}

