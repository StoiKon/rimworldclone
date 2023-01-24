/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package AI;

/**
 *
 * @author xator
 */
public class Position implements Comparable {

    private Position previousPosition;
    private int x, y, z;

    public Position(Position previousPosition, int x, int y, int z) {
        this.previousPosition = previousPosition;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public boolean equals(Position p) {
        return p.x == this.x && p.y == this.y && p.z == this.z;
    }
    @Override
    public boolean equals(Object p) {
        Position p1=(Position) p;
        return p1.x == x && p1.y == y && p1.z == z;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public Position getPreviousPosition() {
        return previousPosition;
    }

    @Override
    public String toString() {
        return "Position{ x=" + x + ", y=" + y + ", z=" + z + '}';
    }

    @Override
    public int compareTo(Object t) {
        Position p=(Position)t;
        if (this.equals(p)) {
            return 0;
        } else if (p.getY()<this.getY()) {
            return 1;
        } else {
            return -1;
        }

    }

}
