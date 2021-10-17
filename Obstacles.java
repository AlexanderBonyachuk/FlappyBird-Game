package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.concurrent.ThreadLocalRandom;

public class Obstacles {

    // внутренний класс для отображения пары труб
    class WallPair {
        Vector2 position;
        float speed;
        int offset;                   // смещение
        ThreadLocalRandom random = ThreadLocalRandom.current();     // для генерации случайного числа
        Rectangle emptySpace;       // расстояние между трубами пустое

// 16.10.2021 остановился на emptySpace - надо бы допилить
        public WallPair(Vector2 pos) {
            this.position = pos;
            speed = 2;
            offset = random.nextInt(320);   // генерация случайного смещения труб
            // свободное место: Х, Y - смещение + высота трубы, ширина трубы, расстояние между верхней и нижней трубой
            emptySpace = new Rectangle(position.x, position.y - offset + txt.getHeight(), txt.getWidth(), betweenDistance);
        }

        public void update() {
            position.x -= speed;
            if (position.x < -txt.getWidth()) {
                position.x = 1109;                   // при прохождении до конца экрана труба, появляется в правом углу
                offset = random.nextInt(320);   // генерация случайного смещения труб
            }
            emptySpace.x = position.x;
        }
    }

    static WallPair[] obs;        // массив пар труб
    Texture txt;                 // картинка
    int betweenDistance;        // расстояние между трубами

    public Obstacles() {
        txt = new Texture("wall.png");
        obs = new WallPair[5];          // 5 пар труб
        betweenDistance = 240;          // расстояние между верхней и нижней трубой
        int startPosX = 400;            // стартовая позиция труб
        for (int i = 0; i < obs.length; i++) {
            obs[i] = new WallPair(new Vector2(startPosX, 0));
            startPosX += 234;           // прибавляем расстояние между трубами
        }
    }

    public void render(SpriteBatch batch) {
        for (int i = 0; i < obs.length; i++) {
            batch.draw(txt, obs[i].position.x, obs[i].position.y - obs[i].offset);
            batch.draw(txt, obs[i].position.x, obs[i].position.y + betweenDistance + txt.getHeight() - obs[i].offset);
        }
    }
    
    public void update() {
        for (int i = 0; i < obs.length; i++) {
            obs[i].update();
        }
    }

    // пересоздание при рестарте
    public void recreate() {
        int startPosX = 400;            // стартовая позиция труб
        for (int i = 0; i < obs.length; i++) {
            obs[i] = new WallPair(new Vector2(startPosX, 0));
            startPosX += 234;           // прибавляем расстояние между трубами
        }
    }
}
