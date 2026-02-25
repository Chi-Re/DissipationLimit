package chire.heatdeath.core;

import arc.util.Nullable;
import mindustry.core.World;
import mindustry.gen.Building;
import mindustry.world.Tile;

import java.util.WeakHashMap;

public class DLWorld extends World {
    @Override
    public @Nullable Building build(int x, int y){
        return super.build(x, y);
    }

    @Override
    @Nullable
    public Tile tile(int x, int y){
        return super.tile(x, y);
    }
}
