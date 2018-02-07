package sports.mgl.mgl;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import sports.mgl.mgl.BussinessLayer.Controller;
import sports.mgl.mgl.BussinessLayer.Schedule;

public class UpdateActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnTime, btnDate,btnUpdateMatch;
    private int mYear, mMonth, mDay, mHour, mMinute;
    EditText etPlayer1,etPlayer2,etWinner;
    String scheduleId,gameId,time,winner,player1,player2;
    private ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();
    private static String url_add_match = "https://mastersgamingleague.000webhostapp.com/update_game_schedule.php";
    private static final String TAG_SUCCESS = "success";
    private Context context;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        context=this;
        etPlayer1= (EditText)findViewById(R.id.etPlayer1);
        etPlayer2= (EditText) findViewById(R.id.etPlayer2);
        btnDate = (Button) findViewById(R.id.btnDate);
        btnTime = (Button) findViewById(R.id.btnTime);
        etWinner = (EditText) findViewById(R.id.etWinner);
        btnUpdateMatch= (Button) findViewById(R.id.btnUpdateMatch);
        btnUpdateMatch.setOnClickListener(this);
        scheduleId = getIntent().getStringExtra("id");
        gameId = getIntent().getStringExtra("gid");
        winner=getIntent().getStringExtra("winner");
        time=getIntent().getStringExtra("time");
        player1=getIntent().getStringExtra("player1");
        player2=getIntent().getStringExtra("player2");
        String [] t=time.split(" - ");
        btnTime.setText(t[1]);
        btnDate.setText(t[0]);
        etPlayer1.setText(player1);
        etPlayer2.setText(player2);


        btnTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(getApplication(),"clicking",Toast.LENGTH_LONG);
                toast.show();

                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(UpdateActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        btnTime.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });



        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(getApplicationContext(),"clicking",Toast.LENGTH_LONG);
                toast.show();

                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                // Launch Date Picker Dialog
                DatePickerDialog dpd = new DatePickerDialog(UpdateActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // Display Selected date in textbox
                                btnDate.setText(dayOfMonth + "-"
                                        + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                dpd.show();
            }



        });
    }
    String p1="",p2="",date="",win="",updateLine=null;
    public void onClick(View v) {
        if (v.getId()==R.id.btnUpdateMatch)
        {
            p1=etPlayer1.getText().toString();
            p2=etPlayer2.getText().toString();
            date=btnDate.getText()+" - "+btnTime.getText();
            if (!time.equals(date)){
                updateLine="Time change from "+time+" to "+date;
            }
            win=etWinner.getText().toString();
            new UpdateMatch().execute();
        }
    }
    class UpdateMatch extends AsyncTask<String, String, String> {


        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(context);
            pDialog.setMessage("Uploading to server...");
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
            params.add(new BasicNameValuePair("player1", p1));
            params.add(new BasicNameValuePair("player2", p2));
            params.add(new BasicNameValuePair("date", date));
            params.add(new BasicNameValuePair("c_id", String.valueOf(gameId)));
            params.add(new BasicNameValuePair("id", String.valueOf(scheduleId)));
            params.add(new BasicNameValuePair("winner", String.valueOf(win)));
            if (updateLine!=null)
                params.add(new BasicNameValuePair("updateLine", updateLine));
            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(url_add_match,
                    "POST", params);

            // checking for success tag
            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // successfully created product
                    // closing this screen
                } else {
                    // failed to create product
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
            pDialog.dismiss();
        }

    }
}
