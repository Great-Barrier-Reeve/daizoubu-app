package org.greatbarrierreeve.daizoubu.data.model;

import java.math.BigDecimal;
import java.util.Objects;

//  Firestore document model for errands. Stored in errands collection.
//  Bounty and all money values use BigDecimal. Dates use Firestore Timestamp.
public class Errand {

    private String id;
    private String buyerId;
    private String runnerId;
    private BigDecimal bounty;
    private OrderItem orderItem;
    private ErrandStatus status;
    private PriorityLevel priorityLevel;


    private Timestamp createdAt;
    private Timestamp acceptedAt;
    private Timestamp deliveredAt;
    private Timestamp refundedAt;
    private boolean priorityLocked;
    private Location deliveryLocation;
    private String storeName;

    public Location getDeliveryLocation() {
        return deliveryLocation;
    }

    public void setDeliveryLocation(Location deliveryLocation) {
        this.deliveryLocation = deliveryLocation;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public OrderItem getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(OrderItem orderItem) {
        this.orderItem = orderItem;
    }


    public Errand() {
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    public String getRunnerId() {
        return runnerId;
    }

    public void setRunnerId(String runnerId) {
        this.runnerId = runnerId;
    }

    public BigDecimal getBounty() {
        return bounty;
    }

    public void setBounty(BigDecimal bounty) {
        this.bounty = bounty;
    }

    public ErrandStatus getStatus() {
        return status;
    }

    public void setStatus(ErrandStatus status) {
        this.status = status;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getAcceptedAt() {
        return acceptedAt;
    }

    public void setAcceptedAt(Timestamp acceptedAt) {
        this.acceptedAt = acceptedAt;
    }

    public Timestamp getDeliveredAt() {
        return deliveredAt;
    }

    public void setDeliveredAt(Timestamp deliveredAt) {
        this.deliveredAt = deliveredAt;
    }

    public Timestamp getRefundedAt() {
        return refundedAt;
    }

    public void setRefundedAt(Timestamp refundedAt) {
        this.refundedAt = refundedAt;
    }

    public PriorityLevel getPriorityLevel() {
        return priorityLevel;
    }

    public void setPriorityLevel(PriorityLevel priorityLevel) {
        this.priorityLevel = priorityLevel;
    }

    public boolean isPriorityLocked() {
        return priorityLocked;
    }



    public void setPriorityLocked(boolean priorityLocked) {
        this.priorityLocked = priorityLocked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Errand errand = (Errand) o;
        return Objects.equals(id, errand.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Errand{" +
                "id='" + id + '\'' +
                ", buyerId='" + buyerId + '\'' +
                ", runnerId='" + runnerId + '\'' +
                ", bounty=" + bounty +
                ", orderItem=" + orderItem +
                ", status=" + status +
                ", priorityLevel=" + priorityLevel +
                ", createdAt=" + createdAt +
                ", acceptedAt=" + acceptedAt +
                ", deliveredAt=" + deliveredAt +
                ", refundedAt=" + refundedAt +
                ", priorityLocked=" + priorityLocked +
                ", deliveryLocation=" + deliveryLocation +
                ", storeName='" + storeName + '\'' +
                '}';
    }
}