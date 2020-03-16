package eu.rutolo.automusico.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FragmentosDBHelper extends SQLiteOpenHelper {

    // region Singleton
    private static FragmentosDBHelper fragmentosDBHelper;

    public static FragmentosDBHelper getInstance() {
        return fragmentosDBHelper;
    }

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
        // region Crear tablas
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
        // endregion

        // region Insertar datos
        // Categorias
        sql = "INSERT INTO Categoria ('nom')" +
                "VALUES ('Tenebrosa');";
        db.execSQL(sql);

        sql = "INSERT INTO Categoria ('nom')" +
                "VALUES ('Alegre');";
        db.execSQL(sql);

        sql = "INSERT INTO Categoria ('nom')" +
                "VALUES ('Neutral');";
        db.execSQL(sql);

        // Tipos funcionales
        sql = "INSERT INTO TipoFuncional ('nom', 'minSimult', 'maxSimult')" +
                "VALUES ('Perucsion', '1', '1');";
        db.execSQL(sql);

        sql = "INSERT INTO TipoFuncional ('nom', 'minSimult', 'maxSimult')" +
                "VALUES ('Melodia', '0', '1');";
        db.execSQL(sql);

        // Fragmentos
        sql = "INSERT INTO Fragmento ('nombre', 'ruta', 'tFunc')" +
                "VALUES ('piedras', 'base1_piedras.ogg', '1');";
        db.execSQL(sql);

        sql = "INSERT INTO Fragmento ('nombre', 'ruta', 'tFunc')" +
                "VALUES ('piano1', 'piano1_edev.ogg', '2');";
        db.execSQL(sql);

        sql = "INSERT INTO Fragmento ('nombre', 'ruta', 'tFunc')" +
                "VALUES ('trompeta1', 'trompeta1_caida.ogg', '2');";
        db.execSQL(sql);

        // CancCateg
        sql = "INSERT INTO CancCateg ('idFrag', 'idCateg', 'intensidad')" +
                "VALUES ('1', '3', '100');";
        db.execSQL(sql);

        sql = "INSERT INTO CancCateg ('idFrag', 'idCateg', 'intensidad')" +
                "VALUES ('2', '3', '100');";
        db.execSQL(sql);

        sql = "INSERT INTO CancCateg ('idFrag', 'idCateg', 'intensidad')" +
                "VALUES ('2', '2', '50');";
        db.execSQL(sql);

        sql = "INSERT INTO CancCateg ('idFrag', 'idCateg', 'intensidad')" +
                "VALUES ('3', '1', '100');";
        db.execSQL(sql);
        // endregion
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

    public Categoria getCategoriaById(int id) {
        Categoria cat = null;
        Cursor cur = getReadableDatabase().query("Categoria", null,
                "idCateg = ?", new String[] {Integer.toString(id)},
                null, null, null, null);

        if (cur.moveToFirst()) {
            cat = new Categoria(
                    cur.getInt(cur.getColumnIndex("idCateg")),
                    cur.getString(cur.getColumnIndex("nom")),
                    cur.getString(cur.getColumnIndex("descr"))
            );
        }
        cur.close();
        return cat;
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
        Fragmento[] frags = new Fragmento[cur.getCount()];
        if (cur.moveToFirst()) {
            for (int i = 0; i < cur.getCount(); i++) {
                frags[i] = new Fragmento(
                        cur.getInt(cur.getColumnIndex("idFrag")),
                        cur.getString(cur.getColumnIndex("nombre")),
                        cur.getString(cur.getColumnIndex("ruta")),
                        cur.getString(cur.getColumnIndex("hash")),
                        cur.getInt(cur.getColumnIndex("tempo")),
                        getTipoEstructural(cur.getInt(cur.getColumnIndex("tEstr"))),
                        getTipoFuncional(cur.getInt(cur.getColumnIndex("tFunc")))
                );
                cur.moveToNext();
            }
        }
        cur.close();

        for (Fragmento frag : frags) {
            cur = getReadableDatabase().query("CancCateg", null,
                    "idFrag = ?", new String[] {Integer.toString(frag.getId())},
                    null, null, null, null
            );

            if (cur.moveToFirst()) {
                for (int i = 0; i < cur.getCount(); i++) {
                    Categoria cat = getCategoriaById(
                            cur.getInt(cur.getColumnIndex("idCateg"))
                    );
                    int intensidad = cur.getInt(cur.getColumnIndex("intensidad"));
                    frag.addCategoria(cat, intensidad);

                    cur.moveToNext();
                }
            }
        }

        return frags;
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
