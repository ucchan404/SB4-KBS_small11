package dk.sdu.mmmi.cbse.enemysystem;

import com.badlogic.gdx.math.MathUtils;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.commonenemy.Enemy;
import org.springframework.stereotype.Service;

@Service("EnemyPlugin")
public class EnemyPlugin implements IGamePluginService{


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

        Entity enemyShip = new Enemy();
        enemyShip.add(new MovingPart(0, speed, speed, 0));
        enemyShip.add(new PositionPart(x, y, radians));
        enemyShip.add(new LifePart(1));

        return enemyShip;
    }

    @Override
    public void stop(GameData gameData, World world) {
        world.removeEntity(enemy);
    }


}
