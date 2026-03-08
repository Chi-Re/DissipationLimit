package chire.heatdeath.type.entity;

import arc.func.Cons;
import arc.util.io.Reads;
import arc.util.io.Writes;
import chire.heatdeath.core.UnitChunks;
import chire.heatdeath.world.valkyrie.ValkyrieChunk;
import mindustry.Vars;
import mindustry.core.World;
import mindustry.gen.Building;
import mindustry.gen.UnitEntity;
import mindustry.world.Tile;
import mindustry.world.Tiles;
import mindustry.world.blocks.distribution.Conveyor;
import mindustry.world.blocks.payloads.BuildPayload;

public class ValkyrieUnitEntity extends UnitEntity {
    //单位上保存的区块内容
    public UnitChunks unitChunks;

    public Tiles tiles;

    protected ValkyrieUnitEntity() {
        super();
    }

    public static UnitEntity create() {
        return new ValkyrieUnitEntity();
    }

    public void drawTile() {
        for (Tile chunk : tiles) {
            if (chunk.build == null) continue;
            chunk.build.x(this.x + chunk.x * Vars.tilesize + (float) (chunk.build.block.size * Vars.tilesize) / 2);
            chunk.build.y(this.y + chunk.y * Vars.tilesize + (float) (chunk.build.block.size * Vars.tilesize) / 2);
            chunk.build.draw();
//            chunk.set(
//                    unit.x + chunk.x * Vars.tilesize + (float) (chunk.build.block.size * Vars.tilesize) / 2,
//                    unit.y + chunk.y * Vars.tilesize + (float) (chunk.build.block.size * Vars.tilesize) / 2,
//                    unit.rotation
//            );
//            chunk.draw();
        }
    }

    public void updateTile() {
        for (Tile chunk : tiles) {
            if (chunk.build == null) continue;
            if (chunk.build instanceof Conveyor.ConveyorBuild conveyor) conveyor.onProximityUpdate();
            chunk.build.updateProximity();

            chunk.build.update();
        }
    }

    @Override
    public void read(Reads read) {
        super.read(read);
    }

    @Override
    public void write(Writes write) {
        super.write(write);
    }
}
