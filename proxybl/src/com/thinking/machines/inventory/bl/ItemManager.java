package com.thinking.machines.inventory.bl;
import com.thinking.machines.inventory.POJOCopier.*;
import com.thinking.machines.inventory.bl.interfaces.*;
import com.thinking.machines.inventory.bl.exceptions.*;
import com.thinking.machines.inventory.bl.pojo.*;
import com.thinking.machines.inventory.wrappers.*;
import java.util.*;
import java.util.LinkedList;
import java.math.*;
import java.io.*;
import java.net.*;
public class ItemManager 
{
private Socket socket;
public ItemManager()
{
try{
socket=new Socket("localhost",7000);
}catch(Exception ex)
{
System.out.println(ex);
}
}
public void add(ItemInterface item) throws ValidationException,ProcessException
{
RequestWrapper rq=new RequestWrapper("add",item);
ResponseWrapper responseWrapper=new ResponseWrapper();
try{
ByteArrayOutputStream baos=new ByteArrayOutputStream();
ObjectOutputStream oos=new ObjectOutputStream(baos);
oos.writeObject(rq);
oos.flush();
byte data[];
int fromIndex;
int bytesToWrite;
data=baos.toByteArray();
OutputStream os=socket.getOutputStream();
InputStream is=socket.getInputStream();
bytesToWrite=1024;
byte ack[]=new byte[1];
int bc;
//logic to build header that will contain meta data
byte header[]=new byte[10];
int length=data.length;
int i=9;
while(length>0) {
header[i]=(byte)(length%10);
length=length/10;
i--; }
os.write(header,0,10);
os.flush();
while(true) {
bc=is.read(ack);
if(bc!=-1) break;
}
fromIndex=0;
while(fromIndex<data.length)
{
if(data.length-fromIndex<bytesToWrite) bytesToWrite=data.length-fromIndex;
os.write(data,fromIndex,bytesToWrite);
os.flush();
while(true)
{
bc=is.read(ack);
if(bc!=-1) break;
}
fromIndex+=bytesToWrite;
}
int j;
while(true)
{
j=is.read(header);
if(j!=-1) break;
}
int bytesToRead;
int y=0;
bytesToRead=0;
while(y<=9 && header[y]==0) y++;
while(y<=9)
{
bytesToRead=(bytesToRead*10)+header[y];
y++;
}
//System.out.println("Response bytes : "+bytesToRead);
os.write(ack,0,1);
os.flush();
byte buffer[]=new byte[1024];
baos=new ByteArrayOutputStream();
//System.out.println("Ack sent");
while(bytesToRead>0)
{
j=is.read(buffer);
if(j==-1) continue;
baos.write(buffer);
baos.flush();
os.write(ack,0,1);
os.flush();
bytesToRead=bytesToRead-j;
}
//System.out.println("Response received");
data=baos.toByteArray();
ByteArrayInputStream bais=new ByteArrayInputStream(data);
ObjectInputStream ois=new ObjectInputStream(bais);
responseWrapper=(ResponseWrapper)ois.readObject();
}catch(Exception ex)
{
//System.out.println(ex+"add");
}
if(responseWrapper.getSuccess()!=true)
{
if(responseWrapper.getException()!=null)
{
if(responseWrapper.getException().equals("ValidationException"))
{
throw (ValidationException)responseWrapper.getResult();

}
else{
throw (ProcessException)responseWrapper.getResult();
}
}
}
//socket.close();
}
public void update(ItemInterface item) throws ValidationException,ProcessException
{
RequestWrapper rq=new RequestWrapper("update",item);
ResponseWrapper responseWrapper=new ResponseWrapper();
try{
//socket=new Socket("localhost",7000);
int fromIndex;
int bytesToWrite;
ByteArrayOutputStream baos=new ByteArrayOutputStream();
ObjectOutputStream oos=new ObjectOutputStream(baos);
oos.writeObject(rq);
oos.flush();
byte data[];
data=baos.toByteArray();
OutputStream os=socket.getOutputStream();
InputStream is=socket.getInputStream();
bytesToWrite=1024;
byte ack[]=new byte[1];
int bc;
//logic to build header that will contain meta data
byte header[]=new byte[10];
int length=data.length;
int i=9;
while(length>0) {
header[i]=(byte)(length%10);
length=length/10;
i--; }
os.write(header,0,10);
os.flush();
while(true) {
bc=is.read(ack);
if(bc!=-1) break;
}
fromIndex=0;
while(fromIndex<data.length)
{
if(data.length-fromIndex<bytesToWrite) bytesToWrite=data.length-fromIndex;
os.write(data,fromIndex,bytesToWrite);
os.flush();
while(true)
{
bc=is.read(ack);
if(bc!=-1) break;
}
fromIndex+=bytesToWrite;
}
int j;
while(true)
{
j=is.read(header);
if(j!=-1) break;
}
int bytesToRead;
int y=0;
bytesToRead=0;
while(y<=9 && header[y]==0) y++;
while(y<=9)
{
bytesToRead=(bytesToRead*10)+header[y];
y++;
}
//System.out.println("Response bytes : "+bytesToRead);
os.write(ack,0,1);
os.flush();
byte buffer[]=new byte[1024];
baos=new ByteArrayOutputStream();
//System.out.println("Ack sent");
while(bytesToRead>0)
{
j=is.read(buffer);
if(j==-1) continue;
baos.write(buffer);
baos.flush();
os.write(ack,0,1);
os.flush();
bytesToRead=bytesToRead-j;
}
//System.out.println("Response received");
data=baos.toByteArray();
ByteArrayInputStream bais=new ByteArrayInputStream(data);
ObjectInputStream ois=new ObjectInputStream(bais);
responseWrapper=(ResponseWrapper)ois.readObject();
}catch(Exception ex)
{
}

if(responseWrapper.getSuccess()!=true)
{
if(responseWrapper.getException()!=null)
{
if(responseWrapper.getException().equals("ValidationException"))
{
throw (ValidationException)responseWrapper.getResult();
}
else{
throw (ProcessException)responseWrapper.getResult();
}
}
}
//socket.close();
}
public void delete(int code) throws ValidationException,ProcessException
{
RequestWrapper rq=new RequestWrapper("delete",code);
ResponseWrapper responseWrapper=new ResponseWrapper();
try{
//socket=new Socket("localhost",7000);
int fromIndex;
int bytesToWrite;
ByteArrayOutputStream baos=new ByteArrayOutputStream();
ObjectOutputStream oos=new ObjectOutputStream(baos);
oos.writeObject(rq);
oos.flush();
byte data[];
data=baos.toByteArray();
OutputStream os=socket.getOutputStream();
InputStream is=socket.getInputStream();
bytesToWrite=1024;
byte ack[]=new byte[1];
int bc;
//logic to build header that will contain meta data
byte header[]=new byte[10];
int length=data.length;
int i=9;
while(length>0) {
header[i]=(byte)(length%10);
length=length/10;
i--; }
os.write(header,0,10);
os.flush();
while(true) {
bc=is.read(ack);
if(bc!=-1) break;
}
fromIndex=0;
while(fromIndex<data.length)
{
if(data.length-fromIndex<bytesToWrite) bytesToWrite=data.length-fromIndex;
os.write(data,fromIndex,bytesToWrite);
os.flush();
while(true)
{
bc=is.read(ack);
if(bc!=-1) break;
}
fromIndex+=bytesToWrite;
}
int j;
while(true)
{
j=is.read(header);
if(j!=-1) break;
}
int bytesToRead;
int y=0;
bytesToRead=0;
while(y<=9 && header[y]==0) y++;
while(y<=9)
{
bytesToRead=(bytesToRead*10)+header[y];
y++;
}
//System.out.println("Response bytes : "+bytesToRead);
os.write(ack,0,1);
os.flush();
byte buffer[]=new byte[1024];
baos=new ByteArrayOutputStream();
//System.out.println("Ack sent");
while(bytesToRead>0)
{
j=is.read(buffer);
if(j==-1) continue;
baos.write(buffer);
baos.flush();
os.write(ack,0,1);
os.flush();
bytesToRead=bytesToRead-j;
}
//System.out.println("Response received");
data=baos.toByteArray();
ByteArrayInputStream bais=new ByteArrayInputStream(data);
ObjectInputStream ois=new ObjectInputStream(bais);
responseWrapper=(ResponseWrapper)ois.readObject();
}catch(Exception ex)
{
}

if(responseWrapper.getSuccess()!=true)
{
if(responseWrapper.getException()!=null)
{
if(responseWrapper.getException().equals("ValidationException"))
{
throw (ValidationException)responseWrapper.getResult();
}
else{
throw (ProcessException)responseWrapper.getResult();
}
}
}
//socket.close();

}
public List<ItemInterface> getList() throws ValidationException,ProcessException
{
//System.out.println("getList()");
RequestWrapper rq=new RequestWrapper("getList",null);
ResponseWrapper responseWrapper=new ResponseWrapper();
try{
//socket=new Socket("localhost",7000);
//System.out.println("getList()");
int fromIndex;
int bytesToWrite;
ByteArrayOutputStream baos=new ByteArrayOutputStream();
ObjectOutputStream oos=new ObjectOutputStream(baos);
oos.writeObject(rq);
oos.flush();
byte data[];
data=baos.toByteArray();
OutputStream os=socket.getOutputStream();
InputStream is=socket.getInputStream();
bytesToWrite=1024;
byte ack[]=new byte[1];
int bc;
//logic to build header that will contain meta data
byte header[]=new byte[10];
int length=data.length;
int i=9;
while(length>0) {
header[i]=(byte)(length%10);
length=length/10;
i--; }
os.write(header,0,10);
os.flush();
while(true) {
bc=is.read(ack);
if(bc!=-1) break;
}
fromIndex=0;
while(fromIndex<data.length)
{
if(data.length-fromIndex<bytesToWrite) bytesToWrite=data.length-fromIndex;
os.write(data,fromIndex,bytesToWrite);
os.flush();
while(true)
{
bc=is.read(ack);
if(bc!=-1) break;
}
fromIndex+=bytesToWrite;
}
int j;
while(true)
{
j=is.read(header);
if(j!=-1) break;
}
int bytesToRead;
int y=0;
bytesToRead=0;
while(y<=9 && header[y]==0) y++;
while(y<=9)
{
bytesToRead=(bytesToRead*10)+header[y];
y++;
}
//System.out.println("Response bytes : "+bytesToRead);
os.write(ack,0,1);
os.flush();
byte buffer[]=new byte[1024];
baos=new ByteArrayOutputStream();
//System.out.println("Ack sent");
while(bytesToRead>0)
{
j=is.read(buffer);
if(j==-1) continue;
baos.write(buffer);
baos.flush();
os.write(ack,0,1);
os.flush();
bytesToRead=bytesToRead-j;
}
//System.out.println("Response received");
data=baos.toByteArray();
ByteArrayInputStream bais=new ByteArrayInputStream(data);
ObjectInputStream ois=new ObjectInputStream(bais);
responseWrapper=(ResponseWrapper)ois.readObject();
}catch(Exception ex)
{
//System.out.println(ex);
}

if(responseWrapper.getSuccess()!=true)
{
if(responseWrapper.getException()!=null)
{
if(responseWrapper.getException().equals("ValidationException"))
{
throw (ValidationException)responseWrapper.getResult();
}
else{
throw (ProcessException)responseWrapper.getResult();
}
}
}
//socket.close();
return (LinkedList<ItemInterface>)responseWrapper.getResult();
}
public int getListSize() throws ValidationException,ProcessException
{
RequestWrapper rq=new RequestWrapper("getListSize",null);
ResponseWrapper responseWrapper=new ResponseWrapper();
try{
//socket=new Socket("localhost",7000);
int fromIndex;
int bytesToWrite;
ByteArrayOutputStream baos=new ByteArrayOutputStream();
ObjectOutputStream oos=new ObjectOutputStream(baos);
oos.writeObject(rq);
oos.flush();
byte data[];
data=baos.toByteArray();
OutputStream os=socket.getOutputStream();
InputStream is=socket.getInputStream();
bytesToWrite=1024;
byte ack[]=new byte[1];
int bc;
//logic to build header that will contain meta data
byte header[]=new byte[10];
int length=data.length;
int i=9;
while(length>0) {
header[i]=(byte)(length%10);
length=length/10;
i--; }
os.write(header,0,10);
os.flush();
while(true) {
bc=is.read(ack);
if(bc!=-1) break;
}
fromIndex=0;
while(fromIndex<data.length)
{
if(data.length-fromIndex<bytesToWrite) bytesToWrite=data.length-fromIndex;
os.write(data,fromIndex,bytesToWrite);
os.flush();
while(true)
{
bc=is.read(ack);
if(bc!=-1) break;
}
fromIndex+=bytesToWrite;
}
int j;
while(true)
{
j=is.read(header);
if(j!=-1) break;
}
int bytesToRead;
int y=0;
bytesToRead=0;
while(y<=9 && header[y]==0) y++;
while(y<=9)
{
bytesToRead=(bytesToRead*10)+header[y];
y++;
}
//System.out.println("Response bytes : "+bytesToRead);
os.write(ack,0,1);
os.flush();
byte buffer[]=new byte[1024];
baos=new ByteArrayOutputStream();
System.out.println("Ack sent");
while(bytesToRead>0)
{
j=is.read(buffer);
if(j==-1) continue;
baos.write(buffer);
baos.flush();
os.write(ack,0,1);
os.flush();
bytesToRead=bytesToRead-j;
}
//System.out.println("Response received");
data=baos.toByteArray();
ByteArrayInputStream bais=new ByteArrayInputStream(data);
ObjectInputStream ois=new ObjectInputStream(bais);
responseWrapper=(ResponseWrapper)ois.readObject();
}catch(Exception ex)
{
}

if(responseWrapper.getSuccess()!=true)
{
if(responseWrapper.getException()!=null)
{
if(responseWrapper.getException().equals("ValidationException"))
{
throw (ValidationException)responseWrapper.getResult();
}
else{
throw (ProcessException)responseWrapper.getResult();
}
}
}
//socket.close();
return (Integer)responseWrapper.getResult();

}
public ItemInterface getByCode(int code) throws ValidationException,ProcessException
{
RequestWrapper rq=new RequestWrapper("getByCode",code);
ResponseWrapper responseWrapper=new ResponseWrapper();
try{
//socket=new Socket("localhost",7000);
int fromIndex;
int bytesToWrite;
ByteArrayOutputStream baos=new ByteArrayOutputStream();
ObjectOutputStream oos=new ObjectOutputStream(baos);
oos.writeObject(rq);
oos.flush();
byte data[];
data=baos.toByteArray();
OutputStream os=socket.getOutputStream();
InputStream is=socket.getInputStream();
bytesToWrite=1024;
byte ack[]=new byte[1];
int bc;
//logic to build header that will contain meta data
byte header[]=new byte[10];
int length=data.length;
int i=9;
while(length>0) {
header[i]=(byte)(length%10);
length=length/10;
i--; }
os.write(header,0,10);
os.flush();
while(true) {
bc=is.read(ack);
if(bc!=-1) break;
}
fromIndex=0;
while(fromIndex<data.length)
{
if(data.length-fromIndex<bytesToWrite) bytesToWrite=data.length-fromIndex;
os.write(data,fromIndex,bytesToWrite);
os.flush();
while(true)
{
bc=is.read(ack);
if(bc!=-1) break;
}
fromIndex+=bytesToWrite;
}
int j;
while(true)
{
j=is.read(header);
if(j!=-1) break;
}
int bytesToRead;
int y=0;
bytesToRead=0;
while(y<=9 && header[y]==0) y++;
while(y<=9)
{
bytesToRead=(bytesToRead*10)+header[y];
y++;
}
//System.out.println("Response bytes : "+bytesToRead);
os.write(ack,0,1);
os.flush();
byte buffer[]=new byte[1024];
baos=new ByteArrayOutputStream();
//System.out.println("Ack sent");
while(bytesToRead>0)
{
j=is.read(buffer);
if(j==-1) continue;
baos.write(buffer);
baos.flush();
os.write(ack,0,1);
os.flush();
bytesToRead=bytesToRead-j;
}
//System.out.println("Response received");
data=baos.toByteArray();
ByteArrayInputStream bais=new ByteArrayInputStream(data);
ObjectInputStream ois=new ObjectInputStream(bais);
responseWrapper=(ResponseWrapper)ois.readObject();
}catch(Exception ex)
{
}

if(responseWrapper.getSuccess()!=true)
{
if(responseWrapper.getException()!=null)
{
if(responseWrapper.getException().equals("ValidationException"))
{
throw (ValidationException)responseWrapper.getResult();
}
else{
throw (ProcessException)responseWrapper.getResult();
}
}
}
//socket.close();
return (ItemInterface)responseWrapper.getResult();
}
public ItemInterface getByName(String name) throws ValidationException,ProcessException
{
RequestWrapper rq=new RequestWrapper("getByName",name);
ResponseWrapper responseWrapper=new ResponseWrapper();
try{
//socket=new Socket("localhost",7000);
int fromIndex;
int bytesToWrite;
ByteArrayOutputStream baos=new ByteArrayOutputStream();
ObjectOutputStream oos=new ObjectOutputStream(baos);
oos.writeObject(rq);
oos.flush();
byte data[];
data=baos.toByteArray();
OutputStream os=socket.getOutputStream();
InputStream is=socket.getInputStream();
bytesToWrite=1024;
byte ack[]=new byte[1];
int bc;
//logic to build header that will contain meta data
byte header[]=new byte[10];
int length=data.length;
int i=9;
while(length>0) {
header[i]=(byte)(length%10);
length=length/10;
i--; }
os.write(header,0,10);
os.flush();
while(true) {
bc=is.read(ack);
if(bc!=-1) break;
}
fromIndex=0;
while(fromIndex<data.length)
{
if(data.length-fromIndex<bytesToWrite) bytesToWrite=data.length-fromIndex;
os.write(data,fromIndex,bytesToWrite);
os.flush();
while(true)
{
bc=is.read(ack);
if(bc!=-1) break;
}
fromIndex+=bytesToWrite;
}
int j;
while(true)
{
j=is.read(header);
if(j!=-1) break;
}
int bytesToRead;
int y=0;
bytesToRead=0;
while(y<=9 && header[y]==0) y++;
while(y<=9)
{
bytesToRead=(bytesToRead*10)+header[y];
y++;
}
//System.out.println("Response bytes : "+bytesToRead);
os.write(ack,0,1);
os.flush();
byte buffer[]=new byte[1024];
baos=new ByteArrayOutputStream();
//System.out.println("Ack sent");
while(bytesToRead>0)
{
j=is.read(buffer);
if(j==-1) continue;
baos.write(buffer);
baos.flush();
os.write(ack,0,1);
os.flush();
bytesToRead=bytesToRead-j;
}
//System.out.println("Response received");
data=baos.toByteArray();
ByteArrayInputStream bais=new ByteArrayInputStream(data);
ObjectInputStream ois=new ObjectInputStream(bais);
responseWrapper=(ResponseWrapper)ois.readObject();
}catch(Exception ex)
{
}

if(responseWrapper.getSuccess()!=true)
{
if(responseWrapper.getException()!=null)
{
if(responseWrapper.getException().equals("ValidationException"))
{
throw (ValidationException)responseWrapper.getResult();
}
else{
throw (ProcessException)responseWrapper.getResult();
}
}
}
//socket.close();
return (ItemInterface)responseWrapper.getResult();
}
}
