/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package allforkids.store.models;

import dopsie.core.Model;
import dopsie.exceptions.ModelException;
/**
 *
 * @author Amine
 */
public class Product extends Model{

    @Override
    public String getTableName() {
        return "products";
    }
    
    public Category category() throws ModelException {
        return this.hasOne(Category.class);
    }
}
