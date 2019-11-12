package game.towerdefense.sprites;

import game.towerdefense.Settings;
import game.towerdefense.ui.HealthBar;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class Enemy extends SpriteBase {

	HealthBar healthBar;
	
	double healthMax;

	public Enemy(Pane layer, Image image, double x, double y, double r, double dx, double dy, double dr, double health, double damage) {
		
		super(layer, image, x, y, r, dx, dy, dr, health, damage);
		
		healthMax = Settings.ENEMY_HEALTH;
		
		setHealth(healthMax);
		
		init();
	}
	
	private void init() {
	}
	
	@Override
	public void checkRemovability() {

		if( Double.compare( getX(), Settings.SCENE_WIDTH) > 0) {
			setRemovable(true);
		}
		
	}
	
	public void addToLayer() {
		

		
		// create health bar; has to be created here because addToLayer is called in super constructor
		// and it wouldn't exist yet if we'd create it as class member
		healthBar = new HealthBar();
		 
		this.layer.getChildren().add(this.healthBar);
        super.addToLayer();
	}

	public void removeFromLayer() {
		
		super.removeFromLayer();
		
		this.layer.getChildren().remove(this.healthBar);
		
	}


	public double getRelativeHealth() {
		return getHealth() / healthMax;
	}

	
	public void updateUI() {

		super.updateUI();

		// update health bar
		healthBar.setValue( getRelativeHealth());
		
		// locate healthbar above enemy, centered horizontally
		healthBar.relocate(x + (imageView.getBoundsInLocal().getWidth() - healthBar.getBoundsInLocal().getWidth()) / 2, y - healthBar.getBoundsInLocal().getHeight() - 4);		
	}
}
