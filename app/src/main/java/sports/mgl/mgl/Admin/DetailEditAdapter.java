package sports.mgl.mgl.Admin;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import sports.mgl.mgl.BussinessLayer.Schedule;
import sports.mgl.mgl.R;

public class DetailEditAdapter extends BaseAdapter {
    private final LayoutInflater mInflater;
    private ArrayList<Schedule> matches;
    private Context context;

    public DetailEditAdapter(Context context,
                             ArrayList<Schedule> matches)
    {
        this.context=context;
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
        ImageButton btnEdit,btnDelete;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final Holder holder;
        Schedule match = matches.get(position);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.listview_detail_edit, null);
            holder = new Holder();
            holder.tvPlayer1= (TextView) convertView.findViewById(R.id.tvPlayer1);
            holder.tvPlayer2= (TextView) convertView.findViewById(R.id.tvPlayer2);
            holder.tvWinner= (TextView) convertView.findViewById(R.id.tvWinner);
            holder.tvTime= (TextView) convertView.findViewById(R.id.tvTime);
//            holder.btnDelete= (ImageButton) convertView.findViewById(R.id.btnDelete);
            holder.btnEdit= (ImageButton) convertView.findViewById(R.id.btnEdit);
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
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(context,GameUpdateActivity.class);
                i.putExtra("id", String.valueOf(matches.get(position).getId()));
                i.putExtra("gid", String.valueOf(matches.get(position).getG_id()));
                i.putExtra("time", String.valueOf(holder.tvTime.getText()));
                i.putExtra("winner", String.valueOf(holder.tvWinner.getText()));
                i.putExtra("player1", String.valueOf(holder.tvPlayer1.getText()));
                i.putExtra("player2", String.valueOf(holder.tvPlayer2.getText()));
                context.startActivity(i);
            }
        });
//        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(context,"Delete",Toast.LENGTH_LONG).show();
//            }
//        });
        return convertView;
    }
}
