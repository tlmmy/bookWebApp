/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Timothy
 */
public interface DbStrategy {

    void closeConnection() throws SQLException;

    List<Map<String, Object>> findAllRecords(String tableName, int maxRecords) throws SQLException;
    void deleteRecordByPrimaryKey(String tableName, String columnName, Object primaryKey) throws SQLException;
    void openConnection(String driverClass, String url, String userName, String password) throws ClassNotFoundException, SQLException;
    void createRecord(String tableName, List<String> colNames, List<Object> colValues) throws SQLException;
    public Map<String,Object> findRecordByPrimaryKey(String tableName, String colName, Object primaryKey) throws SQLException;
    
}
