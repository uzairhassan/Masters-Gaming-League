package sports.mgl.mgl.Admin;

/**
 * Created by HP on 12-Apr-17.
 */
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import sports.mgl.mgl.BussinessLayer.Controller;
import sports.mgl.mgl.Util.JSONParser;
import sports.mgl.mgl.R;


public class GameFragment extends Fragment implements View.OnClickListener{

    private ImageGridAdapter adapter1;
    private GridView lvProduct;
    ImageView imgSelected;
    Controller controller;

    public GameFragment() {
        // Required empty public constructor
    }

    Button btnSaveGame;
    EditText etGameName;
    String name;
    int imgId;
    private ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();
    private static String url_add_game = "https://mastersgamingleague.000webhostapp.com/add_game_name.php";
    private static final String TAG_SUCCESS = "success";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        controller=Controller.getInstance();
        View v=inflater.inflate(R.layout.fragment_game, container, false);
        btnSaveGame= (Button) v.findViewById(R.id.btnSaveGame);
        etGameName= (EditText) v.findViewById(R.id.etGameName);
        btnSaveGame.setOnClickListener(this);
        lvProduct=(GridView) v.findViewById(R.id.grid1);
        imgSelected= (ImageView) v.findViewById(R.id.imgSelected);
        adapter1=new ImageGridAdapter(getActivity());
        lvProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                imgId= (int) adapter1.getImgId(position);
                view.setPressed(true);
                imgSelected.setImageResource(imgId);
            }
        });


        lvProduct.setAdapter(adapter1);
        return v;
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.btnSaveGame)
        {
            name=etGameName.getText().toString();
            if (!name.equals(""))
                new AddGameName().execute();
            else
                etGameName.setError("Required Field !");
        }
    }
    class AddGameName extends AsyncTask<String, String, String> {

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
            params.add(new BasicNameValuePair("name", name));
            params.add(new BasicNameValuePair("imgId", String.valueOf(imgId)));
            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(url_add_game,
                    "POST", params);

            // checking for success tag
            try {
                if (json!=null){
                int success = json.getInt(TAG_SUCCESS);
                    Log.d("json",json.toString());

                if (success == 1) {
                        String id = json.getString("id");
                        controller.addGameName(id, name, String.valueOf(imgId));
                }  else {
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
                etGameName.setText("");
                AdminActivity a= (AdminActivity) getContext();
                a.updateGame();
            }
            pDialog.dismiss();

        }


    }
    public interface Interface {
        void updateGame();
    }
}