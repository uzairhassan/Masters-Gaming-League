package sports.mgl.mgl.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import sports.mgl.mgl.BussinessLayer.Controller;
import sports.mgl.mgl.BussinessLayer.GameName;
import sports.mgl.mgl.User.GamesListAdapter;
import sports.mgl.mgl.R;

/**
 * Created by HP on 12-Apr-17.
 */
public class UpdateFragment extends Fragment {
    ListView lvGames;
    public UpdateFragment() {
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
        View v=inflater.inflate(R.layout.fragment_update, container, false);
        lvGames= (ListView) v.findViewById(R.id.games);
        Controller controller=Controller.getInstance();
        //controller.loadDataDetail();
        final GamesListAdapter Adapter=new GamesListAdapter(getContext(),controller.getGameName());
        lvGames.setAdapter(Adapter);
        lvGames.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GameName gId= (GameName) Adapter.getItem(position);
                Intent intent = new Intent(getContext(), DetailEditActivity.class);
                intent.putExtra("gameId", gId.getId());
                startActivity(intent);
            }
        });
        return v;
    }

    public void setListView() {
        Controller controller=Controller.getInstance();
        final GamesListAdapter Adapter=new GamesListAdapter(getContext(),controller.getGameName());
        lvGames.setAdapter(Adapter);
    }
}