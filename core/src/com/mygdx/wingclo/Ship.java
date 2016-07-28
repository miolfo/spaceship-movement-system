package com.mygdx.wingclo;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Mikko Forsman on 7/28/16.
 */
public class Ship {

    private Vector2 position;
    private Vector2 acceleration = new Vector2(320f,320f);
    private Vector2 deceleration = new Vector2(50f,50f);
    private Vector2 movement = new Vector2(0f,0f);
    private float angle = 0f;
    private float rotationSpeed = 160f;
    private Sprite shipSprite;

    private boolean gravityAffected;
    private float gravityStrength = -120f;

    public Ship(Vector2 initialPosition, Sprite shipSprite){
        this.position = initialPosition;
        this.shipSprite = shipSprite;
    }

    public void setGravityAffected(boolean affected){
        gravityAffected = affected;
    }

    /**
     * Accelerate the ship
     * @param deltaTime Time since last frame
     */
    public void accelerate(float deltaTime){
        movement.x += Math.cos(Math.toRadians(angle -90)) * acceleration.x * deltaTime;
        movement.y += Math.sin(Math.toRadians(angle -90)) * acceleration.y * deltaTime;
        updatePosition(deltaTime);
    }

    /**
     * Decelerate the ship
     * @param deltaTime Time since last frame
     */
    public void decelerate(float deltaTime){
        //Calculate the total movement vector using pythagora
        float vec = (float)Math.sqrt(movement.x* movement.x + movement.y* movement.y);

        //If movement is happening, decelerate
        if(vec > 0){
            movement.x -= (movement.x / vec) * deceleration.x * deltaTime;
            movement.y -= (movement.y / vec) * deceleration.y * deltaTime;
        }

        //TODO: Max speed limitation?
        updatePosition(deltaTime);
    }

    /**
     * Turn the ship
     * @param deltaTime Time since last frame
     * @param direction Positive or negative direction, accepted values 1 and -1
     */
    public void turn(float deltaTime, int direction){
        assert(direction == 1 || direction == -1);
        if(direction == 1){
            angle += rotationSpeed * deltaTime;
        } else{
            angle -= rotationSpeed * deltaTime;
        }
        angle = angle % 360;
        updateRotation();
    }

    public Sprite getShipSprite(){
        return shipSprite;
    }

    private void updatePosition(float deltaTime){

        if(gravityAffected) movement.y += gravityStrength * deltaTime;

        position.x += movement.x * deltaTime;
        position.y += movement.y * deltaTime;

        shipSprite.setPosition(position.x, position.y);
    }

    private void updateRotation(){
        shipSprite.setRotation(angle);
    }
}
