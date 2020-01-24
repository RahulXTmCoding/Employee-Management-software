package com.thinking.machines.hr.dl.dao;
import com.thinking.machines.hr.dl.interfaces.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.dto.*;
import java.math.*;
import java.io.*;
import java.util.*;
public class EmployeeDAO implements EmployeeDAOInterface
{
private static String datafile="employee.data";
public void add(EmployeeDTOInterface employee) throws DAOException
{
int code=employee.getCode();
String name=employee.getName();
String gender=employee.getGender();
BigDecimal salary=employee.getSalary();
String panNumber=employee.getPanNumber();
try{
File f=new File(datafile);
RandomAccessFile raf=new RandomAccessFile(f,"rw");
int vcode;
String vname;
String vgender;
BigDecimal vsalary;
String vpanNumber;
int noOfRecord=0,lastCode=0;
if(raf.length()==0)
{
raf.writeBytes(String.format("%10s","0")+String.format("%10s","0")+"\n");
}
String temp="         0         0";
boolean flag=false;
if(raf.length()>0)
{
temp=raf.readLine();
noOfRecord=Integer.parseInt(temp.substring(0,10).trim());
lastCode=Integer.parseInt(temp.substring(10).trim());
while(raf.getFilePointer()<raf.length())
{
vcode=Integer.parseInt(raf.readLine());
vname=raf.readLine();
vgender=raf.readLine();
vsalary=new BigDecimal(raf.readLine());
vpanNumber=raf.readLine();
if(vpanNumber.equals(panNumber))
{
flag=true;
break;
}

}
if(flag==true)
{
raf.close();
throw new DAOException("employee with given PAN Number alREAdy exist");
}
}
noOfRecord++;
lastCode=lastCode+1;
raf.writeBytes(String.valueOf(lastCode)+"\n");
raf.writeBytes(name+"\n");
raf.writeBytes(gender+"\n");
raf.writeBytes(salary.toPlainString()+"\n");
raf.writeBytes(panNumber+"\n");
raf.seek(0);
employee.setCode(lastCode);
String t=String.format("%10s",noOfRecord)+String.format("%10s",lastCode)+"\n";

raf.writeBytes(t);
raf.close();

}catch(Exception r)
{
}


}
public void update(EmployeeDTOInterface employee) throws DAOException
{
try
{
boolean found;
int vCode;
String vName;
String vGender;
BigDecimal vSalary;
String vPANNumber;
File file=new File(datafile);
if(file.exists()==false) throw new DAOException("Invalid code : "+employee.getCode());
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
throw new DAOException("Invalid code : "+employee.getCode());
}
randomAccessFile.readLine();
found=false;
int panNumberFoundAgainstCode=0;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
vCode=Integer.parseInt(randomAccessFile.readLine());
randomAccessFile.readLine();
randomAccessFile.readLine();
randomAccessFile.readLine();
vPANNumber=randomAccessFile.readLine();

if(!found && vCode==employee.getCode())
{
found=true;
}
if(panNumberFoundAgainstCode==0&&vPANNumber.equalsIgnoreCase(employee.getPanNumber()))
{
panNumberFoundAgainstCode=vCode;
}
}
if(!found)
{
randomAccessFile.close();
throw new DAOException("Invalid code : "+employee.getCode());
}
if(panNumberFoundAgainstCode!=0&&panNumberFoundAgainstCode!=employee.getCode())
{
randomAccessFile.close();
throw new DAOException("PAN number : "+employee.getPanNumber()+" exists.");
}
File tmpFile=new File("tmp.ttx");
if(tmpFile.exists()) tmpFile.delete();
RandomAccessFile tmpRandomAccessFile=new RandomAccessFile(tmpFile,"rw");
randomAccessFile.seek(0);
tmpRandomAccessFile.writeBytes(randomAccessFile.readLine()+"\n");
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
vCode=Integer.parseInt(randomAccessFile.readLine());
vName=randomAccessFile.readLine();
vGender=randomAccessFile.readLine();
vSalary=new BigDecimal(randomAccessFile.readLine());
vPANNumber=randomAccessFile.readLine();
if(vCode!=employee.getCode())
{
tmpRandomAccessFile.writeBytes(vCode+"\n");
tmpRandomAccessFile.writeBytes(vName+"\n");
tmpRandomAccessFile.writeBytes(vGender+"\n");

tmpRandomAccessFile.writeBytes(vSalary.toPlainString()+"\n");
tmpRandomAccessFile.writeBytes(vPANNumber+"\n");
}
else
{
tmpRandomAccessFile.writeBytes(vCode+"\n");
tmpRandomAccessFile.writeBytes(employee.getName()+"\n");
tmpRandomAccessFile.writeBytes(employee.getGender()+"\n");

tmpRandomAccessFile.writeBytes(employee.getSalary().toPlainString()+"\n");
tmpRandomAccessFile.writeBytes(employee.getPanNumber()+"\n");
}
}
randomAccessFile.seek(0);
tmpRandomAccessFile.seek(0);
while(tmpRandomAccessFile.getFilePointer()<tmpRandomAccessFile.length())
{
randomAccessFile.writeBytes(tmpRandomAccessFile.readLine()+"\n");
}
randomAccessFile.setLength(tmpRandomAccessFile.length());
tmpRandomAccessFile.setLength(0);
randomAccessFile.close();
tmpRandomAccessFile.close();
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}

}
public void delete(int code) throws DAOException
{
try
{
File f=new File(datafile);
if(f.exists()==false)
{
throw new DAOException("No employee ADDED");
}
RandomAccessFile raf=new RandomAccessFile(f,"rw");
if(raf.length()==0)
{
throw new DAOException("No employee added");
}
String temp=raf.readLine();
int vcode;
String vname;
String vgender;
String vpanNumber;
BigDecimal vsalary;
boolean flag=false;
while(raf.getFilePointer()<raf.length())
{
vcode=Integer.parseInt(raf.readLine());
vname=raf.readLine();
vgender=raf.readLine();
vsalary=new BigDecimal(raf.readLine());
vpanNumber=raf.readLine();
if(vcode==code)
{
flag=true;
break;
}

}
if(flag==false)
{
throw new DAOException("employee with given code does not exist");
}
File f1=new File("temp.temp");
if(f1.exists())
{
f1.delete();
}
f1=new File("temp.temp");
RandomAccessFile raf1=new RandomAccessFile(f1,"rw");
raf.seek(0);
raf1.writeBytes(raf.readLine()+"\n");
while(raf.getFilePointer()<raf.length())
{
vcode=Integer.parseInt(raf.readLine());
vname=raf.readLine();
vgender=raf.readLine();
vsalary=new BigDecimal(raf.readLine());
vpanNumber=raf.readLine();
if(vcode!=code)
{
raf1.writeBytes(String.valueOf(vcode)+"\n");
raf1.writeBytes(vname+"\n");
raf1.writeBytes(vgender+"\n");
raf1.writeBytes(vsalary.toPlainString()+"\n");
raf1.writeBytes(vpanNumber+"\n");
}

}
int noOfRecord=Integer.parseInt(temp.substring(0,10).trim());
noOfRecord--;
raf.seek(0);
raf1.seek(0);
while(raf1.getFilePointer()<raf1.length())
{
raf.writeBytes(raf1.readLine()+"\n");
}
raf.seek(0);
raf.writeBytes(String.format("%10s",noOfRecord)+temp.substring(10)+"\n");
raf.setLength(raf1.length());
raf.close();
raf1.close();
}catch(IOException e)
{}

}
public EmployeeDTOInterface getByCode(int code) throws DAOException
{
EmployeeDTOInterface e=new EmployeeDTO();
try
{
int vcode=0;
String vname="";
String vgender="";
BigDecimal vsalary=new BigDecimal("0.00");
String vpanNumber="";
boolean flag=false;
File f=new File(datafile);
RandomAccessFile raf=new RandomAccessFile(f,"rw");
String temp="";
if(raf.length()>0)
{
temp=raf.readLine();
while(raf.getFilePointer()<raf.length())
{
vcode=Integer.parseInt(raf.readLine());
vname=raf.readLine();
vgender=raf.readLine();
vsalary=new BigDecimal(raf.readLine());
vpanNumber=raf.readLine();
if(vcode==code)
{
flag=true;
break;
}
}
if(flag==false)
{
throw new DAOException("code does not exist");
}
}

e.setCode(vcode);
e.setName(vname);
e.setGender(vgender);
e.setSalary(vsalary);
e.setPanNumber(vpanNumber);
raf.close();
}
catch(IOException f){}
return e;
}
public EmployeeDTOInterface getByPanNumber(String panNumber) throws DAOException
{
EmployeeDTOInterface e=new EmployeeDTO();
try
{
int vcode;
String vname;
String vgender;
String vpanNumber;
BigDecimal vsalary;

File f=new File(datafile);
if(f.exists()==false)
{
throw new DAOException("NO Employee Added");
}
RandomAccessFile raf=new RandomAccessFile(f,"rw");
if(raf.length()==0)
{
throw new DAOException("NO Employee Added");
}
raf.readLine();
while(raf.getFilePointer()<raf.length())
{
vcode=Integer.parseInt(raf.readLine());
vname=raf.readLine();
vgender=raf.readLine();
vsalary=new BigDecimal(raf.readLine());
vpanNumber=raf.readLine();
if(vpanNumber.equalsIgnoreCase(panNumber))
{
e.setCode(vcode);
e.setName(vname);
e.setGender(vgender);
e.setSalary(vsalary);
e.setPanNumber(vpanNumber);
break;
}
}

raf.close();
return e;
}
catch(IOException g)
{
}
return e;
}
public List<EmployeeDTOInterface> getall() throws DAOException
{
List<EmployeeDTOInterface> l=new ArrayList<EmployeeDTOInterface>();
try{

File f=new File(datafile);
if(f.exists()==false)
{
throw new DAOException("NO Employee Added");
}
RandomAccessFile raf=new RandomAccessFile(f,"rw");
EmployeeDTOInterface[] e;
if(raf.length()==0)
{
raf.close();
throw new DAOException("No record found");
}
String temp=raf.readLine();
e=new EmployeeDTO[Integer.parseInt(temp.substring(0,10).trim())];
int w=0;
while(raf.getFilePointer()<raf.length())
{
e[w]=new EmployeeDTO();
e[w].setCode(Integer.parseInt(raf.readLine()));
e[w].setName(raf.readLine());
e[w].setGender(raf.readLine());
e[w].setSalary(new BigDecimal(raf.readLine()));
e[w].setPanNumber(raf.readLine());
l.add(e[w]);
w++;
}
raf.close();

}catch(IOException e)
{} 
return l;
}

public long getcount() throws DAOException
{
throw new DAOException("no implementaion");
}
}