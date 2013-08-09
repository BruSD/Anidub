package brusd.anidub.com.DBAniDubPackage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

/**
 * Created by BruSD on 04.08.13.
 */
/** Класс создающий, удаляющий и редактирующий базу */
public class DatabaseAniDubOpenHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "db.db";
    private static final int DATABASE_VERSION = 1;
    private static final String DEBUG_TAG = DatabaseAniDubOpenHelper.class.getSimpleName();
    private static final boolean LOGV = false;

    public DatabaseAniDubOpenHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /** Удаление всех таблиц из базы
     *
     * @param db
     *            - object of SQLiteDatabase */
    public void dropTables(SQLiteDatabase db) {

        if (LOGV) {
            Log.d(DEBUG_TAG, "onDropTables called");
        }
        db.execSQL("DROP TABLE IF EXISTS " + Names.TABLE_NAME);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        if (LOGV) {
            Log.v(DEBUG_TAG, "onCreate()");
        }
        db.execSQL("CREATE TABLE " + Names.TABLE_NAME + " (" + BaseColumns._ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL , " + Names.NamesColumns.GUID_ANIME
                + " TEXT NOT NULL, "
                + Names.NamesColumns.TITLE_ANIME
                + " TEXT NOT NULL, "
                + Names.NamesColumns.DESCRIPTION_ANIME
                + " TEXT NOT NULL, "
                + Names.NamesColumns.LINK_ANIME
                + " TEXT NOT NULL, "
                + Names.NamesColumns.UP_DATE_ANIME + " TEXT NOT NULL, "
                + Names.NamesColumns.IMAGE_LINK_ANIME + " TEXT );");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(DEBUG_TAG, "onUpgrade called");
    }
}
