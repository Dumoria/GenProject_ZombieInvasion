package game;

import Util.Coord;

public class Ennemy {

    private static int nextId;

    private int id;
    private int nbShots;
    private Coord coord;

    public Ennemy() {
        id = (nextId++) * 10 + 2;
        nbShots = 0;
        coord = new Coord();
    }

    public Ennemy(int nbShots, Coord coord) {
        id = (nextId++) * 10 + 2;
        this.nbShots = nbShots;
        this.coord = coord;
    }

    public int getNbShots() {
        return nbShots;
    }

    public int getShot(){
        return ++nbShots;
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

    public int getId() {
        return id;
    }

    public void updatePosition() {

    }
}
