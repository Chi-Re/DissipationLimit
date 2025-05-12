package chire.heatdeath.ui.dialogs;

import arc.Core;
import arc.graphics.Pixmap;
import arc.graphics.Texture;
import arc.input.KeyCode;
import arc.math.Mathf;
import arc.scene.Element;
import arc.scene.event.ClickListener;
import arc.scene.event.InputEvent;
import arc.scene.event.InputListener;
import arc.scene.style.Drawable;
import arc.scene.style.TextureRegionDrawable;
import arc.scene.ui.Image;
import arc.scene.ui.layout.Table;
import arc.struct.StringMap;
import arc.util.Log;
import arc.util.Time;
import arc.util.Tmp;
import mindustry.Vars;
import mindustry.content.Blocks;
import mindustry.content.Planets;
import mindustry.core.World;
import mindustry.gen.Tex;
import mindustry.graphics.Pal;
import mindustry.input.Binding;
import mindustry.io.MapIO;
import mindustry.maps.Map;
import mindustry.ui.Fonts;
import mindustry.ui.dialogs.BaseDialog;

import java.io.IOException;

import static mindustry.gen.Tex.windowEmpty;

public class MinimapDialog extends BaseDialog {
    public Element imageTable;

    public MinimapDialog() {
        super("@Minimap");
        addCloseButton();

        shouldPause = false;
        removeChild(titleTable);

        setStyle(new DialogStyle(){{
            stageBackground = ((TextureRegionDrawable) Tex.whiteui).tint(0f, 0f, 0f, 0f);
            titleFont = Fonts.def;
            background = windowEmpty;
            titleFontColor = Pal.accent;
        }});

//        var s = Planets.serpulo.sectors.get(175);
//
//        Log.info(s.save.file);
//
//        try {
//            Map map = MapIO.createMap(s.save.file, true);
//            Pixmap pix = MapIO.generatePreview(map);
//
//            imageTable = new Image(new Texture(pix));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
        imageTable = new Image(Blocks.copperWallLarge.region);


        shown(this::setup);

        addListener(new InputListener(){
            @Override
            public boolean scrolled(InputEvent event, float x, float y, float amountX, float amountY) {
                Vars.renderer.scaleCamera(Core.input.axis(Binding.zoom));
                return true;
            }
        });

        addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Log.info("clicked x:"+ World.toTile(Core.input.mouseWorldX()));
                Log.info("clicked y:"+World.toTile(Core.input.mouseWorldY()));

                super.clicked(event, x, y);
            }

            @Override
            public boolean mouseMoved(InputEvent event, float x, float y) {
                Log.info("clicked x:"+ x);
                Log.info("clicked y:"+ y);

                imageTable.setPosition(x, y);

                return super.mouseMoved(event, x, y);
            }
        });
    }

    void setup(){
        cont.clear();

        cont.add(imageTable);
    }
}
