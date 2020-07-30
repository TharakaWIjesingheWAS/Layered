package dao.impl;

import dao.OrderDAO;
import db.DBConnection;
import entity.Item;
import entity.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {

    public List<Object> findAll(){
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            Statement stm = connection.createStatement();
            ResultSet rst = stm.executeQuery("SELECT * FROM `Order`");
            List<Object> orders = new ArrayList<>();
            while (rst.next()){
                orders.add(new Order(rst.getString(1),
                        rst.getDate(2),
                        rst.getString(3)));
            }
            return orders;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    public Order find(Object key){
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("SELECT * FROM `Order` WHERE id=?");
            pstm.setObject(1,key);
            ResultSet rst = pstm.executeQuery();
            if (rst.next()){
                return new Order(rst.getString(1),
                        rst.getDate(2),
                        rst.getString(3));
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    public boolean save(Object entity){
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("INSERT InTO `Order` VALUES(?,?,?)");
            Order order = (Order) entity;
            pstm.setObject(1,order.getId());
            pstm.setObject(2,order.getDate());
            pstm.setObject(3,order.getCustomerId());
            return pstm.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(Object entity){
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("UPDATE `Order` SET date=?, customerId=? WHERE id=?");
            Order order = (Order) entity;
            pstm.setObject(3,order.getId());
            pstm.setObject(1,order.getDate());
            pstm.setObject(2,order.getCustomerId());
            return pstm.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(Object key){
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("DELETE FROM `Order` WHERE id=?");
            pstm.setObject(1,key);
            return pstm.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getLastOrderId() {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            Statement stm = connection.createStatement();
            ResultSet rst = stm.executeQuery("SELECT * FROM `Order`ORDER BY Id DESC LIMIT 1");
            if (rst.next()) {
                return rst.getString(1);
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
