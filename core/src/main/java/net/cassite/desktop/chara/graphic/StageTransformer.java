// ***LICENSE*** This file is licensed under GPLv2 with Classpath Exception. See LICENSE file under project root for more info

package net.cassite.desktop.chara.graphic;

import javafx.stage.Screen;
import javafx.stage.Stage;
import net.cassite.desktop.chara.manager.ConfigManager;
import net.cassite.desktop.chara.util.Logger;
import net.cassite.desktop.chara.util.Utils;

/**
 * The stage transformer which holds a stage and manages relative coordinates and scales.
 */
public class StageTransformer {
    private final Stage stage;
    private final double originalWidth;
    private final double originalHeight;
    private final double cutLeft;
    private final double cutRight;
    private final double cutTop;
    private final double cutBottom;
    private final double addAbsoluteTop;

    private double scaleRatio = 1;

    public StageTransformer(Stage stage,
                            double originalWidth,
                            double originalHeight,
                            double cutLeft,
                            double cutRight,
                            double cutTop,
                            double cutBottom,
                            double addAbsoluteTop) {
        this.stage = stage;
        this.originalWidth = originalWidth;
        this.originalHeight = originalHeight;
        this.cutLeft = cutLeft;
        this.cutRight = cutRight;
        this.cutTop = cutTop;
        this.cutBottom = cutBottom;
        this.addAbsoluteTop = addAbsoluteTop;

        stage.setX(0);
        stage.setY(0);
        stage.setWidth(originalWidth - cutLeft - cutRight);
        stage.setHeight(originalHeight - cutBottom - cutTop);
    }

    /**
     * Retrieve the held stage
     *
     * @return stage
     */
    public Stage getStage() {
        return stage;
    }

    private void debugStage(String reason) {
        assert Logger.debug("StageTransformer::" + reason + "::[" + stage.getWidth() + "," + stage.getHeight() + "]+(" + stage.getX() + "," + stage.getY() + ")");
    }

    private void moveAbsolute(double deltaX, double deltaY) {
        stage.setX(stage.getX() + deltaX);
        stage.setY(stage.getY() + deltaY);
    }

    /**
     * Scale the stage at real position (0, 0)
     *
     * @param ratio scale ratio
     */
    public void scale(double ratio) {
        scaleAt(cutLeft, cutTop, ratio);
    }

    /**
     * Scale the stage at specified position
     *
     * @param originalOldScaleAtX scale at x pos of the original image
     * @param originalOldScaleAtY scale at y pos of the original image
     * @param ratio               scaling ratio
     */
    public void scaleAt(double originalOldScaleAtX, double originalOldScaleAtY, double ratio) {
        assert Logger.debug("scaleAt(" + originalOldScaleAtX + "," + originalOldScaleAtY + "," + ratio + ")");

        double w = (originalWidth - cutLeft - cutRight) * ratio;
        double h = (originalHeight - cutBottom - cutTop) * ratio;
        stage.setWidth(w);
        stage.setHeight(h + addAbsoluteTop);

        double actualOldScaleAtX = (originalOldScaleAtX - cutLeft) * this.scaleRatio;
        double actualOldScaleAtY = (originalOldScaleAtY - cutTop) * this.scaleRatio;
        double actualNewScaleAtX = (originalOldScaleAtX - cutLeft) * ratio;
        double actualNewScaleAtY = (originalOldScaleAtY - cutTop) * ratio;
        moveAbsolute(actualOldScaleAtX - actualNewScaleAtX,
            actualOldScaleAtY - actualNewScaleAtY);

        this.scaleRatio = ratio;

        debugStage("scaleAt");
    }

    /**
     * Move the stage to the center of the screen
     */
    public void centerOnScreen() {
        assert Logger.debug("centerOnScreen()");

        double centerX = stage.getX() + stage.getWidth() / 2;
        double centerY = stage.getY() + stage.getHeight() / 2;
        Screen screen = getScreen();
        var bounds = screen.getBounds();
        double screenCenterX = bounds.getWidth() / 2;
        double screenCenterY = bounds.getHeight() / 2;

        moveAbsolute(screenCenterX - centerX, screenCenterY - centerY);

        debugStage("centerOnScreen");
    }

    /**
     * Get current scaling ratio
     *
     * @return scaleRatio
     */
    public double getScaleRatio() {
        return scaleRatio;
    }

