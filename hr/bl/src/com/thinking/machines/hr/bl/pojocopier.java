import java.lang.*;
import java.lang.reflect.*;
import java.io.*;
import java.math.*;
public class pojocopier
{

public static Object copy(Object o)
{
Object y=null;
try{
Class c=o.getClass();
Method m[]=c.getDeclaredMethods();
Object ni=c.newInstance();

for(Method method:m)
{
if(isSetter(method))
{
String name=method.getName().toString().substring(3); 

method.invoke(ni,c.getDeclaredMethod("get"+name,null).invoke(o,null));
}

}
y=ni;
}catch(Exception e)
{
e.printStackTrace(); 
}

return y;
}
public static boolean isSetter(Method method){
  if(!method.getName().startsWith("set")) return false;
  if(method.getParameterTypes().length != 1) return false;
  return true;
}
}

class Employee
{
private int code;
private String name;
private String gender;
private BigDecimal salary;
private String panNumber;
public void setCode(int code)
{
this.code=code;
}
public void setName(String name)
{
this.name=name;
}
public void setGender(String gender)
{
this.gender=gender;
}
public void setSalary(BigDecimal salary)
{
this.salary=salary;
}
public void setPanNumber(String panNumber)
{
this.panNumber=panNumber;
}
public int getCode()
{
return code;
}
public String getName()
{
return name;
}
public String getGender()
{
return gender;
}
public BigDecimal getSalary()
{
return salary;
}
public String getPanNumber()
{
return panNumber;
}
}

class aaa
{
public static void main(String arg[])
{
Employee e=new Employee();
e.setCode(5);
e.setName("Rahul singh");
e.setGender("M");
e.setSalary(new BigDecimal("55210.15"));
e.setPanNumber("fsgh4566");
Employee p=(Employee)pojocopier.copy(e);
System.out.println(p.getCode()+"  "+p.getName()+" "+p.getGender());
}
}