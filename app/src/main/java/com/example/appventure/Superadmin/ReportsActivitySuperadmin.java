package com.example.appventure.Superadmin;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appventure.R;
import com.example.appventure.Superadmin.Adapter.CompanyReportAdapter;
import com.example.appventure.Superadmin.Model.CompanyReport;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.List;

public class ReportsActivitySuperadmin extends AppCompatActivity implements CompanyReportAdapter.OnReportClickListener {
    
    private ImageButton buttonBack;
    private ChipGroup chipGroupPeriod;
    private Chip chipToday, chipWeek, chipMonth, chipYear;
    
    // Métricas generales
    private TextView textViewTotalRevenue;
    private TextView textViewCompletedReservations;
    
    // Botones de exportación
    private MaterialButton buttonExportPdf;
    private MaterialButton buttonExportExcel;
    
    // RecyclerView components
    private RecyclerView recyclerViewCompanyReports;
    private CompanyReportAdapter reportAdapter;
    private List<CompanyReport> companyReports;
    
    // Período seleccionado
    private String selectedPeriod = "TODAY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports_superadmin);
        
        initViews();
        setupClickListeners();
        loadReportData();
    }

    private void initViews() {
        buttonBack = findViewById(R.id.buttonBack);
        chipGroupPeriod = findViewById(R.id.chipGroupPeriod);
        chipToday = findViewById(R.id.chipToday);
        chipWeek = findViewById(R.id.chipWeek);
        chipMonth = findViewById(R.id.chipMonth);
        chipYear = findViewById(R.id.chipYear);
        
        textViewTotalRevenue = findViewById(R.id.textViewTotalRevenue);
        textViewCompletedReservations = findViewById(R.id.textViewCompletedReservations);
        
        buttonExportPdf = findViewById(R.id.buttonExport);
        // buttonExportExcel no está en el layout actual
        
        // Initialize RecyclerView
        recyclerViewCompanyReports = findViewById(R.id.recyclerViewCompanyReports);
        setupRecyclerView();
    }
    
    private void setupRecyclerView() {
        if (recyclerViewCompanyReports == null) {
            android.util.Log.e("ReportsActivity", "RecyclerView not found! Check XML layout.");
            return;
        }
        
        companyReports = new ArrayList<>();
        reportAdapter = new CompanyReportAdapter(companyReports);
        reportAdapter.setOnReportClickListener(this);
        
        recyclerViewCompanyReports.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewCompanyReports.setAdapter(reportAdapter);
    }

    private void setupClickListeners() {
        // Botón de volver
        buttonBack.setOnClickListener(v -> finish());
        
        // Chips de período
        chipGroupPeriod.setOnCheckedStateChangeListener((group, checkedIds) -> {
            if (!checkedIds.isEmpty()) {
                int checkedId = checkedIds.get(0);
                
                if (checkedId == R.id.chipToday) {
                    selectedPeriod = "TODAY";
                } else if (checkedId == R.id.chipWeek) {
                    selectedPeriod = "WEEK";
                } else if (checkedId == R.id.chipMonth) {
                    selectedPeriod = "MONTH";
                } else if (checkedId == R.id.chipYear) {
                    selectedPeriod = "YEAR";
                }
                
                // Recargar datos según el período seleccionado
                loadReportData();
            }
        });
        
        // Botón de exportación
        if (buttonExportPdf != null) {
            buttonExportPdf.setOnClickListener(v -> {
                exportToPdf();
            });
        }
        
        // buttonExportExcel no está disponible en el layout actual
        /*
        buttonExportExcel.setOnClickListener(v -> {
            exportToExcel();
        });
        */
    }

    private void loadReportData() {
        // Cargar datos según el período seleccionado
        ReportData data = generateReportData(selectedPeriod);
        
        // Actualizar métricas en la UI
        textViewTotalRevenue.setText(data.getTotalRevenue());
        textViewCompletedReservations.setText(String.valueOf(data.getCompletedReservations()));
        
        // Cargar datos de empresas en RecyclerView
        loadSampleReportData();
    }

    private ReportData generateReportData(String period) {
        // Simular datos basados en el período
        switch (period) {
            case "TODAY":
                return new ReportData("S/2,450", 89);
            case "WEEK":
                return new ReportData("S/18,340", 623);
            case "MONTH":
                return new ReportData("S/45,280", 1247);
            case "YEAR":
                return new ReportData("S/567,890", 12458);
            default:
                return new ReportData("S/45,280", 1247);
        }
    }

    private void exportToPdf() {
        // TODO: Implementar exportación a PDF
        Toast.makeText(this, 
            "Exportando reporte a PDF para período: " + getSelectedPeriodText(), 
            Toast.LENGTH_SHORT).show();
        
        // En una implementación real, aquí generarías el PDF con los datos
        // usando librerías como iTextPDF o similar
    }

    private void exportToExcel() {
        // TODO: Implementar exportación a Excel
        Toast.makeText(this, 
            "Exportando reporte a Excel para período: " + getSelectedPeriodText(), 
            Toast.LENGTH_SHORT).show();
        
        // En una implementación real, aquí generarías el Excel con los datos
        // usando librerías como Apache POI
    }

    private String getSelectedPeriodText() {
        switch (selectedPeriod) {
            case "TODAY": return "Hoy";
            case "WEEK": return "Esta Semana";
            case "MONTH": return "Este Mes";
            case "YEAR": return "Este Año";
            default: return "Este Mes";
        }
    }
    
    private void loadSampleReportData() {
        if (companyReports == null) {
            android.util.Log.e("ReportsActivity", "companyReports list is null!");
            return;
        }
        
        companyReports.clear();
        
        // Datos de ejemplo de empresas de turismo
        companyReports.add(new CompanyReport("Tours Cusco Premium", 23, "€4,350", true));
        companyReports.add(new CompanyReport("Lima Adventures", 18, "€3,240", true));
        companyReports.add(new CompanyReport("Arequipa Expeditions", 15, "€2,890", true));
        companyReports.add(new CompanyReport("Machu Picchu Express", 12, "€2,100", false));
        companyReports.add(new CompanyReport("Sacred Valley Tours", 10, "€1,850", true));
        companyReports.add(new CompanyReport("Inca Trail Specialists", 8, "€1,420", true));
        
        if (reportAdapter != null) {
            reportAdapter.updateReports(companyReports);
        } else {
            android.util.Log.e("ReportsActivity", "reportAdapter is null!");
        }
    }
    
    @Override
    public void onReportClick(CompanyReport report) {
        Toast.makeText(this, 
            "Detalles de " + report.getCompanyName() + ": " + 
            report.getTotalTours() + " tours, " + report.getRevenue(), 
            Toast.LENGTH_SHORT).show();
        
        // TODO: Abrir actividad de detalles de la empresa
    }

    // Clase helper para encapsular datos del reporte
    private static class ReportData {
        private String totalRevenue;
        private int completedReservations;

        public ReportData(String totalRevenue, int completedReservations) {
            this.totalRevenue = totalRevenue;
            this.completedReservations = completedReservations;
        }

        public String getTotalRevenue() {
            return totalRevenue;
        }

        public int getCompletedReservations() {
            return completedReservations;
        }
    }
}