package dk.sdu.mmmi.cbse.collisionsystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;

public class CollisionDetector implements IPostEntityProcessingService {
    @Override
    public void process(GameData gameData, World world) {
        for (Entity entity : world.getEntities()) {
            for (Entity collisioDetection : world.getEntities()) {
                LifePart entityLife = entity.getPart(LifePart.class);

                if (entity.getID().equals(collisioDetection.getID())){
                    continue;
                }

                if (this.collides(entity, collisioDetection)){
                    if (entityLife.getLife() > 0){
                        entityLife.setLife(entityLife.getLife() -1);
                        entityLife.setIsHit(true);
                    } if (entityLife.getLife()<=0){
                        world.removeEntity(entity);
                    }
                }
            }
        }
    }

    public Boolean collides(Entity entity1, Entity entity2){
        PositionPart enPos1 = entity1.getPart(PositionPart.class);
        PositionPart enPos2 = entity2.getPart(PositionPart.class);

        float dx = enPos1.getX() - enPos2.getX();
        float dy = enPos1.getY() - enPos2.getY();

        float distance = (float) Math.sqrt(dx * dx + dy * dy);
        if (distance < entity1.getRadius() + entity2.getRadius()){
            return true;
        }
        return false;
    }
}
