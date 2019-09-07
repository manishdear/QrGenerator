package com.example.qrgenerator;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.qrgenerator.data.TokenContract;

public class TokenCursorAdapter extends CursorAdapter {

    public TokenCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        // Find fields to populate in inflated template
        TextView tvtoken = (TextView) view.findViewById(R.id.token);
        TextView tvbooking = (TextView) view.findViewById(R.id.booking_time);
        TextView tvexpected = (TextView) view.findViewById(R.id.expected_time);

        // Extract properties from cursor
        String booking_date = cursor.getString(cursor.getColumnIndexOrThrow(TokenContract.TokenEntry.COLUMN_BOOKING_DATE));
        String expected_date = cursor.getString(cursor.getColumnIndexOrThrow(TokenContract.TokenEntry.COLUMN_ENDING_DATE));
        String token = cursor.getString(cursor.getColumnIndexOrThrow(TokenContract.TokenEntry.COLUMN_TOKEN));
        int booking_hr = cursor.getInt(cursor.getColumnIndexOrThrow(TokenContract.TokenEntry.COLUMN_BOOKING_HR));
        int booking_min = cursor.getInt(cursor.getColumnIndexOrThrow(TokenContract.TokenEntry.COLUMN_BOOKING_MIN));
        int ending_hr = cursor.getInt(cursor.getColumnIndexOrThrow(TokenContract.TokenEntry.COLUMN_ENDING_HR));
        int ending_min = cursor.getInt(cursor.getColumnIndexOrThrow(TokenContract.TokenEntry.COLUMN_ENDING_MIN));
        int booking_ampm = cursor.getInt(cursor.getColumnIndexOrThrow(TokenContract.TokenEntry.COLUMN_BOOKING_AMPM));
        int ending_ampm = cursor.getInt(cursor.getColumnIndexOrThrow(TokenContract.TokenEntry.COLUMN_ENDING_AMPM));
        // Populate fields with extracted properties
        tvtoken.setText(String.valueOf(token));
        tvbooking.setText(String.valueOf(booking_date));
        tvexpected.setText(String.valueOf(expected_date));

    }
}
