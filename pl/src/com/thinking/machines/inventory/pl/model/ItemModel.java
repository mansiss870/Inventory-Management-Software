package com.thinking.machines.inventory.pl.model;
import com.thinking.machines.inventory.pl.model.*;
import com.thinking.machines.inventory.dl.*;
import com.thinking.machines.inventory.dl.interfaces.*;
import com.thinking.machines.inventory.dl.exceptions.*;
import com.thinking.machines.inventory.dl.pojo.*;
import com.thinking.machines.inventory.bl.*;
import com.thinking.machines.inventory.bl.interfaces.*;
import com.thinking.machines.inventory.bl.exceptions.*;
import com.thinking.machines.inventory.bl.pojo.*;
import java.io.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
public class ItemModel extends AbstractTableModel
{
private String[] title={"S.No","Item"};
private java.util.List<ItemInterface> items;
private ItemManager itemManager=new ItemManager();
public ItemModel() throws ValidationException,ProcessException
{
items=itemManager.getList();
}
public int getRowCount()
{
return items.size();
}
public int getColumnCount()
{
return title.length;
}
public String getColumnName(int ci)
{
return title[ci];
}
public boolean isCellEdittable(int ri,int ci)
{
return false;

}
public Class getColumnClass(int ci)
{
if(ci==0) return Integer.class;
return String.class;

}
public Object getValueAt(int ri,int ci)
{
if(ci==0)
{
return new Integer(ri+1);

}
return items.get(ri).getName();

}
public ItemInterface getItemAt(int index)
{
if(index<0) return null;
return items.get(index);
}
public int searchItem(String g)
{
int x=0;
for(ItemInterface i:items)
{
if(i.getName().toUpperCase().startsWith(g.toUpperCase())) return x;
x++;
}
return -1;
}
public void addItem(ItemInterface item) throws ValidationException,ProcessException
{
String name=item.getName();
try{
itemManager.add(item);
}catch(ValidationException ex)
{
throw ex;
}catch(ProcessException pe)
{
throw pe;
}
System.out.println("add1");
item=itemManager.getByName(name);
System.out.println("add1");
items.add(item);
fireTableDataChanged();
}
public void editItem(ItemInterface item,int index) throws ValidationException,ProcessException
{
itemManager.update(item);
items.remove(index);
items.add(index,item);
fireTableDataChanged();
}
public void deleteItem(int code) throws ValidationException,ProcessException
{
itemManager.delete(code);
int count=0;
for(ItemInterface i:items)
{
if(i.getCode()==code)
{
items.remove(count);
break;
}
count++;
}
fireTableDataChanged();
}
}
