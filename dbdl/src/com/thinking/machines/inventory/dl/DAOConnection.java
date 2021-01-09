package com.thinking.machines.inventory.dl;
import java.sql.*;
class DAOConnection
{
private DAOConnection(){}
public static Connection getConnection()
{
Connection c=null;
try
{
Class.forName("org.apache.derby.jdbc.ClientDriver");
c=DriverManager.getConnection("jdbc:derby://localhost:1527/inventorydb");
}catch(SQLException sqlException)
{
System.out.println(sqlException);
}catch(ClassNotFoundException classNotFoundException)
{
System.out.println(classNotFoundException);
}
return c;
}
}