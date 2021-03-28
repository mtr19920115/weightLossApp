package com.ming.weightlossapp.TechnicalServices.PersistentData;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseHelper {
    private static final String CLS="com.mysql.jdbc.Driver";
    private static final String URL="jdbc:mysql://testdb.cpgav9bgulff.us-east-1.rds.amazonaws.com:3306/CPSC597?characterEncoding=utf8&amp;useSSL=false";
    private static final String USER="root";
    private static final String PWD="nwsama520";

    public static Connection conn;
    public static Statement stmt;
    public static PreparedStatement pstmt;
    public static ResultSet rs;


    //build link
    public static void getConnection()
    {
        try{
            Class.forName(CLS);
            conn= (Connection) DriverManager.getConnection(URL,USER,PWD);
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    //close db instance
    public static void closeAll()
    {
        try{
            if(rs!=null)
            {
                rs.close();
                rs=null;
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }

        try{
            if(pstmt!=null)
            {
                pstmt.close();
                pstmt=null;
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }

        try{
            if(stmt!=null)
            {
                stmt.close();
                stmt=null;
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }

        try{
            if(conn!=null)
            {
                conn.close();
                conn=null;
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }

    }
}
