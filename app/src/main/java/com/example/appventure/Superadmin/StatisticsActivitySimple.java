package com.example.appventure.Superadmin;

import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.appventure.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.*;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;

public class StatisticsActivitySimple extends AppCompatActivity {
    
    private PieChart pieChartUsers;
    private BarChart barChartReservations;
    private PieChart pieChartLogs;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics_superladmin);
        
        initViews();
        setupCharts();
    }
    
    private void initViews() {
        pieChartUsers = findViewById(R.id.pieChartUsers);
        barChartReservations = findViewById(R.id.barChartReservations);
        pieChartLogs = findViewById(R.id.pieChartLogs);
    }
    
    private void setupCharts() {
        setupUsersChart();
        setupReservationsChart();
        setupLogsChart();
    }
    
    private void setupUsersChart() {
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(2387f, "Clientes"));
        entries.add(new PieEntry(156f, "Guías"));
        entries.add(new PieEntry(47f, "Empresas"));
        
        PieDataSet dataSet = new PieDataSet(entries, "Tipos de Usuario");
        
        int[] colors = {
            Color.parseColor("#2D9596"),
            Color.parseColor("#4CAF50"),
            Color.parseColor("#FF9800")
        };
        dataSet.setColors(colors);
        dataSet.setValueTextSize(12f);
        dataSet.setValueTextColor(Color.WHITE);
        
        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter(pieChartUsers));
        
        pieChartUsers.setData(data);
        pieChartUsers.setUsePercentValues(true);
        
        Description desc = new Description();
        desc.setEnabled(false);
        pieChartUsers.setDescription(desc);
        
        pieChartUsers.setDrawHoleEnabled(true);
        pieChartUsers.setHoleColor(Color.WHITE);
        pieChartUsers.setHoleRadius(50f);
        pieChartUsers.setTransparentCircleRadius(55f);
        
        pieChartUsers.setCenterText("Usuarios\nRegistrados");
        pieChartUsers.setCenterTextSize(14f);
        pieChartUsers.setCenterTextColor(Color.BLACK);
        
        Legend legend = pieChartUsers.getLegend();
        legend.setEnabled(true);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        
        pieChartUsers.animateY(1000);
        pieChartUsers.invalidate();
    }
    
    private void setupReservationsChart() {
        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0f, 45f));
        entries.add(new BarEntry(1f, 67f));
        entries.add(new BarEntry(2f, 89f));
        entries.add(new BarEntry(3f, 123f));
        entries.add(new BarEntry(4f, 98f));
        entries.add(new BarEntry(5f, 156f));
        
        BarDataSet dataSet = new BarDataSet(entries, "Reservas por Mes");
        dataSet.setColor(Color.parseColor("#2D9596"));
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setValueTextSize(10f);
        
        BarData data = new BarData(dataSet);
        data.setBarWidth(0.8f);
        
        barChartReservations.setData(data);
        
        Description desc = new Description();
        desc.setEnabled(false);
        barChartReservations.setDescription(desc);
        
        barChartReservations.setDrawGridBackground(false);
        barChartReservations.setFitBars(true);
        
        String[] months = {"Ene", "Feb", "Mar", "Abr", "May", "Jun"};
        XAxis xAxis = barChartReservations.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(months));
        xAxis.setDrawGridLines(false);
        
        YAxis leftAxis = barChartReservations.getAxisLeft();
        leftAxis.setAxisMinimum(0f);
        
        YAxis rightAxis = barChartReservations.getAxisRight();
        rightAxis.setEnabled(false);
        
        barChartReservations.animateY(1000);
        barChartReservations.invalidate();
    }
    
    private void setupLogsChart() {
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(1234f, "Login"));
        entries.add(new PieEntry(567f, "Reservas"));
        entries.add(new PieEntry(890f, "Búsquedas"));
        entries.add(new PieEntry(345f, "Errores"));
        
        PieDataSet dataSet = new PieDataSet(entries, "Actividad del Sistema");
        
        int[] colors = {
            Color.parseColor("#4CAF50"),
            Color.parseColor("#2196F3"),
            Color.parseColor("#FF9800"),
            Color.parseColor("#F44336")
        };
        dataSet.setColors(colors);
        dataSet.setValueTextSize(12f);
        dataSet.setValueTextColor(Color.WHITE);
        
        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter(pieChartLogs));
        
        pieChartLogs.setData(data);
        pieChartLogs.setUsePercentValues(true);
        
        Description desc = new Description();
        desc.setEnabled(false);
        pieChartLogs.setDescription(desc);
        
        pieChartLogs.setDrawHoleEnabled(true);
        pieChartLogs.setHoleColor(Color.WHITE);
        pieChartLogs.setHoleRadius(40f);
        pieChartLogs.setTransparentCircleRadius(45f);
        
        pieChartLogs.setCenterText("Logs\nSistema");
        pieChartLogs.setCenterTextSize(12f);
        pieChartLogs.setCenterTextColor(Color.BLACK);
        
        Legend legend = pieChartLogs.getLegend();
        legend.setEnabled(true);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        
        pieChartLogs.animateY(1000);
        pieChartLogs.invalidate();
    }
}