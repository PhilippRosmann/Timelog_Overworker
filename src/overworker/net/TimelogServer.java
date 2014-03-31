/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package overworker.net;

import overworker.data.TimelogProvider;



/**
 *
 * @author schueler
 */
public class TimelogServer extends SimpleServer
{
  private final static int PORT=4711;
  private final TimelogProvider dataprovider;

  public TimelogServer(TimelogProvider dataprovider)
  {
    super(PORT);
    this.dataprovider = dataprovider;
  }
  

  @Override
  protected String createResponse(String request)
  {
    
    String resp ="ERROR";
    try
    {
      switch(request.toUpperCase())
      {
        case "GET NAME": resp = dataprovider.getServerLocation();break;
        case "GET ERFASSUNG": resp = dataprovider.getTimelog().toCsv();break;
        default: resp = "ERROR: Unbekannter Befehl";break;
      }
    }
    catch(Exception ex)
    {
      resp = "ERROR:" +ex.getMessage();
    }
    
    return resp;
  }
  
}
