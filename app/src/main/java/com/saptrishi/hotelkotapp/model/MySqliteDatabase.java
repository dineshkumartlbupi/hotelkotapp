package com.saptrishi.hotelkotapp.model;

        import android.content.ContentValues;
        import android.content.Context;
        import android.database.Cursor;
        import android.database.SQLException;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteException;
        import android.database.sqlite.SQLiteOpenHelper;
        import android.util.Log;
        import android.widget.Toast;

        import static com.saptrishi.hotelkotapp.model.MySqliteDBHelper.date_of_licence_expiry;

//import static com.saptrishi.sandeep_pc.livestock.database.MySqliteDBHelper.basic_id_auto;
//import static com.saptrishi.sandeep_pc.livestock.database.MySqliteDBHelper.org_id;


public class MySqliteDatabase {
    private Context context;
    private MySqliteDBHelper dbHelper ;

    public MySqliteDatabase(Context context){
        this.context = context;
        dbHelper = new MySqliteDBHelper(context);
    }

    //    public long insertOrgInfoTable(String orgId, String orgNameEng , String orgNameHindi){
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        ContentValues cv = new ContentValues();
//        cv.put(dbHelper.org_name_eng, orgNameEng);
//        cv.put(dbHelper.org_mobile , orgNameHindi);
//        return db.insertWithOnConflict(dbHelper.org_info_tbl, null, cv, SQLiteDatabase.CONFLICT_REPLACE);
//    }

    public long insertDeviceRegistrationInfoTable(String orgSiteCode, String orgHotelName , String phoneIMEI ,String orgHotelNumber, String expiryDate){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(dbHelper.org_Site_code, orgSiteCode);
        cv.put(dbHelper.org_Hotel_Name , orgHotelName);
        cv.put(dbHelper.org_Phone_IMEI, phoneIMEI);
        cv.put(dbHelper.org_Hotel_Number,orgHotelNumber);
        cv.put(date_of_licence_expiry, expiryDate);
        return db.insertWithOnConflict(dbHelper.device_reg_tbl, null, cv, SQLiteDatabase.CONFLICT_REPLACE);
    }


    public long insertkotListTable(String itenName, String amount , String quantity ,String rate, String Unit){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(dbHelper.kot_list_ITEMNAME, itenName);
        cv.put(dbHelper.kot_list_Amount , amount);
        cv.put(dbHelper.kot_list_Qty, quantity);
        cv.put(dbHelper.kot_list_Rate,rate);
        cv.put(dbHelper.kot_list_Unit, Unit);
        return db.insertWithOnConflict(dbHelper.kotListTable, null, cv, SQLiteDatabase.CONFLICT_REPLACE);
    }

    public Cursor fetchkotListTable(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "select * from " + dbHelper.kotListTable;
        Cursor cursor = db.rawQuery(sql , null);
        return cursor;
    }
    public int deletekotListTable()
    {
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        //db.execSQL("delete from "+dbHelper.rest_table_name);
        int getvalue=db.delete(dbHelper.kotListTable,null,null);
        Log.e("kot table is Deleted","kot table is Deleted");
        return getvalue;
    }

    public long insertRestaurantTable(String restCode, String itemCode , String quantity ,String rate, String amount,String Uname,String waiterCode,String tableNumber,String item_modifier){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(dbHelper.rest_Rest_code, restCode);
        cv.put(dbHelper.rest_uname_Name , Uname);
        cv.put(dbHelper.rest_Waiter_code, waiterCode);
        cv.put(dbHelper.rest_Item_Code,itemCode);
        cv.put(dbHelper.rest_Item_Quantity, quantity);
        cv.put(dbHelper.rest_Item_rate,rate);
        cv.put(dbHelper.rest_Item_Amount, amount);
        cv.put(dbHelper.rest_Table_Number,tableNumber);
        cv.put(dbHelper.rest_item_modifier,item_modifier);
        return db.insertWithOnConflict(dbHelper.rest_table_name, null, cv, SQLiteDatabase.CONFLICT_REPLACE);
    }

//    public long insertOrgInfoTable(String orgId, String orgNameEng , String orgNameHindi){
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        ContentValues cv = new ContentValues();
//        cv.put(dbHelper.org_name_eng, orgNameEng);
//        cv.put(dbHelper.org_mobile , orgNameHindi);
//        return db.insertWithOnConflict(dbHelper.org_info_tbl, null, cv, SQLiteDatabase.CONFLICT_REPLACE);
//    }

    public Cursor fetchRestaurantTable(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "select * from " + dbHelper.rest_table_name;
        Cursor cursor = db.rawQuery(sql , null);
        return cursor;
    }

//    public Cursor fetchOrgInfoTable(){
//        SQLiteDatabase db = dbHelper.getReadableDatabase();
//        String sql = "select * from " + dbHelper.org_info_tbl;
//        Cursor cursor = db.rawQuery(sql , null);
//        return cursor;
//    }

    public Cursor fetchrestTableonItemId(String itemId){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "select * from " + dbHelper.rest_table_name+" where rest_Item_Code='"+itemId+"'";
        Log.e("sql check",sql);
        Cursor cursor = db.rawQuery(sql , null);
        return cursor;
    }

//    public void deleteRestaurantTable(){
//        SQLiteDatabase db= dbHelper.getWritableDatabase();
//        db.execSQL("delete from "+ dbHelper.device_reg_tbl);
//    }

    public long insertIPInfoTable(String ipname , String appname){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(dbHelper.ipname, ipname);
        cv.put(dbHelper.appname , appname);
        return db.insertWithOnConflict(dbHelper.IP_info_tbl, null, cv, SQLiteDatabase.CONFLICT_REPLACE);
    }

    public Cursor fetchIPTable(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "select * from " + dbHelper.IP_info_tbl;
        Cursor cursor = db.rawQuery(sql , null);
        return cursor;
    }
    public Cursor fetchExpiryDate(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "select "+date_of_licence_expiry+" from " + dbHelper.device_reg_tbl;
        Cursor cursor = db.rawQuery(sql , null);
        return cursor;
    }

    public void deleteIPDetailTable(){
        SQLiteDatabase db= dbHelper.getWritableDatabase();
        db.execSQL("delete from "+ dbHelper.IP_info_tbl);

    }

    public int deleteRestaurantTable()
    {
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        //db.execSQL("delete from "+dbHelper.rest_table_name);
        int getvalue=db.delete(dbHelper.rest_table_name,null,null);
        Log.e("table is Deleted","table is Deleted");
        return getvalue;
    }
    public void deleteRestaurantTableOnID(String Itemcode)
    {
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        db.delete(dbHelper.rest_table_name,"rest_Item_Code='"+Itemcode+"'",null);
        Log.e("deleteItem","Item is Deleted");
    }

