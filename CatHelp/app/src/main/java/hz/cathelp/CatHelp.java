package hz.cathelp;

import android.app.Application;
import android.util.Log;

import com.parrot.arsdk.ARSDK;

import io.socket.client.IO;
import io.socket.client.Socket;


/**
 * Created by leo on 16/09/16.
 */

public final class CatHelp extends Application {
    private Socket mSocket;

    public CatHelp(){
//        try{
//            mSocket = IO.socket("http://hz.wx.rs:8000/");
//        }catch(Exception ex)
//        {
//            Log.e("CatHelp", ex.getMessage());
//        }
        ARSDK.loadSDKLibs();

    }

    public Socket getSocket() {
        return mSocket;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
