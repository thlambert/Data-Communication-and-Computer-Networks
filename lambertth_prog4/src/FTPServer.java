import java.net.*;

/**
 * Handles setting up the server
 * @author lambertth
 */
public class FTPServer {
    
    public static void main(String args[])
    {
        FTPServer serv = new FTPServer();
        serv.run();
    }
    
    /**
    * Handles setting up the server socket, the output stream and waits 
    * for connections
    */
    public void run()
    {
        try
        {
            ServerUI ui = new ServerUI();
            ui.setVisible(true);
            int portNum = 5764;
            ServerSocket servSock = new ServerSocket(portNum);
            while(true)
            {
                Socket sock = servSock.accept();
                FTPThread servThread = new FTPThread(sock, ui);
                servThread.start();
            }
        }
        catch(Exception ex)
        {

        }
    }
}
