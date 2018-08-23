package org.einar.regnskap.rema1000.api;

public class ServiceStatus {
    private Service service;
    private Versions versions;

    public Service getService() {
        return this.service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public Versions getVersions() {
        return this.versions;
    }

    public void setVersions(Versions versions) {
        this.versions = versions;
    }
}
