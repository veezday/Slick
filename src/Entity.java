import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.*;

public abstract class Entity {

    private Position position;
    private Shape heatBox;
    private Image texture = null;
    private float textureScale = 1.0f;
    private SpriteSheet spriteSheet = null;
    public Vector2f vector = new Vector2f(0f,0f);

    public void draw() {
        texture.draw(position.getX(), position.getY(), getTextureScale());
    }

    public Entity() {
        textureScale = Settings.scale;
    }

    public Entity(Image texture) {
        this.texture = texture;
        textureScale = Settings.scale;
    }

    public void setPosition(Position position) {
        this.position = position;
        heatBox.setX(position.getX());
        heatBox.setY(position.getY());
    }
    public Position getPosition() {
        return this.position;
    }

    public Image getTexture() {
        return texture;
    }
    public void setTexture(Image texture) {
        this.texture = texture;
    }

    public float getTextureScale() {
        return textureScale;
    }
    public void setTextureScale(float textureScale) {
        this.textureScale = textureScale;
    }

    public SpriteSheet getSpriteSheet() {
        return spriteSheet;
    }
    public void setSpriteSheet(SpriteSheet spriteSheet) {
        this.spriteSheet = spriteSheet;
    }

    public Shape getHeatBox() {
        return heatBox;
    }
    public void setHeatBox(Shape heatBox) {
        this.heatBox = heatBox;
    }

    public Vector2f getVector() {
        return vector;
    }
    public void setVector(Vector2f vector) {
        if (this instanceof  Movable) {
            if (this.vector.getX()<Settings.EntityMaxSpeed) {
                if (this.vector.getY()<Settings.EntityMaxSpeed) {
                    this.vector = vector;
                } else {
                    this.vector.x = vector.x;
                }
            } else if (this.vector.getY()<Settings.EntityMaxSpeed) {
                this.vector.y = vector.y;
            }
        }
    }

}