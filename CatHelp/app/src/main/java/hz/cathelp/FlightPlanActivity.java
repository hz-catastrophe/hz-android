package hz.cathelp;

import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.IBinder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.TileOverlay;
import com.parrot.arsdk.arcontroller.ARCONTROLLER_DEVICE_STATE_ENUM;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class FlightPlanActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener {
    private String TAG = FlightPlanActivity.class.getName();

    private GoogleMap mMap;
    private ArrayList<MarkerOptions> mWayPoints = new ArrayList<>();
    private HashMap<MarkerOptions, Marker> mMarker = new HashMap<>();
    private HashMap<Marker, Integer> mMarkerIndex = new HashMap<>();
    private ArrayList<Integer> images = new ArrayList<>(Arrays.asList(
            R.drawable.start,
            R.drawable.injured1s,
            R.drawable.injured2s,
            R.drawable.injured3s
    ));

    private VideoView videoView;

    private int currentWaypointShowWindow = 0;

    SocketIOService mService;
    boolean mBound = false;

    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            SocketIOService.LocalBinder binder = (SocketIOService.LocalBinder) service;
            mService = binder.getService();
            mBound = true;

            Log.i(TAG, "Connected to service");
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
            Log.i(TAG, "Disconnected from service!");
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        // Bind to LocalService
        Intent intent = new Intent(this, SocketIOService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Unbind from the service
        if (mBound) {
            unbindService(mConnection);
            mBound = false;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_plan);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapLongClickListener(this);
        mMap.setInfoWindowAdapter(this.windowAdapter);

        // Add a marker in Sydney and move the camera
        LatLng technopark = new LatLng(47.389776, 8.516455);
        MarkerOptions moTechnopark = new MarkerOptions().position(technopark)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));

        mMarker.put(moTechnopark, mMap.addMarker(moTechnopark));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(technopark, 18.0f));
        mWayPoints.add(moTechnopark);

        for(int i=0;i<users.size();i++){
            mWayPoints.add(new MarkerOptions().position(users.get(i)
                    .getPosition()).title(users.get(i).getName())
                    .draggable(false).icon(users.get(i).getMarkerColor()));
        }

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                currentWaypointShowWindow = mMarkerIndex.get(marker);
                marker.showInfoWindow();

                return false;
            }
        });

        updateMap();
    }

    @Override
    public void onMapLongClick(LatLng point) {
        if(mMap != null){
            mWayPoints.add(new MarkerOptions()
                    .position(point)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            updateMap();
        }
    }

    public void updateMap(){
        if(mMap != null) {
            mMap.clear();
            mMarker.clear();

            for(int i=0;i<mWayPoints.size();i++){
                MarkerOptions mo = mWayPoints.get(i);

                mMarker.put(mo, mMap.addMarker(mo));
                mMarkerIndex.put(mMarker.get(mo), i);
            }

            PolylineOptions pl = new PolylineOptions();
            for (int i = 0; i < mWayPoints.size(); i++) {
                pl.add(mWayPoints.get(i).getPosition());
            }

            pl.color(Color.RED);
            mMap.addPolyline(pl);
        }
    }

    public void buttonDeleteLastWaypoint_onClick(View vew){
        if(mWayPoints.size() > 1){
            mWayPoints.remove(mWayPoints.size()-1);
            updateMap();
        }
    }

    public void buttonPreviousWaypoint_onClick(View view) {
        this.currentWaypointShowWindow = (this.mWayPoints.size() + this.currentWaypointShowWindow - 1) % this.mWayPoints.size();

        mMarker.get(mWayPoints.get(currentWaypointShowWindow)).showInfoWindow();
    }

    public void buttonNextWaypoint_onClick(View view) {
        this.currentWaypointShowWindow = (this.mWayPoints.size() + this.currentWaypointShowWindow + 1) % this.mWayPoints.size();

        mMarker.get(mWayPoints.get(currentWaypointShowWindow)).showInfoWindow();
    }

    public void buttonWaypointsDone_onClick(View view) {
        if(mMap != null){
            mWayPoints.add(mWayPoints.get(0));

            updateMap();
            PolygonOptions po = new PolygonOptions();
            for(MarkerOptions mo : mWayPoints){
                po.add(mo.getPosition());
            }
            po.fillColor(Color.RED);

//            mMap.addPolygon(po);
            Uri video = Uri.parse("android.resource://hz.cathelp/" + R.raw.dronedydrone);
            VideoView vidView = (VideoView) findViewById(R.id.videoViewLive);
//            MediaController mediaController = new MediaController(this);
//            mediaController.setAnchorView(vidView);
//            vidView.setMediaController(mediaController);
            vidView.setZOrderOnTop(true);
            vidView.setVideoURI(video);
            vidView.start();
            vidView.setZOrderOnTop(false);
        }
    }

    GoogleMap.InfoWindowAdapter windowAdapter = new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            // Defines the contents of the InfoWindow
            @Override
            public View getInfoContents(Marker marker) {

                // Getting view from the layout file info_window_layout
                View v = getLayoutInflater().inflate(R.layout.marker_view, null);

                ImageView iv = (ImageView) v.findViewById(R.id.imageView);
                iv.setImageDrawable(ContextCompat.getDrawable(FlightPlanActivity.this, images.get(currentWaypointShowWindow % images.size())));

                // Returning the view containing InfoWindow contents
                return v;
            }
    };



    private List<User> users = new ArrayList<>(Arrays.asList(
            new User(0, "Hans", UserSource.IOS, "073/234 123 42",  UserState.INJURED, new LatLng(47.389811, 8.516842), UserNeeds.MEDIC, UserNeedsStatus.OPEN, new ArrayList<UserSkill>(), new ArrayList<Integer>()),
            new User(1, "Matthias", UserSource.IOS, "073/341 568 25",  UserState.HEAVILY_INJURED, new LatLng(47.389973, 8.517327), UserNeeds.MEDIC, UserNeedsStatus.OPEN, new ArrayList<UserSkill>(), new ArrayList<Integer>()),
            new User(3, "Lukas", UserSource.IOS, "073/442 19 63",  UserState.OK, new LatLng(47.389754, 8.517627), UserNeeds.MEDIC, UserNeedsStatus.OPEN, new ArrayList<UserSkill>(), new ArrayList<Integer>()),
            new User(2, "Robert", UserSource.IOS, "073/341 568 25",  UserState.HEAVILY_INJURED, new LatLng(47.389478, 8.516763), UserNeeds.MEDIC, UserNeedsStatus.OPEN, new ArrayList<UserSkill>(), new ArrayList<Integer>())
    ));
}
