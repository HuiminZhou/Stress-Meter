package com.example.huimin_zhou.huimin_zhou_stressmeter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Random;

/**
 * Created by Lucidity on 17/1/24.
 */

public class MyImageAdapter extends BaseAdapter {
    private Context mContext;
    private int set = -1;

    public static final Integer[] mImagesSet0 = {
        R.drawable.psm_kettle, R.drawable.psm_yoga4, R.drawable.psm_lawn_chairs3,
        R.drawable.psm_bird3, R.drawable.psm_blue_drop, R.drawable.psm_mountains11,
        R.drawable.psm_to_do_list, R.drawable.psm_wine3, R.drawable.psm_to_do_list3,
        R.drawable.psm_stressed_person8, R.drawable.psm_work4, R.drawable.psm_exam4,
        R.drawable.psm_stressed_person7, R.drawable.psm_barbed_wire2,
        R.drawable.psm_stressed_person6, R.drawable.psm_clutter
    };
    public static final Integer[] mImagesSet1 = {
        R.drawable.psm_beach3, R.drawable.psm_lake3, R.drawable.psm_peaceful_person,
        R.drawable.psm_cat, R.drawable.psm_gambling4, R.drawable.psm_talking_on_phone2,
        R.drawable.psm_clutter3, R.drawable.psm_stressed_person, R.drawable.psm_alarm_clock2,
        R.drawable.psm_puppy3, R.drawable.psm_sticky_notes2, R.drawable.psm_neutral_person2,
        R.drawable.psm_reading_in_bed2, R.drawable.psm_stressed_person12,
        R.drawable.psm_stressed_person4, R.drawable.psm_lonely
    };
    public static final Integer[] mImagesSet2 = {
        R.drawable.psm_bar, R.drawable.psm_baby_sleeping, R.drawable.psm_running3,
        R.drawable.psm_puppy, R.drawable.psm_dog_sleeping, R.drawable.psm_anxious,
        R.drawable.psm_running4, R.drawable.psm_hiking3, R.drawable.psm_neutral_child,
        R.drawable.psm_stressed_cat, R.drawable.psm_headache, R.drawable.psm_angry_face,
        R.drawable.psm_alarm_clock, R.drawable.psm_lonely2, R.drawable.psm_headache2,
        R.drawable.psm_stressed_person3
    };

    public MyImageAdapter(Context content) {
        Random random = new Random();
        set = (random.nextInt(2));
        mContext = content;
    }

    @Override
    public int getCount() {
        return mImagesSet0.length;
    }

    @Override
    // create a view for each image
    public View getView(int pos, View view, ViewGroup parent) {
        ImageView imageView;
        if (view == null) {
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(340, 340));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } else {
            imageView = (ImageView) view;
        }
        // determine which image set to visit
        switch (set) {
            case 0:
                imageView.setImageResource(mImagesSet0[pos]);
                break;
            case 1:
                imageView.setImageResource(mImagesSet1[pos]);
                break;
            case 2:
                imageView.setImageResource(mImagesSet2[pos]);
                break;
        }

        return imageView;
    }

    @Override
    public long getItemId(int pos) {
        return 0;
    }

    // all three sets take turn when MORE IMAGES button is clicked
    public void setNewSet() {
        if (set + 1 < 3) {
            set += 1;
        } else {
            set = 0;
        }
    }

    public int getSet() {
        return set;
    }

    @Override
    public Object getItem(int pos) {
        return null;
    }

}
