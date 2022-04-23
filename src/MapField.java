import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Shape;

public class MapField extends Map{

    public void drawMap(Graphics g) {
        player.draw();
        for (Entity entity : entities) entity.draw();
        drawCollision(g);
        Physic.collision(entities,g);
    }

    public MapField() throws SlickException {
        entities.add(new Sky());

        startPlayerPos.setPos(192*Settings.scale,320*Settings.scale);
        player = new Player(startPlayerPos);
        //Physic.rotate(player, 45);
        entities.add(player);

        Ground ground = new Ground(new Position(10*Settings.scale, 500*Settings.scale), Direct.N);
        entities.add(new Ground(new Position(180*Settings.scale, 380*Settings.scale), Direct.NW));
        entities.add(new Ground(new Position(250*Settings.scale,460*Settings.scale), Direct.NE));
        entities.add(ground);
        entities.addAll(ground.createTo(new Position(800*Settings.scale, 500*Settings.scale)));
        entities.addAll(ground.createTo(new Position(68*Settings.scale,215*Settings.scale)));
    }
}
