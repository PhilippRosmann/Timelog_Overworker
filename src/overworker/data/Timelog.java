/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package overworker.data;


/**
 *
 * @author schueler
 */
public class Timelog 
{
  private final int id;
  private final String time;
  private final boolean arrival;

  public Timelog(int id, String time, boolean arrival)
  {
    this.id = id;
    this.time = time;
    this.arrival = arrival;
  }


  public int getId()
  {
    return id;
  }


  public String getZeit()
  {
    return time;
  }


  public boolean isKommend()
  {
    return arrival;
  }
  
  public String toCsvLine()
  {
    return time+";"+id+";"+ (arrival?"1":"0");
  }
  
  
}
