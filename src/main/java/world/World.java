/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package world;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import main.Camera;
import main.Handler;
import ui.Button;
import ui.ButtonAction;

/**
 *
 * @author xator
 */
public class World implements MouseListener, MouseMotionListener {

    private int w, h;
    private int numberOfSettlements;
    private double[][] tiles;
    private Image forest;
    private NoiseGenerator ns;
    private HashMap<String, Settlement> settlements;
    private static Color green = new Color(140, 255, 140);
    private static Color gray = new Color(111, 111, 111);
    private boolean inWorld = true;
    ///mouse
    private int mouseX, mouseY;
    private Handler handler;
    ///--------UI--------------//
    private boolean townMenu=false;
    private Settlement chosenSettlement = null;
    private Button enterSettlement;
    ///--------UI-------------//

    public World(int w, int h, int numberOfSettlements, Handler handler) {
        this.handler = handler;
        loadRes();
        this.w = w;
        this.h = h;
        this.numberOfSettlements = numberOfSettlements;
        tiles = new double[w][h];
        biomeGeneration();
        try {
            settlementGeneration();
            //forestGenerator(0,0,w,h);
        } catch (IOException ex) {
            Logger.getLogger(World.class.getName()).log(Level.SEVERE, null, ex);
        }
        loadUI();

    }

    public Button getEnterSettlement() {
        return enterSettlement;
    }
    
    private void loadUI(){
        enterSettlement=new Button("enterSettlement",1100,120,400,50);
        handler.addMouseListener(enterSettlement);
        enterSettlement.setButtonAction(new ButtonAction(){
            @Override
            public void action() {
                if(chosenSettlement!=null)
                handler.enterLevel(chosenSettlement);
            }
        });
    }

    private void settlementGeneration() throws IOException {
        settlements = new HashMap<String, Settlement>();
        Random rand = new Random();
        Image[] imgs = new Image[2];
        imgs[0] = ImageIO.read(new File("resources/castle.png"));
        imgs[1] = ImageIO.read(new File("resources/camp.jpg"));
        ArrayList<String> names = new ArrayList<String>();
        names.add("Athens");
        names.add("Atlantis");
        names.add("Rome");
        names.add("Spata");
        names.add("London");
        names.add("Eretria");
        names.add("Tokyo");
        names.add("Delhi");
        names.add("Shanghai");
        names.add("São Paulo");
        names.add("Mexico City");
        names.add("Cairo");
        names.add("Mumbai");
        names.add("Beijing");
        names.add("Dhaka");
        names.add("Osaka");
        names.add("New York");
        names.add("Karachi");
        names.add("Buenos Aires");
        names.add("Chongqing");
        names.add("Konstantinople");
        names.add("Kolkata");
        names.add("Manila");
        names.add("Lagos");
        names.add("Rio de Janeiro");
        names.add("Tianjin");
        names.add("Kinshasa");
        names.add("Guangzhou");
        names.add("Los Angeles");
        names.add("Moscow");
        names.add("Shenzhen");
        names.add("Lahore");
        names.add("Bangalore");
        names.add("Paris");
        names.add("Bogotá");

        for (int i = 0; i < numberOfSettlements; i++) {
            Collections.shuffle(names);
            Settlement current = new Settlement(imgs[0], rand.nextInt(w-1), rand.nextInt(h-1),  60,  60, names.get(0));
            if (!settlements.isEmpty()) {
                for (Settlement s : settlements.values()) {
                    if (current.collides(s)) {
                        current = new Settlement(imgs[0], rand.nextInt(w-1), rand.nextInt(h-1),  60,  60, names.get(0));
                    }
                }

            }
            settlements.put(current.getX() + "" + current.getY(), current);
        }
        //road generation
        ArrayList<Settlement> settlements2 = new ArrayList<Settlement>();
        settlements2.addAll(settlements.values());
        for (int i = 0; i < settlements2.size() - 1; i++) {
            int x = settlements2.get(i).getX();
            int y = settlements2.get(i).getY() + 1 % (h);
            int dx = settlements2.get(i + 1).getX();
            int dy = settlements2.get(i + 1).getY() + 1 % (h);

            while (x != dx || y != dy) {

                for (int j = 0; j < 4; j++) {
                    if (x < w && y < h) {
                        tiles[x][y] = 3;
                    }
                    if (x < dx) {
                        x += 1;
                    } else {
                        if (x != dx) {
                            x -= 1;
                        }
                    }
                }
                for (int k = 0; k < 4; k++) {
                    if (x < w && y < h) {
                        tiles[x][y] = 3;
                    }
                    if (y < dy) {
                        y += 1;
                    } else {
                        if (y != dy) {
                            y -= 1;
                        }
                    }
                }
            }

        }

    }

