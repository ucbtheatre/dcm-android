package com.ucb.dcm;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
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

    protected String getEmptyText() {
        return "No Favorites.";
    }


    @Override
    public void onResume() {
        super.onResume();

        ArrayList<Show> shows = getData();
        mAdpt = new ShowsListAdapter(shows, getActivity().getLayoutInflater());
        setListAdapter(mAdpt);
    }
}
