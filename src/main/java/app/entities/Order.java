package app.entities;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private int order_id;
    private int userid;
    private int price;
    private String status;
    private String date;
    private List<OrderLine> orderlist = new ArrayList<>();


    //lost of orderlines
    public Order(int order_id, int price, String status, String date) {
        this.order_id = order_id;

        this.price = price;
        this.status = status;
        this.date = date;
    }

    public void addtoOrderLine(OrderLine item) {

        orderlist.add(item);
    }

        public int getOrder_id () {
            return order_id;
        }

        public void setOrder_id ( int order_id){
            this.order_id = order_id;
        }

        public int getPrice () {
            return price;
        }

        public void setPrice ( int price){
            this.price = price;
        }

        public String getStatus () {
            return status;
        }

        public void setStatus (String status){
            this.status = status;
        }

        public String getDate () {
            return date;
        }

        public void setDate (String date){
            date = date;
        }



    public List<OrderLine> getOrderlist() {
        return orderlist;
    }

    public void setOrderlist(List<OrderLine> orderlist) {
        this.orderlist = orderlist;
    }
    public void calculateTotalPrice() {

        for (OrderLine orderLine : orderlist) {
           int  totalprice =+  orderLine.getpricePerQuantity();

        }

    }


}

