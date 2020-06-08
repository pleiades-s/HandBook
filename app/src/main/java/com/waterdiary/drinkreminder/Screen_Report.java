package com.waterdiary.drinkreminder;

import android.content.Intent;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.viewpager.widget.ViewPager;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.waterdiary.drinkreminder.adapter.ReportPagerAdapter;
import com.waterdiary.drinkreminder.base.MasterBaseAppCompatActivity;
import com.waterdiary.drinkreminder.custom.NonSwipeableViewPager;

import java.util.ArrayList;


public class Screen_Report extends MasterBaseAppCompatActivity
{
	LinearLayout left_icon_block;
	private BarChart barChart;
	private DatabaseReference historyRef;
	private String user_id;
	private ArrayList<BarEntry> history_data = new ArrayList<>();
	private ArrayList dates = new ArrayList<>();
	private int idx = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.screen_report);
		left_icon_block = findViewById(R.id.left_icon_block);

		left_icon_block.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(getApplicationContext(), Screen_Dashboard.class);
				startActivity(intent);
			}
		});


		barChart = (BarChart)findViewById(R.id.barchart);

		// TODO: User id intent로 넘어와야 함.
//		FirebaseUser user = mAuth.getInstance().getCurrentUser();
//		String user_id = user.getUid();
		user_id = "user1";

		historyRef = FirebaseDatabase.getInstance().getReference("UserData/" + user_id + "/history");

		// TODO: 3. Firebase에서  History 정보 가져오기 -> Listener 함수에서 Graph 그리기
		getFirbase();
	}

	public String dateParser(String date){

		String[] dateArray = date.split("-");
		String parsedDate;
		switch(dateArray[1]) {
			case "01":
				parsedDate = "Jan ";
				break;

			case "02":
				parsedDate = "Feb ";
				break;

			case "03":
				parsedDate = "Mar ";
				break;

			case "04":
				parsedDate = "Apr ";
				break;

			case "05":
				parsedDate = "May ";
				break;

			case "06":
				parsedDate = "Jun ";
				break;

			case "07":
				parsedDate = "Jul ";
				break;

			case "08":
				parsedDate = "Aug ";
				break;

			case "09":
				parsedDate = "Sep ";
				break;

			case "10":
				parsedDate = "Oct ";
				break;

			case "11":
				parsedDate = "Nov ";
				break;

			case "12":
				parsedDate = "Dec ";
				break;

			default:
				parsedDate = "";

		}

			if (dateArray[2].charAt(0) == '0'){
				parsedDate += dateArray[2].substring(1);
			}
			else{
				parsedDate += dateArray[2];
			}


		return parsedDate;

	}

	public void getFirbase(){
		final ValueEventListener historyListener = new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {
				history_data.clear();
				dates.clear();

				for (DataSnapshot historySnapshot : dataSnapshot.getChildren()){

					String date = historySnapshot.getKey();
					Integer usage = historySnapshot.getValue(Integer.class);
					history_data.add(new BarEntry(idx, usage));
					dates.add(dateParser(date));
					idx++;
				}
				idx = 0;

				BarDataSet bardataset = new BarDataSet(history_data, "Usage (minutes)");
//				barChart.animateY(500);
				BarData data = new BarData(bardataset);
				bardataset.setColors(ColorTemplate.rgb("#0059ab")); // 색 통일하기
				barChart.setData(data);
				barChart.getDescription().setEnabled(false);

//				barChart.setDrawValueAboveBar(false);
//				barChart.getLegend().setEnabled(false);

				XAxis xAxis = barChart.getXAxis();
				xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
				IndexAxisValueFormatter formatter = new IndexAxisValueFormatter(dates);
				xAxis.setGranularity(1f);
				xAxis.setValueFormatter(formatter);
				barChart.getXAxis().setDrawGridLines(false);
				barChart.setData(data);
				barChart.setFitBars(true);
//				barChart.animateXY(5000, 5000);
				barChart.invalidate();



			}

			@Override
			public void onCancelled(DatabaseError databaseError) {

			}
		};

		historyRef.addValueEventListener(historyListener);
	}
}
