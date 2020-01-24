package com.thinking.machines.hr.pl.model;
import javax.swing.table.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.util.*;
import java.util.stream.Collectors; 
import java.util.stream.Stream;
import java.io.*;
import com.itextpdf.text.*;
import com.itextpdf.text.exceptions.*;
import com.itextpdf.text.pdf.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.exceptions.*;
import com.thinking.machines.hr.bl.interfaces.*;
import com.thinking.machines.hr.bl.manager.*;
import com.thinking.machines.hr.pl.exceptions.*;
import com.thinking.machines.hr.pl.ui.*;
public class EmployeeModel extends AbstractTableModel
{
private java.util.List<EmployeeInterface> list;
EmployeeManagerInterface emf;
public EmployeeModel()
{
emf=new EmployeeManager();
populateDs();
}
private void populateDs()
{


list=emf.getOrderedBy(EmployeeManagerInterface.NAME);

}
public int getColumnCount()
{

return 3;
}
public String getColumnName(int columnindex)
{
if(columnindex==0) return "S.no";
if(columnindex==1) return "Code";
return "Employee";
}
public int getRowCount()
{

return list.size();

}
public boolean isCellEditable(int row,int column)
{
return false;
}
public Object getValueAt(int row,int column)
{
EmployeeInterface ef=list.get(row);

if(column==0) return (row+1);
if(column==1) return ef.getCode();
return ef.getName();
}
public Class getColumnClass(int column)
{
if(column==0||column==1) return Integer.class;
return String.class;
}
public void setValueAt(Object d,int row,int column)
{

}
public int search(String s) throws ModelException
{
EmployeeInterface e;
for(int i=0;i<list.size();i++)
{
e=list.get(i);
if(e.getName().toUpperCase().startsWith(s.toUpperCase())) return i;
}
throw new ModelException("Not found");
}
public EmployeeInterface getData(int row)
{
return list.get(row);
}
public void add(EmployeeInterface ei)throws ModelException
{
try{
emf.add(ei);
list.add(ei);
list=list.stream().sorted(Comparator.comparing(EmployeeInterface::getName,String.CASE_INSENSITIVE_ORDER).thenComparing(EmployeeInterface::getCode)).collect(Collectors.toList());
fireTableDataChanged();
}
catch(ValidationException ve)
{
new ModelException(ve.getMessage());
}
catch(processException pe)
{
new ModelException(pe.getMessage());
}

}
public void edit(EmployeeInterface ei)throws ModelException
{
try{
emf.update(ei);
int i;
for(i=0;i<list.size();i++)
{
if(list.get(i).getCode()==ei.getCode())
{
break;
}


}
list.set(i,ei);
list=list.stream().sorted(Comparator.comparing(EmployeeInterface::getName,String.CASE_INSENSITIVE_ORDER).thenComparing(EmployeeInterface::getCode)).collect(Collectors.toList());
fireTableDataChanged();
}
catch(ValidationException ve)
{
new ModelException(ve.getMessage());
}
catch(processException pe)
{
new ModelException(pe.getMessage());
}

}
public void delete(EmployeeInterface ei)throws ModelException
{
try
{
int i;
emf.delete(ei.getCode());
for(i=0;i<list.size();i++)
{
if(list.get(i).getCode()==ei.getCode())
{

break;
}


}
list.remove(i);
fireTableDataChanged();
}catch(ValidationException ve)
{
new ModelException(ve.getMessage());
}
catch(processException pe)
{
new ModelException(pe.getMessage());
}

}
public void toPdf()
{
try
{
JFileChooser j=new JFileChooser("");

j.setCurrentDirectory(new java.io.File("."));
j.setDialogTitle("ExportToPdf");
j.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
j.setAcceptAllFileFilterUsed(false);
FileNameExtensionFilter filter = new FileNameExtensionFilter("Pdf File", "pdf");
j.addChoosableFileFilter(filter);
int r=j.showOpenDialog(null);
String location=null;
String fileName=null;
if(r!=JFileChooser.APPROVE_OPTION)
{
return;
}
location=j.getSelectedFile().getAbsolutePath();
fileName=j.getSelectedFile().getName();

if(location.endsWith("."))
{
location=location+"pdf";
}
else
if(location.endsWith(".pdf"))
{

}
else
{
location=location+".pdf";
}
Document document=new Document();
try
{
PdfWriter w=PdfWriter.getInstance(document,new FileOutputStream(location));
document.open();

int se=0;
PdfPTable table=null; 
PdfPCell c1=null;
PdfPCell c2=null;
PdfPCell c3=null;
PdfPCell c4=null;
PdfPCell c5=null;
boolean flag=false;
int size=list.size();
while(se<size)
{
if(flag==false)
{
//header
table=new PdfPTable(5);
flag=true;
}

EmployeeInterface ef=list.get(se);
c1=new PdfPCell(new Paragraph(String.valueOf(ef.getCode())));
c2=new PdfPCell(new Paragraph(ef.getName()));
c3=new PdfPCell(new Paragraph(ef.getGender()));
c4=new PdfPCell(new Paragraph(ef.getSalary().toPlainString()));
c5=new PdfPCell(new Paragraph(ef.getPanNumber()));
table.addCell(c1);
table.addCell(c2);
table.addCell(c3);
table.addCell(c4);
table.addCell(c5);


se++;

if(se%40==0||se==size-1)
{
document.add(table);
//footer
if(se!=size-1)
{

document.newPage();
flag=false;
}
}
}


document.close();
w.close();
}
catch(DocumentException de)
{

}
catch(FileNotFoundException fe)
{
}


}catch(Exception e)
{
}

}
}