package com.droplay.nova.game;

import java.io.File;
import java.io.FileNotFoundException;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.util.Log;

// Used to save an image and share it
public class CachedFileProvider extends ContentProvider {

    private static final String TAG = "ImagesProvider";
    private static final String AUTHORITY = "com.droplay.nova";
    private static final String CONTENT_TYPE = "vnd.android.cursor.dir/com.droplay.nova";
    private static final UriMatcher uriMatcher;

    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/images");

    private static final int MATCH_LIST = 1;
    private static final int MATCH_ID = 2;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, "images", MATCH_LIST);
        uriMatcher.addURI(AUTHORITY, "images/#", MATCH_ID);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
        case MATCH_LIST:
            return CONTENT_TYPE;
        default:
            throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean onCreate() {
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
            String[] selectionArgs, String sortOrder) {
        Log.d(TAG, "query(" + uri + ")");
        MatrixCursor cursor = new MatrixCursor(new String[] { "_id", "image",
                "_data" });
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        switch (uriMatcher.match(uri)) {
        case MATCH_LIST:
                addRow(cursor);
            break;
        case MATCH_ID:
            addRow(cursor);
            break;
        default:
            throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        return cursor;
    }

    private void addRow(MatrixCursor cursor) {
        File imageFile = getImageFile();
        String imagePath = null;
        if (imageFile.exists())
            imagePath = imageFile.getAbsolutePath();
        
        cursor.addRow(new Object[] { 0,
                ContentUris.withAppendedId(CONTENT_URI, 0), imagePath});
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
            String[] selectionArgs) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public ParcelFileDescriptor openFile(Uri uri, String mode)
            throws FileNotFoundException {
        Log.d(TAG, "openFile(" + uri + ", " + mode + ")");
        return super.openFileHelper(uri, mode);
    }

    private File getImageFile() {
        return new File(getContext().getFilesDir() + File.separator + "temporary_file.jpeg");
    }

}