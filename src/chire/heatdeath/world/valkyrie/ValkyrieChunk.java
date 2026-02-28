package chire.heatdeath.world.valkyrie;

import arc.graphics.g2d.Draw;
import arc.math.Mathf;
import arc.util.Log;
import arc.util.Nullable;
import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.gen.Unit;
import mindustry.graphics.Layer;
import mindustry.world.Block;
import mindustry.world.Tile;
import mindustry.world.blocks.payloads.BuildPayload;

import static mindustry.Vars.emptyTile;

public class ValkyrieChunk extends BuildPayload {
    public int x, y;

    public ValkyrieChunk(Block block, Team team, int x, int y) {
        this(block.newBuilding().create(block, team), x, y);
    }

    public ValkyrieChunk(Building build, int x, int y){
        super(build);
        build.tile = new ChunkTile(x, y);
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
        float prevZ = Draw.z();
        Draw.z(prevZ - 0.001f);
        drawShadow(1f);
        Draw.z(prevZ);
        Draw.zTransform(z ->
                z >= Layer.flyingUnitLow + 1f ? z :
                        0.0011f + Math.min(Mathf.clamp((z - prevZ)/100f, -0.0009f, 0.9f) + prevZ, Layer.flyingUnitLow - 1f)
        );
//        build.tile = emptyTile;
        build.draw();
        build.tile.build = build;
        Draw.zTransform();
        Draw.z(prevZ);
    }
}
