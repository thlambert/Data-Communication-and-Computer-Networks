import java.io.*;
import java.net.*;
import java.util.*;
import java.sql.Timestamp;

/**
 * Handles each of the connection threads
 * @author lambertth
 */
class FTPThread extends Thread
{
    Socket sock;
    PrintWriter writeSock;
    BufferedReader readSock;
    Date d = new Date();
    ServerUI currentUI;
    String location = "C:\\Users\\lambertth\\Desktop\\Server";
    //String location2 = "C:\\Users\\Tom\\Desktop\\Server";
    
    /**
     * Constructor which sets up the connections and calls files().
     */
    public FTPThread(Socket clientSock, ServerUI ui)
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
            files();
        }
        catch (Exception ex)
        {
            writeSock.println(ex.getMessage()); 
        }
        
    }

    /**
     * Handles reading the input message and then the filename 
     * and calling the appropriate method to either send or receive a file.
     */
    @Override
    public void run()
    {
        try
        {
            String inputLine;
            while((inputLine = readSock.readLine()) != null)
            {
                //inputLine = readSock.readLine();
                if(inputLine.equals("quit"))
                {
                    break;
                }
                else if(inputLine.equals("get")) //server sends to client
                {
                    inputLine = readSock.readLine();
                    sendFile(inputLine);
                }
                else if(inputLine.equals("put")) // client sends to server
                {
                    inputLine = readSock.readLine();
                    getFile(inputLine);
                }
                else
                {
                    
                }
            }
            currentUI.add("Connection closed. Port: " + sock.getPort() + " " +
                    date());
            sock.close();
        }
        catch (Exception ex)
        {
            writeSock.println(ex.getMessage());
        }
    }
    
    /**
     * Makes a vector and then adds the filenames from the location to the 
     * vector. Sends vector to client. 
     */
    private void files()
    {
        try
        {
            Vector items = new Vector();
            File dir = new File(location);
            File [] files = dir.listFiles();
            String temp = "";
            for(int i = 0; i < files.length; i++)
            {
                if(files[i].isFile())
                {
                    items.add(files[i].getName());
                    temp += items.get(i).toString() + ",";
                }
            }
            writeSock.println(temp);
        }
        catch (Exception ex)
        {
            writeSock.println(ex.getMessage());
        }
    }
    
    /**
     * Creates socket and sends port number to client.
     * Looks through the list of files until you get to filename provided. 
     * Read file 1024 bytes at a time and put it in mybytearray. 
     * Write mybytearray to client. Close connection when done. 
     * Sends message to client. 
     * @param filename 
     */
    private void sendFile(String filename)
    {
        try
        {
            File folder = new File(location);
            File[] listofFiles = folder.listFiles();
            ServerSocket servSoc = new ServerSocket(5720);
            writeSock.println(servSoc.getLocalPort());
            Socket s1 = servSoc.accept();
            FileInputStream fos = new FileInputStream(location + "\\" + filename);
            DataOutputStream out = new DataOutputStream(new BufferedOutputStream(s1.getOutputStream()));
            int current = 0;
            currentUI.add("Sending File: " + filename);
            for(int i = 0; i < listofFiles.length; i++)
            {
                if(listofFiles[i].getName().equals(filename))
                {
                    int filesize = 1024;
                    byte[] mybytearray = new byte[filesize];
                    while(fos.read(mybytearray) != -1)
                    {
                        current = current + filesize;
                        out.write(mybytearray);
                        out.flush();
                    }
                }
            }
            
            currentUI.add("Bytes sent: " + current);
            fos.close();
            s1.close();
            servSoc.close();
            currentUI.add("Connection closed");
        }
        catch(Exception ex)
        {
            
        }
    }
    
    /**
     * Creates new socket and sends port number to client.
     * Reads file at location and 1024 bytes at a time into mybytearray.
     * Sends mybytearray to client. Closes connection when done. 
     * Sends updated file list.
     * @param filename 
     */
    private void getFile(String filename)
    {
        try
        {
            currentUI.add("Receiving File: " + filename);
            ServerSocket servSock = new ServerSocket(5720);
            writeSock.println(servSock.getLocalPort());
            Socket s2 = servSock.accept();
            
            DataInputStream in = new 
                DataInputStream(new BufferedInputStream(s2.getInputStream()));
            FileOutputStream fos = new FileOutputStream(location + "\\" + filename);

            int filesize = 1024;
            int bytesRead;
            int current = 0;
            byte[] mybytearray = new byte[filesize];
            while((bytesRead = in.read(mybytearray)) > 0)
            {
                current += bytesRead;
                fos.write(mybytearray, 0, bytesRead);
            }
            currentUI.add("Bytes received: " + current);
            servSock.close();
            in.close();
            fos.close();
            s2.close();
            currentUI.add("Connection closed");
            files();
        }
        catch(Exception ex)
        {
            
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
