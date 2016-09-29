/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

/**
 *
 * @author trogers8
 */
public class MySqlDbStrategy implements DbStrategy {

    private Connection conn;

    @Override
    public void openConnection(String driverClass, String url,
            String userName, String password)
            throws ClassNotFoundException, SQLException {

        Class.forName(driverClass);
        conn = DriverManager.getConnection(url, userName, password);
    }

    @Override
    public void closeConnection() throws SQLException {
        conn.close();
    }

    @Override
    public List<Map<String, Object>> findAllRecords(String tableName, int maxRecords) throws SQLException {

        String sql = "SELECT * FROM " + tableName + " LIMIT " + maxRecords;
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        List<Map<String, Object>> records = new ArrayList<>();
        ResultSetMetaData rsmd = rs.getMetaData();
        int colCount = rsmd.getColumnCount();

        while (rs.next()) {
            Map<String, Object> record = new LinkedHashMap<>();
            for (int i = 1; i <= colCount; i++) {
                String colName = rsmd.getColumnName(i);
                Object colData = rs.getObject(colName);
                record.put(colName, colData);
            }
            records.add(record);
        }

        return records;
    }

    @Override
    public Map<String, Object> findRecordByPrimaryKey(String tableName, String colName, Object primaryKey) throws SQLException {
        String sql = "SELECT * FROM " + tableName + " WHERE " + colName + " = " + primaryKey;
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        ResultSetMetaData rsmd = rs.getMetaData();
        int colCount = rsmd.getColumnCount();

        Map<String, Object> record = new LinkedHashMap<>();
        while (rs.next()) {

            for (int i = 1; i <= colCount; i++) {
                String columnName = rsmd.getColumnName(i);
                Object colData = rs.getObject(columnName);
                record.put(columnName, colData);
            }

        }

        return record;
    }

    //DELETE FROM tableName WHERE columnName = primaryKey
    @Override
    public void deleteRecordByPrimaryKey(String tableName, String columnName, Object primaryKey) throws SQLException {

        PreparedStatement stmt = buildDeleteStatement(tableName, columnName, primaryKey);

        stmt.executeUpdate();
    }

    @Override
    public void createRecord(String tableName, List<String> colNames, List<Object> colValues) throws SQLException {
        PreparedStatement stmt = buildInsertStatement(tableName, colNames);
        for (int i = 0; i < colValues.size(); i++) {
            stmt.setObject(i + 1, colValues.get(i));
        }
        stmt.executeUpdate();
    }

    private PreparedStatement buildInsertStatement(String tableName, List colNames)
            throws SQLException {

        String sql = "INSERT INTO " + tableName;
        StringJoiner sj = new StringJoiner(", ", "(", ")");
        for (Object colName : colNames) {
            sj.add(colName.toString());
        }
        sql += sj.toString();
        sql += " VALUES ";
        sj = new StringJoiner(", ", "(", ")");
        for (Object colName : colNames) {
            sj.add("?");
        }
        sql += sj.toString();
        return conn.prepareStatement(sql);
    }

    private PreparedStatement buildDeleteStatement(String tableName, String columnName, Object primaryKey)
            throws SQLException {
        String sql = "DELETE FROM " + tableName + " WHERE " + columnName + " = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setObject(1, primaryKey);
        return stmt;
    }

    private PreparedStatement buildUpdateStatement(String tableName,
            List colNames, String whereField)
            throws SQLException {
               
        String sql = "UPDATE " + tableName + " SET ";
        StringJoiner sj = new StringJoiner(", ","","");
        for(Object colName : colNames){
            sj.add(colName.toString() + " = ?,");
        }
        sql += sj.toString();
        sql += " WHERE " + whereField + " = ?";
        return conn.prepareStatement(sql);
    }

    public static void main(String[] args) throws Exception {
        MySqlDbStrategy db = new MySqlDbStrategy();
        db.openConnection("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/book", "root", "admin");
        //List<Map<String, Object>> records = db.findAllRecords("author", 1000);
        //System.out.println(author);
        //List<String> colNames = Arrays.asList("author_name", "date_added");
        List<String> colNames = Arrays.asList("author_id", "author_name", "date_added");
        //List<Object> colValues = new ArrayList<>();
        //colValues.add("Chelsea Orozco");
        //colValues.add("1991-03-27");
        //db.createRecord("author", colNames , colValues);
        //Map<String, Object> author = db.findRecordByPrimaryKey("author", "author_id", 1);
        //System.out.println(author);
        //db.deleteRecordByPrimaryKey("author", "author_id", 6);
        List<Map<String, Object>> records = db.findAllRecords("author", 1000);
        System.out.println(records);
        db.closeConnection();
    }

}