    public long updateRestaurantTable(String itemCode , String quantity , String amount){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new  ContentValues();
//            cv.put(dbHelper.rest_Rest_code, restCode);
//            cv.put(dbHelper.rest_uname_Name , Uname);
//            cv.put(dbHelper.rest_Waiter_code, waiterCode);
        cv.put(dbHelper.rest_Item_Code,itemCode);
        cv.put(dbHelper.rest_Item_Quantity, quantity);
//            cv.put(dbHelper.rest_Item_rate,rate);
        cv.put(dbHelper.rest_Item_Amount, amount);
//            cv.put(dbHelper.rest_Table_Number,tableNumber);
        Log.e("itemcodeitemcode",itemCode);
        Log.e("itemcodequantity",quantity);
        Log.e("itemcodeamount",amount);
        return db.update(dbHelper.rest_table_name, cv, "rest_Item_Code='"+itemCode+"'", null);


    }
    public long updateRestaurantTable(String itemCode , String item_modifier){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new  ContentValues();
//            cv.put(dbHelper.rest_Rest_code, restCode);
//            cv.put(dbHelper.rest_uname_Name , Uname);
//            cv.put(dbHelper.rest_Waiter_code, waiterCode);
        cv.put(dbHelper.rest_Item_Code,itemCode);
        cv.put(dbHelper.rest_item_modifier, item_modifier);
//            cv.put(dbHelper.rest_Item_rate,rate);

//            cv.put(dbHelper.rest_Table_Number,tableNumber);
        Log.e("itemcodeitemcode",itemCode);
        Log.e("item_modifier",item_modifier);

        return db.update(dbHelper.rest_table_name, cv, "rest_Item_Code='"+itemCode+"'", null);


    }

//    public long insertLoginDetailTable(String loginId, String loginOrgId, String userName , String userPass){
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        ContentValues cv = new ContentValues();
//        cv.put(dbHelper.login_id, loginId);
//        cv.put(dbHelper.logOrg_id, loginOrgId);
//        cv.put(dbHelper.user_name, userName);
//        cv.put(dbHelper.user_pass , userPass);
//        return db.insertWithOnConflict(dbHelper.login_detail_tbl, null, cv, SQLiteDatabase.CONFLICT_REPLACE);
//    }
//
//    public Cursor fetchLoginDetailTable(){
//        SQLiteDatabase db = dbHelper.getReadableDatabase();
//        String sql = "select * from " + dbHelper.login_detail_tbl;
//        Cursor cursor = db.rawQuery(sql , null);
//        return cursor;
//    }
//
//
//    public Cursor fetchLoginDetailTblForLogId(String uname){
//        SQLiteDatabase db = dbHelper.getReadableDatabase();
//        Cursor cursor1 = db.rawQuery("select "+dbHelper.login_id+" from "+dbHelper.login_detail_tbl+" where "+dbHelper.user_name+" = ?", new String []{uname});
//      return cursor1;
//    }
//
//    public Cursor getLogOrgIdCount(String orgId){
//        //Log.e("dbsql",orgId);
//        SQLiteDatabase db = dbHelper.getReadableDatabase();
//        Cursor cursor1 = db.rawQuery("select "+dbHelper.org_id+" from "+dbHelper.org_info_tbl+" where "+dbHelper.org_id+" = ?", new String []{orgId});
//        //Log.e("getvalue",cursor1.getColumnName(0));
//        return cursor1;
//    }
//    public Cursor getLogOrgidByNamePass(String name, String pass){
//        SQLiteDatabase db = dbHelper.getReadableDatabase();
//        Log.e("dbname:", name);
//        Log.e("dbpass:", pass);
//        Cursor cursor = db.rawQuery("SELECT "+dbHelper.logOrg_id+" FROM "+dbHelper.login_detail_tbl+" WHERE "+dbHelper.user_name+" = ? and "+dbHelper.user_pass+" = ?", new String[] {name,pass});
//        return cursor;
//
//    }
//
//    public Cursor fetchBasicInfoWhereFlag(){
//        SQLiteDatabase db = dbHelper.getReadableDatabase();
//        Cursor cursor = db.rawQuery("SELECT "+dbHelper.basic_id_auto+", "+dbHelper.animal_name+", "+dbHelper.animal_breed+", " +
//                ""+dbHelper.animal_location_details+", "+dbHelper.animal_milk_growth+", " +
//                ""+dbHelper.animal_age+", "+dbHelper.animal_tag1+", "+dbHelper.animal_tag2+", "+dbHelper.animal_tag3+", " +
//                ""+dbHelper.animal_tag4+", "+dbHelper.animal_pic+", "+dbHelper.org_id_+", "+dbHelper.animal_type_id+" " +
//                "FROM "+dbHelper.basic_registration_tbl+" WHERE "+dbHelper.flag+" = ?", new String []{"false"});
//        Log.e("fetchBasicoWhereFlag:", ""+cursor.getCount());
//        return cursor;
//    }
//    public Cursor fetchBasicInfoTblDuplicateTag(String tagToIndeDupli){
//        SQLiteDatabase db = dbHelper.getReadableDatabase();
//        Cursor cursor = db.rawQuery("SELECT * FROM "+dbHelper.basic_registration_tbl+" WHERE "+dbHelper.animal_tag1+" = ? or "+dbHelper.animal_tag2+" = ? or "+dbHelper.animal_tag3+" = ? or "+dbHelper.animal_tag4+" = ?", new String []{tagToIndeDupli,tagToIndeDupli,tagToIndeDupli,tagToIndeDupli});
//        Log.e("fetchBasicTagDuplicate:", ""+cursor.getCount());
//        return cursor;
//    }
//    public void deleteLoginDetailTable(){
//        SQLiteDatabase db= dbHelper.getWritableDatabase();
//        db.execSQL("delete from "+ dbHelper.login_detail_tbl);
//    }
//
//    public long insertBasicTable(String animType , String animName, String animBreed,
//                                 String animLocationDetails, String milkGrowth, String animalAge,
//                                 String tag1, String tag2, String tag3, String tag4,
//                                 byte[] anim_pic, String orgID_, String animTypeID, String flag) throws SQLiteException {
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        ContentValues cv = new  ContentValues();
//        cv.put(dbHelper.animal_type, animType);
//        cv.put(dbHelper.animal_name, animName);
//        cv.put(dbHelper.animal_breed, animBreed);
//        cv.put(dbHelper.animal_location_details, animLocationDetails);
//        cv.put(dbHelper.animal_milk_growth, milkGrowth);
//        cv.put(dbHelper.animal_age, animalAge);
//        cv.put(dbHelper.animal_tag1, tag1);
//        cv.put(dbHelper.animal_tag2, tag2);
//        cv.put(dbHelper.animal_tag3, tag3);
//        cv.put(dbHelper.animal_tag4, tag4);
//        cv.put(dbHelper.animal_pic, anim_pic);
//        cv.put(dbHelper.org_id_, orgID_);
//        cv.put(dbHelper.animal_type_id, animTypeID);
//        cv.put(dbHelper.flag, flag);
//       return db.insertWithOnConflict(dbHelper.basic_registration_tbl,null, cv, SQLiteDatabase.CONFLICT_REPLACE);
//    }
//
//    public long updateBasicTblforFlag(String flag , String maxID){
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        ContentValues cv = new  ContentValues();
//        cv.put(dbHelper.flag, flag);
//        return db.update(dbHelper.basic_registration_tbl, cv, "basic_id_auto="+maxID, null);
//    }
//
//    public long updateBasicTable(String id, String animType , String animName, String animBreed,
//                                 String animLocationDetails, String milkGrowth, String animalAge,
//                                 String tag1, String tag2, String tag3, String tag4,
//                                 byte[] anim_pic) throws SQLiteException {
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        ContentValues cv = new  ContentValues();
//        //cv.put(dbHelper.basic_id_auto, id);
//        cv.put(dbHelper.animal_type, animType);
//        cv.put(dbHelper.animal_name, animName);
//        cv.put(dbHelper.animal_breed, animBreed);
//        cv.put(dbHelper.animal_location_details, animLocationDetails);
//        cv.put(dbHelper.animal_milk_growth, milkGrowth);
//        //cv.put(dbHelper.animal_age, animalAge);
//        cv.put(dbHelper.animal_tag1, tag1);
//        cv.put(dbHelper.animal_tag2, tag2);
//        cv.put(dbHelper.animal_tag3, tag3);
//        cv.put(dbHelper.animal_tag4, tag4);
//        //cv.put(dbHelper.animal_pic, anim_pic);
//        return db.update(dbHelper.basic_registration_tbl, cv, "basic_id_auto="+id, null);
//    }
//
//    public long updateBasicTableForLocation(String newLocation, String sqliteId){
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        ContentValues cv = new ContentValues();
//        cv.put(dbHelper.animal_location_details, newLocation);
//        return db.update(dbHelper.basic_registration_tbl, cv, "basic_id_auto="+sqliteId, null);
//    }
//
//    public Cursor fetchBasicInfoTable() {
//        SQLiteDatabase db = dbHelper.getReadableDatabase();
//        String sql = "select * from " + dbHelper.basic_registration_tbl;
//        //Cursor cursor =db.rawQuery("select count(*)as id from "+helper.TABLE_NAME2+"", null);
//        Cursor cursor = db.rawQuery(sql, null);
//        // Log.e("cursorDB", ""+ cursor);
//        return cursor;
//    }
//
//    public Cursor fetchMissingAnimFromBasic(String sqliteId){
//        SQLiteDatabase db = dbHelper.getReadableDatabase();
//
//        Cursor cursor = db.rawQuery( "select * FROM "+dbHelper.basic_registration_tbl+" WHERE  "+dbHelper.basic_id_auto+" = ?" , new String []{sqliteId});
//Log.e("cursorCountMissing:", ""+cursor.getCount());
//        return cursor;
//    }
//
//    public Cursor getMaxId(){
//        SQLiteDatabase db = dbHelper.getReadableDatabase();
//        String sql = "select max("+ MySqliteDBHelper.basic_id_auto+") from " + dbHelper.basic_registration_tbl;
//       // Cursor cursor = db.rawQuery(sql, null);
//        Cursor cursor = db.query(dbHelper.basic_registration_tbl, new String [] {"MAX("+ MySqliteDBHelper.basic_id_auto+")"}, null, null, null, null, null);
//
//        return cursor;
//    }
//
//    public void deleteBasicInfoTable(){
//        SQLiteDatabase db= dbHelper.getWritableDatabase();
//        db.execSQL("delete from "+ dbHelper.basic_registration_tbl);
//    }
//
//    public long insertTagReadTable(String serverId , String type, String name ,String breed , String t1, String t2, String t3, String t4){
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        ContentValues cv = new ContentValues();
//        cv.put(dbHelper.r_server_id, serverId);
//        cv.put(dbHelper.r_anim_type, type);
//        cv.put(dbHelper.r_anim_name, name);
//        cv.put(dbHelper.r_anim_breed, breed);
//        cv.put(dbHelper.r_tag1, t1);
//        cv.put(dbHelper.r_tag2, t2);
//        cv.put(dbHelper.r_tag3, t3);
//        cv.put(dbHelper.r_tag4, t4);
//        return db.insertWithOnConflict(dbHelper.read_tags_tbl, null, cv, SQLiteDatabase.CONFLICT_REPLACE);
//    }
//
//    public Cursor fetchTagsReadTable(){
//        SQLiteDatabase db = dbHelper.getReadableDatabase();
//        String sql = "select * from " + dbHelper.read_tags_tbl;
//        //Cursor cursor =db.rawQuery("select count(*)as id from "+helper.TABLE_NAME2+"", null);
//        Cursor cursor = db.rawQuery(sql, null);
//        // Log.e("cursorDB", ""+ cursor);
//        return cursor;
//    }
//
//    public void deleteTagsReadTable(){
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        db.execSQL("delete from "+ dbHelper.read_tags_tbl);
//    }
//
//    public long insertAnimalAttend(String serverId, String locationId, String currentTime, String currentDate, String orgID){
//        SQLiteDatabase db= dbHelper.getWritableDatabase();
//        ContentValues cv= new ContentValues();
//        cv.put(dbHelper.attn_server_id, serverId);
//        cv.put(dbHelper.attn_location_id, locationId);
//        cv.put(dbHelper.current_time, currentTime);
//        cv.put(dbHelper.current_date, currentDate);
//        cv.put(dbHelper.send_attn_org_ID, orgID);
//        return db.insertWithOnConflict(dbHelper.send_anim_attand_tbl, null, cv, SQLiteDatabase.CONFLICT_REPLACE);
//    }
//
//    public synchronized Cursor fetchAnimalAttend(){
//        SQLiteDatabase db= dbHelper.getReadableDatabase();
//        String sql= "select * from "+dbHelper.send_anim_attand_tbl;
//        Cursor cursor= db.rawQuery(sql, null);
//        return cursor;
//    }
//
//    public void deleteAnimalAttnd(){
//        SQLiteDatabase db= dbHelper.getWritableDatabase();
//        db.execSQL("delete from "+ dbHelper.send_anim_attand_tbl);
//    }
//
//    public long insertUpdateBasicTable(String serverId, String animType , String animName, String animBreed,
//                                 String animLocationDetails, String milkGrowth, String animalAge,
//                                 String tag1, String tag2, String tag3, String tag4,
//                                 byte[] anim_pic) throws SQLiteException {
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        ContentValues cv = new  ContentValues();
//        cv.put(dbHelper.server_id, serverId);
//        cv.put(dbHelper.updt_animal_type, animType);
//        cv.put(dbHelper.updt_animal_name, animName);
//        cv.put(dbHelper.updt_animal_breed, animBreed);
//        cv.put(dbHelper.updt_animal_location_details, animLocationDetails);
//        cv.put(dbHelper.updt_animal_milk_growth, milkGrowth);
//        cv.put(dbHelper.updt_animal_age, animalAge);
//        cv.put(dbHelper.updt_animal_tag1, tag1);
//        cv.put(dbHelper.updt_animal_tag2, tag2);
//        cv.put(dbHelper.updt_animal_tag3, tag3);
//        cv.put(dbHelper.updt_animal_tag4, tag4);
//        cv.put(dbHelper.updt_animal_pic, anim_pic);
//        return db.insertWithOnConflict(dbHelper.update_basic_regist_tbl,null, cv, SQLiteDatabase.CONFLICT_REPLACE);
//    }
//
//    public Cursor fetchUpdateBasicInfoTable() {
//        SQLiteDatabase db = dbHelper.getReadableDatabase();
//        String sql = "select * from " + dbHelper.update_basic_regist_tbl;
//        //Cursor cursor =db.rawQuery("select count(*)as id from "+helper.TABLE_NAME2+"", null);
//        Cursor cursor = db.rawQuery(sql, null);
//        // Log.e("cursorDB", ""+ cursor);
//        return cursor;
//    }
//
//    public void deleteUpdateBasicInfoTable(){
//        SQLiteDatabase db= dbHelper.getWritableDatabase();
//        db.execSQL("delete from "+ dbHelper.update_basic_regist_tbl);
//    }
//
//    public long insertAnimalTypeSpinnerTbl(String animTypeHindi, String idAanimType, String typeOrgId){
//        SQLiteDatabase db= dbHelper.getWritableDatabase();
//        ContentValues cv= new ContentValues();
//        cv.put(dbHelper.type_animal_type_hindi,animTypeHindi);
//        cv.put(dbHelper.type_id_animal_type,idAanimType);
//        cv.put(dbHelper.type_OrgId,typeOrgId);
//        return db.insertWithOnConflict(dbHelper.bind_anim_type_tbl, null, cv, SQLiteDatabase.CONFLICT_REPLACE);
//    }
//
//    public Cursor fetchAnimalTypeSpinnerTbl() {
//        SQLiteDatabase db = dbHelper.getReadableDatabase();
//        String sql = "select * from " + dbHelper.bind_anim_type_tbl;
//        //Cursor cursor =db.rawQuery("select count(*)as id from "+helper.TABLE_NAME2+"", null);
//        Cursor cursor = db.rawQuery(sql, null);
//        // Log.e("cursorDB", ""+ cursor);
//        return cursor;
//    }
//
//    public void deleteAnimalTypeSpinnerTbl() {
//    SQLiteDatabase db = dbHelper.getWritableDatabase();
//    db.execSQL("delete from " + dbHelper.bind_anim_type_tbl);
//
//}
//
//    public long insertAnimalLocationSpinnerTbl(String idAnimalLocation, String bindLocation, String bindOrgId){
//        SQLiteDatabase db= dbHelper.getWritableDatabase();
//        ContentValues cv= new ContentValues();
//        cv.put(dbHelper.id_animal_location,idAnimalLocation);
//        cv.put(dbHelper.bind_location,bindLocation);
//        cv.put(dbHelper.bind_OrgId,bindOrgId);
//        return db.insertWithOnConflict(dbHelper.bind_location_tbl, null, cv, SQLiteDatabase.CONFLICT_REPLACE);
//    }
//
//    public Cursor fetchAnimalLocationSpinnerTbl() {
//        SQLiteDatabase db = dbHelper.getReadableDatabase();
//        String sql = "select * from " + dbHelper.bind_location_tbl;
//        //Cursor cursor =db.rawQuery("select count(*)as id from "+helper.TABLE_NAME2+"", null);
//        Cursor cursor = db.rawQuery(sql, null);
//        // Log.e("cursorDB", ""+ cursor);
//        return cursor;
//    }
//
//    public void deleteAnimalLocationSpinnerTbl() {
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        db.execSQL("delete from " + dbHelper.bind_location_tbl);
//
//    }
//
//    public long insertReadTagForLocationTransferTbl(String serverId, String sqliteId, String locationId, String animType, String animName,
//                                                    String animBreed, String animLocation, String tag1, String tag2, String tag3, String tag4){
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        ContentValues cv = new ContentValues();
//        cv.put(dbHelper.read_tags_for_trans_server_id, serverId);
//        cv.put(dbHelper.read_tags_for_trans_sqlite_id, sqliteId);
//        cv.put(dbHelper.read_tags_for_trans_location_id, locationId);
//        cv.put(dbHelper.read_tags_for_trans_anim_type, animType);
//        cv.put(dbHelper.read_tags_for_trans_anim_name, animName);
//        cv.put(dbHelper.read_tags_for_trans_anim_breed, animBreed);
//        cv.put(dbHelper.read_tags_for_trans_anim_location, animLocation);
//        cv.put(dbHelper.read_tags_for_trans_tag1, tag1);
//        cv.put(dbHelper.read_tags_for_trans_tag2, tag2);
//        cv.put(dbHelper.read_tags_for_trans_tag3, tag3);
//        cv.put(dbHelper.read_tags_for_trans_tag4, tag4);
//        return db.insertWithOnConflict(dbHelper.read_tags_for_transfer_anim_location_tbl, null, cv, SQLiteDatabase.CONFLICT_REPLACE);
//    }
//
//    public Cursor fetchReadTagForLocationTransferTbl() {
//        SQLiteDatabase db = dbHelper.getReadableDatabase();
//        String sql = "select * from " + dbHelper.read_tags_for_transfer_anim_location_tbl;
//        Cursor cursor = db.rawQuery(sql, null);
//        return cursor;
//    }
//
//    public void deleteReadTagForLocationTransferTbl() {
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        db.execSQL("delete from " + dbHelper.read_tags_for_transfer_anim_location_tbl);
//    }
//
//    public long insertTransferAnimLocationTbl(String serverIdFLAG, String sqliteIdFLAG, String locationId, String serverId, String sqliteId, String orgId){
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        ContentValues cv = new ContentValues();
//        cv.put(dbHelper.tran_server_id_flag, serverIdFLAG);
//        cv.put(dbHelper.tran_sqlite_id_flag, sqliteIdFLAG);
//        cv.put(dbHelper.tran_location_id, locationId);
//        cv.put(dbHelper.tran_server_id, serverId);
//        cv.put(dbHelper.tran_sqlite_id, sqliteId);
//        cv.put(dbHelper.tran_org_id, orgId);
//        return db.insertWithOnConflict(dbHelper.transfer_anim_location_tbl, null, cv, SQLiteDatabase.CONFLICT_REPLACE);
//    }
//
//    public Cursor fetchTransferAnimLocationTbl(){
//        SQLiteDatabase db = dbHelper.getReadableDatabase();
//        String sql = "select * from "+ dbHelper.transfer_anim_location_tbl;
//        Cursor cursor = db.rawQuery(sql, null);
//        return cursor;
//    }
//
//    public void deleteTransferAnimLocationTbl(){
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        db.execSQL("delete from " + dbHelper.transfer_anim_location_tbl);
//    }
//
//    public long insertMilkProductionTbl(String milkProdQty, String serverId){
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        ContentValues cv = new ContentValues();
//        cv.put(dbHelper.milk_prod_qty, milkProdQty);
//        cv.put(dbHelper.milk_prod_server_id, serverId);
//        return db.insertWithOnConflict(dbHelper.milk_production_tbl, null, cv, SQLiteDatabase.CONFLICT_REPLACE);
//    }
//
//    public Cursor fetchMilkProdTbl(){
//        SQLiteDatabase db = dbHelper.getReadableDatabase();
//        String sql = "select * from "+ dbHelper.milk_production_tbl;
//        Cursor cursor = db.rawQuery(sql, null);
//        return cursor;
//    }
//
//    public void deleteMilkProdTbl(){
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        db.execSQL("delete from " + dbHelper.milk_production_tbl);
//    }
//
//    public long insertAdoptiveInfoTbl(String serverId, String name, String mobile, String proffId, String residenceAddress){
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        ContentValues cv = new ContentValues();
//        cv.put(dbHelper.adoptive_server_id, serverId);
//        cv.put(dbHelper.adoptive_name, name);
//        cv.put(dbHelper.adoptive_mobile, mobile);
//        cv.put(dbHelper.adoptive_proff_id, proffId);
//        cv.put(dbHelper.adoptive_residence_address, residenceAddress);
//        return db.insertWithOnConflict(dbHelper.adoptive_profile_tbl, null, cv, SQLiteDatabase.CONFLICT_REPLACE);
//    }
//
//    public Cursor fetchAdoptiveInfoTbl(){
//        SQLiteDatabase db = dbHelper.getReadableDatabase();
//        String sql = "select * from "+ dbHelper.adoptive_profile_tbl;
//        Cursor cursor = db.rawQuery(sql, null);
//        return cursor;
//    }
//
//    public void deleteAdoptiveInfoTbl(){
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        db.execSQL("delete from " + dbHelper.adoptive_profile_tbl);
//    }
//
//    public long insertAnimDeathInfoTbl(String serverId, String animDisease, String animInspection, String animInspePersonName, String animDeathCertificate){
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        ContentValues cv = new ContentValues();
//        cv.put(dbHelper.anim_death_server_id, serverId);
//        cv.put(dbHelper.anim_disease_, animDisease);
//        cv.put(dbHelper.anim_inspection_, animInspection);
//        cv.put(dbHelper.anim_inspection_person_name, animInspePersonName);
//        cv.put(dbHelper.anim_death_certificate, animDeathCertificate);
//        return db.insertWithOnConflict(dbHelper.anim_death_profile_tbl, null, cv, SQLiteDatabase.CONFLICT_REPLACE);
//    }
//
//    public Cursor fetchAnimDeathInfoTbl(){
//        SQLiteDatabase db = dbHelper.getReadableDatabase();
//        String sql = "select * from "+ dbHelper.anim_death_profile_tbl;
//        Cursor cursor = db.rawQuery(sql, null);
//        return cursor;
//    }
//
//    public void deleteAnimDeathInfoTbl(){
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        db.execSQL("delete from " + dbHelper.anim_death_profile_tbl);
//    }
//
//    public long insertAnimImmunInfoTbl(String diseaseTypeHi, String vaccinationId, String vaccinationNameHI){
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        ContentValues cv = new ContentValues();
//        cv.put(dbHelper.anim_immun_diseaseTypeHI, diseaseTypeHi);
//        cv.put(dbHelper.anim_immun_vaccinationId, vaccinationId);
//        cv.put(dbHelper.anim_immun_vaccinationNameHI, vaccinationNameHI);
//
//        return db.insertWithOnConflict(dbHelper.anim_immunization_tbl, null, cv, SQLiteDatabase.CONFLICT_REPLACE);
//    }
//
//    public Cursor fetchAnimImmunInfoTbl(){
//        SQLiteDatabase db = dbHelper.getReadableDatabase();
//        String sql = "select * from "+ dbHelper.anim_immunization_tbl;
//        Cursor cursor = db.rawQuery(sql, null);
//        return cursor;
//    }
//
//    public void deleteAnimImmunInfoTbl(){
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        db.execSQL("delete from " + dbHelper.anim_immunization_tbl);
//    }
//
//    public long insertAnimPostImmunInfoTbl(String serverId, String inocullationDate, String vaccinationID){
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        ContentValues cv = new ContentValues();
//        cv.put(dbHelper.anim_post_immunization_server_id, serverId);
//        cv.put(dbHelper.anim_post_immun_inoculation_date, inocullationDate);
//        cv.put(dbHelper.anim_post_immun_vaccinationId, vaccinationID);
//
//        return db.insertWithOnConflict(dbHelper.anim_post_immunization_tbl, null, cv, SQLiteDatabase.CONFLICT_REPLACE);
//    }
//
//    public Cursor fetchAnimPostImmunInfoTbl(){
//        SQLiteDatabase db = dbHelper.getReadableDatabase();
//        String sql = "select * from "+ dbHelper.anim_post_immunization_tbl;
//        Cursor cursor = db.rawQuery(sql, null);
//        return cursor;
//    }
//
//    public void deleteAnimPostImmunInfoTbl(){
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        db.execSQL("delete from " + dbHelper.anim_post_immunization_tbl);
//    }
//
//    public long insertAnimDiseaseInfoTbl(String serverId, String diseaseName, String inspectinPersonName, String treatment, String remark){
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        ContentValues cv = new ContentValues();
//        cv.put(dbHelper.anim_dis_server_id, serverId);
//        cv.put(dbHelper.anim_dis_name, diseaseName);
//        cv.put(dbHelper.anim_dis_inspectn_person_name, inspectinPersonName);
//        cv.put(dbHelper.anim_dis_treatment, treatment);
//        cv.put(dbHelper.anim_dis_remark, remark);
//
//        return db.insertWithOnConflict(dbHelper.anim_disease_profile_tbl, null, cv, SQLiteDatabase.CONFLICT_REPLACE);
//    }
//
//    public Cursor fetchAnimDiseaseInfoTbl(){
//        SQLiteDatabase db = dbHelper.getReadableDatabase();
//        String sql = "select * from "+ dbHelper.anim_disease_profile_tbl;
//        Cursor cursor = db.rawQuery(sql, null);
//        return cursor;
//    }
//
//    public void deleteAnimDiseaseInfoTbl(){
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        db.execSQL("delete from " + dbHelper.anim_disease_profile_tbl);
//    }
//
//    public long insertAnimTotalPresentReport(String totalPresent, String totalAvailable, String location){
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        ContentValues cv = new ContentValues();
//        cv.put(dbHelper.anim_total_pres_present, totalPresent);
//        cv.put(dbHelper.anim_total_pres_available, totalAvailable);
//        cv.put(dbHelper.anim_total_pres_location, location);
//
//        return db.insertWithOnConflict(dbHelper.anim_total_present_report_tbl, null, cv, SQLiteDatabase.CONFLICT_REPLACE);
//    }
//
//    public Cursor fetchAnimTotalPresentReport(){
//        SQLiteDatabase db = dbHelper.getReadableDatabase();
//        String sql = "select * from "+ dbHelper.anim_total_present_report_tbl;
//        Cursor cursor = db.rawQuery(sql, null);
//        return cursor;
//    }
//
//    public void deleteAnimTotalPresentReport(){
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        db.execSQL("delete from " + dbHelper.anim_total_present_report_tbl);
//    }
//
//    public long insertAnimShadePresentReport(String shadPresent, String shadAvailable, String shadlocation){
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        ContentValues cv = new ContentValues();
//        cv.put(dbHelper.anim_shade_total_present, shadPresent);
//        cv.put(dbHelper.anim_shade_total_available, shadAvailable);
//        cv.put(dbHelper.anim_shade_location, shadlocation);
//
//        return db.insertWithOnConflict(dbHelper.anim_shade_report_tbl, null, cv, SQLiteDatabase.CONFLICT_REPLACE);
//    }
//
//    public Cursor fetchAnimShadePresentReport(){
//        SQLiteDatabase db = dbHelper.getReadableDatabase();
//        String sql = "select * from "+ dbHelper.anim_shade_report_tbl;
//        Cursor cursor = db.rawQuery(sql, null);
//        return cursor;
//    }
//
//    public void deleteAnimShadePresentReport(){
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        db.execSQL("delete from " + dbHelper.anim_shade_report_tbl);
//    }


}

