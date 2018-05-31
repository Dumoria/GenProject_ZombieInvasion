package ClientServer.Json;

import java.util.LinkedList;

public class EnnemisJson {

    //VOIR pour type paquet
    private LinkedList<EnnemyJson> ennemies; //Verif pour double deserialisation si meilleure idée

    public EnnemisJson(LinkedList<EnnemyJson> ennemies) {
        this.ennemies = ennemies;
    }

    public LinkedList<EnnemyJson> getEnnemies() {
        return ennemies;
    }

    public void setEnnemies(LinkedList<EnnemyJson> ennemies) {
        this.ennemies = ennemies;
    }
}
