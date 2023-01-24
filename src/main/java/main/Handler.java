/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import Characters.PlayerBandOfHeroes;
import java.awt.Canvas;
import java.awt.Graphics2D;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JFrame;
import ui.ChoosePlayerPanel;
import world.Level;
import world.Settlement;
import world.SettlementControls;
import world.World;

/**
 *
 * @author xator
 */
public class Handler {

    private World world;
    private static Camera camera;
    private JFrame frame;
    private Canvas canvas;
    ///Settlements and stuff
    private Settlement currentSettlement = null;
    private SettlementControls settlementControls;
    private boolean inSettlement = false;
    /// New game menu
    private ChoosePlayerPanel choosePlayerPanel;
    private PlayerBandOfHeroes playerBand;

    public Handler(JFrame frame, Canvas canvas) {
        this.frame = frame;
        this.canvas = canvas;
        world = new World(40, 40, 10, this);
        camera = new Camera();
        settlementControls = new SettlementControls(null, this);

        addMouseListener(settlementControls);
        addKeyListener(settlementControls);
        addMouseMotionListener(settlementControls);

        playerBand = new PlayerBandOfHeroes("Player",this);
        choosePlayerPanel = new ChoosePlayerPanel(playerBand);
        this.choosePlayerPanel.setActive(true);
        this.addMouseListener(choosePlayerPanel.getRollthedice());
        this.addMouseListener(this.playerBand);

    }

    public void render(Graphics2D g) {
        if (world.isInWorld()) {
            world.render(g, camera);
        }
        if (inSettlement) {
            currentSettlement.renderLevel(g, camera);
            this.playerBand.renderEntitiesInLevel(g, camera);
        }
        if (choosePlayerPanel.isActive()) {
            choosePlayerPanel.render(g, camera);
        }
        if (this.playerBand != null) {
            this.playerBand.render(g,camera);
        }
    }

    public Camera getCamera() {
        return camera;
    }

    public World getWorld() {
        return world;
    }

    public void addMouseListener(MouseListener ml) {
        frame.addMouseListener(ml);
        canvas.addMouseListener(ml);
    }

    public void addMouseMotionListener(MouseMotionListener ml) {
        frame.addMouseMotionListener(ml);
        canvas.addMouseMotionListener(ml);
    }

    public void addKeyListener(KeyListener ml) {
        frame.addKeyListener(ml);
        canvas.addKeyListener(ml);
    }

    public void enterLevel(Settlement chosenSettlement) {
        world.setInWorld(false);
        world.getEnterSettlement().setActive(false);
        this.currentSettlement = chosenSettlement;
        this.currentSettlement.getLevel().generate(true);
        settlementControls.setSettlement(this.currentSettlement);
        settlementControls.setEnabled(true);
        

        inSettlement = true;
        System.out.println(Integer.valueOf(currentSettlement.getLevel().getTile(0, 0).split("-")[0])+","+Integer.valueOf(currentSettlement.getLevel().getTile(0, 0).split("-")[1]));
        playerBand.positioning(Integer.parseInt(currentSettlement.getLevel().getTile(0, 0).split("-")[0]),Integer.parseInt(currentSettlement.getLevel().getTile(0, 0).split("-")[1]));
        //setting camera to player not working properly
        camera.setZoom(32);
        camera.setXY(Integer.parseInt(currentSettlement.getLevel().getTile(0, 0).split("-")[0])*-16,Integer.parseInt(currentSettlement.getLevel().getTile(0, 0).split("-")[1])*-16);
    }

    public void enterWorld() {
        world.setInWorld(true);
        world.getEnterSettlement().setActive(true);
        this.currentSettlement = null;
        settlementControls.setEnabled(false);
        settlementControls.setSettlement(null);

        inSettlement = false;
    }

    public boolean isInSettlement() {
        return inSettlement;
    }

    public void setInSettlement(boolean inSettlement) {
        this.inSettlement = inSettlement;
    }

    public Level getLevel() {
        return this.currentSettlement.getLevel();
    }

}
