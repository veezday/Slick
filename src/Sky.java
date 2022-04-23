import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Sky extends Entity{

    public Sky() throws SlickException {
        setTexture(new Image("data/sky.png"));
    }

    @Override
    public void draw() {
        getTexture().draw(0,0,Settings.width,Settings.height);
    }
}