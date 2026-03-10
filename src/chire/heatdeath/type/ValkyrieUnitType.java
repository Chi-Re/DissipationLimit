package chire.heatdeath.type;

import arc.graphics.Color;
import arc.graphics.g2d.Batch;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.SpriteBatch;
import arc.math.Mat;
import arc.scene.ui.layout.Table;
import arc.struct.Seq;
import arc.util.Tmp;
import chire.heatdeath.core.DLWorld;
import chire.heatdeath.core.UnitChunks;
import chire.heatdeath.type.entity.ValkyrieUnitEntity;
import mindustry.Vars;
import mindustry.content.Blocks;
import mindustry.content.Items;
import mindustry.game.Schematic;
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

        if (unit instanceof ValkyrieUnitEntity valkyrieUnit) valkyrieUnit.unitChunks = new UnitChunks(chunkWidth, chunkHeight);

        return unit;
    }

    public Unit spawn(Team team, float x, float y, Seq<Schematic.Stile> tiles) {
        Unit unit = super.spawn(team, x, y);

        if (unit instanceof ValkyrieUnitEntity valkyrieUnit) {
            for (Schematic.Stile tile : tiles) {
                valkyrieUnit.unitChunks.addChunkBlock(tile, team);
            }
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
