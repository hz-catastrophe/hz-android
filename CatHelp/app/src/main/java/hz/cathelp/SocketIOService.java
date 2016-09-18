package hz.cathelp;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;

import com.google.android.gms.maps.model.LatLng;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class SocketIOService extends IntentService {
    private static final String ACTION_GET_INJURED = "hz.cathelp.action.ACTION_GET_INJURED";

    private static final String EXTRA_GET_INJURED_POSITION = "hz.cathelp.extra.EXTRA_GET_INJURED_POSITION";

    private final IBinder mBinder = new LocalBinder();

    private Emitter.Listener mEventConnectListener = new Emitter.Listener() {
        @Override
        public void call(Object... args) {

        }
    };

    private Emitter.Listener mEventDisconnectListener = new Emitter.Listener() {
        @Override
        public void call(Object... args) {

        }
    };

    private Emitter.Listener mEventListener = new Emitter.Listener() {
        @Override
        public void call(Object... args) {

        }
    };

    public class LocalBinder extends Binder {
        SocketIOService getService() {
            return SocketIOService.this;
        }
    }

    public Runnable mWorker = new Runnable() {

        @Override
        public void run() {

        }
    };

    public SocketIOService() throws URISyntaxException {
        super("SocketIOService");


    }

    public static void startActionGetInjured(Context context, LatLng position) {
        Intent intent = new Intent(context, SocketIOService.class);
        intent.setAction(ACTION_GET_INJURED);
        intent.putExtra(EXTRA_GET_INJURED_POSITION, position);
        context.startService(intent);
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_GET_INJURED.equals(action)) {
                startActionGetInjured(this, (LatLng)intent.getParcelableExtra(EXTRA_GET_INJURED_POSITION));
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionGetInjured(LatLng position) {

    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

}
