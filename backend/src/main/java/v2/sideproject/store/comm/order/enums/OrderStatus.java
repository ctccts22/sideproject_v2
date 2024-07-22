package v2.sideproject.store.comm.order.enums;

public enum OrderStatus {
    PENDING,        // Order has been created but not yet processed
    PROCESSING,     // Order is currently being processed
    APPROVED,       // Order has been approved for fulfillment
    SHIPPED,        // Order has been shipped to the customer
    DELIVERED,      // Order has been delivered to the customer
    CANCELLED,      // Order has been cancelled by the customer or the store
    RETURNED,       // Order has been returned by the customer
    REFUNDED        // Order has been refunded to the customer
}