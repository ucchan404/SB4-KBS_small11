package dk.sdu.mmmi.cbse.enemysystem;

import com.badlogic.gdx.math.MathUtils;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.commonbullet.BulletSPI;

public class EnemyControlSystem implements IEntityProcessingService {
    private BulletSPI bulletService;


    @Override
    public void process(GameData gameData, World world) {
        for (Entity enemy : world.getEntities(Enemy.class)) {
            PositionPart positionPart = enemy.getPart(PositionPart.class);
            MovingPart movingPart = enemy.getPart(MovingPart.class);
            LifePart lifePart = enemy.getPart(LifePart.class);

            int random = MathUtils.random(1, 3);
            if (random == 1) {
                movingPart.setUp(true);
            } else if (random == 2) {
                movingPart.setRight(true);
            } else if (random == 3) {
                movingPart.setRight(true);
            }
            if (bulletService != null) {
                Entity bullet = bulletService.createBullet(enemy, gameData);
                world.addEntity(bullet);
            }

            movingPart.process(gameData, enemy);
            positionPart.process(gameData, enemy);
            lifePart.process(gameData, enemy);

            updateShape(enemy);

            movingPart.setLeft(false);
            movingPart.setUp(false);
            movingPart.setRight(false);

        }
    }


    private void updateShape(Entity enemy) {
        PositionPart positionPart = enemy.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();
        float radians = positionPart.getRadians();

        float[] shapex = new float[6];
        float[] shapey = new float[6];

        shapex[0] = x - 10;
        shapey[0] = y;

        shapex[1] = x - 3;
        shapey[1] = y - 5;

        shapex[2] = x + 3;
        shapey[2] = y - 5;

        shapex[3] = x + 10;
        shapey[3] = y;

        shapex[4] = x + 3;
        shapey[4] = y + 5;

        shapex[5] = x - 3;
        shapey[5] = y + 5;

        enemy.setShapeX(shapex);
        enemy.setShapeY(shapey);
    }


}
