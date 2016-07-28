package com.mygdx.wingclo.GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
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

    private float buttonSize;
    private int buttonOffsetPx = 15;
    private int screenWidth;

    private static Stage mStage;


    public ShipControls(Ship ship){
        this.ship = ship;

        mStage = new Stage();
        Gdx.input.setInputProcessor(mStage);

        screenWidth = Gdx.graphics.getWidth();

        initTextbuttonStyle();

        accelerateButton = new TextButton("A", circleStyle);
        turnLeftButton = new TextButton("L", circleStyle);
        turnRightButton = new TextButton("R", circleStyle);

        accelerateButton.setPosition(screenWidth - buttonSize - buttonOffsetPx, 0 + buttonOffsetPx);
        turnLeftButton.setPosition(0 + buttonOffsetPx , 0 + buttonOffsetPx);
        turnRightButton.setPosition(buttonSize+buttonOffsetPx*2, 0 + buttonOffsetPx);

        mStage.addActor(accelerateButton);
        mStage.addActor(turnLeftButton);
        mStage.addActor(turnRightButton);
    }

    public void render(){
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        mStage.draw();
        Gdx.gl.glDisable(GL20.GL_BLEND);

        //System.out.println("draw");
    }

    private void initTextbuttonStyle(){
        Sprite baseCircleTest = new Sprite(new Texture(Gdx.files.internal("button_base_circle.png")));
        buttonSize = screenWidth / 10;
        baseCircleTest.setSize(buttonSize, buttonSize);

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/opensansbold.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter param = new FreeTypeFontGenerator.FreeTypeFontParameter();
        param.size = 22;
        param.color = new Color(r/255, g/255, b/255, a);

        circleStyle = new TextButton.TextButtonStyle();
        circleStyle.font = generator.generateFont(param);
        circleStyle.up = new SpriteDrawable(baseCircleTest);
    }
}
