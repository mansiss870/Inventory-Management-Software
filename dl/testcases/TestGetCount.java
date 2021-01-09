import com.thinking.machines.inventory.dl.*;
import com.thinking.machines.inventory.dl.interfaces.*;
import com.thinking.machines.inventory.dl.exceptions.*;
import com.thinking.machines.inventory.dl.pojo.*;
import java.math.*;
import java.util.*;
class TestGetCount
{
public static void main(String gg[])
{
try{
ItemDAOInterface itemDAO=new ItemDAO();
System.out.println(itemDAO.getCount());
}catch(Exception ex)
{
}
}
}
