/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package allforkids.orderManagement.models;

import dopsie.core.Model;
import dopsie.exceptions.ModelException;
import dopsie.exceptions.UnsupportedDataTypeException;
import java.util.logging.Level;
import java.util.logging.Logger;
import allforkids.store.models.*;

/**
 *
 * @author KHOUBEIB
 */
public class LineItem extends Model {

    @Override
    public String getTableName() {
        return "`line_item`";
    }

    @Override
    public String getPrimaryKeyName() {
        return "id";
    }

    public Product product() throws ModelException {
        return this.hasOne(Product.class);
    }

    void incrementQuantity() throws ModelException, UnsupportedDataTypeException {

        int qty = (int) this.getAttr("quantity");
        qty++;
        this.setAttr("quantity", qty);
        Double unitPrice = (Double) this.product().getAttr("unit_price") ;
        float vatRate = (float) this.product().getAttr("vat_rate");
        this.setAttr("total", (unitPrice * qty));
        this.setAttr("vat", (unitPrice * qty / 100 *vatRate ));
        this.save();
        
        
    }
    
       void incrementQuantity( int newQty) throws ModelException, UnsupportedDataTypeException {

        int qty = (int) this.getAttr("quantity");
        qty = qty + newQty;
        this.setAttr("quantity", qty);
        Double unitPrice = (Double) this.product().getAttr("unit_price") ;
        float vatRate = (float) this.product().getAttr("vat_rate");
        this.setAttr("total", (unitPrice * qty));
        this.setAttr("vat", (unitPrice * qty / 100 *vatRate ));
        this.save();
        
        
    }

    public void decrementQuantity() {

        int qty = (int) this.getAttr("quantity");
        qty--;
        this.setAttr("quantity", qty);
    }

    void setQuantity(int quantity) {

        this.setAttr("quantity", quantity);
        
    }

    int getQuantity() {
        return (int) this.getAttr("quantity");
    }
    


//    public void updateCalculation() throws ModelException, UnsupportedDataTypeException {
//
//        try {
//            this.setAttr("vat_total", this.product().vat() * this.getQuantity());
//            this.setAttr("sub_total", this.product().getPrice() * this.getQuantity());
//            this.setAttr("total", (Double) this.getAttr("sub_total") + (Double) this.getAttr("vat_total"));
//        } catch (ModelException ex) {
//            Logger.getLogger(LineItem.class.getName()).log(Level.SEVERE, null, ex);
//        }
//           
//        this.save();
//    }

}
