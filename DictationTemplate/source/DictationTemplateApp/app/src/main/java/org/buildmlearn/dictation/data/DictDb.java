package org.buildmlearn.dictation.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

/**
 * @brief Contains database util functions for dictation template's app.
 *
 * Created by Anupam (opticod) on 4/7/16.
 */
public class DictDb {

    private static final String EQUAL = " == ";
    private final DictDBHelper dbHelper;
    private SQLiteDatabase db;

    public DictDb(Context context) {
        dbHelper = new DictDBHelper(context);
    }

    public void open() throws SQLException {
        db = dbHelper.getWritableDatabase();
    }

    public boolean isOpen() {
        return db.isOpen();
    }

    public void close() {
        dbHelper.close();
    }

    public Cursor getDictsCursor() {

        return db.query(
                DictContract.Dict.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );
    }

    public Cursor getDictCursorById(int id) {

        String selection = DictContract.Dict._ID + EQUAL + id;

        return db.query(
                DictContract.Dict.TABLE_NAME,
                null,
                selection,
                null,
                null,
                null,
                null
        );
    }

    public long getCount() {

        return DatabaseUtils.queryNumEntries(db,
                DictContract.Dict.TABLE_NAME);
    }

    public int bulkInsert(@NonNull ContentValues[] values) {

        db.beginTransaction();
        int returnCount = 0;
        try {
            for (ContentValues value : values) {

                long _id = db.insert(DictContract.Dict.TABLE_NAME, null, value);
                if (_id != -1) {
                    returnCount++;
                }
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        return returnCount;
    }
}
