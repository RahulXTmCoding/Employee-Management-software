import javax.swing.*;
import java.awt.*;
import java.util.*;
import javax.swing.table.*;
class MyModel extends AbstractTableModel
{
private String[] title={"S.No","Roll number","Name","Indian"};
private Object[][] data;
int i=1;
MyModel()
{
data=new Object[5][4];
data[0][0]=new Integer(1);
data[0][1]=123; // Autoboxing
data[0][2]="Rahul";
data[0][3]=true;
data[1][0]=new Integer(2);
data[1][1]=103; // Autoboxing
data[1][2]="Rita";
data[1][3]=false;
data[2][0]=new Integer(3);
data[2][1]=323; // Autoboxing
data[2][2]="Lalita";
data[2][3]=true;

data[3][0]=new Integer(4);
data[3][1]=350; // Autoboxing
data[3][2]="Rakesh";
data[3][3]=true; }
public int getColumnCount() {
System.out.println("column chala");
return title.length; }
public String getColumnName(int columnIndex) {
return title[columnIndex]; }
public int getRowCount() {
System.out.println("row chala="+(i++));

return data.length; }
public boolean isCellEditable(int rowIndex,int columnIndex) {
if(columnIndex==3) return true;
return false; }
public Object getValueAt(int rowIndex,int columnIndex) {
return data[rowIndex][columnIndex]; }
public Class getColumnClass(int columnIndex) {
Class c=null;
try{
if(columnIndex==0 || columnIndex==1) {
c=Class.forName("java.lang.Integer"); }
if(columnIndex==2) {
c=Class.forName("java.lang.String"); }
if(columnIndex==3) {
c=Class.forName("java.lang.Boolean"); }
}catch(ClassNotFoundException cnfe) {}
return c;

}
public void setValueAt(Object d,int rowIndex,int columnIndex)
{
data[rowIndex][columnIndex]=d;
}
}
class swing3 extends JFrame
{
private Container c;
private JTable table;
private JScrollPane jsp;
private MyModel m;
swing3()
{
m=new MyModel();
table=new JTable(m);
Font f=new Font("Verdana",Font.PLAIN,16);
table.setFont(f);
table.setRowHeight(30);
jsp=new
JScrollPane(table,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
c=getContentPane(); // get the address of the container on which we will be placing our compopnets
c.setLayout(new BorderLayout());
c.add(jsp,BorderLayout.CENTER);
setDefaultCloseOperation(EXIT_ON_CLOSE);
setSize(500,400);
Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
setLocation(d.width/2-250,d.height/2-200);
setVisible(true);
}
public static void main(String g[])
{
swing3 s=new swing3();
}
}