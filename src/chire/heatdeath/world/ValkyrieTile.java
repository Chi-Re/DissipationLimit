package chire.heatdeath.world;

import arc.func.Prov;
import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.world.Block;
import mindustry.world.Tile;
import mindustry.world.blocks.environment.Floor;

import static mindustry.Vars.tilesize;

public class ValkyrieTile extends Tile {
    public float unitX, unitY;

    public ValkyrieTile(int x, int y) {
        super(x, y);
    }

    @Override
    public float worldx(){
        return unitX - tilesize + 12;
    }

    @Override
    public float worldy(){
        return unitY - tilesize + 13;
    }
}
