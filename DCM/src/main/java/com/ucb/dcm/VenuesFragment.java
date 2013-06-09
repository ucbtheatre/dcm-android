package com.ucb.dcm;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.actionbarsherlock.app.SherlockListFragment;
import com.ucb.dcm.data.DataService;
import com.ucb.dcm.data.Show;
import com.ucb.dcm.data.Venue;
import com.ucb.dcm.list.ShowsListAdapter;
import com.ucb.dcm.R;

import java.util.ArrayList;

/**
 * Created by kurtguenther on 6/8/13.
 */
public class VenuesFragment extends SherlockListFragment {
    ArrayAdapter<Venue> mAdpt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ArrayList<Venue> venues = DataService.getSharedService().getVenues();
        mAdpt = new ArrayAdapter<Venue>(getActivity(),R.layout.list_venue,R.id.venue_name, venues);
        setListAdapter(mAdpt);

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Intent displayVenue = new Intent(getActivity(), DisplayVenueActivity.class);
        Venue venue = (Venue) getListAdapter().getItem(position);
        displayVenue.putExtra(DisplayVenueActivity.VENUE_KEY, venue);
        startActivity(displayVenue);
    }

}
