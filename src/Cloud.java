import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Cloud extends Entity{

    public Cloud() throws SlickException {
        setTexture(new Image("data/cloud.png"));
    }
}