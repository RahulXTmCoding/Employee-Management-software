package com.thinking.machines.hr.pl.ui;
import com.thinking.machines.hr.pl.model.*;
import com.thinking.machines.hr.pl.exceptions.*;
import com.thinking.machines.hr.bl.interfaces.*;
import com.thinking.machines.hr.bl.pojo.*;
import java.awt.*;
import java.awt.event.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;
import java.math.*;
import java.net.URL; 
public class EmployeePanel extends JPanel
{
private enum Mode{VIEW,ADD,EDIT,DELETE,EXPORTTOPDF};
private Mode mode=Mode.VIEW;
private EmployeeModel em;
private JTable table;
private JScrollPane jsp;
private JLabel j1;
private JLabel searchlabel;
private JLabel sel;
private JTextField jstb;
private JButton jsb;

private InnerPanel ip;
public EmployeePanel()
{
initComponent();
setApperiance();
addListener();
view();
}
private void initComponent()
{
ip=new InnerPanel();
em=new EmployeeModel();
table=new JTable(em);
jsp=new JScrollPane(table,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
j1=new JLabel("Employee:");
searchlabel=new JLabel("Search");
 sel=new JLabel("");
jstb=new JTextField(20);
jsb=new JButton("X");


}
private void setApperiance()
{
table.setRowHeight(30);
table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
this.setLayout(null);
jsp.setBounds(5,75,480,260);
j1.setBounds(5,5,80,30);
sel.setBounds(150,25,50,10);
searchlabel.setBounds(5,40,80,30);
jstb.setBounds(100,40,100,30);
jsb.setBounds(210,40,60,30);
j1.setFont(new Font(j1.getFont().getName(), Font.PLAIN,16));
sel.setFont(new Font(sel.getFont().getName(), Font.PLAIN,9));
sel.setForeground (Color.red);

ip.setBounds(6,341,476,259);

this.add(ip);
this.add(j1);
this.add(sel);
this.add(searchlabel);

this.add(jstb);
this.add(jsb);
this.add(jsp);


this.setVisible(true);
}
private void addListener()
{
jstb.getDocument().addDocumentListener(new DocumentListener() {
      public void changedUpdate(DocumentEvent documentEvent) {
        Search(jstb.getText());
      }
      public void insertUpdate(DocumentEvent documentEvent) {
        Search(jstb.getText());
      }
      public void removeUpdate(DocumentEvent documentEvent) {
       Search(jstb.getText());
      }
}
);


table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
        public void valueChanged(ListSelectionEvent event) {
            
int row=table.getSelectedRow();
if(row<0)
{
return;
}
EmployeeInterface e=em.getData(row);
ip.setEmployee(e);
        }
    });
}
public void paintComponent(Graphics g) {
    super.paintComponent(g);  
    g.drawRect(5,340,477,260);  
       
  }

public void Search(String s)
{

try{
int index=em.search(s);
sel.setText("");
table.getSelectionModel().setSelectionInterval(index,index);
table.scrollRectToVisible(new Rectangle(table.getCellRect(index,0,true)));
}catch(ModelException me)
{
sel.setText(me.toString());
}

}
public void view()
{
ip.view();
}
public void add()
{}
public void delete()
{}
public void edit()
{}
public void pdf()
{
em.toPdf();
}

