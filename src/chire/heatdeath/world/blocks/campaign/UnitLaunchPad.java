package chire.heatdeath.world.blocks.campaign;

import arc.Core;
import arc.audio.Sound;
import arc.func.Cons;
import arc.graphics.Color;
import arc.graphics.g2d.TextureRegion;
import arc.scene.ui.layout.Table;
import arc.struct.EnumSet;
import arc.util.Log;
import mindustry.Vars;
import mindustry.content.Planets;
import mindustry.content.SectorPresets;
import mindustry.content.UnitTypes;
import mindustry.entities.Units;
import mindustry.game.Rules;
import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.gen.Icon;
import mindustry.gen.Sounds;
import mindustry.ui.Styles;
import mindustry.world.Block;
import mindustry.world.meta.BlockFlag;

import static chire.heatdeath.DissipationLimit.getAtlas;
import static chire.heatdeath.DissipationLimit.selectDialog;
import static mindustry.Vars.*;

public class UnitLaunchPad extends Block {
    public float launchTime = 1f;
    public Sound launchSound = Sounds.none;
    public TextureRegion lightRegion;
    public TextureRegion podRegion;
    public Color lightColor = Color.valueOf("eab678");


    public UnitLaunchPad(String name) {
        super(name);
        hasItems = true;
        solid = true;
        update = true;
        configurable = true;
        flags = EnumSet.of(BlockFlag.launchPad);
    }

    @Override
    public void load() {
        super.load();

        region = getAtlas("launch-pad");
        lightRegion = getAtlas("launch-pad-light");
        podRegion = getAtlas("launch-pad-pod");
    }

    @Override
    public boolean outputsItems(){
        return false;
    }

    public class UnitLaunchPadBuild extends Building {
        @Override
        public void display(Table table){
            super.display(table);
        }

        @Override
        public void buildConfiguration(Table table){
            if(!state.isCampaign()){
                deselect();
                return;
            }

            table.button(Icon.upOpen, Styles.cleari, () -> {
                ui.planet.showSelect(state.rules.sector, other -> {
                    control.playSector(other);
                    selectDialog.addDeploy(UnitTypes.dagger);
                    selectDialog.show();
                });
            }).size(40f);
        }
    }
}
