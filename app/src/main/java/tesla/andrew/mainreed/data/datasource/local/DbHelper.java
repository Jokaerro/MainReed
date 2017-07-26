package tesla.andrew.mainreed.data.datasource.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import tesla.andrew.mainreed.data.datasource.local.SubscribeContract.SubscribeEntry;
import tesla.andrew.mainreed.data.datasource.local.NewsContract.ArticleEntry;

/**
 * Created by TESLA on 22.07.2017.
 */

public class DbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 3;
    static final String DATABASE_NAME = "main_reed.db";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_SUBSCRIBE_TABLE = "CREATE TABLE " + SubscribeEntry.TABLE_NAME + " (" +
                BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                SubscribeEntry.COLUMN_CAPTION + " TEXT, " +
                SubscribeEntry.COLUMN_SOURCE + " TEXT, " +
                SubscribeEntry.COLUMN_VISIBLE + " INTEGER " +
                ");";

        sqLiteDatabase.execSQL(SQL_CREATE_SUBSCRIBE_TABLE);
        fillSubscribes(sqLiteDatabase);

        final String SQL_CREATE_ARTICLES_TABLE = "CREATE TABLE " + ArticleEntry.TABLE_NAME + " (" +
                BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ArticleEntry.COLUMN_AUTHOR + " TEXT, " +
                ArticleEntry.COLUMN_TITLE + " TEXT, " +
                ArticleEntry.COLUMN_DESCRIPTION + " TEXT, " +
                ArticleEntry.COLUMN_URL + " TEXT, " +
                ArticleEntry.COLUMN_URL_TO_IMAGE + " TEXT, " +
                ArticleEntry.COLUMN_PUBLISHED_AT + " TEXT, " +
                ArticleEntry.COLUMN_SUBSCRIBE_KEY + " TEXT NOT NULL, " +

                " FOREIGN KEY (" + ArticleEntry.COLUMN_SUBSCRIBE_KEY + ") REFERENCES " +
                SubscribeEntry.TABLE_NAME + " (" + SubscribeEntry.COLUMN_SOURCE + "), " +

                " UNIQUE (" + ArticleEntry.COLUMN_URL + ", " +
                ArticleEntry.COLUMN_SUBSCRIBE_KEY + ") ON CONFLICT REPLACE);";

        sqLiteDatabase.execSQL(SQL_CREATE_ARTICLES_TABLE);

    }

    private void fillSubscribes(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("insert into " + SubscribeEntry.TABLE_NAME +" values (1,'Associated Press','associated-press','0');");
        sqLiteDatabase.execSQL("insert into " + SubscribeEntry.TABLE_NAME +" values (2,'BBC News','bbc-news','0');");
        sqLiteDatabase.execSQL("insert into " + SubscribeEntry.TABLE_NAME +" values (3,'Bloomberg','bloomberg','0');");
        sqLiteDatabase.execSQL("insert into " + SubscribeEntry.TABLE_NAME +" values (4,'Buzzfeed','buzzfeed','1');");
        sqLiteDatabase.execSQL("insert into " + SubscribeEntry.TABLE_NAME +" values (5,'Hacker News','hacker-news','1');");
        sqLiteDatabase.execSQL("insert into " + SubscribeEntry.TABLE_NAME +" values (6,'IGN','ign','0');");
        sqLiteDatabase.execSQL("insert into " + SubscribeEntry.TABLE_NAME +" values (7,'Reddit /r/all','reddit-r-all','1');");
        sqLiteDatabase.execSQL("insert into " + SubscribeEntry.TABLE_NAME +" values (8,'MTV News','mtv-news','0');");
        sqLiteDatabase.execSQL("insert into " + SubscribeEntry.TABLE_NAME +" values (9,'TechCrunch','techcrunch','1');");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + SubscribeEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ArticleEntry.TABLE_NAME);

        onCreate(sqLiteDatabase);
    }
}
