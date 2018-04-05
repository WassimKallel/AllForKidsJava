/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package allforkids.blog.models;

import allforkids.userManagement.models.User;
import dopsie.core.Model;
import dopsie.exceptions.ModelException;

/**
 *
 * @author Wassim
 */
public class Comment extends Model{
    
    public Post post() throws ModelException {
        return this.belongsTo(Post.class);
    }
    
    public User commentor() throws ModelException {
        return this.hasOne(User.class);
    }
}
