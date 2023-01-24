package world;

import Characters.CharacterGenerator;
import Characters.Entity;
import Characters.PlayerBandOfHeroes;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.imageio.ImageIO;
import main.Camera;

public class Level {

    private int[][][] tiles;
    private int type, level, floors, w, h;
    private int preferableNumberOfRooms;
    private ArrayList<Room> rooms = null;
    private boolean visible;
    private boolean generatedAlready = false;
    private static Image stonef, stonew, grass, stdoor, ststairs;
    private static CharacterGenerator cg;
    private ArrayList<Entity> characters;
    private static final Random random = new Random();
    //will be generated in build rooms

    {
        try {
            if (stonef == null) {
                stonef = ImageIO.read(new File("resources/stonef.jpg"));
                stonew = ImageIO.read(new File("resources/stonew.jpg"));
                grass = ImageIO.read(new File("resources/grass.jpg"));
                stdoor = ImageIO.read(new File("resources/door.png"));
            }
        } catch (IOException ex) {
            System.out.println("Level->Images problem");
        }
        if (cg == null) {
            cg = new CharacterGenerator();
        }
    }

    public Level(int type, int level, int floors, int preferableNumberOfRooms, int w, int h) {

        this.type = type;
        this.level = level;
        this.floors = floors;
        this.w = w;
        this.h = h;
        this.preferableNumberOfRooms = preferableNumberOfRooms;
        tiles = new int[w][h][floors];
        characters = new ArrayList<Entity>();

    }

    public void generate(boolean visible) {
        this.visible = visible;
        if (visible && !generatedAlready) {
            genDungeonLevel();
            generatedAlready = true;
        }
    }

    public Room getRoomByIndex(int index) {
        return rooms.get(index);
    }

//not working properly
    //due to thread times i guess
    public Room getRoomByFloor(int z) {
        if (!rooms.isEmpty()) {
            for (Room room : rooms) {
                if (room.getZ() == z) {
                    return room;

                }
            }
        }
        return null;
    }

