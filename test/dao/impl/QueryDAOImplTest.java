package dao.impl;

import dao.DAOFactory;
import dao.DAOType;
import dao.QueryDAO;
import dao.SuperDAO;
import entity.CustomEntity;



class QueryDAOImplTest {
    public static void main(String[] args) {
        QueryDAO queryDAO = DAOFactory.getInstance().getDAO(DAOType.QUERY);
        CustomEntity ce = queryDAO.getOrderDetail2("OD001");
        System.out.println("Customer Name:" + ce.getCustomerName());
        System.out.println("Order ID :" + ce.getOrderId());
        System.out.println("Order Date :" + ce.getOrderDate());
        System.out.println("Customer ID:" + ce.getCustomerId());
    }

}