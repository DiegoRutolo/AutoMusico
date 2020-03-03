package eu.rutolo.automusico.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FragmentosDBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "fragmentos.db";
    public static final int DB_VERSION = 1;

    public FragmentosDBHelper(Context ctx) {
        super(ctx, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
