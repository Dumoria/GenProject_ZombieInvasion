package server;

import com.google.gson.*;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * This class implements a multi-threaded TCP server. It is able to interact
 * with several clients at the time, as well as to continue listening for
 * connection requests.
 *
 * @author oussama lagha
 */
public class MultiThreadedServer {

    /*
    Create interface for bonus, etc..
     */

    final static Logger LOG = Logger.getLogger(MultiThreadedServer.class.getName());
    static UserList userList;

    Gson moteurJson = new Gson();

    int port;

    /**
     * Constructor
     *
     * @param port the port to listen on
     */
    public MultiThreadedServer(int port) {
        this.port = port;
        chargeUserList("Server_Zombi/src/main/java/server/Player.json");
    }

    void chargeUserList(String JsonFileName) {
        String stringJson = lireStringDepuisFichier(JsonFileName);
        userList = moteurJson.fromJson(stringJson, UserList.class);
    }

    public String lireStringDepuisFichier(String fileName) {

        String lignes = "";
        try {
            InputStream flux = new FileInputStream(fileName);
            InputStreamReader lecture = new InputStreamReader(flux);
            BufferedReader buff = new BufferedReader(lecture);
            String ligne;
            while ((ligne = buff.readLine()) != null) {
                lignes += ligne;
            }
            buff.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        return lignes;
    }

    /**
     * This method initiates the process. The server creates a socket and binds it
     * to the previously specified port. It then waits for clients in a infinite
     * loop. When a client arrives, the server will read its input line by line
     * and send back the data converted to uppercase. This will continue until the
     * client sends the "BYE" command.
     */
    public void serveClients() {
        LOG.info("Starting the Receptionist Worker on a new thread...");
        new Thread(new ReceptionistWorker()).start();
    }

    /**
     * This inner class implements the behavior of the "receptionist", whose
     * responsibility is to listen for incoming connection requests. As soon as a
     * new client has arrived, the receptionist delegates the processing to a
     * "servant" who will execute on its own thread.
     */
    private class ReceptionistWorker implements Runnable {

        @Override
        public void run() {
            ServerSocket serverSocket;

            try {
                serverSocket = new ServerSocket(port);
            } catch (IOException ex) {
                LOG.log(Level.SEVERE, null, ex);
                return;
            }

            while (true) {
                LOG.log(Level.INFO, "Waiting (blocking) for a new client on port " + port);
                try {
                    Socket clientSocket = serverSocket.accept();
                    LOG.info("A new client has arrived. Starting a new thread and delegating work to a new servant...");
                    new Thread(new ServantWorker(clientSocket)).start();
                } catch (IOException ex) {
                    Logger.getLogger(MultiThreadedServer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }

        /**
         * This inner class implements the behavior of the "servants", whose
         * responsibility is to take care of clients once they have connected. This
         * is where we implement the application protocol logic, i.e. where we read
         * data sent by the client and where we generate the responses.
         */
        private class ServantWorker implements Runnable {

            Socket clientSocket;
            BufferedReader in = null;
            PrintWriter out = null;
            private Boolean done=false;

            public ServantWorker(Socket clientSocket) {
                try {
                    this.clientSocket = clientSocket;
                    in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    out = new PrintWriter(clientSocket.getOutputStream());
                } catch (IOException ex) {
                    Logger.getLogger(MultiThreadedServer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
            public void run() {
                String command = "";

                out.println("Welcome to the Multi-Threaded Server. Send me text lines and conclude with the BYE command.");
                out.flush();

                try {
                    LOG.info("Reading the client data or closes the connection...");
                    while(!done&& ((command = in.readLine()) != null)) {
                        switch (command.toUpperCase()){
                            case ServerProtocol.CMD_BYE:
                                done=true;
                                LOG.info("Cleaning up resources...");
                                clientSocket.close();
                                in.close();
                                out.close();
                                break;
                            case ServerProtocol.CMD_CONNECT:
                                String line;
                                UserJson user = new UserJson("", "");
                                while (!userList.exist(user)) {
                                    line = in.readLine();
                                    user = moteurJson.fromJson(line, UserJson.class);

                                    if (userList.exist(user)) {
                                        out.write("connected\n");
                                    } else {
                                        out.write("disconnected\n");
                                    }
                                    out.flush();
                                }
                                break;
                            case ServerProtocol.CMD_CREATE:
                                String ligne;
                                UserJson userToAdd ;
                                ligne=in.readLine();
                                userToAdd= moteurJson.fromJson(ligne, UserJson.class);
                                if(userList.exist(userToAdd)){
                                    out.write("connected\n");
                                }
                                else{
                                    userList.addUser(userToAdd);
                                    out.write("compte créer\n");
                                }
                                out.flush();break;
                        }
                    }

                } catch (IOException ex) {
                    if (in != null) {
                        try {
                            in.close();
                        } catch (IOException ex1) {
                            LOG.log(Level.SEVERE, ex1.getMessage(), ex1);
                        }
                    }
                    if (out != null) {
                        out.close();
                    }
                    if (clientSocket != null) {
                        try {
                            clientSocket.close();
                        } catch (IOException ex1) {
                            LOG.log(Level.SEVERE, ex1.getMessage(), ex1);
                        }
                    }
                    LOG.log(Level.SEVERE, ex.getMessage(), ex);
                }

            }
        }
    }
}