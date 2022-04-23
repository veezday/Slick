abstract class Settings {

    //Параметры отображения
    public static int width = 1600;
    public static int height = 1024;
    public static float tileSize = 32.0f;
    public static int widthInTiles = 30;
    public static float scale = width/widthInTiles/tileSize;
    public static float tile = scale*tileSize;

    //Параметры игрока
    public static float EntityMaxSpeed = 100f*scale;
    public static float playerMaxRunSpeed = 2.5f*scale;
    public static float playerSpeed = 0.2f*scale;
    public static float playerJumpHeight = 5.0f*scale;

    //Параметры физики
    public static float gravity = 0.2f*scale; //Гравитация близкая к реальности = 0,25f
    public static float frictionForce = 0.1f*scale;

    //Параметры игры
    public static boolean isEdit = true;
}
