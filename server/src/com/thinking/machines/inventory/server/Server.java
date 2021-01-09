import com.thinking.machines.inventory.POJOCopier.*;
import com.thinking.machines.inventory.bl.interfaces.*;
import com.thinking.machines.inventory.bl.exceptions.*;
import com.thinking.machines.inventory.bl.pojo.*;
import com.thinking.machines.inventory.bl.*;
import com.thinking.machines.inventory.dl.interfaces.*;
import com.thinking.machines.inventory.dl.exceptions.*;
import com.thinking.machines.inventory.dl.pojo.*;
import com.thinking.machines.inventory.dl.*;
import com.thinking.machines.inventory.wrappers.*;
import java.util.*;
import java.util.LinkedList;
import java.math.*;
import java.net.*;
import java.io.*;
class Server
{
private ServerSocket serverSocket;
Server()
{
try{
serverSocket=new ServerSocket(7000);
startListening();
}catch(Exception ex)
{

}
}
public void startListening()
{
try{
while(true)
{
Socket socket;
socket=serverSocket.accept();
System.out.println("Inventory management start listening");
new RequestProcessor(socket);
}
}catch(Exception ex)
{
}
}
public static void main(String gg[])
{
Server server=new Server();
}
}
class RequestProcessor extends Thread
{
private Socket socket;
private ItemManager itemManager;
RequestProcessor(Socket socket)
{
this.itemManager=new ItemManager();
this.socket=socket;
try{
start();
}catch(Exception ex)
{
//System.out.println(ex);
}
}
public void run()
{
while(true)
{
try{
//System.out.println("run");
RequestWrapper requestWrapper;
ResponseWrapper responseWrapper=new ResponseWrapper();
byte header[]=new byte[10];
InputStream is=socket.getInputStream();
OutputStream os=socket.getOutputStream();
byte ack[]=new byte[1];
int j;
while(true) {
j=is.read(header);
if(j!=-1) break; }
int bytesToRead;
int y=0;
bytesToRead=0;
while(y<=9 && header[y]==0) y++;
while(y<=9) {
bytesToRead=(bytesToRead*10)+header[y];
y++; }
os.write(ack,0,1);
os.flush();
byte buffer[]=new byte[1024];
ByteArrayOutputStream baos=new ByteArrayOutputStream();
while(bytesToRead>0) {
j=is.read(buffer);
if(j==-1) continue;
baos.write(buffer);
baos.flush();
os.write(ack,0,1);
os.flush();
bytesToRead=bytesToRead-j;
}
byte data[]=baos.toByteArray();
ByteArrayInputStream bais=new ByteArrayInputStream(data);
ObjectInputStream ois=new ObjectInputStream(bais);
requestWrapper=(RequestWrapper)ois.readObject();
//System.out.println(requestWrapper.getAction());
if(requestWrapper.getAction().equals("add"))
{
try{
itemManager.add((ItemInterface)requestWrapper.getObject());
responseWrapper.setSuccess(true);
responseWrapper.setException("");
responseWrapper.setResult(null);
}catch(ValidationException vd)
{
responseWrapper.setSuccess(false);
responseWrapper.setException("ValidationException");
responseWrapper.setResult(vd);
}catch(ProcessException pe)
{
responseWrapper.setSuccess(false);
responseWrapper.setException("ProcessException");
responseWrapper.setResult(pe);
}
// Logic to prepare response starts here
baos=new ByteArrayOutputStream();
ObjectOutputStream oos=new ObjectOutputStream(baos);
oos.writeObject(responseWrapper);
oos.flush();
data=baos.toByteArray();
int bytesToWrite=1024;
int bc;
//logic to build header that will contain meta data
int length=data.length;
int i=9;
header=new byte[10];
while(length>0)
{
header[i]=(byte)(length%10);
length=length/10;
i--;
}
os.write(header,0,10);
os.flush();
while(true)
{
bc=is.read(ack);
if(bc!=-1) break;
}
//System.out.println("Header sent");
int fromIndex=0;
while(fromIndex<data.length)
{
if(data.length-fromIndex<bytesToWrite) bytesToWrite=data.length-fromIndex;
os.write(data,fromIndex,bytesToWrite);
os.flush();
while(true) {
bc=is.read(ack);
if(bc!=-1) break; }
fromIndex+=bytesToWrite; }
//System.out.println("Data sent");

}else
{
if(requestWrapper.getAction().equals("update"))
{
try{
itemManager.update((ItemInterface)requestWrapper.getObject());
responseWrapper.setSuccess(true);
responseWrapper.setException("");
responseWrapper.setResult(null);
}catch(ValidationException vd)
{
responseWrapper.setSuccess(false);
responseWrapper.setException("ValidationException");
responseWrapper.setResult(vd);
}catch(ProcessException pe)
{
responseWrapper.setSuccess(false);
responseWrapper.setException("ProcessException");
responseWrapper.setResult(pe);
}
// Logic to prepare response starts here
baos=new ByteArrayOutputStream();
ObjectOutputStream oos=new ObjectOutputStream(baos);
oos.writeObject(responseWrapper);
oos.flush();
data=baos.toByteArray();
int bytesToWrite=1024;
int bc;
//logic to build header that will contain meta data
int length=data.length;
int i=9;
header=new byte[10];
while(length>0)
{
header[i]=(byte)(length%10);
length=length/10;
i--;
}
os.write(header,0,10);
os.flush();
while(true)
{
bc=is.read(ack);
if(bc!=-1) break;
}
//System.out.println("Header sent");
int fromIndex=0;
while(fromIndex<data.length)
{
if(data.length-fromIndex<bytesToWrite) bytesToWrite=data.length-fromIndex;
os.write(data,fromIndex,bytesToWrite);
os.flush();
while(true) {
bc=is.read(ack);
if(bc!=-1) break; }
fromIndex+=bytesToWrite; }
//System.out.println("Data sent");


}else{
if(requestWrapper.getAction().equals("delete"))
{
try{
itemManager.delete((Integer)requestWrapper.getObject());
responseWrapper.setSuccess(true);
responseWrapper.setException("");
responseWrapper.setResult(null);
}catch(ValidationException vd)
{
responseWrapper.setSuccess(false);
responseWrapper.setException("ValidationException");
responseWrapper.setResult(vd);
}catch(ProcessException pe)
{
responseWrapper.setSuccess(false);
responseWrapper.setException("ProcessException");
responseWrapper.setResult(pe);
}
// Logic to prepare response starts here
baos=new ByteArrayOutputStream();
ObjectOutputStream oos=new ObjectOutputStream(baos);
oos.writeObject(responseWrapper);
oos.flush();
data=baos.toByteArray();
int bytesToWrite=1024;
int bc;
//logic to build header that will contain meta data
int length=data.length;
int i=9;
header=new byte[10];
while(length>0)
{
header[i]=(byte)(length%10);
length=length/10;
i--;
}
os.write(header,0,10);
os.flush();
while(true)
{
bc=is.read(ack);
if(bc!=-1) break;
}
//System.out.println("Header sent");
int fromIndex=0;
while(fromIndex<data.length)
{
if(data.length-fromIndex<bytesToWrite) bytesToWrite=data.length-fromIndex;
os.write(data,fromIndex,bytesToWrite);
os.flush();
while(true) {
bc=is.read(ack);
if(bc!=-1) break; }
fromIndex+=bytesToWrite; }
//System.out.println("Data sent");


}else{
if(requestWrapper.getAction().equals("getList"))
{

try{
responseWrapper.setResult(itemManager.getList());
responseWrapper.setSuccess(true);
responseWrapper.setException("");
}catch(ValidationException vd)
{
responseWrapper.setSuccess(false);
responseWrapper.setException("ValidationException");
responseWrapper.setResult(vd);
}catch(ProcessException pe)
{
responseWrapper.setSuccess(false);
responseWrapper.setException("ProcessException");
responseWrapper.setResult(pe);
}
// Logic to prepare response starts here
baos=new ByteArrayOutputStream();
ObjectOutputStream oos=new ObjectOutputStream(baos);
oos.writeObject(responseWrapper);
oos.flush();
data=baos.toByteArray();
int bytesToWrite=1024;
int bc;
//logic to build header that will contain meta data
int length=data.length;
int i=9;
header=new byte[10];
while(length>0)
{
header[i]=(byte)(length%10);
length=length/10;
i--;
}
os.write(header,0,10);
os.flush();
while(true)
{
bc=is.read(ack);
if(bc!=-1) break;
}
//System.out.println("Header sent");
int fromIndex=0;
while(fromIndex<data.length)
{
if(data.length-fromIndex<bytesToWrite) bytesToWrite=data.length-fromIndex;
os.write(data,fromIndex,bytesToWrite);
os.flush();
while(true) {
bc=is.read(ack);
if(bc!=-1) break; }
fromIndex+=bytesToWrite; }
//System.out.println("Data sent");


}else
{
if(requestWrapper.getAction().equals("getListSize"))
{
try{
responseWrapper.setResult(itemManager.getListSize());
responseWrapper.setSuccess(true);
responseWrapper.setException("");
}catch(ValidationException vd)
{
responseWrapper.setSuccess(false);
responseWrapper.setException("ValidationException");
responseWrapper.setResult(vd);
}catch(ProcessException pe)
{
responseWrapper.setSuccess(false);
responseWrapper.setException("ProcessException");
responseWrapper.setResult(pe);
}
// Logic to prepare response starts here
baos=new ByteArrayOutputStream();
ObjectOutputStream oos=new ObjectOutputStream(baos);
oos.writeObject(responseWrapper);
oos.flush();
data=baos.toByteArray();
int bytesToWrite=1024;
int bc;
//logic to build header that will contain meta data
int length=data.length;
int i=9;
header=new byte[10];
while(length>0)
{
header[i]=(byte)(length%10);
length=length/10;
i--;
}
os.write(header,0,10);
os.flush();
while(true)
{
bc=is.read(ack);
if(bc!=-1) break;
}
//System.out.println("Header sent");
int fromIndex=0;
while(fromIndex<data.length)
{
if(data.length-fromIndex<bytesToWrite) bytesToWrite=data.length-fromIndex;
os.write(data,fromIndex,bytesToWrite);
os.flush();
while(true) {
bc=is.read(ack);
if(bc!=-1) break; }
fromIndex+=bytesToWrite; }
//System.out.println("Data sent");


}else
{
if(requestWrapper.getAction().equals("getByCode"))
{
try{
responseWrapper.setResult(itemManager.getByCode((Integer)requestWrapper.getObject()));
responseWrapper.setSuccess(true);
responseWrapper.setException("");
}catch(ValidationException vd)
{
responseWrapper.setSuccess(false);
responseWrapper.setException("ValidationException");
responseWrapper.setResult(vd);
}catch(ProcessException pe)
{
responseWrapper.setSuccess(false);
responseWrapper.setException("ProcessException");
responseWrapper.setResult(pe);
}
// Logic to prepare response starts here
baos=new ByteArrayOutputStream();
ObjectOutputStream oos=new ObjectOutputStream(baos);
oos.writeObject(responseWrapper);
oos.flush();
data=baos.toByteArray();
int bytesToWrite=1024;
int bc;
//logic to build header that will contain meta data
int length=data.length;
int i=9;
header=new byte[10];
while(length>0)
{
header[i]=(byte)(length%10);
length=length/10;
i--;
}
os.write(header,0,10);
os.flush();
while(true)
{
bc=is.read(ack);
if(bc!=-1) break;
}
//System.out.println("Header sent");
int fromIndex=0;
while(fromIndex<data.length)
{
if(data.length-fromIndex<bytesToWrite) bytesToWrite=data.length-fromIndex;
os.write(data,fromIndex,bytesToWrite);
os.flush();
while(true) {
bc=is.read(ack);
if(bc!=-1) break; }
fromIndex+=bytesToWrite; }
//System.out.println("Data sent");


}else
{

if(requestWrapper.getAction().equals("getByName"))
{
try{
responseWrapper.setResult(itemManager.getByName((String)requestWrapper.getObject()));
responseWrapper.setSuccess(true);
responseWrapper.setException("");
}catch(ValidationException vd)
{
responseWrapper.setSuccess(false);
responseWrapper.setException("ValidationException");
responseWrapper.setResult(vd);
}catch(ProcessException pe)
{
responseWrapper.setSuccess(false);
responseWrapper.setException("ProcessException");
responseWrapper.setResult(pe);
}
// Logic to prepare response starts here
baos=new ByteArrayOutputStream();
ObjectOutputStream oos=new ObjectOutputStream(baos);
oos.writeObject(responseWrapper);
oos.flush();
data=baos.toByteArray();
int bytesToWrite=1024;
int bc;
//logic to build header that will contain meta data
int length=data.length;
int i=9;
header=new byte[10];
while(length>0)
{
header[i]=(byte)(length%10);
length=length/10;
i--;
}
os.write(header,0,10);
os.flush();
while(true)
{
bc=is.read(ack);
if(bc!=-1) break;
}
//System.out.println("Header sent");
int fromIndex=0;
while(fromIndex<data.length)
{
if(data.length-fromIndex<bytesToWrite) bytesToWrite=data.length-fromIndex;
os.write(data,fromIndex,bytesToWrite);
os.flush();
while(true) {
bc=is.read(ack);
if(bc!=-1) break; }
fromIndex+=bytesToWrite; }
//System.out.println("Data sent");


}
}
}
}
}
}
}
}catch(Exception ex)
{
//System.out.println(ex);
try{
socket.close();
}catch(Exception ee)
{
}
System.exit(0);
}
}
}
}
