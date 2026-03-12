package chire.heatdeath.world.blocks.valkyrie;

import arc.math.geom.Point2;
import arc.scene.ui.layout.Table;
import arc.struct.IntSeq;
import arc.struct.IntSet;
import arc.struct.Seq;
import arc.struct.StringMap;
import arc.util.Log;
import chire.heatdeath.content.DLUnitTypes;
import chire.heatdeath.type.ValkyrieUnitType;
import mindustry.Vars;
import mindustry.game.Schematic;
import mindustry.game.Schematics;
import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.gen.Icon;
import mindustry.gen.Unit;
import mindustry.input.Placement;
import mindustry.ui.Styles;
import mindustry.world.Block;
import mindustry.world.Tiles;
import mindustry.world.blocks.ConstructBlock;
import mindustry.world.blocks.power.PowerNode;
import mindustry.world.blocks.storage.CoreBlock;

import static mindustry.Vars.*;
import static mindustry.Vars.world;

public class ValkyrieCreator extends Block {
    int width = 10, height = 10;

    public ValkyrieCreator(String name){
        super(name);
        update = true;
        configurable = true;
        hasItems = true;
        itemCapacity = 150;
        separateItemCapacity = true;
    }

    public class ValkyrieCreatorBuild extends Building {
        @Override
        public void buildConfiguration(Table table){
            table.button(Icon.units, Styles.cleari, () -> {
                Schematic schematic = create(tileX(), tileY(), tileX()+width, tileY()+height);

                Log.warn(schematic.tiles.toString());
                Unit unit = DLUnitTypes.valkyrie.spawn(team, x(), y(), schematic.tiles);
            }).size(50f);
        }

        private void buildValkyrie(){
            for (int i = (int) (x()/Vars.tilesize); i < (x()/Vars.tilesize)+width; i++) {
                for (int j = (int) (y()/Vars.tilesize); j < (y()/Vars.tilesize)+height; j++) {
                    var b = Vars.world.build(i, j);

                    Log.warn("Valkyrie " + i + " " + j);
                    if (b != null) {
                        Log.warn(b.block.name);
                    } else {
                        Log.warn("none");
                    }
                }
            }
        }

        public Schematic create(int x, int y, int x2, int y2){
            Team team = headless ? null : Vars.player.team();
            Placement.NormalizeResult result = Placement.normalizeArea(x, y, x2, y2, 0, false, maxSchematicSize);
            x = result.x;
            y = result.y;
            x2 = result.x2;
            y2 = result.y2;

            int ox = x, oy = y, ox2 = x2, oy2 = y2;

            Seq<Schematic.Stile> tiles = new Seq<>();

            int minx = x2, miny = y2, maxx = x, maxy = y;
            boolean found = false;
            for(int cx = x; cx <= x2; cx++){
                for(int cy = y; cy <= y2; cy++){
                    Building linked = world.build(cx, cy);
                    if(linked != null && (!linked.isDiscovered(team) || !linked.wasVisible)) continue;

                    Block realBlock = linked == null ? null : linked instanceof ConstructBlock.ConstructBuild cons ? cons.current : linked.block;

                    if(linked != null && realBlock != null && (realBlock.isVisible() || realBlock instanceof CoreBlock)){
                        int top = realBlock.size/2;
                        int bot = realBlock.size % 2 == 1 ? -realBlock.size/2 : -(realBlock.size - 1)/2;
                        minx = Math.min(linked.tileX() + bot, minx);
                        miny = Math.min(linked.tileY() + bot, miny);
                        maxx = Math.max(linked.tileX() + top, maxx);
                        maxy = Math.max(linked.tileY() + top, maxy);
                        found = true;
                    }
                }
            }

            if(found){
                x = minx;
                y = miny;
                x2 = maxx;
                y2 = maxy;
            }else{
                return new Schematic(new Seq<>(), new StringMap(), 1, 1);
            }

            int width = x2 - x + 1, height = y2 - y + 1;
            int offsetX = -x, offsetY = -y;
            IntSet counted = new IntSet();
            for(int cx = ox; cx <= ox2; cx++){
                for(int cy = oy; cy <= oy2; cy++){
                    Building tile = world.build(cx, cy);
                    if(tile != null && (!tile.isDiscovered(team) || !tile.wasVisible)) continue;
                    Block realBlock = tile == null ? null : tile instanceof ConstructBlock.ConstructBuild cons ? cons.current : tile.block;

                    if(tile != null && !counted.contains(tile.pos()) && realBlock != null
                            && (realBlock.isVisible() || realBlock instanceof CoreBlock)){
//                        Object config = tile instanceof ConstructBlock.ConstructBuild cons ? cons.lastConfig : tile.config();
                        Object config;

                        if (tile instanceof ConstructBlock.ConstructBuild cons){
                            config = cons.lastConfig;
                        } else if(tile instanceof PowerNode.PowerNodeBuild power) {
                            IntSeq seq = power.power.links, nseq = new IntSeq();
                            for(int i = 0; i < seq.size; i++){
                                int pos = seq.get(i);
                                nseq.add(Point2.pack(
                                        Point2.x(pos)-x,
                                        Point2.y(pos)-y
                                ));
                            }

                            config = nseq;
                        } else {
                            config = tile.config();
                        }

                        tiles.add(new Schematic.Stile(realBlock, cx-x, cy-y, config, (byte)tile.rotation));
                        counted.add(tile.pos());
                    }
                }
            }

            return new Schematic(tiles, new StringMap(), width, height);
        }
    }
}
