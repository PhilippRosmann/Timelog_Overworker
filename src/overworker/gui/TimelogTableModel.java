/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package overworker.gui;

import javax.swing.table.AbstractTableModel;
import overworker.data.TimelogCollection;


/**
 *
 * @author schueler
 */
public class TimelogTableModel extends AbstractTableModel
{
  private final TimelogCollection collection;


  public TimelogTableModel(TimelogCollection collection)
  {
    this.collection = collection;
  }
  
  

  @Override
  public int getRowCount()
  {
    return collection.size();
  }


  @Override
  public int getColumnCount()
  {
    return 3;
  }


  @Override
  public String getColumnName(int column)
  {
    switch(column)
    {
      case 0: return "Time";
      case 1: return "Id";
      case 2: return "A/D";
      default: return null;
    }
  }

  
  

  @Override
  public Object getValueAt(int rowIndex, int columnIndex)
  {
    switch(columnIndex)
    {
      case 0: return collection.get(rowIndex).getZeit();
      case 1: return collection.get(rowIndex).getId();
      case 2: return collection.get(rowIndex).isKommend()?"A":"D";
      default: return null;
    }
  }
  
}
