package edu.psm.application8;

import android.os.Build;

import androidx.annotation.RequiresApi;

import org.altbeacon.beacon.Beacon;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LocationService {

    private final List<BeaconLocations> beaconLocations;

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
}
