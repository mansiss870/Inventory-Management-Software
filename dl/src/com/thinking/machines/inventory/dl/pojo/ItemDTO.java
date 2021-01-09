package com.thinking.machines.inventory.dl.pojo;
import com.thinking.machines.inventory.dl.interfaces.*;
import java.util.*;
import java.math.*;
public class ItemDTO implements ItemDTOInterface
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
if(!(object instanceof ItemDTOInterface))
{
return false;

}
ItemDTOInterface otherItemDTO;
otherItemDTO=(ItemDTOInterface)object;
return this.code==otherItemDTO.getCode();

}
public int compareTo(ItemDTOInterface other)
{
return this.name.toUpperCase().compareTo(other.getName().toUpperCase());

}

}
