package com.mygdx.wingclo;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class MainGame extends ApplicationAdapter {
	SpriteBatch batch;
	Ship ship;

	private Sprite shipSprite;

	private Vector2 position;

	@Override
	public void create () {
		batch = new SpriteBatch();
		shipSprite = new Sprite(new Texture(Gdx.files.internal("ship.png")));
		position = new Vector2(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
		ship = new Ship(position, shipSprite);
		ship.setGravityAffected(true);
	}

	@Override
	public void render () {
		handleMovement();

		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		ship.getShipSprite().draw(batch);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}

	public void handleMovement(){
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
}
