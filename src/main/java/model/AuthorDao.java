/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import javax.activation.DataSource;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

/**
 *
 * @author Timothy
 */
@Dependent
public class AuthorDao implements AuthorDaoStrategy, Serializable{

@Inject
    private DbStrategy db;

    private String driverClass;
    private String url;
    private String userName;
    private String password;
    

    public AuthorDao() {
           }

  @Override
  public void initDao(String driver, String url, String user, String password){
      setDriverClass(driver);
      setUrl(url);
      setUserName(user);
      setPassword(password);
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

    @Override
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
    
    @Override
    public void updateAuthorById(List<String> colNames, List<Object> colValues, Object whereValue) throws ClassNotFoundException, SQLException{
        db.openConnection(driverClass, url, userName, password);
        db.updateRecordByPrimaryKey("author", colNames, colValues, "author_id", whereValue);
        db.closeConnection();
    }

    public String getDriverClass() {
        return driverClass;
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    
    public static void main(String[] args) throws Exception {
        //AuthorDaoStrategy dao = new AuthorDao(new MySqlDbStrategy(), "com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/book", "root", "admin");
        //Author author = dao.findAuthorById(4);
        List<String> colNames = Arrays.asList("author_name", "date_added");
        
        List<Object> colValues = new ArrayList<>();
        colValues.add("Chelsea Orozco");
        colValues.add("1991-03-27");
       // dao.updateAuthorById(colNames, colValues, 8);
       // List<Author> authors = dao.getAuthorList();
        //System.out.println(authors);
        //System.out.println(author);
    }
}
