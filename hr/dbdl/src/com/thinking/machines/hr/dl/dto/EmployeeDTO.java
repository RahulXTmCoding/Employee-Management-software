package com.thinking.machines.hr.dl.dto;
import com.thinking.machines.hr.dl.interfaces.*;
import java.math.*;
public class EmployeeDTO implements EmployeeDTOInterface
{
private int code;
private String name;
private String gender;
private BigDecimal salary;
private String panNumber;
public EmployeeDTO()
{
this.code=0;
this.salary=new BigDecimal("0.00");
this.name="";
this.gender="";
this.panNumber="";

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
if(!(other instanceof EmployeeDTOInterface)) return false;
EmployeeDTOInterface e=(EmployeeDTOInterface)other;
return this.code==e.getCode();
}
public int compareTo(EmployeeDTOInterface e)
{
return this.code-e.getCode();
}
public int hashcode()
{
return this.code;
}

}