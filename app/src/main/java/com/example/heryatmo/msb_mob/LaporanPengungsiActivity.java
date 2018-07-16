package com.example.heryatmo.msb_mob;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class LaporanPengungsiActivity extends AppCompatActivity {

    private BarChart chPengungsi;
    private static float BAR_WIDTH = 0.9f;
    private static float GRANULARITY = 1f;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laporan_pengungsi);

        chPengungsi = findViewById(R.id.chartPengungsi);
        chPengungsi.setDragEnabled(true);
        chPengungsi.setScaleEnabled(false);
        chPengungsi.getDescription().setEnabled(false);

        ArrayList<BarEntry> values = new ArrayList<>();
        final ArrayList<String> title = new ArrayList<>();

        values.add(new BarEntry(0,500));
        values.add(new BarEntry(1,200));
        values.add(new BarEntry(2,400));
        values.add(new BarEntry(3,300));

        title.add("Anak-Anak");
        title.add("Dewasa");
        title.add("Manula");
        title.add("Disabilitas");

        BarDataSet dsSomething = new BarDataSet(values,"Logistik");
        dsSomething.setColors(ColorTemplate.COLORFUL_COLORS);

        BarData data = new BarData(dsSomething);
        data.setBarWidth(BAR_WIDTH);
        chPengungsi.setData(data);

        XAxis xAxis = chPengungsi.getXAxis();
        xAxis.setGranularity(GRANULARITY);
        xAxis.setGranularityEnabled(true);
        xAxis.setValueFormatter(new AxisValueFormatter(title));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
    }

    public class AxisValueFormatter implements IAxisValueFormatter {

        private ArrayList<String> values = new ArrayList<>();
        public AxisValueFormatter(ArrayList<String> values){
            this.values = values;
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            return values.get((int)value);
        }
    }
}
