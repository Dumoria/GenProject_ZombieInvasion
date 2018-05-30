package ClientServer.Json;

import Util.Coord;

public class EnnemyJson extends ClientJson{

    private int id;
    private int nbShots;
    private Coord coord;

    public EnnemyJson(int idClient, int id, int nbShots, Coord coord) {
        super(idClient);
        this.id = id;
        this.nbShots = nbShots;
        this.coord = coord;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNbShots() {
        return nbShots;
    }

    public void setNbShots(int nbShots) {
        this.nbShots = nbShots;
    }

    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }
}
