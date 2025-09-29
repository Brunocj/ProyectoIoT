package com.example.appventure.Superadmin;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.appventure.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.util.ArrayList;

public class StatisticsActivitySuperladmin extends AppCompatActivity {

    private ImageButton buttonBack;
    private PieChart pieChartUsers;
    private BarChart barChartReservations;
    private PieChart pieChartLogs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics_superladmin);

        initializeViews();
        setupClickListeners();
        setupCharts();
    }

    private void initializeViews() {
        buttonBack = findViewById(R.id.buttonBack);
        pieChartUsers = findViewById(R.id.pieChartUsers);
        barChartReservations = findViewById(R.id.barChartReservations);
        pieChartLogs = findViewById(R.id.pieChartLogs);
    }

    private void setupClickListeners() {
        buttonBack.setOnClickListener(v -> finish());
    }

    private void setupCharts() {
        setupUsersChart();
        setupReservationsChart();
        setupLogsChart();
    }

    private void setupUsersChart() {
        if (pieChartUsers == null) return;
        
        // Datos de ejemplo para usuarios
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(2387f, "Clientes"));
        entries.add(new PieEntry(156f, "Guías"));
        entries.add(new PieEntry(47f, "Empresas"));

        PieDataSet dataSet = new PieDataSet(entries, "Tipos de Usuario");
        
        // Colores personalizados más básicos para evitar errores
        int[] colors = {
            Color.parseColor("#2D9596"), // Teal primary
            Color.parseColor("#4CAF50"), // Green success
            Color.parseColor("#FF9800")  // Orange warning
        };
        dataSet.setColors(colors);
        
        dataSet.setValueTextSize(14f);
        dataSet.setValueTextColor(Color.WHITE);
        dataSet.setValueFormatter(new PercentFormatter(pieChartUsers));

        PieData data = new PieData(dataSet);
        pieChartUsers.setData(data);

        // Configuración del chart
        pieChartUsers.setUsePercentValues(true);
        
        Description description = new Description();
        description.setEnabled(false);
        pieChartUsers.setDescription(description);
        
        pieChartUsers.setDrawHoleEnabled(true);
        pieChartUsers.setHoleColor(Color.WHITE);
        pieChartUsers.setHoleRadius(50f);
        pieChartUsers.setTransparentCircleRadius(55f);
        pieChartUsers.setDrawCenterText(true);
        pieChartUsers.setCenterText("Usuarios\nRegistrados");
        pieChartUsers.setCenterTextSize(16f);
        pieChartUsers.setCenterTextColor(Color.BLACK);

        // Configurar leyenda
        Legend legend = pieChartUsers.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(false);
        legend.setEnabled(true);

        pieChartUsers.animateY(1000);
        pieChartUsers.invalidate();
    }

    private void setupReservationsChart() {
        // Datos de ejemplo para reservas mensuales
        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0f, 1240f)); // Enero
        entries.add(new BarEntry(1f, 1580f)); // Febrero
        entries.add(new BarEntry(2f, 1890f)); // Marzo
        entries.add(new BarEntry(3f, 1650f)); // Abril
        entries.add(new BarEntry(4f, 2140f)); // Mayo
        entries.add(new BarEntry(5f, 1980f)); // Junio

        BarDataSet dataSet = new BarDataSet(entries, "Reservas");
        dataSet.setColor(ContextCompat.getColor(this, R.color.primary_color));
        dataSet.setValueTextColor(ContextCompat.getColor(this, R.color.black));
        dataSet.setValueTextSize(12f);

        BarData data = new BarData(dataSet);
        data.setBarWidth(0.9f);
        
        barChartReservations.setData(data);

        // Configuración del chart
        barChartReservations.getDescription().setEnabled(false);
        barChartReservations.setDrawGridBackground(false);
        barChartReservations.setDrawBorders(false);

        // Configurar eje X
        XAxis xAxis = barChartReservations.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(6);
        String[] months = {"Ene", "Feb", "Mar", "Abr", "May", "Jun"};
        xAxis.setValueFormatter(new IndexAxisValueFormatter(months));

        // Configurar eje Y izquierdo
        barChartReservations.getAxisLeft().setDrawGridLines(true);
        barChartReservations.getAxisLeft().setAxisMinimum(0f);

        // Deshabilitar eje Y derecho
        barChartReservations.getAxisRight().setEnabled(false);

        // Configurar leyenda
        Legend legend = barChartReservations.getLegend();
        legend.setEnabled(false);

        barChartReservations.animateY(1400);
        barChartReservations.invalidate();
    }

    private void setupLogsChart() {
        // Datos de ejemplo para logs
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(65.2f, "INFO"));
        entries.add(new PieEntry(18.7f, "WARNING"));
        entries.add(new PieEntry(12.4f, "ERROR"));
        entries.add(new PieEntry(3.7f, "DEBUG"));

        PieDataSet dataSet = new PieDataSet(entries, "Logs por Prioridad");
        
        // Colores para diferentes tipos de logs
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(ContextCompat.getColor(this, R.color.success_color)); // INFO
        colors.add(ContextCompat.getColor(this, R.color.warning_color)); // WARNING
        colors.add(ContextCompat.getColor(this, R.color.error_color)); // ERROR
        colors.add(ContextCompat.getColor(this, R.color.primary_color)); // DEBUG
        dataSet.setColors(colors);
        
        dataSet.setValueTextSize(12f);
        dataSet.setValueTextColor(Color.WHITE);
        dataSet.setValueFormatter(new PercentFormatter());

        PieData data = new PieData(dataSet);
        pieChartLogs.setData(data);

        // Configuración del chart
        pieChartLogs.setUsePercentValues(true);
        pieChartLogs.getDescription().setEnabled(false);
        pieChartLogs.setDrawHoleEnabled(true);
        pieChartLogs.setHoleColor(Color.WHITE);
        pieChartLogs.setHoleRadius(58f);
        pieChartLogs.setTransparentCircleRadius(61f);
        pieChartLogs.setDrawCenterText(true);
        pieChartLogs.setCenterText("Logs\nSistema");
        pieChartLogs.setCenterTextSize(14f);
        pieChartLogs.setCenterTextColor(ContextCompat.getColor(this, R.color.black));

        // Configurar leyenda
        Legend legend = pieChartLogs.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(false);
        legend.setXEntrySpace(7f);
        legend.setYEntrySpace(0f);
        legend.setYOffset(0f);

        pieChartLogs.animateY(1400);
        pieChartLogs.invalidate();
    }
}