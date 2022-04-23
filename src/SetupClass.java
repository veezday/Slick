import org.newdawn.slick.*;

public class SetupClass extends BasicGame {

    public SetupClass(String title) {
        super(title);
    }

    public SetupClass(String title, boolean fullscreen) {
        super(title);
        this.setFullscreen(fullscreen);
    }

    private boolean fullscreen = false;
    private Map map;


    public void init(GameContainer container) throws SlickException {
        map = new MapField();
    }

    public void update(GameContainer container, int delta) throws SlickException {
        map.update(container.getInput(), delta);
    }

    public void render(GameContainer container, Graphics g) throws SlickException {
        map.drawMap(g);
    }

    public static void main(String[] args) throws SlickException {
        SetupClass setupClass = new SetupClass("Stick KCD");
        AppGameContainer app = new AppGameContainer(setupClass);
        app.setDisplayMode(Settings.width, Settings.height, setupClass.isFullscreen());
        app.setAlwaysRender(true);
        app.setMinimumLogicUpdateInterval(10);
        app.setMaximumLogicUpdateInterval(10);
        app.start();

    }

    public boolean isFullscreen() {
        return fullscreen;
    }

    public void setFullscreen(boolean fullscreen) {
        this.fullscreen = fullscreen;
    }

}
