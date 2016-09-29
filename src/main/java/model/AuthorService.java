/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Tim
 */
public class AuthorService {
    private AuthorDaoStrategy dao;

    public AuthorService(AuthorDaoStrategy dao) {
        this.dao = dao;
    }
    
    public final List<Author> getAuthorList() throws ClassNotFoundException, SQLException {
        return dao.getAuthorList();
}
    
    public final Author getAuthorById(Object primaryKey) throws ClassNotFoundException, SQLException{
        return dao.findAuthorById(primaryKey);
    }
    
    public final void createAuthor(Object authorName, Object dateAdded) throws ClassNotFoundException, SQLException{
        List<Object> colValues = new ArrayList<>();
        colValues.add(authorName);
        colValues.add(dateAdded);
        dao.createAuthor(colValues);
    }
    
    public final void deleteAuthorById(String primaryKey) throws ClassNotFoundException, SQLException{
        dao.deleteAuthorById(primaryKey);
    }
    
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        AuthorDaoStrategy dao = new AuthorDao(new MySqlDbStrategy(), "com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/book", "root", "admin");
        AuthorService service = new AuthorService(dao);
        //service.createAuthor("Tim Rogers", "1990-07-14");
        //service.deleteAuthorById("5");
        //List<Author> authors  = service.getAuthorList();
        Author author = service.getAuthorById(7);
        //System.out.println(authors);
        System.out.println(author);
    }
}