package chire.heatdeath.world.blocks.valkyrie;

import arc.scene.ui.layout.Table;
import arc.util.Log;
import chire.heatdeath.content.DLUnitTypes;
import chire.heatdeath.type.ValkyrieUnitType;
import mindustry.Vars;
import mindustry.gen.Building;
import mindustry.gen.Icon;
import mindustry.gen.Unit;
import mindustry.ui.Styles;
import mindustry.world.Block;
import mindustry.world.Tiles;

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
                Tiles tiles = new Tiles(width, height);

                for (int i = (int) (x()/Vars.tilesize); i < (x()/Vars.tilesize)+width; i++) {
                    for (int j = (int) (y()/Vars.tilesize); j < (y()/Vars.tilesize)+height; j++) {
                        tiles.set((int) (i-x()/Vars.tilesize), (int) (j-y()/Vars.tilesize), Vars.world.tile(i, j));
                    }
                }

                Unit u = ((ValkyrieUnitType)DLUnitTypes.valkyrie).spawn(team, x, y, tiles);
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
    }
}
