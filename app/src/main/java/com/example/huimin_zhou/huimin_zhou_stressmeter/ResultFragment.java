package com.example.huimin_zhou.huimin_zhou_stressmeter;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import lecho.lib.hellocharts.formatter.SimpleAxisValueFormatter;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;

/**
 * Created by Lucidity on 17/1/24.
 */

public class ResultFragment extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        View view = inflater.inflate(R.layout.fragment_result, container, false);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        BufferedReader br = null;
        String content = "";
        ArrayList<PointValue> pointArray = new ArrayList<>();
        ArrayList<AxisValue> axisValuesForX = new ArrayList<AxisValue>();
        ArrayList<AxisValue> axisValuesForY = new ArrayList<AxisValue>();

        File file = new File(Environment.getExternalStorageDirectory(), "stress_value.csv");
        try {
            if (!file.exists()) {
                file.createNewFile();
            } else {
                TableLayout table = (TableLayout) getActivity().findViewById(R.id.table);
                TableRow row = null;
                TextView textTime = null, textStress = null;
                int starttime = 0;

                br = new BufferedReader(new FileReader(file));
                while ((content = br.readLine()) != null) {
                    // get data from csv, separated by ","
                    String[] output = content.split(",");

                    // chart
                    float y = Float.valueOf(output[1]);
                    pointArray.add(new PointValue(starttime, y));
                    axisValuesForX.add(new AxisValue(starttime));
                    axisValuesForY.add(new AxisValue(y));
                    starttime += 1;

                    // table
                    row         = new TableRow(getActivity());
                    textTime    = new TextView(getActivity());
                    textStress  = new TextView(getActivity());
                    textTime.setText(" " + output[0]);
                    textStress.setText(" " + output[1]);
                    textTime.setBackgroundResource(R.drawable.cell);
                    textStress.setBackgroundResource(R.drawable.cell);
                    row.setLayoutParams(new TableRow.LayoutParams(
                            TableRow.LayoutParams.MATCH_PARENT,
                            TableRow.LayoutParams.MATCH_PARENT));
                    row.addView(textTime, new TableRow.LayoutParams(
                            TableRow.LayoutParams.MATCH_PARENT,
                            TableRow.LayoutParams.WRAP_CONTENT, 1.0f));
                    row.addView(textStress, new TableRow.LayoutParams(
                            TableRow.LayoutParams.MATCH_PARENT,
                            TableRow.LayoutParams.WRAP_CONTENT, 1.0f));
                    table.addView(row, new TableLayout.LayoutParams(
                            TableLayout.LayoutParams.MATCH_PARENT,
                            TableLayout.LayoutParams.WRAP_CONTENT));
                }

                // chart
                Line line = new Line(pointArray).setFilled(true).setColor(Color.BLUE);
                ArrayList<Line> lineArray = new ArrayList<>();
                lineArray.add(line);
                LineChartData data = new LineChartData();
                data.setLines(lineArray);
                Axis axeX = new Axis(axisValuesForX);
                Axis axeY = new Axis(axisValuesForY);

                data.setAxisXBottom(axeX);
                data.setAxisYLeft(axeY);
                axeX.setLineColor(Color.BLACK);
                axeY.setLineColor(Color.BLACK);
                axeX.setName("Instances");
                axeY.setName("Stress Level");
                LineChartView chartView = (LineChartView) getActivity().findViewById(R.id.chart);
                chartView.setLineChartData(data);
                Viewport vport = new Viewport(chartView.getMaximumViewport());
                vport.top =17; //example max value
                vport.bottom = 0;  //example min value
                chartView.setMaximumViewport(vport);
                chartView.setCurrentViewport(vport);
                chartView.setViewportCalculationEnabled(false);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
