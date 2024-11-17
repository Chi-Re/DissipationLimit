package chire.heatdeath.world.blocks.campaign;

import arc.Core;
import arc.audio.Sound;
import arc.graphics.Color;
import arc.graphics.g2d.TextureRegion;
import arc.struct.EnumSet;
import mindustry.gen.Building;
import mindustry.gen.Sounds;
import mindustry.world.Block;
import mindustry.world.meta.BlockFlag;

import static chire.heatdeath.DissipationLimit.getAtlas;

public class UnitLaunchPad extends Block {
    public float launchTime = 1f;
    public Sound launchSound = Sounds.none;
    public TextureRegion lightRegion;
    public TextureRegion podRegion;
    public Color lightColor = Color.valueOf("eab678");


    public UnitLaunchPad(String name) {
        super(name);
        hasItems = true;
        solid = true;
        update = true;
        configurable = true;
        flags = EnumSet.of(BlockFlag.launchPad);
    }

    @Override
    public void load() {
        super.load();

        region = getAtlas("launch-pad");
        lightRegion = getAtlas("launch-pad-light");
        podRegion = getAtlas("launch-pad-pod");
    }

    @Override
    public boolean outputsItems(){
        return false;
    }

    public class UnitLaunchPadBuild extends Building {

    }
}
