/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package allforkids.blog.models;

import dopsie.core.Model;
import dopsie.exceptions.ModelException;
import java.util.ArrayList;

/**
 *
 * @author Wassim
 */
public class Tag extends Model{


    public ArrayList<Post> posts() throws ModelException {
        return this.manyToMany(Post.class, PostTag.class);
    }
}
