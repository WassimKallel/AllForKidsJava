/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package allforkids.forum.models;

import dopsie.core.Model;
import dopsie.exceptions.ModelException;
import allforkids.userManagement.models.User;

/**
 *
 * @author Wassim
 */
public class Report extends Model{

   public User reporter() throws ModelException {
        return this.hasOne(User.class);
    }
   
   public Post post() throws ModelException {
       return this.hasOne(Post.class);
   }
}
