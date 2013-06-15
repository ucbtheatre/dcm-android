package com.ucb.dcm.data;

import android.database.Cursor;
import android.text.TextUtils;
import com.androiddata.DBColumn;
import com.androiddata.DBObject;
import com.androiddata.DBTable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by kurtguenther on 6/6/13.
 */

@DBTable(tableName = "Show")
public class Show extends DBObject implements Serializable {

    @DBColumn(columnName = "id", dataType = DBColumn.DataType.INTEGER)
    public int ID;
    @DBColumn(columnName = "name")
    public String name;
    @DBColumn(columnName = "sort_name")
    public String sortName;
    @DBColumn(columnName = "promo")
    public String promo;
    @DBColumn(columnName = "city")
    public String city;
    @DBColumn(columnName = "performers")
    public String performers;

    @DBColumn(columnName = "favorite", dataType = DBColumn.DataType.BOOL)
    public boolean isFavorite;

    public Show(){ }

    public Show(Cursor c){
        super(c);
//        ID = c.getInt(c.getColumnIndex("id"));
//        name = c.getString(c.getColumnIndex("name"));
//        sortName = c.getString(c.getColumnIndex("sort_name"));
//        promo = c.getString(c.getColumnIndex("promo"));
//        city = c.getString(c.getColumnIndex("city"));
//        performers= c.getString(c.getColumnIndex("performers"));
    }


    public static Show fromJson(JSONObject json) throws JSONException{
        Show retVal = new Show();

        retVal.ID = json.getInt("id");
        retVal.promo = json.getString("promo_blurb");
        retVal.name = json.getString("show_name");
        retVal.city = json.getString("home_city");
        retVal.isFavorite = false;

        String sortName = retVal.name.toLowerCase();

        if(sortName.startsWith("'")){
            sortName = sortName.substring(Math.min(1, sortName.length()-1));
        }

        if(sortName.startsWith("\"")){
            sortName = sortName.substring(Math.min(1, sortName.length()-1));
        }

        if(sortName.startsWith("the")){
            sortName = sortName.substring(4);
        }

        retVal.sortName = sortName;

        JSONArray perfsJSON = json.getJSONArray("cast");
        String[] perfsArray = new String[perfsJSON.length()];
        for(int i = 0; i < perfsJSON.length(); i++){
            perfsArray[i] = (String) perfsJSON.get(i);
            perfsArray[i] = perfsArray[i].replace(",","");
        }

        retVal.performers = TextUtils.join(",", perfsArray);
        return retVal;
    }

    public static ArrayList<Show> getAll(android.database.sqlite.SQLiteDatabase db, String orderBy){
        Show s = new Show();
        ArrayList<Show> retVal = new ArrayList<Show>();
        Cursor c = db.query(s.getTableName(), s.getColumnNames(), null, null, null, null, orderBy);

        while(c.moveToNext())
        {
            retVal.add(new Show(c));
        }

        return retVal;
    }

    //We do this to eliminate the rogue "" empty performers and other junk.
    public ArrayList<String> getPerformers(){
        ArrayList<String> retVal = new ArrayList<String>();
        String[] arr = TextUtils.split(this.performers, ",");
        for(int i = 0; i < arr.length; i++){
            if(arr[i].length() > 0){
                retVal.add(arr[i]);
            }
        }
        return retVal;
    }

    public Cursor getPerformances(){
        return DBHelper.getSharedService().getWritableDatabase().rawQuery("SELECT * FROM Performance left join Venue v ON venue_id = v.id where show_id = ?", new String[]{Integer.toString(this.ID)});
    }

    public void addFavorite(){
        isFavorite = true;
        DBHelper.getSharedService().getWritableDatabase().update(getTableName(), getContentValues(), "id = ?", new String[]{Integer.toString(this.ID)});
    }

    public void removeFavorite(){
        isFavorite = false;
        DBHelper.getSharedService().getWritableDatabase().update(getTableName(), getContentValues(), "id = ?", new String[]{Integer.toString(this.ID)});
    }

    public static Show getById(Integer id){
        Show retVal = null;
        Show s = new Show();
        Cursor c = DBHelper.getSharedService().getWritableDatabase().query(s.getTableName(), s.getColumnNames(), "id = ?",  new String[]{Integer.toString(id)}, null, null, "sort_name");

        if(c.moveToFirst()){
            retVal = new Show(c);
        }

        return retVal;
    }
}
