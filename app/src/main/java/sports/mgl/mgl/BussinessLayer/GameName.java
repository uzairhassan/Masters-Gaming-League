package sports.mgl.mgl.BussinessLayer;

import android.util.Log;

/**
 * Created by Abdul Rehman Sarohy on 4/12/2017.
 */

public class GameName {
    String id,name,imgId;

    public GameName(String id, String name, String imgId) {
        this.id = id;
        this.name = name;
        this.imgId = imgId;
        Log.d("Check","Done");
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return String.valueOf(id);
    }

    public String getImgId() {
        return imgId;
    }
}
