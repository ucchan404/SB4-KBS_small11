package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

/**
 * Interface containing the methods for starting and stopping entities
 *
 */
public interface IGamePluginService {

    /**
     * creates entities and adds them to the game world
     *
     * @param gameData contains general game data; time passed, width of game window, height of game window
     * @param world contains information about the entities present in the game world
     */
    void start(GameData gameData, World world);

    /**
     *removes entities from the game world
     *
     * @param gameData contains general game data; time passed, width of game window, height of game window
     * @param world contains information about the entities present in the game world
     */
    void stop(GameData gameData, World world);
}
