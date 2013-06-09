package com.ucb.dcm;

import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TextView;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.app.SherlockListActivity;
import com.ucb.dcm.data.DBHelper;
import com.ucb.dcm.data.DataService;
import com.ucb.dcm.data.Show;
import com.ucb.dcm.list.ShowAdapter;

/**
 * Created by kurtguenther on 6/9/13.
 */
public class ViewShowFragment extends SherlockListActivity {

    public static final String SHOW_KEY = "show_key";
    Show mShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        setTitle("Show");

        //Hook up the list.
        mShow = (Show) getIntent().getSerializableExtra(SHOW_KEY);
        setListAdapter(new ShowAdapter(getLayoutInflater(), mShow));
    }
}
