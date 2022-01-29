package main;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import com.example.skylers.databinding.ActivityIdBackBinding;
import com.example.skylers.utils.AppUtils;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import static com.example.skylers.utils.AppUtils.MY_PERMISSIONS_CAMERA;
import static com.example.skylers.utils.AppUtils.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE;


public class ActivityIDBack extends AppCompatActivity {

    private ActivityIdBackBinding binding;
    String imageFilePath, image_source = "id_back";
    Uri contentUri;
    Bitmap selectedBitmap = null;
    ByteArrayOutputStream bytes;
    byte[] byteArray;
    Random random;
    File camera_file, idFontFile;
    Bitmap bitmapIDBack;
    private static final int REQUEST_CAPTURE_IMAGE = 110;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityIdBackBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());

        random = new Random();

        setListeners();

    }

    public void setListeners(){
        Intent intent = new Intent(ActivityIDBack.this, ActivityRegistrationDetails.class);
        startActivity(intent);


        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            AppUtils.Log("reached_here", "reached_here3");

            if (AppUtils.confirmIfPermissionGranted(Manifest.permission.CAMERA, getApplicationContext())) {
                if (AppUtils.confirmIfPermissionGranted(Manifest.permission.READ_EXTERNAL_STORAGE, getApplicationContext())){
                    openCameraIntent();


                }else {
                    AppUtils.askForPermission(Manifest.permission.CAMERA, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE , ActivityIDBack.this);
                }

            }else{
                AppUtils.askForPermission(Manifest.permission.CAMERA, MY_PERMISSIONS_CAMERA , ActivityIDBack.this);

            }
        }else {
            openCameraIntent();
            AppUtils.Log("reached_here", "reached_here4");
        }*/
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_CAMERA:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openCameraIntent();
                }
                break;

            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openCameraIntent();
                } else {
                    AppUtils.ToastMessage("Storage permissions required. Please enable it in settings", ActivityIDBack.this);
                }

                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CAPTURE_IMAGE) {
                performCrop(contentUri, imageFilePath);
            } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                if (resultCode == RESULT_OK) {
                    Uri resultUri = result.getUri();
                    if (data != null) {

                        try {
                            selectedBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), resultUri);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        bytes = new ByteArrayOutputStream();
                        selectedBitmap.compress(Bitmap.CompressFormat.JPEG, 30, bytes);

                        byteArray = bytes.toByteArray();

                        long lengthbmp = byteArray.length;

                        AppUtils.Log("selected_image_size", Long.toString(lengthbmp));


                        camera_file = new File(Environment.getExternalStorageDirectory(), 1000 + random.nextInt(9000) + "" + System.currentTimeMillis() + ".jpg");
                        FileOutputStream fo;

                        try {
                            camera_file.createNewFile();
                            fo = new FileOutputStream(camera_file);
                            fo.write(bytes.toByteArray());
                            fo.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        //if (image_source.equals("id_back")){
                        idFontFile = camera_file;
                        binding.imgIdBack.setImageBitmap(selectedBitmap);
                        bitmapIDBack = selectedBitmap;


                        //selection = 2;

                        //Uploading ID Back
                        //uploadImage(byteArray, selection);
                        //}

                    }
                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    Exception error = result.getError();
                    AppUtils.ToastMessage(error.getMessage(), ActivityIDBack.this);
                }
            }


        }else {
            Toast.makeText(ActivityIDBack.this, "You haven't picked Image",Toast.LENGTH_LONG).show();
        }
    }

    private void openCameraIntent() {
        Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            pictureIntent.putExtra("android.intent.extras.LENS_FACING_FRONT", 1);
        } else {
            pictureIntent.putExtra("android.intent.extras.CAMERA_FACING", 1);
        }

        if(pictureIntent.resolveActivity(getPackageManager()) != null){
            //Create a file to store the image
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            if (photoFile != null) {
                contentUri = FileProvider.getUriForFile(getApplicationContext(), "com.example.skylers", photoFile);
                pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);

                startActivityForResult(pictureIntent, REQUEST_CAPTURE_IMAGE);

            }

        }
    }

    private File createImageFile() throws IOException {
        String timeStamp =
                new SimpleDateFormat("yyyyMMdd_HHmmss",
                        Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir =
                getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        imageFilePath = image.getAbsolutePath();
        return image;
    }

    private void performCrop(Uri picUri, String path) {
        try{
            CropImage.activity(picUri).start(this);
            //CropImage.activity(picUri).start(this);
        }catch (Exception e){
            CropImage.activity(picUri).start(this);
        }
    }
}
