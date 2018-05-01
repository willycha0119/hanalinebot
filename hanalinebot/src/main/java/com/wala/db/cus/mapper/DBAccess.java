package com.wala.db.cus.mapper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service("DBAccess")
public class DBAccess {
	
	private Connection conn = null;
	private Statement stmt = null;
	private ResultSet rs = null;
	public DBAccess() {
	
	}
	
	public int actionQuery(String value) {
		int rtn = 0;
		try {
			//System.out.println(value);
			conn = DruidManager.getInstance().getConnection();		
			stmt = conn.createStatement();
			rtn = stmt.executeUpdate(value);
			if (stmt != null) {
		        try {
		        	stmt.close();
		        } catch (SQLException e) { /* ignored */}
		    }
		    if (conn != null) {
		        try {
		            conn.close();
		        } catch (SQLException e) { /* ignored */}
		    }	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (stmt != null) {
		        try {
		        	stmt.close();
		        } catch (SQLException e) { /* ignored */}
		    }
		    if (conn != null) {
		        try {
		            conn.close();
		        } catch (SQLException e) { /* ignored */}
		    }	
		}
		return rtn;
	}
			
    public List<Map<String, Object>> selectQuery(String value) {
    	List<Map<String, Object>> rtn = new ArrayList();
    	try {
    		//System.out.println(value);
    		conn = DruidManager.getInstance().getConnection();	
    		stmt = conn.createStatement();
    		
			rs = stmt.executeQuery(value);
			rtn = resultSetToList(rs);
			if (rs != null) {
		        try {
		            rs.close();
		        } catch (SQLException e) { /* ignored */}
		    }
		    if (stmt != null) {
		        try {
		        	stmt.close();
		        } catch (SQLException e) { /* ignored */}
		    }
		    if (conn != null) {
		        try {
		            conn.close();
		        } catch (SQLException e) { /* ignored */}
		    }	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (rs != null) {
		        try {
		            rs.close();
		        } catch (SQLException e) { /* ignored */}
		    }
		    if (stmt != null) {
		        try {
		        	stmt.close();
		        } catch (SQLException e) { /* ignored */}
		    }
		    if (conn != null) {
		        try {
		            conn.close();
		        } catch (SQLException e) { /* ignored */}
		    }				
			
			
		}
    	return rtn;
    }	
	
	private List<Map<String, Object>> resultSetToList(ResultSet rs) throws Exception {
		List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
		
		if(rs != null && !rs.isClosed()) {
		    ResultSetMetaData md = rs.getMetaData();
		    int columns = md.getColumnCount();
		    
		    while (rs.next()){
		        Map<String, Object> row = new HashMap<String, Object>(columns);
		        for(int i = 1; i <= columns; ++i){
		            row.put(md.getColumnName(i), rs.getObject(i));
		        }
		        rows.add(row);
		    }
		}
	
	    return rows;
	}
}
