import com.thinking.machines.inventory.dl.*;
import com.thinking.machines.inventory.dl.interfaces.*;
import com.thinking.machines.inventory.dl.exceptions.*;
import com.thinking.machines.inventory.dl.pojo.*;
import java.util.*;
import java.math.*;
import java.io.*;
class TestGetAll
{
public static void main(String[] data)
{
List<ItemDTOInterface> list;
try
{
ItemDAOInterface itemDAO=new ItemDAO();
list=itemDAO.getAll();
System.out.println(list.size());
ItemDTOInterface itemDTO;
for(int i=0;i<=list.size();++i)
{
itemDTO=new ItemDTO();
itemDTO=list.get(i);
System.out.println("Code:"+itemDTO.getCode()+"\n");
System.out.println("Name:"+itemDTO.getName()+"\n");
System.out.println("Category:"+itemDTO.getCategory()+"\n");
System.out.println("Type:"+itemDTO.getType()+"\n");
System.out.println("Price:"+itemDTO.getPrice()+"\n");
}
}catch(Exception ex)
{

}

}
}
