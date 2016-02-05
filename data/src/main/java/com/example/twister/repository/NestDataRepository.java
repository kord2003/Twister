package com.example.twister.repository;

import com.example.twister.model.AccessToken;
import com.example.twister.model.Device;
import com.example.twister.model.SmokeDetector;
import com.example.twister.model.SmokeDetectorRepresentation;
import com.example.twister.model.Structure;
import com.example.twister.model.StructureRepresentation;
import com.example.twister.model.Thermostat;
import com.example.twister.model.ThermostatRepresentation;
import com.example.twister.model.mapper.DeviceMapper;
import com.example.twister.model.mapper.StructureMapper;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.GenericTypeIndicator;
import com.firebase.client.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subjects.BehaviorSubject;
import timber.log.Timber;

public class NestDataRepository implements NestRepository, ValueEventListener, Firebase.AuthListener {
    private String FIREBASE_URL = "https://developer-api.nest.com";
    private static final String STRUCTURES = "structures";
    private static final String DEVICES = "devices";
    private static final String THERMOSTATS = "thermostats";
    private static final String SMOKE_ALARMS = "smoke_co_alarms";
    private static final String CAMERAS = "cameras";

    private final StructureMapper structureMapper;
    private final DeviceMapper deviceMapper;

    //private BehaviorSubject<Structure> selectedStructureSubject;

    private BehaviorSubject<List<Structure>> structuresSubject;
    private BehaviorSubject<List<Device>> deviceSubject;

    private Structure lastSelectedStructure;
    private Subscription loadStructuresSubscription;
    private Firebase firebase;

    private boolean isSubscribed;
    private final Gson gson;

    @Inject
    public NestDataRepository(StructureMapper structureMapper, DeviceMapper deviceMapper) {
        this.structureMapper = structureMapper;
        this.deviceMapper = deviceMapper;
        //this.selectedStructureSubject = BehaviorSubject.create();
        this.structuresSubject = BehaviorSubject.create();
        this.deviceSubject = BehaviorSubject.create();
        this.gson = new GsonBuilder().create();
    }


    @Override
    public synchronized Observable<Structure> registerForSelectedStructureUpdates() {
        /*if (lastSelectedStructure == null && structures != null && structures.size() > 0) {
            setSelectedStructure(structures.get(0));
        }
        return selectedStructureSubject.asObservable();*/
        return null;
    }

    @Override
    public synchronized Observable<List<Structure>> registerForStructuresUpdates(AccessToken accessToken) {
        Timber.d("registerForStructuresUpdates");
        if (!isSubscribed/*loadStructuresSubscription == null || loadStructuresSubscription.isUnsubscribed()*/) {
            Timber.d("no subscription in progress, starting new one");
            authenticate(accessToken);
            /*loadStructuresSubscription = loadStructuresObservable().subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new DefaultSubscriber<List<Structure>>() {
                        private long before;
                        private long after;

                        @Override
                        public void onStart() {
                            before = System.currentTimeMillis();
                            super.onStart();
                        }

                        @Override
                        public void onError(Throwable throwable) {
                            Timber.e("structuresSubject.onError");
                            structuresSubject.onError(throwable);
                        }

                        @Override
                        public void onNext(List<Structure> data) {
                            Timber.d("structuresSubject.onNext: " + data.size());
                            // Caching structures data
                            structures.clear();
                            structures.addAll(data);
                            structuresSubject.onNext(structures);
                        }

                        @Override
                        public void onCompleted() {
                            structuresSubject.onCompleted();
                            after = System.currentTimeMillis();
                            Timber.d("structuresSubject.onCompleted: " + (after - before) + "ms");
                        }
                    });*/
        }
        return structuresSubject.asObservable();
    }

    @Override
    public Observable<List<Device>> registerForDevicesUpdates() {
        return deviceSubject.asObservable();
    }

    @Override
    public synchronized void setSelectedStructure(Structure structure) {
        /*if (structure != null && !structure.equals(lastSelectedStructure)) {
            lastSelectedStructure = structure;
            selectedStructureSubject.onNext(structure);
        }*/
    }

    @Override
    public Observable<Structure> updateStructure(Structure structure) {
        return null;
        /*return authApi.putStructure(structure.getId(), null, structureMapper.toStructureData(structure)).map(new Func1<StructureRepresentation, Structure>() {
            @Override
            public Structure call(StructureRepresentation structureRepresentation) {
                return structureMapper.transform(structureRepresentation);
            }
        });*/
    }

    private synchronized Firebase getFirebase() {
        if (firebase == null) {
            Firebase.goOffline();
            Firebase.goOnline();
            firebase = new Firebase(FIREBASE_URL);
        }

        return firebase;
    }

    private void authenticate(AccessToken token) {
        Timber.v("Authenticating...");
        getFirebase().auth(token.token, this);
    }

    private void subscribeUpdates() {
        Timber.d("Subscribing to updates...");
        getFirebase().addValueEventListener(this);
        isSubscribed = true;
    }

    private void unsubscribeUpdates() {
        Timber.d("Unsubscribing from updates...");
        getFirebase().removeEventListener(this);
    }