class MySqliteDBHelper extends SQLiteOpenHelper{
    private Context context;

    static final int db_version = 9; // strictly recommended to must not be changed

    static final String db_name = "HotelKot.bd";// strictly recommended to must not be changed

//    static final String org_info_tbl = "org_info";
//    static final String org_id_auto = "org_id_auto";
//    static final String org_name_eng = "org_name_eng";
//    static final String org_mobile = "org_mobile";


    static final String IP_info_tbl = "ipdatabase";
    static final String IP_id_auto = "ip_id_auto";
    static final String ipname = "ip";
    static final String appname = "appname";


    static final String device_reg_tbl="device_reg";
    static final String device_reg_id_auto ="device_reg_id_auto";
    static final String org_Site_code="org_Site_code";
    static final String org_Hotel_Name="org_Hotel_Name";
    static final String org_Phone_IMEI="org_Phone_IMEI";
    static final String date_of_licence_expiry="date_of_licence_expiry";
    static final String org_Hotel_Number="org_Hotel_Number";

    static final String rest_table_name="RestaurantTable";
    static final String rest_table_srno_id_auto ="rest_table_srno_id_auto";
    static final String rest_Rest_code="rest_Rest_code";
    static final String rest_uname_Name="rest_uname_Name";
    static final String rest_Waiter_code="rest_Waiter_code";
    static final String rest_Item_Code="rest_Item_Code";
    static final String rest_Item_Quantity="rest_Item_Quantity";
    static final String rest_Item_rate="rest_Item_rate";
    static final String rest_Item_Amount="rest_Item_Amount";
    static final String rest_Table_Number="rest_Table_Number";
    static final String rest_item_modifier="item_modifier";



