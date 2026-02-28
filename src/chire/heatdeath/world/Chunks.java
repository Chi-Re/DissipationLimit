package chire.heatdeath.world;

import arc.util.Log;
import arc.util.Nullable;
import chire.heatdeath.world.valkyrie.ValkyrieChunk;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;

public class Chunks implements Iterable<ValkyrieChunk> {
    public final int width, height;

    final ValkyrieChunk[] array;

    final int[][] position;

    public Chunks(int width, int height) {
        this.width = width;
        this.height = height;
        this.array = new ValkyrieChunk[width * height];
        this.position = new int[width][height];
    }

    public void set(int x, int y, ValkyrieChunk chunk){
        array[y*width + x] = chunk;
        position[x][y] = y*width + x;

        for (int i = x; i < x + chunk.build.block.size; i++) {
            for (int j = y; j < y + chunk.build.block.size; j++) {
                position[i][j] = position[x][y];
                //Log.warn(String.format("set %d %d", i, j)+" is "+chunk.build.block.name);
            }
        }
    }

    @Nullable
    public ValkyrieChunk get(int x, int y){
        return (x < 0 || x >= width || y < 0 || y >= height) ? null :
                array[y*width + x] == null ? array[position[x][y]] : array[y*width + x];
    }

    @Override
    public Iterator<ValkyrieChunk> iterator() {
        return new ChunkIterator();
    }

    private class ChunkIterator implements Iterator<ValkyrieChunk>{
        int index = 0;

        ChunkIterator(){
        }

        @Override
        public boolean hasNext(){
            return index < array.length;
        }

        @Override
        public ValkyrieChunk next(){
            return array[index++];
        }
    }
}
