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
class TestUpdate
{
public static void main(String data[])
{
int code=Integer.parseInt(data[0]);
String name=data[1];
String category=data[2];
String type=data[3];
BigDecimal price= new BigDecimal(data[4]);
ItemInterface item=new Item();
item.setCode(code);
item.setName(name);
item.setCategory(category);
item.setType(type);
item.setPrice(price);
try{
ItemManager itemM=new ItemManager();
itemM.update(item);
}catch(Exception ex)
{
System.out.println(ex);
}
} 
}
