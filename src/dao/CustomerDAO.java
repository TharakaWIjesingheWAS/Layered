package dao;

import entity.Customer;

import java.util.List;

public interface CustomerDAO extends CrudDAO<Customer, String> {

    String getLastCustomerId();
}
