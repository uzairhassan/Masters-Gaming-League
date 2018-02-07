package sports.mgl.mgl;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Uzair Hassan on 16-Apr-17.
 */

public class MyService extends Service {
    private final IBinder mBinder = new MyBinder();
    private ArrayList<String> list = new ArrayList<String>();

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(this, " Hello 1", Toast.LENGTH_SHORT).show();


    }

    @Override

    public int onStartCommand(Intent intent, int flags, int startId) {
        // Should check for database change here?
        Toast.makeText(this, " Hello 2", Toast.LENGTH_SHORT).show();
        return Service.START_STICKY;
        // fetches data from database

    }

    @Override
    public IBinder onBind(Intent arg0) {
        return mBinder;
    }

    public class MyBinder extends Binder {
        MyService getService() {
            return MyService.this;
        }
    }

}