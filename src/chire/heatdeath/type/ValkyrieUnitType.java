package chire.heatdeath.type;

import arc.Core;
import arc.scene.ui.layout.Table;
import arc.util.Log;
import chire.heatdeath.core.DLWorld;
import chire.heatdeath.core.UnitChunks;
import chire.heatdeath.graphics.g2d.ValkyrieSpriteBatch;
import chire.heatdeath.type.entity.ValkyrieUnitEntity;
import chire.heatdeath.world.valkyrie.ValkyrieChunk;
import mindustry.Vars;
import mindustry.content.Blocks;
import mindustry.content.Items;
import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.gen.Unit;
import mindustry.type.UnitType;
import mindustry.world.blocks.sandbox.ItemSource;

public class ValkyrieUnitType extends UnitType {
    public int chunkWidth, chunkHeight;

    public ValkyrieUnitType(String name) {
        super(name);

        chunkWidth = 20;
        chunkHeight = 20;
    }

    @Override
    public Unit create(Team team) {
        Unit unit = super.create(team);

        if (unit instanceof ValkyrieUnitEntity valkyrieUnit) {
            Building building = Blocks.conveyor.newBuilding().create(Blocks.conveyor, team);

            building.rotation = 3;

            valkyrieUnit.unitChunks = new UnitChunks(chunkWidth, chunkHeight);
//            valkyrieUnit.unitChunks.addChunkBlock(0, 0, new ValkyrieChunk(Blocks.salvo, team));
//            valkyrieUnit.unitChunks.addChunkBlock(2, 0, new ValkyrieChunk(Blocks.salvo, team));
//            valkyrieUnit.unitChunks.addChunkBlock(0, 2, new ValkyrieChunk(Blocks.conveyor, team));
//            valkyrieUnit.unitChunks.addChunkBlock(1, 2, new ValkyrieChunk(Blocks.conveyor, team));
//            valkyrieUnit.unitChunks.addChunkBlock(2, 2, new ValkyrieChunk(building));
            valkyrieUnit.unitChunks.addChunkBlock(0, 0, Blocks.salvo, team);
            valkyrieUnit.unitChunks.addChunkBlock(2, 0, Blocks.salvo, team);
            valkyrieUnit.unitChunks.addChunkBlock(0, 2, Blocks.itemSource, team);

            valkyrieUnit.unitChunks.setBoolf(chunk -> {
                if (chunk.build.block == Blocks.conveyor && chunk.x == 0 && chunk.y == 2) {
                    chunk.build.handleItem(chunk.build, Items.copper);
                }

                if (chunk.build instanceof ItemSource.ItemSourceBuild itemSource) {
                    itemSource.outputItem = Items.copper;
                }
            });

            valkyrieUnit.unitChunks.addChunkBlock(1, 2, Blocks.conveyor, team);
            valkyrieUnit.unitChunks.addChunkBlock(2, 2, building);
        }

        return unit;
    }

    @Override
    public void update(Unit unit) {
        super.update(unit);

        if (unit instanceof ValkyrieUnitEntity entity) {
            if (Vars.world instanceof DLWorld unitWorld) {
                unitWorld.setChunks(entity.unitChunks.chunks);

                entity.unitChunks.update(entity);

                entity.unitChunks.chunks = unitWorld.end();
            }
        }
    }

    @Override
    public void draw(Unit unit) {
        super.draw(unit);

        if (unit instanceof ValkyrieUnitEntity entity) {
            //炮台绘制存在问题，只能覆盖Core.batch绘制
//            float z = Draw.z();
//            Draw.z(Layer.flyingUnitLow);
//
//            entity.unitChunks.draw(entity);
//
//            Draw.z(z);
            if (Core.batch instanceof ValkyrieSpriteBatch valkyrieBatch) {
                valkyrieBatch.setUnit(entity);

                entity.unitChunks.draw(entity);

                valkyrieBatch.end();
            } else {
                Log.warn("[ValkyrieUnitType] unexpected batch object");
            }
        }
    }

    @Override
    public void display(Unit unit, Table table) {
        super.display(unit, table);
    }

    @Override
    public void drawBody(Unit unit) {

    }

    @Override
    public void drawCell(Unit unit) {
        super.drawCell(unit);
    }
}
