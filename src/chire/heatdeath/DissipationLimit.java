package chire.heatdeath;

import arc.Core;
import arc.Graphics;
import arc.graphics.g2d.TextureAtlas;
import chire.heatdeath.content.DLBlocks;
import chire.heatdeath.content.DLTechTree;
import chire.heatdeath.content.DLUnitTypes;
import chire.heatdeath.util.EntityRegistry;
import mindustry.Vars;
import mindustry.mod.Mod;

import static mindustry.ui.Fonts.cursorScale;

public class DissipationLimit extends Mod{
    public static String modName;
    public static Graphics.Cursor target = Core.graphics.newCursor("target", cursorScale());

    @Override
    public void init() {
        modName = Vars.mods.getMod(DissipationLimit.class).name;
    }

    @Override
    public void loadContent(){
        EntityRegistry.register();

        DLBlocks.load();
        DLUnitTypes.load();
        DLTechTree.load();
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
