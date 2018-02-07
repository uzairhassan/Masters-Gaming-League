package sports.mgl.mgl.User;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ivbaranov.mfb.MaterialFavoriteButton;

import java.util.ArrayList;

import sports.mgl.mgl.BussinessLayer.GameName;
import sports.mgl.mgl.R;

/**
 * Created by Uzair Hassan on 13-Apr-17.
 */

public class GamesListAdapter extends BaseAdapter {
    private Context mycontext;
    private LayoutInflater myinflater;
    private ArrayList<GameName> gameNames;
    public GamesListAdapter(Context mycontext, ArrayList<GameName> gameName) {
        this.mycontext = mycontext;
        this.gameNames = gameName;
    }
    @Override
    public int getCount() {
        return gameNames.size();
    }

    @Override
    public Object getItem(int position) {
        return gameNames.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        GameName current = gameNames.get(position);
        LayoutInflater inflater = (LayoutInflater) mycontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.listview_games, parent, false);
        holder = new ViewHolder();
        holder.game_name = (TextView) convertView.findViewById(R.id.game_name);
        holder.game_image= (ImageView) convertView.findViewById(R.id.game_image);
        holder.btnFav= (MaterialFavoriteButton) convertView.findViewById(R.id.like_button);
//        holder.btnInfo= (Button) convertView.findViewById(R.id.btnGameInfo);
//        holder.btnInfo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(mycontext,"",Toast.LENGTH_SHORT).show();
//            }
//        });
        holder.btnFav.setOnFavoriteChangeListener(
                new MaterialFavoriteButton.OnFavoriteChangeListener() {
                    @Override
                    public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                        Context context = mycontext;
                        SharedPreferences sharedPref = context.getSharedPreferences(
                                "FavoriteGame", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putBoolean(gameNames.get(position).getId(), favorite);
                        editor.apply();
                    }
                });
        holder.game_name.setText(current.getName());
        holder.game_image.setImageResource(Integer.parseInt(current.getImgId()));
        return convertView;
    }
    static class ViewHolder
    {
        ImageView game_image;
        TextView game_name;
        MaterialFavoriteButton btnFav;
        Button btnInfo;
    }
}
