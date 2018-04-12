/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package allforkids.userManagement.models;

/**
 *
 * @author Bechir
 */
public class UserSession {
    
    private UserSession(){};
    
    private static User user = null;
    
    public static void setInstance(User connectedUser) {
        user = connectedUser;
    }
    public static User getInstance() {
        return user;
    }
}
