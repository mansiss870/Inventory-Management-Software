package com.thinking.machines.inventory.wrappers;
import java.io.*;
public class ResponseWrapper implements Serializable
{
private Boolean success;
private String Exception;
private Object result;
public void setException(String Exception)
{
this.Exception=Exception;
}
public void setResult(Object result)
{
this.result=result;
}
public String getException()
{
return this.Exception;
}
public Object getResult()
{
return this.result;
}
public void setSuccess(Boolean success)
{
this.success=success;
}
public Boolean getSuccess()
{
return this.success;
}
}