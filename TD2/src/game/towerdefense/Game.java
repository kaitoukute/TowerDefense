package game.towerdefense;

import game.towerdefense.grid.Grid;
import game.towerdefense.sprites.Enemy;
import game.towerdefense.sprites.SpriteBase;
import game.towerdefense.sprites.Tower;
import game.towerdefense.utils.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

public class Game extends Application {

    private String msgDatTrung = "invalid tile";
    private String msgDatTrenDuongDi = "invalid tile";
    private String msgKhongDuocDatODay = "invalid tile";

    int toastMsgTime = 3500; //3.5 seconds
    int fadeInTime = 500; //0.5 seconds
    int fadeOutTime = 500; //0.5 seconds

    List<Bounds> lstCheckBoundDatTrung = new ArrayList<>();
    List<Bounds> lstCheckBoundDatODay = new ArrayList<>();
    List<Bounds> lstCheckBoundDuongDi = new ArrayList<>();
    Random rnd = new Random();

    Pane backgroundLayer;
    Pane playfieldLayer;
    Pane scoreLayer;
    Grid towerGridLayer;

    Image backgroundImage;
    Image playerImage;
    Image enemyImage;

    List<Tower> towers = new ArrayList<>();
    ;
	List<Enemy> enemies = new ArrayList<>();
    ;
	
	Text scoreText = new Text();
    int score = 0;

    Scene scene;

    @Override
    public void start(Stage primaryStage) {

        Group root = new Group();

        // create layers
        backgroundLayer = new Pane();

        // ensure the playfield size so that we can click on it
        playfieldLayer = new Pane();
        playfieldLayer.setPrefSize(Settings.SCENE_WIDTH, Settings.SCENE_HEIGHT);

        // score layer
        scoreLayer = new Pane();
        scoreLayer.setPrefWidth(Settings.SCENE_WIDTH); // note: it already spans from x=0 to center because we move the node in the layer to the center, not the layer to the center
        // don't react on mouse events
        scoreLayer.setMouseTransparent(true);

        // tower layer
        towerGridLayer = new Grid(Settings.TOWER_GRID_COLUMNS, Settings.TOWER_GRID_ROWS, Settings.PLAYFIELD_WIDTH, Settings.PLAYFIELD_HEIGHT);

        // add event handler to create towers
        towerGridLayer.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {

            // calculate grid position of new tower
            Bounds bounds = towerGridLayer.getBoundsAt(e.getX(), e.getY());
            double number[][] = {{128.0, 540.0}, {42.666666666666664, 36.0}, {128.0, 36.0}, {213.33333333333331, 36.0}
            , {298.6666666666667, 36.0}, {384.0, 36.0}, {469.3333333333333, 36.0}, {554.6666666666666, 36.0}, {639.9999999999999, 36.0}
            , {725.3333333333333, 36.0}, {810.6666666666666, 36.0}, {981.3333333333333, 36.0}, {1066.6666666666667, 36.0}, {1152.0, 36.0}, {1237.3333333333333, 36.0}
            , {128.0, 180.0}, {42.666666666666664, 324.0}, {42.666666666666664, 468.0}, {810.6666666666666,252.0}
            , {42.666666666666664, 684.0}, {128.0, 684.0}, {213.33333333333331, 684.0}
            , {298.6666666666667, 684.0}, {384.0, 684.0}, {469.3333333333333, 684.0}, {554.6666666666666, 684.0}, {639.9999999999999, 684.0}
            , {725.3333333333333, 684.0}, {810.6666666666666,684.0}, {981.3333333333333, 684.0}, {1066.6666666666667, 684.0}, {1152.0, 684.0}, {1237.3333333333333, 684.0}};
            for (int i = 0; i < number.length; i++) {
                Bounds checkDatODay = towerGridLayer.getBoundsAt(number[i][0], number[i][1]);
                lstCheckBoundDatODay.add(checkDatODay);
            }  ;
            
            double number2[][] = {{42.666666666666664, 396.0},{128.0, 396.0},{213.33333333333331, 396.0},{469.3333333333333, 396.0},{810.6666666666666, 396.0}
            ,{213.33333333333331, 324.0},{213.33333333333331, 252.0}
            ,{384.0, 180.0},{298.6666666666667, 180.0},{469.3333333333333, 180.0}
            ,{469.3333333333333, 468.0},{469.3333333333333, 252.0},{469.3333333333333, 324.0}
            ,{554.6666666666666, 468.0},{639.9999999999999, 468.0},{725.3333333333333, 468.0},{810.6666666666666, 468.0}
            ,{810.6666666666666, 396.0}
           ,{895.9999999999999, 396.0},{981.3333333333333, 396.0},{1066.6666666666667, 396.0},{1152.0,396.0},{1237.3333333333333, 396.0} };
            for (int j = 0; j < number2.length; j++) {
                Bounds checkDuongDi = towerGridLayer.getBoundsAt(number2[j][0], number2[j][1]);
                lstCheckBoundDuongDi.add(checkDuongDi);
            }  ;
            // calculate grid cell center
            double centerX = bounds.getMinX() + bounds.getWidth() / 2;
            double centerY = bounds.getMinY() + bounds.getHeight() / 2;
            boolean check = true;
            boolean check2 = true;
            boolean check3 = true;
            // create & place new tower

            System.out.println("X :" + centerX);
            System.out.println("Y :" + centerY);
            for (Bounds bou : lstCheckBoundDatODay) {
                if ((centerX == bou.getMinX() + bou.getWidth() / 2) && (centerY == bou.getMinY() + bou.getHeight() / 2)) {
                    Stage stage = new Stage();
                    Toast.makeText(stage, msgKhongDuocDatODay, toastMsgTime, fadeInTime, fadeOutTime);
                    check2 = false;
                    break;
                }
            } 
            for (Bounds bou : lstCheckBoundDuongDi) {
                if ((centerX == bou.getMinX() + bou.getWidth() / 2) && (centerY == bou.getMinY() + bou.getHeight() / 2)) {
                    Stage stage = new Stage();
                    Toast.makeText(stage, msgDatTrenDuongDi, toastMsgTime, fadeInTime, fadeOutTime);
                    check3 = false;
                    break;
                }
            } 
            if (check2 && check3) {
                for (Bounds bou : lstCheckBoundDatTrung) {
                    if ((centerX == bou.getMinX() + bou.getWidth() / 2) && (centerY == bou.getMinY() + bou.getHeight() / 2)) {
                        Stage stage = new Stage();
                        Toast.makeText(stage, msgDatTrung, toastMsgTime, fadeInTime, fadeOutTime);
                        check = false;
                        break;
                    }
                }
            }
            if (check && check2) {
                createTower(centerX, centerY);
            }
            lstCheckBoundDatTrung.add(bounds);

        });

