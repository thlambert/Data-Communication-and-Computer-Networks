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
    BufferedReader readSock;
    Date d = new Date();
    ServerUI currentUI;
    
    /**
     * Constructor which sets up the connections
     */
    
    public ServerThread(Socket clientSock, ServerUI ui)
    {
        try
        {
            currentUI = ui;
            sock = clientSock;
            readSock = new BufferedReader(new 
                                    InputStreamReader(sock.getInputStream()));
            currentUI.add("Got a connection: " + date() + " " 
                    + clientSock.getInetAddress() 
                    + " Port: " + clientSock.getPort());
            writeSock = new PrintWriter(clientSock.getOutputStream(), true);
        }
        catch (Exception ex)
        {
            writeSock.println(ex.getMessage()); 
        }
        
    }

    /**
     * Handles reading the input message, calculating the factorial, 
     * sending the output message and closing the connection
     */
    
    @Override
    public void run()
    {
        try
        {
            String inputLine;
            int outputLine = 0;
            double temp = 0;

            while((inputLine = readSock.readLine()) != null)
            {

                if(inputLine.equals("quit"))
                {
                    break;
                }
                try
                {
                    temp = 0;
                    outputLine = Integer.parseInt(inputLine);
                    if(outputLine < 1 || outputLine > 25)
                    {
                        writeSock.println("Incorrect Range. "
                                + "Input allowed between 1 - 25.");
                    }
                    else
                    {
                        for(int x = outputLine; x > 0; x--)
                        {
                            if(temp == 0)
                            {
                                temp = temp + x;
                            }
                            else
                            {
                                temp = temp * x;
                            }
                        }
                        if(temp == 0)
                        {
                            temp = 1;
                        }
                        writeSock.println(temp);
                    }

                    currentUI.add("Received message from IP: " 
                            + sock.getInetAddress()
                            + " Port: " + sock.getPort() 
                            + " Message: " + (inputLine) + " Response: " + 
                            temp);
                }
                catch (Exception ex)
                {
                    
                    writeSock.println(ex.getMessage());
                    currentUI.add("Received message from IP: " 
                            + sock.getInetAddress()
                            + " Port: " + sock.getPort() 
                            + " Message: " + (inputLine) + " Response: " + 
                            temp);
                }
                
            }
            currentUI.add("Connection closed. Port: " + sock.getPort());
            sock.close();
        }
        catch (Exception ex)
        {
            writeSock.println(ex.getMessage());
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