    static final String kotListTable="kotListTable";
    static final String kotListTable_id_auto ="kotListTable_id_auto";
    static final String kot_list_ITEMNAME="kot_list_ITEMNAME";
    static final String kot_list_Amount="kot_list_Amount";
    static final String kot_list_Qty="kot_list_Qty";
    static final String kot_list_Rate="kot_list_Rate";
    static final String kot_list_Unit="kot_list_Unit";




//    static final String login_detail_tbl = "login_detail";
//    static final String login_id_auto= "login_id_auto";
//    static final String login_id = "logOrg_id";
//    static final String logOrg_id = "login_id";
//    static final String user_pass = "user_pass";
//    static final String user_name = "user_name";
//
//    static final String basic_registration_tbl = "basic_registration";
//    static final String basic_id_auto = "basic_id_auto";
//    static final String animal_type = "animal_type";
//    static final String animal_name = "animal_name";
//    static final String animal_breed = "animal_breed";
//    static final String animal_location_details = "animal_location_details";
//    static final String animal_milk_growth = "animal_milk_growth";
//    static final String animal_age = "animal_age";
//    static final String animal_tag1 = "animal_tag1";
//    static final String animal_tag2 = "animal_tag2";
//    static final String animal_tag3 = "animal_tag3";
//    static final String animal_tag4 = "animal_tag4";
//    static final String animal_pic = "animal_pic";
//    static final String org_id_ = "org_id_";
//    static final String animal_type_id = "animal_type_id";
//    static final String flag = "flag";
//
//
//
//    static final String health_profile_tbl = "health_profile";
//    static final String health_id_auto = "health_id_auto";
//    static final String health_id = "id";
//    static final String animal_location_info = "animal_location_info";
//    static final String disease_information = "health_information";
//    static final String treatment_information = "treatment_information";
//    static final String animal_inoculation_done = "animal_inoculation_done";
//    static final String animal_inoculation_remain = "animal_inoculation_remain";
//
//    static final String read_tags_tbl = "read_tags";
//    static final String readtags_id_auto = "readtags_id_auto";
//    static final String r_server_id = "r_server_id";
//    static final String r_anim_name = "r_animla_name";
//    static final String r_anim_breed = "r_animal_breed";
//    static final String r_anim_type = "r_animal_type";
//    static final String r_tag1 = "r_tag1";
//    static final String r_tag2 = "r_tag2";
//    static final String r_tag3 = "r_tag3";
//    static final String r_tag4 = "r_tag4";
//
//    static final String send_anim_attand_tbl = "send_anim_attand";
//    static final String attn_id_auto = "attn_id_auto";
//    static final String attn_server_id = "attn_server_id";
//    static final String attn_location_id = "attn_location_id";
//    static final String current_time = "current_time";
//    static final String current_date = "current_date";
//    static final String send_attn_org_ID = "send_attn_org_ID";
//
//
//    static final String ownership_tbl = "ownership";
//    static final String ownership_id_auto = "ownership_id_auto";
//    static final String owner_id = "owner_id";
//    static final String organisation_name = "organisation_name";
//    static final String owner_name = "owner_name";
//
//    static final String update_basic_regist_tbl = "update_basic_regist";
//    static final String update_id_auto = "update_id_auto";
//    static final String server_id = "id";
//    static final String updt_animal_type = "updt_animal_type";
//    static final String updt_animal_name = "updt_animal_name";
//    static final String updt_animal_breed = "updt_animal_breed";
//    static final String updt_animal_location_details = "updt_animal_location_details";
//    static final String updt_animal_milk_growth = "updt_animal_milk_growth";
//    static final String updt_animal_age = "updt_animal_age";
//    static final String updt_animal_tag1 = "updt_animal_tag1";
//    static final String updt_animal_tag2 = "updt_animal_tag2";
//    static final String updt_animal_tag3 = "updt_animal_tag3";
//    static final String updt_animal_tag4 = "updt_animal_tag4";
//    static final String updt_animal_pic = "updt_animal_pic";
//
//
//    static final String bind_location_tbl = "bind_location";
//    static final String idanim_location_auto = "idanim_location_auto";
//    static final String id_animal_location = "id_animal_location";
//    static final String bind_location = "location";
//    static final String bind_OrgId = "OrgId";
//
//
//
//    static final String bind_anim_type_tbl = "bind_anim_type_tbl";
//    static final String type_id_auto = "type_id_auto";
//    static final String type_animal_type_hindi = "animal_type_hindi";
//    static final String type_id_animal_type = "id_animal_type";
//    static final String type_OrgId = "type_OrgId";
//
//
//    static final String transfer_anim_location_tbl = "transfer_anim_location_tbl";
//    static final String trans_id_auto = "trans_id_auto";
//    static final String tran_sqlite_id_flag = "tran_sqlite_id_flag";
//    static final String tran_server_id_flag = "tran_server_id_flag";
//    static final String tran_location_id = "tran_location_id";
//    static final String tran_server_id = "tran_server_id";
//    static final String tran_sqlite_id = "tran_sqlite_id";
//    static final String tran_org_id = "tran_org_id";
//
//
//    static final String read_tags_for_transfer_anim_location_tbl = "read_tags_for_transfer_anim_location_tbl";
//    static final String read_tags_for_trans_id_auto = "read_tags_for_trans_id_auto";
//    static final String read_tags_for_trans_server_id = "read_tags_for_trans_server_id";
//    static final String read_tags_for_trans_sqlite_id = "read_tags_for_trans_sqlite_id";
//    static final String read_tags_for_trans_location_id = "read_tags_for_trans_location_id";
//    static final String read_tags_for_trans_anim_type = "read_tags_for_trans_anim_type";
//    static final String read_tags_for_trans_anim_name = "read_tags_for_trans_anim_name";
//    static final String read_tags_for_trans_anim_breed = "read_tags_for_trans_anim_breed";
//    static final String read_tags_for_trans_anim_location = "read_tags_for_trans_anim_location";
//    static final String read_tags_for_trans_tag1 = "read_tags_for_trans_tag1";
//    static final String read_tags_for_trans_tag2 = "read_tags_for_trans_tag2";
//    static final String read_tags_for_trans_tag3 = "read_tags_for_trans_tag3";
//    static final String read_tags_for_trans_tag4 = "read_tags_for_trans_tag4";
//
//
//    static final String milk_production_tbl = "milk_production_tbl";
//    static final String milk_prod_id_auto = "milk_prod_id_auto";
//    static final String milk_prod_qty = "milk_prod_qty";
//    static final String milk_prod_server_id = "milk_prod_server_id";
//
//
//
//    static final String adoptive_profile_tbl = "adoptive_profile_tbl";
//    static final String adoptive_id_auto = "adopt_id_auto";
//    static final String adoptive_server_id = "adoptive_server_id";
//    static final String adoptive_name = "adoptive_name";
//    static final String adoptive_mobile = "adoptive_mobile";
//    static final String adoptive_proff_id = "adoptive_proff_id";
//    static final String adoptive_residence_address = "adoptive_residence_address";
//
//    static final String anim_death_profile_tbl = "anim_death_profile_tbl";
//    static final String anim_death_id_auto = "anim_death_id_auto";
//    static final String anim_death_server_id = "anim_death_server_id";
//    static final String anim_disease_ = "anim_disease_";
//    static final String anim_inspection_ = "anim_inspection_";
//    static final String anim_inspection_person_name = "anim_inspection_person_name";
//    static final String anim_death_certificate = "anim_death_certificate";
//
//    static final String anim_immunization_tbl = "anim_immunization_tbl";
//    static final String anim_immunization_id_auto = "anim_immunization_id_auto";
//    static final String anim_immun_diseaseTypeHI = "anim_immun_diseaseTypeHI";
//    static final String anim_immun_vaccinationId = "anim_immun_vaccinationId";
//    static final String anim_immun_vaccinationNameHI = "anim_immun_vaccinationNameHI";
//
//    static final String anim_post_immunization_tbl = "anim_post_immunization_tbl";
//    static final String anim_post_immunization_id_auto = "anim_post_immunization_id_auto";
//    static final String anim_post_immunization_server_id = "anim_post_immunization_server_id";
//    static final String anim_post_immun_inoculation_date = "anim_post_immun_inoculation_date";
//    static final String anim_post_immun_vaccinationId = "anim_immun_vaccinationId";
//
//
//    static final String anim_disease_profile_tbl = "anim_disease_profile_tbl";
//    static final String anim_dis_id_auto = "anim_dis_id_auto";
//    static final String anim_dis_server_id = "anim_dis_server_id";
//    static final String anim_dis_name = "anim_dis_name";
//    static final String anim_dis_inspectn_person_name = "anim_dis_inspectn_person_name";
//    static final String anim_dis_treatment = "anim_dis_treatment";
//    static final String anim_dis_remark = "anim_dis_remark";
//
//
//    static final String anim_total_present_report_tbl = "anim_total_present_report_tbl";
//    static final String anim_total_pres_id_auto = "anim_total_pres_id_auto";
//    static final String anim_total_pres_present = "anim_total_pres_present";
//    static final String anim_total_pres_available = "anim_total_pres_available";
//    static final String anim_total_pres_location = "anim_total_pres_location";
//
//
//
//    static final String anim_shade_report_tbl = "anim_shade_report_tbl";
//    static final String anim_shade_id_auto = "anim_shade_id_auto";
//    static final String anim_shade_total_present = "anim_shade_total_present";
//    static final String anim_shade_total_available = "anim_shade_total_available";
//    static final String anim_shade_location = "anim_shade_location";
//
//
//    static final String anim_milk_production_report_tbl = "anim_milk_production_report_tbl";
//    static final String anim_milk_prod_report_id_auto = "anim_milk_prod_report_id_auto";
//    static final String anim_milk_prod_report_total_milk = "anim_milk_prod_report_total_milk";

//    static final String create_table_anim_total_present_report_tbl = "CREATE TABLE IF NOT EXISTS "+anim_total_present_report_tbl+"("+anim_total_pres_id_auto+" INTEGER PRIMARY KEY AUTOINCREMENT, "+anim_total_pres_present+" VARCHAR, "+anim_total_pres_available+" VARCHAR, "+anim_total_pres_location+" VARCHAR);";
//
//    static final String create_table_anim_shade_report_tbl = "CREATE TABLE IF NOT EXISTS "+anim_shade_report_tbl+"("+anim_shade_id_auto+" INTEGER PRIMARY KEY AUTOINCREMENT, "+anim_shade_total_present+" VARCHAR, "+anim_shade_total_available+" VARCHAR, "+anim_shade_location+" VARCHAR);";
//
//
//    static final String create_table_anim_disease_profile_tbl = "CREATE TABLE IF NOT EXISTS "+anim_disease_profile_tbl+"("+anim_dis_id_auto+" INTEGER PRIMARY KEY AUTOINCREMENT, "+anim_dis_server_id+" VARCHAR, "+anim_dis_name+" VARCHAR, "+anim_dis_inspectn_person_name+" VARCHAR, "+anim_dis_treatment+" VARCHAR, "+anim_dis_remark+" VARCHAR);";
//
//
//
//    static final String create_table_anim_post_immunization_tbl = "CREATE TABLE IF NOT EXISTS "+anim_post_immunization_tbl+"("+anim_post_immunization_id_auto+" INTEGER PRIMARY KEY AUTOINCREMENT, "+anim_post_immunization_server_id+" VARCHAR, "+anim_post_immun_inoculation_date+" VARCHAR, "+anim_post_immun_vaccinationId+" VARCHAR);";
//
//    static final String create_table_anim_immunization_tbl = "CREATE TABLE IF NOT EXISTS "+anim_immunization_tbl+"("+anim_immunization_id_auto+" INTEGER PRIMARY KEY AUTOINCREMENT, "+anim_immun_diseaseTypeHI+" VARCHAR, "+anim_immun_vaccinationId+" VARCHAR, "+anim_immun_vaccinationNameHI+" VARCHAR);";
//
//
//    static final String create_table_anim_death_profile_tbl = "CREATE TABLE IF NOT EXISTS "+anim_death_profile_tbl+"("+anim_death_id_auto+" INTEGER PRIMARY KEY AUTOINCREMENT, "+anim_death_server_id+" VARCHAR, "+anim_disease_+" VARCHAR, "+anim_inspection_+" VARCHAR, "+anim_inspection_person_name+" VARCHAR, "+anim_death_certificate+" VARCHAR);";
//
//
//
//    static final String create_table_adoptive_profile_tbl = "CREATE TABLE IF NOT EXISTS "+adoptive_profile_tbl+"("+adoptive_id_auto+" INTEGER PRIMARY KEY AUTOINCREMENT, "+adoptive_server_id+" VARCHAR, "+adoptive_name+" VARCHAR, "+adoptive_mobile+" VARCHAR, "+adoptive_proff_id+" VARCHAR, "+adoptive_residence_address+" VARCHAR);";
//
//
//    static final String create_table_milk_production_tbl = "CREATE TABLE IF NOT EXISTS "+milk_production_tbl+"("+milk_prod_id_auto+" INTEGER PRIMARY KEY AUTOINCREMENT, "+milk_prod_qty+" VARCHAR, "+milk_prod_server_id+" VARCHAR);";
//
//    static final String create_table_transfer_anim_location_tbl = "CREATE TABLE IF NOT EXISTS "+transfer_anim_location_tbl+"("+trans_id_auto+" INTEGER PRIMARY KEY AUTOINCREMENT, "+tran_sqlite_id_flag+" VARCHAR, "+tran_server_id_flag+" VARCHAR, "+tran_location_id+" VARCHAR, "+tran_server_id+" VARCHAR, "+tran_sqlite_id+" VARCHAR, "+tran_org_id+" VARCHAR);";
//
//
//    static final String create_table_read_tags_for_transfer_anim_location_tbl = "CREATE TABLE IF NOT EXISTS "+read_tags_for_transfer_anim_location_tbl+"("+read_tags_for_trans_id_auto+" INTEGER PRIMARY KEY AUTOINCREMENT, "+read_tags_for_trans_server_id+" VARCHAR, "+read_tags_for_trans_sqlite_id+" VARCHAR, "+read_tags_for_trans_location_id+" VARCHAR, "+read_tags_for_trans_anim_type+" VARCHAR, "+read_tags_for_trans_anim_name+" VARCHAR, " +
//            ""+read_tags_for_trans_anim_breed+" VARCHAR, "+read_tags_for_trans_anim_location+" VARCHAR, "+read_tags_for_trans_tag1+" VARCHAR, "+read_tags_for_trans_tag2+" VARCHAR, "+read_tags_for_trans_tag3+" VARCHAR, "+read_tags_for_trans_tag4+" VARCHAR);";
//
//
//    static final String create_table_bind_location_tbl = "CREATE TABLE IF NOT EXISTS "+bind_location_tbl+"("+idanim_location_auto+" INTEGER PRIMARY KEY AUTOINCREMENT, "+id_animal_location+" VARCHAR, "+bind_location+" VARCHAR, "+bind_OrgId+" VARCHAR);";
//    static final String create_table_bind_anim_type_tbl = "CREATE TABLE IF NOT EXISTS "+bind_anim_type_tbl+"("+type_id_auto+" INTEGER PRIMARY KEY AUTOINCREMENT, "+type_animal_type_hindi+" VARCHAR, "+type_id_animal_type+" VARCHAR, "+type_OrgId+" VARCHAR);";

