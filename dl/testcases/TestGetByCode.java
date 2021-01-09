import com.thinking.machines.inventory.dl.*;
import com.thinking.machines.inventory.dl.pojo.*;
import com.thinking.machines.inventory.dl.interfaces.*;
import com.thinking.machines.inventory.dl.exceptions.*;
import java.util.*;
import java.math.*;
class GetByCode
{
public static void main(String gg[])
{
int vCode=Integer.parseInt(gg[0]);
try
{
ItemDAOInterface itemDAO=new ItemDAO();
ItemDTOInterface itemDTO=new ItemDTO();
itemDTO=itemDAO.getByCode(vCode);
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