    @Override
    public void onDataChange(final DataSnapshot dataSnapshot) {
        Timber.d("onDataChange: " + dataSnapshot);
        Observable.create(
                new Observable.OnSubscribe<Boolean>() {
                    @Override
                    public void call(Subscriber<? super Boolean> subscriber) {
                        final Map<String, Object> values = dataSnapshot.getValue(StringObjectMapIndicator.INSTANCE);
                        for (Map.Entry<String, Object> entry : values.entrySet()) {
                            notifyUpdateListeners(entry);
                        }
                        subscriber.onNext(true);
                        subscriber.onCompleted();
                    }
                })
                .subscribeOn(Schedulers.computation())
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Timber.e(throwable, "Parse error");
                    }
                });
    }

    @Override
    public void onCancelled(FirebaseError firebaseError) {
        Timber.d("onCancelled: " + firebaseError.getMessage());
    }

    @Override
    public void onAuthError(FirebaseError firebaseError) {
        Timber.d("onAuthError: " + firebaseError.getMessage());
    }

    @Override
    public void onAuthSuccess(Object o) {
        Timber.d("onAuthSuccess: " + o);
        subscribeUpdates();
    }

    @Override
    public void onAuthRevoked(FirebaseError firebaseError) {
        Timber.d("onAuthRevoked: " + firebaseError.getMessage());
    }

    private static class StringObjectMapIndicator extends GenericTypeIndicator<Map<String, Object>> {
        static final StringObjectMapIndicator INSTANCE = new StringObjectMapIndicator();
    }

    private void notifyUpdateListeners(Map.Entry<String, Object> entry) {
        final Map<String, Object> value = (Map<String, Object>) entry.getValue();
        switch (entry.getKey()) {
            case STRUCTURES: {
                updateStructures(value);
                break;
            }
            case DEVICES: {
                updateDevices(value);
                break;
            }
        }
    }

    private void updateStructures(Map<String, Object> structuresMap) {
        List<Structure> structures = new ArrayList<>();
        for (Map.Entry<String, Object> entry : structuresMap.entrySet()) {
            final Map<String, Object> value = (Map<String, Object>) entry.getValue();
            Timber.d("updateStructures entry: " + entry.getKey());


            StructureRepresentation structureRepresentation = parseStructureRepresentation(value);
            if (structureRepresentation != null) {
                Structure structure = structureMapper.transform(structureRepresentation);
                Timber.v("Update: structure: " + structure);
                structures.add(structure);
            }
        }
        structuresSubject.onNext(structures);
    }

    private void updateDevices(Map<String, Object> devicesBundle) {
        List<Device> devices = new ArrayList<>();
        for (Map.Entry<String, Object> devicesTypedCollection : devicesBundle.entrySet()) {
            Timber.d("updateDevices devices: " + devicesTypedCollection.getKey());
            switch (devicesTypedCollection.getKey()) {
                case THERMOSTATS:
                    handleThermostats(devices, (Map<String, Object>) devicesTypedCollection.getValue());
                    break;
                case SMOKE_ALARMS:
                    handleSmokeAlarms(devices, (Map<String, Object>) devicesTypedCollection.getValue());
                    break;
                case CAMERAS:
                    break;
            }
        }
        deviceSubject.onNext(devices);
    }

    private void handleSmokeAlarms(List<Device> devices, Map<String, Object> smokeDetectors) {
        for (Map.Entry<String, Object> smokeDetectorEntry : smokeDetectors.entrySet()) {
            Timber.d("updateDevices smokeDetectorId: " + smokeDetectorEntry.getKey());
            final Map<String, Object> smokeDetector = (Map<String, Object>) smokeDetectorEntry.getValue();
            SmokeDetectorRepresentation smokeDetectorRepresentation = parseSmokeDetectorRepresentation(smokeDetector);
            if (smokeDetectorRepresentation != null) {
                SmokeDetector device = (SmokeDetector) deviceMapper.transform(smokeDetectorRepresentation);
                Timber.v("updateDevices: smokeDetector = " + device);
                devices.add(device);
            }
        }
    }

    private void handleThermostats(List<Device> devices, Map<String, Object> thermostats) {
        for (Map.Entry<String, Object> thermostatEntry : thermostats.entrySet()) {
            Timber.d("updateDevices thermostatId: " + thermostatEntry.getKey());
            final Map<String, Object> thermostat = (Map<String, Object>) thermostatEntry.getValue();
            ThermostatRepresentation thermostatRepresentation = parseThermostatRepresentation(thermostat);
            if (thermostatRepresentation != null) {
                Thermostat device = (Thermostat) deviceMapper.transform(thermostatRepresentation);
                Timber.v("updateDevices: thermostat = " + device);
                devices.add(device);
            }
        }
    }

    private ThermostatRepresentation parseThermostatRepresentation(Map<String, Object> value) {
        ThermostatRepresentation representation = null;
        try {
            representation = gson.fromJson(new JSONObject(value).toString(), ThermostatRepresentation.class);
        } catch (Exception e) {
            Timber.d("ThermostatRepresentation parse error");
        }

        return representation;
    }

    private SmokeDetectorRepresentation parseSmokeDetectorRepresentation(Map<String, Object> value) {
        SmokeDetectorRepresentation representation = null;
        try {
            representation = gson.fromJson(new JSONObject(value).toString(), SmokeDetectorRepresentation.class);
        } catch (Exception e) {
            Timber.d("SmokeDetectorRepresentation parse error");
        }

        return representation;
    }

    private StructureRepresentation parseStructureRepresentation(Map<String, Object> value) {
        StructureRepresentation representation = null;
        try {
            representation = gson.fromJson(new JSONObject(value).toString(), StructureRepresentation.class);
        } catch (Exception e) {
            Timber.d("StructureRepresentation parse error");
        }

        return representation;
    }
}