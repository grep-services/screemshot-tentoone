package com.moon_o.screenshottest;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private int count;

    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn = (Button)findViewById(R.id.screenbtn);
        verifyStoragePermissions(this);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                sleep(2000);
                bitmap = tackScreenshot();
//                checkPermission(bitmap);

                saveBitmap(bitmap);
                Toast.makeText(getApplicationContext(), "CAPTURED!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sleep(int second) {
        try {
            Thread.sleep(second);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private Bitmap tackScreenshot() {
        View rootView = findViewById(android.R.id.content).getRootView();
        rootView.setDrawingCacheEnabled(true);
        return rootView.getDrawingCache();
    }

    private void saveBitmap(Bitmap bitmap) {

//        String path = Environment.getExternalStorageDirectory().toString() + "/Pictures/Screenshots/";
        String path =
                Environment.getExternalStorageDirectory()
                        .getPath()+File.separator+
                        "Pictures"+File.separator+
                        "Screenshots"+File.separator;
        File folder = new File(path);
        if(!folder.exists())
            folder.mkdirs();

        File imagePath = new File(path+"screenshot" +count+".png");
        if(!imagePath.exists())
            try {
                imagePath.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

        count++;
        FileOutputStream fos;

        try {
//            Log.e("COME", "COMECOME");
            fos = new FileOutputStream(imagePath);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }




        public static void verifyStoragePermissions(Activity activity) {
            // Check if we have write permission
            int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

            if (permission != PackageManager.PERMISSION_GRANTED) {
                // We don't have permission so prompt the user
                ActivityCompat.requestPermissions(
                        activity,
                        PERMISSIONS_STORAGE,
                        MY_PERMISSION_REQUEST_STORAGE
                );
            }
        }
        private static final int MY_PERMISSION_REQUEST_STORAGE = 1000;
        private static String[] PERMISSIONS_STORAGE = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };

}


