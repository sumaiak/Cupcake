package app.entities;

import java.util.ArrayList;
import java.util.List;

public class OrderLine {
    private int orderline_id;
    private int bottomId;
    private int toppingId;

    public OrderLine(int orderline_id, int bottomId, int toppingId) {
        this.orderline_id = orderline_id;
        this.bottomId = bottomId;
        this.toppingId = toppingId;
    }

    public OrderLine(int bottomId, int toppingId) {
        this.bottomId = bottomId;
        this.toppingId = toppingId;
    }

    public int getOrderline_id() {
        return orderline_id;
    }




    public void setOrderline_id(int orderline_id) {
        this.orderline_id = orderline_id;
    }

    public int getBottomId() {
        return bottomId;
    }

    public void setBottomId(int bottomId) {
        this.bottomId = bottomId;
    }

    public int getToppingId() {
        return toppingId;
    }

    public void setToppingId(int toppingId) {
        this.toppingId = toppingId;
    }

}



