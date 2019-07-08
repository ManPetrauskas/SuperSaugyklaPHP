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
    //editTextHeroId,, editTextRealname
    RatingBar ratingBar;
    Spinner spinnerTeam;
    ProgressBar progressBar;
    ListView listView;
    Button buttonAddUpdate;

    List<Hero> heroList;
    String atsList;
    public static String userToken;

    boolean isUpdating = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //editTextHeroId = (EditText) findViewById(R.id.editTextHeroId);
        editTextName = (EditText) findViewById(R.id.editTextName);
//        editTextRealname = (EditText) findViewById(R.id.editTextRealname);
//        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
//        spinnerTeam = (Spinner) findViewById(R.id.spinnerTeamAffiliation);
        atsList = "";
        buttonAddUpdate = (Button) findViewById(R.id.buttonAddUpdate);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        listView = (ListView) findViewById(R.id.listViewHeroes);

        heroList = new ArrayList<>();


        buttonAddUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userToken = editTextName.getText().toString();
//                if (isUpdating) {
//                    updateHero();
//                } else {
//                    createHero();
//                }
                //getWorkerBoolean();
                //changeBooleanToFalse();
                //changeBooleanToTrue();
                //changeLastTimeStarted();
                //changeLastTimeEnded();


//                // ============ To open user=============(comment everything below before testing any other method)
//                String login_token = editTextName.getText().toString().trim();
//                HashMap<String, String> params = new HashMap<>();
//                params.put("login_token", login_token);
//
//                PerformNetworkRequestToOpenUser request = new PerformNetworkRequestToOpenUser(Api.URL_GET_WORKERBOOLEAN + login_token, params, CODE_POST_REQUEST);
//                request.execute();
//                //=========================================
                getTodaysWorktime();
            }
        });
        //readHeroes();
    }


//    private void createHero() {
//        String name = editTextName.getText().toString().trim();
//        String realname = editTextRealname.getText().toString().trim();
//
//        int rating = (int) ratingBar.getRating();
//
//        String team = spinnerTeam.getSelectedItem().toString();
//
//        if (TextUtils.isEmpty(name)) {
//            editTextName.setError("Please enter name");
//            editTextName.requestFocus();
//            return;
//        }
//
//        if (TextUtils.isEmpty(realname)) {
//            editTextRealname.setError("Please enter real name");
//            editTextRealname.requestFocus();
//            return;
//        }
//
//        HashMap<String, String> params = new HashMap<>();
//        params.put("name", name);
//        params.put("realname", realname);
//        params.put("rating", String.valueOf(rating));
//        params.put("teamaffiliation", team);
//
//        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_CREATE_HERO, params, CODE_POST_REQUEST);
//        request.execute();
//    }

    private void changeLastTimeStarted(){
        //String id = editTextName.getText().toString();

        HashMap<String, String> params = new HashMap<>();
        params.put("login_token", userToken);
        PerformNetworkRequestClone todayshours = new PerformNetworkRequestClone(Api.URL_CHANGE_LASTTIMESTARTED, params, CODE_POST_REQUEST);
        todayshours.execute();
    }

    private void changeLastTimeEnded(){
        //String id = editTextName.getText().toString();

        HashMap<String, String> params = new HashMap<>();
        params.put("login_token", userToken);
        PerformNetworkRequestClone todayshours = new PerformNetworkRequestClone(Api.URL_CHANGE_LASTTIMEENDED, params, CODE_POST_REQUEST);
        todayshours.execute();
    }

    private void readWorkers() {
        PerformNetworkRequestClone request = new PerformNetworkRequestClone(Api.URL_READ_WORKERS, null, CODE_GET_QUERY);
        request.execute();
    }

    private void getTodaysWorktime(){
       PerformNetworkRequestClone updatehours = new PerformNetworkRequestClone(Api.URL_UPDATE_TODAYSWORKTIME, null, CODE_GET_QUERY);
       updatehours.execute();

//        String id = editTextName.getText().toString();
//
//        HashMap<String, String> params = new HashMap<>();
//        params.put("login_token", id);
//        PerformNetworkRequestClone todayshours = new PerformNetworkRequestClone(Api.URL_GET_TODAYSWORKTIME, params, CODE_POST_REQUEST);
//        todayshours.execute();
    }

