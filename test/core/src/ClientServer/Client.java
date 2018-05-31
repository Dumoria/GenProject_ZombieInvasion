package ClientServer;

import ClientServer.Json.BonusJson;
import ClientServer.Json.ClientJson;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import game.Hero;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Timer;
import java.util.logging.Logger;


public class Client {

    //----------------Server connection members-------------------
    private static final Logger LOG = Logger.getLogger(Client.class.getName());
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private boolean connected;
    private Gson gson;
    private int logged;

    private String username;
    private String password;

    private Timer timer;
    private int id;

    //----------------Data game members-------------------
    private Hero hero;
    private LinkedList<ClientJson> teamMate;
    private LinkedList<BonusJson> bonuses;


    //---------------Constructor---------------------------------
    //---------------Constructor---------------------------------
    public Client(int id) {
        this.connected = false;
        logged = 0;

        //Prépare le moteur gson
        gson = new GsonBuilder().create();

        this.id = id;
        this.hero = new Hero(id);
        this.timer = new Timer();
    }

    //---------------Methods-------------------------------------


    public boolean createUser(String username, String password)throws  IOException{

        this.username = username;
        this.password = password;
        if(!connected)
            connect(Protocol.DEFAULT_ADDRESS, Protocol.DEFAULT_PORT);
        if(this.username.isEmpty() || this.password.isEmpty()){
            System.out.println("Error: Username and/or Password should not be empty");
            return false;
        }

        String tmp=gson.toJson(new UserJson(this.username,this.password));
        System.out.println(tmp);

        writeServer("create");
        writeServer(tmp);

        String response=in.readLine();
        System.out.println(response);

        if(response.equals("connected")||response.equals("compte créer"))
            return true;

        return false;
    }

    public void consultStats(){

    }

    public boolean loginUser(String username, String password) throws IOException{
        this.username = username;
        this.password = password;

        if(!connected)
            connect(Protocol.DEFAULT_ADDRESS, Protocol.DEFAULT_PORT);
        if(this.username.isEmpty() || this.password.isEmpty()){
            System.out.println("Error: Username and/or Password should not be empty");
        }

        String tmp=gson.toJson(new UserJson(this.username,this.password));
        System.out.println(tmp);
        writeServer("connect");
        writeServer(tmp);

            String response=in.readLine();
            System.out.println(response);
        if(response.equals("connected"))
            return true;

        return false;
    }

    public void connect(String server, int port) throws IOException {
        socket = new Socket(server, port);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        System.out.println(in.readLine());
        out = new PrintWriter(socket.getOutputStream());
        connected = true;
    }

    public void disconnect() throws IOException {

        if(socket != null) {
            socket.close();
        }
        if(in != null) {
            in.close();
        }
        if(out != null) {
            out.close();
        }

        connected = false;
    }

    public boolean isConnected() {
        return connected && (socket != null);

    }

    public String readServer() throws IOException{
        return in.readLine();
    }

    public void writeServer(String str){
        out.write(str + '\n');
        out.flush();
    }


}
