package com.thinking.machines.inventory.dl.interfaces;
import com.thinking.machines.inventory.dl.interfaces.*;
import com.thinking.machines.inventory.dl.exceptions.*;
import java.util.*;
public interface ItemDAOInterface
{
public void add(ItemDTOInterface itemDTO) throws DAOException;
public void update(ItemDTOInterface itemDTO) throws DAOException;
public void delete(int code) throws DAOException;
public ItemDTOInterface getByName(String name) throws DAOException;
public ItemDTOInterface getByCode(int code) throws DAOException;
public List<ItemDTOInterface> getAll() throws DAOException;
public int getCount() throws DAOException;
}
