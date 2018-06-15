package server;

import ClientServer.Json.*;
import com.google.gson.*;
import com.mygdx.game.RectangleZombi;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
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
    static LinkedList<MultiThreadedServer.ServantWorker> clients=new LinkedList<>();
    protected int nextIdClient;
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
/*

    public void checkStartGame(){ //deja bloquant, pas besoin de timer, enfin, besoin que de un seul bloquage
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try{
                    if(!clients.isEmpty()){ //atta atta, tant que empty check toute les sec mais bloquant dés que client sur read
                        System.out.println(clients.getFirst().readServer());
                    }
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }, 0, 1000);
    }

    public void startGame(){
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try{
                    manageTraffic();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }, 0, 300);
    }

    public TypePaquet getResponseClass(String str){
        ClientJson tmp = moteurJson.fromJson(str, ClientJson.class);
        return tmp.getTypePaquet();
    }

    public ClientJson parseResponse(String str){
        ClientJson tmp = moteurJson.fromJson(str, ClientJson.class);

        switch (tmp.getTypePaquet()){
            case CLIENT:
                return tmp;
            case HERO:
                return moteurJson.fromJson(str, JoueurJson.class);
            case ENNEMY:
                return moteurJson.fromJson(str, EnnemyJson.class);
            case BONUS:
                return moteurJson.fromJson(str, BonusJson.class);
            default:
                return null;
        }
    }
/*

    public void sendDataToOtherClients(int id, String data){
        int count = 0;
        for(ReceptionistWorker.ServantWorker worker : clients){
            if(count++ != id)
                worker.writeServer(data);
        }
    }

    public void broadcast(String data){
        for(ReceptionistWorker.ServantWorker worker : clients){
            worker.writeServer(data);
        }
    }*/
