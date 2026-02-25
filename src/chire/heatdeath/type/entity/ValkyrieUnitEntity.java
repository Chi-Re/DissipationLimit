package chire.heatdeath.type.entity;

import arc.util.Log;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.core.World;
import mindustry.gen.UnitEntity;

public class ValkyrieUnitEntity extends UnitEntity {
    //单位上保存的区块内容
    public World unitWorld = new World();

    protected ValkyrieUnitEntity() {
        super();
    }

    public static UnitEntity create() {
        return new ValkyrieUnitEntity();
    }

    @Override
    public void read(Reads read) {
        super.read(read);
    }

    @Override
    public void write(Writes write) {
        super.write(write);
    }
}
