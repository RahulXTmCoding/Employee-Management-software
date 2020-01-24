import com.thinking.machines.tmutils.*;

import java.math.*;
class Employee
{
private int code;
private String name;
private String gender;
private BigDecimal salary;
private String panNumber;
public Employee()
{
this.code=0;
this.salary=new BigDecimal("0.00");
this.name="";
this.gender="";
this.panNumber="";

}
public Employee(int code,String name,String gender,BigDecimal salary,String panNumber)
{
this.code=code;
this.name=name;
this.gender=gender;
this.salary=salary;
this.panNumber=panNumber;
}
public boolean isMale()
{
return gender.equals("Male")||gender.equals("male")||gender.equals("m");
} 
public boolean isFemale()
{
return gender.equals("Female")||gender.equals("female")||gender.equals("f");
} 
public void setCode(int code)
{
this.code=code;
}
public int getCode()
{
return this.code;
}
public void setName(String name)
{
this.name=name;
}
public String getName()
{
return this.name;
}
public void setGender(String gender)
{
this.gender=gender;
}
public String getGender()
{
return this.gender;
}
public void setSalary(BigDecimal salary)
{
this.salary=salary;
}
public BigDecimal getSalary()
{
return this.salary;
}
public void setPanNumber(String panNumber)
{
this.panNumber=panNumber;
}
public String getPanNumber()
{
return this.panNumber;
}
public boolean equals(Object other)
{
if(!(other instanceof Employee)) return false;
Employee e=(Employee)other;
return this.code==e.getCode();
}
public int compareTo(Employee e)
{
return this.code-e.getCode();
}
public int hashcode()
{
return this.code;
}

}
class psp
{
public static void main(String args[])
{
Employee e=new Employee();
Employee p=new Employee();
e.setCode(1);
e.setName("Name");
e.setGender("M");
e.setSalary(new BigDecimal("5555"));
e.setPanNumber("thgfdh45211");

PojoUtility.copy(p,e);
System.out.println(p.getCode()+" "+p.getName()+" "+p.getSalary());

}
}