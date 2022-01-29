package main;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.example.skylers.databinding.ActivityIdFrontBinding;
import com.example.skylers.utils.AppUtils;
import com.example.skylers.utils.PermissionTrigger;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ActivityIDFront extends AppCompatActivity {

    private ActivityIdFrontBinding binding;
    String imageFilePath, image_source = "id_front", id_front = "", id_back = "",
            storagePermissionRequired = "Please make sure Storage Permission are granted";
    Uri contentUri;
    private static final int CAPTURED_IMAGE = 110;
    public static final int REQUEST_READ_EXTERNAL_STORAGE = 111;
    public static final int MY_PERMISSIONS_CAMERA = 124;
    private static final int STORAGE_PERMISSION = 100;
    private static final int REQUEST_IMAGE = 130;

    Bitmap selectedBitmap = null;
    ByteArrayOutputStream bytes;
    byte[] byteArray;
    File camera_file, idFontFile;
    Random random;
    Bitmap bitmapIDFront;
    int selection = 1;

    @Inject
    PermissionTrigger permissionTrigger;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityIdFrontBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());

        getDateTime();

        random = new Random();

        setListener();
    }

    public void setListener(){

       /*binding.txtSubmit.setOnClickListener(view -> {
           launchCameraIntent();
       });
*/
        /*Intent intent = new Intent(ActivityIDFront.this, ActivityIDBack.class);
        startActivity(intent);*/

        binding.txtSubmit.setOnClickListener(v -> {
            if (binding.txtSubmit.getText().toString().equals("Take ID Front Photo")){
                image_source = "id_front";
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (AppUtils.confirmIfPermissionGranted(Manifest.permission.CAMERA, getApplicationContext())) {
                        if (AppUtils.confirmIfPermissionGranted(Manifest.permission.READ_EXTERNAL_STORAGE, getApplicationContext())){
                            openCameraIntent();


                        }else {
                            AppUtils.askForPermission(Manifest.permission.CAMERA, REQUEST_READ_EXTERNAL_STORAGE , ActivityIDFront.this);
                        }

                    }else{
                        AppUtils.askForPermission(Manifest.permission.CAMERA, MY_PERMISSIONS_CAMERA , ActivityIDFront.this);

                    }
                }else {
                    openCameraIntent();
                }
            } else if (binding.txtSubmit.getText().toString().equals("Take ID Back Photo") || binding.txtSubmit.getText().toString().equals("Proceed")){


                Intent intent = new Intent(ActivityIDFront.this, ActivityIDBack.class);
                startActivity(intent);

            }


        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        AppUtils.Log("result_code", ""+resultCode+" image_file_path"+imageFilePath);

        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                Uri uri = data.getParcelableExtra("path");
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);

                    binding.imgIdFront.setImageBitmap(bitmap);
                } catch (Exception e) {

                }
            }
        }

        if (resultCode == RESULT_OK) {
            if (requestCode == CAPTURED_IMAGE) {
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


                        camera_file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), 1000 + random.nextInt(9000) + "" + System.currentTimeMillis() + ".jpg");
                        FileOutputStream fo;

                        try {
                            camera_file.createNewFile();
                            fo = new FileOutputStream(camera_file);
                            fo.write(bytes.toByteArray());
                            fo.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        idFontFile = camera_file;
                        binding.imgIdFront.setImageBitmap(selectedBitmap);
                        bitmapIDFront = selectedBitmap;


                        selection = 1;

                        generateBitmap(selectedBitmap, imageFilePath);

                        //Uploading ID Front


                    }
                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    Exception error = result.getError();
                    AppUtils.ToastMessage(error.getMessage(), ActivityIDFront.this);
                }
            }


        }else {
            Toast.makeText(ActivityIDFront.this, "You haven't picked Image",Toast.LENGTH_LONG).show();
        }
    }

    private void performCrop(Uri picUri, String path) {
        try{
            CropImage.activity(picUri).start(this);
        }catch (Exception e){
            CropImage.activity(picUri).start(this);

            AppUtils.Log("exception", e.getMessage());
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
                contentUri = FileProvider.getUriForFile(ActivityIDFront.this, "com.example.skylers", photoFile);
                pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);

                startActivityForResult(pictureIntent, CAPTURED_IMAGE);

            }

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case MY_PERMISSIONS_CAMERA:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openCameraIntent();
                }
                break;

            case REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openCameraIntent();
                } else {
                    AppUtils.ToastMessage(storagePermissionRequired, ActivityIDFront.this);
                }
                break;

        }
    }

    private File createImageFile() throws IOException {
        String timeStamp =
                new SimpleDateFormat("yyyyMMdd_HHmmss",
                        Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";

        AppUtils.Log("file_name", imageFileName);

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

    private File generateBitmap(Bitmap bitmap, String path) {
        File file = null;
        if (bitmap != null) {
            file = new File(path);
        }

        try {
            FileOutputStream outputStream = null;
            try {
                outputStream = new FileOutputStream(path); //here is set your file path where you want to save or also here you can set file object directly

                bitmap.compress(Bitmap.CompressFormat.JPEG, 30, outputStream); // bitmap is your Bitmap instance, if you want to compress it you can compress reduce percentage
                // PNG is a lossless format, the compression factor (100) is ignored
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (outputStream != null) {
                        outputStream.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        int file_size = Integer.parseInt(String.valueOf(file.length()/1024));
        AppUtils.Log("image_size", "generateBitmap: "+getFileSize(file));
        return file;
    }

    public static String getFileSize(File file) {
        String modifiedFileSize = null;
        double fileSize = 0.0;
        if (file.isFile()) {
            fileSize = (double) file.length();//in Bytes

            if (fileSize < 1024) {
                modifiedFileSize = String.valueOf(fileSize).concat("B");
            } else if (fileSize > 1024 && fileSize < (1024 * 1024)) {
                modifiedFileSize = String.valueOf(Math.round((fileSize / 1024 * 100.0)) / 100.0).concat("KB");
            } else {
                modifiedFileSize = String.valueOf(Math.round((fileSize / (1024 * 1204) * 100.0)) / 100.0).concat("MB");
            }
        } else {
            modifiedFileSize = "Unknown";
        }

        return modifiedFileSize;
    }

    public String getDateTime(){
        String time = "";
        try {

            final Date dateObj = new Date();
            time = new SimpleDateFormat("HH:mm").format(dateObj);

            AppUtils.Log("date_time", time);

        } catch (Exception e){

        }

        return time;
    }

    private void launchCameraIntent() {
        Intent intent = new Intent(ActivityIDFront.this, ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_IMAGE_CAPTURE);
        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);
        // setting maximum bitmap width and height
        intent.putExtra(ImagePickerActivity.INTENT_SET_BITMAP_MAX_WIDTH_HEIGHT, true);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_WIDTH, 1000);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_HEIGHT, 1000);
        startActivityForResult(intent, REQUEST_IMAGE);
    }

}


