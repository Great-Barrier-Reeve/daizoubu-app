package org.greatbarrierreeve.daizoubu.data.model;

//Getter and Setter methods needed for jackson json serialisation

public class AcceptRequest {

    private String runnerId;

    public String getRunnerId() {
        return runnerId;
    }

    public void setRunnerId(String runnerId) {
        this.runnerId = runnerId;
    }
}
