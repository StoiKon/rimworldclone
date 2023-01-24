/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package main;

import Characters.Actor;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.util.Random;
import javax.swing.JFrame;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

/**
 *
 * @author xator
 */
public class RimWorldClone implements Runnable{
private JFrame frame;
private Canvas canvas;
private boolean running=true;
private Handler handler;

    public RimWorldClone() {
        frame=new JFrame();
        frame.setSize(1920,1080);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        canvas = new Canvas();
        canvas.setSize(1920,1080);
        canvas.setFocusable(true);
        
        canvas.setVisible(true);
        frame.add(canvas);
        frame.setTitle("WinterWorld");
        
        
        handler=new Handler(frame,canvas);
        frame.addKeyListener(handler.getCamera());
        canvas.addKeyListener(handler.getCamera());
        
        frame.addMouseListener(handler.getWorld());
        canvas.addMouseListener(handler.getWorld());
        
        frame.addMouseMotionListener(handler.getWorld());
        canvas.addMouseMotionListener(handler.getWorld());
        
        frame.setFocusable(true);
        
        
        frame.setVisible(true);
        
        new Thread(this).start();
    }
    private void render(){
        Toolkit.getDefaultToolkit().sync();
        BufferStrategy bs = canvas.getBufferStrategy();
        if(canvas.getBufferStrategy() ==null){
            canvas.createBufferStrategy(2);
            bs = canvas.getBufferStrategy();
        }
        Graphics2D g = (Graphics2D)bs.getDrawGraphics();
        g.setColor(Color.getColor("fffff"));
        g.fillRect(0, 0, 1920, 1080);
        
        handler.render(g);
        
        bs.show();
        g.dispose();
    }
    private void tick(){}
    
    public static void main(String[] args) {
        new RimWorldClone();
       
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
		final double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		int ticks = 0;
		int frames = 0;
		long timer = System.currentTimeMillis();

		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if (delta >= 1) {
				tick();
				ticks++;
				delta--;
			}
                        
			render();
			frames++;

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println("FPS : " + frames + " Ticks : " + ticks);
				frames = 0;
				ticks = 0;
			}
		}
    }
}
