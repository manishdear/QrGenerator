package com.example.qrgenerator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.qrgenerator.data.TokenContract;
import com.google.zxing.WriterException;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import androidmads.library.qrgenearator.QRGSaver;

public class MainActivity extends AppCompatActivity {

    EditText edtValue;
    ImageView qrImage;
    Button start, save;
    String inputValue;
    String savePath = Environment.getExternalStorageState(new File("PRIVATE_EXTERNAL_STORAGE/Pictures"));
    //String savePath = Environment.getExternalStorageDirectory().getPath() + "/QRCode/";
    Bitmap bitmap;
    QRGEncoder qrgEncoder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        qrImage = (ImageView) findViewById(R.id.QR_Image);
        start = (Button) findViewById(R.id.start);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //inputValue = edtValue.getText().toString().trim();
                generateQrCode();
            }
        });
        displayDatabaseInfo();
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }

    private void generateQrCode(){
        Calendar c1 = Calendar.getInstance();
        int h = c1.get(Calendar.HOUR);
        int m = c1.get(Calendar.MINUTE);
        int s = c1.get(Calendar.SECOND);
        int x = c1.get(Calendar.AM_PM);
        String ampm;

        if(x ==0){
            ampm = "AM";
        }
        else
            ampm = "PM";

//        Date currentTime = Calendar.getInstance().getTime();
//        inputValue = DateFormat.getDateInstance().format(currentTime);

        inputValue = h +""+ m +"" + s +"" + x;

        insertItem();

        if (inputValue.length() > 0) {
            WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
            Display display = manager.getDefaultDisplay();
            Point point = new Point();
            display.getSize(point);
            int width = point.x;
            int height = point.y;
            int smallerDimension = width < height ? width : height;
            smallerDimension = smallerDimension * 3 / 4;

            qrgEncoder = new QRGEncoder(
                    inputValue, null,
                    QRGContents.Type.TEXT,
                    smallerDimension);
            try {
                bitmap = qrgEncoder.encodeAsBitmap();
                qrImage.setImageBitmap(bitmap);
            } catch (WriterException e) {
                Log.v("GenerateQRCode", e.toString());
            }
        } else {
            edtValue.setError("Required");
        }
        displayDatabaseInfo();
    }

    private void insertItem(){

        Calendar c1 = Calendar.getInstance();
        int dd = c1.get(Calendar.DATE);
        int mm = 1 + c1.get(Calendar.MONTH);
        int yy = c1.get(Calendar.YEAR);
        int h = c1.get(Calendar.HOUR);
        int m = c1.get(Calendar.MINUTE);
        int s = c1.get(Calendar.SECOND);
        int x = c1.get(Calendar.AM_PM);
        String ampm;

        if(x ==0){
            ampm = "AM";
        }
        else
            ampm = "PM";

        String tokenString = Integer.toString(h);
        tokenString += Integer.toString(m);
        tokenString += Integer.toString(s);
        tokenString += Integer.toString(x);

        //Integer token = Integer.parseInt(tokenString);

        Date currentTime = Calendar.getInstance().getTime();
        String value = DateFormat.getDateInstance().format(currentTime);

        value = value + " " + h + ":" + m + ampm;

        String booking_date = value.trim();
        String ending_date = value.trim();

        Integer booking_hr = h;
        Integer booking_min = m;
        Integer booking_ampm = x;

        Integer ending_hr = h;
        Integer ending_min = m + 5;
        Integer ending_ampm = x;


        ContentValues contentValues = new ContentValues();

        contentValues.put(TokenContract.TokenEntry.COLUMN_BOOKING_DATE, booking_date);
        contentValues.put(TokenContract.TokenEntry.COLUMN_BOOKING_HR, booking_hr);
        contentValues.put(TokenContract.TokenEntry.COLUMN_BOOKING_MIN, booking_min);
        contentValues.put(TokenContract.TokenEntry.COLUMN_BOOKING_AMPM, booking_ampm);

        contentValues.put(TokenContract.TokenEntry.COLUMN_ENDING_DATE, ending_date);
        contentValues.put(TokenContract.TokenEntry.COLUMN_ENDING_HR, ending_hr);
        contentValues.put(TokenContract.TokenEntry.COLUMN_ENDING_MIN, ending_min);
        contentValues.put(TokenContract.TokenEntry.COLUMN_ENDING_AMPM, ending_ampm);
        contentValues.put(TokenContract.TokenEntry.COLUMN_TOKEN, tokenString);


        Uri uri = getContentResolver().insert(TokenContract.TokenEntry.CONTENT_URI, contentValues);


        long result = (ContentUris.parseId(uri));

        if(result != -1) {

            Toast.makeText(getApplicationContext(), "Pet saved with id " + result, Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getApplicationContext(), "Not saved " + result, Toast.LENGTH_SHORT).show();
        }
    }

    private void displayDatabaseInfo() {

        // Perform this raw SQL query "SELECT * FROM pets"
        // to get a Cursor that contains all rows from the pets table.
        //Cursor cursor = db.rawQuery("SELECT * FROM " + PetsContract.PetsEntry.TABLE_NAME, null);

        String[] projection = {
                TokenContract.TokenEntry._ID,
                TokenContract.TokenEntry.COLUMN_BOOKING_DATE,
                TokenContract.TokenEntry.COLUMN_BOOKING_HR,
                TokenContract.TokenEntry.COLUMN_BOOKING_MIN,
                TokenContract.TokenEntry.COLUMN_BOOKING_AMPM,
                TokenContract.TokenEntry.COLUMN_ENDING_DATE,
                TokenContract.TokenEntry.COLUMN_ENDING_HR,
                TokenContract.TokenEntry.COLUMN_ENDING_MIN,
                TokenContract.TokenEntry.COLUMN_ENDING_AMPM,
                TokenContract.TokenEntry.COLUMN_TOKEN};


        Cursor cursor = getContentResolver().query( TokenContract.TokenEntry.CONTENT_URI, projection, null, null, null);


        // Find ListView to populate
        ListView tokenListView  = findViewById(R.id.list_item_view);

        // Setup cursor adapter using cursor from last step
        TokenCursorAdapter todoAdapter = new TokenCursorAdapter(this, cursor);
        // Attach cursor adapter to the ListView
        tokenListView .setAdapter(todoAdapter);
    }
}