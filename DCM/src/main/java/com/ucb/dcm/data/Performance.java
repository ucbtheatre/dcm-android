package com.ucb.dcm.data;

import android.database.Cursor;
import com.androiddata.DBColumn;
import com.androiddata.DBObject;
import com.androiddata.DBTable;
import org.json.JSONException;
import org.json.JSONObject;

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
}
