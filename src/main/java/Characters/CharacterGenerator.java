/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Characters;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author xator
 */
public class CharacterGenerator {

    private static ArrayList<Image> charAvatars;
    private static ArrayList<Image> lowerChars;
    private static ArrayList<Image> npc;
    private static ArrayList<Image> samurai;
    private static ArrayList<Image> warriors;
    private static final Random random = new Random();

    {
        System.out.println("Character Generator loading");
        lowerChars = new ArrayList<Image>();
        npc = new ArrayList<Image>();
        samurai = new ArrayList<Image>();
        warriors = new ArrayList<Image>();

    }

    public CharacterGenerator() {
        System.out.println("Character Generator resources loading");
        this.charAvatars= loadResources("characters");
        this.lowerChars=loadResources( "lowerChars");
        this.npc=loadResources( "npc");
        this.samurai=loadResources( "samurai");
        this.warriors=loadResources( "warriors");
        System.out.println("Character Generator loaded");
    }

    private ArrayList<Image> loadResources(String folderName) {
        File charFolder = new File("resources/" + folderName);
        System.out.println("resources "+folderName+" loading");
        File[] listFiles = charFolder.listFiles();
         ArrayList<Image>  avatars = new ArrayList<Image>();
        for (File f : listFiles) {
            try {
                System.out.println(f.toPath());
                avatars.add(ImageIO.read(f));
            } catch (IOException ex) {
                Logger.getLogger(Actor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return avatars;
    }

    public Entity genCharacter(int x, int y, int z, String name, int powerlevel, int hp) {
        Collections.shuffle(charAvatars, random);
        return new Entity(x, y, z, 1, 1, name, powerlevel, hp, charAvatars.get(0)) {
        };
    }

    public Entity genSoldier(int x, int y, int z, String name, int powerlevel, int hp) {
        Collections.shuffle(lowerChars, random);
        return new Entity(x, y, z, 1, 1, name, powerlevel, hp, lowerChars.get(0)) {
        };
    }

    public Entity genSamurai(int x, int y, int z, String name, int powerlevel, int hp) {
        Collections.shuffle(samurai, random);
        return new Entity(x, y, z, 1, 1, name, powerlevel, hp, samurai.get(0)) {
        };
    }

    public Entity genWarrior(int x, int y, int z, String name, int powerlevel, int hp) {
        Collections.shuffle(warriors, random);
        return new Entity(x, y, z, 1, 1, name, powerlevel, hp, warriors.get(0)) {
        };
    }
}
