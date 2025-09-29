package com.example.appventure.Superadmin.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appventure.R;
import com.example.appventure.Superadmin.Model.CompanyReport;

import java.util.List;

public class CompanyReportAdapter extends RecyclerView.Adapter<CompanyReportAdapter.ReportViewHolder> {

    private List<CompanyReport> reports;
    private OnReportClickListener clickListener;

    public interface OnReportClickListener {
        void onReportClick(CompanyReport report);
    }

    public CompanyReportAdapter(List<CompanyReport> reports) {
        this.reports = reports;
    }

    public void setOnReportClickListener(OnReportClickListener listener) {
        this.clickListener = listener;
    }

    @NonNull
    @Override
    public ReportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_company_report, parent, false);
        return new ReportViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReportViewHolder holder, int position) {
        CompanyReport report = reports.get(position);
        
        holder.textCompanyName.setText(report.getCompanyName());
        holder.textTotalTours.setText(String.valueOf(report.getTotalTours()));
        holder.textRevenue.setText(report.getRevenue());
        
        // Color based on activity status
        int textColor = report.isActive() 
            ? holder.itemView.getContext().getColor(R.color.success_color)
            : holder.itemView.getContext().getColor(R.color.text_secondary);
        holder.textRevenue.setTextColor(textColor);
        
        holder.itemView.setOnClickListener(v -> {
            if (clickListener != null) {
                clickListener.onReportClick(report);
            }
        });
    }

    @Override
    public int getItemCount() {
        return reports.size();
    }

    public void updateReports(List<CompanyReport> newReports) {
        this.reports = newReports;
        notifyDataSetChanged();
    }

    static class ReportViewHolder extends RecyclerView.ViewHolder {
        TextView textCompanyName;
        TextView textTotalTours;
        TextView textRevenue;

        public ReportViewHolder(@NonNull View itemView) {
            super(itemView);
            textCompanyName = itemView.findViewById(R.id.textCompanyName);
            textTotalTours = itemView.findViewById(R.id.textTotalTours);
            textRevenue = itemView.findViewById(R.id.textRevenue);
        }
    }
}