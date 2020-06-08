package com.waterdiary.drinkreminder;

import android.os.Bundle;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.waterdiary.drinkreminder.base.MasterBaseActivity;
import java.util.ArrayList;

public class handbook_stats extends MasterBaseActivity {

    private BarChart barChart;
    private DatabaseReference historyRef;
    private String user_id;
    private ArrayList<BarEntry> history_data = new ArrayList<>();
    private ArrayList<String> dates = new ArrayList<>();
    private int idx = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.handbook_stats);

        barChart = (BarChart)findViewById(R.id.chart);

        // TODO: User id intent로 넘어와야 함.
        user_id = "user1";

        historyRef = FirebaseDatabase.getInstance().getReference("UserData/" + user_id + "history");

        // TODO: 1. Toogle Switch 처리

        // TODO: 2. Bell image intent 처리

        // TODO: 3. Firebase에서  History 정보 가져오기 -> Listener 함수에서 Graph 그리기

    }

    public void getFirbase(){
        final ValueEventListener historyListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                history_data.clear();

                for (DataSnapshot historySnapshot : dataSnapshot.getChildren()){

                    String date = historySnapshot.getKey();
                    Integer usage = historySnapshot.getValue(Integer.class);
                    history_data.add(new BarEntry(usage, idx));
                    dates.add(date);
                    idx++;
                }
                idx = 0;

                BarDataSet bardataset = new BarDataSet(history_data, "스마트폰 들고 있었던 시간");
                barChart.animateY(5000);
                BarData data = new BarData((IBarDataSet) dates, bardataset);
                bardataset.setColors(ColorTemplate.COLORFUL_COLORS);
                barChart.setData(data);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
    }

}
