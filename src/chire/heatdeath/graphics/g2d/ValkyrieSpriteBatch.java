package chire.heatdeath.graphics.g2d;

import arc.graphics.g2d.SpriteBatch;
import arc.graphics.g2d.TextureRegion;

public class ValkyrieSpriteBatch extends SpriteBatch {
    private float rotation = -1;

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    @Override
    protected void draw(TextureRegion region, float x, float y, float originX, float originY, float width, float height, float rotation) {
        super.draw(region, x, y, originX, originY, width, height, this.rotation == -1 ? rotation : this.rotation);
    }

    public void endRotation(){
        this.rotation = -1;
    }
}
