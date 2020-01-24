package com.thinking.machines.hr.dl.dao;
import com.thinking.machines.hr.dl.interfaces.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.dto.*;
import java.math.*;
import java.io.*;
import java.util.*;
import java.sql.*;

public class EmployeeDAO implements EmployeeDAOInterface
{
private static String datafile="employee.data";
public void add(EmployeeDTOInterface employee) throws DAOException
{

int code=employee.getCode();
String name=employee.getName();
String gender =employee.getGender();
BigDecimal salary=employee.getSalary();
String panNumber=employee.getPanNumber();

try{
Class.forName("com.mysql.cj.jdbc.Driver");
Connection c=DriverManager.getConnection("jdbc:mysql://localhost:3306/employeedb","Rahul","Rahulsingh@8");
PreparedStatement s=c.prepareStatement("select code from employee where upper(panNumber)=?;");
s.setString(1,panNumber.toUpperCase());
ResultSet r= s.executeQuery();
if(r.next())
{

throw new DAOException("Pannumber already exists");

}
r.close();
s.close();
PreparedStatement Ps=c.prepareStatement("insert into employee(name,gender,salary,panNumber) values(?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
Ps.setString(1,name);
Ps.setString(2,gender);
Ps.setString(3,salary.toPlainString());
Ps.setString(4,panNumber.toUpperCase());
Ps.executeUpdate();
ResultSet p=Ps.getGeneratedKeys();
if(p.next())
{
employee.setCode(p.getInt(1));
}

Ps.close();
c.close();
System.out.println("Record Inserted");
}
catch(SQLException se)
{
System.out.println(se);
}
catch(ClassNotFoundException ce)
{
}

}
public void update(EmployeeDTOInterface employee) throws DAOException
{
int code=employee.getCode();
String name=employee.getName();
String gender =employee.getGender();
BigDecimal salary=employee.getSalary();
String panNumber=employee.getPanNumber();

try{
Class.forName("com.mysql.cj.jdbc.Driver");
Connection c=DriverManager.getConnection("jdbc:mysql://localhost:3306/employeedb","Rahul","Rahulsingh@8");
PreparedStatement s=c.prepareStatement("select code from employee where upper(panNumber)=?;");
s.setString(1,panNumber.toUpperCase());

ResultSet r=s.executeQuery();
s=c.prepareStatement("select code from employee where code=?;");
s.setInt(1,code);
ResultSet p=s.executeQuery();
if(!p.next())
{
throw new DAOException("Record with given code does not exist");

}
if(r.next())
{
if(r.getInt(1)!=p.getInt(1))
{
throw new DAOException("Record with given code does not exist");

}
}
p.close();
r.close();
s.close();
PreparedStatement ps=c.prepareStatement("update employee set name=?,gender=?,salary=?,panNumber=? where code=?;");
ps.setString(1,name);
ps.setString(2,gender);
ps.setString(3,salary.toPlainString());
ps.setString(4,panNumber.toUpperCase());
ps.setInt(5,code);
ps.executeUpdate();
ps.close();
c.close();
System.out.println("Record updated");
}
catch(SQLException se)
{
System.out.println(se);
}
catch(ClassNotFoundException ce)
{
}

}
public void delete(int cd) throws DAOException
{
int code=cd;

try{
Class.forName("com.mysql.cj.jdbc.Driver");
Connection c=DriverManager.getConnection("jdbc:mysql://localhost:3306/employeedb","Rahul","Rahulsingh@8");
Statement s=c.createStatement();
ResultSet p=s.executeQuery("select code from employee where code="+code+";");
if(!p.next())
{
throw new DAOException("Record with given code does not exist");

}

p.close();
s.close();
PreparedStatement ps=c.prepareStatement("delete from employee where code=?;");

ps.setInt(1,code);
ps.executeUpdate();
ps.close();
c.close();
System.out.println("Record deleted");
}
catch(SQLException se)
{
System.out.println(se);
}
catch(ClassNotFoundException ce)
{
}

}
public EmployeeDTOInterface getByCode(int cd) throws DAOException
{
EmployeeDTOInterface e=new EmployeeDTO();
int code=cd;

try{
Class.forName("com.mysql.cj.jdbc.Driver");
Connection c=DriverManager.getConnection("jdbc:mysql://localhost:3306/employeedb","Rahul","Rahulsingh@8");
Statement s=c.createStatement();
ResultSet p=s.executeQuery("select * from employee where code="+code+";");
if(!p.next())
{
throw new DAOException("Record with given code does not exist");

}
e.setCode(code);
e.setName(p.getString(2));
e.setGender(p.getString(3));
e.setSalary(new BigDecimal(p.getString(4)));
e.setPanNumber(p.getString(5));
p.close();
s.close();
c.close();
System.out.println("Record found");
}
catch(SQLException se)
{
System.out.println(se);
}
catch(ClassNotFoundException ce)
{
}

return e;
}
public EmployeeDTOInterface getByPanNumber(String panNumber) throws DAOException
{
EmployeeDTOInterface e=new EmployeeDTO();
String pan=panNumber;

try{
Class.forName("com.mysql.cj.jdbc.Driver");
Connection c=DriverManager.getConnection("jdbc:mysql://localhost:3306/employeedb","Rahul","Rahulsingh@8");
PreparedStatement s=c.prepareStatement("select * from employee where upper(panNumber)=?;");
s.setString(1,pan.toUpperCase());

ResultSet p=s.executeQuery();
if(!p.next())
{
throw new DAOException("Record with given panNumber does not exist");

}
e.setCode(p.getInt(1));
e.setName(p.getString(2));
e.setGender(p.getString(3));
e.setSalary(new BigDecimal(p.getString(4)));
e.setPanNumber(p.getString(5));
p.close();
s.close();
c.close();
System.out.println("Record found");
}
catch(SQLException se)
{
System.out.println(se);
}
catch(ClassNotFoundException ce)
{
}

return e;
}
public List<EmployeeDTOInterface> getall() throws DAOException
{
List<EmployeeDTOInterface> l=new ArrayList<EmployeeDTOInterface>();
EmployeeDTOInterface e=null;

try{
Class.forName("com.mysql.cj.jdbc.Driver");
Connection c=DriverManager.getConnection("jdbc:mysql://localhost:3306/employeedb","Rahul","Rahulsingh@8");
Statement s=c.createStatement();
ResultSet p=s.executeQuery("select * from employee;");
while(p.next())
{
e=new EmployeeDTO();
e.setCode(p.getInt(1));
e.setName(p.getString(2));
e.setGender(p.getString(3));
e.setSalary(new BigDecimal(p.getString(4)));
e.setPanNumber(p.getString(5));
l.add(e);
}

p.close();
s.close();
c.close();
System.out.println("list returned");
}
catch(SQLException se)
{
System.out.println(se);
}
catch(ClassNotFoundException ce)
{
}

return l;
}

public long getcount() throws DAOException
{
long count=0;
try{
Class.forName("com.mysql.cj.jdbc.Driver");
Connection c=DriverManager.getConnection("jdbc:mysql://localhost:3306/employeedb","Rahul","Rahulsingh@8");
Statement s=c.createStatement();
ResultSet p=s.executeQuery("select count(*) from employee;");
if(p.next())
{
count=p.getInt(1);
}
p.close();
s.close();
c.close();
System.out.println("list returned");
}
catch(SQLException se)
{
System.out.println(se);
}
catch(ClassNotFoundException ce)
{
}
return count;
}
}