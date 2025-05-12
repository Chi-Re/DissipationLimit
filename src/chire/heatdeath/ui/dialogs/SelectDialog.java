package chire.heatdeath.ui.dialogs;

import arc.Core;
import arc.input.KeyCode;
import arc.math.geom.Vec2;
import arc.scene.event.ClickListener;
import arc.scene.event.InputEvent;
import arc.scene.ui.Dialog;
import arc.struct.Seq;
import arc.util.Log;
import arc.util.Time;
import arc.util.Timer;
import chire.heatdeath.DissipationLimit;
import mindustry.Vars;
import mindustry.content.Fx;
import mindustry.core.World;
import mindustry.gen.Call;
import mindustry.gen.Unit;
import mindustry.input.Binding;
import mindustry.type.UnitType;
import mindustry.ui.Fonts;
import mindustry.ui.Styles;
import mindustry.ui.dialogs.BaseDialog;

import static mindustry.Vars.*;
import static mindustry.gen.Tex.windowEmpty;

public class SelectDialog extends BaseDialog {

    public Seq<UnitType> plans = new Seq<>();

    private int clickedTimer = 0;

    private Runnable timeTimer = () -> Time.run(20f, () -> {
        if (!this.isShown()) return;
        if (clickedTimer == 0) return;
        clickedTimer = 0;
    });

    public SelectDialog(String title) {
        super(title);

        titleTable.remove();
        titleTable.clear();

        setStyle(new DialogStyle(){{
            stageBackground = Styles.none;
            titleFont = Fonts.def;
            background = windowEmpty;
        }});

        addListener(new ClickListener(){
            @Override
            public boolean mouseMoved(InputEvent event, float x, float y) {
                Core.graphics.cursor(DissipationLimit.target);

                return super.mouseMoved(event, x, y);
            }

            @Override
            public boolean scrolled(InputEvent event, float x, float y, float amountX, float amountY) {

                if(Math.abs(Core.input.axisTap(Binding.zoom)) > 0 && Vars.state != null) {
                    Vars.renderer.scaleCamera(Core.input.axisTap(Binding.zoom));
                }

                return super.scrolled(event, x, y, amountX, amountY);
            }

            @Override
            public void clicked(InputEvent event, float x, float y) {
                clickedTimer += 1;
                timeTimer.run();

                if (clickedTimer >= 2) {
                    doShockwave(Core.input.mouseWorld());
                    spawnEffect(plans.get(0).spawn(player.team(), Core.input.mouseWorld()));
                    clickedTimer = 0;
                }

                super.clicked(event, x, y);
            }


        });

        addCloseListener();
    }

    @Override
    public Dialog show() {
        ui.hudfrag.shown = false;
        return super.show();
    }

    @Override
    public void hide() {
        ui.hudfrag.shown = true;
        super.hide();
    }

    public void addDeploy(UnitType plan) {
        plans.add(plan);
    }

    public int rawTileX(){
        return World.toTile(Core.input.mouseWorld().x);
    }

    public int rawTileY(){
        return World.toTile(Core.input.mouseWorld().y);
    }

    public void doShockwave(Vec2 vec2){
        Fx.spawnShockwave.at(vec2, state.rules.dropZoneRadius);
        //Damage.damage(state.rules.waveTeam, x, y, state.rules.dropZoneRadius, 99999999f, true);
    }

    public void spawnEffect(Unit unit){
        spawnEffect(unit, unit.angleTo(world.width()/2f * tilesize, world.height()/2f * tilesize));
    }

    public void spawnEffect(Unit unit, float rotation){
        unit.rotation = rotation;
//        unit.apply(StatusEffects.unmoving, 30f);
//        unit.apply(StatusEffects.invincible, 60f);
//        unit.add();
//        unit.unloaded();
        Call.spawnEffect(unit.x, unit.y, unit.rotation, unit.type);
    }
}
