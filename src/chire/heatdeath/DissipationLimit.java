package chire.heatdeath;

import arc.Core;
import arc.Graphics;
import arc.graphics.g2d.TextureAtlas;
import arc.util.Log;
import chire.heatdeath.content.DLBlocks;
import chire.heatdeath.content.DLTechTree;
import chire.heatdeath.content.DLUnitTypes;
import chire.heatdeath.core.DLWorld;
import chire.heatdeath.graphics.g2d.ValkyrieSpriteBatch;
import chire.heatdeath.util.EntityRegistry;
import mindustry.Vars;
import mindustry.mod.Mod;

import static mindustry.ui.Fonts.cursorScale;

public class DissipationLimit extends Mod{
    public static String modName;
    public static Graphics.Cursor target = Core.graphics.newCursor("target", cursorScale());

    @Override
    public void init() {
        Log.info("[DissipationLimit] init setup");
        modName = Vars.mods.getMod(DissipationLimit.class).name;
    }

    @Override
    public void loadContent(){
        Log.info("[DissipationLimit] loadContent setup");
        Core.batch = new ValkyrieSpriteBatch(); //覆盖原版代码，实现方块旋转。
        Vars.world = new DLWorld(); //覆盖原版代码，实现多区块加载(TODO 性能存在问题

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
