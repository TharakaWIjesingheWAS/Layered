package dao;


import entity.Item;

import java.util.List;

public interface ItemDAO extends CrudDAO< Item ,String> {
   String getLastItemCode();
}
