package ClientServer.Json;

import Util.Coord;




public class ClientJson {

    private int idClient;
    private TypePaquet typePaquet;

    public ClientJson(int idClient, TypePaquet typePaquet) {
        this.idClient = idClient;
        this.typePaquet = typePaquet;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public TypePaquet getTypePaquet() {
        return typePaquet;
    }

    public void setTypePaquet(TypePaquet typePaquet) {
        this.typePaquet = typePaquet;
    }

    @Override
    public String toString() {
        return "ClientJson{" +
                "idClient=" + idClient +
                ", typePaquet=" + typePaquet +
                '}';
    }
}
