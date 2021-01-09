import com.thinking.machines.inventory.dl.*;
import com.thinking.machines.inventory.dl.interfaces.*;
import com.thinking.machines.inventory.dl.exceptions.*;
import com.thinking.machines.inventory.dl.pojo.*;
import java.util.*;
import java.math.*;
import java.io.*;
class ItemAddTestCase
{
public static void main(String data[])
{
String name=data[0];
String category=data[1];
String type=data[2];
BigDecimal price= new BigDecimal(data[3]);
ItemDTO item=new ItemDTO();
item.setName(name);
item.setCategory(category);
item.setType(type);
item.setPrice(price);
try{
ItemDAO itemDAO=new ItemDAO();
itemDAO.add(item);
}catch(Exception ex)
{
System.out.println(ex);
}
} 
}
