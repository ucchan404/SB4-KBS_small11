import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.GameKeys;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.playersystem.PlayerControlSystem;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestPlayerMovement {
    @Mock
    Entity entity1;

    @Mock
    GameData gameData;

    @Mock
    World world;
    @Mock
    GameKeys keys;

    @Mock
    MovingPart movingPart;

    @Mock
    PositionPart positionPart;

    @Mock
    LifePart lifePart;


    @Test
    public void testPlayerMovement() throws Exception {
        PlayerControlSystem playerControlSystem = new PlayerControlSystem();

        when(entity1.getPart(MovingPart.class)).thenReturn(movingPart);
        when(entity1.getPart(PositionPart.class)).thenReturn(positionPart);
        when(entity1.getPart(LifePart.class)).thenReturn(lifePart);
        when(world.getEntities(any())).thenReturn(new ArrayList<Entity>(){{
            add(entity1);
        }});
        when(gameData.getKeys()).thenReturn(keys);
        when(entity1.getShapeX()).thenReturn(new float[4]);
        when(entity1.getShapeY()).thenReturn(new float[4]);


        playerControlSystem.process(gameData, world);

        verify(movingPart,times(1)).process(any(), any());
        verify(positionPart, times(1)).process(any(), any());

    }
}
