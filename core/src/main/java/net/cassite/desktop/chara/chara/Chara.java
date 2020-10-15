// ***LICENSE*** This file is licensed under GPLv2 with Classpath Exception. See LICENSE file under project root for more info

package net.cassite.desktop.chara.chara;

import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyEvent;

import java.util.Objects;

public interface Chara {
    void ready(ReadyParams params);

    void mouseMove(double x, double y);

    void mouseLeave();

    void dragged();

    void click(double x, double y);

    void keyPressed(KeyEvent e);

    void keyReleased(KeyEvent e);

    Data data();

    void release();

    void takeMessage(String msg);

    boolean getDebugInfo(ClipboardContent content);

    void takeDebugMessage(Clipboard clipboard);

    class ReadyParams {
        public ReadyParams() {
        }
    }

    class Data {
        public final int imageWidth;
        public final int imageHeight;
        public final int minWidth;
        public final int initialWidth;
        public final int minX;
        public final int maxX;
        public final int topMiddleX;
        public final int bottomMiddleX;
        public final int messageOffsetX;
        public final int messageAtMinY;
        public final int minY;
        public final int maxY;

        public final boolean messageSupported;
        public final boolean activeInteractionSupported;

        private Data(int imageWidth,
                     int imageHeight,
                     int minWidth,
                     int initialWidth,
                     int minX,
                     int maxX,
                     int topMiddleX,
                     int bottomMiddleX,
                     int messageOffsetX,
                     int messageAtMinY,
                     int minY,
                     int maxY,
                     boolean messageSupported,
                     boolean activeInteractionSupported) {
            this.imageWidth = imageWidth;
            this.imageHeight = imageHeight;
            this.minWidth = minWidth;
            this.initialWidth = initialWidth;
            this.minX = minX;
            this.maxX = maxX;
            this.topMiddleX = topMiddleX;
            this.bottomMiddleX = bottomMiddleX;
            this.messageOffsetX = messageOffsetX;
            this.messageAtMinY = messageAtMinY;
            this.minY = minY;
            this.maxY = maxY;
            this.messageSupported = messageSupported;
            this.activeInteractionSupported = activeInteractionSupported;
        }
    }

    class DataBuilder {
        private Integer imageWidth;
        private Integer imageHeight;
        private Integer minWidth;
        private Integer initialWidth;
        private Integer minX;
        private Integer maxX;
        private Integer topMiddleX;
        private Integer bottomMiddleX;
        private Integer messageOffsetX;
        private Integer messageAtMinY;
        private Integer minY;
        private Integer maxY;
        private boolean messageSupported = false;
        private boolean activeInteractionSupported = false;

        public Data build() {
            Objects.requireNonNull(imageWidth, "imageWidth");
            Objects.requireNonNull(imageHeight, "imageHeight");
            Objects.requireNonNull(minX, "minX");
            Objects.requireNonNull(maxX, "maxX");
            Objects.requireNonNull(topMiddleX, "topMiddleX");
            Objects.requireNonNull(bottomMiddleX, "bottomMiddleX");
            Objects.requireNonNull(messageOffsetX, "messageOffsetX");
            Objects.requireNonNull(messageAtMinY, "messageAtMinY");
            Objects.requireNonNull(minY, "minY");
            Objects.requireNonNull(maxY, "maxY");
            return new Data(
                imageWidth,
                imageHeight,
                minWidth,
                initialWidth,
                minX,
                maxX,
                topMiddleX,
                bottomMiddleX,
                messageOffsetX,
                messageAtMinY,
                minY,
                maxY,
                messageSupported,
                activeInteractionSupported
            );
        }

        public DataBuilder setImageWidth(int imageWidth) {
            this.imageWidth = imageWidth;
            return this;
        }

        public DataBuilder setImageHeight(int imageHeight) {
            this.imageHeight = imageHeight;
            return this;
        }

        public DataBuilder setMinWidth(int minWidth) {
            this.minWidth = minWidth;
            return this;
        }

        public DataBuilder setInitialWidth(int initialWidth) {
            this.initialWidth = initialWidth;
            return this;
        }

        public DataBuilder setMinX(int minX) {
            this.minX = minX;
            return this;
        }

        public DataBuilder setMaxX(int maxX) {
            this.maxX = maxX;
            return this;
        }

        public DataBuilder setTopMiddleX(int topMiddleX) {
            this.topMiddleX = topMiddleX;
            return this;
        }

        public DataBuilder setBottomMiddleX(int bottomMiddleX) {
            this.bottomMiddleX = bottomMiddleX;
            return this;
        }

        public DataBuilder setMessageOffsetX(int messageOffsetX) {
            this.messageOffsetX = messageOffsetX;
            return this;
        }

        public DataBuilder setMessageAtMinY(int messageAtMinY) {
            this.messageAtMinY = messageAtMinY;
            return this;
        }

        public DataBuilder setMinY(int minY) {
            this.minY = minY;
            return this;
        }

        public DataBuilder setMaxY(int maxY) {
            this.maxY = maxY;
            return this;
        }

        public DataBuilder setMessageSupported(boolean messageSupported) {
            this.messageSupported = messageSupported;
            return this;
        }

        public DataBuilder setActiveInteractionSupported(boolean activeInteractionSupported) {
            this.activeInteractionSupported = activeInteractionSupported;
            return this;
        }
    }
}
