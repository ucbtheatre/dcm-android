package com.ucb.dcm;

import android.app.Activity;
import com.ucb.dcm.data.DataService;
import com.ucb.dcm.data.Show;
import com.ucb.dcm.list.ShowsListAdapter;

import java.util.ArrayList;

/**
 * Created by kurtguenther on 6/15/13.
 */
public class FavoritesFragment extends  ShowsFragment {
    @Override
    protected ArrayList<Show> getData() {
        return DataService.getSharedService().getFavorites();
    }

    @Override
    public void onResume() {
        super.onResume();

        ArrayList<Show> shows = getData();
        mAdpt = new ShowsListAdapter(shows, getActivity().getLayoutInflater());
        setListAdapter(mAdpt);
    }
}
