package sports.mgl.mgl.Admin;

/**
 * Created by HP on 16-Feb-17.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import sports.mgl.mgl.R;

public class ImageGridAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<Integer> images;

    //Constructor

    public ImageGridAdapter(Context mContext) {
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
        images=new ArrayList<>();
        images.add(R.drawable.american_football);
        images.add(R.drawable.archery);
        images.add(R.drawable.football);
        images.add(R.drawable.card);
        images.add(R.drawable.chess);
        images.add(R.drawable.cricket);
        images.add(R.drawable.badminton);
        images.add(R.drawable.bowling);
        images.add(R.drawable.cycling);
        images.add(R.drawable.golf);
        images.add(R.drawable.shooting);
        images.add(R.drawable.poker);
        images.add(R.drawable.boxing);


    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object getItem(int position) {
        return images.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public long getImgId(int position)
    {
        return images.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {

        ViewHolder holder;
        Integer p = images.get(position);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.gridview_images, null);
            holder = new ViewHolder();
            holder.img= (ImageView) convertView.findViewById(R.id.grid_image);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.img.setImageResource(p);
        return convertView;

    }
    static class ViewHolder
    {
        ImageView img;
    }
}

