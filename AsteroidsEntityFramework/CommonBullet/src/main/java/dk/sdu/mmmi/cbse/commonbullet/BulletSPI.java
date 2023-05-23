package dk.sdu.mmmi.cbse.commonbullet;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;

public interface BulletSPI {
    Entity createBullet(Entity entity, GameData gameData);
}
