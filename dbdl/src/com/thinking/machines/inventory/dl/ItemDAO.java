package com.thinking.machines.inventory.dl;
import com.thinking.machines.inventory.dl.interfaces.*;
import com.thinking.machines.inventory.dl.pojo.*;
import java.sql.*;
import java.util.*;
import java.math.*;
import com.thinking.machines.inventory.dl.exceptions.*;
public class ItemDAO implements ItemDAOInterface
{
public void add(ItemDTOInterface itemDTO) throws DAOException
{
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select code from item where upper(name)=?");
preparedStatement.setString(1,itemDTO.getName().toUpperCase());
ResultSet resultSet=preparedStatement.executeQuery();
if(resultSet.next())
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Item : "+itemDTO.getName()+" exists.");
}
resultSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("insert into item (name,category,type,price) values (?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
preparedStatement.setString(1,itemDTO.getName());
preparedStatement.setString(2,itemDTO.getCategory());
preparedStatement.setString(3,itemDTO.getType());
preparedStatement.setBigDecimal(4,itemDTO.getPrice());
preparedStatement.executeUpdate();
resultSet=preparedStatement.getGeneratedKeys();
resultSet.next();
int code=resultSet.getInt(1);
resultSet.close();
preparedStatement.close();
connection.close();
itemDTO.setCode(code);
}catch(SQLException sqlException)
{
System.out.println(sqlException); // remove after testing
throw new DAOException("Unable to add");
}
}
public void update(ItemDTOInterface itemDTO) throws DAOException
{
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select code from item where code=?");
preparedStatement.setInt(1,itemDTO.getCode());
ResultSet resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid item code : "+itemDTO.getCode());
}
resultSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("select code from item where upper(name)=? and code<>?");
preparedStatement.setString(1,itemDTO.getName().toUpperCase());
preparedStatement.setInt(2,itemDTO.getCode());
resultSet=preparedStatement.executeQuery();
if(resultSet.next())
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Item : "+itemDTO.getName()+" exists");
}
resultSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("update item set name=?,category=?,type=?,price=? where code=?");
preparedStatement.setString(1,itemDTO.getName());
preparedStatement.setString(2,itemDTO.getCategory());
preparedStatement.setString(3,itemDTO.getType());
preparedStatement.setBigDecimal(4,itemDTO.getPrice());
preparedStatement.setInt(5,itemDTO.getCode());
preparedStatement.executeUpdate();
preparedStatement.close();
connection.close();
}catch(SQLException sqlException)
{
System.out.println(sqlException); // remove after testing
throw new DAOException("Unable to update");
}
}
public void delete(int code) throws DAOException
{
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select code from item where code=?");
preparedStatement.setInt(1,code);
ResultSet resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid item code : "+code);
}
resultSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("delete from item where code=?");
preparedStatement.setInt(1,code);
preparedStatement.executeUpdate();
preparedStatement.close();
connection.close();
}catch(SQLException sqlException)
{
System.out.println(sqlException); // remove after testing
throw new DAOException("Unable to remove");
}
}
public ItemDTOInterface getByCode(int code) throws DAOException
{
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select * from item where code=?");
preparedStatement.setInt(1,code);
ResultSet resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid item code : "+code);
}
ItemDTOInterface itemDTO=new ItemDTO();
itemDTO.setCode(code);
itemDTO.setName(resultSet.getString("name").trim());
itemDTO.setCategory(resultSet.getString("category").trim());
itemDTO.setType(resultSet.getString("type").trim());
itemDTO.setPrice(resultSet.getBigDecimal("price"));
resultSet.close();
preparedStatement.close();
connection.close();
return itemDTO;
}catch(SQLException sqlException)
{
System.out.println(sqlException); // remove after testing
throw new DAOException("Unable to fetch item");
}
}
public ItemDTOInterface getByName(String name) throws DAOException
{
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select * from item where name=?");
preparedStatement.setString(1,name);
ResultSet resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid item : "+name);
}
ItemDTOInterface itemDTO=new ItemDTO();
itemDTO.setCode(resultSet.getInt("code"));
itemDTO.setName(resultSet.getString("name").trim());
itemDTO.setCategory(resultSet.getString("category").trim());
itemDTO.setType(resultSet.getString("type").trim());
itemDTO.setPrice(resultSet.getBigDecimal("price"));
resultSet.close();
preparedStatement.close();
connection.close();
return itemDTO;
}catch(SQLException sqlException)
{
System.out.println(sqlException); // remove after testing
throw new DAOException("Unable to fetch item");
}
}
public List<ItemDTOInterface> getAll() throws DAOException
{
try
{
List<ItemDTOInterface> items=new LinkedList<>();
Connection connection=DAOConnection.getConnection();
Statement statement=connection.createStatement();
ResultSet resultSet=statement.executeQuery("select * from item order by name");
ItemDTOInterface itemDTO;
while(resultSet.next())
{
itemDTO=new ItemDTO();
itemDTO.setCode(resultSet.getInt("code"));
itemDTO.setName(resultSet.getString("name").trim());
itemDTO.setCategory(resultSet.getString("category").trim());
itemDTO.setType(resultSet.getString("type").trim());
itemDTO.setPrice(resultSet.getBigDecimal("price"));
items.add(itemDTO);
}
resultSet.close();
statement.close();
connection.close();
return items;
}catch(SQLException sqlException)
{
System.out.println(sqlException); // remove after testing
throw new DAOException("Unable to fetch items");
}
}
public int getCount() throws DAOException
{
try
{
Connection connection=DAOConnection.getConnection();
Statement statement=connection.createStatement();
ResultSet resultSet=statement.executeQuery("select count(*) as cnt from item");
resultSet.next();
int count=resultSet.getInt("cnt");
resultSet.close();
statement.close();
connection.close();
return count;
}catch(SQLException sqlException)
{
System.out.println(sqlException); // remove after testing
throw new DAOException("Unable to fetch item count");
}
}
}