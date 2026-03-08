package chire.heatdeath.world.valkyrie;

import arc.util.Nullable;
import mindustry.world.Tile;

import static mindustry.Vars.world;

public class ChunkTile extends Tile {
    public ChunkTile(int x, int y) {
        super(x, y);
    }

//    @Override
//    public @Nullable Tile nearby(int dx, int dy){
//        //TODO 可能存在问题，仅为了适配
//        return world.tile(x + (this.build.block.size-1)/2 + dx, y + (this.build.block.size-1)/2 + dy);
//    }
}