/*

    public void manageTraffic() throws IOException{

        //Deal with the input received from each client
        for(ReceptionistWorker.ServantWorker worker : clients){

            //Read input
            String clientData = worker.readServer();
            ClientJson clientJson = parseResponse(clientData);
            int id = clientJson.getIdClient();

            switch (clientJson.getTypePaquet()){
                case CLIENT:    //Pour deconnexion
                    break;
                case HERO:
                    sendDataToOtherClients(id, clientData);
                    break;
                case ENNEMY:    //Creation du buffer
                    sendDataToOtherClients(id, clientData);
                    break;
                case BONUS:
                    sendDataToOtherClients(id, clientData);
                    break;
                default:
                    break;
            }

        }


        //Send new zombis position
        broadcast(moteurJson.toJson(ennemis));
    }*/

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
                Socket clientSocket = serverSocket.accept();
                out = new DataOutputStream(clientSocket.getOutputStream());
                in = new DataInputStream(clientSocket.getInputStream());
                clients = new LinkedList<>();


                ServantWorker servantWorker = new ServantWorker(in, out, clients);

                clients.add(servantWorker);      //Add client to the clients list

                new Thread(servantWorker).start();

            } catch (IOException ex) {
                Logger.getLogger(MultiThreadedServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    /*
            récup string, lire premier champ avec id, puis déserialiser en fonction
                ------Paquet de transit------
                Paquet hero:
                    -position
                    -id

                Paquet mort:
                    -id joueur/zombi
                    -position pour ev animation

                Paquet bonus: (signal prise bonus)
                    -id bonus
                    -bool apparuDiparu


                --------Calcul serveur--------
                Paquet tire: (signal zombi touché)
                    -id zombi

                Paquet zombis:
                    -Contient l'ensemble des zombi position


            Calcule sur serveur:
                Buffer de requête. Toutes les dix requêtes, va vider
                le buffer.

                Trois tirs sur un zombi le tue
             */

    /*
    Une fonction qui envoie aux autres joueurs
    Une fonction qui lit d'un joueur
    Une fonction/debut de la princi qui gere le type de jsonString (voir protocol pour id puis type, ev type dans id)

    les idees sont gere par le serveur, lui seul en a besoin niveau client
*/

/*
ublic class MultiThreadedServer {

    private final static Logger LOG = Logger.getLogger(MultiThreadedServer.class.getName());
    private Gson moteurJson = new Gson();
    private int port;

    private static UserList userList;
    private LinkedList<ReceptionistWorker.ServantWorker> clients;
    private LinkedList<Ennemy> ennemis;
    private Timer timer;

    /**
     * Constructor
     *
     * @param port the port to listen on

    public MultiThreadedServer(int port) {
        this.port = port;
        chargeUserList("Server_Zombi/src/main/java/server/Player.json");
        clients = new LinkedList<>();
        ennemis = new LinkedList<>();
        timer = new Timer();
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
            /*
            récup string, lire premier champ avec id, puis déserialiser en fonction
                ------Paquet de transit------
                Paquet hero:
                    -position
                    -id

                Paquet mort:
                    -id joueur/zombi
                    -position pour ev animation

                Paquet bonus: (signal prise bonus)
                    -id bonus
                    -bool apparuDiparu


                --------Calcul serveur--------
                Paquet tire: (signal zombi touché)
                    -id zombi

                Paquet zombis:
                    -Contient l'ensemble des zombi position


            Calcule sur serveur:
                Buffer de requête. Toutes les dix requêtes, va vider
                le buffer.

                Trois tirs sur un zombi le tue
             */

    /*
    Une fonction qui envoie aux autres joueurs
    Une fonction qui lit d'un joueur
    Une fonction/debut de la princi qui gere le type de jsonString (voir protocol pour id puis type, ev type dans id)

    les idees sont gere par le serveur, lui seul en a besoin niveau client

    public void startGame(){
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try{
                    manageTraffic();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }, 0, 300);
    }

    public ClientJson parseResponse(String str){ //verif

        switch (getClass(str)){
            case 1:
                return moteurJson.fromJson(str, JoueurJson.class);
            case 2:
                return moteurJson.fromJson(str, EnnemyJson.class);
            case 3:
                return moteurJson.fromJson(str, BonusJson.class);
            default:
                break;
        }
        return null;
    }

    public int getClass(String str){
        int count = 3;                                  //verif
        while(Character.isDigit(str.charAt(count++)));
        return str.charAt(--count);
    }

    public void manageTraffic() throws IOException{

        //Deal with the output from each client
        for(ReceptionistWorker.ServantWorker worker : clients){

            //Read output
            String clientData = worker.readServer();
            ClientJson clientJson = parseResponse(clientData);
            int id = clientJson.getIdClient();

            //Take the adequate measure
            switch (clientJson.getIdClient() % 10){

                //Hero class => move (transit information) or death
                case 1:
                    sendDataToOtherClients(id, clientData);
                    break;

                //Enemy class => shot or appears
                case 2:
                    break;

                //Bonus class => appears or disapears
                case 3:
                    sendDataToOtherClients(id, clientData);
                    break;

                default:
                    break;
            }
        }

        //Zombis position update
        for(Ennemy ennemy : ennemis){
            ennemy.updatePosition();
        }

        //Send new zombis position
        broadcast(moteurJson.toJson(ennemis));
    }

    public void sendDataToOtherClients(int id, String data){
        int count = 0;
        for(ReceptionistWorker.ServantWorker worker : clients){
            if(count++ != id)
                worker.writeServer(data);
        }
    }

    public void broadcast(String data){
        for(ReceptionistWorker.ServantWorker worker : clients){
            worker.writeServer(data);
        }
    }

    public void handleShots(int idZombi){
        for(Ennemy ennemy : ennemis){
            if(idZombi == ennemy.getId()) {

                //Shoot zombi and check if dead
                if(ennemy.getShot() == 3)
                    ennemis.remove(ennemy); //Eventuellement transmettre alors deathNotification
                return;
            }
        }
    }

    /**
     * This method initiates the process. The server creates a socket and binds it
     * to the previously specified port. It then waits for clients in a infinite
     * loop. When a client arrives, the server will read its input line by line
     * and send back the data converted to uppercase. This will continue until the
     * client sends the "BYE" command.

    public void serveClients() {
        LOG.info("Starting the Receptionist Worker on a new thread...");
        new Thread(new ReceptionistWorker()).start();
    }

    /**
     * This inner class implements the behavior of the "receptionist", whose
     * responsibility is to listen for incoming connection requests. As soon as a
     * new client has arrived, the receptionist delegates the processing to a
     * "servant" who will execute on its own thread.

    private class ReceptionistWorker implements Runnable {

        ServerSocket serverSocket;

        @Override
        public void run() {
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

                    ServantWorker servantWorker = new ServantWorker(clientSocket);
                    clients.add(servantWorker);      //Add client to the clients list

                    new Thread(servantWorker).start();
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

            public String readServer() throws IOException{
                return in.readLine();
            }

            public void writeServer(String data){
                out.write(data);
                out.flush();
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
*/

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

        public ServantWorker(DataInputStream in, DataOutputStream out, List<ServantWorker> clients) {
            this.in = in;
            this.out = out;
            this.clients = clients;
        }

        /*

        public String readServer() throws IOException{
            return in.readLine();
        }

        public void writeServer(String data){
            out.write(data);
            out.flush();
        }
    */
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
                                out.writeUTF("connected\n");
                            } else {
                                userList.addUser(userToAdd);
                                out.writeUTF("compte créer\n");
                            }
                            out.flush();
                            break;
                    }

                }

            } catch (IOException ex) {
                ex.printStackTrace();
            }


            while (true) {
                if (clients.size() == 4) {
                    try {
                        String msg = in.readUTF();
                        for (ServantWorker s : clients) {
                            s.out.writeUTF(msg);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
                /*
                String command = "";

                out.println("Welcome to the Multi-Threaded Server. Send me text lines and conclude with the BYE command. Your id is:");
                out.flush();

                out.println(++nextIdClient);
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
                                out.flush();
                                break;
                        }

                        try {
                            //System.out.println(clients.getFirst().readServer());
                            manageTraffic();
                        }catch(IOException e){

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
    }*/

        }
    }
}
