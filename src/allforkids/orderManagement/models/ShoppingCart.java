/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package allforkids.orderManagement.models;

import dopsie.core.*;
import dopsie.exceptions.ModelException;
import java.util.*;

/**
 *
 * @author KHOUBEIB
 */
public class ShoppingCart extends Order {

    List<LineItem> items;


    public ShoppingCart() {

        items = new ArrayList<LineItem>();
        Order order = new Order();
    }

}
