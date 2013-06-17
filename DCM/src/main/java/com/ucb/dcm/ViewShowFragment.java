package com.ucb.dcm;

import android.os.Bundle;
import android.view.Menu;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.view.MenuItem;
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
        int show_id = getIntent().getIntExtra(SHOW_KEY, 0);

        //We refresh the data because the source list is out of date
        mShow = Show.getById(show_id);
        setListAdapter(new ShowAdapter(getLayoutInflater(), mShow));
    }

    @Override
    public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
        getSupportMenuInflater().inflate(R.menu.show, menu);
        MenuItem fav = menu.findItem(R.id.show_favorite);
        if(mShow.isFavorite){
            fav.setIcon(R.drawable.ic_action_favorite);
        }
        else{
            fav.setIcon(R.drawable.ic_action_not_favorite);
        }
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch(item.getItemId()){
            case R.id.show_favorite:
                if(!mShow.isFavorite){
                    Toast.makeText(this, "Favorite Added.", Toast.LENGTH_SHORT).show();
                    mShow.addFavorite();
                    invalidateOptionsMenu();
                }
                else{
                    mShow.removeFavorite();
                    Toast.makeText(this, "Favorite Removed.", Toast.LENGTH_SHORT).show();
                    invalidateOptionsMenu();
                }

                break;

        }
        return super.onMenuItemSelected(featureId, item);
    }
}
