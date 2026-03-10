package chire.heatdeath.core;

import arc.func.Boolf;
import arc.func.Cons;
import arc.func.Prov;
import arc.graphics.g2d.Draw;
import arc.math.geom.Position;
import arc.util.Log;
import chire.heatdeath.type.entity.ValkyrieUnitEntity;
import chire.heatdeath.world.Chunks;
import chire.heatdeath.world.valkyrie.ValkyrieChunk;
import mindustry.Vars;
import mindustry.content.Items;
import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.world.Block;
import mindustry.world.blocks.defense.turrets.BaseTurret;
import mindustry.world.blocks.distribution.Conveyor;
import mindustry.world.blocks.payloads.PayloadConveyor;

import java.util.ArrayList;

public class UnitChunks {
    public int chunkWidth, chunkHeight;

    public Chunks chunks;

    private ArrayList<Cons<ValkyrieChunk>> boolves = new ArrayList<>();

    public UnitChunks(int chunkWidth, int chunkHeight) {
        this.chunkWidth = chunkWidth;
        this.chunkHeight = chunkHeight;
        chunks = new Chunks(this.chunkWidth, this.chunkHeight);
    }

    public void setBoolf(Cons<ValkyrieChunk> chunksBoolf){
        boolves.add(chunksBoolf);
    }

    public void addChunkBlock(int x, int y, Building payload, Object config) {
        chunks.set(x, y, new ValkyrieChunk(payload, x, y, config));
    }

    public void addChunkBlock(int x, int y, Block block, Team team, Object config) {
        chunks.set(x, y, new ValkyrieChunk(block, team, x, y, config));
    }

    public <T extends ValkyrieUnitEntity> void update(T unit) {
        for (ValkyrieChunk chunk : chunks) {
            if (chunk == null) continue;
            //TODO 这里可以继续优化，因为正常情况下只有周围方块更新才有必要。
            chunk.build.onProximityUpdate();

            chunk.build.updateProximity();

            for (Cons<ValkyrieChunk> boolf : boolves) {
                boolf.get(chunk);
            }

            chunk.update(null, null);
        }
    }

    public <T extends ValkyrieUnitEntity> void draw(T unit){
        for (ValkyrieChunk chunk : chunks) {
            if (chunk == null) continue;
            chunk.set(
                    unit.x + chunk.x * Vars.tilesize + (float) (chunk.build.block.size * Vars.tilesize) / 2,
                    unit.y + chunk.y * Vars.tilesize + (float) (chunk.build.block.size * Vars.tilesize) / 2,
                    unit.rotation
            );
            chunk.draw();
        }
    }

    public int width() {
        return chunkWidth;
    }

    public int height() {
        return chunkHeight;
    }
}
