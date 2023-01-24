package dev.webfx.demo.tallycounter;

import dev.webfx.extras.led.Led;
import dev.webfx.kit.util.scene.DeviceSceneUtil;
import dev.webfx.platform.storage.LocalStorage;
import dev.webfx.platform.util.Booleans;
import dev.webfx.platform.util.Numbers;
import eu.hansolo.fx.odometer.Odometer;
import eu.hansolo.fx.odometer.OdometerBuilder;
import javafx.animation.*;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;


/**
 * @author Bruno Salmon
 */
public final class TallyCounterApplication extends Application {

    private int counter = 0, beforeResetCounter;
    private Odometer odometer;
    private Timeline odometerTimeline, swapTimeline;
    private boolean swapped;
    private final Led incrementButton = Led.create(Color.GREEN.darker(),  true,  this::increment);
    private final Led decrementButton = Led.create(Color.ORANGE.darker(), false, this::decrement);
    private final Led resetButton     = Led.create(Color.RED.darker(),    null,  this::reset);
    private final Led swapButton      = Led.create(Color.BLUE.darker(),   null,  this::swap);
    private double leftButtonX, rightButtonX;

    @Override
    public void start(Stage stage) {
        loadState();
        Scene scene = DeviceSceneUtil.newScene(createTallyCounterPane(), 800, 600, Color.BLACK);
        stage.setTitle("Tally Counter");
        stage.setScene(scene);
        stage.show();
    }

    private Pane createTallyCounterPane() {
        odometer = OdometerBuilder.create()
                .digits(4)
                .decimals(0)
                .digitBackgroundColor(Color.BLACK)
                .digitForegroundColor(Color.WHITE)
                .decimalBackgroundColor(Color.BLACK)
                .decimalForegroundColor(Color.WHITE)
                .value(counter)
                .build();
        odometer.setPrefHeight(Region.USE_COMPUTED_SIZE);
        odometer.setPrefWidth(Region.USE_COMPUTED_SIZE);
        double gap = 10;
        Pane pane = new Pane(odometer, decrementButton, incrementButton, resetButton, swapButton) {
            @Override
            protected void layoutChildren() {
                boolean swapAnimationRunning = swapTimeline != null && swapTimeline.getStatus() == Animation.Status.RUNNING;
                Insets insets = getInsets();
                final double left = insets.getLeft();
                final double width = getWidth() - left - insets.getRight();
                final double top = insets.getTop();
                final double height = getHeight() - top - insets.getBottom();
                double odometerHeight = Math.min(height, 300);
                double odometerWidth = odometer.prefWidth(odometerHeight);
                double extraWidth = width - odometerWidth;
                double extraHeight = height - odometerHeight;
                double buttonSize;
                Led leftButton = swapped ? incrementButton : decrementButton;
                Led rightButton = swapped ? decrementButton : incrementButton;
                if (extraWidth > 1.61 * extraHeight) {
                    if (odometerWidth > 0.6 * width) {
                        odometerWidth = 0.6 * width;
                        odometerHeight = odometer.prefHeight(odometerWidth);
                        extraWidth = width - odometerWidth;
                    }
                    buttonSize = Math.min(300, Math.min(extraWidth / 2 - gap, height));
                    double baseline = top + height / 2.61;
                    layoutInArea(odometer, left + extraWidth / 2, baseline - odometerHeight / 2, odometerWidth, odometerHeight, 0, HPos.CENTER, VPos.CENTER);
                    leftButtonX = left + extraWidth / 4 - buttonSize / 2 - gap / 2;
                    rightButtonX = left + width - extraWidth / 4 - buttonSize / 2 + gap / 2;
                    if (!swapAnimationRunning) {
                        layoutInArea(leftButton, leftButtonX, baseline - buttonSize / 2, buttonSize, buttonSize, 0, HPos.CENTER, VPos.CENTER);
                        layoutInArea(rightButton, rightButtonX, baseline - buttonSize / 2, buttonSize, buttonSize, 0, HPos.CENTER, VPos.CENTER);
                    }
                } else {
                    odometerWidth = width;
                    odometerHeight = odometer.prefHeight(width);
                    double maxHeight = Math.min(300, 0.6 * height);
                    if (odometerHeight > maxHeight) {
                        odometerHeight = maxHeight;
                        odometerWidth = odometer.prefWidth(odometerHeight);
                    }
                    extraWidth = width - odometerWidth;
                    extraHeight = height - odometerHeight;
                    buttonSize = Math.min(200, Math.min(odometerWidth / 3, extraHeight));
                    double spaceY = height - odometerHeight - buttonSize;
                    layoutInArea(odometer, left + extraWidth / 2, top + spaceY / 3, odometerWidth, odometerHeight, 0, HPos.CENTER, VPos.CENTER);
                    double buttonsY = top + spaceY / 3 + odometerHeight + spaceY / 3;
                    double leftButtonCenterX = left + extraWidth / 2 + odometerWidth / 4 - gap / 2;
                    leftButtonX = leftButtonCenterX - buttonSize / 2;
                    rightButtonX = leftButtonX + odometerWidth / 2 + gap;
                    double distanceFromCorner = distance(0, getHeight(), leftButtonCenterX, buttonsY + buttonSize / 2);
                    double distanceFromRight = odometerWidth / 2 + gap;
                    double distanceDiff = distanceFromRight - distanceFromCorner;
                    if (distanceDiff > 0) {
                        double delta = distanceDiff / 2 / Math.sqrt(2);
                        leftButtonX += delta;
                        rightButtonX -= delta;
                    }
                    if (!swapAnimationRunning) {
                        layoutInArea(leftButton, leftButtonX, buttonsY, buttonSize, buttonSize, 0, HPos.CENTER, VPos.CENTER);
                        layoutInArea(rightButton, rightButtonX, buttonsY, buttonSize, buttonSize, 0, HPos.CENTER, VPos.CENTER);
                    }
                }
                double cornerButtonY = getHeight() - buttonSize / 2;
                layoutInArea(resetButton, 0 - buttonSize / 2, cornerButtonY, buttonSize, buttonSize, 0, HPos.CENTER, VPos.CENTER);
                layoutInArea(swapButton, getWidth() - buttonSize / 2, cornerButtonY, buttonSize, buttonSize, 0, HPos.CENTER, VPos.CENTER);
            }
        };
        pane.setPadding(new Insets(gap));
        pane.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        return pane;
    }

