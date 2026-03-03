package chire.heatdeath.type;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.Batch;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.SpriteBatch;
import arc.math.Mat;
import arc.scene.ui.layout.Table;
import arc.util.Log;
import arc.util.Tmp;
import chire.heatdeath.core.DLWorld;
import chire.heatdeath.core.UnitChunks;
import chire.heatdeath.graphics.g2d.ValkyrieSpriteBatch;
import chire.heatdeath.type.entity.ValkyrieUnitEntity;
import mindustry.Vars;
import mindustry.content.Blocks;
import mindustry.content.Items;
import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.gen.Unit;
import mindustry.graphics.Layer;
import mindustry.type.UnitType;
import mindustry.world.blocks.sandbox.ItemSource;

import static arc.Core.batch;
import static arc.Core.camera;

public class ValkyrieUnitType extends UnitType {
    public int chunkWidth, chunkHeight;

    public static final Batch altBatch = new SpriteBatch();

    public ValkyrieUnitType(String name) {
        super(name);

        chunkWidth = 20;
        chunkHeight = 20;
    }

    @Override
    public Unit create(Team team) {
        Unit unit = super.create(team);

        if (unit instanceof ValkyrieUnitEntity valkyrieUnit) {
            //测试部分方块代码
            Building building = Blocks.conveyor.newBuilding().create(Blocks.conveyor, team);

            building.rotation = 3;

            valkyrieUnit.unitChunks = new UnitChunks(chunkWidth, chunkHeight);
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
            float z = Draw.z();

            Draw.draw(Layer.flyingUnitLow - 1f, () -> {
                Mat proj = Tmp.m1.set(Draw.proj());

                camera.update();
                Draw.flush();
                Batch old = batch;
                batch = altBatch;

                Draw.proj(camera);

                Draw.sort(true);

                entity.unitChunks.draw(entity);

                Draw.z(9999f);
                Draw.color(Color.clear);
                Fill.rect(0, 0, 0, 0);

                Draw.reset();
                Draw.flush();
                Draw.sort(false);

                camera.update();
                Draw.proj(proj);
                batch = old;
            });

            Draw.z(z);
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
