package chire.heatdeath.world.valkyrie;

import arc.graphics.g2d.Draw;
import arc.util.Nullable;
import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.gen.Unit;
import mindustry.graphics.Layer;
import mindustry.world.Block;
import mindustry.world.blocks.payloads.BuildPayload;

public class ValkyrieChunk extends BuildPayload {
    public int x, y;

    public ValkyrieChunk(Block block, Team team, int x, int y) {
        this(block.newBuilding().create(block, team), x, y);
    }

    public ValkyrieChunk(Building build, int x, int y){
        super(build);
        build.tile = new ChunkTile(x+(build.block.size-1)/2, y+(build.block.size-1)/2);
        build.tile.build = build;
        set(x, y);
    }

    public void set(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public float x(){
        return this.x;
    }

    @Override
    public float y(){
        return this.y;
    }

    public void update(){
        this.update(null, null);
    }

    @Override
    public void update(@Nullable Unit unitHolder, @Nullable Building buildingHolder){
//        build.tile = emptyTile;
        build.update();
        build.tile.build = build;
    }

    @Override
    public void draw(){
        Draw.z(Layer.block);
        build.tile.build = build;
        build.draw();
    }
}