    static final String create_table_device_reg_tbl = "CREATE TABLE IF NOT EXISTS "+device_reg_tbl+"("+device_reg_id_auto+" INTEGER PRIMARY KEY AUTOINCREMENT, "+org_Site_code+" VARCHAR, "+org_Hotel_Name+" VARCHAR,"+org_Phone_IMEI+" VARCHAR,"+org_Hotel_Number+" VARCHAR,"+date_of_licence_expiry+" VARCHAR);";
    static final String create_table_IP_info = "CREATE TABLE IF NOT EXISTS "+IP_info_tbl+"("+IP_id_auto+" INTEGER PRIMARY KEY AUTOINCREMENT, "+ipname+" VARCHAR, "+appname+" VARCHAR);";
 //   static final String create_table_org_info = "CREATE TABLE IF NOT EXISTS "+org_info_tbl+"("+org_id_auto+" INTEGER PRIMARY KEY AUTOINCREMENT, "+org_name_eng+" VARCHAR, "+org_mobile+" VARCHAR);";

    static final String create_table_restaurant_Table = "CREATE TABLE IF NOT EXISTS "+rest_table_name+"("+rest_table_srno_id_auto+" INTEGER PRIMARY KEY AUTOINCREMENT, "+rest_Rest_code+" VARCHAR,"+rest_uname_Name+" VARCHAR,"+rest_Waiter_code+" VARCHAR,"+rest_Item_Code+" VARCHAR,"+rest_Item_Quantity+" VARCHAR,"+rest_Item_rate+" VARCHAR,"+rest_Item_Amount+" VARCHAR,"+rest_Table_Number+" VARCHAR,"+rest_item_modifier+" VARCHAR);";

