/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Timothy
 */
public class AuthorDao implements AuthorDaoStrategy {

    private DbStrategy db;
    private final String driverClass;
    private final String url;
    private final String userName;
    private final String password;

    public AuthorDao(DbStrategy db, String driverClass, String url, String userName, String password) {
        this.db = db;
        this.driverClass = driverClass;
        this.url = url;
        this.userName = userName;
        this.password = password;
    }

    public DbStrategy getDb() {
        return db;
    }

    public void setDb(DbStrategy db) {
        this.db = db;
    }

    @Override
    public List<Author> getAuthorList()
            throws ClassNotFoundException, SQLException {

        db.openConnection(driverClass, url, userName, password);

        List<Map<String, Object>> records = db.findAllRecords("author", 500);
        List<Author> authors = new ArrayList();
        for (Map<String, Object> rec : records) {
            Author author = new Author();
            int id = Integer.parseInt(rec.get("author_id").toString());
            author.setAuthorId(id);
            String authorName = rec.get("author_name").toString();
            author.setAuthorName(authorName != null ? authorName : "");
            Date dateAdded = (Date) rec.get("date_added");
            author.setDateAdded(dateAdded);
            authors.add(author);
        }

        db.closeConnection();
        return authors;
    }

    @Override
    public void createAuthor(List<Object> colValues) throws ClassNotFoundException, SQLException {
        db.openConnection(driverClass, url, userName, password);
        List<String> colNames = Arrays.asList("author_name", "date_added");
        db.createRecord("author", colNames, colValues);
        db.closeConnection();
    }

    @Override
    public void deleteAuthorById(String primaryKey) throws ClassNotFoundException, SQLException, NumberFormatException {
        
        db.openConnection(driverClass, url, userName, password);
        Integer id = Integer.parseInt(primaryKey);
        db.deleteRecordByPrimaryKey("author", "author_id", id);
        db.closeConnection();
    }

    public Author findAuthorById(Object primaryKey) throws ClassNotFoundException, SQLException {
        db.openConnection(driverClass, url, userName, password);
        Map<String, Object> rec = db.findRecordByPrimaryKey("author", "author_id", primaryKey);
        Author author = new Author();
        int id = Integer.parseInt(rec.get("author_id").toString());
        author.setAuthorId(id);
        String authorName = rec.get("author_name").toString();
        author.setAuthorName(authorName != null ? authorName : "");
        Date dateAdded = (Date) rec.get("date_added");
        author.setDateAdded(dateAdded);
        db.closeConnection();
        return author;
    }

    public static void main(String[] args) throws Exception {
        AuthorDaoStrategy dao = new AuthorDao(new MySqlDbStrategy(), "com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/book", "root", "admin");
        //Author author = dao.findAuthorById(4);
        List<Author> authors = dao.getAuthorList();
        System.out.println(authors);
        //System.out.println(author);
    }
}
