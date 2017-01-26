package com.example.huimin_zhou.huimin_zhou_stressmeter;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

/**
 * Created by Lucidity on 17/1/24.
 */

public class ImageFragment extends Fragment implements Button.OnClickListener {
    public static final String ITEM_NUM = "item_num";
    public static final String SET_NUM = "set_num";
    private MyImageAdapter adapter = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image, container, false);

        GridView gridView = (GridView)view.findViewById(R.id.grid_view);
        adapter = new MyImageAdapter(getActivity());
        gridView.setAdapter(adapter);

        Button button = (Button) view.findViewById(R.id.btn_more);
        button.setOnClickListener(this);

        // onclick for gridview images
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ChoiceActivity.class);
                intent.putExtra(SET_NUM, adapter.getSet());
                intent.putExtra(ITEM_NUM, position);
                startActivityForResult(intent, 1);
            }
        });

        return view;
    }

    @Override
    public void onClick(View v) {
        MainActivity.manager.cancel(MainActivity.NOTIFICATION_ID); // stop the notification sound
        adapter.setNewSet();
        adapter.notifyDataSetChanged();
    }

    //@Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == MainActivity.RESULT_OK) {
            getActivity().finish();
        }
    }
}
