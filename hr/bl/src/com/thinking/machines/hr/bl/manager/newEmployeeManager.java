package com.thinking.machines.hr.bl.manager;
import com.thinking.machines.hr.bl.exceptions.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.interfaces.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.dao.*;
import java.util.*;
import java.util.stream.Collectors; 
import java.util.stream.Stream; 
import com.thinking.machines.tmutils.*;
public class newEmployeeManager implements EmployeeManagerInterface
{
Map<Integer,EmployeeInterface> codewiseemployeemap=new HashMap<Integer,EmployeeInterface>();
Map<String,EmployeeInterface> pannumberwiseemployeemap=new HashMap<String,EmployeeInterface>();
List<EmployeeInterface> codewiseemployeelist=new LinkedList<EmployeeInterface>();
List<EmployeeInterface> namewiseemployeelist=new LinkedList<EmployeeInterface>();

public newEmployeeManager()
{
populateds();
}
public void populateds()
{
try{
EmployeeDAOInterface e=new EmployeeDAO();
List<EmployeeDTOInterface> list=e.getall();
list.forEach((p)->{

EmployeeInterface employee=new Employee();
employee.setCode(p.getCode());
employee.setName(p.getName());
employee.setGender(p.getGender());
employee.setSalary(p.getSalary());
employee.setPanNumber(p.getPanNumber());

codewiseemployeemap.put(employee.getCode(),employee);
pannumberwiseemployeemap.put(employee.getPanNumber(),employee);
codewiseemployeelist.add(employee);
namewiseemployeelist.add(employee);
});
namewiseemployeelist.stream().sorted((p,v)->{
if(p.getName().toUpperCase().compareTo(v.getName().toUpperCase())==0)
{
return p.getCode()-v.getCode();
}
return p.getName().toUpperCase().compareTo(v.getName().toUpperCase());

}).collect(Collectors.toList());
}catch(DAOException e)
{
}
}

public void add(EmployeeInterface e) throws ValidationException,processException
{
try{
ValidationException ve=new ValidationException();
if(e==null)
{
ve.addExceptions("Employee","Needed");
return;
}
if(e.getCode()!=0)
{
ve.addExceptions("code","code should be zero");
}
if(e.getName()==null&&e.getName().length()==0)
{
ve.addExceptions("name","Needed");
}
if(e.getName()!=null&&e.getName().length()>=22)
{
ve.addExceptions("name","Name can be only 22 character long");
}
if(e.getGender()==null&&e.getGender().length()==0)
{
ve.addExceptions("gender","Needed");
}
if(e.getGender()!=null&&!(e.getGender().equals("M")||e.getGender().equals("Male")||e.getGender().equals("F")||e.getGender().equals("Female")))
{
ve.addExceptions("gender","Gender can only be male or female");
}
try
{
if(e.getSalary()==null&&e.getSalary().toPlainString().length()==0)
{
ve.addExceptions("salary","needed");
}
if(e.getSalary()!=null&&e.getSalary().signum()<=0)
{
ve.addExceptions("salary","salary cannot be negative");
}
}catch(NumberFormatException ne)
{
ve.addExceptions("salary","Only numerical values allowed");
}
if(e.getPanNumber()==null&&e.getPanNumber().length()<=0)
{
ve.addExceptions("pannumber","Needed");
}
if(e.getPanNumber()!=null&&pannumberwiseemployeemap.containsKey(e.getPanNumber()))
{
ve.addExceptions("pannumber","panNumber already exist");
}
if(ve.hasException())
{
throw ve;
}
EmployeeDTOInterface ee=new EmployeeDTO();
ee.setCode(e.getCode());
ee.setName(e.getName());
ee.setGender(e.getGender());
ee.setSalary(e.getSalary());
ee.setPanNumber(e.getPanNumber());
EmployeeDAOInterface edao=new EmployeeDAO();
edao.add(ee);
EmployeeInterface ei=new Employee();
ei.setCode(e.getCode());
ei.setName(e.getName());
ei.setGender(e.getGender());
ei.setSalary(e.getSalary());
ei.setPanNumber(e.getPanNumber());
codewiseemployeemap.put(e.getCode(),ei);
pannumberwiseemployeemap.put(e.getPanNumber(),ei);
codewiseemployeelist.add(ei);
int a=0;
int f=namewiseemployeelist.size()-1;
boolean flag=false;
int index=0;
int mid=0;
if(ei.getName().toUpperCase().compareTo(namewiseemployeelist.get(0).getName().toUpperCase())<=0&&flag==false)
{
namewiseemployeelist.add(0,ei);
flag=true;
}

if(ei.getName().toUpperCase().compareTo(namewiseemployeelist.get(f).getName().toUpperCase())>=0&&flag==false)
{
namewiseemployeelist.add(f,ei);
flag=true;
}
EmployeeInterface p;
while(true && (flag==false))
{
mid=(a+f+1)/2;
p=namewiseemployeelist.get(mid);
if(p.getName().toUpperCase().compareTo(ei.getName().toUpperCase())==0)
{
if(namewiseemployeelist.get(mid+1).getName().toUpperCase().equals(ei.getName().toUpperCase())==false)
{
index=mid+1;
flag=true;
break;
}
else
{
a=mid;
continue;
}
}
if(p.getName().toUpperCase().compareTo(ei.getName().toUpperCase())>0)
{
if(namewiseemployeelist.get(mid-1).getName().toUpperCase().compareTo(ei.getName().toUpperCase())<=0)
{
index=mid;
flag=true;
break;
}
else
{
f=mid;
}
}
if(p.getName().toUpperCase().compareTo(ei.getName().toUpperCase())<0)
{
a=mid;
}
}
namewiseemployeelist.add(index,ei);




}catch(DAOException d)
{
throw new processException("Cannot ADD Employee");
}
}
public boolean hasEmployee()
{
return codewiseemployeemap.size()>0;
}
public int getCount()
{
return codewiseemployeemap.size();
}
public void delete(int code) throws ValidationException,processException
{
try{
ValidationException ve=new ValidationException();
if(code==0)
{
ve.addExceptions("code","Code cannot be zero");
}
if(code<0)
{
ve.addExceptions("code","code cannot be negative");
}
if(codewiseemployeemap.containsKey(code)==false)
{
ve.addExceptions("code","Employee with given code does not exist");
}
if(ve.hasException())
{
throw ve;
}
EmployeeInterface ei=codewiseemployeemap.get(code);
EmployeeDAOInterface dao=new EmployeeDAO();
dao.delete(code);
codewiseemployeemap.remove(code);
pannumberwiseemployeemap.remove(ei.getPanNumber());
int index=Collections.binarySearch(codewiseemployeelist,ei,Comparator.comparing((p)->p.getCode()));
codewiseemployeelist.remove(index);
int a=0;
int f=namewiseemployeelist.size()-1;
boolean flag=false;
index=0;
int mid=0;
if(ei.getName().toUpperCase().compareTo(namewiseemployeelist.get(0).getName().toUpperCase())==0&&flag==false&&ei.getCode()==namewiseemployeelist.get(0).getCode())
{
namewiseemployeelist.remove(0);
flag=true;
}

if(ei.getName().toUpperCase().compareTo(namewiseemployeelist.get(f).getName().toUpperCase())==0&&flag==false&&ei.getCode()==namewiseemployeelist.get(f).getCode())
{
namewiseemployeelist.remove(f);
flag=true;
}
EmployeeInterface p;
while(true && (flag==false))
{
mid=(a+f+1)/2;
p=namewiseemployeelist.get(mid);
if(p.getName().toUpperCase().compareTo(ei.getName().toUpperCase())==0)
{
if(p.getCode()==ei.getCode())
{
index=mid;
flag=true;
break;
}
else
{
if(p.getCode()<ei.getCode())
{
a=mid;
continue;
}
else
{
f=mid;
continue;
}
}
}
if(p.getName().toUpperCase().compareTo(ei.getName().toUpperCase())>0)
{

f=mid;

}
if(p.getName().toUpperCase().compareTo(ei.getName().toUpperCase())<0)
{
a=mid;
}
}
namewiseemployeelist.remove(index);
}
catch(DAOException e)
{
throw new processException(e.toString());
}
}
public void update(EmployeeInterface e)throws ValidationException,processException
{
try{
ValidationException ve=new ValidationException();
if(e==null)
{
ve.addExceptions("Employee","Needed");
return;
}
if(e.getCode()==0)
{
ve.addExceptions("code","code cannot be zero");
}
if(e.getCode()<0)
{
ve.addExceptions("code","code should not be negative");
}
if(codewiseemployeemap.containsKey(e.getCode())==false)
{
ve.addExceptions("Code","Employee with given code does not exists");
}
if(e.getName()==null&&e.getName().length()==0)
{
ve.addExceptions("name","Needed");
}
if(e.getName()!=null&&e.getName().length()>=22)
{
ve.addExceptions("name","Name can be only 22 character long");
}
if(e.getGender()==null&&e.getGender().length()==0)
{
ve.addExceptions("gender","Needed");
}
if(e.getGender()!=null&&!(e.getGender().equals("M")||e.getGender().equals("Male")||e.getGender().equals("F")||e.getGender().equals("Female")))
{
ve.addExceptions("gender","Gender can only be male or female");
}
try
{
if(e.getSalary()==null&&e.getSalary().toPlainString().length()==0)
{
ve.addExceptions("salary","needed");
}
if(e.getSalary()!=null&&e.getSalary().signum()<=0)
{
ve.addExceptions("salary","salary cannot be negative");
}
}catch(NumberFormatException ne)
{
ve.addExceptions("salary","Only numerical values allowed");
}
if(e.getPanNumber()==null&&e.getPanNumber().length()<=0)
{
ve.addExceptions("pannumber","Needed");
}
if(e.getPanNumber()!=null&&pannumberwiseemployeemap.containsKey(e.getPanNumber())&&codewiseemployeemap.get(e.getCode()).getPanNumber().toUpperCase().equals(e.getPanNumber().toUpperCase())==false)
{
ve.addExceptions("pannumber","panNumber already exist");
}
if(ve.hasException())
{
throw ve;
}
EmployeeInterface ei=codewiseemployeemap.get(e.getCode());
EmployeeDTOInterface dto =new EmployeeDTO();
dto.setCode(e.getCode());
dto.setName(e.getName());
dto.setGender(e.getGender());
dto.setSalary(e.getSalary());
dto.setPanNumber(e.getPanNumber());
EmployeeDAOInterface dao=new EmployeeDAO();
dao.update(dto);
codewiseemployeemap.put(e.getCode(),e);
pannumberwiseemployeemap.remove(ei.getPanNumber());
pannumberwiseemployeemap.put(e.getPanNumber(),e);
int index=Collections.binarySearch(codewiseemployeelist,e,Comparator.comparing((p)->p.getCode()));
codewiseemployeelist.set(index,e);
int a=0;
int f=namewiseemployeelist.size()-1;
boolean flag=false;
 index=0;
int mid=0;
if(ei.getName().toUpperCase().compareTo(namewiseemployeelist.get(0).getName().toUpperCase())==0&&flag==false&&ei.getCode()==namewiseemployeelist.get(0).getCode())
{
namewiseemployeelist.remove(0);
flag=true;
}

if(ei.getName().toUpperCase().compareTo(namewiseemployeelist.get(f).getName().toUpperCase())==0&&flag==false&&ei.getCode()==namewiseemployeelist.get(f).getCode())
{
namewiseemployeelist.remove(f);
flag=true;
}
EmployeeInterface p;
while(true && (flag==false))
{
mid=(a+f+1)/2;
p=namewiseemployeelist.get(mid);
if(p.getName().toUpperCase().compareTo(ei.getName().toUpperCase())==0)
{
if(p.getCode()==ei.getCode())
{
index=mid;
flag=true;
break;
}
else
{
if(p.getCode()<ei.getCode())
{
a=mid;
continue;
}
else
{
f=mid;
continue;
}
}
}
if(p.getName().toUpperCase().compareTo(ei.getName().toUpperCase())>0)
{

f=mid;

}
if(p.getName().toUpperCase().compareTo(ei.getName().toUpperCase())<0)
{
a=mid;
}
}
namewiseemployeelist.remove(index);


a=0; f=namewiseemployeelist.size()-1; flag=false; index=0;
mid=0;
if(e.getName().toUpperCase().compareTo(namewiseemployeelist.get(0).getName().toUpperCase())<=0&&flag==false)
{
namewiseemployeelist.add(0,e);
flag=true;
}

if(e.getName().toUpperCase().compareTo(namewiseemployeelist.get(f).getName().toUpperCase())>=0&&flag==false)
{
namewiseemployeelist.add(f,e);
flag=true;
}
while(true && (flag==false))
{
mid=(a+f+1)/2;
p=namewiseemployeelist.get(mid);
if(p.getName().toUpperCase().compareTo(e.getName().toUpperCase())==0)
{
if(namewiseemployeelist.get(mid+1).getName().toUpperCase().equals(e.getName().toUpperCase())==false)
{
index=mid+1;
flag=true;
break;
}
else
{
a=mid;
continue;
}
}
if(p.getName().toUpperCase().compareTo(e.getName().toUpperCase())>0)
{
if(namewiseemployeelist.get(mid-1).getName().toUpperCase().compareTo(e.getName().toUpperCase())<=0)
{
index=mid;
flag=true;
break;
}
else
{
f=mid;
}
}
if(p.getName().toUpperCase().compareTo(e.getName().toUpperCase())<0)
{
a=mid;
}
}
namewiseemployeelist.add(index,e);



}catch(DAOException daoe)
{
throw new processException(daoe.toString());
}
}
public EmployeeInterface getByCode(int code)throws ValidationException
{
ValidationException ve=new ValidationException();
if(code==0)
{
ve.addExceptions("code","Code cannot be zero");
}
if(code<0)
{
ve.addExceptions("code","code cannot be negative");
}
if(codewiseemployeemap.containsKey(code)==false)
{
ve.addExceptions("code","Employee with given code does not exist");
}
if(ve.hasException())
{
throw ve;
}
return codewiseemployeemap.get(code);
}
public EmployeeInterface getByPanNumber(String PanNumber) throws ValidationException
{
ValidationException ve=new ValidationException();
if(PanNumber==null&&PanNumber.length()<=0)
{
ve.addExceptions("pannumber","Needed");
}
if(PanNumber!=null&&pannumberwiseemployeemap.containsKey(PanNumber)==false)
{
ve.addExceptions("pannumber","Employee with given panNumber does not exist");
}
if(ve.hasException())
{
throw ve;
}
return pannumberwiseemployeemap.get(PanNumber);
}
public List<EmployeeInterface> getOrderedBy(OrderedBy order)
{
List<EmployeeInterface> copy=new LinkedList<>();
if(order==OrderedBy.CODE)
{ 
codewiseemployeelist.forEach((p)->{
EmployeeInterface e=new Employee();
PojoUtility.copy(e,p);
copy.add(e);
});

return copy;
}
namewiseemployeelist.forEach((p)->{
EmployeeInterface e=new Employee();
PojoUtility.copy(e,p);
copy.add(e);
});

return copy;
}
public void deleteAll()throws processException
{
EmployeeDAOInterface dao=new EmployeeDAO();
codewiseemployeelist.forEach((p)->{
try{
dao.delete(p.getCode());
}catch(DAOException d)
{

}
});
codewiseemployeemap.clear();
pannumberwiseemployeemap.clear();
codewiseemployeelist.clear();
namewiseemployeelist.clear();

}
}

