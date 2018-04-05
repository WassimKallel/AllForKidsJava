/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package allforkids.blog.models;

import allforkids.userManagement.models.User;
import dopsie.core.Model;
import dopsie.exceptions.ModelException;
import java.util.ArrayList;

/**
 *
 * @author Wassim
 */
public class Post extends Model {

    @Override
    public String getTableName() {
        return "blog_post"; //To change body of generated methods, choose Tools | Templates.
    }
    
    public ArrayList<Comment> comments() throws ModelException {
        return this.hasMany(Comment.class);
    }
    
    public User author() throws ModelException {
        return this.hasOne(User.class);
    }
    
    public ArrayList<Tag> tags() throws ModelException {
        return this.manyToMany(Tag.class, PostTag.class);
    }
    
    public ArrayList<PostTag> postTagsRelation() throws ModelException {
        return this.hasMany(PostTag.class);
    }
    
    @Override
    public boolean equals(Object o) {
        return ((Post)o).getAttr("id").equals(this.getAttr("id"));
    }

    @Override
    public int hashCode() {
        return 1;
    }
}
