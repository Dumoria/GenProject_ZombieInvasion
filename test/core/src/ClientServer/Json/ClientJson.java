package ClientServer.Json;

import Util.Coord;

public class ClientJson {

    private int idClient;

    public ClientJson(int idClient) {
        this.idClient = idClient;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    @Override
    public String toString() {
        return "ClientJson{" +
                "idClient=" + idClient +
                '}';
    }
}