        root.getChildren().add(backgroundLayer);
        root.getChildren().add(playfieldLayer);
        root.getChildren().add(towerGridLayer);
        root.getChildren().add(scoreLayer);

        scene = new Scene(root, Settings.SCENE_WIDTH, Settings.SCENE_HEIGHT);
        scene.getStylesheets().add(getClass().getResource("css/application.css").toExternalForm());

        primaryStage.setScene(scene);

        // fullscreen
        primaryStage.setFullScreen(Settings.STAGE_FULLSCREEN);
        primaryStage.setFullScreenExitHint("");

        // scale by factor of 2 (in settings we have half-hd) to get proper dimensions in fullscreen (full-hd)
        if (primaryStage.isFullScreen()) {
            Scale scale = new Scale(Settings.STAGE_FULLSCREEN_SCALE, Settings.STAGE_FULLSCREEN_SCALE);
            scale.setPivotX(0);
            scale.setPivotY(0);
            scene.getRoot().getTransforms().setAll(scale);
        }

        primaryStage.show();
        loadGame();

        createBackgroundLayer();
        createPlayfieldLayer();
        createScoreLayer();
        //createTowers();

        AnimationTimer gameLoop = new AnimationTimer() {

            @Override
            public void handle(long now) {

                // add random enemies
                spawnEnemies(true);

                // check if target is still valid
                towers.forEach(tower -> tower.checkTarget());

                // tower movement: find target
                for (Tower tower : towers) {
                    tower.findTarget(enemies);
                }

                // movement
                towers.forEach(sprite -> sprite.move());
                enemies.forEach(sprite -> sprite.move());

                // check collisions
                checkCollisions();

                // update sprites in scene
                towers.forEach(sprite -> sprite.updateUI());
                enemies.forEach(sprite -> sprite.updateUI());

                // check if sprite can be removed
                enemies.forEach(sprite -> sprite.checkRemovability());

                // remove removables from list, layer, etc
                removeSprites(enemies);

                // update score, health, etc
                updateScore();
            }

        };
        gameLoop.start();

    }

    private void loadGame() {
        backgroundImage = new Image(getClass().getResource("images/BG.png").toExternalForm());
        playerImage = new Image(getClass().getResource("images/tower.png").toExternalForm(), 85, 72, true, false);
        enemyImage = new Image(getClass().getResource("images/TDsprite.png").toExternalForm(), 85, 72, true, false);
    }

    private void createBackgroundLayer() {
        ImageView background = new ImageView(backgroundImage);
        background.setFitHeight(720);
        background.setFitWidth(1280);
        backgroundLayer.getChildren().add(background);
    }

    private void createPlayfieldLayer() {

        // shadow effect to show depth
        // setting it on the entire group/layer preserves the shadow angle even if the node son the layer are rotated
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(5.0);
        dropShadow.setOffsetX(5.0);
        dropShadow.setOffsetY(5.0);

        playfieldLayer.setEffect(dropShadow);

    }

    private void createScoreLayer() {

        scoreText.setFont(Font.font(null, FontWeight.BOLD, 48));
        scoreText.setStroke(Color.BLACK);
        scoreText.setFill(Color.RED);

        scoreLayer.getChildren().add(scoreText);

        scoreText.setText(String.valueOf(score));

        double x = (Settings.SCENE_WIDTH - scoreText.getBoundsInLocal().getWidth()) / 2;
        double y = 0;
        scoreText.relocate(x, y);

        scoreText.setBoundsType(TextBoundsType.VISUAL);

    }

    /*  private void createTowers() {

     // position initial towers
     List<Point2D> towerPositionList = new ArrayList<>();
     //		towerPositionList.add(new Point2D( 100, 200));
     //		towerPositionList.add(new Point2D( 100, 400));
     //		towerPositionList.add(new Point2D( 1160, 200));
     //		towerPositionList.add(new Point2D( 1160, 600));
     createTower();

     }   **/
    private void createTower(double x, double y) {

        Image image = playerImage;

        // center image at position
        x -= image.getWidth() / 2;
        y -= image.getHeight() / 2;

        int X = (int) (x / Settings.CELL_WIDTH);
        int Y = (int) (y / Settings.CELL_HEIGHT);

        // create player
        if (towerGridLayer.map[Y][X] == 1) {

            Tower player = new Tower(playfieldLayer, image, x, y, 180, 0, 0, 0,
                    Settings.PLAYER_SHIP_HEALTH, 0, Settings.PLAYER_SHIP_SPEED);
            towers.add(player);
        } else {
            return;
        }

    }

    private void spawnEnemies(boolean random) {

        if (random && rnd.nextInt(Settings.ENEMY_SPAWN_RANDOMNESS) != 0) {
            return;
        }

        // image
        Image image = enemyImage;

        // random speed
        double speed = 1.0;
        double x = 0;
        double y = 5* Settings.CELL_HEIGHT;

        // x position range: enemy is always fully inside the trench, no part of it is outside
        // y position: right on top of the view, so that it becomes visible with the next game iteration

        if (rnd.nextInt(15) == 0) {

            // create a sprite
            Enemy enemy = new Enemy(playfieldLayer, image, x, y, 0, speed, 0, 0, 1, 1);

            // manage sprite
            enemies.add(enemy);

        }





    }

    private void removeSprites(List<? extends SpriteBase> spriteList) {
        Iterator<? extends SpriteBase> iter = spriteList.iterator();
        while (iter.hasNext()) {
            SpriteBase sprite = iter.next();

            if (sprite.isRemovable()) {

                // remove from layer
                sprite.removeFromLayer();

                // remove from list
                iter.remove();
            }
        }
    }

    private void checkCollisions() {

        for (Tower tower : towers) {
            for (Enemy enemy : enemies) {
                if (tower.hitsTarget(enemy)) {

                    enemy.getDamagedBy(tower);

                    if (!enemy.isAlive()) {

                        enemy.setRemovable(true);

                        // increase score
                        score++;

                    }
                }
            }
        }
    }

    private void updateScore() {
        scoreText.setText(String.valueOf(score));
    }

    public static void main(String[] args) {
        launch(args);
    }

}
