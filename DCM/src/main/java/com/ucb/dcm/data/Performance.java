package com.ucb.dcm.data;

import android.database.Cursor;
import com.androiddata.DBColumn;
import com.androiddata.DBObject;
import com.androiddata.DBTable;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by kurtguenther on 6/6/13.
 */


@DBTable(tableName = "Performance")
public class Performance extends DBObject {

    @DBColumn(columnName = "id", dataType = DBColumn.DataType.INTEGER)
    public int id;

    @DBColumn(columnName = "show_id", dataType = DBColumn.DataType.INTEGER)
    public int show_id;

    @DBColumn(columnName = "venue_id", dataType = DBColumn.DataType.INTEGER)
    public int venue_id;

    @DBColumn(columnName = "start_date", dataType = DBColumn.DataType.INTEGER)
    public int start_date;

    @DBColumn(columnName = "end_date", dataType = DBColumn.DataType.INTEGER)
    public int end_date;

    @DBColumn(columnName = "minutes", dataType = DBColumn.DataType.INTEGER)
    public int minutes;

    public Performance(){ }

    public Performance(Cursor c){
        super(c);
    }


    public static Performance fromJson(JSONObject json) throws JSONException {
        Performance retVal = new Performance();

        retVal.id = json.getInt("id");
        retVal.show_id = json.getInt("show_id");
        retVal.venue_id = json.getInt("venue_id");
        retVal.start_date = json.getInt("starttime");
        retVal.end_date = json.getInt("endtime");
        retVal.minutes = json.getInt("minutes");

        return retVal;
    }

    public static ArrayList<NowPerformance> getUpcomingShows (Date now) {
        ArrayList<NowPerformance> retVal = new ArrayList<NowPerformance>();

        ArrayList<Venue> venues = Venue.getAll(DBHelper.getSharedService().getReadableDatabase(), "id");

        long start_date = now.getTime() / (long) 1000;

        for(Venue v : venues){
            Cursor c = DBHelper.getSharedService().getReadableDatabase().rawQuery("SELECT * FROM performance p LEFT JOIN show s on p.show_id = s.id where venue_id = ? and end_date > ? ORDER BY end_date LIMIT 2", new String[]{Integer.toString(v.id), Long.toString(start_date)});
            while(c.moveToNext()){
                NowPerformance np = new NowPerformance();
                np.show_name = c.getString(c.getColumnIndex("s.name"));
                np.venue_name = v.name;
                np.start_date = c.getInt(c.getColumnIndex("p.start_date"));
                np.show_id = c.getInt(c.getColumnIndex("show_id"));
                retVal.add(np);
            }
        }

        return retVal;

//        String query = "select _id, start_date, venue_id, show_id " +
//                "FROM performance " +
//                "WHERE (" +
//                "   SELECT count(*) from PERFORMANCE as p" +
//                "   where p.venue_id = performance.venue_id and p.start_date <= performance.start_date" +
//                ") <= 2;";
//
//        String old_q = "SELECT p.startdate, v.name as venue_name, s.name as show_name from performance p LEFT JOIN show s on p.show_id = s.id LEFT JOIN venue v on p.venue_id = v.id order by p.start_date";
//
//        return DBHelper.getSharedService().getWritableDatabase().rawQuery(query, null);
    }
}
