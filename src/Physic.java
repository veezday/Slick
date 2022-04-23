import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Vector2f;
import java.util.ArrayList;

abstract class Physic {

    public static void move(ArrayList<Entity> entities) {
        for (Entity entity : entities) {
            if (entity instanceof Movable) {
                entity.setPosition(new Position(entity.getPosition().getX() + entity.getVector().getX(),entity.getPosition().getY() + entity.getVector().getY()));
            }
        }
    }

    public static void gravitation(ArrayList<Entity> entities) {
        for (Entity entity : entities) {
            if (entity instanceof Gravity) {
                entity.setVector(entity.getVector().add(new Vector2f(0f,Settings.gravity)));
            }
        }
    }

    public static void friction(ArrayList<Entity> entities) {

        for (Entity entity : entities) {
            if (entity instanceof Friction) {
                if (((Friction) entity).isCollide()) {
                    if (entity.getVector().getX() >= Settings.frictionForce)
                        entity.setVector(entity.getVector().add(new Vector2f(-Settings.frictionForce, 0.0f)));
                    else if (entity.getVector().getX() <= -Settings.frictionForce)
                        entity.setVector(entity.getVector().add(new Vector2f(Settings.frictionForce, 0.0f)));
                    else if (entity.getVector().getX() < Settings.frictionForce && entity.getVector().getX() > -Settings.frictionForce)
                        entity.setVector(entity.getVector().add(new Vector2f(entity.getVector().negate().getX(), 0.0f)));
                }
            }
        }
    }

    public static void afterMove(ArrayList<Entity> entities) {
        ArrayList<Entity> canJumpEntities = new ArrayList<>();
        ArrayList<Entity> collideEntities = new ArrayList<>();
        ArrayList<Entity> entities1 = new ArrayList<>();

        for (Entity entity : entities) {
            if (entity instanceof Collision) entities1.add(entity);
        }
        //is collide?
        for (int i = 0; i < entities1.size(); i++) {
            for (int j = 0; j < entities1.size(); j++) {
                Entity entity1 = entities1.get(i);
                Entity entity2;
                if (i != j) {
                    entity2 = entities1.get(j);
                } else continue;

                if (entity1.getHeatBox().intersects(entity2.getHeatBox())) {
                    if (whichSide(entity1, entity2) == Direct.N) {
                        if (entity1 instanceof CanJump) canJumpEntities.add(entity1);
                        if (entity2 instanceof CanJump) canJumpEntities.add(entity2);
                        if (entity1 instanceof Friction) collideEntities.add(entity1);
                        if (entity2 instanceof Friction) collideEntities.add(entity2);
                    }
                }
            }
        }
        //false collide
        for (Entity entity : entities1) {

            if (entity instanceof Friction) {
                boolean was = false;
                for (Entity collideEntity : collideEntities) {
                    if (entity == collideEntity) {
                        was = true;
                        break;
                    }
                }
                if (!was) ((Friction)entity).setCollide(false);
            }

            if (entity instanceof CanJump) {
                boolean was = false;
                for (Entity canJumpEntity : canJumpEntities) {
                    if (entity == canJumpEntity) {
                        was = true;
                        break;
                    }
                }
                if (!was) ((CanJump)entity).setCanJump(false);
            }
        }
        //true collide
        for (Entity entity : collideEntities) {
            ((Friction)entity).setCollide(true);
        }

        for (Entity entity : canJumpEntities) {
            ((CanJump)entity).setCanJump(true);
        }
    }

    public static void collision(ArrayList<Entity> entities, Graphics g) {
        //Сбор массива сталкиваемых обьектов

        ArrayList<Entity> entities1 = new ArrayList<>();

        for (Entity entity : entities) {
            if (entity instanceof Collision) entities1.add(entity);
        }

        for (int i = 0; i < entities1.size(); i++) {

            for (int j = 0; j < entities1.size(); j++) {
                Entity entity1 = entities1.get(i);
                Entity entity2;
                if (i != j) {
                    entity2 = entities1.get(j);
                } else continue;

                if (entity1.getHeatBox().intersects(entity2.getHeatBox()))
                {

                    if (!(entity1 instanceof Movable) && !(entity2 instanceof Movable)) break;

                    else if (!(entity2 instanceof Movable))
                    {

                        Direct side = whichSide(entity1, entity2);
                       paramOfIntersection(entity1,entity2,g);

                        switch (side) {
                            case N -> {
                                entity1.setVector(new Vector2f(entity1.getVector().add(new Vector2f(0.0f, entity1.getVector().negate().getY()))));
                                entity1.setPosition(new Position(entity1.getPosition().getX(), entity2.getPosition().getY()-entity1.getHeatBox().getHeight()));
                            }
                            case S -> {
                                entity1.setVector(new Vector2f(entity1.getVector().add(new Vector2f(0.0f, entity1.getVector().negate().getY()))));
                                entity1.setPosition(new Position(entity1.getPosition().getX(), entity2.getPosition().getY()+entity2.getHeatBox().getHeight()+0.0f));
                            }
                            case W -> {
                                entity1.setVector(new Vector2f(entity1.getVector().add(new Vector2f(entity1.getVector().negate().getX(), 0.0f))));
                                entity1.setPosition(new Position(entity2.getPosition().getX()-entity2.getHeatBox().getWidth(), entity1.getPosition().getY()));
                            }
                            case E -> {
                                entity1.setVector(new Vector2f(entity1.getVector().add(new Vector2f(entity1.getVector().negate().getX(), 0.0f))));
                                entity1.setPosition(new Position(entity2.getPosition().getX()+entity2.getHeatBox().getWidth(), entity1.getPosition().getY()));
                            }
                        }
                    }
                }
            }
        }
    }

