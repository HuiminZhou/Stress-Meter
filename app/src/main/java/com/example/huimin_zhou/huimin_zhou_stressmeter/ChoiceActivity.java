package com.example.huimin_zhou.huimin_zhou_stressmeter;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

/**
 * Created by Lucidity on 17/1/24.
 */

public class ChoiceActivity extends AppCompatActivity{
    public final int[] scores = new int[] {
            1,  2,  3,  4,
            5,  6,  7,  8,
            9,  10, 11, 12,
            13, 14, 15, 16};
    private int curscore = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);
        ImageView imageView = (ImageView)findViewById(R.id.image_choice);
        Intent intent = getIntent();

        // get the index of image set, and the index of the image
        int ind_item = intent.getIntExtra(ImageFragment.ITEM_NUM, 0);
        int ind_set = intent.getIntExtra(ImageFragment.SET_NUM, 0);
        curscore = scores[ind_item];
        // Toast.makeText(getApplicationContext(), "" + ind_set, Toast.LENGTH_SHORT).show();
        // Toast.makeText(getApplicationContext(), "" + ind_item, Toast.LENGTH_SHORT).show();

        // set the selected image to view
        switch (ind_set) {
            case 0:
                imageView.setImageResource(MyImageAdapter.mImagesSet0[ind_item]);
                break;
            case 1:
                imageView.setImageResource(MyImageAdapter.mImagesSet1[ind_item]);
                break;
            case 2:
                imageView.setImageResource(MyImageAdapter.mImagesSet2[ind_item]);
                break;
        }
    }

    // write the score of current image into a file
    public void OnClickSubmit(View view) throws IOException {
        File file = new File(Environment.getExternalStorageDirectory(), "stress_value.csv");
        if (!file.exists()) {
            file.createNewFile();
        }
        FileWriter fw = new FileWriter(file, true);
        BufferedWriter bw = new BufferedWriter(fw);
        Date dt = new Date();
        bw.append(Long.toString(dt.getTime()));
        bw.append(",");
        bw.append(Integer.toString(curscore));
        bw.newLine();

        bw.flush();
        bw.close();
        Intent intent = new Intent();
        setResult(MainActivity.RESULT_OK, intent);
        finish();
    }

    // return to the calling activity
    public void OnClickCancel(View view) {
        Intent intent = new Intent();
        setResult(RESULT_CANCELED, intent);
        finish();
    }
}
