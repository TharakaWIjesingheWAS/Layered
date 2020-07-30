package dao;

import entity.Order;

import java.util.List;

public interface OrderDAO extends SuperDAO {
     String getLastOrderId();
}
