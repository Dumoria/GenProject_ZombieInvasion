package ClientServer.Json;

import Util.Coord;

public class EnnemyJson extends ClientJson{

    private int idEnnemy;
    private int nbShots;
    private Coord coord;

    public EnnemyJson(int idClient, int idEnnemy, int nbShots, Coord coord) {
        super(idClient, TypePaquet.ENNEMY);
        this.idEnnemy = idEnnemy;
        this.nbShots = nbShots;
        this.coord = coord;
    }

    public int getIdEnnemy() {
        return idEnnemy;
    }

    public void setIdEnnemy(int idEnnemy) {
        this.idEnnemy = idEnnemy;
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

    @Override
    public String toString() {
        return "EnnemyJson{" +
                "idEnnemy=" + idEnnemy +
                ", nbShots=" + nbShots +
                ", coord=" + coord +
                '}';
    }
}
