/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

/**
 *
 * @author Tim
 */
@SessionScoped
public class AuthorService implements Serializable {
    @Inject
    private AuthorDaoStrategy dao;

    public AuthorService() {
       
    }
    
    public List<Author> getAuthorList() throws ClassNotFoundException, SQLException {
        return dao.getAuthorList();
}
    
    public Author getAuthorById(Object primaryKey) throws ClassNotFoundException, SQLException{
        return dao.findAuthorById(primaryKey);
    }
    
    public void createAuthor(Object authorName, Object dateAdded) throws ClassNotFoundException, SQLException{
        List<Object> colValues = new ArrayList<>();
        colValues.add(authorName);
        colValues.add(dateAdded);
        dao.createAuthor(colValues);
    }
    
    public void deleteAuthorById(String primaryKey) throws ClassNotFoundException, SQLException{
        dao.deleteAuthorById(primaryKey);
    }
    
    
    public void updateAuthorById(List<String> colNames, List<Object> colValues, Object whereValue) throws ClassNotFoundException, SQLException{
        dao.updateAuthorById(colNames, colValues, whereValue);
    }

    public AuthorDaoStrategy getDao() {
        return dao;
    }

    public void setDao(AuthorDaoStrategy dao) {
        this.dao = dao;
    }
    
    
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
//        AuthorDaoStrategy dao = new AuthorDao(new MySqlDbStrategy(), "com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/book", "root", "admin");
//        AuthorService service = new AuthorService(dao);
//        //service.createAuthor("Tim Rogers", "1990-07-14");
//        //service.deleteAuthorById("5");
//        //List<Author> authors  = service.getAuthorList();
//        Author author = service.getAuthorById(7);
//        //System.out.println(authors);
//        System.out.println(author);
    }
}