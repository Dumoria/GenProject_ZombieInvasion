package server;

import com.google.gson.Gson;
import com.mygdx.game.RectangleZombi;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
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
    static LinkedList<MultiThreadedServer.ServantWorker> clients = new LinkedList<>();
    protected int nextIdClient;
    int pos=0;
    static ServerSocket serverSocket;
    static DataOutputStream out;
    static DataInputStream in;
    private Gson moteurJson = new Gson();
    private static UserList userList;

    protected LinkedList<RectangleZombi> ennemis;

    /**
     * Constructor
     *
     * @param port the port to listen on
     */
    public MultiThreadedServer(int port) {
        try {
            serverSocket = new ServerSocket(port);
            //timer = new Timer();
            //un seul timer pour l'ensemble des clients
            //checkStartGame();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        chargeUserList("Server_Zombi/src/main/java/server/Player.json");
        serveClients();
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

        while (true) {

            try {
                if (clients.size() < 4) {
                    Socket clientSocket = serverSocket.accept();
                    out = new DataOutputStream(clientSocket.getOutputStream());
                    in = new DataInputStream(clientSocket.getInputStream());

                    ServantWorker servantWorker = new ServantWorker(in, out, clients,pos);

                    clients.addFirst(servantWorker);      //Add client to the clients list
                    new Thread(servantWorker).start();
                    pos++;
                }

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
    class ServantWorker implements Runnable {
        List<ServantWorker> clients;
        DataOutputStream out;
        DataInputStream in;
        private Boolean done = false;
        private boolean waiting = false;
        int pos ;

        public ServantWorker(DataInputStream in, DataOutputStream out, List<ServantWorker> clients,int pos) {
            this.pos=pos;
            this.in = in;
            this.out = out;
            this.clients = clients;
        }

        @Override
        public void run() {
            String command = "";

            try {
                while (!done) {
                    command = in.readUTF();
                    System.out.print(command);
                    switch (command.toUpperCase()) {
                        case ServerProtocol.CMD_BYE:
                            done = true;
                            break;
                        case ServerProtocol.CMD_CONNECT:
                            String line;
                            UserJson user = new UserJson("", "");
                            while (!userList.exist(user)) {
                                line = in.readUTF();
                                System.out.print(line);
                                user = moteurJson.fromJson(line, UserJson.class);

                                if (userList.exist(user)) {
                                    out.writeUTF("connected");
                                } else {
                                    out.writeUTF("disconnected");
                                }
                                out.flush();
                            }
                            break;
                        case ServerProtocol.CMD_CREATE:
                            String ligne;
                            UserJson userToAdd;
                            ligne = in.readUTF();
                            userToAdd = moteurJson.fromJson(ligne, UserJson.class);
                            if (userList.exist(userToAdd)) {
                                out.writeUTF("connected");
                            } else {
                                userList.addUser(userToAdd);
                                out.writeUTF("compte créer");
                            }
                            out.flush();
                            break;
                    }

                }

            } catch (IOException ex) {
                ex.printStackTrace();
            }


            while (true) {
                if (clients.size() >= 4) {

                    try {
                        String msg = in.readUTF();
                        ServantWorker[] tmp = clients.toArray(new ServantWorker[clients.size()]);
                        for (int i = 0; i < 4; ++i) {
                            if(i!=pos)
                            tmp[i].out.writeUTF(msg);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        }

    }
}

