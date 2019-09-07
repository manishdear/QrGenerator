package com.example.qrgenerator.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public class TokenContract {

    public TokenContract() {
    }

    /**
     * The "Content authority" is a name for the entire content provider, similar to the
     * relationship between a domain name and its website.  A convenient string to use for the
     * content authority is the package name for the app, which is guaranteed to be unique on the
     * device.
     */
    public static final String CONTENT_AUTHORITY = "com.example.qrgenerator";

    /**
     * Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
     * the content provider.
     */
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    /**
     * Possible path (appended to base content URI for possible URI's)
     * For instance, content://com.example.android.pets/pets/ is a valid path for
     * looking at pet data. content://com.example.android.pets/staff/ will fail,
     * as the ContentProvider hasn't been given any information on what to do with "staff".
     */
    public static final String PATH_TOKEN = "time";

    public static final class TokenEntry implements BaseColumns{

        /** The content URI to access the pet data in the provider */
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_TOKEN);

        public final static String TABLE_NAME = "time";

        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_BOOKING_DATE = "bookingDate";
        public final static String COLUMN_BOOKING_HR = "bookingHr";
        public final static String COLUMN_BOOKING_MIN = "bookingMin";
        public final static String COLUMN_BOOKING_AMPM = "bookingAMPM";
        public final static String COLUMN_ENDING_DATE = "endingDate";
        public final static String COLUMN_ENDING_HR = "endingHr";
        public final static String COLUMN_ENDING_MIN = "endingMin";
        public final static String COLUMN_ENDING_AMPM = "endingAMPM";
        public final static String COLUMN_TOKEN = "token";

    }
}
