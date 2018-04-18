/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package allforkids.forum.models;

import allforkids.userManagement.models.User;
import dopsie.core.Model;
import dopsie.exceptions.ModelException;

/**
 *
 * @author Wassim
 */
public class Vote extends Model{
    
    public User voter() throws ModelException {
        return this.hasOne(User.class);
    }
    
    public Post post() throws ModelException {
        return this.hasOne(Post.class);
    }
    
    public VoteType getType() {
        return (int)this.getAttr("vote") == -1 ? VoteType.DOWN : VoteType.UP;
    }

    public void setVoteType(VoteType voteType) {
        if(voteType ==  VoteType.UP) {
            this.setAttr("vote", 1);
        } else {
            this.setAttr("vote", -1);
        }
    }
}




