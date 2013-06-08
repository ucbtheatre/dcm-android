package com.ucb.dcm.data;

import android.database.Cursor;
import com.androiddata.DBColumn;
import com.androiddata.DBObject;
import com.androiddata.DBTable;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by kurtguenther on 6/8/13.
 */
@DBTable(tableName = "Venue")
public class Venue extends DBObject {

    @DBColumn(columnName = "id", dataType = DBColumn.DataType.INTEGER)
    public int id;

    @DBColumn(columnName = "name")
    public String name;

    @DBColumn(columnName = "address")
    public String address;

    @DBColumn(columnName = "directions")
    public String directions;

    @DBColumn(columnName = "image")
    public String image;

    @DBColumn(columnName = "gmaps")
    public String gmaps;

    @DBColumn(columnName = "url")
    public String url;


    public Venue(){}

    public Venue(Cursor c){
        super(c);
    }

    public static Venue fromJson(JSONObject json) throws JSONException{
        Venue retVal = new Venue();

        retVal.id = json.getInt("id");
        retVal.name = json.getString("name");
        retVal.address = json.getString("address");
        retVal.directions = json.getString("directions");
        retVal.image = json.getString("image");
        retVal.gmaps = json.getString("gmaps");
        retVal.url = json.getString("url");

        return retVal;
    }

    public static ArrayList<Venue> getAll(android.database.sqlite.SQLiteDatabase db, String orderBy){
        Venue s = new Venue();
        ArrayList<Venue> retVal = new ArrayList<Venue>();
        Cursor c = db.query(s.getTableName(), s.getColumnNames(), null, null, null, null, orderBy);

        while(c.moveToNext())
        {
            retVal.add(new Venue(c));
        }

        return retVal;
    }

    @Override
    public String toString() {
        return name;
    }
}
