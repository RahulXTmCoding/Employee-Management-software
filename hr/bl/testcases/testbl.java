import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.manager.*;
import com.thinking.machines.hr.bl.interfaces.*;
import com.thinking.machines.hr.bl.exceptions.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.interfaces.*;
import com.thinking.machines.hr.dl.exceptions.*;
import java.util.*;
import java.math.*;
class go
{
public static void main(String args[])
{
EmployeeInterface e;
EmployeeManagerInterface em=new EmployeeManager();
try{
e=new Employee();

e.setName("gopal singh");
e.setGender("M");
e.setSalary(new BigDecimal("4545"));
e.setPanNumber("4512632ggh");
em.add(e);
}catch(ValidationException ve)
{
System.out.println("validation exception");
}
catch(processException p)
{
System.out.println(p.toString());
p.printStackTrace();
}
}
}