package com.thinking.machines.inventory.bl.interfaces;
import java.math.*;
import java.io.*;
import java.util.*;
public interface ItemInterface extends java.io.Serializable,Comparable<ItemInterface>
{
public void setCode(int code);
public int getCode();
public void setName(String name);
public String getName();
public void setCategory(String category);
public String getCategory();
public void setType(String type);
public String getType();
public void setPrice(BigDecimal price);
public BigDecimal getPrice();
}
