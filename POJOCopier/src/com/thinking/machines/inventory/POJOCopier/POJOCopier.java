package com.thinking.machines.inventory.POJOCopier;
import java.util.*;
import java.lang.reflect.*;
public class POJOCopier 
{
public static void copy(Object target,Object source) throws Throwable
{
Class sourceClass=source.getClass();
Method methods[];
methods=sourceClass.getMethods();
ArrayList<Method> getterMethods =new ArrayList<>();
for(Method m:methods)
{
if(isGetter(m))
{
getterMethods.add(m);
}
}
Class targetClass=target.getClass();
ArrayList<Method> targetSetters=new ArrayList<>();
Method targetSetter,getterMethod;
int i=0;
while(i<getterMethods.size())
{
getterMethod=getterMethods.get(i);
targetSetter=getSetter(getterMethod,targetClass);
if(targetSetter!=null)
{
targetSetters.add(targetSetter);
++i;
}
else
{
getterMethods.remove(i);
}
}
i=0;
while(i<getterMethods.size())
{
getterMethod=getterMethods.get(i);
targetSetter=targetSetters.get(i);
Object result;
result=getterMethod.invoke(source);
try
{
if(performDeepCopy(result))
{
Class k=result.getClass();
Object o=k.newInstance();
targetSetter.invoke(target,o);
POJOCopier.copy(o,result);
}
else
{
targetSetter.invoke(target,getterMethod.invoke(source));
}
}catch(IllegalAccessException illegalAccessException)
{
throw illegalAccessException;
}
catch(InvocationTargetException invocationTargetException)
{
throw invocationTargetException.getCause();
}
++i;
}
}
private static boolean isGetter(Method m)
{
String name=m.getName();
if(!name.startsWith("get")) return false;
if(m.getParameterCount()>0) return false;
if(m.getReturnType().getName().equals("void")) return false;
char next=name.charAt(3);
if(next>=65 && next<=90) return true;
if(next>=97 && next<=122) return false;
return true;
}
private static Method getSetter(Method getterMethod, Class c)
{
String setterName="set"+getterMethod.getName().substring(3);
Class setterParameter=getterMethod.getReturnType();
try{
Method setterMethod=c.getMethod(setterName,setterParameter);
return setterMethod;
}catch(NoSuchMethodException nsme)
{
return null;
}
}
private static boolean performDeepCopy(Object k)
{
Class result=k.getClass();
if(result.equals(Boolean.class) || 
        result.equals(Integer.class) ||
        result.equals(Character.class) ||
        result.equals(Byte.class) ||
        result.equals(Short.class) ||
        result.equals(Double.class) ||
        result.equals(Long.class) ||
        result.equals(Float.class) || result.equals(String.class)) return false;
return true;
}
}