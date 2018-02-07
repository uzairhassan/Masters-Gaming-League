package sports.mgl.mgl;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import sports.mgl.mgl.Util.JSONParser;

public class SignUpActivity extends AppCompatActivity {
    EditText etName, etEmail, etPassword, etCPassword;
    private ProgressDialog pDialog;
    private String name, email, password, cPassword;
    JSONParser jsonParser = new JSONParser();
    // url to create new product
    private static String url_create_product = "https://mastersgamingleague.000webhostapp.com/add_new_user.php";
    // JSON Node names
    private static final String TAG_SUCCESS = "success";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        etName = (EditText) findViewById(R.id.user_name);
        etEmail = (EditText) findViewById(R.id.user_email);
        etPassword = (EditText) findViewById(R.id.user_password);
        etCPassword = (EditText) findViewById(R.id.confirm_password);
        Button b = (Button) findViewById(R.id.btn_sginup);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = etName.getText().toString();
                email = etEmail.getText().toString();
                password = etPassword.getText().toString();
                cPassword = etCPassword.getText().toString();
                if (password.equals(cPassword)) {
                    new CreateNewUser().execute();
                }
                else {
                    etCPassword.setError("Password Not Matched !");
                }
            }
        });
    }

    class CreateNewUser extends AsyncTask<String, String, String> {

        private String error;

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(SignUpActivity.this);
            pDialog.setMessage("Uploading to server...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Creating product
         */
        protected String doInBackground(String... args) {
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("user_name", name));
            params.add(new BasicNameValuePair("user_email", email));
            params.add(new BasicNameValuePair("user_password", password));
            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(url_create_product,
                    "POST", params);

            // checking for success tag
            try {
                if (json!=null) {
                    int success = json.getInt(TAG_SUCCESS);

                    if (success == 1) {
                        // successfully created product

                        Intent i = new Intent(getApplicationContext(), SignInActivity.class);
                        startActivity(i);
                    } else {
                        error=json.getString("message");
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
            // dismiss the dialog once done
            if (error!=null)
                Toast.makeText(getApplication(),error,Toast.LENGTH_LONG).show();
            pDialog.dismiss();
        }

    }
}

