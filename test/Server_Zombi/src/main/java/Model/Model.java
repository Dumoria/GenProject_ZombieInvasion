package Model;

import game.Ennemy;

import java.util.LinkedList;

/*This class contains all the data relativ
to the game that needs to be present on the server
(like zombi's health and positions)
 */
public class Model {

    private LinkedList<Ennemy> ennemis;

    public Model() {
        ennemis = new LinkedList<>();
    }

    public LinkedList<Ennemy> getEnnemis() {
        return ennemis;
    }

    public void setEnnemis(LinkedList<Ennemy> ennemis) {
        this.ennemis = ennemis;
    }
}
