/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package allforkids.orderManagement.models;

import dopsie.core.Model;
import java.util.HashMap;

/**
 *
 * @author KHOUBEIB
 */
public class ShippingMethod extends Model {

    @Override
    public String getTableName() {
        return "`shipping_method`";
    }

    @Override
    public String getPrimaryKeyName() {
        return "id";
    }
    
    public  String getMethodName() {
        return (String) this.getAttr("name");
    }
    
    public  Float getFee() {
        return (Float) this.getAttr("extra_fee");
    }
}
