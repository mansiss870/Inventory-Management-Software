import com.thinking.machines.inventory.dl.pojo.*;
import com.thinking.machines.inventory.dl.*;
import com.thinking.machines.inventory.dl.interfaces.*;
import com.thinking.machines.inventory.dl.exceptions.*;
import java.util.*;
import java.math.*;
class Delete
{
public static void main(String gg[])
{
int vCode=Integer.parseInt(gg[0]);
try
{
ItemDAOInterface itemDAO=new ItemDAO();
itemDAO.delete(vCode);
}catch(Exception ex)
{
System.out.println(ex);
}
}
}

