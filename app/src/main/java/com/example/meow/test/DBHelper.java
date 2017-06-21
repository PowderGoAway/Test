package com.example.meow.test;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;



public class DBHelper extends SQLiteOpenHelper {

    private static String DB_PATH = "/data/data/com.example.meow.test/databases/";
    private static String DB_NAME = "DB.db";
    private SQLiteDatabase myDataBase;
    private final Context mContext;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, 1);
        this.mContext = context;
    }

    public void createDataBase() throws IOException {
        boolean dbExist = checkDataBase();

        if(dbExist){
        }else{
            this.getReadableDatabase();
            try {
                copyDataBase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    private boolean checkDataBase(){
        SQLiteDatabase checkDB = null;

        try{
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        }catch(SQLiteException e){
        }
        if(checkDB != null){
            checkDB.close();
        }
        return checkDB != null ? true : false;
    }

    private void copyDataBase() throws IOException{
        InputStream myInput = mContext.getAssets().open(DB_NAME);
        String outFileName = DB_PATH + DB_NAME;
        OutputStream myOutput = new FileOutputStream(outFileName);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
        }
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    public void openDataBase() throws SQLException {
        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
    }

    @Override
    public synchronized void close() {
        if(myDataBase != null)
            myDataBase.close();
        super.close();
    }


    public String searchDescription(Integer ID){
        openDataBase();
        String query = "select ActionID, Description from " + "Actions";
        Cursor cursor = myDataBase.rawQuery(query, null);
        Integer id;
        String description;
        description = "Not found";
        if (cursor.moveToFirst()){
            do {
                id = cursor.getInt(0);

                if (id == ID){
                    description = cursor.getString(1);
                    break;
                }
            }
            while (cursor.moveToNext());
        }
        return description;
    }

    public Rate searchRate (Integer ActID){
        openDataBase();
        String query = "select ActionID, MinRate, MaxRate from Actions";
        Cursor cursor = myDataBase.rawQuery(query, null);
        int id;
        Rate rate = new Rate();
        if (cursor.moveToFirst()){
            do {
                id = cursor.getInt(0);
                if (id == ActID){
                rate.setMinRate(cursor.getInt(1));
                rate.setMaxRate(cursor.getInt(2));
                }
            }
            while (cursor.moveToNext());
        }
        return rate;
    }


    public ArrayList<String> getProfName(){
        openDataBase();
        String query = "select ProfessionName from " + "Professions";
        Cursor cursor = myDataBase.rawQuery(query, null);
        ArrayList<String> arrList = new ArrayList<String>();
        String profName;
        if (cursor.moveToFirst()){
            do {
                profName = cursor.getString(0);
                arrList.add(profName);

            }
            while (cursor.moveToNext());
        }
        return arrList;
    }

    public Integer searchProfID(String prof){
        openDataBase();
        String query = "select ProfessionID, ProfessionName from " + "Professions";
        Cursor cursor = myDataBase.rawQuery(query, null);
        Integer id=0;
        String profession;
        if (cursor.moveToFirst()){
            do {
                profession = cursor.getString(1);

                if (profession.equals(prof)){
                    id = cursor.getInt(0);
                    break;
                }
            }
            while (cursor.moveToNext());
        }
        return id;
    }


    public ArrayList<Data> searchCoefficient (Integer ProfID){
        openDataBase();
        String query = "select ActionID, Coefficient from Signifance where ProfessionID = "+ProfID;
        Cursor cursor = myDataBase.rawQuery(query, null);
        ArrayList<Data> arrData = new ArrayList<>();
        if (cursor.moveToFirst()){
            do {
                Data data = new Data();
                    data.setActId(cursor.getInt(0));
                    data.setCoef(cursor.getInt(1));
                arrData.add(data);
            }
            while (cursor.moveToNext());
        }
        return arrData;
    }

    public ArrayList<Action> searchAction(Integer ProfID){
        openDataBase();
        String query = "select sf.ActionID, ac.Description from Signifance as sf, Actions as ac where sf.ActionID = ac.ActionID and sf.ProfessionID = "+ProfID;
        Cursor cursor = myDataBase.rawQuery(query,null);
        ArrayList<Action> arrAction = new ArrayList<>();
        if (cursor.moveToFirst()){
            do{
                Action action = new Action();
                action.setActID(cursor.getInt(0));
                action.setDescr(cursor.getString(1));
                arrAction.add(action);
            }
            while (cursor.moveToNext());
        }
        return arrAction;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
