package chire.heatdeath.content;


import chire.heatdeath.world.blocks.train.TrainTrackBlock;
import mindustry.content.Items;
import mindustry.type.Category;

import static mindustry.type.ItemStack.with;

public class DLBlocks {
    public static void load(){
        new TrainTrackBlock("train-track"){{
            conductivePower = true;
            size = 2;
            rotate = true;
            acceptsPayload = true;
            outputsPayload = true;
            hasShadow = false;
            requirements(Category.units, with(Items.copper, 1));
        }};
    }
}
