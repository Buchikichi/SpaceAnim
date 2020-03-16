package to.kit.tools;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Space extends BufferedImage {
	private static final int STARS = 200;
	static final Color[] COLORS = { Color.red, Color.blue, Color.yellow, Color.yellow, Color.white, Color.white, Color.white };
	public static final int IMAGE_SIZE = 512;

	private List<Star> starList = new ArrayList<>();

	public void next() {
		Graphics g = getGraphics();

		g.clearRect(0, 0, IMAGE_SIZE, IMAGE_SIZE);
		for (Star star : this.starList) {
			int half = star.size / 2;
			g.setColor(star.color);
			g.fillOval(star.x - half, star.y - half, star.size, star.size);
			star.x -= star.speed;
			if (star.x < 0) {
				star.x += IMAGE_SIZE;
			}
		}
	}

	private void initStars() {
		for (int ix = 0;ix < STARS; ix++) {
			this.starList.add(new Star());
		}
	}

	public Space() {
		super(IMAGE_SIZE, IMAGE_SIZE, BufferedImage.TYPE_INT_RGB);
		initStars();
	}

	private class Star {
		int x;
		int y;
		int size;
		int speed;
		Color color;

		Color changeAlpha(Color org) {
			int r = org.getRed();
			int g = org.getGreen();
			int b = org.getBlue();
			return new Color(r, g, b, 140);
		}

		Star() {
			Random r = new Random() {
				private static final long serialVersionUID = -4621092867889638207L;

				@Override
				public int nextInt() {
					return Math.abs(super.nextInt());
				}
			};
			this.x = r.nextInt() % IMAGE_SIZE;
			this.y = r.nextInt() % IMAGE_SIZE;
			this.size = r.nextInt() % 3 + 1;
			this.speed = (int) Math.pow(2, r.nextInt() % 4 + 1);
			this.color = changeAlpha(COLORS[r.nextInt() % COLORS.length]);
		}
	}
}
