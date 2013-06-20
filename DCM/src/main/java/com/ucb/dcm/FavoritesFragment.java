package com.ucb.dcm;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import com.ucb.dcm.data.DataService;
import com.ucb.dcm.data.Show;
import com.ucb.dcm.list.FavoritesListAdapter;
import com.ucb.dcm.list.ShowsListAdapter;

import java.util.ArrayList;

/**
 * Created by kurtguenther on 6/15/13.
 */
public class FavoritesFragment extends ShowsFragment {
    protected String getEmptyText() {
        return "No Favorites.";
    }

    @Override
    public void onResume() {
        super.onResume();

        Cursor c = DataService.getSharedService().getFavorites();
        setListAdapter(new FavoritesListAdapter(c, getActivity()));
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Intent displayShow = new Intent(getActivity(), ViewShowFragment.class);
        Cursor c = (Cursor) getListAdapter().getItem(position);
        displayShow.putExtra(ViewShowFragment.SHOW_KEY, c.getInt(c.getColumnIndex("show_id")));
        startActivity(displayShow);
    }
}
