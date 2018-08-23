package org.einar.regnskap.rema1000.api;

public class Service {
    private String description;
    private Boolean maintenance;

    public Boolean getMaintenance() {
        return this.maintenance;
    }

    public void setMaintenance(Boolean maintenance) {
        this.maintenance = maintenance;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
