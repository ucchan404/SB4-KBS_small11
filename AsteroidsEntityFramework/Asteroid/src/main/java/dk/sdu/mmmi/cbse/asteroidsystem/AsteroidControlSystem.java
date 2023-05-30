package dk.sdu.mmmi.cbse.asteroidsystem;

import com.badlogic.gdx.math.MathUtils;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.commonasteroid.Asteroid;
import dk.sdu.mmmi.cbse.commonasteroid.IAsteroidSplitter;
import org.springframework.stereotype.Service;

@Service("AsteroidControlSystem")
public class AsteroidControlSystem implements IEntityProcessingService {
    int numPoints = 12;
    private IAsteroidSplitter asteroidSplitter = new AsteroidSplitterImpl();
    @Override
    public void process(GameData gameData, World world) {
        for (Entity asteroid : world.getEntities(Asteroid.class)) {

            PositionPart positionPart = asteroid.getPart(PositionPart.class);
            MovingPart movingPart = asteroid.getPart(MovingPart.class);
            LifePart lifePart = asteroid.getPart(LifePart.class);

            float speed = MathUtils.random() * 10f + 20f;
            if (lifePart.getLife() == 1) {
                numPoints = 8;
                speed = MathUtils.random() * 30f + 70f;
            } else if (lifePart.getLife() == 2) {
                numPoints = 10;
                speed = MathUtils.random() * 10f + 50f;
            }

            movingPart.setMaxSpeed(speed);
            movingPart.setUp(true);

            movingPart.process(gameData, asteroid);
            positionPart.process(gameData, asteroid);


            if (lifePart.isHit()){
                asteroidSplitter.createSplitAsteroid(asteroid, world);
            }

            updateShape(asteroid, numPoints);
        }
    }

    private void updateShape(Entity asteroid, int numPoints) {

        PositionPart positionPart = asteroid.getPart(PositionPart.class);
        LifePart lifePart = asteroid.getPart(LifePart.class);

        float[] shapex = new float[numPoints];
        float[] shapey = new float[numPoints];

        float radians = positionPart.getRadians();
        float x = positionPart.getX();
        float y = positionPart.getY();
        float radius = asteroid.getRadius();

        float angle = 0;
        if (lifePart.getLife() == 3) {
            for (int i = 0; i < numPoints; i++) {
                shapex[i] = x + (float) Math.cos(angle + radians) * 26;
                shapey[i] = y + (float) Math.sin(angle + radians) * 26;
                angle += 2 * 3.1415f / numPoints;
            }
        } else if (lifePart.getLife() == 2) {
            for (int i = 0; i < numPoints; i++) {
                shapex[i] = x + (float) Math.cos(angle + radians) * 16;
                shapey[i] = y + (float) Math.sin(angle + radians) * 16;
                angle += 2 * 3.1415f / numPoints;
            }
        } else if (lifePart.getLife() == 1) {
            for (int i = 0; i < numPoints; i++) {
                shapex[i] = x + (float) Math.cos(angle + radians) * 26;
                shapey[i] = y + (float) Math.sin(angle + radians) * 26;
                angle += 2 * 3.1415f / numPoints;
            }
        }

        asteroid.setShapeX(shapex);
        asteroid.setShapeY(shapey);
    }

    public void setAsteroidSplitter(IAsteroidSplitter asteroidSplitter) {
        this.asteroidSplitter = asteroidSplitter;
    }

    public void removeAsteroidSplitter(IAsteroidSplitter asteroidSplitter) {
        this.asteroidSplitter = null;
    }
}
