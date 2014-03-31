/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package overworker.net;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author Thomas
 */
public abstract class SimpleServer    //abstract => nicht alle Methoden sind ausimplementiert (es fehlt min. 1 Methode)
{
  private final int port;
  private ServerSocket serverSocket = null;
  private HandleRequestThread handleRequestThread;
  
  private final ExecutorService exe = Executors.newFixedThreadPool(4);    //legt fest, wie viele Threads maximal zu gleich laufen
  
  public SimpleServer(int port)
  {
    this.port = port;
  }
  
  private class HandleRequestThread extends Thread    //Thread wird verwendet, wenn nichts bei der GUI verändert werden soll
  {
    @Override
    public void run()       //hier kommt das rein, was im hintergrund ausgeführt werden soll (=doInBackground beim SwingWorker)
    {
      while(!isInterrupted())           //wenn interrupt flag gesetzt, wird die schleife beendet (zeile 115)
      {
        try
        {
          handleRequest();
        }
        catch (SocketTimeoutException ex)
        {          
        }
        catch (Exception ex)
        {
          ex.printStackTrace();
        }
      }
    }
  }
  
  private class AcceptWorker implements Runnable    //Runnable = Arbeitsauftrag
  {
    private final Socket socket;

    public AcceptWorker(Socket socket)
    {
      this.socket = socket;
    }
    
    @Override
    public void run()
    {
      try
      {
        final BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        //InputStreamReader wandelt die Bytes vom InputStream in Zeichen um und BufferedReader fasst sie zusammen

        final String request = reader.readLine();
        final String response = createResponse(request);

        final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        writer.write(response);
        writer.newLine();
        writer.flush();
        socket.shutdownOutput();
      }
      catch (Exception ex)
      {        
      }     
    }    
  }
  
  public void start() throws IOException
  {
    if(serverSocket == null)
    {
      serverSocket = new ServerSocket(port);
      serverSocket.setSoTimeout(500);           
      handleRequestThread = new HandleRequestThread();
      handleRequestThread.start();
    }    
  }
  
  private void handleRequest() throws IOException
  {
    final Socket socket = serverSocket.accept();  //accept => server wartet und horcht am port bis eine anfrage kommt
    //new Thread(new AcceptWorker(socket)).start();
    exe.submit(new AcceptWorker(socket));
  }
  
  protected abstract String createResponse(String request);
  
  public void stop() throws Exception
  {
    if(serverSocket != null)
    {
      handleRequestThread.interrupt();  //interrupt flag wird gesetzt und in zeile 42 mit isInterrupted() abgefragt
      handleRequestThread.join(3000);   //wartet 3000ms bis sich der Thread beendet
      serverSocket.close();             //Verbindung wird getrennt
      serverSocket = null;
    }    
  }
  
//  public static void main(String[] args)
//  {
//    try
//    {
//      final SimpleServer server = new SimpleServer(4711);
//      server.start();
//      Thread.sleep(3000);
//      server.stop();
//    }
//    catch (Exception ex)
//    {
//      ex.printStackTrace();
//    }
//  }
}
