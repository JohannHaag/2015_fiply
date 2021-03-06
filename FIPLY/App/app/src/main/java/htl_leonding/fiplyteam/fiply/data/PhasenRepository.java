package htl_leonding.fiplyteam.fiply.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import htl_leonding.fiplyteam.fiply.data.FiplyContract.PhasenEntry;


public class PhasenRepository {

    private static Context repoContext;
    private static PhasenRepository instance = null;

    SQLiteDatabase db = getWritableDatabase();

    public static PhasenRepository getInstance() {
        if (instance == null)
            instance = new PhasenRepository();
        return instance;
    }

    public static void setContext(Context context) {
        repoContext = context;
    }

    private SQLiteDatabase getWritableDatabase() {
        if (repoContext == null)
            throw new IllegalStateException("Context is null - Set a repoContext in the Repository with setContext()");

        Log.wtf(FiplyDBHelper.LOG_TAG, "Repository::getWriteableDatabase()");
        return FiplyDBHelper.getInstance(repoContext).getWritableDatabase();
    }

    private SQLiteDatabase getReadableDatabase() {
        if (repoContext == null)
            throw new IllegalStateException("Context is null - Set a repoContext in the Repository with setContext()");

        Log.wtf(FiplyDBHelper.LOG_TAG, "Repository::getReadableDatabase()");
        return FiplyDBHelper.getInstance(repoContext).getReadableDatabase();
    }

    //Fügt eine Phase in die Tabelle ein.
    public long insertPhase(String startDate, String endDate, String phasenName,
                            String phasenDauer, String pausenDauer, String saetze, String wiederholungen, String planId) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(PhasenEntry.COLUMN_STARTDATE, startDate);
        initialValues.put(PhasenEntry.COLUMN_ENDDATE, endDate);
        initialValues.put(PhasenEntry.COLUMN_PHASENNAME, phasenName);
        initialValues.put(PhasenEntry.COLUMN_PHASENDAUER, phasenDauer);
        initialValues.put(PhasenEntry.COLUMN_PAUSENDAUER, pausenDauer);
        initialValues.put(PhasenEntry.COLUMN_SAETZE, saetze);
        initialValues.put(PhasenEntry.COLUMN_WIEDERHOLUNGEN, wiederholungen);
        initialValues.put(PhasenEntry.COLUMN_PLANID, planId);

        return db.insert(PhasenEntry.TABLE_NAME, null, initialValues);
    }

    //Gibt alle Phasen zurück.
    public Cursor getAllPhasen() {
        return db.query(PhasenEntry.TABLE_NAME, new String[]{
                        PhasenEntry.COLUMN_ROWID,
                        PhasenEntry.COLUMN_STARTDATE,
                        PhasenEntry.COLUMN_ENDDATE,
                        PhasenEntry.COLUMN_PHASENNAME,
                        PhasenEntry.COLUMN_PHASENDAUER,
                        PhasenEntry.COLUMN_PAUSENDAUER,
                        PhasenEntry.COLUMN_SAETZE,
                        PhasenEntry.COLUMN_WIEDERHOLUNGEN,
                        PhasenEntry.COLUMN_PLANID
                },
                null, null, null, null, PhasenEntry.COLUMN_STARTDATE + " ASC");
    }

    //Gibt eine Phase mit einer bestimmten id zurück.
    public Cursor getPhase(long rowId) throws SQLException {
        Cursor myCursor = db.query(true, PhasenEntry.TABLE_NAME, new String[]{
                        PhasenEntry.COLUMN_ROWID,
                        PhasenEntry.COLUMN_STARTDATE,
                        PhasenEntry.COLUMN_ENDDATE,
                        PhasenEntry.COLUMN_PHASENNAME,
                        PhasenEntry.COLUMN_PHASENDAUER,
                        PhasenEntry.COLUMN_PAUSENDAUER,
                        PhasenEntry.COLUMN_SAETZE,
                        PhasenEntry.COLUMN_WIEDERHOLUNGEN,
                        PhasenEntry.COLUMN_PLANID},
                PhasenEntry.COLUMN_ROWID + "=" + rowId,
                null, null, null, null, null);
        if (myCursor != null) {
            myCursor.moveToFirst();
        }
        return myCursor;
    }

    // Gibt alle Phasen mit einem bestimmten startDate zurück.
    public Cursor getPhaseByStartDate(Date startDate) {
        DateFormat format = new SimpleDateFormat("dd. MMMM yyyy", Locale.ENGLISH);

        return db.query(PhasenEntry.TABLE_NAME, new String[]{
                        PhasenEntry.COLUMN_ROWID
                }, PhasenEntry.COLUMN_STARTDATE + "=" + "'" + format.format(startDate) + "'",
                null, null, null, PhasenEntry.COLUMN_ROWID + " ASC", null);
    }

    // Erstellt die Phasentabelle
    public void reCreatePhasenTable() {
        db.execSQL("DROP TABLE IF EXISTS " + PhasenEntry.TABLE_NAME + ";");
        final String SQL_CREATE_PHASEN_TABLE = "create table " + PhasenEntry.TABLE_NAME +
                " (" + PhasenEntry.COLUMN_ROWID + " integer primary key autoincrement, " +
                PhasenEntry.COLUMN_STARTDATE + " text not null, " +
                PhasenEntry.COLUMN_ENDDATE + " text not null, " +
                PhasenEntry.COLUMN_PHASENNAME + " text not null, " +
                PhasenEntry.COLUMN_PHASENDAUER + " text not null, " +
                PhasenEntry.COLUMN_PAUSENDAUER + " text not null, " +
                PhasenEntry.COLUMN_SAETZE + " text not null, " +
                PhasenEntry.COLUMN_WIEDERHOLUNGEN + " text not null," +
                PhasenEntry.COLUMN_PLANID + " text not null" +
                ");";
        db.execSQL(SQL_CREATE_PHASEN_TABLE);
    }

    // Löscht eine Phase mit einer bestimmten Id
    public void deleteByPlanId(String id){
        db.delete(PhasenEntry.TABLE_NAME, PhasenEntry.COLUMN_PLANID + "=" + id, null);
    }

    // Gibt alle Phasen mit einer bestimmten Id zurück.
    public Cursor getIdsByPlanId(String id) {
        return db.query(true, PhasenEntry.TABLE_NAME, new String[]{
                PhasenEntry.COLUMN_ROWID,
                PhasenEntry.COLUMN_STARTDATE
        }, PhasenEntry.COLUMN_PLANID + "=" + id, null, null, null, null, null);
    }

    // Löscht alle Phasen aus der Tabelle.
    public void deleteAll() {
        db.delete(PhasenEntry.TABLE_NAME, null, null);
    }
}