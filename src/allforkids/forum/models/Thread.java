/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package allforkids.forum.models;

import dopsie.core.Model;
import dopsie.exceptions.ModelException;
import java.util.ArrayList;
/**
 *
 * @author Bechir
 */
public class Thread extends Model{
    public ArrayList<Post> posts() throws ModelException {
        return this.hasMany(Post.class);
    }
    
    public Topic topic() throws ModelException {
        return this.hasOne(Topic.class);
    }    
}
