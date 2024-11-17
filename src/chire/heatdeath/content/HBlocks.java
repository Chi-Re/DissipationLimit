package chire.heatdeath.content;

import chire.heatdeath.world.blocks.campaign.UnitLaunchPad;
import mindustry.content.Items;
import mindustry.type.Category;
import mindustry.world.Block;

import static mindustry.type.ItemStack.with;

public class HBlocks {
    public static Block unitLaunchPad;

    public static void load(){
        unitLaunchPad = new UnitLaunchPad("unit-launch-pad"){{
            requirements(Category.effect, with(Items.copper, 3));
            size = 3;
            itemCapacity = 100;
            launchTime = 60f * 20;
            hasPower = true;
            consumePower(4f);
        }};
    }
}
