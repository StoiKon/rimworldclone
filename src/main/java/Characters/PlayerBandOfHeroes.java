/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Characters;

import AI.Pathing;
import AI.Position;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Logger;
import main.Camera;
import main.Handler;
import world.Level;
import world.Room;

/**
 *
 * @author xator
 */
public class PlayerBandOfHeroes implements MouseListener {

    private ArrayList<Entity> actors;
    private String name;
    private int mx = 0, my = 0;
    private int mouseButton = 0;
    private ArrayList<Entity> selected;
    private boolean clicked = false;
    private Handler handler;
    private Thread thread = null;
    //movement
    ArrayList<Position> path;

    public PlayerBandOfHeroes(String name, Handler handler) {
        actors = new ArrayList<Entity>();
        selected = new ArrayList<Entity>();
        this.name = name;
        this.handler = handler;
        this.thread = new Thread();
    }

    public void add(Entity ent) {
        actors.add(ent);
    }

    public void render(Graphics2D g, Camera camera) {
        int i = 0;
        int j = 0;

        if (!actors.isEmpty()) {
            for (Entity e : actors) {

                g.setColor((selected.contains(e)) ? Color.red : Color.lightGray);
                g.fillRect(36 + i, 36 + j, 70, 70);
                if (mx > 40 + i && mx < 104 + i && my > 40 + j && my < 104 + j) {
                    g.setColor(Color.yellow);
                    g.fillRect(36 + i, 36 + j, 70, 70);

                    if (this.selected.contains(e) && clicked) {
                        this.selected.remove(e);
                        clicked = false;
                    } else {
                        if (clicked) {
                            this.selected.add(e);
                        }
                        clicked = false;
                    }
                }
                g.drawImage(e.getEntityImg(), 40 + i, 40 + j, 64, 64, null);

                i += 64;
                if (i == 64 * 8) {
                    i = 0;
                    j += 64;
                }
            }
        }
    }

    public void renderEntitiesInLevel(Graphics2D g, Camera camera) {
        if (path != null) {
            for (Position p : path) {
                g.setColor(Color.green);
                //g.fillRect(p.getX() * camera.getZoom() + camera.getX(), p.getY() * camera.getZoom() + camera.getY(), camera.getZoom(), camera.getZoom());
                g.drawRect(p.getX() * camera.getZoom() + camera.getX(), p.getY() * camera.getZoom() + camera.getY(), camera.getZoom(), camera.getZoom());
            }
        }
        if (!this.actors.isEmpty()) {
            for (Entity actor : actors) {
                //g2.setStroke(new BasicStroke(1));

                g.setColor((this.selected.contains(actor)) ? Color.red : Color.green);
                g.drawRect(actor.getX() * camera.getZoom() + camera.getX(), actor.getY() * camera.getZoom() + camera.getY(), camera.getZoom(), camera.getZoom());
                actor.render(g, camera);
            }

        }

    }

    public void positioning(int x, int y) {
        Random rand = new Random();
        if (!this.actors.isEmpty()) {
            for (Entity actor : actors) {
                actor.setX(x);
                actor.setY(y);
            }
        }
    }

    public synchronized void moveSelectedHeroes(int x, int y, int z) {
        Level level = handler.getLevel();
        if (level.getTileType(x, y, z) % 2 == 1) {
            return;
        }

        if (!this.actors.isEmpty()) {
            Runnable r = () -> {
                for (Entity actor : selected) {
                    /*
                int orio=100;
                while (x != actor.getX() || y != actor.getY() && orio>=0) {
                    orio--;
                    System.out.println("in while");
                    for (int i = 0; i < 3; i++) {
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(PlayerBandOfHeroes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                        }
                        if(x != actor.getX() )
                        actor.moveX((actor.getX() > x) ? actor.getX() - 1 : actor.getX() + 1,z,level);
                    }
                    for (int i = 0; i < 3; i++) {
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(PlayerBandOfHeroes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                        }
                        if(y != actor.getY())
                        actor.moveY((actor.getY() > y) ? actor.getY() - 1 : actor.getY() + 1,z,level);
                    }
                }*/

                    Position p = Pathing.path(actor, level, x, y, z);
                    if (p == null) {
                        return;
                    }
                    path = new ArrayList<>();
                    path.add(p);
                    while (p.getPreviousPosition() != null) {
                        path.add(p.getPreviousPosition());
                        p = p.getPreviousPosition();
                    }
                    for (int i = path.size() - 1; i >= 0; i--) {
                        try {
                            Thread.sleep(120);
                            if(i<path.size())
                            actor.move(path.get(i), level);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(PlayerBandOfHeroes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                        }
                    }
                    path.clear();
                    thread = null;

                }
            };

            thread = new Thread(r);
            thread.start();

        }
    }

    @Override
    public void mouseClicked(MouseEvent me) {

    }

    @Override
    public void mousePressed(MouseEvent me) {
        mx = me.getX();
        my = me.getY();
        mouseButton = me.getButton();
        clicked = true;
        if (handler.isInSettlement()) {
            if (mouseButton == 3) {
                System.out.println("rightClick at" + mx + "," + my);
                Camera camera = handler.getCamera();
                int x = (mx - camera.getX()) / camera.getZoom();
                int y = (my - camera.getY()) / camera.getZoom();
                int z = camera.getZ();
                System.out.println("x y z " + x + " - " + y + " - " + " - " + z);
                this.moveSelectedHeroes(x, y, z);
            }

        }
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
}
