import com.thinking.machines.inventory.dl.*;
import com.thinking.machines.inventory.dl.pojo.*;
import com.thinking.machines.inventory.dl.interfaces.*;
import com.thinking.machines.inventory.dl.exceptions.*;
import java.util.*;
import java.math.*;
class GetByName
{
public static void main(String gg[])
{
String vName=gg[0];
try
{
ItemDAOInterface itemDAO=new ItemDAO();
ItemDTOInterface itemDTO=new ItemDTO();
itemDTO=itemDAO.getByName(vName);
System.out.println(itemDTO.getCode()+"\n");
System.out.println(itemDTO.getName()+"\n");
System.out.println(itemDTO.getCategory()+"\n");
System.out.println(itemDTO.getType()+"\n");
System.out.println(itemDTO.getPrice()+"\n");
}catch(Exception ex)
{
System.out.println(ex);
}
}
}

