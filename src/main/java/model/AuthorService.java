/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Tim
 */
public class AuthorService {
    private List<Author> authors;
    
    public AuthorService() {
        initFakeDb();
    }
    
    private final void initFakeDb() {
        authors = Arrays.asList(
                new Author(1, "George RR Martin", new Date()),
                new Author(2, "JK Rowling", new Date()),
                new Author(3, "Suzanne Collins", new Date())
        );
        
    }
    
    public final List<Author> getAuthorList() {
        return authors;
}
}