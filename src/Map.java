
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Shape;

import java.util.ArrayList;

public abstract class Map {

    public Player player;

    public Position startPlayerPos = new Position(0,0);

    public ArrayList<Entity> entities = new ArrayList<>();

    public void update(Input input, int delta) {
        player.control(input, delta);
        Physic.move(entities);
        Physic.afterMove(entities);
        Physic.gravitation(entities);
        Physic.friction(entities);
        //Physic.collision(entities);
    }

    public abstract void drawMap(Graphics g) throws SlickException;

    public void drawCollision(Graphics g) {
        for (Entity entity : entities) {
            if (entity instanceof Collision) {
                g.draw(entity.getHeatBox());
            }
        }
        g.draw(player.getHeatBox());
    }

    public Position getStartPlayerPos() {
        return startPlayerPos;
    }

    public void setStartPlayerPos(Position startPlayerPos) {
        this.startPlayerPos = startPlayerPos;
    }

}
