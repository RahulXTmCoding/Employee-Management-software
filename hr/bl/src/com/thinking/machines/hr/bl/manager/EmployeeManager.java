package com.thinking.machines.hr.bl.manager;
import com.thinking.machines.hr.bl.exceptions.*;
import com.thinking.machines.hr.bl.interfaces.*;
import com.thinking.machines.hr.bl.pojo.*;
import java.util.*;
import java.util.stream.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.tmutils.*;
public class EmployeeManager implements EmployeeManagerInterface
{
private Map<Integer,EmployeeInterface> codeWiseEmployeesMap=new HashMap<>();
private Map<String,EmployeeInterface> panNumberWiseEmployeesMap=new HashMap<>();
private List<EmployeeInterface> codeWiseEmployeesList=new LinkedList<>();
private List<EmployeeInterface> nameWiseEmployeesList=new LinkedList<>();
EmployeeDAOInterface employeeDAO=new EmployeeDAO();
public EmployeeManager()
{
populatedDataStructure();
}
private void populatedDataStructure()
{
try
{
List<EmployeeDTOInterface> list=employeeDAO.getall();
list.forEach(e->{
EmployeeInterface employee=new Employee();
employee.setCode(e.getCode());
employee.setName(e.getName());
employee.setGender(e.getGender());
employee.setSalary(e.getSalary());
employee.setPanNumber(e.getPanNumber());
codeWiseEmployeesMap.put(e.getCode(),employee);
panNumberWiseEmployeesMap.put(e.getPanNumber().toUpperCase(),employee);
codeWiseEmployeesList.add(employee);
nameWiseEmployeesList.add(employee);
});
codeWiseEmployeesList=codeWiseEmployeesList.stream().sorted().collect(Collectors.toList());
nameWiseEmployeesList=nameWiseEmployeesList.stream().sorted(Comparator.comparing(EmployeeInterface::getName,String.CASE_INSENSITIVE_ORDER).thenComparing(EmployeeInterface::getCode)).collect(Collectors.toList());

}catch(DAOException daoException)
{
return;
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
if(e.getPanNumber()!=null&&panNumberWiseEmployeesMap.containsKey(e.getPanNumber()))
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
e.setCode(ee.getCode());
EmployeeInterface ei=new Employee();
ei.setCode(e.getCode());
ei.setName(e.getName());
ei.setGender(e.getGender());
ei.setSalary(e.getSalary());
ei.setPanNumber(e.getPanNumber());
codeWiseEmployeesMap.put(e.getCode(),ei);
panNumberWiseEmployeesMap.put(e.getPanNumber(),ei);
codeWiseEmployeesList.add(ei);
int a=0;
int f=nameWiseEmployeesList.size()-1;
boolean flag=false;
int index=0;
int mid=0;
if(ei.getName().toUpperCase().compareTo(nameWiseEmployeesList.get(0).getName().toUpperCase())<=0&&flag==false)
{
nameWiseEmployeesList.add(0,ei);
flag=true;
}


if(ei.getName().toUpperCase().compareTo(nameWiseEmployeesList.get(f).getName().toUpperCase())>=0&&flag==false)
{
nameWiseEmployeesList.add(f,ei);
flag=true;
}
EmployeeInterface p;
while(true && (flag==false))
{
mid=(a+f+1)/2;
p=nameWiseEmployeesList.get(mid);
if(p.getName().toUpperCase().compareTo(ei.getName().toUpperCase())==0)
{
if(nameWiseEmployeesList.get(mid+1).getName().toUpperCase().equals(ei.getName().toUpperCase())==false)
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
if(nameWiseEmployeesList.get(mid-1).getName().toUpperCase().compareTo(ei.getName().toUpperCase())<=0)
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
nameWiseEmployeesList.add(index,ei);




}catch(DAOException d)
{
throw new processException("Cannot ADD Employee");
}

}
public void delete(int code) throws ValidationException,processException
{
ValidationException validationException=new ValidationException();
if(code==0)
{
validationException.addExceptions("code","Should not be zero");
}
if(code!=0 && codeWiseEmployeesMap.containsKey(code)==false)
{
validationException.addExceptions("code","Invalid");
}
if(validationException.hasException()) throw validationException;
try
{
new EmployeeDAO().delete(code);
EmployeeInterface employee=codeWiseEmployeesMap.get(code);
codeWiseEmployeesMap.remove(code);
panNumberWiseEmployeesMap.remove(employee.getPanNumber().toUpperCase());

int index=Collections.binarySearch(codeWiseEmployeesList,employee,Comparator.comparing(EmployeeInterface::getCode));
if(index >-1) codeWiseEmployeesList.remove(index);

// serious bug as discussed in classroom session
// remove it

index=Collections.binarySearch(nameWiseEmployeesList,employee,Comparator.comparing(EmployeeInterface::getName,String.CASE_INSENSITIVE_ORDER).thenComparing(EmployeeInterface::getCode));
if(index > -1) nameWiseEmployeesList.remove(index);
}catch(DAOException daoException)
{
throw new processException(daoException.getMessage());
}
}

public void update(EmployeeInterface employee) throws ValidationException,processException
{
ValidationException validationException=new ValidationException();
if(employee==null)
{
validationException.addExceptions("Employee","Required");
throw validationException;
}
if(employee.getCode()==0)
{
validationException.addExceptions("code","Should not be zero");
}
if(employee.getCode()!=0 && codeWiseEmployeesMap.containsKey(employee.getCode())==false)
{
validationException.addExceptions("code","Invalid");
//throw validationException;
}
if(employee.getName()==null || employee.getName().trim().length()==0)
{
validationException.addExceptions("name","Required");
}
if(employee.getName()!=null && employee.getName().trim().length()>30)
{
validationException.addExceptions("name","Should not exceed 30 characters");
}
if(employee.getGender()==null || employee.getGender().trim().length()==0)
{
validationException.addExceptions("gender","Required");
}
if(!employee.isMale() && !employee.isFemale())
{
validationException.addExceptions("gender","Invalid");
}
if(employee.getSalary()==null)
{
validationException.addExceptions("salary","Required");
}
else
{
try
{
if(employee.getSalary().signum()==-1)
{
validationException.addExceptions("salary","Invalid");
}
}catch(NumberFormatException numberFormatException)
{
validationException.addExceptions("salary","Invalid");
}
}//else end
if(employee.getPanNumber()==null || employee.getPanNumber().trim().length()==0)
{
validationException.addExceptions("panNumber","Required");
}
if(employee.getPanNumber()!=null && employee.getPanNumber().length()>15)
{
validationException.addExceptions("panNumber","Cannot exceed 15 characters");
}
if(employee.getPanNumber()!=null && panNumberWiseEmployeesMap.containsKey(employee.getPanNumber().toUpperCase()))
{
validationException.addExceptions("panNumber","Exists");
}
if(validationException.hasException())throw validationException;
try
{
EmployeeDTOInterface dlEmployee=new EmployeeDTO();
EmployeeInterface tmpEmployee=codeWiseEmployeesMap.get(employee.getCode());
PojoUtility.copy(dlEmployee,employee);
new EmployeeDAO().update(dlEmployee);
EmployeeInterface dsEmployee=new Employee();
PojoUtility.copy(dsEmployee,employee);
codeWiseEmployeesMap.replace(dsEmployee.getCode(),dsEmployee);
panNumberWiseEmployeesMap.replace(dsEmployee.getPanNumber().toUpperCase(),dsEmployee);
int index=Collections.binarySearch(codeWiseEmployeesList,tmpEmployee,Comparator.comparing(EmployeeInterface::getCode));
codeWiseEmployeesList.set(index,dsEmployee);

index=Collections.binarySearch(nameWiseEmployeesList,tmpEmployee,Comparator.comparing(EmployeeInterface::getName,String.CASE_INSENSITIVE_ORDER).thenComparing(EmployeeInterface::getCode));
nameWiseEmployeesList.set(index,dsEmployee);
Collections.sort(nameWiseEmployeesList,Comparator.comparing(EmployeeInterface::getName,String.CASE_INSENSITIVE_ORDER).thenComparing(EmployeeInterface::getCode));

}catch(DAOException daoException)
{
throw new processException(daoException.getMessage());
}
}

public void deleteAll() throws processException
{
EmployeeDAOInterface employeeDAO=new EmployeeDAO();
List<EmployeeInterface> cannotDeleteList=new LinkedList<>();
codeWiseEmployeesList.forEach((e)->{
// code to delete starts here
try
{ 
int code=e.getCode();
employeeDAO.delete(code);
codeWiseEmployeesMap.remove(code);
panNumberWiseEmployeesMap.remove(e.getPanNumber().toUpperCase());
int index=Collections.binarySearch(nameWiseEmployeesList,e,Comparator.comparing(EmployeeInterface::getName,String.CASE_INSENSITIVE_ORDER).thenComparing(EmployeeInterface::getCode));
if(index >=0) nameWiseEmployeesList.remove(index);

}catch(DAOException daoException)
{ 
cannotDeleteList.add(e);
// do nothing
}
// code to delete ends here
});
codeWiseEmployeesList.clear();
codeWiseEmployeesList=cannotDeleteList;
}
public int getCount()
{
return codeWiseEmployeesList.size();
}
public boolean hasEmployee()
{
return codeWiseEmployeesList.size()>0;
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
if(codeWiseEmployeesMap.containsKey(code)==false)
{
ve.addExceptions("code","Employee with given code does not exist");
}
if(ve.hasException())
{
throw ve;
}
return codeWiseEmployeesMap.get(code);
}
public List<EmployeeInterface> getOrderedBy(OrderedBy orderedBy) 
{
List<EmployeeInterface> list=new LinkedList<>();
if(orderedBy==OrderedBy.CODE)
{
codeWiseEmployeesList.forEach((e)->{
EmployeeInterface ei= new Employee();
PojoUtility.copy(ei,e);
list.add(ei);
});
return list;
}
if(orderedBy==OrderedBy.NAME)
{
nameWiseEmployeesList.forEach((e)->{
EmployeeInterface ei= new Employee();
PojoUtility.copy(ei,e);
list.add(ei);
});
return list;
}
if(orderedBy==OrderedBy.PAN_NUMBER)
{
List<EmployeeInterface> panNumberWiseEmployeesList;
panNumberWiseEmployeesList=codeWiseEmployeesList.stream().sorted(Comparator.comparing(EmployeeInterface::getPanNumber)).collect(Collectors.toList());
panNumberWiseEmployeesList.forEach((e)->{
EmployeeInterface ei=new Employee();
PojoUtility.copy(ei,e);
list.add(ei);
});
return list;
}
if(orderedBy==OrderedBy.GENDER)
{
List<EmployeeInterface> genderWiseEmployeesList;
genderWiseEmployeesList=codeWiseEmployeesList.stream().sorted(Comparator.comparing(EmployeeInterface::getGender)).collect(Collectors.toList());
genderWiseEmployeesList.forEach((e)->{
EmployeeInterface ei=new Employee();
PojoUtility.copy(ei,e);
list.add(ei);
});
return list;
}
if(orderedBy==OrderedBy.SALARY)
{
List<EmployeeInterface> salaryWiseEmployeesList;
salaryWiseEmployeesList=codeWiseEmployeesList.stream().sorted(Comparator.comparing(EmployeeInterface::getSalary)).collect(Collectors.toList());
salaryWiseEmployeesList.forEach((e)->{
EmployeeInterface ei=new Employee();
PojoUtility.copy(ei,e);
list.add(ei);
});
return list;
}
return list;
}
public EmployeeInterface getByPanNumber(String PanNumber) throws ValidationException
{
ValidationException ve=new ValidationException();
if(PanNumber==null&&PanNumber.length()<=0)
{
ve.addExceptions("pannumber","Needed");
}
if(PanNumber!=null&&panNumberWiseEmployeesMap.containsKey(PanNumber)==false)
{
ve.addExceptions("pannumber","Employee with given panNumber does not exist");
}
if(ve.hasException())
{
throw ve;
}
return panNumberWiseEmployeesMap.get(PanNumber);
}
}