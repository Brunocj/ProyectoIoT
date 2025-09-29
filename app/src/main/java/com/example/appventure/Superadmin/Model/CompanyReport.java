package com.example.appventure.Superadmin.Model;

public class CompanyReport {
    private String companyName;
    private int totalTours;
    private String revenue;
    private boolean isActive;

    public CompanyReport(String companyName, int totalTours, String revenue, boolean isActive) {
        this.companyName = companyName;
        this.totalTours = totalTours;
        this.revenue = revenue;
        this.isActive = isActive;
    }

    // Getters
    public String getCompanyName() {
        return companyName;
    }

    public int getTotalTours() {
        return totalTours;
    }

    public String getRevenue() {
        return revenue;
    }

    public boolean isActive() {
        return isActive;
    }

    // Setters
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setTotalTours(int totalTours) {
        this.totalTours = totalTours;
    }

    public void setRevenue(String revenue) {
        this.revenue = revenue;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}