    static final String create_table_kotListTable = "CREATE TABLE IF NOT EXISTS "+kotListTable+"("+kotListTable_id_auto+" INTEGER PRIMARY KEY AUTOINCREMENT, "+kot_list_ITEMNAME+" VARCHAR, "+kot_list_Amount+" VARCHAR,"+kot_list_Qty+" VARCHAR,"+kot_list_Rate+" VARCHAR,"+kot_list_Unit+" VARCHAR);";


//    static final String create_table_login = "CREATE TABLE IF NOT EXISTS "+login_detail_tbl+"("+login_id_auto+" INTEGER PRIMARY KEY AUTOINCREMENT, "+login_id+" VARCHAR, "+logOrg_id+" VARCHAR, "+user_name+" VARCHAR, "+user_pass+" VARCHAR);";
//
//    static final String create_table_basic = "CREATE TABLE IF NOT EXISTS "+basic_registration_tbl+"("+basic_id_auto+" INTEGER PRIMARY KEY AUTOINCREMENT, "+animal_type+" VARCHAR, "+animal_name+" NVARCHAR, " +
//            ""+animal_breed+" VARCHAR, "+animal_location_details+" VARCHAR, "+animal_milk_growth+" VARCHAR, "+animal_age+" VARCHAR, "+animal_tag1+" VARCHAR, "+animal_tag2+" VARCHAR, "+animal_tag3+" VARCHAR, "+animal_tag4+" VARCHAR, "+animal_pic+" BLOB NOT NULL, "+org_id_+" VARCHAR, "+animal_type_id+" VARCHAR, "+flag+" VARCHAR);";
//
//    static final String create_table_update_basic = "CREATE TABLE IF NOT EXISTS "+update_basic_regist_tbl+"("+update_id_auto+" INTEGER PRIMARY KEY AUTOINCREMENT, "+server_id+" VARCHAR, "+updt_animal_type+" VARCHAR, "+updt_animal_name+" NVARCHAR, " +
//            ""+updt_animal_breed+" VARCHAR, "+updt_animal_location_details+" VARCHAR, "+updt_animal_milk_growth+" VARCHAR, "+updt_animal_age+" VARCHAR, "+updt_animal_tag1+" VARCHAR, "+updt_animal_tag2+" VARCHAR, "+updt_animal_tag3+" VARCHAR," +
//            ""+updt_animal_tag4+" VARCHAR, "+updt_animal_pic+" BLOB NOT NULL);";
//
//
//   static final String create_table_send_anim_attand=  "CREATE TABLE IF NOT EXISTS "+send_anim_attand_tbl+"("+attn_id_auto+" INTEGER PRIMARY KEY AUTOINCREMENT, "+attn_server_id+" VARCHAR, "+attn_location_id+" VARCHAR, "+current_time+" VARCHAR, "+current_date+" VARCHAR, "+send_attn_org_ID+" VARCHAR);";
//
//    static final String create_table_health = "CREATE TABLE IF NOT EXISTS "+health_profile_tbl+"("+health_id_auto+" INTEGER PRIMARY KEY AUTOINCREMENT, "+health_id+" INTEGER PRIMARY KEY AUTOINCREMENT, "+animal_location_info+" VARCHAR, " +
//            ""+disease_information+" NVARCHAR, "+treatment_information+" VARCHAR, "+animal_inoculation_done+" VARCHAR, "+animal_inoculation_remain+" VARCHAR);";
//
//    static final String create_table_read_tags = "CREATE TABLE IF NOT EXISTS "+read_tags_tbl+"("+readtags_id_auto+" INTEGER PRIMARY KEY AUTOINCREMENT, "+r_server_id+" VARCHAR, "+r_anim_type+" VARCHAR, "+r_anim_name+" VARCHAR, "+r_anim_breed+" VARCHAR, "+r_tag1+" VARCHAR, "+r_tag2+" VARCHAR, "+r_tag3+" VARCHAR, "+r_tag4+" VARCHAR);";
//
//    static final String create_table_ownership = "CREATE TABLE IF NOT EXISTS "+ownership_tbl+"("+ownership_id_auto+" INTEGER PRIMARY KEY AUTOINCREMENT, "+owner_id+" INTEGER PRIMARY KEY AUTOINCREMENT, "+organisation_name+" VARCHAR, "+owner_name+" VARCHAR);";


