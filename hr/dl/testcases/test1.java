import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.dao.*;
import java.math.*;
class EmployeeAddTestCases
{
public static void main(String gg[])
{
try
{
String name=gg[0];
String gender=gg[1];
String panNumber=gg[2];
BigDecimal salary=new BigDecimal(gg[3]);
EmployeeDTOInterface employee;
employee=new EmployeeDTO();
employee.setName(name);
employee.setGender(gender);
employee.setPanNumber(panNumber);

employee.setSalary(salary);
EmployeeDAOInterface employeeDAO;
employeeDAO=new EmployeeDAO();
employeeDAO.add(employee);
System.out.printf("Employee added with code as %d\n",employee.getCode());
EmployeeDTOInterface h=employeeDAO.getByCode(3);
System.out.println(h.getName()+h.getGender());
EmployeeDTOInterface e=employeeDAO.getByPanNumber("f5565vfd");
System.out.println(e.getName()+e.getGender());
h.setName("lofar");
employeeDAO.update(h);
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}