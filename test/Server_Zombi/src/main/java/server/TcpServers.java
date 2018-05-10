package server;

public class TcpServers {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.setProperty("java.util.logging.SimpleFormatter.format", "%5$s %n");

        MultiThreadedServer multi = new MultiThreadedServer(2323);
        multi.serveClients();
    }

}