    //    static final String drop_table_basic = "DROP TABLE IF EXISTS "+basic_registration_tbl;
//    static final String drop_table_health_profile = "DROP TABLE IF EXISTS "+health_profile_tbl;
//    static final String drop_tableread_tags = "DROP TABLE IF EXISTS "+read_tags_tbl;
//    static final String drop_table_attn_send = "DROP TABLE IF EXISTS "+send_anim_attand_tbl;
//    static final String drop_table_ownership = "DROP TABLE IF EXISTS "+ownership_tbl;
    static final String drop_table_IP_info = "DROP TABLE IF EXISTS "+IP_info_tbl;
    static final String drop_table_kotListTable = "DROP TABLE IF EXISTS "+kotListTable;
   // static final String drop_table_org_info = "DROP TABLE IF EXISTS "+org_info_tbl;
    static final String drop_table_device_reg_tbl = "DROP TABLE IF EXISTS "+device_reg_tbl;
    static final String drop_table_Restaurant_table="DROP TABLE IF EXISTS "+rest_table_name;
//    static final String drop_table_login = "DROP TABLE IF EXISTS "+login_detail_tbl;
//    static final String drop_table_update_basic = "DROP TABLE IF EXISTS "+update_basic_regist_tbl;
//    static final String drop_table_bind_anim_type_tbl = "DROP TABLE IF EXISTS "+bind_anim_type_tbl;
//    static final String drop_table_bind_location_tbl = "DROP TABLE IF EXISTS "+bind_location_tbl;
//    static final String drop_table_read_tags_for_transfer_anim_location_tbl = "DROP TABLE IF EXISTS "+read_tags_for_transfer_anim_location_tbl;
//    static final String drop_table_transfer_anim_location_tbl = "DROP TABLE IF EXISTS "+transfer_anim_location_tbl;
//    static final String drop_table_milk_production_tbl = "DROP TABLE IF EXISTS "+milk_production_tbl;
//    static final String drop_table_adoptive_profile_tbl = "DROP TABLE IF EXISTS "+adoptive_profile_tbl;
//    static final String drop_table_anim_death_profile_tbl = "DROP TABLE IF EXISTS "+anim_death_profile_tbl;
//    static final String drop_table_anim_immunization_tbl = "DROP TABLE IF EXISTS "+anim_immunization_tbl;
//    static final String drop_table_anim_post_immunization_tbl = "DROP TABLE IF EXISTS "+anim_post_immunization_tbl;
//    static final String drop_table_anim_disease_profile_tbl = "DROP TABLE IF EXISTS "+anim_disease_profile_tbl;
//    static final String drop_table_anim_total_present_report_tbl = "DROP TABLE IF EXISTS "+anim_total_present_report_tbl;
//    static final String drop_table_anim_shade_report_tbl = "DROP TABLE IF EXISTS "+anim_shade_report_tbl;

