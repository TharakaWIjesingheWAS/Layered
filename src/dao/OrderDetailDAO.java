package dao;

import db.DBConnection;
import entity.Order;
import entity.OrderDetail;
import entity.OrderDetailPK;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailDAO {

    public static List<OrderDetail> findAllOrderDetails(){
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

    public static OrderDetail findOrderDetail(OrderDetailPK orderDetailPK){
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("SELECT * FROM OrderDetail WHERE orderid=? AND itemCode=?");
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

    public static boolean saveOrderDetail(OrderDetail orderDetail){
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("INSERT InTO OrderDetail VALUES(?,?,?,?)");
            pstm.setObject(1,orderDetail.getOrderDetailPK().getOrderId());
            pstm.setObject(2,orderDetail.getOrderDetailPK().getItemCode());
            pstm.setObject(3,orderDetail.getQty());
            pstm.setObject(4,orderDetail.getUnitPrice());
            return pstm.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updateOrderDetail(OrderDetail orderDetail){
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("UPDATE OrderDetail SET qty=?, unitPrice=? WHERE orderid=? AND itemCode=?");
            pstm.setObject(3,orderDetail.getOrderDetailPK().getOrderId());
            pstm.setObject(4,orderDetail.getOrderDetailPK().getItemCode());
            pstm.setObject(1,orderDetail.getQty());
            pstm.setObject(2,orderDetail.getUnitPrice());
            return pstm.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean deleteOrderDetail(OrderDetailPK OrderDetailPk){
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("DELETE FROM OrderDetail WHERE orderid=? AND itemCode=?");
            pstm.setObject(1,OrderDetailPk.getOrderId());
            pstm.setObject(2,OrderDetailPk.getItemCode());
            return pstm.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