//    private void updateHero() {
//        String id = editTextHeroId.getText().toString();
//        String name = editTextName.getText().toString().trim();
//        String realname = editTextRealname.getText().toString().trim();
//
//        int rating = (int) ratingBar.getRating();
//
//        String team = spinnerTeam.getSelectedItem().toString();
//
//
//        if (TextUtils.isEmpty(name)) {
//            editTextName.setError("Please enter name");
//            editTextName.requestFocus();
//            return;
//        }
//
//        if (TextUtils.isEmpty(realname)) {
//            editTextRealname.setError("Please enter real name");
//            editTextRealname.requestFocus();
//            return;
//        }
//
//        HashMap<String, String> params = new HashMap<>();
//        params.put("id", id);
//        params.put("name", name);
//        params.put("realname", realname);
//        params.put("rating", String.valueOf(rating));
//        params.put("teamaffiliation", team);
//
//
//        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_UPDATE_HERO, params, CODE_POST_REQUEST);
//        request.execute();
//
//        buttonAddUpdate.setText("Add");
//
//        editTextName.setText("");
//        editTextRealname.setText("");
//        ratingBar.setRating(0);
//        spinnerTeam.setSelection(0);
//
//        isUpdating = false;
//    }

    private void deleteHero(int id) {
        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_DELETE_HERO + id, null, CODE_GET_REQUEST);
        request.execute();
    }

    private void changeBooleanToTrue(){
        String login_token = editTextName.getText().toString().trim();
        HashMap<String, String> params = new HashMap<>();
        params.put("login_token", login_token);

        PerformNetworkRequestClone request = new PerformNetworkRequestClone(Api.URL_CHANGE_BOOLEANTOTRUE, params, CODE_POST_REQUEST);
        request.execute();
    }

    private void changeBooleanToFalse(){
        String login_token = editTextName.getText().toString().trim();
        HashMap<String, String> params = new HashMap<>();
        params.put("login_token", login_token);

        PerformNetworkRequestClone request = new PerformNetworkRequestClone(Api.URL_CHANGE_BOOLEANTOFALSE, params, CODE_POST_REQUEST);
        request.execute();
    }

    private void getWorkerBoolean(){          //veikia
        String login_token = editTextName.getText().toString().trim();
        HashMap<String, String> params = new HashMap<>();
        params.put("login_token", login_token);

        PerformNetworkRequestClone request = new PerformNetworkRequestClone(Api.URL_GET_WORKERBOOLEAN + login_token, params, CODE_POST_REQUEST);
        request.execute();
    }
    private void launchUser(){
        Intent intent = new Intent(this, user.class);
        startActivity(intent);
    }



    private void refreshHeroList(JSONArray heroes) throws JSONException {
        heroList.clear();

        for (int i = 0; i < heroes.length(); i++) {
            JSONObject obj = heroes.getJSONObject(i);

            heroList.add(new Hero(
                    obj.getInt("id"),
                    obj.getString("name"),
                    obj.getString("realname"),
                    obj.getInt("rating"),
                    obj.getString("teamaffiliation")
            ));
        }

        HeroAdapter adapter = new HeroAdapter(heroList);
        listView.setAdapter(adapter);
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

    private class PerformNetworkRequestClone extends AsyncTask<Void, Void, String> {
        String url;
        HashMap<String, String> params;
        int requestCode;

        PerformNetworkRequestClone(String url, HashMap<String, String> params, int requestCode) {
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
            Matcher m = Pattern.compile("[0-9]").matcher(s);
            while (m.find()) {
                atsList+=(m.group());
            }
            System.out.println(atsList);
            progressBar.setVisibility(GONE);
            try {
                JSONObject object = new JSONObject(s);
                if (!object.getBoolean("error")) {
                    Toast.makeText(getApplicationContext(), object.getString("message"), Toast.LENGTH_SHORT).show();
                    refreshHeroList(object.getJSONArray("heroes"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
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

    private class PerformNetworkRequest extends AsyncTask<Void, Void, String> {
        String url;
        HashMap<String, String> params;
        int requestCode;

        PerformNetworkRequest(String url, HashMap<String, String> params, int requestCode) {
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
            progressBar.setVisibility(GONE);
            try {
                JSONObject object = new JSONObject(s);
                if (!object.getBoolean("error")) {
                    Toast.makeText(getApplicationContext(), object.getString("message"), Toast.LENGTH_SHORT).show();
                    refreshHeroList(object.getJSONArray("heroes"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(Void... voids) {
            RequestHandler requestHandler = new RequestHandler();

            if (requestCode == CODE_POST_REQUEST)
                return requestHandler.sendPostRequest(url, params);


            if (requestCode == CODE_GET_REQUEST)
                return requestHandler.sendGetRequest(url);

            return null;
        }
    }

    class HeroAdapter extends ArrayAdapter<Hero> {
        List<Hero> heroList;

        public HeroAdapter(List<Hero> heroList) {
            super(MainActivity.this, R.layout.layout_hero_list, heroList);
            this.heroList = heroList;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View listViewItem = inflater.inflate(R.layout.layout_hero_list, null, true);

            TextView textViewName = listViewItem.findViewById(R.id.textViewName);

            TextView textViewUpdate = listViewItem.findViewById(R.id.textViewUpdate);
            TextView textViewDelete = listViewItem.findViewById(R.id.textViewDelete);

            final Hero hero = heroList.get(position);

            textViewName.setText(hero.getName());

            textViewUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    isUpdating = true;
//                    editTextHeroId.setText(String.valueOf(hero.getId()));
                    editTextName.setText(hero.getName());
//                    editTextRealname.setText(hero.getRealname());
                    ratingBar.setRating(hero.getRating());
                    spinnerTeam.setSelection(((ArrayAdapter<String>) spinnerTeam.getAdapter()).getPosition(hero.getTeamaffiliation()));
                    buttonAddUpdate.setText("Update");
                }
            });

            textViewDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                    builder.setTitle("Delete " + hero.getName())
                            .setMessage("Are you sure you want to delete it?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    deleteHero(hero.getId());
                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();

                }
            });

            return listViewItem;
        }
    }
}
