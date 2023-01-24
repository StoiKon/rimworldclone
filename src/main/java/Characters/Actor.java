/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Characters;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author xator
 */
public class Actor extends Entity{
    private static ArrayList<Image> avatars;
    {
        File charFolder=new File("resources/characters");
        File[] listFiles = charFolder.listFiles();
        avatars=new ArrayList<Image>();
        for(File f : listFiles){
            try {
                avatars.add(ImageIO.read(f));
            } catch (IOException ex) {
                Logger.getLogger(Actor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }

    public Actor(int x, int y,int z, int w, int h, String name, int powerlevel, int hp,int imageIndex) {
        super(x, y,z, w, h, name, powerlevel, hp, null);
        File charFolder=new File("resources/characters");
        File[] listFiles = charFolder.listFiles();
        avatars=new ArrayList<Image>();
        for(File f : listFiles){
            try {
                avatars.add(ImageIO.read(f));
            } catch (IOException ex) {
                Logger.getLogger(Actor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.setEntityImg(avatars.get(imageIndex%avatars.size()));
    }
    
}
