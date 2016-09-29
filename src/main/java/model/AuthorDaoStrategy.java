/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Timothy
 */
public interface AuthorDaoStrategy {

    List<Author> getAuthorList() throws ClassNotFoundException, SQLException;
    void createAuthor(List<Object> colValues) throws ClassNotFoundException, SQLException;
    void deleteAuthorById(String primaryKey) throws ClassNotFoundException, SQLException;
    Author findAuthorById(Object primaryKey) throws ClassNotFoundException, SQLException;
}
