package com.rohit.assignment_09;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
	
	 private static Connection  con ;
	 private DBConnection(){}
	 static 
	 {
		 try
		 {
			 Class.forName("oracle.jdbc.driver.OracleDriver");
			 con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","system","manager");
			 
		 } catch(Exception e) {  e.printStackTrace(); }
		 
		     
     }
		 
		 public static Connection  getcon()
		 {
			 return con;
		 }
		 

}
