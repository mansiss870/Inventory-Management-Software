package com.thinking.machines.inventory.bl;
import com.thinking.machines.inventory.POJOCopier.*;
import com.thinking.machines.inventory.bl.interfaces.*;
import com.thinking.machines.inventory.bl.exceptions.*;
import com.thinking.machines.inventory.bl.pojo.*;
import com.thinking.machines.inventory.dl.interfaces.*;
import com.thinking.machines.inventory.dl.exceptions.*;
import com.thinking.machines.inventory.dl.pojo.*;
import com.thinking.machines.inventory.dl.*;
import java.util.*;
import java.util.LinkedList;
import java.math.*;
import java.io.*;
public class ItemManager 
{
private List<ItemInterface> itemsList;
private HashMap<String,ItemInterface> itemNameWiseMap;
private HashMap<Integer,ItemInterface> itemCodeWiseMap;
public ItemManager()
{
populateDataStructures();
}
private void populateDataStructures()
{
this.itemsList=new LinkedList<>();
this.itemNameWiseMap=new HashMap<>();
this.itemCodeWiseMap=new HashMap<>();
try
{
List<ItemDTOInterface> dlItemsList;
dlItemsList=new ItemDAO().getAll();
ItemInterface item;
for(ItemDTOInterface dlItem:dlItemsList)
{
item=new Item();
item.setCode(dlItem.getCode());
item.setName(dlItem.getName());
item.setType(dlItem.getType());
item.setCategory(dlItem.getCategory());
item.setPrice(dlItem.getPrice());
this.itemsList.add(item);
this.itemNameWiseMap.put(item.getName(),item);
this.itemCodeWiseMap.put(new Integer(item.getCode()),item);
}
Collections.sort(this.itemsList,new Comparator<ItemInterface>()
{
public int compare(ItemInterface left,ItemInterface right)
{
return left.getName().toUpperCase().compareTo(right.getName().toUpperCase());
}
});
}
catch(DAOException ex)
{
//System.out.println(ex);
}
}
public void add(ItemInterface item) throws ValidationException,ProcessException
{
ValidationException validationException=new ValidationException();
if(item==null)
{
throw validationException;
}
int vCode=item.getCode();
String vName=item.getName().trim();
String vCategory=item.getCategory().trim();
String vType=item.getType().trim();
BigDecimal vPrice=item.getPrice();
if(vCode!=0)
{
validationException.add("Code","should be zero.");
}
if(vName==null || vName.length()==0)
{
validationException.add("Name","Required");
}
else
{
if(vName.length()>35)
{
validationException.add("Name","cannot exceed 35 characters");

}

}
if(vCategory==null || vCategory.length()==0)
{
validationException.add("Category","Required");

}
else
{
if(vCategory.trim().length()>35)
{
validationException.add("Category","cannot exceed length 35");

}

}
if(vType==null || vType.length()==0)
{
validationException.add("Type","cannot exceed length 35");

}
else
{
if(vType.trim().length()>35)
{
validationException.add("Type","Required");

}

}
if(vPrice.compareTo(new BigDecimal("0.0"))<=0)
{
validationException.add("Price","Required");

}
if(this.itemNameWiseMap.containsKey(vName))
{
validationException.add("name","exists");

}
if(validationException.size()>0)
{
throw validationException;

}
ItemDTOInterface itemDTO=new ItemDTO();
itemDTO.setName(vName);
itemDTO.setCategory(vCategory);
itemDTO.setType(vType);
itemDTO.setPrice(vPrice);
try
{
new ItemDAO().add(itemDTO);
itemDTO=new ItemDAO().getByName(vName);
ItemInterface dsItem=new Item();
dsItem.setCode(itemDTO.getCode());
dsItem.setName(itemDTO.getName());
dsItem.setType(itemDTO.getType());
dsItem.setCategory(itemDTO.getCategory());
dsItem.setPrice(itemDTO.getPrice());
this.itemCodeWiseMap.put(new Integer(dsItem.getCode()),dsItem);
this.itemNameWiseMap.put(dsItem.getName(),dsItem);
if(this.itemsList.size()>0)
{
ItemInterface listItem=this.itemsList.get(0);
int c;
c=vName.toUpperCase().compareTo(listItem.getName().toUpperCase());
if(c<0)
{
this.itemsList.add(0,dsItem);
}
else
{
listItem=this.itemsList.get(this.itemsList.size()-1);
c=vName.toUpperCase().compareTo(listItem.getName().toUpperCase());
if(c>0)
{
this.itemsList.add(this.itemsList.size()-1,dsItem);

}
else
{
String nData =vName.toUpperCase();
int lb=0;
int ub=this.itemsList.size()-1;
String listData;
int mid;
while(true)
{
mid=(lb+ub)/2;
listItem=this.itemsList.get(mid);
listData=listItem.getName().toUpperCase();
c=listData.compareTo(nData);
if(c==0)
{
this.itemsList.add(mid,dsItem);
break;
}
if(c>0)
{
listData=this.itemsList.get(mid-1).getName().toUpperCase();
c=listData.compareTo(nData);
if(c<0)
{
this.itemsList.add(mid,dsItem);
break;

}
else
{
ub=mid;

}

}
else
{
lb=mid+1;

}

}

}
 
}

}
}
catch(DAOException ex)
{
System.out.println(ex);

}

}
public void update(ItemInterface item) throws ValidationException,ProcessException
{
ValidationException validationException=new ValidationException();
if(item==null)
{
throw validationException;

}
int vCode=item.getCode();
String vName=item.getName().trim();
String vCategory=item.getCategory().trim();
String vType=item.getType().trim();
BigDecimal vPrice=item.getPrice();
if(vCode<=0)
{
validationException.add("Code","Required");

}
else
{
if(!this.itemCodeWiseMap.containsKey(vCode))
{
validationException.add("Code","Invalid");

}

}
if(vName==null || vName.length()==0)
{
validationException.add("Name","Required");

}
else
{
if(vName.length()>35)
{
validationException.add("Name","cannot exceed 35 characters");

}

}
if(vCategory==null || vCategory.trim().length()==0)
{
validationException.add("Category","Required");

}
else
{
if(vCategory.trim().length()>35)
{
validationException.add("Category","cannot exceed length 35");

}

}
if(vType==null || vType.trim().length()==0)
{
validationException.add("Type","cannot exceed length 35");

}
else
{
if(vType.trim().length()>35)
{
validationException.add("Type","Required");

}

}
if(vPrice.compareTo(new BigDecimal("0.0"))<=0)
{
validationException.add("Price","Required");

}
ItemInterface vItem=this.itemNameWiseMap.get(vName);
if(vCode!=vItem.getCode())
{
validationException.add("Name","exists.");

}
if(validationException.size()>0)
{
throw validationException;
}
ItemDTOInterface itemDTO=new ItemDTO();
itemDTO.setCode(vCode);
itemDTO.setName(vName);
itemDTO.setCategory(vCategory);
itemDTO.setType(vType);
itemDTO.setPrice(vPrice);
try
{
new ItemDAO().update(itemDTO);
ItemInterface dsItem=new Item();
dsItem.setCode(vCode);
dsItem.setName(vName);
dsItem.setCategory(vCategory);
dsItem.setType(vType);
dsItem.setPrice(vPrice);
this.itemCodeWiseMap.remove(new Integer(vCode));
this.itemNameWiseMap.remove(vItem.getName().toUpperCase());
this.itemCodeWiseMap.put(new Integer(dsItem.getCode()),dsItem);
this.itemNameWiseMap.put(dsItem.getName(),dsItem);
this.itemsList.remove(vItem);
ItemInterface listItem=this.itemsList.get(0);
int c;
c=vName.toUpperCase().compareTo(listItem.getName().toUpperCase());
if(c<0)
{
this.itemsList.add(0,dsItem);
}
else
{
listItem=this.itemsList.get(this.itemsList.size()-1);
c=vName.toUpperCase().compareTo(listItem.getName().toUpperCase());
if(c>0)
{
this.itemsList.add(this.itemsList.size()-1,dsItem);

}
else
{
String nData =vName.toUpperCase();
int lb=0;
int ub=this.itemsList.size()-1;
String listData;
int mid;
while(true)
{
mid=(lb+ub)/2;
listItem=this.itemsList.get(mid);
listData=listItem.getName().toUpperCase();
c=listData.compareTo(nData);
if(c==0)
{
this.itemsList.add(mid,dsItem);
break;
}
if(c>0)
{
listData=this.itemsList.get(mid-1).getName().toUpperCase();
c=listData.compareTo(nData);
if(c<0)
{
this.itemsList.add(mid,dsItem);
break;

}
else
{
ub=mid;

}

}
else
{
lb=mid+1;
}
}
}
}
}
catch(DAOException ex)
{
System.out.println(ex);
}
}
public void delete(int code) throws ValidationException,ProcessException
{
ValidationException validationException=new ValidationException();
if(code<=0)
{
validationException.add("Code","Required");

}
else
{
if(!(this.itemCodeWiseMap.containsKey(code)))
{
validationException.add("Code","Invalid");

}

}
if(validationException.size()>0)
{
throw validationException;
}
try
{
new ItemDAO().delete(code);
ItemInterface dsItem=this.itemCodeWiseMap.get(code);

this.itemCodeWiseMap.remove(new Integer(code));
this.itemNameWiseMap.remove(dsItem.getName().toUpperCase());
this.itemsList.remove(dsItem);
}
catch(DAOException ex)
{
System.out.println(ex);
}
}
public List<ItemInterface> getList() throws ValidationException,ProcessException
{
List<ItemInterface> list=new LinkedList<>();
ItemInterface vItem;
Iterator<ItemInterface> iterator=this.itemsList.iterator();
ItemInterface item;
while(iterator.hasNext())
{
item=iterator.next();
vItem=new Item();
vItem.setCode(item.getCode());
vItem.setName(item.getName());
vItem.setCategory(item.getCategory());
vItem.setType(item.getType());
vItem.setPrice(item.getPrice());
list.add(vItem);
}
return list;
}
public int getListSize() throws ValidationException,ProcessException
{
return this.itemsList.size();
}
public ItemInterface getByCode(int code) throws ValidationException,ProcessException
{
if(!(this.itemCodeWiseMap.containsKey(code)))
{
ValidationException validationException=new ValidationException();
validationException.add("Code","Invalid");
throw validationException;
}
ItemInterface item=this.itemCodeWiseMap.get(code);
return item;
}
public ItemInterface getByName(String name) throws ValidationException,ProcessException
{
if(!(this.itemNameWiseMap.containsKey(name)))
{
ValidationException validationException=new ValidationException();
validationException.add("name","Invalid");
throw validationException;
}
ItemInterface item=this.itemNameWiseMap.get(name);
return item;
}
}
