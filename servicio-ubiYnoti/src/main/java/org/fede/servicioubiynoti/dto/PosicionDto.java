package org.fede.servicioubiynoti.dto;

public class PosicionDto {
    private double x; // Coordenada X
    private double y; // Coordenada Y

    public PosicionDto() {}

    public PosicionDto(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}
