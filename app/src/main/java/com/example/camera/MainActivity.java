package com.example.camera;

import static android.os.Environment.getExternalStorageDirectory;
import static android.os.Environment.getExternalStoragePublicDirectory;
import static android.os.Environment.getStorageDirectory;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity<Protected> extends AppCompatActivity {

    private ImageView imv;
private Button cam,gal;
OutputStream outputStream;
    String currentPhotoPath=null;
    private ImageView imv2;
private static final int REQUEST_IMAGE_CAPTURE=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
imv=findViewById(R.id.Imv);
cam=findViewById(R.id.Cam);
imv2=findViewById(R.id.Imv2);
gal=findViewById(R.id.Gal);
gal.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Toast.makeText(MainActivity.this,"xxxxx",Toast.LENGTH_SHORT).show();

        Bitmap bitmap= BitmapFactory.decodeFile(currentPhotoPath);//one way of displaying image
        imv2.setImageBitmap(bitmap);

     //   File f=new File(currentPhotoPath);//another way of displaying image from uri
       // imv.setImageURI(Uri.fromFile(f));
      //  Log.d("tag","ABsolute Url of Image is"+Uri.fromFile(f));
        //Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        //File f = new File(currentPhotoPath);
        //Uri contentUri = Uri.fromFile(f);
        //mediaScanIntent.setData(contentUri);

    }
    }
);

cam.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Toast.makeText(MainActivity.this,
                "inside cam cliack", Toast.LENGTH_SHORT).show();
        //Toast.makeText(MainActivity.this,"Your Message", Toast.LENGTH_LONG).show();
        //askCameraPermissions();
      // dispatchTakePictureIntent();
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) == null || 0==0) {//this condition is becoming false don't know why
            // Create the File where the photo should go
            File photoFilec = null;
            try {
                Toast.makeText(MainActivity.this,
                        "photo file inside", Toast.LENGTH_LONG).show();
                photoFilec = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Toast.makeText(MainActivity.this,
                        "Exception", Toast.LENGTH_LONG).show();
            }
            // Continue only if the File was successfully created
            if (photoFilec != null) {
                Uri photoURIc = FileProvider.getUriForFile(MainActivity.this,
                        "com.example.android.fileprovider",
                        photoFilec);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURIc);
                startActivityForResult(takePictureIntent, 101);
                Toast.makeText(MainActivity.this, ".....", Toast.LENGTH_LONG).show();

            }
        }
        else
            Toast.makeText(MainActivity.this,
                    "Your cx Message", Toast.LENGTH_LONG).show();    }


});

}

    private File createImageFile() throws IOException {

        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageName = "JPEG_" + timeStamp + "_";
     File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

      //  File storageDir=Environment.getRootDirectory();
        //File storageDir=Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */

        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null || 7==7) {
            Toast.makeText(MainActivity.this,
                    "Your cx Message", Toast.LENGTH_LONG).show();
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }else
            Toast.makeText(MainActivity.this,
                    "Your cx1 Message", Toast.LENGTH_LONG).show();
    }
    private void askCameraPermissions() {
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.CAMERA}, 101);
        }else {
          //  opencamera();
            dispatchTakePictureIntent();

        }

    }

    private void opencamera() {
        Intent camera=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(camera,101);
    }

   // @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Toast.makeText(MainActivity.this, "onclick", Toast.LENGTH_LONG).show();

        super.onActivityResult(requestCode, resultCode, data);
//Bitmap image=(Bitmap) data.getExtras().get("data");
//imv.setImageBitmap(image);
        File f=new File(currentPhotoPath);//another way of displaying image from uri
        imv.setImageURI(Uri.fromFile(f));
        Log.d("tag","ABsolute Url of Image is"+Uri.fromFile(f));
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        //File f = new File(currentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);

    }
}