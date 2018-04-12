/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package allforkids.userManagement.models;

import allforkids.forum.models.Post;

import allforkids.orderManagement.models.Order;

import dopsie.core.Model;
import dopsie.exceptions.ModelException;
import dopsie.exceptions.UnsupportedDataTypeException;
import helpers.CustomImageViewPane;

import helpers.randomStringGenerator;
import java.nio.file.Paths;

import java.util.ArrayList;

/**
 *
 * @author Bechir
 */

public class User extends Model{

    public ArrayList<Post> posts() throws ModelException {
        return this.hasMany(Post.class);
    }

    @Override
    public boolean equals(Object obj) {
        allforkids.userManagement.models.User user = (allforkids.userManagement.models.User) obj;
        return this.getAttr("id").equals(user.getAttr("id"));
    }


    public ArrayList<Order> orders() throws ModelException {
        return this.hasMany(Order.class, "customer_id");
    }

   public Order getUserShoppingCart() throws ModelException, UnsupportedDataTypeException {
        Order UserShoppingCart = null;
        boolean newCustomer = true;
        for (Order o : this.orders()) {

            if ((int) o.getAttr("order_status") == 0) {
                UserShoppingCart = o;
                newCustomer = false;
            }
        }

        if (newCustomer) {
            UserShoppingCart = new Order();
            UserShoppingCart.setAttr("customer_id", this.getAttr("id"));
            UserShoppingCart.setAttr("order_status", 0);
            UserShoppingCart.setAttr("reference", randomStringGenerator.generateString());
            UserShoppingCart.save();
        }

        return UserShoppingCart;

    }

    public static User getByUsername(String username) throws ModelException, UnsupportedDataTypeException {
        try {
            return (User) Model.fetch(User.class).all().where("username", username).execute().get(0);
        } catch (IndexOutOfBoundsException ex) {
            return null;
        }
    }

    public String getFullName() {
        return this.getAttr("first_name") + " " + this.getAttr("last_name");
    }

    public Role getRole() {
        switch ((int) getAttr("role")) {

            case 0:
                return Role.USER;
            case 1:
                return Role.ANALYST;
            case 2:
                return Role.COMMUNITY_MANAGER;
            case 3:
                return Role.ADMIN;

        }
        return Role.USER;
    }

    public void setRole(Role role) {
        if (null != role) {
            switch (role) {
                case USER:
                    this.setAttr("role", 0);
                    break;
                case ANALYST:
                    this.setAttr("role", 1);
                    break;
                case COMMUNITY_MANAGER:
                    this.setAttr("role", 2);
                    break;
                case ADMIN:
                    this.setAttr("role", 3);
                    break;
                default:
                    break;
            }
        }
    }

    public CustomImageViewPane getAvatarViewPane(double width, double height) {
        String path = (String) this.getAttr("avatar_path");
        if (path == null) {
            path = "/img/default-user.png";
            return new CustomImageViewPane(path, width, height);
        }
        String absolutePath = System.getProperty("uploads_folder");

        path = "file:" + absolutePath + path;
        return new CustomImageViewPane(path, width, height);
    }
}
