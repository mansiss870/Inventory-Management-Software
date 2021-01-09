package com.thinking.machines.inventory.wrappers;
import java.io.*;
public class RequestWrapper implements Serializable
{
private String action;
private Object object;
public RequestWrapper(String action,Object object)
{
this.action=action;
this.object=object;
}
public void setAction(String action)
{
this.action=action;
}
public void setObject(Object object)
{
this.object=object;
}
public String getAction()
{
return this.action;
}
public Object getObject()
{
return this.object;
}
}