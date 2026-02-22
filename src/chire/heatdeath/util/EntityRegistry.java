package chire.heatdeath.util;

import arc.func.Func;
import arc.func.Prov;
import arc.struct.ObjectIntMap;
import arc.struct.ObjectMap;
import arc.util.Structs;
import mindustry.gen.EntityMapping;
import mindustry.gen.Entityc;
import mindustry.gen.Unit;
import mindustry.gen.UnitEntity;
import mindustry.type.UnitType;

import static chire.heatdeath.DissipationLimit.name;

@SuppressWarnings("unchecked")
public final class EntityRegistry {
    private static final ObjectIntMap<Class<? extends Unit>> ids = new ObjectIntMap<>();

    private static final ObjectMap<String, Prov<? extends Unit>> map = new ObjectMap<>();

    private EntityRegistry() {
        throw new AssertionError();
    }

    public static <T extends Unit> Prov<T> get(Class<T> type) {
        return get(type.getCanonicalName());
    }

    public static <T extends Unit> Prov<T> get(String name) {
        return (Prov<T>)map.get(name);
    }

    public static <T extends Unit> void register(String name, Class<T> type,
            Prov<? extends T> prov) {
        map.put(name, prov);
        ids.put(type, EntityMapping.register(name, prov));
    }

    public static <T extends UnitType> T content(String name, Prov<? extends Unit> type,
                                                 Func<String, ? extends T> create) {
        if(type.get().getClass().getName().startsWith("mindustry.gen.")) {
            var prov = Structs.find(EntityMapping.idMap, p -> p != null && p.get().getClass().equals(type));
            EntityMapping.nameMap.put(name(name), prov);
        } else {
            EntityMapping.nameMap.put(name(name), get(type.get().getClass()));
        }
        T unit = create.get(name);
        unit.constructor = type;
        return unit;
    }

    public static int getID(Class<? extends Unit> type) {
        return ids.get(type, -1);
    }

    public static void register() {
    }
}
