package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class FlappyBird extends ApplicationAdapter {
	SpriteBatch batch;
	Background bg;
	Bird bird;
	Obstacles obstacles;
	boolean gameOver;
	Texture restartTexture;
	
	@Override
	public void create () {
		batch = new SpriteBatch();		// отрисовывает картинку
		bg = new Background();
		bird = new Bird();
		obstacles = new Obstacles();
		gameOver = false;
		restartTexture = new Texture("restart.png");
	}

	@Override
	public void render () {
		update();					// обновление картинки
		ScreenUtils.clear(1, 1, 1, 1); 	// первые три цифры RGB 4-я это альфа канал - прозрачность
		batch.begin();				// начало отрисовки
		bg.render(batch);			// координаты начала картинки из нижнего левого угла
		obstacles.render(batch);	// рисуем трубы
		if (!gameOver) {
			bird.render(batch);      // рисуем птичку
		} else {
			batch.draw(restartTexture, 350, 250);		// рисуем кнопку рестарт
		}
		batch.end();				// конец отрисовки
	}

	public void update() {
		bg.update();				// обнова картинки
		bird.update();
		obstacles.update();
		for (int i = 0; i < Obstacles.obs.length; i++) {
			if (bird.position.x > Obstacles.obs[i].position.x && bird.position.x < Obstacles.obs[i].position.x + 60) {	// 60 - ширина трубы
				if (!Obstacles.obs[i].emptySpace.contains(bird.position)) {
					gameOver = true;
				}
			}
		}
		if (bird.position.y < 0 || bird.position.y > 650) {		 // 650 - высота картинки +50
			gameOver = true;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && gameOver) {
			recreate();
		}
	}
	
	@Override
	public void dispose () {		//очистка ресурсов
		batch.dispose();
	}

	public void recreate() {
		bird.recreate();
		obstacles.recreate();
		gameOver = false;
	}
}
