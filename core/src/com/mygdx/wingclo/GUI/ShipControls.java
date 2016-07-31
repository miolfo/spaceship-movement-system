package com.mygdx.wingclo.GUI;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.mygdx.wingclo.MainGame;
import com.mygdx.wingclo.Ship;

import java.awt.*;

/**
 * Created by Mikko Forsman on 7/28/16.
 * UI class for ship controls on Android
 */

public class ShipControls {

    private TextButton accelerateButton;
    private TextButton turnLeftButton, turnRightButton;

    private float r = 45f, g = 45f, b = 45f, a = 0.8f;     //Font color
    private TextButton.TextButtonStyle circleStyle;
    private Ship ship;

    private final String ACCELERATE_BUTTON_NAME = "accelerate";
    private final String LEFT_BUTTON_NAME = "left";
    private final String RIGHT_BUTTON_NAME = "right";
    private ClickListener accListener = new ClickListener();
    private ClickListener leftListener = new ClickListener();
    private ClickListener rightListener = new ClickListener();

    private float buttonSize;
    private int buttonOffsetPx = 15;
    private int screenWidth;

    private boolean useTouchInputs;

    private static Stage mStage;


    public ShipControls(Ship ship){
        this.ship = ship;

        if(Gdx.app.getType() == Application.ApplicationType.Android){
            useTouchInputs = true;
        } else if(Gdx.app.getType() == Application.ApplicationType.Desktop){
            useTouchInputs = false;
        }

        if(useTouchInputs) {
            mStage = new Stage();
            Gdx.input.setInputProcessor(mStage);

            screenWidth = Gdx.graphics.getWidth();

            initTextbuttonStyle();

            accelerateButton = new TextButton("A", circleStyle);
            turnLeftButton = new TextButton("L", circleStyle);
            turnRightButton = new TextButton("R", circleStyle);

            accelerateButton.setPosition(screenWidth - buttonSize - buttonOffsetPx, 0 + buttonOffsetPx);
            turnLeftButton.setPosition(0 + buttonOffsetPx, 0 + buttonOffsetPx);
            turnRightButton.setPosition(buttonSize + buttonOffsetPx * 2, 0 + buttonOffsetPx);

            //Add names to distinguish between buttons in inputListener
            accelerateButton.setName(ACCELERATE_BUTTON_NAME);
            turnRightButton.setName(RIGHT_BUTTON_NAME);
            turnLeftButton.setName(LEFT_BUTTON_NAME);

            accelerateButton.addListener(accListener);
            turnLeftButton.addListener(leftListener);
            turnRightButton.addListener(rightListener);

            mStage.addActor(accelerateButton);
            mStage.addActor(turnLeftButton);
            mStage.addActor(turnRightButton);
        }
    }

    public void render(){
        if(useTouchInputs)handleInputsTouch();
        else handleInputsKeyboard();
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        if(useTouchInputs) mStage.draw();
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    private void handleInputsTouch(){
        float deltaTime = Gdx.graphics.getDeltaTime();
        if(accListener.isPressed()){
            ship.accelerate(deltaTime);
        } else{
            ship.decelerate(deltaTime);
        }
        if(leftListener.isPressed()){
            ship.turn(deltaTime, 1);
        }
        if(rightListener.isPressed()){
            ship.turn(deltaTime, -1);
        }
    }

    private void handleInputsKeyboard(){
        float deltaTime = Gdx.graphics.getDeltaTime();
        if(Gdx.input.isKeyPressed(Input.Keys.D)){
            ship.turn(deltaTime, -1);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.A)){
            ship.turn(deltaTime, 1);
        }

        //Add to the velocity if W is pressed
        if(Gdx.input.isKeyPressed(Input.Keys.W)){
            ship.accelerate(deltaTime);
        }

        //If not, decelerate
        else{
            ship.decelerate(deltaTime);
        }
    }

    private void initTextbuttonStyle(){
        Sprite baseCircleTest = new Sprite(new Texture(Gdx.files.internal("button_base_circle.png")));
        buttonSize = screenWidth / 10;
        baseCircleTest.setSize(buttonSize, buttonSize);

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/opensansbold.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter param = new FreeTypeFontGenerator.FreeTypeFontParameter();
        //Scale the font size to different screens
        param.size = screenWidth * 26 / 640;
        param.color = new Color(r/255, g/255, b/255, a);

        circleStyle = new TextButton.TextButtonStyle();
        circleStyle.font = generator.generateFont(param);
        circleStyle.up = new SpriteDrawable(baseCircleTest);
    }
}
