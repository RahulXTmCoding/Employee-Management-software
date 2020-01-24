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
class psp
{
public static void main(String args[])
{
EmployeeInterface e;
EmployeeManagerInterface em=new EmployeeManager();

List<EmployeeInterface> list=em.getOrderedBy(EmployeeManagerInterface.NAME);
list.forEach((it)->{System.out.println(it.getName());});
System.out.println(list.get(1).getName());
}
}