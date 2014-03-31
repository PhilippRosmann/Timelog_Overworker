/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package overworker.data;

import java.util.ArrayList;


/**
 *
 * @author schueler
 */
public class TimelogCollection
{
  private final ArrayList<Timelog> list = new ArrayList<>();


  public int size()
  {
    return list.size();
  }


  public Timelog get(int index)
  {
    return list.get(index);
  }


  public boolean add(Timelog e)
  {
    return list.add(e);
  }
  
  public String toCsv()
  {
    final StringBuilder sb = new StringBuilder();
    for(Timelog e : list)
    {
      sb.append(e.toCsvLine()).append("\n");
    }
    return sb.toString();
  }
}
