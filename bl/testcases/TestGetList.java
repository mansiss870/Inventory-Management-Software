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
class TestGetList
{
public static void main(String[] data)
{
List<ItemInterface> list;
try
{
ItemManager Mitem=new ItemManager();
list=Mitem.getList();
System.out.println(list.size());
ItemInterface item;
for(int i=0;i<list.size();++i)
{
item=new Item();
item=list.get(i);
System.out.println("Code:"+item.getCode()+"\n");
System.out.println("Name:"+item.getName()+"\n");
System.out.println("Category:"+item.getCategory()+"\n");
System.out.println("Type:"+item.getType()+"\n");
System.out.println("Price:"+item.getPrice()+"\n");
}
}catch(Exception ex)
{
System.out.println(ex);
}

}
}