    public int getTileByType(int type, int z) {
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                if (tiles[i][j][z] == type) {
                    return tiles[i][j][z];
                }
            }
        }
        return -1;
    }

    public String getTile(int type, int z) {
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                if (tiles[i][j][z] == type) {
                    return i + "-" + j;
                }
            }
        }
        return null;
    }

    public int getFloors() {
        return floors;
    }

    private void genDungeonLevel() {
        System.out.println("generating dungeon level");
        Random rand = new Random();

        System.out.println("executing for " + floors + " floors");
        for (int i = 0; i < floors; i++) {
            //generate level

            boolean collide = false;
            int collisions = 0;
            //generate rooms randomly

            System.out.println("generate rooms randomly");
            rooms = new ArrayList<Room>();
            Room current;
            collide = false;
            while (rooms.size() < this.preferableNumberOfRooms && collisions < 1000) {
                System.out.println("collision " + collisions);
                do {
                    current = new Room(rand.nextInt(w), rand.nextInt(h), rand.nextInt(5) + 4, rand.nextInt(5) + 4, i);
                    collide = !(current.x + current.w < w && current.y + current.h < h);

                    for (Room r : rooms) {
                        if (current.collide(r)) {
                            collide = true;
                            collisions++;
                        }
                    }
                } while (collide);
                if (!collide) {
                    rooms.add(current);
                    collisions = 0;
                }

            }
            //build rooms

            System.out.println("initializing tiles");
            int[][] tiles = new int[w][h];
            for (int k = 0; k < w; k++) {
                for (int j = 0; j < h; j++) {
                    tiles[k][j] = 1;
                    System.out.println("k" + k + "j" + j);

                }
            }

            System.out.println("building rooms " + rooms.size());
            if (rooms != null) {
                for (Room room : rooms) {
                    System.out.println("Room " + room.toString());
                    for (int k = room.x; k < room.x + room.w; k++) {
                        for (int j = room.y; j < room.y + room.h; j++) {
                            tiles[k][j] = 0;

                        }
                    }
                }
            }

            //roads 
            System.out.println("first road set");
            for (Room r : rooms) {
                int x = r.x + r.w / 2;
                int y = r.y + r.h / 2;
                Room target = rooms.get((rooms.indexOf(r) + 1) % rooms.size());
                int x2 = target.x + target.w / 2;
                int y2 = target.y + target.h / 2;
                while (x != x2 || y2 != y) {
                    for (int k = 0; k < 5; k++) {
                        if (x == x2) {
                            break;
                        }
                        if (x > x2) {
                            x--;
                        } else {
                            x++;
                        }

                        tiles[x][y] = 0;
                    }
                    for (int j = 0; j < 5; j++) {

                        if (y == y2) {
                            break;
                        }
                        if (y > y2) {
                            y--;
                        } else {
                            y++;
                        }

                        tiles[x][y] = 0;

                    }
                }
            }
            System.out.println("second road set");
            for (Room r : rooms) {
                int x = r.x + r.w / 2;
                int y = r.y + r.h / 2;
                Room target = rooms.get((rooms.indexOf(r) + rand.nextInt(3)) % rooms.size());
                int x2 = target.x + target.w / 2;
                int y2 = target.y + target.h / 2;
                while (x != x2 || y2 != y) {
                    for (int k = 0; k < 5; k++) {
                        if (x == x2) {
                            break;
                        }
                        if (x > x2) {
                            x--;
                        } else {
                            x++;
                        }

                        tiles[x][y] = 0;
                    }
                    for (int j = 0; j < 5; j++) {

                        if (y == y2) {
                            break;
                        }
                        if (y > y2) {
                            y--;
                        } else {
                            y++;
                        }

                        tiles[x][y] = 0;
                    }

                }
            }
            //stairs and doors
            Room stairs = rooms.get((rand.nextInt(this.preferableNumberOfRooms)) % rooms.size());

            Room doors = rooms.get((rand.nextInt(this.preferableNumberOfRooms)) % rooms.size());

            if (i < this.floors - 1) {
                tiles[stairs.x + stairs.h / 2][stairs.y + stairs.h / 2] = 6;
            }

            while (doors.x + doors.w / 2 + doors.y + doors.h / 2 == stairs.x + stairs.w / 2 + stairs.y + stairs.h / 2) {
                stairs = rooms.get((rand.nextInt(this.preferableNumberOfRooms)) % rooms.size());
            }

            tiles[doors.x + doors.w / 2][doors.y + doors.h / 2] = 4;
            System.out.println("print tiles");
            int f = 0;
            for (int k = 0; k < w; k++) {
                for (int j = 0; j < h; j++) {
                    System.out.print(tiles[k][j]);
                    this.tiles[k][j][i] = (tiles[k][j]);

                }
                System.out.print("\n");
            }
            System.out.println("initializing entities");
            for (int l = 0; l < floors; l++) {
                for (int k = 0; k < w; k++) {
                    for (int j = 0; j < h; j++) {
                        if (random.nextGaussian() > 2.1 && this.tiles[k][j][l] == 0) {
                            System.out.println(random.nextGaussian());
                            characters.add(cg.genSoldier(k, j, l, "soldier", rand.nextInt(50) + 60, 100));
                        }

                    }
                }
            }

        }

        System.out.println("level initialized");
        ///not implemented
        //build roads
        ///not implemented

    }

    public void render(Graphics2D g, Camera camera) {

        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {

                if (camera.getZ() < floors && camera.getZ() >= 0) {
                    switch (tiles[i][j][camera.getZ()]) {
                        case 0:
                            //System.out.println("0 - tile");
                            g.drawImage(stonef, (i) * camera.getZoom() + camera.getX(), (j) * camera.getZoom() + camera.getY(), camera.getZoom(), camera.getZoom(), null);
                            break;
                        case 1:
                            //System.out.println("1 - tile");
                            g.drawImage(stonew, (i) * camera.getZoom() + camera.getX(), (j) * camera.getZoom() + camera.getY(), camera.getZoom(), camera.getZoom(), null);
                            break;
                        case 2:
                            //System.out.println("2 - tile");
                            g.drawImage(grass, (i) * camera.getZoom() + camera.getX(), (j) * camera.getZoom() + camera.getY(), camera.getZoom(), camera.getZoom(), null);
                            break;
                        case 6:
                            //System.out.println("3 - tile");
                            g.drawImage(stdoor, (i) * camera.getZoom() + camera.getX(), (j) * camera.getZoom() + camera.getY(), camera.getZoom(), camera.getZoom(), null);
                            break;
                        case 4:
                            //System.out.println("4 - tile");
                            //g.drawImage(ststairs, rx, ry, size, size, null);
                            break;
                    }
                }

            }
        }
        for (Entity c : characters) {
            c.render(g, camera);
        }

    }

    public int getTileType(int x, int y, int z) {
        if (x < w && y < h && x >= 0 && y >= 0) {
            return tiles[x][y][z];
        }
        return 1;
    }

}
