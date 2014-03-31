/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package overworker.data;


/**
 *
 * @author schueler
 */
public interface TimelogProvider
{
  public TimelogCollection getTimelog();
  public String getServerLocation();
}
