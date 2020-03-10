package eu.rutolo.automusico.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;

public class FragmentosDBHelper extends SQLiteOpenHelper {

    // region Singleton
    private static FragmentosDBHelper fragmentosDBHelper;

    public static FragmentosDBHelper getInstance(Context ctx) {
        if (fragmentosDBHelper == null) {
            fragmentosDBHelper = new FragmentosDBHelper(ctx);
        }
        return fragmentosDBHelper;
    }
    // endregion

    private static final String DB_NAME = "fragmentos.db";
    private static final int DB_VERSION = 1;

    private FragmentosDBHelper(Context ctx) {
        super(ctx, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE Categoria (" +
                "idCateg    integer primary key autoincrement," +
                "nom        text unique," +
                "descr      text" +
                ");";
        db.execSQL(sql);

        sql = "CREATE TABLE TipoEstructural (" +
                "idTipo integer primary key autoincrement," +
                "nom    text unique," +
                "descr  text" +
                ");";
        db.execSQL(sql);

        sql = "CREATE TABLE TipoFuncional (" +
                "idTipo     integer primary key autoincrement," +
                "nom        text unique," +
                "descr      text," +
                "minSimult  integer," +
                "maxSimult  integer" +
                ");";
        db.execSQL(sql);

        sql = "CREATE TABLE Fragmento (" +
                "idFrag     integer primary key autoincrement," +
                "nombre     text," +
                "ruta       text," +
                "hash       text," +
                "tempo      integer," +
                "tEstr      integer," +
                "tFunc      integer," +
                "" +
                "foreign key (tEstr) references TipoEstructural (idTipo)" +
                "   on update cascade on delete no action," +
                "foreign key (tFunc) references TipoFuncional (idTipo)" +
                "   on update cascade on delete no action" +
                ");";
        db.execSQL(sql);

        sql = "CREATE TABLE CancCateg (" +
                "idFrag     integer," +
                "idCateg    integer," +
                "intensidad integer," +
                "" +
                "primary key (idFrag, idCateg)," +
                "foreign key (idFrag) references Fragmento (idFrag)" +
                "   on update cascade on delete cascade," +
                "foreign key (idCateg) references Categoria (idCateg)" +
                "   on update cascade on delete cascade" +
                ");";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    // region Categoria
    public long addCategoria(String nom, String descr) {
        ContentValues vals = new ContentValues();
        vals.put("nom", nom);
        vals.put("descr", descr);
        return getWritableDatabase().insert("Categoria", null, vals);
    }

    public Categoria[] listCategoria() {
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
    // endregion

    // region TipoEstructural
    public long addTipoEstructural(String nom, String descr) {
        ContentValues vals = new ContentValues();
        vals.put("nom", nom);
        vals.put("descr", descr);
        return getWritableDatabase().insert("TipoEstructural", null, vals);
    }

    public TipoEstructural[] listTipoEstructural() {
        Cursor cur = getReadableDatabase().rawQuery("SELECT * FROM TipoEstructural", null);
        TipoEstructural[] tes = new TipoEstructural[cur.getCount()];
        if (cur.moveToFirst()) {
            for (int i = 0; i < cur.getCount(); i++) {
                tes[i] = new TipoEstructural(
                        cur.getInt(cur.getColumnIndex("idTipo")),
                        cur.getString(cur.getColumnIndex("nom")),
                        cur.getString(cur.getColumnIndex("descr"))
                );
            }
        }
        cur.close();
        return tes;
    }

    public TipoEstructural getTipoEstructural(int id) {
        Cursor cur = getReadableDatabase().query("TipoEstructural", null,
                "idTipo = ?", new String[] {Integer.toString(id)},
                null, null, null
        );
        TipoEstructural te = null;
        if (cur.moveToFirst()) {
            te = new TipoEstructural(
                    cur.getInt(cur.getColumnIndex("idTipo")),
                    cur.getString(cur.getColumnIndex("nom")),
                    cur.getString(cur.getColumnIndex("descr"))
            );
        }
        cur.close();
        return te;
    }

    public TipoEstructural getTipoEstructural(String nom) {
        Cursor cur = getReadableDatabase().query("TipoEstructural", null,
                        "nom = ?", new String[] {nom},
                        null, null, null
                );
        TipoEstructural te = null;
        if (cur.moveToFirst()) {
            te = new TipoEstructural(
                    cur.getInt(cur.getColumnIndex("idTipo")),
                    cur.getString(cur.getColumnIndex("nom")),
                    cur.getString(cur.getColumnIndex("descr"))
            );
        }
        cur.close();
        return te;
    }
    // endregion

    // region TipoFuncional
    public long addTipoFuncional(String nom, String descr) {
        return addTipoFuncional(nom, descr, 1, 1);
    }

    public long addTipoFuncional(String nom, String descr, int maxSimult) {
        return addTipoFuncional(nom, descr, 1, maxSimult);
    }

    public long addTipoFuncional(String nom, String descr, int minSimult, int maxSimult) {
        ContentValues vals = new ContentValues();
        vals.put("nom", nom);
        vals.put("descr", descr);
        vals.put("minSimult", minSimult);
        vals.put("maxSimult", maxSimult);
        return getWritableDatabase().insert("TipoFuncional", null, vals);
    }

    public TipoFuncional[] listTipoFuncional() {
        Cursor cur = getReadableDatabase().rawQuery("SELECT * FROM TipoFuncional", null);
        TipoFuncional[] tfs = new TipoFuncional[cur.getCount()];
        if (cur.moveToFirst()) {
            for (int i = 0; i < cur.getCount(); i++) {
                tfs[i] = new TipoFuncional(
                        cur.getInt(cur.getColumnIndex("idTipo")),
                        cur.getString(cur.getColumnIndex("nom")),
                        cur.getString(cur.getColumnIndex("descr")),
                        cur.getInt(cur.getColumnIndex("minSimult")),
                        cur.getInt(cur.getColumnIndex("maxSimult"))
                );
            }
        }
        cur.close();
        return tfs;
    }

    public TipoFuncional getTipoFuncional(int id) {
        Cursor cur = getReadableDatabase().query("TipoFuncional", null,
                "idTipo = ?", new String[] {Integer.toString(id)},
                null, null, null
        );
        TipoFuncional tf = null;
        if (cur.moveToFirst()) {
            tf = new TipoFuncional(
                    cur.getInt(cur.getColumnIndex("idTipo")),
                    cur.getString(cur.getColumnIndex("nom")),
                    cur.getString(cur.getColumnIndex("descr")),
                    cur.getInt(cur.getColumnIndex("minSimult")),
                    cur.getInt(cur.getColumnIndex("maxSimult"))
            );
        }
        cur.close();
        return tf;
    }

    public TipoFuncional getTipoFuncional(String nom) {
        Cursor cur = getReadableDatabase().query("TipoFuncional", null,
                "nom = ?", new String[] {nom},
                null, null, null
        );
        TipoFuncional tf = null;
        if (cur.moveToFirst()) {
            tf = new TipoFuncional(
                    cur.getInt(cur.getColumnIndex("idTipo")),
                    cur.getString(cur.getColumnIndex("nom")),
                    cur.getString(cur.getColumnIndex("descr")),
                    cur.getInt(cur.getColumnIndex("minSimult")),
                    cur.getInt(cur.getColumnIndex("maxSimult"))
            );
        }
        cur.close();
        return tf;
    }
    // endregion

    // region Fragmento
    public long addFragmento(String nom, String ruta, String hash, int tempo, TipoEstructural te, TipoFuncional tf) {
        ContentValues vals = new ContentValues();
        vals.put("nom", nom);
        vals.put("ruta", ruta);
        vals.put("hash", hash);
        vals.put("tempo", tempo);
        vals.put("tEstr", te.getIdTipo());
        vals.put("tFunc", tf.getIdTipo());
        return getWritableDatabase().insert("Fragmento", null, vals);
    }

    public Fragmento[] listFragmento() {
        Cursor cur = getReadableDatabase().rawQuery("SELECT * FROM Fragmento", null);
        Fragmento[] tes = new Fragmento[cur.getCount()];
        if (cur.moveToFirst()) {
            for (int i = 0; i < cur.getCount(); i++) {
                tes[i] = new Fragmento(
                        cur.getInt(cur.getColumnIndex("idFrag")),
                        cur.getString(cur.getColumnIndex("nom")),
                        cur.getString(cur.getColumnIndex("ruta")),
                        cur.getString(cur.getColumnIndex("hash")),
                        cur.getInt(cur.getColumnIndex("tempo")),
                        getTipoEstructural(cur.getInt(cur.getColumnIndex("tEstr"))),
                        getTipoFuncional(cur.getInt(cur.getColumnIndex("tFunc")))
                );
            }
        }
        cur.close();
        return tes;
    }

    public long categorizarFragmento(Fragmento frag, Categoria categ, int intensidad) {
        ContentValues vals = new ContentValues();
        vals.put("idFrag", frag.getId());
        vals.put("idCateg", categ.getId());
        vals.put("intensidad", intensidad);
        return getWritableDatabase().insert("CancCateg", null, vals);
    }
    // endregion
}
