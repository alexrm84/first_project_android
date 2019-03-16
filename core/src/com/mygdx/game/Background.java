package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

public class Background {

    private class Star {
        private float x, y;
        private float speed;

        public Star() {
            this.x = MathUtils.random(0, 1280);
            this.y = MathUtils.random(0, 720);
            this.speed = MathUtils.random(50.0f, 120.0f);
        }

        public void update(float dt) {
            x -= speed * dt;
            if (x < -16) {
                x = 1280.0f;
                y = MathUtils.random(0, 720);
                speed = MathUtils.random(50.0f, 120.0f);
            }
        }
    }

    private class Asteroid {
        private float x, y;
        private float speed;

        public Asteroid() {
            this.x = MathUtils.random(800, 1280);
            this.y = MathUtils.random(64, 720);
            this.speed = MathUtils.random(100.0f, 200.0f);
        }

        public void update(float dt) {
            x -= speed * dt;
            if (x < -32 || collision()) {
                x = 1280.0f;
                y = MathUtils.random(64, 720);
                speed = MathUtils.random(100.0f, 1200.0f);
            }
        }

        public boolean collision(){
            if (hero.position.x-32<x+16 && hero.position.x+32>x-16 && hero.position.y-32<y+16 && hero.position.y+32>y-16){
                return true;
            }
            return false;
        }
    }

    private Texture texture;
    private Hero hero;
    private Texture textureStar;
    private Texture textureAsteroid;
    private Star[] stars;
    private Asteroid[] asteroids;

    public Background(Hero hero) {
        this.hero = hero;
        this.texture = new Texture("bg.png");
        this.textureStar = new Texture("star16.png");
        this.textureAsteroid = new Texture("asteroids64.png");
        this.stars = new Star[400];
        this.asteroids = new  Asteroid[10];
        for (int i = 0; i < stars.length; i++) {
            stars[i] = new Star();
        }
        for (int i = 0; i < asteroids.length; i++) {
            asteroids[i] = new Asteroid();
        }
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, 0, 0);
        for (int i = 0; i < stars.length; i++) {
            float scale = stars[i].speed / 150.0f;
            if (MathUtils.random(0, 500) < 2) {
                scale *= 1.5f;
            }
            batch.draw(textureStar, stars[i].x - 8, stars[i].y - 8, 8, 8, 16, 16, scale, scale, 0, 0, 0, 16, 16, false, false);
        }
        for (int i = 0; i < asteroids.length; i++) {
            batch.draw(textureAsteroid, asteroids[i].x - 32, asteroids[i].y - 32, 32, 32, 64, 64, 1, 1, 0.0f, 0, 0, 64, 64, false, false);

        }
    }

    public void update(float dt) {
        for (int i = 0; i < stars.length; i++) {
            stars[i].update(dt);
        }
        for (int i = 0; i < asteroids.length; i++) {
            asteroids[i].update(dt);
        }
    }
}
