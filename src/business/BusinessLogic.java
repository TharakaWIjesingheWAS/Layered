package business;

import dao.DataLayer;
import util.CustomerTM;
import util.ItemTM;
import util.OrderDetailTM;
import util.OrderTM;

import java.util.List;

public class BusinessLogic {


    public static List<CustomerTM> getAllCustomer(){
        return DataLayer.getAllCustomer();
    }

    public static boolean saveCustomer(CustomerTM customer){
        return DataLayer.saveCustomer(customer);
    }

    public static boolean deleteCustomer(String customerId){
        return DataLayer.deleteCustomer(customerId);
    }

    public static boolean updateCustomer(CustomerTM customer){
        return DataLayer.updateCustomer(customer);
    }

    public static List<ItemTM> getAllItems(){
        return DataLayer.getAllItems();
    }

    public static boolean saveItem(ItemTM item){
        return DataLayer.saveItem(item);
    }

    public static boolean deleteItem(String itemCode){
        return DataLayer.deleteItem(itemCode);
    }

    public static boolean updateItem(ItemTM item){
        return DataLayer.updateItem(item);
    }

    public static boolean placeOrder(OrderTM order, List<OrderDetailTM> orderDetails){
        return DataLayer.placeOrder(order,orderDetails);
    }

}
