package com.news.akhbar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.widget.Toast;

import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;
import com.google.android.play.core.tasks.Task;

import java.util.ArrayList;
import java.util.Locale;

public class CommonTasks {

    private static boolean isFva = false;
    public static void addFavourite(Context context , String title , String img , String url,String content)
    {
        TinyDB tinyDB = new TinyDB(context);

        ArrayList<String> ar1;
        ArrayList<String> ar2;
        ArrayList<String> ar3;
        ArrayList<String> ar4;

        ar1 = tinyDB.getListString("titles");
        ar2 = tinyDB.getListString("images");
        ar3 = tinyDB.getListString("url");
        ar4 = tinyDB.getListString("content");

         isFva = false;
        for (int i=0;i<ar1.size();i++){
       String t = ar1.get(i);
        if(t.equalsIgnoreCase(title)) {
            isFva = true;
            break;
        }
        }

        // for (int i=0 ;i<)
       // final boolean isFva = tinyDB.getString()
        if(!isFva){
        ar1.add(title);
        ar2.add(img);
        ar3.add(url);
        ar4.add(content);
        tinyDB.putListString("titles" , ar1);
        tinyDB.putListString("images" , ar2);
        tinyDB.putListString("url" , ar3);
            tinyDB.putListString("content" , ar4);


            Toast.makeText(context,  R.string.addFav, Toast.LENGTH_SHORT).show();}

 }

    public static void share(final Context  context  , final String content)
    {


                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = content;
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,  R.string.share_subject+" : "+content);
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                context.startActivity(Intent.createChooser(sharingIntent, "Share via"));


    }
    public static void contact(final Context  context )
    {

        final  String devolper = "omarsaad34@gmail.com";
        final  String subject =context.getResources().getString(R.string.email_subject);


        Intent email = new Intent(Intent.ACTION_SEND);
        email.putExtra(Intent.EXTRA_EMAIL, new String[]{ devolper});
        email.putExtra(Intent.EXTRA_SUBJECT, subject);
       // email.putExtra(Intent.EXTRA_TEXT, R.string.contact);

//need this to prompts email client only
        email.setType("message/rfc822");

        context.startActivity(Intent.createChooser(email, "Choose an Email client :"));
    }

    public static void changeLanguage(final Context  context , String[] lang , String[] country )
    {


        Locale locale = new Locale(lang[0] , country[0]);

        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        context.getResources().updateConfiguration(config,
                context.getResources().getDisplayMetrics());
      //  Activity activity = getActivity(context);
      //  activity.startActivity(activity.getIntent());
      //  activity.finish();


    }

    public static void inAppReview(Activity activity){

        ReviewManager manager = ReviewManagerFactory.create(activity);
        Task<ReviewInfo> request = manager.requestReviewFlow();
        request.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // We can get the ReviewInfo object
                ReviewInfo reviewInfo = task.getResult();
                Task<Void> flow = manager.launchReviewFlow(activity, reviewInfo);
                flow.addOnCompleteListener(task1 -> {
                    // The flow has finished. The API does not indicate whether the user
                    // reviewed or not, or even whether the review dialog was shown. Thus, no
                    // matter the result, we continue our app flow.
                });
            } else {
                // There was some problem, continue regardless of the result.
            }
        });

    }



    public static boolean isFva(Context context , String title)
    {
        TinyDB tinyDB = new TinyDB(context);

        ArrayList<String> ar1;


        ar1 = tinyDB.getListString("titles");


        isFva = false;
        for (int i=0;i<ar1.size();i++){
            String t = ar1.get(i);
            if(t.equalsIgnoreCase(title)) {
                isFva = true;
                break;
            }
        }
     return isFva;
    }

    public static void removeFavourite(Context context , String title , String img , String url,String content){
        TinyDB tinyDB = new TinyDB(context);

        ArrayList<String> ar1;
        ArrayList<String> ar2;
        ArrayList<String> ar3;
        ArrayList<String> ar4;

        ar1 = tinyDB.getListString("titles");
        ar2 = tinyDB.getListString("images");
        ar3 = tinyDB.getListString("url");
        ar4 = tinyDB.getListString("content");

        for (int i=0 ;i<ar1.size();i++){
            if (ar1.get(i).equalsIgnoreCase(title)){
                ar1.remove(i);
                ar2.remove(i);
                ar3.remove(i);
                ar4.remove(i);
            }
        }

        tinyDB.putListString("titles" , ar1);
        tinyDB.putListString("images" , ar2);
        tinyDB.putListString("url" , ar3);
        tinyDB.putListString("content" , ar4);


        Toast.makeText(context,  R.string.remove_fav, Toast.LENGTH_SHORT).show();
    }


}
