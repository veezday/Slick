import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

import java.util.ArrayList;
import java.util.Set;

public class Ground extends Entity implements Collision {

    private Direct direct;

    public Ground(Position position, Direct direct) throws SlickException {
        super();
        setSpriteSheet(new SpriteSheet("data/ground2.png",32,32));
        getSpriteSheet().startUse();
        switch (direct) {
            case N -> {this.direct = Direct.N; setTexture(getSpriteSheet().getSubImage(0,0));}
            case NE -> {this.direct = Direct.NE; setTexture(getSpriteSheet().getSubImage(3,1));}
            case E -> {this.direct = Direct.E; setTexture(getSpriteSheet().getSubImage(3,0));}
            case SE -> {this.direct = Direct.SE; setTexture(getSpriteSheet().getSubImage(2,1));}
            case S -> {this.direct = Direct.S; setTexture(getSpriteSheet().getSubImage(1,0));}
            case SW -> {this.direct = Direct.SW; setTexture(getSpriteSheet().getSubImage(1,1));}
            case W -> {this.direct = Direct.W; setTexture(getSpriteSheet().getSubImage(2,0));}
            case NW -> {this.direct = Direct.NW; setTexture(getSpriteSheet().getSubImage(0,1));}
            case NnE -> {this.direct = Direct.NnE; setTexture(getSpriteSheet().getSubImage(3,2));}
            case SnE -> {this.direct = Direct.SnE; setTexture(getSpriteSheet().getSubImage(2,2));}
            case SnW -> {this.direct = Direct.SnW; setTexture(getSpriteSheet().getSubImage(1,2));}
            case NnW -> {this.direct = Direct.NnW; setTexture(getSpriteSheet().getSubImage(0,2));}
        }
        getSpriteSheet().endUse();
        setHeatBox(new Rectangle(position.getX(), position.getY(),getTexture().getWidth()*Settings.scale,getTexture().getHeight()*Settings.scale));
        setPosition(position);
    }

    public ArrayList<Entity> createTo(Position position) throws SlickException {
        ArrayList<Entity> entities = new ArrayList<>();
        float startX = Math.min(this.getPosition().getX(), position.getX());
        float startY = Math.min(this.getPosition().getY(), position.getY());
        float endX = Math.max(this.getPosition().getX(), position.getX());
        float endY = Math.max(this.getPosition().getY(), position.getY());

        for(float j = startY; j <= endY; j = j + Settings.tile) {
            for(float i = startX + Settings.tile; i <= endX; i = i + Settings.tile) {
                entities.add(new Ground(new Position(i,j),this.getDirect()));
            }
        }
        return entities;
    }

    public Direct getDirect() {
        return direct;
    }
    public void setDirect(Direct direct) {
        this.direct = direct;
    }
}