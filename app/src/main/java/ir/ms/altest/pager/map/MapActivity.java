package ir.ms.altest.pager.map;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.android.gms.location.LocationRequest;
import com.patloew.rxlocation.RxLocation;

import java.util.concurrent.TimeUnit;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import ir.ms.altest.R;

public class MapActivity extends AppCompatActivity implements MapCallback {

    private static final String TAG = MapActivity.class.getSimpleName();
    @BindView(R.id.map)
    Map map;
    @BindView(R.id.btnSelectLocation)
    Button btnSetLocation;
    @BindView(R.id.progress)
    ProgressBar progress;
    private RxLocation rxLocation;
    private LocationRequest locationRequest;
    private CompositeDisposable disposable = new CompositeDisposable();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favorite_map);
        ButterKnife.bind(this);

        rxLocation = new RxLocation(this);
        rxLocation.setDefaultTimeout(30, TimeUnit.SECONDS);

        map.setCallback(this);
        map.setDefaultZoom(12);
        map.setMyLocationEnabled(true);

        locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY)
                .setFastestInterval(15 * 1000)
                .setInterval(15 * 1000);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        getDisposable().add(rxLocation.location().updates(locationRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(this::onLocationUpdate)
                .subscribe()
        );
    }

    @Override
    protected void onDestroy() {
        if (getDisposable() != null)
            getDisposable().clear();
        super.onDestroy();
    }

    public CompositeDisposable getDisposable() {
        return disposable;
    }

    @Override
    public void onCameraIdle() {
        btnSetLocation.setEnabled(true);
    }

    @Override
    public void onCameraMoveStarted() {
        btnSetLocation.setEnabled(false);
    }

    @Override
    public void onMapReady() {

    }

    @OnClick(R.id.btnSelectLocation)
    public void selectLocation() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("lat", String.valueOf(map.getMap().getCameraPosition().target.latitude));
        returnIntent.putExtra("lng", String.valueOf(map.getMap().getCameraPosition().target.longitude));
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_CANCELED, returnIntent);
        finish();
    }

    public void showWaiting() {
        progress.setVisibility(View.VISIBLE);
    }


    public void hideWaiting() {
        progress.setVisibility(View.GONE);
    }

    public void onLocationUpdate(Location location) {
        Log.d(TAG, "onLocationUpdate");
        map.moveToLoc(location);
    }

}
