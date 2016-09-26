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
    public Map<String,Object> findRecordByPrimaryKey(String tableName, String colName, Object primaryKey) throws SQLException{
        String sql = "SELECT * FROM " + tableName + " WHERE " + colName + " = " + primaryKey;
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        ResultSetMetaData rsmd = rs.getMetaData();
        int colCount = rsmd.getColumnCount();
        Map<String, Object> record = new LinkedHashMap<>();
            for (int i = 1; i <= colCount; i++) {
                String columnName = rsmd.getColumnName(i);
                Object colData = rs.getObject(colName);
                record.put(columnName, colData);
            }
        return record;
    }

    //DELETE FROM tableName WHERE columnName = primaryKey
    @Override
    public void deleteRecordByPrimaryKey(String tableName, String columnName, Object primaryKey) throws SQLException {
        String sql = "DELETE FROM " + tableName + " WHERE " + columnName + " = " + primaryKey;
        Statement stmt = conn.createStatement();
        stmt.executeUpdate(sql);
           
    }

    @Override
    public void createRecord(String tableName, List<String> colNames, List<Object> colValues) throws SQLException {
        PreparedStatement pstmt = null;
        int recsUpdated = 0;
        pstmt = buildInsertStatement(conn, tableName, colNames);

        final Iterator i = colValues.iterator();
        int index = 1;
        while (i.hasNext()) {
            final Object obj = i.next();
            if (obj instanceof String) {
                pstmt.setString(index++, (String) obj);
            } else if (obj instanceof Integer) {
                pstmt.setInt(index++, ((Integer) obj).intValue());
            } else if (obj instanceof Long) {
                pstmt.setLong(index++, ((Long) obj).longValue());
            } else if (obj instanceof Double) {
                pstmt.setDouble(index++, ((Double) obj).doubleValue());
            } else if (obj instanceof java.sql.Date) {
                pstmt.setDate(index++, (java.sql.Date) obj);
            } else if (obj instanceof Boolean) {
                pstmt.setBoolean(index++, ((Boolean) obj).booleanValue());
            } else if (obj != null) {
                pstmt.setObject(index++, obj);
            }
        }
        recsUpdated = pstmt.executeUpdate();
        pstmt.close();
     
    }

    private PreparedStatement buildInsertStatement(Connection conn_loc, String tableName, List colDescriptors)
            throws SQLException {
        StringBuffer sql = new StringBuffer("INSERT INTO ");
        (sql.append(tableName)).append(" (");
        final Iterator i = colDescriptors.iterator();
        while (i.hasNext()) {
            (sql.append((String) i.next())).append(", ");
        }
        sql = new StringBuffer((sql.toString()).substring(0, (sql.toString()).lastIndexOf(", ")) + ") VALUES (");
        for (int j = 0; j < colDescriptors.size(); j++) {
            sql.append("?, ");
        }
        final String finalSQL = (sql.toString()).substring(0, (sql.toString()).lastIndexOf(", ")) + ")";
        //System.out.println(finalSQL);
        return conn_loc.prepareStatement(finalSQL);
    }

    public static void main(String[] args) throws Exception {
        MySqlDbStrategy db = new MySqlDbStrategy();
        db.openConnection("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/book", "root", "admin");
        List<Map<String, Object>> records = db.findAllRecords("author", 1000);
        System.out.println(records);
        db.closeConnection();
    }

}
