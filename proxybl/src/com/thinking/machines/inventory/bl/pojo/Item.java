package com.thinking.machines.inventory.bl.pojo;
import com.thinking.machines.inventory.bl.interfaces.*;
import java.util.*;
import java.math.*;
public class Item implements ItemInterface
{
private int code;
private String name;
private String category;
private String type;
private BigDecimal price;
public void setCode(int code)
{
this.code=code;
}
public int getCode()
{
return this.code;
}
public void setName(String name)
{
this.name=name;
}
public String getName()
{
return this.name;
}
public void setCategory(String category)
{
this.category=category;
}
public String getCategory()
{
return this.category;
}
public void setType(String type)
{
this.type=type;
}
public String getType()
{
return this.type;
}
public void setPrice(BigDecimal price)
{
this.price=price;
}
public BigDecimal getPrice()
{
return this.price;
}
public boolean equals(Object object)
{
if(!(object instanceof ItemInterface))
{
return false;
}
ItemInterface otherItem;
otherItem=(ItemInterface)object;
return this.code==otherItem.getCode();
}
public int compareTo(ItemInterface other)
{
return this.name.toUpperCase().compareTo(other.getName().toUpperCase());
}
}
