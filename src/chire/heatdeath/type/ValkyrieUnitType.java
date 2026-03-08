package chire.heatdeath.type;

import arc.graphics.Color;
import arc.graphics.g2d.Batch;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.SpriteBatch;
import arc.math.Mat;
import arc.scene.ui.layout.Table;
import arc.util.Tmp;
import chire.heatdeath.core.DLWorld;
import chire.heatdeath.core.UnitChunks;
import chire.heatdeath.type.entity.ValkyrieUnitEntity;
import mindustry.Vars;
import mindustry.content.Blocks;
import mindustry.content.Items;
import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.gen.Unit;
import mindustry.graphics.Layer;
import mindustry.type.UnitType;
import mindustry.world.Tiles;
import mindustry.world.blocks.sandbox.ItemSource;
import mindustry.world.blocks.units.UnitFactory;

import static arc.Core.batch;
import static arc.Core.camera;

public class ValkyrieUnitType extends UnitType {
    public int chunkWidth, chunkHeight;

    public static final Batch altBatch = new SpriteBatch();

    public ValkyrieUnitType(String name) {
        super(name);

        chunkWidth = 50;
        chunkHeight = 50;
    }

    @Override
    public Unit create(Team team) {
        Unit unit = super.create(team);

        if (unit instanceof ValkyrieUnitEntity valkyrieUnit) {
            //测试部分方块代码
            valkyrieUnit.unitChunks = new UnitChunks(chunkWidth, chunkHeight);
            valkyrieUnit.unitChunks.addChunkBlock(0, 0, Blocks.salvo, team);
            valkyrieUnit.unitChunks.addChunkBlock(2, 0, Blocks.salvo, team);

            Building building = Blocks.itemSource.newBuilding().create(Blocks.itemSource, team);

            ((ItemSource.ItemSourceBuild) building).outputItem = Items.copper;

            valkyrieUnit.unitChunks.addChunkBlock(0, 2, building);

            valkyrieUnit.unitChunks.setBoolf(chunk -> {
                if (chunk.build instanceof ItemSource.ItemSourceBuild itemSource && itemSource.outputItem == null) {
                    itemSource.outputItem = Items.silicon;
                }
            });

            building = Blocks.conveyor.newBuilding().create(Blocks.conveyor, team);

            building.rotation = 3;

            valkyrieUnit.unitChunks.addChunkBlock(1, 2, Blocks.conveyor, team);
            valkyrieUnit.unitChunks.addChunkBlock(2, 2, building);

            valkyrieUnit.unitChunks.addChunkBlock(4, 2, Blocks.powerSource, team);
            valkyrieUnit.unitChunks.addChunkBlock(4, 0, Blocks.repairTurret, team);

            building = Blocks.airFactory.newBuilding().create(Blocks.airFactory, team);

            building.rotation = 1;
            ((UnitFactory.UnitFactoryBuild) building).currentPlan = 0;

            valkyrieUnit.unitChunks.addChunkBlock(0, 3, building);
            valkyrieUnit.unitChunks.addChunkBlock(0, 6, Blocks.payloadConveyor, team);

            valkyrieUnit.unitChunks.addChunkBlock(3, 4, Blocks.itemSource, team);
            valkyrieUnit.unitChunks.addChunkBlock(3, 3, Blocks.powerSource, team);
        }

        return unit;
    }

    public Unit spawn(Team team, float x, float y, Tiles tiles) {
        Unit unit = super.spawn(team, x, y);

        if (unit instanceof ValkyrieUnitEntity valkyrieUnit) {
            valkyrieUnit.tiles = tiles;
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
    }

    @Override
    public void display(Unit unit, Table table) {
        super.display(unit, table);
    }

    @Override
    public void drawBody(Unit unit) {
        if (unit instanceof ValkyrieUnitEntity entity) {
            if (Vars.world instanceof DLWorld unitWorld) {
                float z = Draw.z();

                Draw.draw(Layer.flyingUnitLow - 1f, () -> {
                    Mat proj = Tmp.m1.set(Draw.proj());

                    camera.update();
                    Draw.flush();
                    Batch old = batch;
                    batch = altBatch;

                    Draw.proj(camera);

                    Draw.sort(true);

                    unitWorld.setChunks(entity.unitChunks.chunks);
                    entity.unitChunks.draw(entity);
                    entity.unitChunks.chunks = unitWorld.end();

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
    }

    @Override
    public void drawCell(Unit unit) {
        super.drawCell(unit);
    }
}
