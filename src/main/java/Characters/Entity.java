/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Characters;

import AI.Position;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.logging.Level;
import main.Camera;

/**
 *
 * @author xator
 */
public abstract class Entity {

    private int x, y, z;
    private int w, h;
    private String name;
    private int powerlevel;
    private int hp;
    private Image entityImg;
    private boolean hostile=false;

  

    public Entity(int x, int y, int z, int w, int h, String name, int powerlevel, int hp, Image entityImg) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.z = z;
        this.name = name;
        this.powerlevel = powerlevel;
        this.hp = hp;
        this.entityImg = entityImg;
        
        System.out.println(this.toString()+" generated");
    }

    public void render(Graphics2D g, Camera camera) {
        if (camera.getZ() == z) {
            g.setColor((hostile) ? Color.red:Color.cyan);
            g.drawString(this.name+" "+this.x+":"+this.y+":"+this.z, (x) * camera.getZoom() + camera.getX(), (y) * camera.getZoom() + camera.getY());
            g.drawImage(entityImg, (x) * camera.getZoom() + camera.getX(), (y) * camera.getZoom() + camera.getY(), camera.getZoom() * w, camera.getZoom() * h, null);
        }
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

    public void moveX(int x, int z, world.Level l) {
        if (l.getTileType(x, y, z) % 2 == 1) {
            this.x = x;
        }

    }

    @Override
    public String toString() {
        return "Entity{" + "x=" + x + ", y=" + y + ", z=" + z + ", w=" + w + ", h=" + h + ", name=" + name + ", powerlevel=" + powerlevel + ", hp=" + hp + ", hostile=" + hostile + '}';
    }
    

    public void moveY(int y, int z, world.Level l) {
        if (l.getTileType(x, y, z) % 2 == 1) {
            this.y = y;
        }

    }

    public void setY(int y) {
        this.y = y;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getPowerlevel() {
        return powerlevel;
    }

    public void setPowerlevel(int powerlevel) {
        this.powerlevel = powerlevel;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public Image getEntityImg() {
        return entityImg;
    }

    public void setEntityImg(Image entityImg) {
        this.entityImg = entityImg;
    }

    void move(Position p, world.Level level) {
        setX(p.getX());
        setY(p.getY());
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public boolean isHostile() {
        return hostile;
    }

    public void setHostile(boolean hostile) {
        this.hostile = hostile;
    }
    
}
