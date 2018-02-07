package sports.mgl.mgl.BussinessLayer;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import sports.mgl.mgl.Util.JSONParser;


public class Controller {
    private ArrayList<GameName> gameNames;
    private ProgressDialog pDialog;
    JSONArray products = null;
    private Controller controller;
    JSONParser jParser = new JSONParser();
    private static String url_all_gameName = "https://mastersgamingleague.000webhostapp.com/get_game_names.php";
    private static String url_all_matches = "https://mastersgamingleague.000webhostapp.com/get_game_schedule.php";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_GAME_NAME = "gameNames";
    private static final String TAG_MATCH = "schedules";
    private static final String TAG_GID = "id";
    private static final String TAG_NAME = "name";
    private static final String TAG_IMG = "imgId";
    private static final String TAG_MID = "id";
    private static final String TAG_M_GID = "c_id";
    private static final String TAG_PLAYER1 = "user1";
    private static final String TAG_PLAYER2 = "user2";
    private static final String TAG_WINNER = "winner";
    private static final String TAG_DATE = "time";
    private static Controller ourInstance = new Controller();
    private Context context;
    private ArrayList<Schedule> matches;

    public static Controller getInstance() {
        return ourInstance;
    }
    public void setContext(Context c){
        context=c;
    }
    public void loadData(){
        matches.clear();
        gameNames.clear();
        new LoadAllGameName().execute();
    }

    private Controller() {
        matches=new ArrayList<>();
        gameNames =new ArrayList<>();
    }

    public void addGameName(String id, String name, String imgId) {
        gameNames.add(new GameName(id,name,imgId));
    }

    public void getGameName(ArrayList<String> items, ArrayList<Integer> itemIds) {
        for (int i=0;i<gameNames.size();i++)
        {
            items.add(gameNames.get(i).getName());
            itemIds.add(Integer.valueOf(gameNames.get(i).getId()));
        }
    }

    public ArrayList<GameName> getGameName() {
        return gameNames;
    }

    class LoadAllGameName extends AsyncTask<String, String, String> {

        String error=null;
        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(context);
            pDialog.setMessage("Loading data. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * getting All products from url
         */
        protected String doInBackground(String... args) {
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();

            // getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest(url_all_gameName, "GET", params);

            // Check your log cat for JSON reponse
            //

            try {
                if (json!=null) {
                    Log.d("All Patients: ", json.toString());
                    // Checking for SUCCESS TAG
                    int success = json.getInt(TAG_SUCCESS);

                    if (success == 1) {
                        // products found
                        // Getting Array of patients
                        products = json.getJSONArray(TAG_GAME_NAME);

                        // looping through All Patients
                        for (int i = 0; i < products.length(); i++) {
                            JSONObject c = products.getJSONObject(i);

                            // Storing each json item in variable
                            String id = c.getString(TAG_GID);
                            String name = c.getString(TAG_NAME);
                            String imgId = c.getString(TAG_IMG);

                            addGameName(id, name, imgId);
                        }
                    } else {
                        error = json.getString("message");
                    }
                }
                else {
                    error="Please Connect Internet !";
                }
            } catch (JSONException e) {

                e.printStackTrace();
            }
             params = new ArrayList<NameValuePair>();

            // getting JSON string from URL
            json = jParser.makeHttpRequest(url_all_matches, "GET", params);

            // Check your log cat for JSON reponse


            try {
                if (json!=null) {
                    Log.d("All Patients: ", json.toString());
                    // Checking for SUCCESS TAG
                    int success = json.getInt(TAG_SUCCESS);

                    if (success == 1) {
                        // products found
                        // Getting Array of patients
                        products = json.getJSONArray(TAG_MATCH);

                        // looping through All Patients
                        for (int i = 0; i < products.length(); i++) {
                            JSONObject c = products.getJSONObject(i);

                            // Storing each json item in variable
                            String id = c.getString(TAG_MID);
                            String c_id = c.getString(TAG_M_GID);
                            String date = c.getString(TAG_DATE);
                            String p1 = c.getString(TAG_PLAYER1);
                            String p2 = c.getString(TAG_PLAYER2);
                            String winner = c.getString(TAG_WINNER);

                            addMatch(id, c_id, p1, p2, winner, date);
                        }
                    } else {
                        error = json.getString("message");
                    }
                }
                else {
                    error="Please Connect Internet !";
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all products
            if (error!=null)
                Toast.makeText(context,error,Toast.LENGTH_LONG).show();
            pDialog.dismiss();
            // updating UI from Background Thread

        }
    }

    private void addMatch(String id, String c_id, String p1, String p2, String winner, String date) {
        matches.add(new Schedule(id,c_id,p1,p2,winner,date));
    }
    public ArrayList<Schedule> getMatchOfGame(String id){
        ArrayList<Schedule> s=new ArrayList<>();
        for (int i=0;i<matches.size();i++)
        {
            if (matches.get(i).getG_id().equals(id)){
                s.add(matches.get(i));
            }
        }
        return s;
    }
}
