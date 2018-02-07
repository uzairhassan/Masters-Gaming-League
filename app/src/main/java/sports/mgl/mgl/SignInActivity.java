package sports.mgl.mgl;

import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import sports.mgl.mgl.Admin.AdminActivity;
import sports.mgl.mgl.BussinessLayer.Controller;
import sports.mgl.mgl.ServiceAndBrodcast.NotificationService;
import sports.mgl.mgl.User.GamesViewAcitivty;
import sports.mgl.mgl.Util.JSONParser;

public class SignInActivity extends AppCompatActivity {

    Intent mServiceIntent;
    private NotificationService mSensorService;
    private ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();
    // url to create new product
    private static String url_create_product = "https://mastersgamingleague.000webhostapp.com/login_check.php";
    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_TYPE = "type";
    private String email,password;
    private EditText etEmail,etPassword;
    private Controller controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        mSensorService = new NotificationService(getBaseContext());
        mServiceIntent = new Intent(getBaseContext(), mSensorService.getClass());
        if (!isMyServiceRunning(mSensorService.getClass())) {
            startService(mServiceIntent);
        }
        etEmail=(EditText) findViewById(R.id.etEmail);
        etPassword=(EditText) findViewById(R.id.etPassword);
        controller= Controller.getInstance();
        controller.setContext(SignInActivity.this);
        controller.loadData();
        SharedPreferences sharedPref = getApplication().getSharedPreferences(
                "PersonData", Context.MODE_PRIVATE);
        String check=sharedPref.getString("person","0");
        if (check.equals("1")){
            etEmail.setText(sharedPref.getString("email",""));
            etPassword.setText(sharedPref.getString("password",""));
        }
    }
    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.i ("isMyServiceRunning?", true+"");
                return true;
            }
        }
        Log.i ("isMyServiceRunning?", false+"");
        return false;
    }
    @Override
    protected void onDestroy() {
        stopService(mServiceIntent);
        Log.i("MAINACT", "onDestroy!");
        super.onDestroy();

    }
    public void button(View v)
    {
        if(R.id.btnSignIn==v.getId())
        {
            email=etEmail.getText().toString();
            password=etPassword.getText().toString();
            new LoginUser().execute();
        }
        else if(R.id.btnSignUp==v.getId())
        {
            startActivity(new Intent(this,SignUpActivity.class));
        }
    }
    class LoginUser extends AsyncTask<String, String, String> {

        String error=null;
        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(SignInActivity.this);
            pDialog.setMessage("Checking from server...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Creating product
         * */
        protected String doInBackground(String... args) {
            // Building Parameters

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("user_email", email));
            params.add(new BasicNameValuePair("user_password",password));
            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(url_create_product,
                    "GET", params);
            // checking for success tag
            try {
                if (json!=null) {
                    int success = json.getInt(TAG_SUCCESS);
                    if (success == 1) {
                        String type = json.getString(TAG_TYPE);
                        // successfully created product
                        if (type.equals("1")) {
                            Intent i = new Intent(getApplicationContext(), AdminActivity.class);
                            startActivity(i);
                        } else {
                            startActivity(new Intent(getApplicationContext(), GamesViewAcitivty.class));
                            SharedPreferences sharedPrefNotify = getApplication().getSharedPreferences(
                                    "PersonData", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPrefNotify.edit();
                            editor.putString("person", "1");
                            editor.putString("email", email);
                            editor.putString("password", password);
                            editor.apply();
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
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
            if (error!=null)
                Toast.makeText(getApplication(),error,Toast.LENGTH_LONG).show();
            pDialog.dismiss();
        }

    }
}
