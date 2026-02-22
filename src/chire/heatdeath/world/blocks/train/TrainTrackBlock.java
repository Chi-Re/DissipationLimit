package chire.heatdeath.world.blocks.train;

import arc.math.geom.Geometry;
import arc.math.geom.Vec2;
import arc.util.Tmp;
import mindustry.gen.Building;
import mindustry.world.Tile;
import mindustry.world.blocks.payloads.Payload;
import mindustry.world.blocks.payloads.PayloadBlock;
import mindustry.world.draw.DrawBlock;

import static arc.math.Mathf.mod;
import static java.lang.Math.abs;
import static mindustry.Vars.tilesize;

public class TrainTrackBlock extends PayloadBlock {
    public DrawBlock drawer;

    public TrainTrackBlock(String name) {
        super(name);
    }

    @Override
    public void load() {
        super.load();
    }

    public class TrainTrackBuild extends PayloadBlockBuild<Payload> {
        @Override
        public void updateTile() {
            super.updateTile();
            if (payload == null) return;
            updatePayload();

            int trns = this.block.size / 2 + 1;
            Vec2 dest = Tmp.v1.trns(rotdeg(), size * tilesize / 2f);
            int velocity = 1;
            payVector.approach(dest, velocity * delta());

            Tile next = tile.nearby(Geometry.d4(rotation).x * trns, Geometry.d4(rotation).y * trns);

            if (payVector.within(dest, 0.001f)) {
                if (next != null && next.build != null && next.build.team == this.team) {
                    if (next.build instanceof TrainTrackBuild build) {
                        if (build.acceptPayload(this, payload)) {
                            build.handlePayload(this, payload);
                            payload = null;
                        }
                    }
                    else {
                        if (next.build.acceptPayload(this, payload)){
                            next.build.handlePayload(this, payload);
                            payload = null;
                        }
                    }
                }
            }
        }

        @Override
        public boolean acceptPayload(Building source, Payload payload){
            return super.acceptPayload(source, payload);
        }

        @Override
        public void draw() {
            super.draw();
            drawPayload();
        }
    }
}
