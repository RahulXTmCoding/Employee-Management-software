package com.thinking.machines.hr.bl.pojo;
import com.thinking.machines.hr.bl.interfaces.*;
import java.math.*;
public class Employee implements EmployeeInterface
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
if(!(other instanceof EmployeeInterface)) return false;
EmployeeInterface e=(EmployeeInterface)other;
return this.code==e.getCode();
}
public int compareTo(EmployeeInterface e)
{
return this.code-e.getCode();
}
public int hashcode()
{
return this.code;
}

}