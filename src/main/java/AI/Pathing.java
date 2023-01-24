/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package AI;

import Characters.Entity;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import world.Level;

/**
 *
 * @author xator
 */
public class Pathing {

    static public Position path(Entity en, Level level, int x, int y, int z) {
        if (level.getTileType(x, y, z) % 2 == 1) {
            return null;
        }
        ArrayList<Position> closed = new ArrayList<Position>();
        ArrayDeque<Position> fringe = new ArrayDeque<Position>();
        fringe.add(new Position(null, en.getX(), en.getY(), z));
        while (true) {
            if (fringe.isEmpty()) {
                return null;
            }
            System.out.println(closed);
            Object[] array =  fringe.toArray();
            Arrays.sort(array,new Comparator(){
                @Override
                public int compare(Object t, Object t1) {
                    if(hx((Position) t,x,y)>hx((Position)t,x,y)){
                     return -1;
                    }else{
                        return 1;
                    }
                }
            });
            fringe.clear();
            for (Object ap : array) {
                if(!closed.contains((Position)ap))
                fringe.add((Position)ap);
            }
            System.out.println(fringe);

            Position node = fringe.removeFirst();

            System.out.println("current node " + node);

            System.out.println(node);

            if (node.equals(new Position(null, x, y, z))) {
               
                System.out.println("solution  "+node);
                return node;
            }
            if (!closed.contains(node)) {
                closed.add(node);
            }
            expand(fringe, node, level, closed);
        }

    }

    static private void expand(ArrayDeque<Position> fringe, Position p, Level level, ArrayList<Position> closed) {
        System.out.println("expanding " + p);
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (level.getTileType(p.getX() + i, p.getY() + j, p.getZ()) % 2 == 0) {
                    boolean existsAlready = false;
                    for (Position p2 : closed) {
                        if (p.equals(new Position(p, p.getX() + i, p.getY() + j, p.getZ()))) {
                            existsAlready = true;
                        }
                    }
                    if (!existsAlready && !p.equals(new Position(p, p.getX() + i, p.getY() + j, p.getZ()))) {
                        fringe.add(new Position(p, p.getX() + i, p.getY() + j, p.getZ()));
                    }
                }
            }
        }

    }
    static private int hx(Position p,int x,int y){
        return Math.min(Math.abs(p.getX()-y), Math.abs(p.getY()-y));
    }
}
