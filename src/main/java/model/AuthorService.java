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
import java.util.Arrays;
import java.util.Calendar;
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

    public Author getAuthorById(Object primaryKey) throws ClassNotFoundException, SQLException {
        return dao.findAuthorById(primaryKey);
    }

    public void createAuthor(String authorName) throws ClassNotFoundException, SQLException {
       
        dao.createAuthor(authorName);
    }

    public void deleteAuthorById(String primaryKey) throws ClassNotFoundException, SQLException {
        dao.deleteAuthorById(primaryKey);
    }

    public void updateAuthorById(Object authorId, String authorName) throws ClassNotFoundException, SQLException {
        List<String> colNames = Arrays.asList("author_name");
        List<Object> colValues = new ArrayList();
        colValues.add(authorName);
        
        dao.updateAuthorById(colNames, colValues, authorId);
    }

    public AuthorDaoStrategy getDao() {
        return dao;
    }

    public void setDao(AuthorDaoStrategy dao) {
        this.dao = dao;
    }

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
//        AuthorService service = new AuthorService();
//        service.createAuthor("Tim Rogers");
//        service.createAuthor("Joe Shmoe");
//        
//        //service.deleteAuthorById("5");
//        //List<Author> authors  = service.getAuthorList();
//        Author author = service.getAuthorById(7);
//        //System.out.println(authors);
//        System.out.println(author);
    }
}