    public MySqliteDBHelper(Context context) {
        super(context, db_name, null, db_version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try{
            //db.execSQL(create_table_basic);
           // db.execSQL(create_table_org_info);
            db.execSQL(create_table_IP_info);
            db.execSQL(create_table_device_reg_tbl);
            db.execSQL(create_table_restaurant_Table);
            db.execSQL(create_table_kotListTable);
//            db.execSQL(create_table_login);
//            db.execSQL(create_table_update_basic);
//            db.execSQL(create_table_send_anim_attand);
//            db.execSQL(create_table_read_tags);
//            db.execSQL(create_table_bind_anim_type_tbl);
//            db.execSQL(create_table_bind_location_tbl);
//            db.execSQL(create_table_read_tags_for_transfer_anim_location_tbl);
//            db.execSQL(create_table_transfer_anim_location_tbl);
//            db.execSQL(create_table_milk_production_tbl);
//            db.execSQL(create_table_adoptive_profile_tbl);
//            db.execSQL(create_table_anim_death_profile_tbl);
//            db.execSQL(create_table_anim_immunization_tbl);
//            db.execSQL(create_table_anim_post_immunization_tbl);
//            db.execSQL(create_table_anim_disease_profile_tbl);
//            db.execSQL(create_table_anim_total_present_report_tbl);
//            db.execSQL(create_table_anim_shade_report_tbl);

            Toast.makeText(context, "tables created successfully",Toast.LENGTH_LONG).show();
        }catch (SQLException e){
            e.printStackTrace();
            Toast.makeText(context,"un-successful:" + e.getMessage(),Toast.LENGTH_LONG).show();
            Log.e("un-successful:", e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {

            db.execSQL(drop_table_IP_info);
            db.execSQL(drop_table_device_reg_tbl);
            db.execSQL(drop_table_Restaurant_table);
            db.execSQL(drop_table_kotListTable);

            onCreate(db);
            Toast.makeText(context,"onUpgrade is called..",Toast.LENGTH_LONG).show();
        }catch (Exception e){
            e.getMessage();
        }
    }
}

