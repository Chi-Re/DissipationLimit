package chire.heatdeath;

import arc.Core;
import arc.graphics.g2d.TextureAtlas;
import chire.heatdeath.content.HBlocks;
import chire.heatdeath.content.HTechTree;
import mindustry.Vars;
import mindustry.mod.Mod;

public class DissipationLimit extends Mod{
    public static String modName;

    @Override
    public void init() {
        modName = Vars.mods.getMod(DissipationLimit.class).name;
    }

    @Override
    public void loadContent(){
        HBlocks.load();
        HTechTree.load();
    }

    public static String getBundle(String str){
        return Core.bundle.format(str);
    }

    public static TextureAtlas.AtlasRegion getAtlas(String name){
        return Core.atlas.find(name);
    }

    public static String name(String add){
        return modName + "-" + add;
    }
}
