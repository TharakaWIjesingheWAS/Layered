package dao.impl;

import dao.QueryDAO;
import db.DBConnection;
import entity.CustomEntity;
import entity.SuperEntity;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class QueryDAOImpl implements QueryDAO {


    @Override
    public CustomEntity getOrderDetail(String orderId) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("SELECT o.id, c.name,o.date FROM `Order` o\n" +
                    "INNER JOIN Customer c on  o.customerId = c.id\n" +
                    "WHERE o.id=?");
            pstm.setObject(1,orderId);
            ResultSet rst = pstm.executeQuery();
            if (rst.next()){
                return new CustomEntity(rst.getString(1),
                        rst.getString(2),
                        rst.getString(3));
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public CustomEntity getOrderDetail2(String orderId) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("SELECT c.id, c.name,o.id FROM `Order` o\n" +
                    "INNER JOIN Customer c on  o.customerId = c.id\n" +
                    "WHERE o.id=?");
            pstm.setObject(1,orderId);
            ResultSet rst = pstm.executeQuery();
            if (rst.next()){
                return new CustomEntity(rst.getString(1),
                        rst.getString(2),
                        rst.getString(3));
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