    /**
     * Get absolute x relative to the screen
     *
     * @return stage.x
     */
    public double getAbsoluteX() {
        return stage.getX();
    }

    /**
     * Get absolute y relative to the screen
     *
     * @return stage.y
     */
    public double getAbsoluteY() {
        return stage.getY();
    }

    /**
     * Set absolute x relative to the screen
     *
     * @param x x
     */
    public void setAbsoluteX(double x) {
        stage.setX(x);
    }

    /**
     * Set absolute y relative to the screen
     *
     * @param y y
     */
    public void setAbsoluteY(double y) {
        stage.setY(y);
    }

    /**
     * Save stage x, y and ratio to the config file
     */
    public void saveConfig() {
        ConfigManager.get().setStageX(stage.getX());
        ConfigManager.get().setStageY(stage.getY());
        ConfigManager.get().setCharacterRatio(scaleRatio);
    }

    private Screen lastRetrievedScreen;

    // ----------- THE FOLLOWING CODE IS THE SAME AS {@link UStage#getScreen()} -----------
    // ----------- MAKE SURE ANY CHANGE TO THIS CODE ALSO APPLY THERE -----------

    /**
     * Retrieve the screen that is showing the stage.<br>
     * If not retrieved, the primary screen will return.
     *
     * @return the showing screen or the primary screen
     */
    @SuppressWarnings("DuplicatedCode")
    public Screen getScreen() {
        Screen s = Utils.getScreen(stage.getX() + stage.getWidth() / 2, stage.getY() + stage.getHeight() / 2);
        if (s == null) {
            if (lastRetrievedScreen == null) {
                assert Logger.debug("getScreen returns primary");
                return Screen.getPrimary();
            }
            assert Logger.debug("getScreen returns lastReturnedScreen: " + lastRetrievedScreen);
            return lastRetrievedScreen;
        }
        lastRetrievedScreen = s;
        return s;
    }
    // ----------- THE UPPER CODE IS THE SAME AS {@link UStage#getScreen()} -----------
    // ----------- MAKE SURE ANY CHANGE TO THIS CODE ALSO APPLY THERE -----------

    /**
     * Get pixels of image cut from the left
     *
     * @return cutLeft
     */
    public double getCutLeft() {
        return cutLeft;
    }

    /**
     * Get pixels of image cut from the right
     *
     * @return cutRight
     */
    public double getCutRight() {
        return cutRight;
    }

    /**
     * Get pixels of image cut from the top
     *
     * @return cutTop
     */
    public double getCutTop() {
        return cutTop;
    }

    /**
     * Get pixels of image cut from the bottom
     *
     * @return cutBottom
     */
    public double getCutBottom() {
        return cutBottom;
    }

    /**
     * Get real pixels added to the top
     *
     * @return addAbsolute top
     */
    public double getAddAbsoluteTop() {
        return addAbsoluteTop;
    }

    /**
     * Get image x by real x relative to the scene
     *
     * @param x real x
     * @return calculated image x
     */
    public double getImageXBySceneX(double x) {
        x /= getScaleRatio();
        x += getCutLeft();
        return x;
    }

    /**
     * Get image y by real y relative to the scene
     *
     * @param y real y
     * @return calculated image y
     */
    public double getImageYBySceneY(double y) {
        y -= getAddAbsoluteTop();
        y /= getScaleRatio();
        y += getCutTop();
        return y;
    }

    /**
     * Get real x relative to the scene by image x
     *
     * @param x image x
     * @return calculated real x
     */
    public double getSceneXByImageX(double x) {
        x -= getCutLeft();
        x *= getScaleRatio();
        return x;
    }

    /**
     * Get real y relative to the scene by image y
     *
     * @param y image y
     * @return calculated real y
     */
    public double getSceneYByImageY(double y) {
        y -= getCutTop();
        y *= getScaleRatio();
        y += getAddAbsoluteTop();
        return y;
    }

    /**
     * Get real x relative to the scene by screen x
     *
     * @param x screen x
     * @return calculated real x
     */
    public double getSceneXByScreenX(double x) {
        return x - getAbsoluteX();
    }

    /**
     * Get real y relative to the scene by screen x
     *
     * @param y screen y
     * @return calculated real y
     */
    public double getSceneYByScreenY(double y) {
        return y - getAbsoluteY();
    }
}
