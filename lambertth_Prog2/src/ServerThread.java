import java.io.*;
import java.net.*;
import java.util.*;
import java.sql.Timestamp;

/**
 * Handles each of the connection threads
 * @author lambertth
 */
class ServerThread extends Thread
{
    Socket sock;
    PrintWriter writeSock;
    PrintWriter log;
    BufferedReader readSock;
    Date d = new Date();
    ServerUI currentUI;
    
    /**
     * Constructor which sets up the connections
     */
    
    public ServerThread(Socket clientSock, PrintWriter logfile, ServerUI ui)
    {
        try
        {
            currentUI = ui;
            sock = clientSock;
            log = logfile;
            readSock = new BufferedReader(new 
                                    InputStreamReader(sock.getInputStream()));
            log.println("Got a connection: " + date() + " " 
                    + clientSock.getInetAddress() 
                    + " Port: " + clientSock.getPort());
            currentUI.add("Got a connection: " + date() + " " 
                    + clientSock.getInetAddress() 
                    + " Port: " + clientSock.getPort());
            writeSock = new PrintWriter(clientSock.getOutputStream(), true);
        }
        catch (Exception ex)
        {
            log.println(ex.getMessage());
        }
        
    }

    /**
     * Handles reading the input message, sending the output message 
     * and closing the connection
     */
    
    @Override
    public void run()
    {
        try
        {
            String inputLine, outputLine;
            PolyAlphabet p = new PolyAlphabet();
            while((inputLine = readSock.readLine()) != null)
            {
                
                outputLine = p.encrypt("Message received: " + (inputLine));
                if(p.decrypt(inputLine).equals("quit") || 
                        inputLine.equals("quit"))
                {
                    break;
                }
                writeSock.println(outputLine);
                log.println("Received message from IP: " + sock.getInetAddress()
                        + " Port: " + sock.getPort() 
                        + " Message: " + (inputLine));
                currentUI.add("Received message from IP: " 
                        + sock.getInetAddress()
                        + " Port: " + sock.getPort() 
                        + " Message: " + (inputLine));
            }
            log.println("Connection closed. Port: " + sock.getPort());
            currentUI.add("Connection closed. Port: " + sock.getPort());
            sock.close();
        }
        catch (Exception ex)
        {
            log.println(ex.getMessage());
        }
    }
    
    /**
     * Returns the current date and time
     */
    public String date()
    {
        String datestr = "";
        
        Date date = new Date();
        long time = date.getTime();
        Timestamp ts = new Timestamp(time);
        
        datestr += ts.toLocaleString();
        return datestr;
    }
}
