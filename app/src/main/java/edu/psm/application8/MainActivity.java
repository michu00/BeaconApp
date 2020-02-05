package edu.psm.application8;

import androidx.annotation.RequiresApi;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.Region;

public class MainActivity extends Activity implements BeaconConsumer {
    protected static final String TAG = "RangingActivity";
    private BeaconManager beaconManager;
    private LocationService locationService = new LocationService();
    int counter = 0;

    ImageView imageView;
    TextView tView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        beaconManager = BeaconManager.getInstanceForApplication(this);
        beaconManager.getBeaconParsers().add(new BeaconParser().
              setBeaconLayout(BeaconParser.EDDYSTONE_UID_LAYOUT));

        tView = findViewById(R.id.textView);
        imageView = findViewById(R.id.imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                beaconManager.bind(MainActivity.this);
                Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.rotate);
                imageView.startAnimation(animation);
            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        beaconManager.unbind(this);
    }

    protected void startScan(){
        beaconManager.bind(MainActivity.this);
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBeaconServiceConnect() {
        beaconManager.removeAllRangeNotifiers();
        beaconManager.setForegroundScanPeriod(200);
        beaconManager.addRangeNotifier((beacons, region) -> {
                Log.i(TAG, "Found beacons: "+beacons.size());
                beacons.forEach(it -> locationService.updateBeacon(it.getBluetoothAddress(), it.getRssi()) );
                beacons.forEach(it -> locationService.averageRssi(it.getBluetoothAddress(), it.getRssi()) );
                //Log.i(TAG, "Beacon stats: \n"+ locationService.printBeaconLocations() + "\n" + locationService.printAverageBeaconRssi() + "\n");
                //tView.append("Beacon stats: \n"+ locationService.printBeaconLocations() + "\n" + locationService.printAverageBeaconRssi() + "\n");
                counter++;
                checkScanResult();
        });

        try {
            beaconManager.startRangingBeaconsInRegion(new Region("myRangingUniqueId", null, null, null));
        } catch (RemoteException e) {
            Log.i(TAG, e.getMessage());
        }
    }

    public void checkScanResult(){
        if(counter==10){
            onDestroy();
            if(locationService.getAverageA0()*(-1) < locationService.getAverageA5()*(-1) && locationService.getAverageA0() != 0){
                showExercise("A0");
                counter=0;
            } else if(locationService.getAverageA0()*(-1) > locationService.getAverageA5()*(-1) && locationService.getAverageA5() != 0){
                showExercise("A5");
                counter=0;
            }else if(locationService.getAverageA0() == 0 && locationService.getAverageA5() == 0){
                tView.append("You are probably too far from any exercise position. Also you can check if your bluetooth is on \n");
                counter=0;
            }
            else{
                showExercise("A5");
                counter=0;
            }
            locationService.cleanLists();
        }

    }

    public void showExercise(String id){

        Intent i = new Intent(this, ShowExercise.class);
        i.putExtra("test", id);
        startActivity(i);



    /*    if(id.equals("A0")){
            Intent i = new Intent(this,ActivityA0.class);
            startActivity(i);
        }else {
            Intent i = new Intent(this,ActivityA5.class);
            startActivity(i);
        }*/
    }


}