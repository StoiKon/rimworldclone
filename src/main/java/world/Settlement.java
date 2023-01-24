/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package world;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import main.Camera;

/**
 *
 * @author xator
 */
public class Settlement {
    private Image avatar;
    private int x,y;
    private int w,h;
    private double[][] tiles;
    private String name;
    private Level level;
    

    public Settlement(Image avatar, int x, int y, int w, int h, String name) {
        this.avatar = avatar;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.name = name;
        //(int type, int level, int floors, int preferableNumberOfRooms, int w, int h)
        level = new Level(0,1,3,7,w,h);
    }
    public void renderAvatar(Graphics2D g,Camera camera){
         g.drawImage(avatar, x*camera.getZoom()+camera.getX(),y*camera.getZoom() +camera.getY(),camera.getZoom()*2,camera.getZoom()*2, null);
         g.setColor(Color.red);
         g.setFont(new Font("TimesRoman", Font.PLAIN, 23)); 
         g.drawString(name, x*camera.getZoom()+camera.getX(), y*camera.getZoom()+camera.getZoom()/4+camera.getY());
        
    }
    public void renderLevel(Graphics2D g,Camera camera){
        level.render(g, camera);
    }

    boolean collides(Settlement s) {
       return x == this.getX() && y == this.getY();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Level getLevel() {
        return level;
    }
    
}
