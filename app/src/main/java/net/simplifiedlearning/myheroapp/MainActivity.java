package net.simplifiedlearning.myheroapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.view.View.GONE;

public class MainActivity extends AppCompatActivity {

    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;
    private static final int CODE_GET_QUERY = 1026;

    EditText  editTextName;

    ProgressBar progressBar;
    ListView listView;
    Button buttonAddUpdate;

    String atsList;
    public static String userToken;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextName = (EditText) findViewById(R.id.editTextName);
        atsList = "";
        buttonAddUpdate = (Button) findViewById(R.id.buttonAddUpdate);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        listView = (ListView) findViewById(R.id.listViewHeroes);



        buttonAddUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userToken = editTextName.getText().toString();
                // ============ To open user=============(comment everything below before testing any other method)
                String login_token = editTextName.getText().toString().trim();
                HashMap<String, String> params = new HashMap<>();
                params.put("login_token", login_token);

                PerformNetworkRequestToOpenUser request = new PerformNetworkRequestToOpenUser(Api.URL_GET_WORKERBOOLEAN + login_token, params, CODE_POST_REQUEST);
                request.execute();
//                //=========================================


            }
        });
    }

    private void launchUser(){
        Intent intent = new Intent(this, user.class);
        startActivity(intent);
    }
    private class PerformNetworkRequestToOpenUser extends AsyncTask<Void, Void, String> {
        String url;
        HashMap<String, String> params;
        int requestCode;

        PerformNetworkRequestToOpenUser(String url, HashMap<String, String> params, int requestCode) {
            this.url = url;
            this.params = params;
            this.requestCode = requestCode;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            atsList = "";
            Matcher m = Pattern.compile("[0-9]").matcher(s);
            while (m.find()) {
                atsList+=(m.group());
            }
            System.out.println(atsList);
            if(atsList.equals("0") || atsList.equals("1")){
                launchUser();
            }
            else{
                System.out.println(atsList+" is not 0 or 1... Such token doesnt exist ?");
            }
            progressBar.setVisibility(View.INVISIBLE);
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
