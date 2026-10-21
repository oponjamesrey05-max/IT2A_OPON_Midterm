package com.scc.waterstation;

public class Order {
    private int id;
    private String customerName;
    private int qtySlim;
    private int qtyRound;
    private String status;

    public Order(String customerName, int qtySlim, int qtyRound, String status) {
        this.customerName = customerName;
        this.qtySlim = qtySlim;
        this.qtyRound = qtyRound;
        this.status = status;
    }

    public Order(int id, String customerName, int qtySlim, int qtyRound, String status) {
        this.id = id;
        this.customerName = customerName;
        this.qtySlim = qtySlim;
        this.qtyRound = qtyRound;
        this.status = status;
    }

    public int getId() { return id; }
    public String getCustomerName() { return customerName; }
    public int getQtySlim() { return qtySlim; }
    public int getQtyRound() { return qtyRound; }
    public String getStatus() { return status; }
}
