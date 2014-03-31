/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package overworker.net;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;

/**
 *
 * @author Thomas
 * 
 * InetAddress: repräsentiert IP-Adresse,
 * Socket: für die Verbindung zuständig
 */
public class SimpleClient
{
  public static String sendRequestAndReceiveResponse(String host, int port, String request) throws Exception
  {
    //Schritt 1: Namen auflösen
    final InetAddress adr = InetAddress.getByName(host);
    
    //Schritt 2: Verbindung aufbauen
    try(Socket socket = new Socket(adr, port))
    {
      //Schritt 3: OutputStream holen + Writer erzeugen
      final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
      
      //Schritt 4: Request senden
      writer.write(request);
      writer.newLine();
      writer.flush();
      //socket.shutdownOutput();
      
      //Schritt 5: InputStream holen + Reader erzeugen
      final BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      
      //Schritt 6: Response einlesen
      final StringBuilder sb = new StringBuilder();
      String zeile;
      
      while((zeile = reader.readLine()) != null)
      {
        sb.append(zeile).append("\n");  //Rückgabewert von append ist ein StringBuilder d.h. er gibt sich selbst zurück. 
                                        //Dadurch kann append nochmal aufgerufen werden. (normalerweise syntax error)
      }
      
      return sb.toString();      
    }    
  }
  
  public static void main(String[] args)
  {
    try
    {
      //weil die Methode statisch ist, ist es möglich ohne Objekt die Methode aufzurufen
      final String response = SimpleClient.sendRequestAndReceiveResponse("localhost", 4711, 
              "GET NAME");
      
      System.out.println(response);
          
      final String response1 = SimpleClient.sendRequestAndReceiveResponse("localhost", 4711, 
              "GET ERFASSUNG");
      
      System.out.println(response1);
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
  }
}
