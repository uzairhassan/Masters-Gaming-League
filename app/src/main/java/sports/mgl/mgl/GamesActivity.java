package sports.mgl.mgl;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import sports.mgl.mgl.BussinessLayer.Controller;
import sports.mgl.mgl.BussinessLayer.GameName;

public class GamesActivity extends AppCompatActivity {

    private ListView list;
    //private List<country> countries;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games_list);
        list = (ListView)findViewById(R.id.games);
        Controller controller=Controller.getInstance();
        final GamesListAdapter Adapter=new GamesListAdapter(getApplicationContext(),controller.getGameName());
        list.setAdapter(Adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GameName gId= (GameName) Adapter.getItem(position);
                Intent intent = new Intent(getBaseContext(), DetailActivity.class);
                intent.putExtra("gameId", gId.getId());
                startActivity(intent);
            }
        });


    }
}
