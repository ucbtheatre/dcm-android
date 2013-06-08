package com.ucb.dcm.data;

import com.androiddata.DBColumn;
import com.androiddata.DBObject;
import com.androiddata.DBTable;

/**
 * Created by kurtguenther on 6/6/13.
 */

@DBTable(tableName = "Show")
public class Show extends DBObject {

    @DBColumn(columName = "id", dataType = DBColumn.DataType.INTEGER)
    public int ID;
    @DBColumn(columName = "name")
    public String name;
    @DBColumn(columName = "promo")
    public String promo;
    @DBColumn(columName = "city")
    public String city;

}
