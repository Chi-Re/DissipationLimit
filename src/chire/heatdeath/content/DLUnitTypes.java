package chire.heatdeath.content;

import chire.heatdeath.type.ValkyrieUnitType;
import chire.heatdeath.type.entity.ValkyrieUnitEntity;
import chire.heatdeath.util.EntityRegistry;
import mindustry.gen.Sounds;
import mindustry.gen.UnitEntity;
import mindustry.type.UnitType;

public class DLUnitTypes {
    public static UnitType valkyrie;

    public static void load() {
        valkyrie = EntityRegistry.content("valkyrie", ValkyrieUnitEntity::create, name -> new ValkyrieUnitType(name){{
            researchCostMultiplier = 0.5f;
            speed = 2.7f;
            accel = 0.08f;
            drag = 0.04f;
            flying = true;
            health = 70;
            engineOffset = 5.75f;
            hitSize = 9;
            itemCapacity = 10;
            circleTarget = true;
            omniMovement = true;
            rotateSpeed = 5f;
            circleTargetRadius = 60f;
            wreckSoundVolume = 0.7f;

            moveSound = Sounds.loopThruster;
            moveSoundPitchMin = 0.3f;
            moveSoundPitchMax = 1.5f;
            moveSoundVolume = 0.2f;
        }});
    }
}