    private void increment() {
        setCounter(counter + 1);
    }

    private void decrement() {
        setCounter(counter - 1);
    }

    private void reset() {
        if (counter == 0)
            setCounter(beforeResetCounter);
        else {
            beforeResetCounter = counter;
            setCounter(0);
        }
    }

    private void swap() {
        if (swapTimeline != null)
            swapTimeline.stop();
        Led leftButton = swapped ? incrementButton : decrementButton;
        Led rightButton = swapped ? decrementButton : incrementButton;
        swapTimeline = new Timeline(new KeyFrame(Duration.seconds(0.5),
                new KeyValue(leftButton.layoutXProperty(), rightButtonX, Interpolator.EASE_OUT),
                new KeyValue(rightButton.layoutXProperty(), leftButtonX, Interpolator.EASE_OUT)));
        swapTimeline.play();
        swapped = !swapped;
        saveState();
    }

    private void setCounter(int counter) {
        if (odometerTimeline != null)
            odometerTimeline.stop();
        this.counter = Math.max(counter, 0);
        odometerTimeline = new Timeline(new KeyFrame(Duration.seconds(0.2),
                new KeyValue(odometer.valueProperty(), this.counter, Interpolator.LINEAR)));
        odometerTimeline.play();
        saveState();
    }

    private void loadState() {
        counter = Numbers.intValue(LocalStorage.getItem("counter"));
        beforeResetCounter = Numbers.intValue(LocalStorage.getItem("beforeResetCounter"));
        swapped = Booleans.booleanValue(LocalStorage.getItem("swapped"));
    }

    private void saveState() {
        LocalStorage.setItem("counter", String.valueOf(counter));
        LocalStorage.setItem("beforeResetCounter", String.valueOf(beforeResetCounter));
        LocalStorage.setItem("swapped", String.valueOf(swapped));
    }

    private double distance(double x1, double y1, double x2, double y2) {
        double a = x1 - x2, b = y1 - y2;
        return Math.sqrt(a * a + b * b);
    }
}
