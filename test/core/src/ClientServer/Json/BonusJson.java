package ClientServer.Json;

import Util.Coord;

public class BonusJson extends ClientJson{

    private int idBonus;
    private Coord coord;

    public BonusJson(int idClient, TypePaquet typePaquet, int idBonus, Coord coord) {
        super(idClient, typePaquet);
        this.idBonus = idBonus;
        this.coord = coord;
    }

    public int getIdBonus() {
        return idBonus;
    }

    public void setIdBonus(int idBonus) {
        this.idBonus = idBonus;
    }

    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }

    @Override
    public String toString() {
        return "BonusJson{" +
                "idBonus=" + idBonus +
                ", coord=" + coord +
                '}';
    }
}
