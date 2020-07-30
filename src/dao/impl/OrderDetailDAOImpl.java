package dao.impl;

import dao.OrderDetailDAO;
import db.DBConnection;
import entity.Order;
import entity.OrderDetail;
import entity.OrderDetailPK;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailDAOImpl implements OrderDetailDAO {




    @Override
    public List<OrderDetail> findAll() {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            Statement stm = connection.createStatement();
            ResultSet rst = stm.executeQuery("SELECT * FROM OrderDetail");
            List<OrderDetail> orderDetails = new ArrayList<>();
            while (rst.next()){
                orderDetails.add(new OrderDetail(rst.getString(1),
                        rst.getString(2),
                        rst.getInt(3),
                        rst.getBigDecimal(4)));
            }
            return orderDetails;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public OrderDetail find(OrderDetailPK key) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("SELECT * FROM OrderDetail WHERE orderid=? AND itemCode=?");
            OrderDetailPK orderDetailPK = key;
            pstm.setObject(1,orderDetailPK.getOrderId());
            pstm.setObject(2,orderDetailPK.getItemCode());
            ResultSet rst = pstm.executeQuery();
            if (rst.next()){
                return new OrderDetail(rst.getString(1),
                        rst.getString(2),
                        rst.getInt(3),
                        rst.getBigDecimal(4));
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean save(OrderDetail entity) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("INSERT InTO OrderDetail VALUES(?,?,?,?)");
            pstm.setObject(1,entity.getOrderDetailPK().getOrderId());
            pstm.setObject(2,entity.getOrderDetailPK().getItemCode());
            pstm.setObject(3,entity.getQty());
            pstm.setObject(4,entity.getUnitPrice());
            return pstm.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(OrderDetail entity) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("UPDATE OrderDetail SET qty=?, unitPrice=? WHERE orderid=? AND itemCode=?");
            pstm.setObject(3,entity.getOrderDetailPK().getOrderId());
            pstm.setObject(4,entity.getOrderDetailPK().getItemCode());
            pstm.setObject(1,entity.getQty());
            pstm.setObject(2,entity.getUnitPrice());
            return pstm.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(OrderDetailPK key) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("DELETE FROM OrderDetail WHERE orderid=? AND itemCode=?");
            OrderDetailPK orderDetailPK = key;
            pstm.setObject(1,orderDetailPK.getOrderId());
            pstm.setObject(2,orderDetailPK.getItemCode());
            return pstm.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
