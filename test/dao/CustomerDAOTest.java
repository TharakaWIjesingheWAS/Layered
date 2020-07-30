package dao;

import entity.Customer;

import java.util.List;

public class CustomerDAOTest {

    public static void main(String[] args) {
        DAOFactory.getInstance().getDAO(DAOType.QUERY);
//        List<Customer> allCustomers = CustomerDAO.findAllCustomers();
//        for (Customer customer : allCustomers)
//            System.out.println(customer);

        assert false:"";
    }
}
