package com.thinking.machines.inventory.dl;
import com.thinking.machines.inventory.POJOCopier.*;
import com.thinking.machines.inventory.dl.interfaces.*;
import com.thinking.machines.inventory.dl.exceptions.*;
import com.thinking.machines.inventory.dl.pojo.*;
import java.util.*;
import java.math.*;
import java.io.*;
public class ItemDAO implements ItemDAOInterface
{
final private String datafile="Items.data";
public void add(ItemDTOInterface itemDTO) throws DAOException
{
try
{
File f;
f=new File(datafile);
RandomAccessFile raf=new RandomAccessFile(f,"rw");
int vCode;
String vName;
String vCategory;
String vType;
BigDecimal vPrice;
int lastCode=0;
vName=itemDTO.getName();
vCategory=itemDTO.getCategory();
vType=itemDTO.getType();
vPrice=itemDTO.getPrice();
if(!(f.exists()))
{
throw new DAOException("FIle not exists.");

}
if(raf.length()>0)
{
lastCode=Integer.parseInt(raf.readLine().trim());
while(raf.getFilePointer()<raf.length())
{
raf.readLine();
if(raf.readLine().equals(vName))
{
throw new DAOException("Item already exists.");

}
raf.readLine();
raf.readLine();
raf.readLine();

}
vCode=lastCode+1;
raf.writeBytes(vCode+"\n");
raf.writeBytes(vName+"\n");
raf.writeBytes(vCategory+"\n");
raf.writeBytes(vType+"\n");
raf.writeBytes(vPrice.toPlainString()+"\n");
raf.seek(0);
raf.writeBytes(String.format("%10d\n",vCode));

}
else
{
vCode=1;
raf.writeBytes(String.format("%10d\n",vCode));
raf.writeBytes(vCode+"\n");
raf.writeBytes(vName+"\n");
raf.writeBytes(vCategory+"\n");
raf.writeBytes(vType+"\n");
raf.writeBytes(vPrice.toPlainString()+"\n");

}
raf.close();

}
catch(Exception ex)
{
System.out.println(ex);

}

}
public void update(ItemDTOInterface itemDTO) throws DAOException
{
int found=0;
int code;
int vCode;
String vName;
String name;
try
{
File f=new File(datafile);
if(!f.exists())
{
throw new DAOException("File not exists.");

}
RandomAccessFile raf=new RandomAccessFile(f,"rw");
code=itemDTO.getCode();
name=itemDTO.getName();
raf.readLine();
while(raf.getFilePointer()<raf.length())
{
vCode=Integer.parseInt(raf.readLine());
if(vCode==code) 
{
found=1;
break;

}
raf.readLine();
raf.readLine();
raf.readLine();
raf.readLine();

}
if(found==1)
{
raf.seek(0);
raf.readLine();
while(raf.getFilePointer()<raf.length())
{
vCode=Integer.parseInt(raf.readLine());
vName=raf.readLine();
if(vCode!=code && name.toUpperCase().equals(vName.toUpperCase()))
{
throw new DAOException("Item:"+vName+" already exists.");

}
raf.readLine();
raf.readLine();
raf.readLine();

}
File tempfile=new File("Temp.data");
RandomAccessFile tempraf=new RandomAccessFile(tempfile,"rw");
raf.seek(0);
tempraf.writeBytes(raf.readLine()+"\n");
while(raf.getFilePointer()<raf.length())
{
vCode=Integer.parseInt(raf.readLine());
if(vCode==code)
{
raf.readLine();
raf.readLine();
raf.readLine();
raf.readLine();
tempraf.writeBytes(vCode+"\n");
tempraf.writeBytes(itemDTO.getName()+"\n");
tempraf.writeBytes(itemDTO.getCategory()+"\n");
tempraf.writeBytes(itemDTO.getType()+"\n");
tempraf.writeBytes(itemDTO.getPrice()+"\n");

}
else
{
tempraf.writeBytes(vCode+"\n");
tempraf.writeBytes(raf.readLine()+"\n");
tempraf.writeBytes(raf.readLine()+"\n");
tempraf.writeBytes(raf.readLine()+"\n");
tempraf.writeBytes(raf.readLine()+"\n");

}

}
raf.seek(0);
tempraf.seek(0);
raf.writeBytes(tempraf.readLine()+"\n");
while(tempraf.getFilePointer()<tempraf.length())
{
raf.writeBytes(tempraf.readLine()+"\n");
raf.writeBytes(tempraf.readLine()+"\n");
raf.writeBytes(tempraf.readLine()+"\n");
raf.writeBytes(tempraf.readLine()+"\n");
raf.writeBytes(tempraf.readLine()+"\n");

}
raf.setLength(tempraf.length());
tempraf.setLength(0);
raf.close();
tempraf.close();

}
else
{
throw new DAOException("Invalide code:"+code);

}

}
catch(Exception ex)
{
System.out.println(ex);

}

}
public void delete(int code) throws DAOException
{
int found=0;
int vCode;
try
{
File f=new File(datafile);
if(!f.exists())
{
throw new DAOException("Invalid code:"+code);

}
RandomAccessFile raf=new RandomAccessFile(f,"rw");
raf.readLine();
while(raf.getFilePointer()<raf.length())
{
vCode=Integer.parseInt(raf.readLine());
raf.readLine();
raf.readLine();
raf.readLine();
raf.readLine();
 if(vCode==code)
{
found=1;
break;

}

}
if(found==1)
{
File tempfile=new File("temp.data");
RandomAccessFile tempraf=new RandomAccessFile(tempfile,"rw");
raf.seek(0);
tempraf.writeBytes(raf.readLine()+"\n");
while(raf.getFilePointer()<raf.length())
{
vCode=Integer.parseInt(raf.readLine());
if(vCode==code)
{
raf.readLine();
raf.readLine();
raf.readLine();
raf.readLine();

}
else
{
tempraf.writeBytes(vCode+"\n");
tempraf.writeBytes(raf.readLine()+"\n");
tempraf.writeBytes(raf.readLine()+"\n");
tempraf.writeBytes(raf.readLine()+"\n");
tempraf.writeBytes(raf.readLine()+"\n");

}

}
raf.seek(0);
tempraf.seek(0);
raf.setLength(tempraf.length());
raf.writeBytes(tempraf.readLine()+"\n");
while(tempraf.getFilePointer()<tempraf.length())
{
raf.writeBytes(tempraf.readLine()+"\n");
raf.writeBytes(tempraf.readLine()+"\n");
raf.writeBytes(tempraf.readLine()+"\n");
raf.writeBytes(tempraf.readLine()+"\n");
raf.writeBytes(tempraf.readLine()+"\n");

}
raf.close();
tempraf.setLength(0);
tempraf.close();

}
else
{
throw new DAOException("Invalid code:"+code);

}

}
catch(IOException ioe)
{
throw new DAOException(ioe.getMessage());

}

}
public List<ItemDTOInterface> getAll() throws DAOException
{
List<ItemDTOInterface> items=new LinkedList<ItemDTOInterface>();
try
{
File f=new File(datafile);
if(f.exists())
{
RandomAccessFile raf=new RandomAccessFile(f,"rw");
if(raf.length()>0)
{
raf.readLine();
ItemDTOInterface item;
while(raf.getFilePointer()<raf.length())
{
item=new ItemDTO();
item.setCode(Integer.parseInt(raf.readLine()));
item.setName(raf.readLine());
item.setCategory(raf.readLine());
item.setType(raf.readLine());
item.setPrice(new BigDecimal(raf.readLine()));
items.add(item);

}

}
raf.close();

}

}
catch(IOException ex)
{
ex.printStackTrace();
throw new DAOException("Error");

}
return items;

}
public ItemDTOInterface getByName(String name) throws DAOException
{
int vCode;
ItemDTOInterface itemDTO;
itemDTO=new ItemDTO();
try
{
File f=new File(datafile);
if(!(f.exists()))
{
throw new DAOException("File not exists");

}
RandomAccessFile raf=new RandomAccessFile(f,"rw");
if(raf.length()>0)
{
raf.readLine();
while(raf.getFilePointer()<raf.length())
{
vCode=Integer.parseInt(raf.readLine());
if(name.toUpperCase().equals(raf.readLine().toUpperCase()))
{
itemDTO.setCode(vCode);
itemDTO.setName(name);
itemDTO.setCategory(raf.readLine());
itemDTO.setType(raf.readLine());
itemDTO.setPrice(new BigDecimal(raf.readLine()));

}
raf.readLine();
raf.readLine();
raf.readLine();

}
raf.close();

}

}
catch(Exception ex)
{
System.out.println(ex);

}
if(itemDTO.getCode()==0)
{
throw new DAOException("Item not found.");

}
return itemDTO;

}
public ItemDTOInterface getByCode(int code) throws DAOException
{
int vCode;
ItemDTOInterface itemDTO=new ItemDTO();
try
{
File f=new File(datafile);
if(!(f.exists()))
{
throw new DAOException("File not exists.");

}
RandomAccessFile raf=new RandomAccessFile(f,"rw");
if(raf.length()>0)
{
raf.readLine();
while(raf.getFilePointer()<raf.length())
{
vCode=Integer.parseInt(raf.readLine());
if(code==vCode)
{
itemDTO.setCode(code);
itemDTO.setName(raf.readLine());
itemDTO.setCategory(raf.readLine());
itemDTO.setType(raf.readLine());
itemDTO.setPrice(new BigDecimal(raf.readLine()));

}
raf.readLine();
raf.readLine();
raf.readLine();
raf.readLine();

}

}
raf.close();

}
catch(Exception ex)
{
System.out.println(ex);

}
if(itemDTO.getCode()==0)
{
throw new DAOException("Item not found.\n");

}
return itemDTO;

}
public int getCount() throws DAOException
{
int count=0;
try
{
File f=new File(datafile);
if(!(f.exists()))
{
throw new DAOException("File not exists.");

}
RandomAccessFile raf=new RandomAccessFile(f,"rw");
if(raf.length()>0)
{
raf.readLine();
while(raf.getFilePointer()<raf.length())
{
raf.readLine();
count++;

}
count=count/5;

}
raf.close();

}
catch(Exception ex)
{

}
return count;

}

}
