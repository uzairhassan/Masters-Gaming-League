package sports.mgl.mgl.User;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import sports.mgl.mgl.BussinessLayer.Schedule;
import sports.mgl.mgl.R;

/**
 * Created by Abdul Rehman Sarohy on 4/14/2017.
 */

public class MatchDetailAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private ArrayList<Schedule> matches;

    public MatchDetailAdapter(Context context, ArrayList<Schedule> matches) {
        this.mInflater  = LayoutInflater.from(context);
        this.matches=matches;
    }

    @Override
    public int getCount() {
        return matches.size();
    }

    @Override
    public Object getItem(int position) {
        return matches.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    class Holder{
        TextView tvPlayer1,tvPlayer2,tvWinner;
        TextView tvTime;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        Schedule match = matches.get(position);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.listview_match_view, null);
            holder = new Holder();
            holder.tvPlayer1= (TextView) convertView.findViewById(R.id.tvPlayer1);
            holder.tvPlayer2= (TextView) convertView.findViewById(R.id.tvPlayer2);
            holder.tvWinner= (TextView) convertView.findViewById(R.id.tvWinner);
            holder.tvTime= (TextView) convertView.findViewById(R.id.tvTime);
            convertView.setTag(holder);
        }
        else
        {
            holder = (Holder) convertView.getTag();
        }
        holder.tvTime.setText(match.getDate());
        holder.tvWinner.setText(match.getWinner());
        holder.tvPlayer1.setText(match.getPlayer1());
        holder.tvPlayer2.setText(match.getPlayer2());
        return convertView;
    }
}
