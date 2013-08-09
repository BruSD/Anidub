package brusd.anidub.com.DBAniDubPackage;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.provider.BaseColumns;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by BruSD on 04.08.13.
 */
public class AnimeController {

    private static final boolean LOGV = false;
    private static int maxRowsInNames = -1;
    private static final String TAG = AnimeController.class.getSimpleName();

    private AnimeController() {

    }

    public static int getMaxRowsInNames() {

        return maxRowsInNames;
    }
    public static void setMaxRowsInNames(int maxRowsInNames) {

        AnimeController.maxRowsInNames = maxRowsInNames;
    }
    /**  Функция возвращает все данные из базы при запросе к ней
     *
     * @param context
     * @return */
    public static ArrayList<Names> readNames(Context context, int resulCount ) {

        ArrayList<Names> list = new ArrayList<Names>();
        try {
            DatabaseAniDubOpenHelper dbhelper = new DatabaseAniDubOpenHelper(context);
            SQLiteDatabase sqliteDB = dbhelper.getReadableDatabase();
            String[] columnsToTake = { BaseColumns._ID,
                    Names.NamesColumns.GUID_ANIME,
                    Names.NamesColumns.TITLE_ANIME,
                    Names.NamesColumns.LINK_ANIME,
                    Names.NamesColumns.UP_DATE_ANIME,
                    Names.NamesColumns.IMAGE_LINK_ANIME };

            /*Cursor cursor = sqliteDB.query(false,
                    Names.TABLE_NAME,
                    columnsToTake,
                    null, null, null, null, null,
                    String.valueOf(resulCount)
                     / limit /);
            */
            Cursor cursor = sqliteDB.query(Names.TABLE_NAME, columnsToTake, null, null, null, null,null);

            if (!cursor.moveToFirst())
                return list;

            while (cursor.moveToNext()) {
                Names oneRow = new Names();
                oneRow.setGuid_anime(cursor.getString(cursor.getColumnIndexOrThrow(Names.NamesColumns.GUID_ANIME)));
                oneRow.setTitle_anime(cursor.getString(cursor.getColumnIndexOrThrow(Names.NamesColumns.TITLE_ANIME)));
                //TODO: Add All nead Items
                list.add(oneRow);
            }
            cursor.close();
            dbhelper.close();
        } catch (Exception e) {
            Log.e(TAG, "Failed to se    Names.NamesColumns.lect Names.", e);
        }
        return list;
    }


    public static HashSet<String> isAnimeInBase(Context context, String[] guidList){
        HashSet<String>  animeGuid = new HashSet<String>();
        try {
            DatabaseAniDubOpenHelper dbhelper = new DatabaseAniDubOpenHelper(context);
            SQLiteDatabase sqliteDB = dbhelper.getReadableDatabase();
            String[] columnsToTake = { BaseColumns._ID,
                    Names.NamesColumns.GUID_ANIME,
                    };
            Cursor cursor = sqliteDB.query(Names.TABLE_NAME, columnsToTake, null, null, null, null,null);

        }catch (Exception e) {
            Log.e(TAG, "Failed to se    Names.NamesColumns.lect Names.", e);
        }
        return animeGuid;
    }


    /**Изменение строки в списке*/
    public static void update(Context context, String comment, String comment1, String guid) {

        try {
            DatabaseAniDubOpenHelper dbhelper = new DatabaseAniDubOpenHelper(context);
            SQLiteDatabase sqliteDB = dbhelper.getWritableDatabase();
            String quer = null;
            int countRows = -1;
            Cursor cursor = sqliteDB.query(Names.TABLE_NAME, new String[] { "count(*)" }, null, null, null,
                    null, null);
            if (cursor.moveToFirst()) {
                countRows = cursor.getInt(0);
                if (LOGV) {
                    Log.v(TAG, "Count in Names table" + String.valueOf(countRows));
                }
            }
            cursor.close();
            quer = "UPDATE " + Names.TABLE_NAME + " SET " +
                    Names.NamesColumns.TITLE_ANIME + " = '" + comment + "' "+
                    Names.NamesColumns.UP_DATE_ANIME + " = '" + comment1 + "' "+
                    "WHERE " + Names.NamesColumns.GUID_ANIME + " = '" + guid +"'";
            Log.d("", "" + quer);
            sqliteDB.execSQL(quer);
            sqliteDB.close();
            dbhelper.close();
        } catch (SQLiteException e) {
            Log.e(TAG, "Failed open database. ", e);
        } catch (SQLException e) {
            Log.e(TAG, "Failed to update Names. ", e);
        }
    }

    /**Удаление строки из списка*/
    public static void delete(Context context, long l) {

        DatabaseAniDubOpenHelper dbhelper = new DatabaseAniDubOpenHelper(context);
        SQLiteDatabase sqliteDB = dbhelper.getWritableDatabase();
        sqliteDB.delete(Names.TABLE_NAME, BaseColumns._ID  + " = " + l, null);
        sqliteDB.close();
        dbhelper.close();
    }

    /**
     * <p>Эта функция создает запрос которые дальше записывает данные в нашу базу данных</p>
     * @param context
     * @param GUID
     * @param TITLE
     * @param DESCRIPTION
     * @param LINK
     * @param UP_DATA
     * @param IMAGE_LINK
     */
    public static void write(Context context, SQLiteDatabase sqliteDB, String GUID, String TITLE, String DESCRIPTION, String LINK,String UP_DATA, String IMAGE_LINK ) {

        try {
            //создали нашу базу и открыли для записи

            String quer = null;
            int countRows = -1;
            //Открыли курсор для записи
            Cursor cursor = sqliteDB.query(Names.TABLE_NAME, new String[] { "count(*)" }, null, null, null,
                    null, null);
            if (cursor.moveToFirst()) {
                countRows = cursor.getInt(0);
                if (LOGV) {
                    Log.v(TAG, "Count in Names table" + String.valueOf(countRows));
                }
            }
            cursor.close();
            if ((maxRowsInNames == -1) || (maxRowsInNames >= countRows)) {

                quer = "Insert into " + Names.TABLE_NAME + "("
                        + Names.NamesColumns.GUID_ANIME + ", "
                        + Names.NamesColumns.TITLE_ANIME + ", "
                        + Names.NamesColumns.DESCRIPTION_ANIME + ", "
                        + Names.NamesColumns.LINK_ANIME + ", "
                        + Names.NamesColumns.UP_DATE_ANIME + ","
                        + Names.NamesColumns.IMAGE_LINK_ANIME
                        + ") Values ('" + GUID + "', '" + TITLE + "', '" + DESCRIPTION + "', '" + LINK + "', '" +
                        UP_DATA + "', '" + IMAGE_LINK + "');";
            }
            //закрыли всю базу
            sqliteDB.execSQL(quer);

        } catch (SQLiteException e) {
            Log.e(TAG, "Failed open rimes database. ", e);
        } catch (SQLException e) {
            Log.e(TAG, "Failed to insert Names. ", e);
        }
    }
}
