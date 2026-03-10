package chire.heatdeath.world;

import arc.math.geom.Position;
import arc.struct.ArrayMap;
import arc.util.Log;
import arc.util.Nullable;
import chire.heatdeath.world.valkyrie.ValkyrieChunk;

import java.util.*;

public class Chunks implements Iterable<ValkyrieChunk> {
    public final int width, height;

    private final Map<ChunkPosition, ValkyrieChunk> coordChunk = new HashMap<>();

    private final Map<ChunkPosition, ChunkPosition> coordPos = new HashMap<>();

    public Chunks(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void set(int x, int y, ValkyrieChunk chunk){
        coordChunk.put(new ChunkPosition(x, y), chunk);

        for (int i = x; i < x + chunk.size(); i++) {
            for (int j = y; j < y + chunk.size(); j++) {
                coordPos.put(new ChunkPosition(i, j), new ChunkPosition(x, y));
            }
        }
    }

    @Nullable
    public ValkyrieChunk get(int x, int y){
//        return (x < 0 || x >= width || y < 0 || y >= height) ? null :
//                coordChunk.containsKey(new ChunkPosition(x, y)) ? coordChunk.get(new ChunkPosition(x, y)) :
//                    coordPos.containsKey(new ChunkPosition(x, y)) ? coordChunk.get(coordPos.get(new ChunkPosition(x, y))) : null;
        return coordPos.containsKey(new ChunkPosition(x, y)) ? coordChunk.get(coordPos.get(new ChunkPosition(x, y))) : null;
    }

    @Override
    public Iterator<ValkyrieChunk> iterator() {
        return coordChunk.values().iterator();
    }

    public static class ChunkPosition implements Position {
        float x, y;

        public ChunkPosition(float x, float y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public float getX() {
            return x;
        }

        @Override
        public float getY() {
            return y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof ChunkPosition)) return false;
            ChunkPosition point3D = (ChunkPosition) o;
            return x == point3D.x && y == point3D.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }
}
