package chire.heatdeath.graphics.g2d;

import arc.graphics.g2d.SpriteBatch;
import arc.graphics.g2d.TextureRegion;
import arc.util.Log;
import mindustry.gen.Unit;
import mindustry.graphics.Layer;

public class ValkyrieSpriteBatch extends SpriteBatch {
    private Unit unit;

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    @Override
    protected void draw(TextureRegion region, float x, float y, float originX, float originY, float width, float height, float rotation) {
        super.draw(region, x, y, originX, originY, width, height, rotation);
    }

    @Override
    protected void z(float z){
        if (this.unit != null) {
            super.z(Layer.flyingUnitLow);
//            Log.warn("Valkyrie sprite batch z: "+z);
//            if (z > Layer.plans && z < Layer.flyingUnitLow) {
//                super.z(Layer.flyingUnitLow + (z - Layer.plans));
//            } else {
//                super.z(Layer.flyingUnitLow);
//            }
        } else {
            super.z(z);
        }
    }

    public void end(){
        this.unit = null;
    }
}
