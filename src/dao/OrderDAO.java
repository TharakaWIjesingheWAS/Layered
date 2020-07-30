package dao;

import entity.Order;

import java.util.List;

public interface OrderDAO extends CrudDAO< Order,String> {
     String getLastOrderId();
}
