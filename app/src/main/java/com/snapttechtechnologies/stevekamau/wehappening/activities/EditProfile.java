package com.snapttechtechnologies.stevekamau.wehappening.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.snapttechtechnologies.stevekamau.wehappening.R;
import com.snapttechtechnologies.stevekamau.wehappening.helper.ImageInputHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class EditProfile extends AppCompatActivity implements ImageInputHelper.ImageActionListener {

    Bitmap bitmap;
    private ImageInputHelper imageInputHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        imageInputHelper = new ImageInputHelper(this);
        imageInputHelper.setImageActionListener(this);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        findViewById(R.id.relative_click).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFilterPopup(v);
            }
        });
        File path = new File(Environment.getExternalStorageDirectory(), "/weHappening/profile_image.png");
        Bitmap b = null;
        if (path.exists()) {
            try {
                b = BitmapFactory.decodeStream(new FileInputStream(path));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            ImageView img = (ImageView) findViewById(R.id.image);
            img.setImageBitmap(b);
        } else {
            ImageView img = (ImageView) findViewById(R.id.image);
            img.setImageDrawable(getResources().getDrawable(R.drawable.ic_person));
        }


    }


    private void showFilterPopup(View v) {
        PopupMenu popup = new PopupMenu(EditProfile.this, v);
        // Inflate the menu from xml
        popup.getMenuInflater().inflate(R.menu.popup_select_image, popup.getMenu());
        // Setup menu item selection
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.gallery:
                        imageInputHelper.selectImageFromGallery();
                        return true;
                    case R.id.camera:
                        imageInputHelper.takePhotoWithCamera();
                        return true;
                    default:
                        return false;
                }
            }
        });
        // Handle dismissal with: popup.setOnDismissListener(...);
        // Show the menu
        popup.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imageInputHelper.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onImageSelectedFromGallery(Uri uri, File imageFile) {
        // cropping the selected image. crop intent will have aspect ratio 16/9 and result image
        // will have size 800x450
        imageInputHelper.requestCropImage(uri, 800, 800, 1, 1);
    }

    @Override
    public void onImageTakenFromCamera(Uri uri, File imageFile) {
        // cropping the taken photo. crop intent will have aspect ratio 16/9 and result image
        // will have size 800x450
        imageInputHelper.requestCropImage(uri, 800, 800, 1, 1);
    }

    @Override
    public void onImageCropped(Uri uri, File imageFile) {
        try {
            // getting bitmap from uri
            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);

            // showing bitmap in image view
            ((ImageView) findViewById(R.id.image)).setImageBitmap(bitmap);


        } catch (IOException e) {
            e.printStackTrace();
        }
       /* File filepath = Environment.getExternalStorageDirectory();

        // Create a new folder in SD Card
        File dir = new File(filepath.getAbsolutePath()
                + "/weHappening/");
        dir.mkdirs();

        OutputStream output;
        // Create a name for the saved image
        File file = new File(dir, "profile_image.png");

        // Show a toast message on successful save
        Toast.makeText(EditProfile.this, "Image Changed Successfully",
                Toast.LENGTH_SHORT).show();
        try {

            output = new FileOutputStream(file);

            // Compress into png format image from 0% - 100%
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, output);
            output.flush();
            output.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }*/
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}