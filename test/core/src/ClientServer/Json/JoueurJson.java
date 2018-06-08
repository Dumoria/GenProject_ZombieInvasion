package ClientServer.Json;

import Util.Coord;

public class JoueurJson extends ClientJson {

    private Coord coord;

    public JoueurJson(int idClient, Coord coord) {
        super(idClient, TypePaquet.HERO);
        this.coord = coord;
    }

    public JoueurJson(int idClient, float x, float y){
        super(idClient, TypePaquet.HERO);
        this.coord = new Coord(x, y);
    }

    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }

    @Override
    public String toString() {
        return "JoueurJson{" +
                "coord=" + coord +
                '}';
    }
}
