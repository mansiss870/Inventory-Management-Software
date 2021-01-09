import com.thinking.machines.inventory.dl.*;
import com.thinking.machines.inventory.dl.pojo.*;
import com.thinking.machines.inventory.dl.interfaces.*;
import com.thinking.machines.inventory.dl.exceptions.*;
import com.thinking.machines.inventory.bl.*;
import com.thinking.machines.inventory.bl.pojo.*;
import com.thinking.machines.inventory.bl.interfaces.*;
import com.thinking.machines.inventory.bl.exceptions.*;
import java.util.*;
import java.math.*;
class TestGetByName
{
public static void main(String gg[])
{
String vName=gg[0];
try
{
ItemManager itemM=new ItemManager();
ItemInterface item=new Item();
item=itemM.getByName(vName);
System.out.println(item.getCode()+"\n");
System.out.println(item.getName()+"\n");
System.out.println(item.getCategory()+"\n");
System.out.println(item.getType()+"\n");
System.out.println(item.getPrice()+"\n");
}
catch(ValidationException ex)
{
System.out.println(ex.getException(ex.getProperties()));

}

}

}
