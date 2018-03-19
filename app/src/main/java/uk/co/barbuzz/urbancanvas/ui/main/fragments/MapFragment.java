package uk.co.barbuzz.urbancanvas.ui.main.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;


/**
 * Simple map fragment that extends the google map support fragment
 */
public class MapFragment extends SupportMapFragment {

    public interface OnGoogleMapFragmentListener {
        void onMapReady(GoogleMap map);
    }

    private static final String DEFAULT_LAT_LNG = MapFragment.class.getSimpleName() + "_default_lat_lng_extra";

    private OnMapReadyCallback mCallback;

    public static MapFragment newInstance() {
        MapFragment fragment = new MapFragment();
        Bundle bundle = new Bundle();
        //bundle.putParcelable(DEFAULT_LAT_LNG, defaultLatLng);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallback = (OnMapReadyCallback) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().getClass().getName() + " must implement OnGoogleMapFragmentListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        if (mCallback != null) {
            getMapAsync(mCallback);
        }
        return view;
    }

}
