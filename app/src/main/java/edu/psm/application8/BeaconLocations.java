package edu.psm.application8;

import java.util.ArrayList;
import java.util.List;

public class BeaconLocations {
    private final String deviceId;
    private final List<Integer> signals;


    public BeaconLocations(String deviceId, int initialSignal) {
        this.deviceId = deviceId;
        this.signals = new ArrayList<>();
        this.signals.add(initialSignal);
    }

    public void updateSignal(int signal) {
        signals.add(signal);
    }

    public String getDeviceId() {
        return deviceId;
    }

    public List<Integer> getSignals() {
        return signals;
    }
}
