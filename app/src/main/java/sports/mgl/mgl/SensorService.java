package sports.mgl.mgl;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by fabio on 30/01/2016.
 */
public class SensorService extends Service {
    private static final String TAG_NAME = "name";
    public int counter=0;
    JSONArray products = null;
    JSONParser jParser = new JSONParser();
    private static String url_all_gameName = "https://mastersgamingleague.000webhostapp.com/get_notification.php";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_NOTI = "noti";
    private static final String TAG_ID = "id";
    private static final String TAG_GID = "game_id";
    private static final String TAG_DETAIL = "detail";

    public SensorService(Context applicationContext) {
        super();
        Log.i("HERE", "here I am!");
    }

    public SensorService() {
    }
    NotificationCompat.Builder builder;
    NotificationManager manager;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        startTimer();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("EXIT", "ondestroy!");
        Intent broadcastIntent = new Intent("mgl.service.RestartSensor");
        sendBroadcast(broadcastIntent);
        stoptimertask();
    }

    private Timer timer;
    private TimerTask timerTask;
    long oldTime=0;
    public void startTimer() {
        //set a new Timer
        timer = new Timer();

        //initialize the TimerTask's job
        initializeTimerTask();

        //schedule the timer, to wake up every 1 second
        timer.schedule(timerTask, 60000, 60000); //
    }

    /**
     * it sets the timer to print the counter every x seconds
     */
    class GetNotify extends AsyncTask{
        @Override
        protected Object doInBackground(Object[] param) {
            SharedPreferences sharedPrefNotify = getApplication().getSharedPreferences(
                    "Notification", Context.MODE_PRIVATE);
            String notified =sharedPrefNotify.getString("notified","0");
            SharedPreferences sharedPrefFav = getApplication().getSharedPreferences(
                    "FavoriteGame", Context.MODE_PRIVATE);

            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();

            // getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest(url_all_gameName, "GET", params);

            // Check your log cat for JSON reponse
            Log.d("All Patients: ", json.toString());

            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // products found
                    // Getting Array of patients
                    products = json.getJSONArray(TAG_NOTI);

                    // looping through All Patients
                    for (int i = 0; i < products.length(); i++) {
                        JSONObject c = products.getJSONObject(i);

                        // Storing each json item in variable

                        String id = c.getString(TAG_ID);
                        if (Integer.valueOf(id)>Integer.valueOf(notified))
                        {
                            String game_id = c.getString(TAG_GID);
                            String detail = c.getString(TAG_DETAIL);
                            String gameName = c.getString(TAG_NAME);
                            boolean check=sharedPrefFav.getBoolean(game_id,false);
                            Log.d("check1",String.valueOf(check));
                            if (check){
                                builder =
                                        new NotificationCompat.Builder(getApplication())
                                                .setSmallIcon(R.drawable.golf)
                                                .setContentTitle("Change of time of "+gameName)
                                                .setContentText(detail);

                                Intent notificationIntent = new Intent(getApplication(), SignInActivity.class);
                                PendingIntent contentIntent = PendingIntent.getActivity(getApplication(), 0, notificationIntent,
                                        PendingIntent.FLAG_UPDATE_CURRENT);
                                builder.setContentIntent(contentIntent);

                                // Add as notification
                                manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                                manager.notify(0, builder.build());
                            }
                            SharedPreferences.Editor editor = sharedPrefNotify.edit();
                            editor.putString("notified", id);
                            editor.apply();
                        }

                    }
                } else {
                    // no products found
                    // Launch Add New product Activity
                   /* Intent i = new Intent(getApplicationContext(),
                            NewProductActivity.class);
                    // Closing all previous activities
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);*/
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
    public void initializeTimerTask() {
        timerTask = new TimerTask() {
            public void run() {
                Log.i("in timer", "in timer ++++  "+ (counter++));

                new GetNotify().execute();
            }
        };
    }

    /**
     * not needed
     */
    public void stoptimertask() {
        //stop the timer, if it's not already null
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}