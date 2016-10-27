/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;

/**
 *
 * @author Timothy
 */
public interface AuthorDaoStrategy {

    List<Author> getAuthorList() throws ClassNotFoundException, SQLException;
    void createAuthor(String name) throws ClassNotFoundException, SQLException;
    void deleteAuthorById(String primaryKey) throws ClassNotFoundException, SQLException;
    Author findAuthorById(Object primaryKey) throws ClassNotFoundException, SQLException;
     public void updateAuthorById(List<String> colNames, List<Object> colValues, Object whereValue) throws ClassNotFoundException, SQLException;
     public void initDao(String driver, String url, String user, String password);
      public void initDao(DataSource ds) throws SQLException;
}