class InnerPanel extends JPanel
{
private EmployeeInterface ei;
private JLabel name;
private JLabel gender;
private JLabel salary;
private JLabel pannumber;
private JLabel code;
private JLabel name1;
private JLabel gender1;
private JLabel salary1;
private JLabel pannumber1;
private JLabel code1;
private JButton add;
private JButton edit;
private JButton delete;
private JButton pdf;
private JButton undo;
private JButton save;
private JTextField code2;
private JTextField name2;
private JTextField gender2;
private JTextField salary2;
private JTextField pannumber2;
InnerPanel()
{
initComponent();
setApperiance();
addListener();
view();
}
private void initComponent()
{
code=new JLabel("Code:");
name=new JLabel("Name:");
gender=new JLabel("Gender:");
salary=new JLabel("Salary:");
pannumber=new JLabel("Pan_Number:");
code1=new JLabel("");
name1=new JLabel("");
gender1=new JLabel("");
salary1=new JLabel("");
pannumber1=new JLabel("");
code2=new JTextField("");
name2=new JTextField("");
gender2=new JTextField("");
salary2=new JTextField("");
pannumber2=new JTextField("");
add=new JButton("add");

edit=new JButton("edit");
save=new JButton("");
delete=new JButton("delete");

undo=new JButton("undo");

pdf=new JButton("pdf");

}
private void setApperiance()
{
setLayout(null);
code.setBounds(5,10,80,30);
code1.setBounds(100,10,80,30);
name.setBounds(5,50,80,30);
name1.setBounds(100,50,80,30);
pannumber.setBounds(5,90,80,30);

pannumber1.setBounds(100,90,80,30);
salary.setBounds(220,10,80,30);
salary1.setBounds(310,10,80,30);
gender.setBounds(220,50,80,30);
gender1.setBounds(310,50,80,30);


code2.setBounds(100,10,80,30);

name2.setBounds(100,50,80,30);


pannumber2.setBounds(100,90,80,30);

salary2.setBounds(310,10,80,30);

gender2.setBounds(310,50,80,30);


add.setBounds(10,140,60,65);
edit.setBounds(100,140,60,65);
undo.setBounds(195,140,60,65);
delete.setBounds(290,140,60,65);
pdf.setBounds(385,140,60,65);
save.setBounds(10,140,60,65);
add(code);
add(code1);
add(salary);
add(salary1);
add(name);
add(name1);
add(gender);
add(gender1);
add(pannumber);
add(pannumber1);
add(code2);
add(name2);
add(salary2);
add(gender2);
add(pannumber2);
add(add);
add(edit);
add(undo);
add(delete);
add(pdf);
add(save);
setVisible(true);
try{
add.setHorizontalAlignment(SwingConstants.CENTER);
pdf.setHorizontalAlignment(SwingConstants.CENTER);
undo.setHorizontalAlignment(SwingConstants.CENTER);
edit.setHorizontalAlignment(SwingConstants.CENTER);
delete.setHorizontalAlignment(SwingConstants.CENTER);
save.setHorizontalAlignment(SwingConstants.CENTER);

ImageIcon icon=new ImageIcon("C://uec20198am//hr//pl//icons//add1.png");
add.setIcon(icon);
icon=new ImageIcon("C://uec20198am//hr//pl//icons//delete1.png");
delete.setIcon(icon);
icon=new ImageIcon("C://uec20198am//hr//pl//icons//save1.png");
save.setIcon(icon);
icon=new ImageIcon("C://uec20198am//hr//pl//icons//edit1.png");
edit.setIcon(icon);
icon=new ImageIcon("C://uec20198am//hr//pl//icons//cancel1.png");
undo.setIcon(icon);
icon=new ImageIcon("C://uec20198am//hr//pl//icons//pdf1.png");
pdf.setIcon(icon);
}catch(Exception e)
{
e.printStackTrace();
}
}
private void addListener()
{
add.addActionListener(new ActionListener(){

public void actionPerformed(ActionEvent ae)
{
InnerPanel.this.add();
EmployeePanel.this.add();
}

});


undo.addActionListener(new ActionListener(){

public void actionPerformed(ActionEvent ae)
{
undo();
}

});

save.addActionListener(new ActionListener(){

public void actionPerformed(ActionEvent ae)
{
save();

}

});

delete.addActionListener(new ActionListener(){

public void actionPerformed(ActionEvent ae)
{
delete();

}

});

edit.addActionListener(new ActionListener(){

public void actionPerformed(ActionEvent ae)
{
edit();

}

});


pdf.addActionListener(new ActionListener(){

public void actionPerformed(ActionEvent ae)
{
pdf();

}

});

}
public void setEmployee(EmployeeInterface ef)
{
ei=ef;
code1.setText(String.valueOf(ei.getCode()));
name1.setText(ei.getName());
gender1.setText(ei.getGender());
salary1.setText(ei.getSalary().toPlainString());
pannumber1.setText(ei.getPanNumber());
edit.setEnabled(true);

delete.setEnabled(true);

}
protected ImageIcon createImageIcon(String path,
                                           String description) {
    java.net.URL imgURL = getClass().getResource(path);
    if (imgURL != null) {
        return new ImageIcon(imgURL, description);
    } else {
        System.err.println("Couldn't find file: " + path);
        return null;
    }
}
public void view()
{
if(mode==Mode.VIEW)
{
add.setEnabled(true);
save.setEnabled(false);
edit.setEnabled(false);
delete.setEnabled(false);
undo.setEnabled(false);
pdf.setEnabled(true);
code2.setVisible(false);
name2.setVisible(false);

gender2.setVisible(false);

salary2.setVisible(false);

pannumber2.setVisible(false);



}
}
public void add()
{
mode=Mode.ADD;
jstb.setEnabled(false);
jsb.setEnabled(false);
table.setRowSelectionAllowed(false);

code2.setEnabled(false);
code1.setVisible(false);
name1.setVisible(false);

gender1.setVisible(false);

salary1.setVisible(false);

pannumber1.setVisible(false);


code2.setVisible(true);
name2.setVisible(true);

gender2.setVisible(true);

salary2.setVisible(true);

pannumber2.setVisible(true);


code2.setText("");
name2.setText("");

gender2.setText("");

salary2.setText("");

pannumber2.setText("");


add.setVisible(false);
edit.setEnabled(false);
delete.setEnabled(false);
undo.setEnabled(true);
pdf.setEnabled(false);
save.setEnabled(true);
}
public void undo()
{
mode=Mode.VIEW;
jstb.setEnabled(true);
jsb.setEnabled(true);

table.setRowSelectionAllowed(true);

code1.setVisible(true);
name1.setVisible(true);

gender1.setVisible(true);

salary1.setVisible(true);

pannumber1.setVisible(true);

code2.setVisible(false);
name2.setVisible(false);

gender2.setVisible(false);

salary2.setVisible(false);

pannumber2.setVisible(false);



add.setVisible(true);
edit.setEnabled(true);
delete.setEnabled(true);
undo.setEnabled(false);
pdf.setEnabled(true);
save.setEnabled(false);
}
public void save()
{



EmployeeInterface ef=null;
if(mode==mode.ADD)
{
ef=new Employee();
ef.setName(name2.getText());
ef.setSalary(new BigDecimal(salary2.getText()));
ef.setGender(gender2.getText());
ef.setPanNumber(pannumber2.getText());
try{
em.add(ef);
}
catch(ModelException me)
{
 JOptionPane.showMessageDialog(EmployeePanel.this,em.toString());
}

try{
int index=em.search(ef.getName());
sel.setText("");
table.getSelectionModel().setSelectionInterval(index,index);
table.scrollRectToVisible(new Rectangle(table.getCellRect(index,0,true)));
}catch(ModelException me)
{
sel.setText(me.toString());
}

}
if(mode==mode.EDIT)
{
ef=ei;

ef.setName(name2.getText());
ef.setSalary(new BigDecimal(salary2.getText()));
ef.setGender(gender2.getText());
ef.setPanNumber(pannumber2.getText());
try{
em.edit(ef);
}
catch(ModelException me)
{
 JOptionPane.showMessageDialog(EmployeePanel.this,em.toString());
}

}



jstb.setEnabled(true);
jsb.setEnabled(true);
code1.setVisible(true);
name1.setVisible(true);

gender1.setVisible(true);

salary1.setVisible(true);

pannumber1.setVisible(true);

code2.setVisible(false);
name2.setVisible(false);

gender2.setVisible(false);

salary2.setVisible(false);

pannumber2.setVisible(false);



add.setVisible(true);
edit.setEnabled(true);
delete.setEnabled(true);
undo.setEnabled(false);
pdf.setEnabled(true);
save.setEnabled(false);
mode=Mode.VIEW;
table.setRowSelectionAllowed(true);
}
public void edit()
{
mode=Mode.EDIT;
jstb.setEnabled(false);
jsb.setEnabled(false);
table.setRowSelectionAllowed(false);

code2.setEnabled(false);
code1.setVisible(false);
name1.setVisible(false);

gender1.setVisible(false);

salary1.setVisible(false);

pannumber1.setVisible(false);

code2.setText(String.valueOf(ei.getCode()));
name2.setText(ei.getName());
salary2.setText(ei.getSalary().toPlainString());
gender2.setText(ei.getGender());
pannumber2.setText(ei.getPanNumber());

code2.setVisible(true);
name2.setVisible(true);

gender2.setVisible(true);

salary2.setVisible(true);

pannumber2.setVisible(true);



add.setVisible(false);
edit.setEnabled(false);
delete.setEnabled(false);
undo.setEnabled(true);
pdf.setEnabled(false);
save.setEnabled(true);
}

public void delete()
{
try{
em.delete(ei);
}
catch(ModelException me)
{
 JOptionPane.showMessageDialog(EmployeePanel.this,em.toString());
}
code1.setText(" ");
name1.setText(" ");
gender1.setText(" ");
salary1.setText(" ");
pannumber1.setText(" ");
}
public void pdf()
{

EmployeePanel.this.pdf();

}
}
}