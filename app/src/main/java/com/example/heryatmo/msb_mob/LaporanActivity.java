package com.example.heryatmo.msb_mob;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.heryatmo.msb_mob.model.Logistik;
import com.example.heryatmo.msb_mob.remote.APIService;
import com.example.heryatmo.msb_mob.remote.RetroClient;
import com.example.heryatmo.msb_mob.response.LogistikResponse;
import com.example.heryatmo.msb_mob.response.TampilLogistikResponse;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LaporanActivity extends AppCompatActivity {

    private BarChart chartLaporan;
    private static float BAR_WIDTH = 0.9f;
    private static float GRANULARITY = 1f;

    List<Logistik> logistik = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laporan);

        chartLaporan = findViewById(R.id.chartLaporan);
        chartLaporan.setDragEnabled(true);
        chartLaporan.setScaleEnabled(false);
        chartLaporan.getDescription().setEnabled(false);

        ArrayList<BarEntry> values = new ArrayList<>();
        final ArrayList<String> title = new ArrayList<>();
        /*
        * dataAPI = getDataAPI
        * for(int i=0;i<dataAPI.size();i++)
        * {
        *   values.add(new BarEntry(i,dataAPI.get(i).getMJumlah());
        *   title.add(dataAPI.get(i).getMNamaLogistik());
        * }
        * */

        Retrofit retrofit = RetroClient.getClient();

        APIService request = retrofit.create(APIService.class);
        Call<TampilLogistikResponse> call = request.getLogistik();
        call.enqueue(new Callback<TampilLogistikResponse>() {
            @Override
            public void onResponse(Call<TampilLogistikResponse> call, Response<TampilLogistikResponse> response) {
                logistik = response.body().getMData();
                for(int i=0; i<logistik.size();i++)
                {
//                    values.add(new BarEntry(i,logistik.get(i).getMJumlahLogistik()));
                    title.add(logistik.get(i).getMNamaLogistik());
                }
            }

            @Override
            public void onFailure(Call<TampilLogistikResponse> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });

        values.add(new BarEntry(0,500));
        values.add(new BarEntry(1,200));
        values.add(new BarEntry(2,400));
        values.add(new BarEntry(3,300));

        title.add("Mie");
        title.add("Beras");
        title.add("Minyak");
        title.add("Susu");

        BarDataSet dsSomething = new BarDataSet(values,"Logistik");
        dsSomething.setColors(ColorTemplate.COLORFUL_COLORS);

        BarData data = new BarData(dsSomething);
        data.setBarWidth(BAR_WIDTH);
        chartLaporan.setData(data);

        XAxis xAxis = chartLaporan.getXAxis();
        xAxis.setGranularity(GRANULARITY);
        xAxis.setGranularityEnabled(true);
        xAxis.setValueFormatter(new AxisValueFormatter(title));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
    }

    public class AxisValueFormatter implements IAxisValueFormatter{

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
