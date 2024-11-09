package com.example.mytetris.models;

import android.graphics.Color;
import android.graphics.Point;

import androidx.annotation.NonNull;

import com.example.mytetris.R;
import com.example.mytetris.constants.FieldConstants;

import java.util.Random;

public class Block {

    /***
     * Переменная shapeIndex включает индекс формы блока;
     * переменная frameNumber отслеживает количество фреймов, относящихся к форме блока;
     * переменная color содержит цветовые характеристики блока;
     * переменная position используется для отслеживания текущей
     * пространственной позиции блока в игровом поле.
     */

    private final int shapeIdx;
    private int frameNumber;
    private final BlockColor color;
    private Point position;

    private Block(int shapeIdx, BlockColor blockColor) {
        this.frameNumber = 0;
        this.shapeIdx = shapeIdx;
        this.color = blockColor;
        this.position = new Point(FieldConstants.COLUMN_COUNT.getValue() / 2, 0);
    }

    public static Block createBlock() {
        Random random = new Random();
        int shapeIndex = random.nextInt(Shape.values().length);
        BlockColor blockColor = BlockColor.values()
                [random.nextInt(BlockColor.values().length)];
        Block block = new Block(shapeIndex, blockColor);
        block.position.x = block.position.x - Shape.values()
                [shapeIndex].getStartPosition();
        return block;
    }

    public static int getColor(byte value) {
        for (BlockColor colour : BlockColor.values()) {
            if (value == colour.byteValue)
                return colour.rgbValue;
        }
        return -1;
    }

    public final void setState(int frame, Point position) {
        this.frameNumber = frame;
        this.position = position;
    }

    @NonNull
    public final byte[][]getShape(int frameNumber) {
        return Shape.values()[shapeIdx].getFrame(
            frameNumber).as2dByteArray();
    }

    public Point getPosition() {
        return this.position;
    }

    public final int getFrameCount() {
        return Shape.values()[shapeIdx].getFrameCount();
    }

    public int getFrameNumber() {
        return frameNumber;
    }

    public int getColor() {
        return color.rgbValue;
    }

    public byte getStaticValue() {
        return color.byteValue;
    }

    public enum BlockColor {
        PINK(Color.rgb(255, 105, 180), (byte) 2),
        GREEN(Color.rgb(0, 128, 0), (byte) 3),
        ORANGE(Color.rgb(255, 140, 0), (byte) 4),
        YELLOW(Color.rgb(255, 255, 0), (byte) 5),
        CYAN(Color.rgb(0, 255, 255), (byte) 6);
        BlockColor(int rgbValue, byte value) {
            this.rgbValue = rgbValue;
            this.byteValue = value;
        }

        private final int rgbValue;
        private final  byte byteValue;
    }
}
