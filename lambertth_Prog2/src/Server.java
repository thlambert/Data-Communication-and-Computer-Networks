import java.io.*;
import java.net.*;

/**
 * Handles setting up the server
 * @author lambertth
 */
public class Server {
    
    public static void main(String args[])
    {
        Server serv = new Server();
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
            PrintWriter lg = new PrintWriter(new FileOutputStream
                                            (new File("Prog2.log")), true);
            while(true)
            {
                Socket sock = servSock.accept();
                ServerThread servThread = new ServerThread(sock, lg, ui);
                servThread.start();
            }
        }
        catch(Exception ex)
        {

        }
    }
}
