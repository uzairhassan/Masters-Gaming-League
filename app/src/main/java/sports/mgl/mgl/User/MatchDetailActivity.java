package sports.mgl.mgl.User;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import sports.mgl.mgl.BussinessLayer.Controller;
import sports.mgl.mgl.R;

public class MatchDetailActivity extends AppCompatActivity {

    Toolbar toolbar;
    private Controller controller;
    private String gameId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_detail);
        ListView lv= (ListView) findViewById(R.id.lvDetailGame);
        controller=Controller.getInstance();
        gameId = getIntent().getStringExtra("gameId");
        lv.setAdapter(new MatchDetailAdapter(this,controller.getMatchOfGame(gameId)));
    }
}
