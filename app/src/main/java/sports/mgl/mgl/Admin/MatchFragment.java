package sports.mgl.mgl.Admin;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
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
import sports.mgl.mgl.Util.JSONParser;
import sports.mgl.mgl.R;

/**
 * Created by HP on 12-Apr-17.
 */
public class MatchFragment extends Fragment implements View.OnClickListener {

    Button btnTime, btnDate;
    ImageButton btnSaveMatch;
    private int mYear, mMonth, mDay, mHour, mMinute;
    EditText etPlayer1,etPlayer2;
    int gameId;
    Spinner ddGameName;
    ArrayList<String> items=new ArrayList<>();
    ArrayList<Integer> itemIds=new ArrayList<>();
    private ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();
    private static String url_add_match = "https://mastersgamingleague.000webhostapp.com/add_game_schedule.php";
    private static final String TAG_SUCCESS = "success";
    private String player1,player2,date;
    private Controller controller;

    public MatchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View v1 = inflater.inflate(R.layout.fragment_match, container, false);
        btnSaveMatch= (ImageButton) v1.findViewById(R.id.btnSaveMatch);
        btnSaveMatch.setOnClickListener(this);
        etPlayer1= (EditText) v1.findViewById(R.id.etPlayer1);
        etPlayer2= (EditText) v1.findViewById(R.id.etPlayer2);
        btnDate = (Button) v1.findViewById(R.id.btnDate);
        btnTime = (Button) v1.findViewById(R.id.btnTime);
        Controller controller=Controller.getInstance();
        controller.getGameName(items,itemIds);
        ddGameName = (Spinner)v1.findViewById(R.id.ddGameName);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, items);
        ddGameName.setAdapter(adapter);
        ddGameName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> arg0, View view, int position, long id) {
                gameId = itemIds.get(ddGameName.getSelectedItemPosition());

            }
            public void onNothingSelected(AdapterView<?> arg0) { }
        });
        //Current time
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+5:00"));
        Date currentLocalTime = cal.getTime();
        DateFormat date = new SimpleDateFormat("HH:mm a");
        date.setTimeZone(TimeZone.getTimeZone("GMT+5:00"));
        String localTime = date.format(currentLocalTime);
        Toast.makeText(getActivity(), "Current TIme is: " + localTime, Toast.LENGTH_SHORT).show();
        //Current Date
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(cal.getTime());
        Toast.makeText(getActivity(), "Current Date is: " + formattedDate, Toast.LENGTH_SHORT).show();
        btnTime.setText(localTime);
        btnDate.setText(formattedDate);

        btnTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(getActivity(),"clicking",Toast.LENGTH_LONG);
                toast.show();

                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
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
                Toast toast = Toast.makeText(getActivity(),"clicking",Toast.LENGTH_LONG);
                toast.show();

                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                // Launch Date Picker Dialog
                DatePickerDialog dpd = new DatePickerDialog(getActivity(),
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




        return v1;

    }


    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.btnSaveMatch)
        {
            player1=etPlayer1.getText().toString();
            player2=etPlayer2.getText().toString();
            date=btnDate.getText()+" - "+btnTime.getText();
            if (!player1.equals("")&&!player2.equals(""))
                new AddMatch().execute();
            else {
                if (player1.equals(""))
                    etPlayer1.setError("Required Field !");
                if (player2.equals(""))
                    etPlayer2.setError("Required Field !");
            }
        }
    }

    public void setDropDown() {
        itemIds.clear();
        items.clear();
        Controller controller=Controller.getInstance();
        controller.getGameName(items,itemIds);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, items);
        ddGameName.setAdapter(adapter);
    }

    class AddMatch extends AsyncTask<String, String, String> {


        private String error=null;

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getContext());
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
            params.add(new BasicNameValuePair("player1", player1));
            params.add(new BasicNameValuePair("player2", player2));
            params.add(new BasicNameValuePair("date", date));
            params.add(new BasicNameValuePair("c_id", String.valueOf(gameId)));
            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(url_add_match,
                    "POST", params);

            // checking for success tag
            try {
                if (json!=null) {
                    int success = json.getInt(TAG_SUCCESS);

                    if (success == 1) {

                        // successfully created product
                        // closing this screen
                    } else {
                        // failed to create product
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
                Toast.makeText(getContext(),error,Toast.LENGTH_LONG).show();
            else
            {
                etPlayer1.setText("");
                etPlayer2.setText("");
            }
            pDialog.dismiss();
        }

    }
}