    private void biomeGeneration() {
        ns = new NoiseGenerator();
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                tiles[i][j] = ns.noise(i, j, i) * 4 % 3;
                //System.out.print(" " + (int) tiles[i][j]);
            }
            //System.out.print("\n");
        }
    }
    

    public void setInWorld(boolean inWorld) {
        this.inWorld = inWorld;
       
    }

    private void loadRes() {
        try {

            forest = ImageIO.read(new File("resources/tree1.png"));
        } catch (IOException ex) {
            Logger.getLogger(World.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void forestGenerator(int x, int y, int w, int h) {
        Random rand = new Random();
        for (int i = x; i < x + w; i++) {
            for (int j = y; j < y + h; j++) {
                tiles[i][j] = Math.abs((int) rand.nextGaussian()) % 2;
            }
            System.out.print("\n");
        }
    }

    public void render(Graphics2D g, Camera camera) {

        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                switch (Math.abs((int) tiles[i][j])) {
                    case 0:
                        g.setColor(this.green);
                        g.fillRect(i * camera.getZoom() + camera.getX(), j * camera.getZoom() + camera.getY(), camera.getZoom(), camera.getZoom());
                        break;
                    case 1:
                        //g.setColor(new Color(0,100,0));
                        g.setColor(this.green);
                        g.fillRect(i * camera.getZoom() + camera.getX(), j * camera.getZoom() + camera.getY(), camera.getZoom(), camera.getZoom());
                        g.drawImage(this.forest, i * camera.getZoom() + camera.getX(), j * camera.getZoom() + camera.getY(), camera.getZoom(), camera.getZoom(), null);
                        break;
                    case 2:
                        g.setColor(this.gray);
                        g.fillRect(i * camera.getZoom() + camera.getX(), j * camera.getZoom() + camera.getY(), camera.getZoom(), camera.getZoom());
                        //g.drawImage(this.forest,i*camera.getZoom(),j*camera.getZoom(),camera.getZoom(),camera.getZoom(),null);
                        break;
                    case 3:
                        g.setColor(new Color(98, 69, 40));
                        g.fillRect(i * camera.getZoom() + camera.getX(), j * camera.getZoom() + camera.getY(), camera.getZoom(), camera.getZoom());
                        //g.drawImage(this.forest,i*camera.getZoom(),j*camera.getZoom(),camera.getZoom(),camera.getZoom(),null);
                        break;

                }
            }
        }
        if (!settlements.isEmpty()) {
            for (Settlement s : settlements.values()) {
                s.renderAvatar(g, camera);
            }
        }
        g.setColor(Color.red);
        g.drawString(mouseX + " - " + mouseY, mouseX, mouseY);
        g.drawRect(mouseX - camera.getZoom() / 2, mouseY - camera.getZoom() / 2, camera.getZoom(), camera.getZoom());
        if(townMenu && chosenSettlement!=null){
            g.setColor(Color.lightGray);
            g.fillRect(1000, 100, 600, 900);
            g.setColor(Color.DARK_GRAY);
            g.fillRect(1010, 110, 580, 880);
            enterSettlement.render(g, camera);
        }
    }

    public boolean isInWorld() {
        return inWorld;
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        mouseX = me.getX();
        mouseY = me.getY();
        if (inWorld) {
            Camera camera = handler.getCamera();
            mouseX = (mouseX - camera.getX()) / camera.getZoom();
            mouseY = (mouseY - camera.getY()) / camera.getZoom();
            System.out.println(mouseX + "-" + mouseY);
            //do not close townMenu if in rect
            ///does not work properly
            if(this.townMenu 
                    && mouseX>1000
                    && mouseX<1600
                    && mouseY>100
                    && mouseY<1000){
                
            
            }else{
                this.townMenu=false;
                
            }
            
            for (int i = -1; i < 2; i++) {
                for (int j = -1; j < 2; j++) {
                    if (settlements.get((mouseX +i)+ "" + (mouseY+j)) != null) {
                        System.out.println(settlements.get((mouseX + i) + "" + (mouseY + j)));
                        if(settlements.get((mouseX + i) + "" + (mouseY + j))!=null)
                        chosenSettlement=settlements.get((mouseX + i) + "" + (mouseY + j));
                        townMenu=true;
                        enterSettlement.setActive(true);
                    }
                }
            }

        }
        
    }

    @Override
    public void mousePressed(MouseEvent me) {

    }

    @Override
    public void mouseReleased(MouseEvent me) {

    }

    @Override
    public void mouseEntered(MouseEvent me) {

    }

    @Override
    public void mouseExited(MouseEvent me) {

    }

    @Override
    public void mouseDragged(MouseEvent me) {

    }

    @Override
    public void mouseMoved(MouseEvent me) {
        mouseX = me.getX();
        mouseY = me.getY();
    }
}
