package org.fede.servicioubiynoti.services;

public class ZonaRestringida {

    private Coordenada noroeste;
    private Coordenada sureste;

    public Coordenada getNoroeste() {
        return noroeste;
    }

    public void setNoroeste(Coordenada noroeste) {
        this.noroeste = noroeste;
    }

    public Coordenada getSureste() {
        return sureste;
    }

    public void setSureste(Coordenada sureste) {
        this.sureste = sureste;
    }
}
