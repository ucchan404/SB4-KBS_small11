package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;


/**
 *Interface containing the methods for updating an entity at time passed or user input
 *
 */
public interface IEntityProcessingService {

    /**
     *processes an entity in game at time passed or user input
     *
     * @param gameData contains general game data; time passed, width of game window, height of game window
     * @param world contains information about the entities present in the game world
     */
    void process(GameData gameData, World world);
}
