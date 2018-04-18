/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package allforkids.forum.models;

import allforkids.userManagement.models.User;
import dopsie.core.Model;
import dopsie.exceptions.ModelException;
import java.util.ArrayList;

/**
 *
 * @author Wassim
 */
public class Post extends Model{    
    
    public User author() throws ModelException {
        return this.hasOne(User.class);
    }
    
    public Thread thread() throws ModelException {
        return this.belongsTo(Thread.class);
    }
    
    public ArrayList<Vote> votes() throws ModelException {
        return this.hasMany(Vote.class);
    }
    
    public ArrayList<Report> reports() throws ModelException {
        return this.hasMany(Report.class);
    }
    
    
    public int voteScore() throws ModelException {
        int score = 0;
        for(Vote vote: this.votes()) {
            if(vote.getType() == VoteType.UP) {
                score++;
            } else {
                score--;
            }
        }
        return score;
    }
    
    public boolean userVoted(User user) throws ModelException {
        for(Vote vote: this.votes()) {
            if(vote.voter().equals(user)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean userReported(User user) throws ModelException {
        ArrayList<Report> reports = this.reports();
        for (Report report: reports) {
            if(report.reporter().equals(user)) {
                return true;
            }
        }
        return false;
    }
}
