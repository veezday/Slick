import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import java.util.ArrayList;

public class Player extends Entity implements Movable, Gravity, Collision, Friction, CanJump{

    private boolean canJump = false;
    private boolean collide = false;
    private Image standTextureE;
    private Image standTextureW;
    private SpriteSheet walkSheetE;
    private Animation walkAnimationE;
    private Animation walkAnimationW;
    private Direct direct = Direct.E;

    public Player(Position position) throws SlickException {
        setTexture(new Image("data/stickman2.png"));
        standTextureE = getTexture();
        standTextureW = standTextureE.getFlippedCopy(true, false);
        walkSheetE = new SpriteSheet("data/stickmanWalk.png",32,64);
        walkSheetE.startUse();
        walkAnimationE = new Animation(walkSheetE,100);

        ArrayList<Image> list = new ArrayList<Image>();
        for (int j = 0; walkSheetE.getVerticalCount() > j; j++) {
            for (int i = 0; walkSheetE.getHorizontalCount() > i; i++) {
                list.add(walkSheetE.getSubImage(i, j).getFlippedCopy(true, false));
            }
        }
        walkSheetE.endUse();
        Image frames[] = new Image[list.size()];
        for (int i = 0; list.size() > i; i++) {
            frames[i] = list.get(i);
        }
        walkAnimationW = new Animation(frames, 100);

        setHeatBox(new Rectangle(position.getX(), position.getY(), getTexture().getWidth()*Settings.scale, getTexture().getHeight()*Settings.scale));
        setPosition(position);
    }

    public void control(Input input, int delta) {
        if (input.isKeyDown(Input.KEY_A) && getVector().getX() > -Settings.playerMaxRunSpeed) setVector(getVector().add(new Vector2f(-Settings.playerSpeed,0f)));
        if (input.isKeyDown(Input.KEY_D) && getVector().getX() < Settings.playerMaxRunSpeed) setVector(getVector().add(new Vector2f(Settings.playerSpeed,0f)));
        if (input.isKeyPressed(Input.KEY_W) && canJump) {
            setVector(getVector().add(new Vector2f(0.0f,-Settings.playerJumpHeight)));
            setCanJump(false);
        }
        if (input.isKeyDown(Input.KEY_SPACE)) setVector(new Vector2f(0.0f,0.0f));

        walkAnimationE.update(delta);
        walkAnimationW.update(delta);

        if (getPosition().getX() > Settings.width) setPosition(new Position(0.0f,getPosition().getY()));
        if (getPosition().getX() < 0) setPosition(new Position(Settings.width,getPosition().getY()));
        if (getPosition().getY() > Settings.height) setPosition(new Position(getPosition().getX(),0.0f));
        if (getPosition().getY() < 0) setPosition(new Position(getPosition().getX(),Settings.height));
    }

    @Override
    public void draw() {
        if (this.getVector().getX() > 0.0f) {
            walkAnimationE.draw(getPosition().getX(), getPosition().getY(), getTextureScale()*getTexture().getWidth(), getTextureScale()*getTexture().getHeight());
            direct = Direct.E;
        }
        else if (this.getVector().getX() < 0.0f) {
            walkAnimationW.draw(getPosition().getX(), getPosition().getY(), getTextureScale()*getTexture().getWidth(), getTextureScale()*getTexture().getHeight());
            direct = Direct.W;
        }
        else if (direct == Direct.E) {
            standTextureE.draw(getPosition().getX(), getPosition().getY(), getTextureScale());
        }
        else standTextureW.draw(getPosition().getX(), getPosition().getY(), getTextureScale());
    }

    @Override
    public boolean isCanJump() {
        return canJump;
    }

    @Override
    public void setCanJump(boolean canJump) {
        this.canJump = canJump;
    }

    @Override
    public boolean isCollide() {
        return collide;
    }

    @Override
    public void setCollide(boolean collide) {
        this.collide = collide;
    }
}