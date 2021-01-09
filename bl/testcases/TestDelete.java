import com.thinking.machines.inventory.dl.pojo.*;
import com.thinking.machines.inventory.dl.*;
import com.thinking.machines.inventory.dl.interfaces.*;
import com.thinking.machines.inventory.dl.exceptions.*;
import com.thinking.machines.inventory.bl.pojo.*;
import com.thinking.machines.inventory.bl.*;
import com.thinking.machines.inventory.bl.interfaces.*;
import com.thinking.machines.inventory.bl.exceptions.*;
import java.util.*;
import java.math.*;
class TestDelete
{
public static void main(String gg[])
{
int vCode=Integer.parseInt(gg[0]);
try
{
ItemManager itemM=new ItemManager();
itemM.delete(vCode);
}catch(Exception ex)
{
System.out.println(ex);
}
}
}
