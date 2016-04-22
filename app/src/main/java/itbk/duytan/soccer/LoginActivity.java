package itbk.duytan.soccer;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class LoginActivity extends SQLiteOpenHelper {
    private static String DB_PATH = "/data/data/itbk.duytan.soccer/databases/";
    private static String DB_NAME = "cndd.sqlite";
    private SQLiteDatabase mDatabase;
    private Context mContext;


    public LoginActivity(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB_NAME, null, 1);
        mContext=context;
        creatDatabase();
    }
    public void addAccount(int id, String user, String pass){
        openDatabase();
        ContentValues cv = new ContentValues();
        cv.put("user_id", id);
        cv.put("username", user);
        cv.put("password", pass);
        mDatabase.insert("user_profiles", null, cv);
        closeDatabase();
    }

    private void closeDatabase() {
        if (mDatabase != null) {
            mDatabase.close();
        }
    }

    private void creatDatabase() {
        if(checkDatabase()) openDatabase();

        else copyDatabase();
    }

    private void copyDatabase() {
        try {
            InputStream is = mContext.getAssets().open(DB_NAME);
            String outName = DB_PATH + DB_NAME;
            OutputStream os = new FileOutputStream(outName);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
            os.flush();
            os.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void openDatabase() {
        mDatabase = SQLiteDatabase.openDatabase(DB_PATH+DB_NAME,null,SQLiteDatabase.OPEN_READWRITE);

    }

    private boolean checkDatabase() {
        File f= new File(DB_PATH+DB_NAME);
        return f.exists();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}

