import javax.swing.Icon;
import javax.swing.table.AbstractTableModel;


public class ImageInTable extends AbstractTableModel {

	    private String[] columns;
	    private Object[][] rows;
	    
	    public ImageInTable(){}
	    
	    public ImageInTable(Object[][] data, String[] columnName){
	    
	        this.rows = data;
	        this.columns = columnName;
	    }//end constructor

	    
	    public Class getColumnClass(int column){
	        if(column == 7){ //1..8
	            return Icon.class;
	        }//end if
	        else{
	            return getValueAt(0,column).getClass();
	        }//end else
	    }//end C getColumClass

	    public int getRowCount() {
	     return this.rows.length;
	    }

	    public int getColumnCount() {
	     return this.columns.length;
	    }

	    public Object getValueAt(int rowIndex, int columnIndex) { 
	    
	    return this.rows[rowIndex][columnIndex];
	    }
	    public String getColumnName(int col){
	        return this.columns[col];
	    }


}
