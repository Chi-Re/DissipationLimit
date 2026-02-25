package chire.heatdeath.type;

import arc.Core;
import arc.graphics.g2d.Draw;
import arc.math.geom.Vec2;
import arc.scene.ui.layout.Table;
import arc.util.Log;
import chire.heatdeath.graphics.g2d.ValkyrieSpriteBatch;
import chire.heatdeath.type.entity.ValkyrieUnitEntity;
import chire.heatdeath.world.ValkyrieTile;
import mindustry.Vars;
import mindustry.content.Blocks;
import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.gen.Unit;
import mindustry.type.UnitType;
import mindustry.world.Tile;
import mindustry.world.Tiles;
import mindustry.world.blocks.defense.turrets.Turret;

public class ValkyrieUnitType extends UnitType {
    public int worldWidth, worldHeight;

    public ValkyrieUnitType(String name) {
        super(name);

        worldWidth = 10;
        worldHeight = 10;
    }

    @Override
    public Unit create(Team team) {
        Unit unit = super.create(team);

        if (unit instanceof ValkyrieUnitEntity valkyrieUnit) {
            valkyrieUnit.unitWorld.tiles = new Tiles(worldWidth, worldHeight);
            for(int i = 0; i < worldWidth * worldHeight; i++){
                valkyrieUnit.unitWorld.tiles.set(i % worldWidth, i / worldWidth, new ValkyrieTile(i % worldWidth, i / worldWidth));
            }
            valkyrieUnit.unitWorld.tiles.eachTile(tile -> tile.setFloor(Blocks.metalFloor.asFloor()));
            valkyrieUnit.unitWorld.tiles.eachTile(tile -> {
                if (tile.x == 0 && tile.y == 0) {
                    tile.setBlock(Blocks.salvo);
                }
            });
        }

        return unit;
    }

    @Override
    public void update(Unit unit) {
        super.update(unit);

        if (unit instanceof ValkyrieUnitEntity entity) {
            for (var tile : entity.unitWorld.tiles) {
                if (!(tile instanceof ValkyrieTile valkyrieTile)) continue;

                Vec2 vec = new Vec2(tile.x + (float) (tile.block().size - 1) / 2, tile.y + (float) (tile.block().size - 1) / 2).rotate(unit.rotation - 90);

                if (tile.build != null) {
                    tile.build.set(unit.x + vec.x * Vars.tilesize, unit.y + vec.y * Vars.tilesize);
                }

                //TODO 神秘floor贴图偏移，可能会修
                vec = new Vec2(tile.x+0.5f, tile.y-0.5f).rotate(unit.rotation - 90);

                valkyrieTile.unitX = unit.x + (vec.x) * Vars.tilesize;
                valkyrieTile.unitY = unit.y + (vec.y) * Vars.tilesize;
            }
        }
    }

//    float cwX(float x, Unit unit){
//        return (x - this.x) + (unitWorld.width() * Vars.tilesize / 2f);
//    }
//
//    float cwY(float y, Unit unit){
//        return (y - this.y) + (unitWorld.height() * Vars.tilesize / 2f);
//    }

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
//        super.drawBody(unit);
//        ValkyrieSpriteBatch batch = new ValkyrieSpriteBatch();
//
//        Draw.batch(batch, () -> {
//
//        });

//        Log.warn(Core.batch.getClass().getName());

        if (unit instanceof ValkyrieUnitEntity entity) {
            if (Core.batch instanceof ValkyrieSpriteBatch valkyrieBatch) {
                valkyrieBatch.setRotation(unit.rotation);

                for (var tile : entity.unitWorld.tiles) {
                    if (tile.build != null) {
                        tile.build.draw();
                    }

                    //TODO 一般不会存在地板的问题，因为地板应该不会飞起来。之后可能只支持地板。
                    float z = Draw.z();
                    Draw.z(z - 0.01f);
                    tile.floor().drawBase(tile);
                    Draw.z(z);
                }

                valkyrieBatch.endRotation();
            } else {
                Log.warn("[ValkyrieUnitType] unexpected batch object");
            }
        }
    }

    @Override
    public void drawCell(Unit unit) {
        super.drawCell(unit);
    }
}
