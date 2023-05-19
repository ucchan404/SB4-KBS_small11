package dk.sdu.mmmi.cbse.gamestates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import dk.sdu.mmmi.cbse.entities.Asteroid;
import dk.sdu.mmmi.cbse.entities.Bullet;
import dk.sdu.mmmi.cbse.entities.Enemy;
import dk.sdu.mmmi.cbse.entities.Player;
import dk.sdu.mmmi.cbse.main.Game;
import dk.sdu.mmmi.cbse.managers.GameKeys;
import dk.sdu.mmmi.cbse.managers.GameStateManager;

import java.nio.Buffer;
import java.util.ArrayList;

public class PlayState extends GameState {

    private ShapeRenderer sr;
    private Player player;
    private Player hudPlayer;
    private ArrayList<Bullet> playerBullets;
    private ArrayList<Asteroid> asteroids;

    private int level;
    private int totalAsteroids;
    private int numAsteroidsLeft;

    private Enemy enemy;
    private float eTimer;
    private float eTime;
    private ArrayList<Bullet> enemyBullets;

    public PlayState(GameStateManager gsm) {
        super(gsm);
    }

    public void init() {

        sr = new ShapeRenderer();

        playerBullets = new ArrayList<>();
        player = new Player(playerBullets);

        asteroids = new ArrayList<Asteroid>();

        level = 1;
        spawnAsteroids();

        hudPlayer = new Player(null);

        //enemy setup
        eTimer = 0;
        eTime = 5;
        enemyBullets = new ArrayList<Bullet>();
    }

    private void splitAsteroids(Asteroid a) {
        numAsteroidsLeft--;
        if (a.getType() == Asteroid.LARGE) {
            asteroids.add(new Asteroid(a.getx(), a.gety(), Asteroid.MEDIUM));
            asteroids.add(new Asteroid(a.getx(), a.gety(), Asteroid.MEDIUM));
        }
        if (a.getType() == Asteroid.MEDIUM) {
            asteroids.add(new Asteroid(a.getx(), a.gety(), Asteroid.SMALL));
            asteroids.add(new Asteroid(a.getx(), a.gety(), Asteroid.SMALL));
        }
    }

    private void spawnAsteroids() {
        asteroids.clear();

        int numToSpawn = 4 + level - 1;
        totalAsteroids = numToSpawn * 7;
        numAsteroidsLeft = totalAsteroids;

        for (int i = 0; i < numToSpawn; i++) {
            float x = MathUtils.random(Game.WIDTH);
            float y = MathUtils.random(Game.HEIGHT);

            float dx = x - player.getx();
            float dy = y - player.gety();
            float dist = (float) Math.sqrt(dx * dx + dy * dy);

            while (dist < 100) {
                x = MathUtils.random(Game.WIDTH);
                y = MathUtils.random(Game.HEIGHT);
                dx = x - player.getx();
                dy = y - player.gety();
                dist = (float) Math.sqrt(dx * dx + dy * dy);
            }
            asteroids.add(new Asteroid(x, y, Asteroid.LARGE));

        }
    }

    public void update(float dt) {
        //get user input
        handleInput();

        // next level
        if (asteroids.size() == 0) {
            level++;
            spawnAsteroids();
        }

        // update player
        player.update(dt);
        if (player.isDead()) {
            player.reset();
            player.loseLife();
            enemy = null;
            return;
        }

        //update player bullets
        for (int i = 0; i < playerBullets.size(); i++) {
            playerBullets.get(i).update(dt);
            if (playerBullets.get(i).shouldRemove()) {
                playerBullets.remove(i);
                i--;
            }
        }

        //update asteroids

        for (int i = 0; i < asteroids.size(); i++) {
            asteroids.get(i).update(dt);
            if (asteroids.get(i).shouldRemove()) {
                asteroids.remove(i);
                i--;
            }
        }

        //check collision
        checkCollisions();

        //update enemy
        if (enemy == null) { // no enemy present in game
            eTimer += dt;
            if (eTimer >= eTime) {
                eTimer = 0;
                int type = MathUtils.random() < 0.5 ? Enemy.SMALL : Enemy.LARGE;
                int direction = MathUtils.random() < 0.5 ? Enemy.RIGHT : Enemy.LEFT;
                enemy = new Enemy(type, direction, player, enemyBullets);
            }
        } else { // enemy present in game
            enemy.update(dt);
            if (enemy.shouldRemove()) {
                enemy = null;
            }
        }

        //update enemy bullets
        for (int i = 0; i < enemyBullets.size(); i++) {
            enemyBullets.get(i).update(dt);
            if (enemyBullets.get(i).shouldRemove()) {
                enemyBullets.remove(i);
                i--;
            }
        }
    }

    private void checkCollisions() {
        // player-asteroid collision
        if (!player.isHit()) {
            for (int i = 0; i < asteroids.size(); i++) {
                Asteroid a = asteroids.get(i);
                if (a.intersects(player)) {
                    player.hit();
                    asteroids.remove(i);
                    i--;
                    splitAsteroids(a);
                    break;
                }
            }
        }

        //player bullet-asteroid collision
        for (int i = 0; i < playerBullets.size(); i++) {
            Bullet b = playerBullets.get(i);
            for (int j = 0; j < asteroids.size(); j++) {
                Asteroid a = asteroids.get(j);
                // if polygon a contains point b
                if (a.contains(b.getx(), b.gety())) {
                    playerBullets.remove(i);
                    i--;
                    asteroids.remove(j);
                    j--;
                    splitAsteroids(a);
                    player.incrementScore(a.getScore());
                    break;
                }
            }
        }

        // enemy bullet-player collision
        if (!player.isHit()) {
            for (int i = 0; i < enemyBullets.size(); i++) {
                Bullet b = enemyBullets.get(i);
                if (player.contains(b.getx(), b.gety())) {
                    player.hit();
                    enemyBullets.remove(i);
                    i--;
                    break;
                }
            }
        }

        //enemy-player collision
        if (enemy != null) {
            if (player.intersects(enemy)) {
                player.hit();
                enemy = null;
            }
        }

        //player bullet-enemy collision
        if (enemy != null) {
            for (int i = 0; i < playerBullets.size(); i++) {
                Bullet b = playerBullets.get(i);
                if (enemy.contains(b.getx(), b.gety())) {
                    playerBullets.remove(i);
                    i--;
                    player.incrementScore(enemy.getScore());
                    enemy = null;
                    break;
                }
            }
        }
    }

    public void draw() {
        //draw player
        player.draw(sr);

        //draw player bullets
        for (int i = 0; i < playerBullets.size(); i++) {
            playerBullets.get(i).draw(sr);
        }

        //draw asteroids

        for (int i = 0; i < asteroids.size(); i++) {
            asteroids.get(i).draw(sr);
        }

        //draw enemy
        if (enemy != null) {
            enemy.draw(sr);
        }

        //draw enemy bullets
        for (int i = 0; i < enemyBullets.size(); i++) {
            enemyBullets.get(i).draw(sr);
        }

        //draw lives
        for (int i = 0; i < player.getExtraLives(); i++) {
            hudPlayer.setPosition(40 + i * 15, 390);
            hudPlayer.draw(sr);
        }
    }

    public void handleInput() {

        if (!player.isHit()) {
            player.setLeft(GameKeys.isDown(GameKeys.LEFT));
            player.setRight(GameKeys.isDown(GameKeys.RIGHT));
            player.setUp(GameKeys.isDown(GameKeys.UP));
            if (GameKeys.isPressed(GameKeys.SPACE)) {
                player.shoot();
            }
        }
    }

    public void dispose() {
    }

}