    private static Direct whichSide(Entity entity1, Entity entity2) {

        Direct direct;

        float simX = Math.min(Math.abs(entity1.getHeatBox().getMaxX() - entity2.getHeatBox().getMaxX()), Math.abs(entity1.getHeatBox().getMinX()-entity2.getHeatBox().getMinX()));
        float simY = Math.min(Math.abs(entity1.getHeatBox().getMaxY() - entity2.getHeatBox().getMaxY()), Math.abs(entity1.getHeatBox().getMinY()-entity2.getHeatBox().getMinY()));

        if(simX <= simY) {
            float difY1 = Math.abs(entity1.getHeatBox().getMaxY()-entity2.getHeatBox().getMinY());
            float difY2 = Math.abs(entity1.getHeatBox().getMinY()-entity2.getHeatBox().getMaxY());

            if (difY1 >= difY2) direct = Direct.S;
            else direct = Direct.N;

        } else {
            float difX1 = Math.abs(entity1.getHeatBox().getMaxX() - entity2.getHeatBox().getMinX());
            float difX2 = Math.abs(entity1.getHeatBox().getMinX() - entity2.getHeatBox().getMaxX());

            if (difX1 >= difX2) direct = Direct.E;
            else direct = Direct.W;
        }
        return direct;
    }

    private static float[] paramOfIntersection(Entity entity1, Entity entity2, Graphics g)
    {
        Line horizon = new Line(0,0,1,0);
        Line line1 = null;
        Line line2 = null;
        Vector2f point = null;
        Vector2f primePoint = null;
        float[] result = new float[4];
        ArrayList<Float> points1 = new ArrayList<Float>();
        ArrayList<Float> points2 = new ArrayList<Float>();
        for (float f : entity1.getHeatBox().getPoints()) points1.add(f);
        points1.add(points1.get(0));
        points1.add(points1.get(1));
        for (float f : entity2.getHeatBox().getPoints()) points2.add(f);
        points2.add(points2.get(0));
        points2.add(points2.get(1));

        for (int i = 3; i < points1.size(); i=i+2)
        {
            for (int j = 3; j < points2.size(); j=j+2)
            {
                float x1 = points1.get(j-3);
                float y1 = points1.get(j-2);
                float x2 = points1.get(j-1);
                float y2 = points1.get(j);

                float x3 = points2.get(i-3);
                float y3 = points2.get(i-2);
                float x4 = points2.get(i-1);
                float y4 = points2.get(i);

                line1 = getSmallerLine(new Line(x1,y1,x2,y2));
                line2 = getSmallerLine(new Line(x3,y3,x4,y4));

                point = line1.intersect(line2, true);
                if (point == null) {
                    Vector2f pS = new Vector2f(-1,-1);
                    Vector2f pE = new Vector2f(-1,-1);
                    Vector2f p1 = new Vector2f(-1,-1);
                    Vector2f p2 = new Vector2f(-1,-1);

                    line1.getClosestPoint(line2.getStart(),p1);
                    System.out.println(line2.distance(p1));
                    if (line2.on(p1)) point = p1;
                }

            if (point != null ) {
                result[0] = point.getX();
                result[1] = point.getY();
                result[2] = getAngle(line1,horizon);
                result[3] = getAngle(line2,horizon);
                g.setColor(Color.red);
                g.draw(line1);
                g.draw(line2);
                g.setColor(Color.white);
                break;
            }
            } if (point != null ) break;
        }
        return result;
    }

    private static float getAngle(Line line1, Line line2)
    {
        float degrees;
        double angle1 = Math.atan2(line1.getY1() - line1.getY2(),
                line1.getX1() - line1.getX2());
        double angle2 = Math.atan2(line2.getY1() - line2.getY2(),
                line2.getX1() - line2.getX2());
        degrees = (float) Math.toDegrees(angle1-angle2);
        if (degrees >= 180.0f) degrees -= 180;
        if (degrees <= -180.0f) degrees += 180;
        return Math.abs(degrees);
    }

    public static Entity rotate(Entity entity, float angle)
    {
        entity.getTexture().rotate(angle);
        float[] points = entity.getHeatBox().getPoints();
        float centerX = entity.getHeatBox().getCenterX();
        float centerY = entity.getHeatBox().getCenterY();
        Polygon polygon = new Polygon();
        for (int i = 1; i < points.length; i=i+2)
        {
            float x = points[i-1];
            float y = points[i];

            float radius =(float)(Math.sqrt(Math.abs(Math.pow(x - centerX,2))+Math.abs(Math.pow(y-centerY,2))));
            float x1 = x+radius*(float)(Math.cos((double)(angle)));
            float y1 = y+radius*(float)(Math.sin((double)(angle)));
            polygon.addPoint(x1,y1);
        }
        entity.setHeatBox(polygon);
        return entity;
    }

    private static Line getSmallerLine(Line l) {
        float points[] = l.getPoints();
        float x1 = points[0];
        float x2 = points[2];
        float y1 = points[1];
        float y2 = points[3];
        float decrease = Settings.playerSpeed+0.1f;
        if (x1<x2) {
            x1 = x1+decrease;
            x2 = x2-decrease;
        } else if (x1>x2) {
            x1 = x1-decrease;
            x2 = x2+decrease;
        }
        if (y1<y2) {
            y1 = y1+decrease;
            y2 = y2-decrease;
        } else if (y1>y2) {
            y1 = y1-decrease;
            y2 = y2+decrease;
        }
        return new Line(x1,y1,x2,y2);
    }
}