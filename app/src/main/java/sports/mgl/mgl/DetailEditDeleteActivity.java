package sports.mgl.mgl;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import sports.mgl.mgl.BussinessLayer.Controller;

public class DetailEditDeleteActivity extends AppCompatActivity {

    private Controller controller;
    private String gameId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_edit_delete);
        ListView lv= (ListView) findViewById(R.id.lvDetailGame);
        controller= Controller.getInstance();
        gameId = getIntent().getStringExtra("gameId");
        lv.setAdapter(new DetailEditDeleteAdapter(this,controller.getMatchOfGame(gameId)));
    }
}
