package order.domain;

import java.util.List;

public class Order {

    private String orderNumber;
    private String customerID;

    private List<OrderLine> orderLines;

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public void setOrderLines(List<OrderLine> orderLines) {
        this.orderLines = orderLines;
    }

    public List<OrderLine> getOrderLines(List<OrderLine> orderLines) {
        return orderLines;
    }
}
