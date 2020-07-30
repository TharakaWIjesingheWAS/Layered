package business;

import dao.*;
import dao.impl.CustomerDAOImpl;
import dao.impl.ItemDAOImpl;
import dao.impl.OrderDAOImpl;
import dao.impl.OrderDetailDAOImpl;
import db.DBConnection;
import entity.Customer;
import entity.Item;
import entity.Order;
import entity.OrderDetail;
import util.CustomerTM;
import util.ItemTM;
import util.OrderDetailTM;
import util.OrderTM;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BusinessLogic {

    public static String getNewCustomerId(){
        CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getInstance().getDAO(DAOType.CUSTOMER);
        String lastCustomerId =customerDAO.getLastCustomerId();
        if (lastCustomerId == null){
            return "C001";
        }else {
            int maxId = Integer.parseInt(lastCustomerId.replace("C", ""));
            maxId = maxId + 1;
            String id = "";
            if (maxId < 10) {
                id = "C00" + maxId;
            } else if (maxId < 100) {
                id = "C0" + maxId;
            } else {
                id = "C" + maxId;
            }
            return id;
        }
    }


    public static List<CustomerTM> getAllCustomer(){
        CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getInstance().getDAO(DAOType.CUSTOMER);
        List<Customer>allCustomers = customerDAO.findAll();
        List<CustomerTM> customers = new ArrayList<>();
       for (Customer customer: allCustomers){
            customers.add(new CustomerTM(customer.getId(), customer.getName(), customer.getAddress()));
        }
        return customers;
        //return DataLayer.getAllCustomer();
    }

    public static boolean saveCustomer(String id,String name,String address){
        CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getInstance().getDAO(DAOType.CUSTOMER);
         return customerDAO.save(new Customer(id,name,address));
    }

    public static boolean deleteCustomer(String customerId){
        CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getInstance().getDAO(DAOType.CUSTOMER);
        return customerDAO.delete(customerId);
    }

    public static boolean updateCustomer(String name, String address, String customerId){
        CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getInstance().getDAO(DAOType.CUSTOMER);
        return customerDAO.update(new Customer(customerId,name,address));
    }

    public static String getNewItemCode(){
        ItemDAO itemDAO = (ItemDAO) DAOFactory.getInstance().getDAO(DAOType.ITEM);
        String lastItemCode = itemDAO.getLastItemCode();
        if (lastItemCode == null){
            return "I001";
        }else {
            int maxCode = Integer.parseInt(lastItemCode.replace("I", ""));
            String code = "";
            if (maxCode < 10) {
                code = "I00" + maxCode;
            } else if (maxCode < 100) {
                code = "I0" + maxCode;
            } else {
                code = "I" + maxCode;
            }
            return code;
        }
    }

    public static List<ItemTM> getAllItems(){
        ItemDAO itemDAO = (ItemDAO) DAOFactory.getInstance().getDAO(DAOType.ITEM);
        List<Item> allItems = itemDAO.findAll();
        List<ItemTM> items = new ArrayList<>();
        for (Item item : allItems){
            items.add(new ItemTM(item.getCode(),item.getDescription(),item.getQtyOnHand(),item.getUnitPrice().doubleValue()));
        }
        return items;
    }

    public static boolean saveItem(String code,String description, int qtyOnHand, double unitPrice){
        ItemDAO itemDAO = (ItemDAO) DAOFactory.getInstance().getDAO(DAOType.ITEM);
        return itemDAO.save(new Item(code,description,BigDecimal.valueOf(unitPrice),qtyOnHand));
    }

    public static boolean deleteItem(String itemCode){
        ItemDAO itemDAO = (ItemDAO) DAOFactory.getInstance().getDAO(DAOType.ITEM);
        return itemDAO.delete(itemCode);
    }

    public static boolean updateItem(String description, int qtyOnHand, double unitPrice, String itemCode){
        ItemDAO itemDAO = (ItemDAO) DAOFactory.getInstance().getDAO(DAOType.ITEM);
        return itemDAO.update(new Item(itemCode,description,BigDecimal.valueOf(unitPrice),qtyOnHand));
    }

    public  static String getNewOrderId(){
        OrderDAO orderDAO = (OrderDAO) DAOFactory.getInstance().getDAO(DAOType.ORDER);
        String lastOrderId = orderDAO.getLastOrderId();
        if (lastOrderId == null){
            return "OD001";
        }else {
            int maxId = Integer.parseInt(lastOrderId.replace("OD", ""));
            maxId = maxId + 1;
            String id = "";
            if (maxId < 10) {
                id = "OD00" + maxId;
            } else if (maxId < 100) {
                id = "OD0" + maxId;
            } else {
                id = "OD" + maxId;
            }
            return id;
        }
    }

    public static boolean placeOrder(OrderTM order, List<OrderDetailTM> orderDetails){
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);
            OrderDAO orderDAO = (OrderDAO) DAOFactory.getInstance().getDAO(DAOType.ORDER);
            boolean result = orderDAO.save(new Order(order.getOrderId(),
                    Date.valueOf(order.getOrderDate()),
                    order.getCustomerId()));
            if (!result){
                connection.rollback();
                return false;
            }
            for (OrderDetailTM orderDetail : orderDetails) {

                OrderDetailDAO orderDetailDAO = (OrderDetailDAO) DAOFactory.getInstance().getDAO(DAOType.ORDER_DETAIL);
                result = orderDetailDAO.save(new OrderDetail(
                        order.getOrderId(), orderDetail.getCode(),
                        orderDetail.getQty(), BigDecimal.valueOf(orderDetail.getUnitPrice())
                ));
                if (!result) {
                    connection.rollback();
                    return false;
                }
                ItemDAO itemDAO = (ItemDAO) DAOFactory.getInstance().getDAO(DAOType.ITEM);
                Item item = (Item) itemDAO.find(orderDetail.getCode());
                item.setQtyOnHand(item.getQtyOnHand() - orderDetail.getQty());
                result = new ItemDAOImpl().update(item);
                if (!result) {
                    connection.rollback();
                    return false;
                }
            }

            connection.commit();
            return true;
        } catch (SQLException a) {
            a.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return false;
        }finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
