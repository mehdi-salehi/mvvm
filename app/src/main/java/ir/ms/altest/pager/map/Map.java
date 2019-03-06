package ir.ms.altest.pager.map;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import ir.ms.altest.R;

public class Map extends FrameLayout implements OnMapReadyCallback, GoogleMap.OnCameraIdleListener, GoogleMap.OnCameraMoveStartedListener {
    private int defaultZoom = 18;
    private LatLng defaultLocation = new LatLng(32.933144, 53.133750);//IRAN
    private Context context;
    private AttributeSet attributeSet;
    private GoogleMap googleMap;
    private MapCallback callback;
    private boolean myLocationEnabled;
    private ArrayList<Marker> markers = new ArrayList<>();

    public Map(Context context) {
        super(context);

        LayoutInflater.from(context).inflate(R.layout.map, this);
        this.context = context;
        if (!isInEditMode())
            init();
    }

    public Map(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.map, this);
        this.context = context;
        this.attributeSet = attrs;
        if (!isInEditMode())
            init();
    }

    public Map(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.map, this);
        this.context = context;
        this.attributeSet = attrs;
        if (!isInEditMode())
            init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public Map(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        LayoutInflater.from(context).inflate(R.layout.map, this);
        this.context = context;
        this.attributeSet = attrs;
        if (!isInEditMode())
            init();
    }

    private void init() {
        addMapFragment();
    }

    private void addMapFragment() {
        MapsInitializer.initialize(context);
        FragmentManager fm = ((AppCompatActivity) context).getSupportFragmentManager();
        SupportMapFragment supportMapFragment = SupportMapFragment.newInstance();
        fm.beginTransaction().replace(R.id.mapContainer, supportMapFragment, "map").commitAllowingStateLoss();
        supportMapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        this.googleMap.getUiSettings().setTiltGesturesEnabled(true);
        this.googleMap.getUiSettings().setRotateGesturesEnabled(false);
        this.googleMap.getUiSettings().setCompassEnabled(false);
        this.googleMap.getUiSettings().setZoomControlsEnabled(false);
        this.googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        this.googleMap.setOnCameraIdleListener(this);
        this.googleMap.setOnCameraMoveStartedListener(this);
        this.googleMap.getUiSettings().setMapToolbarEnabled(false);

        if (ActivityCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context,
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            googleMap.setMyLocationEnabled(false);
            return;
        }
        googleMap.setMyLocationEnabled(isMyLocationEnabled());
        moveToLoc(new LatLng(defaultLocation.latitude, defaultLocation.longitude), 6);
        callback.onMapReady();
    }

    @Override
    public void onCameraIdle() {
        if (callback != null)
            callback.onCameraIdle();
    }

    public void moveToLoc(LatLng loc, float zoom) {
        if (googleMap != null)
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, zoom));
    }

    public void moveToLoc(Location location) {
        if (location == null) {
            location = new Location("");
            location.setLatitude(defaultLocation.latitude);
            location.setLongitude(defaultLocation.longitude);
        }

        LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());
        if (googleMap != null)
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, defaultZoom));
    }

    @Nullable
    public GoogleMap getMap() {
        return googleMap;
    }

    public void setDefaultZoom(int defaultZoom) {
        this.defaultZoom = defaultZoom;
    }

    public void setCallback(MapCallback callback) {
        this.callback = callback;
    }

    public boolean isMyLocationEnabled() {
        return myLocationEnabled;
    }

    public void setMyLocationEnabled(boolean myLocationEnabled) {
        this.myLocationEnabled = myLocationEnabled;
    }

    @Override
    public void onCameraMoveStarted(int i) {
        if (callback != null)
            callback.onCameraMoveStarted();
    }
}
