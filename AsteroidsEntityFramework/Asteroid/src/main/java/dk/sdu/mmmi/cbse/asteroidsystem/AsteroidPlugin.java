package dk.sdu.mmmi.cbse.asteroidsystem;

import com.badlogic.gdx.math.MathUtils;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.commonasteroid.Asteroid;
import org.springframework.stereotype.Service;

@Service("Asteroid*Plugin")
public class AsteroidPlugin implements IGamePluginService{

    private Entity asteroid;


    @Override
    public void start(GameData gameData, World world) {
        int numAsteroids = MathUtils.random(1, 5);
        for (int i = 0; i < numAsteroids; i++) {
            asteroid = createAsteroid(gameData);
            world.addEntity(asteroid);
        }
    }

    private Entity createAsteroid(GameData gameData) {

        Entity asteroid = new Asteroid();
        float radians = MathUtils.random()* 2*3.1415f;
        float speed = MathUtils.random() *10f +20f;
        float rotationSpeed = MathUtils.random(-1,1);

        asteroid.setRadius(40);
        asteroid.add(new MovingPart(0, speed, speed, rotationSpeed));
        asteroid.add(new PositionPart(30,30, radians));
        asteroid.add(new LifePart(3));

        return asteroid;


    }

    @Override
    public void stop(GameData gameData, World world) {
        for (Entity enemy : world.getEntities(Asteroid.class)){
           world.removeEntity(asteroid);
       }
    }

}
