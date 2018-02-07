package sports.mgl.mgl;

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

/**
 * Created by HP on 12-Apr-17.
 */
public class UpdateFragment extends Fragment {
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
        ListView lvGames= (ListView) v.findViewById(R.id.games);
        Controller controller=Controller.getInstance();
        //controller.loadDataDetail();
        final GamesListAdapter Adapter=new GamesListAdapter(getContext(),controller.getGameName());
        lvGames.setAdapter(Adapter);
        lvGames.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GameName gId= (GameName) Adapter.getItem(position);
                Intent intent = new Intent(getContext(), DetailEditDeleteActivity.class);
                intent.putExtra("gameId", gId.getId());
                startActivity(intent);
            }
        });
        return v;
    }
}
