/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import main.Camera;

/**
 *
 * @author xator
 */
public class Button implements MouseListener{
    private int x;
    private int y;
    private int w;
    private int h;
    private boolean active=false;
    private String title;
    private ButtonAction ba;
    public Button(String title,int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.title=title;
    }

    public void setButtonAction(ButtonAction ba) {
        this.ba = ba;
    }
    

    public void setActive(boolean active) {
        this.active = active;
    }
    
    @Override
    public void mouseClicked(MouseEvent me) {
        if(active){
            int mx=me.getX();
            int my=me.getY();
            
            if(mx>x && mx<x+w && my>y && my<y+h){
                ba.action();
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
    public void render(Graphics2D g,Camera camera){
    if(active){
        g.setColor(Color.lightGray);
        g.fillRect(x,y,w,h);
        g.setColor(Color.yellow);
        g.drawString(title,x+w/2 -title.length()*5 ,y+h/2 );
        }
    }
}
