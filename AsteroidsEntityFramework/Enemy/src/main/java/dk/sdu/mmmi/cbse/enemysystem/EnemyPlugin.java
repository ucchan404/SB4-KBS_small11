package dk.sdu.mmmi.cbse.enemysystem;

import com.badlogic.gdx.math.MathUtils;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;

public class EnemyPlugin implements IGamePluginService, IPostEntityProcessingService {


    private Entity enemy;

    public EnemyPlugin() {
    }

    @Override
    public void start(GameData gameData, World world) {
        enemy = createEnemyShip(gameData);
        world.addEntity(enemy);

    }

    private Entity createEnemyShip(GameData gameData) {
        float speed = 100;
        float radians = MathUtils.random(3.1415f);
        float x = MathUtils.random(gameData.getDisplayWidth());
        float y = MathUtils.random(gameData.getDisplayHeight());
        float direction = MathUtils.random(0,1);
//        if (direction < 0.5) {
//            radians = 3.1415f / 3;
//            x = gameData.getDisplayWidth();
//        } else {
//            radians = 3.1415f / 1;
//            x = 0;
//        }

        Entity enemyShip = new Enemy();
        enemyShip.add(new MovingPart(0, speed, speed, 0));
        enemyShip.add(new PositionPart(x, y, radians));
        enemyShip.add(new LifePart(1, 0));

        return enemyShip;
    }

    @Override
    public void stop(GameData gameData, World world) {
        world.removeEntity(enemy);
    }

    @Override
    public void process(GameData gameData, World world) {

    }
}
