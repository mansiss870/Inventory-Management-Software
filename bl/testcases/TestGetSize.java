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
class TestGetSize
{
public static void main(String data[])
{
try
{
ItemManager itemM=new ItemManager();
System.out.println(itemM.getListSize());
}
catch(Exception ex)
{
System.out.println(ex);

}

}
 
}
