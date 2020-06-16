package com.haseebahmedsaeed.pollavote.ShareReport;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.haseebahmedsaeed.pollavote.R;
import com.haseebahmedsaeed.pollavote.RecyclerViewForPollReports.PollAdapter;
import com.haseebahmedsaeed.pollavote.RecyclerViewForPollReports.PollCardView;
import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;

public class ShareReports extends AppCompatActivity {

    // Declaration of Different DataTypes
    private static final int STORAGE_PERMISSION_CODE = 142;
    ArrayList<PollCardView> data;
    int position;

    // Declaration of Different Widgets
    TextView tvName, AdminName, tvSurname, Option1, Option2, Option3, Option4, percent1, percent2, percent3, percent4;
    ProgressBar progbar1, progbar2, progbar3, progbar4;
    Button captureSH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_reports);

        // Initialization of Different Widgets
        data = PollAdapter.people;
        position = PollAdapter.position;

        captureSH = findViewById(R.id.captureSH);
        AdminName = findViewById(R.id.AdminName);
        tvName = findViewById(R.id.tvName);
        tvSurname = findViewById(R.id.tvSurname);

        Option1 = findViewById(R.id.Option1);
        Option2 = findViewById(R.id.Option2);
        Option3 = findViewById(R.id.Option3);
        Option4 = findViewById(R.id.Option4);

        progbar1 = findViewById(R.id.progbar1);
        progbar2 = findViewById(R.id.progbar2);
        progbar3 = findViewById(R.id.progbar3);
        progbar4 = findViewById(R.id.progbar4);

        percent1 = findViewById(R.id.percent1);
        percent2 = findViewById(R.id.percent2);
        percent3 = findViewById(R.id.percent3);
        percent4 = findViewById(R.id.percent4);

        LoadAndSetData();

        // Take ScreenShot When Share Button Clicked
        captureSH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ContextCompat.checkSelfPermission(ShareReports.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                    takeScreenshot();
                } else{
                    requestStoragePermission();
                }
            }
        });
    }

    // Save or request Permission to save screenshot.
    private void requestStoragePermission(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)){

            new AlertDialog.Builder(this)
                    .setTitle("Permission Needed")
                    .setMessage("This Permission is Needed Because of Share")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(ShareReports.this,
                                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
                        }
                    })

                    .setNegativeButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create()
                    .show();
        }

        else{
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if( requestCode == STORAGE_PERMISSION_CODE ){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Capture and Save ScreenShot
    private void takeScreenshot() {
        Date now = new Date();
        android.text.format.DateFormat.format("mm:ss-dd-MM-yyyy", now);

        try {
            // image naming and path  to include sd card  appending name you choose for file
            String mPath = Environment.getExternalStorageDirectory().toString() + "/" + now + ".jpg";

            // create bitmap screen capture
            View v1 = getWindow().getDecorView().getRootView();
            v1.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
            v1.setDrawingCacheEnabled(false);

            File imageFile = new File(mPath);

            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();

            if(Build.VERSION.SDK_INT>=24){
                try{
                    Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                    m.invoke(null);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }

            shareIt(imageFile);
            openScreenshot(imageFile);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    // Open the saved ScreenShot
    private void openScreenshot(File imageFile) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(imageFile);
        intent.setDataAndType(uri, "image/*");
        startActivity(intent);
    }

    // It provides the Share features
    private void shareIt(File imageFile) {

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        Uri uri = Uri.fromFile(imageFile);
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("image/jpeg");
        String shareBody = "Polling Details";
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Detailed Statistics of Pollings");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        sharingIntent.putExtra(Intent.EXTRA_STREAM, uri);

        startActivity(Intent.createChooser(sharingIntent, "Share Via"));
    }

    public void LoadAndSetData() {
        tvName.setText((CharSequence) data.get(position).getName());
        tvSurname.setText((CharSequence) data.get(position).getSurname());

        Option1.setText(data.get(position).getOp1());
        Option2.setText(data.get(position).getOp2());
        Option3.setText(data.get(position).getOp3());
        Option4.setText(data.get(position).getOp4());

        int num1 = data.get(position).getOpNo1();
        int num2 = data.get(position).getOpNo2();
        int num3 = data.get(position).getOpNo3();
        int num4 = data.get(position).getOpNo4();
        int totalVotes = num1 + num2 + num3 + num4;

        percent1.setText(String.valueOf(num1));
        percent2.setText(String.valueOf(num2));
        percent3.setText(String.valueOf(num3));
        percent4.setText(String.valueOf(num4));

        if (totalVotes != 0) {
            progbar1.setProgress(((num1 * 100) / totalVotes));
            progbar2.setProgress(((num2 * 100) / totalVotes));
            progbar3.setProgress(((num3 * 100) / totalVotes));
            progbar4.setProgress(((num4 * 100) / totalVotes));
        }
    }

}
