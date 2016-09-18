package hz.cathelp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
    public static String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.activitiy_main_button_start_discovery);
    }

//    public native String stringFromJNI();

    // Used to load the 'native-lib' library on application startup.
//    static {
//        System.loadLibrary("native-lib");
//    }

    public void button_start_discovery_onClick(View view){
        Log.i(TAG, "foo");
        startActivity(new Intent(this, FlightPlanActivity.class));
    }

    public void button_bebop_activity_onClick(View view) {
        startActivity(new Intent(this, DeviceListActivity.class));
    }
}
