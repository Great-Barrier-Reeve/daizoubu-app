package org.greatbarrierreeve.daizoubu.data.model;
//Getter and Setter methods needed for jackson json serialisation

public class TransitionRequest {

    private ErrandStatus targetStatus;

    public ErrandStatus getTargetStatus() {
        return targetStatus;
    }

    public void setTargetStatus(ErrandStatus targetStatus) {
        this.targetStatus = targetStatus;
    }
}


