/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package listener;

import java.util.Date;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 *
 * @author Timothy
 */
public class userCountListener implements HttpSessionListener {
     public static int users = 0;

    public void sessionCreated(HttpSessionEvent event) {
        ++users;
        event.getSession().getServletContext().setAttribute("users", 
                userCountListener.users);
        
        
    }

    public void sessionDestroyed(HttpSessionEvent event) {
        --users;
        event.getSession().getServletContext().setAttribute("users", 
                userCountListener.users);
        
        
    }
}
