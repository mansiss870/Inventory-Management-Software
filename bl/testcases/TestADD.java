import com.thinking.machines.inventory.dl.*;
import com.thinking.machines.inventory.dl.interfaces.*;
import com.thinking.machines.inventory.dl.exceptions.*;
import com.thinking.machines.inventory.dl.pojo.*;
import com.thinking.machines.inventory.bl.*;
import com.thinking.machines.inventory.bl.interfaces.*;
import com.thinking.machines.inventory.bl.exceptions.*;
import com.thinking.machines.inventory.bl.pojo.*;
import java.util.*;
import java.math.*;
import java.io.*;
class TestAdd1
{
public static void main(String data[])
{
String name=data[0];
String category=data[1];
String type=data[2];
BigDecimal price= new BigDecimal(data[3]);
ItemInterface item=new Item();
item.setName(name);
item.setCategory(category);
item.setType(type);
item.setPrice(price);
try
{
ItemManager itemM=new ItemManager();
itemM.add(item);

}
catch(Exception ex)
{
System.out.println(ex);

}

}
 
}
