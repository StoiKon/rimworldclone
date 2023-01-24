/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package world;

/**
 *
 * @author xator
 */
public class Room {
public int x,y,z,w,h;  

    public Room(int x, int y,  int w, int h,int z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
        this.h = h;
    }
    public boolean collide(Room a){
        return x>=a.x+3 && x<= a.x+a.w-3 && y>=a.y-3 && y<= a.y+a.h+3 && z==a.z; 
    }

    @Override
    public String toString() {
        return "Room{" + "x=" + x + ", y=" + y + ", z=" + z + ", w=" + w + ", h=" + h + '}';
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public int getW() {
        return w;
    }

    public int getH() {
        return h;
    }
    
    

}
