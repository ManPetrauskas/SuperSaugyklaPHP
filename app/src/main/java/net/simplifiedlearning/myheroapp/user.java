package net.simplifiedlearning.myheroapp;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.view.View.GONE;

public class user extends AppCompatActivity {

    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;
    private static final int CODE_GET_QUERY = 1026;

    Button startTime;

    Thread thread;

    Date firstTimeStamp;
    Date secondTimeStamp;

    long timeDiff;

    private DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    TextView date1Variable;
    TextView date2Variable;
    TextView timeTextVariable;

    String atsList;
    String userToken;

    boolean inProgress = false;
    boolean timerRuning = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        this.date1Variable = findViewById(R.id.date1Text);
        userToken = MainActivity.userToken;
        this.date2Variable = findViewById(R.id.date2Text);
        this.timeTextVariable = findViewById(R.id.timeText);
        startTime=findViewById(R.id.startButton);
        //===========tikrinama ar pradetas buvo===========
        inProgress=true;
        HashMap<String, String> params = new HashMap<>();
        params.put("login_token", userToken);

        user.PerformNetworkRequestCheck1 request = new user.PerformNetworkRequestCheck1(Api.URL_GET_WORKERBOOLEAN + userToken, params, CODE_POST_REQUEST);
        request.execute();
        //================================================
        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!inProgress){
                    if(!timerRuning){
                        startTimer();

                    }
                    else{
                        stopTimer();
                    }
                }
            }
        });

        this.thread = new Thread() {

            @Override
            public void run() {
                try {
                    while (!thread.isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (timerRuning) {
                                    RefreshTime();
                                }
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };

        thread.start();

    }
    private void startTimer() {
        this.startTime.setText("Stop Timer");
        timerRuning = true;
        //===============Timerio pradejimas bazeje ir boolean pakeisti=====
        inProgress=true;
        beginTimer();
        //=======================================================================
        this.firstTimeStamp = new Date();
        this.date1Variable.setText(dateFormat.format(this.firstTimeStamp));
        RefreshTime();
    }
    private void RefreshTime() {
        this.secondTimeStamp = new Date();
        this.date2Variable.setText(dateFormat.format(this.secondTimeStamp));
        this.timeDiff = this.secondTimeStamp.getTime() - this.firstTimeStamp.getTime();
        long diffSeconds = this.timeDiff / 1000 % 60;
        long diffMinutes = this.timeDiff / (60 * 1000) % 60;
        long diffHours = this.timeDiff / (60 * 60 * 1000);
        this.timeTextVariable.setText("You are " + diffHours + " h " + diffMinutes + " min " + diffSeconds + " sec  working");
    }
    private void stopTimer(){
        //===============Bazeje timerio uzbaigimas ir total hours suskaiciavimas=======================
        inProgress=true;
        endTimer();
        //============================================================================================
        date1Variable.setText("Starting date");
        timeTextVariable.setText("Time passed");
        startTime.setText("Start Timer");
        timerRuning = false;
    }
    private void beginTimerNotFresh(){
        this.startTime.setText("Stop Timer");
        firstTimeStamp = new Date();
        long what=firstTimeStamp.getTime();
        long nowTime = what - this.timeDiff *1000;
        firstTimeStamp.setTime(nowTime);
        date1Variable.setText(dateFormat.format(this.firstTimeStamp));
        timerRuning=true;
        RefreshTime();
    }

    private void beginTimer(){
        HashMap<String, String> params = new HashMap<>();
        params.put("login_token", userToken);
        user.PerformNetworkRequestBegining1 todayshours = new user.PerformNetworkRequestBegining1(Api.URL_CHANGE_LASTTIMESTARTED, params, CODE_POST_REQUEST);
        todayshours.execute();
    }
    private void endTimer(){
        HashMap<String, String> params = new HashMap<>();
        params.put("login_token", userToken);
        user.PerformNetworkRequestEnd1 todayshours = new user.PerformNetworkRequestEnd1(Api.URL_CHANGE_LASTTIMEENDED, params, CODE_POST_REQUEST);
        todayshours.execute();
    }

//================================Custom tasks============================
private class PerformNetworkRequestCheck1 extends AsyncTask<Void, Void, String> {
    String url;
    HashMap<String, String> params;
    int requestCode;

    PerformNetworkRequestCheck1(String url, HashMap<String, String> params, int requestCode) {
        this.url = url;
        this.params = params;
        this.requestCode = requestCode;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
      //  progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        atsList = "";
        Matcher m = Pattern.compile("[0-9]").matcher(s);
        while (m.find()) {
            atsList+=(m.group());
        }
        if(atsList.equals("1")){
            //===========================================
            //update time 2                         Done
            //update time passed                    Done
            //get time passed                       Done
            //calculate all times                   Done
            //refresh                               Done
            HashMap<String, String> params = new HashMap<>();
            params.put("login_token", userToken);
            user.PerformNetworkRequestCheck2 todayshours = new user.PerformNetworkRequestCheck2(Api.URL_CHANGE_LASTTIMEENDED, params, CODE_POST_REQUEST);
            todayshours.execute();
        }
        else{
            inProgress =false;
        }

    }

    @Override
    protected String doInBackground(Void... voids) {
        RequestHandler requestHandler = new RequestHandler();

        if (requestCode == CODE_POST_REQUEST)
            return requestHandler.sendPostRequest(url, params);


        if (requestCode == CODE_GET_REQUEST)
            return requestHandler.sendGetRequest(url);

        if (requestCode == CODE_GET_QUERY)
            return requestHandler.sendUpdateRequest(url);

        return null;
    }
}
private class PerformNetworkRequestCheck2 extends AsyncTask<Void, Void, String> {
    String url;
    HashMap<String, String> params;
    int requestCode;

    PerformNetworkRequestCheck2(String url, HashMap<String, String> params, int requestCode) {
        this.url = url;
        this.params = params;
        this.requestCode = requestCode;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
       // progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        PerformNetworkRequestCheck3 updatehours = new PerformNetworkRequestCheck3(Api.URL_UPDATE_TODAYSWORKTIME, null, CODE_GET_QUERY);
        updatehours.execute();
    }

    @Override
    protected String doInBackground(Void... voids) {
        RequestHandler requestHandler = new RequestHandler();

        if (requestCode == CODE_POST_REQUEST)
            return requestHandler.sendPostRequest(url, params);


        if (requestCode == CODE_GET_REQUEST)
            return requestHandler.sendGetRequest(url);

        if (requestCode == CODE_GET_QUERY)
            return requestHandler.sendUpdateRequest(url);

        return null;
    }
}
private class PerformNetworkRequestCheck3 extends AsyncTask<Void, Void, String> {
    String url;
    HashMap<String, String> params;
    int requestCode;

    PerformNetworkRequestCheck3(String url, HashMap<String, String> params, int requestCode) {
        this.url = url;
        this.params = params;
        this.requestCode = requestCode;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
       // progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        HashMap<String, String> params = new HashMap<>();
        params.put("login_token", userToken);
        user.PerformNetworkRequestCheck4 todayshours = new user.PerformNetworkRequestCheck4(Api.URL_GET_TODAYSWORKTIME, params, CODE_POST_REQUEST);
        todayshours.execute();

    }

    @Override
    protected String doInBackground(Void... voids) {
        RequestHandler requestHandler = new RequestHandler();

        if (requestCode == CODE_POST_REQUEST)
            return requestHandler.sendPostRequest(url, params);


        if (requestCode == CODE_GET_REQUEST)
            return requestHandler.sendGetRequest(url);

        if (requestCode == CODE_GET_QUERY)
            return requestHandler.sendUpdateRequest(url);

        return null;
    }
}
private class PerformNetworkRequestCheck4 extends AsyncTask<Void, Void, String> {
    String url;
    HashMap<String, String> params;
    int requestCode;

    PerformNetworkRequestCheck4(String url, HashMap<String, String> params, int requestCode) {
        this.url = url;
        this.params = params;
        this.requestCode = requestCode;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
       // progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        atsList = "";
        Matcher m = Pattern.compile("[0-9]").matcher(s);
        while (m.find()) {
            atsList+=(m.group());
        }
        timeDiff= Long.parseLong(atsList);
        inProgress=false;
        beginTimerNotFresh();

    }

    @Override
    protected String doInBackground(Void... voids) {
        RequestHandler requestHandler = new RequestHandler();

        if (requestCode == CODE_POST_REQUEST)
            return requestHandler.sendPostRequest(url, params);


        if (requestCode == CODE_GET_REQUEST)
            return requestHandler.sendGetRequest(url);

        if (requestCode == CODE_GET_QUERY)
            return requestHandler.sendUpdateRequest(url);

        return null;
    }
}


private class PerformNetworkRequestBegining1 extends AsyncTask<Void, Void, String> {
    String url;
    HashMap<String, String> params;
    int requestCode;

    PerformNetworkRequestBegining1(String url, HashMap<String, String> params, int requestCode) {
        this.url = url;
        this.params = params;
        this.requestCode = requestCode;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
//        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        HashMap<String, String> params = new HashMap<>();
        params.put("login_token", userToken);

        user.PerformNetworkRequestBegining2 request = new user.PerformNetworkRequestBegining2(Api.URL_CHANGE_BOOLEANTOTRUE, params, CODE_POST_REQUEST);
        request.execute();
    }

    @Override
    protected String doInBackground(Void... voids) {
        RequestHandler requestHandler = new RequestHandler();

        if (requestCode == CODE_POST_REQUEST)
            return requestHandler.sendPostRequest(url, params);


        if (requestCode == CODE_GET_REQUEST)
            return requestHandler.sendGetRequest(url);

        if (requestCode == CODE_GET_QUERY)
            return requestHandler.sendUpdateRequest(url);

        return null;
    }
}
private class PerformNetworkRequestBegining2 extends AsyncTask<Void, Void, String> {
    String url;
    HashMap<String, String> params;
    int requestCode;

    PerformNetworkRequestBegining2(String url, HashMap<String, String> params, int requestCode) {
        this.url = url;
        this.params = params;
        this.requestCode = requestCode;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        inProgress = false;
    }

    @Override
    protected String doInBackground(Void... voids) {
        RequestHandler requestHandler = new RequestHandler();

        if (requestCode == CODE_POST_REQUEST)
            return requestHandler.sendPostRequest(url, params);


        if (requestCode == CODE_GET_REQUEST)
            return requestHandler.sendGetRequest(url);

        if (requestCode == CODE_GET_QUERY)
            return requestHandler.sendUpdateRequest(url);

        return null;
    }
}

private class PerformNetworkRequestEnd1 extends AsyncTask<Void, Void, String> {
    String url;
    HashMap<String, String> params;
    int requestCode;

    PerformNetworkRequestEnd1(String url, HashMap<String, String> params, int requestCode) {
        this.url = url;
        this.params = params;
        this.requestCode = requestCode;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
//            progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        HashMap<String, String> params = new HashMap<>();
        params.put("login_token", userToken);

        user.PerformNetworkRequestEnd2 request = new user.PerformNetworkRequestEnd2(Api.URL_CHANGE_BOOLEANTOFALSE, params, CODE_POST_REQUEST);
        request.execute();
    }

    @Override
    protected String doInBackground(Void... voids) {
        RequestHandler requestHandler = new RequestHandler();

        if (requestCode == CODE_POST_REQUEST)
            return requestHandler.sendPostRequest(url, params);


        if (requestCode == CODE_GET_REQUEST)
            return requestHandler.sendGetRequest(url);

        if (requestCode == CODE_GET_QUERY)
            return requestHandler.sendUpdateRequest(url);

        return null;
    }
}
    private class PerformNetworkRequestEnd2 extends AsyncTask<Void, Void, String> {
        String url;
        HashMap<String, String> params;
        int requestCode;

        PerformNetworkRequestEnd2(String url, HashMap<String, String> params, int requestCode) {
            this.url = url;
            this.params = params;
            this.requestCode = requestCode;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            PerformNetworkRequestEnd3 updatehours = new PerformNetworkRequestEnd3(Api.URL_UPDATE_TODAYSWORKTIME, null, CODE_GET_QUERY);
            updatehours.execute();

        }

        @Override
        protected String doInBackground(Void... voids) {
            RequestHandler requestHandler = new RequestHandler();

            if (requestCode == CODE_POST_REQUEST)
                return requestHandler.sendPostRequest(url, params);


            if (requestCode == CODE_GET_REQUEST)
                return requestHandler.sendGetRequest(url);

            if (requestCode == CODE_GET_QUERY)
                return requestHandler.sendUpdateRequest(url);

            return null;
        }
    }
    private class PerformNetworkRequestEnd3 extends AsyncTask<Void, Void, String> {
        String url;
        HashMap<String, String> params;
        int requestCode;

        PerformNetworkRequestEnd3(String url, HashMap<String, String> params, int requestCode) {
            this.url = url;
            this.params = params;
            this.requestCode = requestCode;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            HashMap<String, String> params = new HashMap<>();
            params.put("login_token", userToken);
            user.PerformNetworkRequestEnd4 todayshours = new user.PerformNetworkRequestEnd4(Api.URL_COPY_WORKER, params, CODE_POST_REQUEST);
            todayshours.execute();

        }

        @Override
        protected String doInBackground(Void... voids) {
            RequestHandler requestHandler = new RequestHandler();

            if (requestCode == CODE_POST_REQUEST)
                return requestHandler.sendPostRequest(url, params);


            if (requestCode == CODE_GET_REQUEST)
                return requestHandler.sendGetRequest(url);

            if (requestCode == CODE_GET_QUERY)
                return requestHandler.sendUpdateRequest(url);

            return null;
        }
    }
    private class PerformNetworkRequestEnd4 extends AsyncTask<Void, Void, String> {
        String url;
        HashMap<String, String> params;
        int requestCode;

        PerformNetworkRequestEnd4(String url, HashMap<String, String> params, int requestCode) {
            this.url = url;
            this.params = params;
            this.requestCode = requestCode;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            inProgress=false;

        }

        @Override
        protected String doInBackground(Void... voids) {
            RequestHandler requestHandler = new RequestHandler();

            if (requestCode == CODE_POST_REQUEST)
                return requestHandler.sendPostRequest(url, params);


            if (requestCode == CODE_GET_REQUEST)
                return requestHandler.sendGetRequest(url);

            if (requestCode == CODE_GET_QUERY)
                return requestHandler.sendUpdateRequest(url);

            return null;
        }
    }

}
