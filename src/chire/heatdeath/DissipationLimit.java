package chire.heatdeath;

import arc.Core;
import arc.Graphics;
import arc.graphics.g2d.TextureAtlas;
import arc.util.Log;
import arc.util.Time;
import chire.heatdeath.content.HBlocks;
import chire.heatdeath.content.HTechTree;
import chire.heatdeath.ui.dialogs.SelectDialog;
import mindustry.Vars;
import mindustry.mod.Mod;

import static mindustry.ui.Fonts.cursorScale;

public class DissipationLimit extends Mod{
    public static String modName;

    public static SelectDialog selectDialog;

    public static Graphics.Cursor target = Core.graphics.newCursor("target", cursorScale());

    @Override
    public void init() {
        modName = Vars.mods.getMod(DissipationLimit.class).name;
        selectDialog = new SelectDialog("test");
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
