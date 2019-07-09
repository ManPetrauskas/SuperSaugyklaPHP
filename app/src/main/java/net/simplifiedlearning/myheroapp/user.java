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

import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.view.View.GONE;

public class user extends AppCompatActivity {

    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;
    private static final int CODE_GET_QUERY = 1026;

    EditText  editTextName;
    //editTextHeroId,, editTextRealname
    RatingBar ratingBar;
    Spinner spinnerTeam;
    ProgressBar progressBar;
    ListView listView;
    Button startTime;

    List<Hero> heroList;
    String atsList;
    String userToken;
    boolean inProgress = false;
    boolean isUpdating = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        startTime=findViewById(R.id.startButton);
        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
            }
        });

    }
    private void changeLastTimeStarted(){
        HashMap<String, String> params = new HashMap<>();
        params.put("login_token", userToken);
        user.PerformNetworkRequestClone todayshours = new user.PerformNetworkRequestClone(Api.URL_CHANGE_LASTTIMESTARTED, params, CODE_POST_REQUEST);
        todayshours.execute();
    }

    private void changeLastTimeEnded(){
        HashMap<String, String> params = new HashMap<>();
        params.put("login_token", userToken);
        user.PerformNetworkRequestClone todayshours = new user.PerformNetworkRequestClone(Api.URL_CHANGE_LASTTIMEENDED, params, CODE_POST_REQUEST);
        todayshours.execute();
    }

    private void readWorkers() {
        user.PerformNetworkRequestClone request = new user.PerformNetworkRequestClone(Api.URL_READ_WORKERS, null, CODE_GET_QUERY);
        request.execute();
    }

    private void getTodaysHours(){
//       PerformNetworkRequestClone updatehours = new PerformNetworkRequestClone(Api.URL_UPDATE_TODAYSWORKTIME, null, CODE_GET_QUERY);
//       updatehours.execute();


        HashMap<String, String> params = new HashMap<>();
        params.put("login_token", userToken);
        user.PerformNetworkRequestClone todayshours = new user.PerformNetworkRequestClone(Api.URL_GET_TODAYSWORKTIME, params, CODE_POST_REQUEST);
        todayshours.execute();
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
//        user.PerformNetworkRequest request = new user.PerformNetworkRequest(Api.URL_UPDATE_HERO, params, CODE_POST_REQUEST);
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
        user.PerformNetworkRequest request = new user.PerformNetworkRequest(Api.URL_DELETE_HERO + id, null, CODE_GET_REQUEST);
        request.execute();
    }

    private void changeBooleanToTrue(){
        HashMap<String, String> params = new HashMap<>();
        params.put("login_token", userToken);

        user.PerformNetworkRequestClone request = new user.PerformNetworkRequestClone(Api.URL_CHANGE_BOOLEANTOTRUE, params, CODE_POST_REQUEST);
        request.execute();
    }

    private void changeBooleanToFalse(){
        HashMap<String, String> params = new HashMap<>();
        params.put("login_token", userToken);

        user.PerformNetworkRequestClone request = new user.PerformNetworkRequestClone(Api.URL_CHANGE_BOOLEANTOFALSE, params, CODE_POST_REQUEST);
        request.execute();
    }

    private void getWorkerBoolean(){          //veikia
        HashMap<String, String> params = new HashMap<>();
        params.put("login_token", userToken);

        user.PerformNetworkRequestClone request = new user.PerformNetworkRequestClone(Api.URL_GET_WORKERBOOLEAN + userToken, params, CODE_POST_REQUEST);
        request.execute();
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

        user.HeroAdapter adapter = new user.HeroAdapter(heroList);
        listView.setAdapter(adapter);
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
            super(user.this, R.layout.layout_hero_list, heroList);
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
                    startTime.setText("Update");
                }
            });

            textViewDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(user.this);

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
