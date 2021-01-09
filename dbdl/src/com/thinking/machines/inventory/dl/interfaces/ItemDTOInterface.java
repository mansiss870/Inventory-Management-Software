package com.thinking.machines.inventory.dl.interfaces;

import java.math.*;

import java.io.*;

import java.util.*;

public interface ItemDTOInterface extends java.io.Serializable,Comparable<ItemDTOInterface>

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
