/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui;

import Characters.Actor;
import Characters.PlayerBandOfHeroes;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Collections;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.Camera;

/**
 *
 * @author xator
 */
public class ChoosePlayerPanel {

    private boolean active = false;
    private Button rollthedice;
    private Actor actor = null;

    public ChoosePlayerPanel(PlayerBandOfHeroes currentBand) {
        rollthedice = new Button("random Player", 220, 120, 350, 50);
        rollthedice.setActive(active);

        rollthedice.setButtonAction(new ButtonAction() {
            @Override
            public void action() {
                Random rand = new Random();
                int index = rand.nextInt(1000);
                String[] randomNames = {
                    "Okarot",
                    "Kolard",
                    "Bage",
                    "Calaba",
                    "Kail",
                    "Lottus",
                    "Endife",
                    "Matoto",
                    "Onnio",
                    "Aubergi"
                };
                int name= rand.nextInt(10);
                actor = new Actor(6, 6,0, 10, 10, randomNames[name], 100, 100, index);
                currentBand.add(new Actor(0, 0,0, 1, 1, randomNames[name], 100, 100, index));

                Runnable mythread = () -> {
                    try {
                        Thread.sleep(1700);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(ChoosePlayerPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    setActive(false);
                };
                mythread.run();

            }
        });
    }

    public void render(Graphics2D g, Camera camera) {
        if (active) {
            g.setColor(Color.lightGray);
            g.fillRect(100, 100, 600, 900);
            g.setColor(Color.gray);
            g.fillRect(110, 110, 580, 880);
            rollthedice.render(g, camera);
            if (actor != null) {
                actor.render(g, camera);
            }
            g.drawString("This is your Player", 220, 720);
        }
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
        rollthedice.setActive(active);
    }

    public Button getRollthedice() {
        return rollthedice;
    }

}
