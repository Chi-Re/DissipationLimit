package chire.heatdeath.core;

import arc.util.Nullable;
import chire.heatdeath.world.Chunks;
import mindustry.core.World;
import mindustry.gen.Building;
import mindustry.world.Tile;

public class DLWorld extends World {
    private Chunks chunks;

    public void setChunks(Chunks chunks) {
        this.chunks = chunks;
    }

//    @Override
//    public @Nullable Building build(int x, int y){
////        if (chunks != null) Log.warn("[DLWorld] Building " + x + " " + y);
////        return super.build(x, y);
//        if (chunks != null) {
//            var chunk = chunks.get(x, y);
//            return chunk == null ? null : chunk.build;
//        } else {
//            return super.build(x, y);
//        }
//    }

    @Override
    public Tile tile(int x, int y) {
        if (chunks != null) {
            var chunk = chunks.get(x, y);
            return chunk != null ? chunk.build.tile : super.tile(x, y);
        } else {
            return super.tile(x, y);
        }
    }


    public Chunks end(){
        var c = chunks;
        this.chunks = null;
        return c;
    }
}
