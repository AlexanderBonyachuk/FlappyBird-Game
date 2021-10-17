package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Background {

    class BGPicture {
        private Texture tx;     // текстура фона
        private Vector2 pos;    // вектор для задания позиции

        public BGPicture(Vector2 pos) {
            tx = new Texture("back.png");   // текстура это картинка фона
            this.pos = pos;
        }
    }

    private int speed;                   // скорость перерисовки фона
    private BGPicture[] backs;           // массив задних фонов

    public Background() {
        speed = 4;                      // на 4 точки смещается картинка
        backs = new BGPicture[2];
        // в 1й элемент массива кладем первую картинку с координатами 0, 0:
        backs[0] = new BGPicture(new Vector2(0, 0));
        // во 2й элемент массива кладем вторую картинку с координатами ширина картинки, 0:
        backs[1] = new BGPicture(new Vector2(1109, 0));
        // таким образом получим массив из двух склеенных друг за другом картинок
    }

    public void render(SpriteBatch batch) {
        for (int i = 0; i < backs.length; i++) {
            // прорисовка картинки из массива картинок фона с нужными координатами:
            batch.draw(backs[i].tx, backs[i].pos.x, backs[i].pos.y);
        }
    }

    public void update() {
        for (int i = 0; i < backs.length; i++) {
            backs[i].pos.x -=  speed;
        }
        // фон перемещается по Х
        if (backs[0].pos.x < -1109) {
            backs[0].pos.x = 0;
            backs[1].pos.x = 1109;
        }
    }
}
