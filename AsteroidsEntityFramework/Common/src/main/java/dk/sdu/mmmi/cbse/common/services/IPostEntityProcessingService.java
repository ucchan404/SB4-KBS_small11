package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

/**
 *Interface containing the methods for actions taking place after an entity has been updated
 * @author
 */
public interface IPostEntityProcessingService  {
        /**
         * processes the changes that needs to take place after an entity has been updated
         *
         * @param gameData contains general game data; time passed, width of game window, height of game window
         * @param world contains information about the entities present in the game world
         */
        void process(GameData gameData, World world);
}
