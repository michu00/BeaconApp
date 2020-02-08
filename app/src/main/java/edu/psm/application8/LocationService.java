package edu.psm.application8;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LocationService {

    private final List<BeaconLocations> beaconLocations;
     List<Integer> rssiListA0 = new ArrayList<>();
     List<Integer> rssiListA5 = new ArrayList<>();
    int averageA0;
    int averageA5;

    public LocationService() {
        this.beaconLocations = new ArrayList<>();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void updateBeacon(String deviceId, int signal) {
        Optional<BeaconLocations> beaconToUpdate = beaconLocations.stream().filter(it -> it.getDeviceId().equals(deviceId)).findAny();

        if (beaconToUpdate.isPresent()) {
            beaconToUpdate.get().updateSignal(signal);
        } else {
            beaconLocations.add(new BeaconLocations(deviceId, signal));
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public String printBeaconLocations() {
        StringBuilder sb = new StringBuilder();
        beaconLocations.forEach(it -> sb.append(it.getDeviceId()).append(" : ").append(it.getSignals()).append("\n"));
        return sb.toString();
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public String printAverageBeaconRssi(){
        StringBuilder sb = new StringBuilder();
        beaconLocations.forEach(it -> sb.append(" srednia A0: "+ averageA0).append(" srednia A5: " + averageA5));
        return sb.toString();
    }


    public int averageRssi(String deviceId, int signal){

        if (deviceId.equals("D0:F0:18:43:E0:A5")) {
            rssiListA5.add(signal);
            int sum = 0;
            for(int i : rssiListA5){
                sum += i;
            }
            averageA5 = sum/rssiListA5.size();
            return averageA5;

        } else if (deviceId.equals("D0:F0:18:43:E0:A0")){
           rssiListA0.add(signal);
            int sum = 0;
            for(int i : rssiListA0){
                sum += i;
            }
            averageA0 = sum/rssiListA0.size();
            return averageA0;
        }

        return 0;
    }

    public void cleanLists(){
        rssiListA0.clear();
        rssiListA5.clear();
        beaconLocations.clear();
    }

    public int getAverageA0() {
        return averageA0;
    }

    public int getAverageA5() {
        return averageA5;
    }
}
