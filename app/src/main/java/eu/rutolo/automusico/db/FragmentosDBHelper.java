package eu.rutolo.automusico.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class FragmentosDBHelper extends SQLiteOpenHelper {

    // region Singleton
    public static FragmentosDBHelper fragmentosDBHelper;

    public static FragmentosDBHelper getInstance(Context ctx) {
        if (fragmentosDBHelper == null) {
            fragmentosDBHelper = new FragmentosDBHelper(ctx);
        }
        return fragmentosDBHelper;
    }
    // endregion

    public static final String DB_NAME = "fragmentos.db";
    public static final int DB_VERSION = 1;

    private FragmentosDBHelper(Context ctx) {
        super(ctx, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long addCategoría(String nom, String descr) {
        ContentValues vals = new ContentValues();
        vals.put("nom", nom);
        vals.put("descr", descr);
        return getWritableDatabase().insert("Categoria", null, vals);
    }

    public Categoria[] listCategorias() {
        Cursor cur = getReadableDatabase().rawQuery("SELECT * FROM Categoria", null);
        Categoria[] cats = new Categoria[cur.getCount()];
        if (cur.moveToFirst()) {
            for (int i = 0; i < cur.getCount(); i++) {
                cats[i] = new Categoria(
                        cur.getInt(cur.getColumnIndex("idCateg")),
                        cur.getString(cur.getColumnIndex("nom")),
                        cur.getString(cur.getColumnIndex("descr"))
                );
                cur.moveToNext();
            }
        }
        cur.close();
        return cats;
    }


}
