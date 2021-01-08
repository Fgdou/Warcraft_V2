package Warcraft.screen;

import Warcraft.tools.StdDraw;
import Warcraft.tools.Vec2;
import Warcraft.tools.Vec2i;

import java.awt.*;

/**
 * This class is an interface of StdDraw with world coordinates
 * The world coordinates simplify the drawing an the logic of the game
 */

public class Screen {
	private final Vec2i size;
	private final int nTileX;

	/**
	 * @param size 		int pixel
	 * @param nTileX 	number of tiles horizontally
	 */
	public Screen(Vec2i size, int nTileX){
		this.nTileX = nTileX;
		this.size = size;
		
		StdDraw.setCanvasSize(size.x, size.y);
		StdDraw.enableDoubleBuffering();
	}

	/**
	 * @return the numbers of tiles on the screen
	 */
	public Vec2i getnTiles() {
		return new Vec2i(nTileX, nTileX*size.y/size.x);
	}

	/**
	 * @return the size of one tile in StdDraw coordinates (0 - 1)
	 */
	private double getSizeTile() {
		return 1.0 / nTileX;
	}

	/**
	 * Draw on the screen
	 */
	public void render(){
		StdDraw.show();
	}

	/**
	 * @param pos 	A world coord
	 * @return		A StdDraw coord
	 */
	public Vec2 worldPosToScreen(Vec2 pos){
		// (pos + 0.5) * tileSize * screenRatio
		Vec2 p = pos.add(0.5).mul(getSizeTile()).mul(new Vec2(1, getScreenRatio()));
		if(p.x < 0 || p.x > 1 || p.y < 0 || p.y > 1)
			return new Vec2();
		return p;
	}

	/**
	 * @param pos	A world scale
	 * @return		A StdDraw scale
	 */
	public Vec2 worldScaleToScreen(Vec2 pos){
		// pos * tileSize * screenRatio
		return pos.mul(getSizeTile()).mul(new Vec2(1, getScreenRatio()));
	}

	/**
	 * @param pos	A StdDraw coord
	 * @return		A world coord
	 */
	public Vec2 screenPosToWorld(Vec2 pos){
		// pos / ratio / tileSize - 0.5
		Vec2 p = pos.div(new Vec2(1, getScreenRatio())).div(getSizeTile()).sub(0.5);
		if(p.x < 0 || p.x >= size.x || p.y < 0 || p.y >= size.y)
			return new Vec2();
		return p;
	}

	/**
	 * @param pos	A StdDraw coord
	 * @return		The position of the tile
	 */
	public Vec2i screenPosToWorldTile(Vec2 pos) {
		return (new Vec2i(pos.div(new Vec2(1, getScreenRatio())).div(getSizeTile())));
	}

	/**
	 * @param pos 	A StdDraw scale
	 * @return		A world scale
	 */
	public Vec2 screenScaleToWorld(Vec2 pos){
		// pos / screenRatio / tileSize
		return pos.div(getSizeTile()).div(new Vec2(1, getScreenRatio()));
	}

	/**
	 * @return the screen ratio
	 */
	public double getScreenRatio(){
		return (double)size.x/size.y;
	}

	/**
	 * @return return the size in pixel
	 */
	public Vec2i getSize() {
		return size;
	}

	/**
	 * Draw a rectangle on the screen
	 * @param pos			A world coord
	 * @param halfSize		A world size
	 * @param c				A color
	 */
	public void drawRectangle(Vec2 pos, Vec2 halfSize, Color c){
		pos = worldPosToScreen(pos);
		halfSize = worldScaleToScreen(halfSize);

		drawRectangleAbsolute(pos, halfSize, c);
	}
	/**
	 * Draw a rectangle on the screen
	 * @param pos			A StdDraw coord
	 * @param halfSize		A StdDraw size
	 * @param c				A color
	 */
	public void drawRectangleAbsolute(Vec2 pos, Vec2 halfSize, Color c){
		StdDraw.setPenColor(c);
		StdDraw.rectangle(pos.x, pos.y, halfSize.x, halfSize.y);
	}
	/**
	 * Draw a rectangle on the screen
	 * @param pos			A world coord
	 * @param halfSize		A world size
	 * @param c				A color
	 */
	public void drawFilledRectangle(Vec2 pos, Vec2 halfSize, Color c){
		pos = worldPosToScreen(pos);
		halfSize = worldScaleToScreen(halfSize);

		drawFilledRectangleAbsolute(pos, halfSize, c);
	}
	/**
	 * Draw a rectangle on the screen
	 * @param pos			A StdDraw coord
	 * @param halfSize		A StdDraw size
	 * @param c				A color
	 */
	public void drawFilledRectangleAbsolute(Vec2 pos, Vec2 halfSize, Color c){
		StdDraw.setPenColor(c);
		StdDraw.filledRectangle(pos.x, pos.y, halfSize.x, halfSize.y);
	}

	/**
	 * Draw an image on the screen
	 * @param pos		A StdDraw coord
	 * @param size		A StdDraw size
	 * @param file		The path of the file
	 * @param angle		The angle in deg
	 */
	public void drawImageAbsolute(Vec2 pos, Vec2 size, String file, double angle){
		if(file == null)
			return;
		StdDraw.picture(pos.x, pos.y, file, size.x, size.y, angle);
	}
	/**
	 * Draw an image on the screen
	 * @param pos		A world coord
	 * @param size		A world size
	 * @param file		The path of the file
	 * @param angle		The angle in deg
	 */
	public void drawImage(Vec2 pos, Vec2 size, String file, double angle){
		pos = worldPosToScreen(pos);
		size = worldScaleToScreen(size);
		drawImageAbsolute(pos, size, file, angle);
	}

	/**
	 * Draw a circle on the screen
	 * @param pos		A world coord
	 * @param radius	A world radius
	 * @param c			A color
	 */
	public void drawCircle(Vec2 pos, double radius, Color c){
		pos = worldPosToScreen(pos);
		Vec2 size = worldScaleToScreen(new Vec2(radius, radius));

		StdDraw.setPenColor(c);
		StdDraw.ellipse(pos.x, pos.y, size.x, size.y);
	}
	/**
	 * Draw a circle on the screen
	 * @param pos		A world coord
	 * @param radius	A world radius
	 * @param c			A color
	 */
	public void drawCircleAbsolute(Vec2 pos, double radius, Color c){
		Vec2 size = new Vec2(radius, radius);

		StdDraw.setPenColor(c);
		StdDraw.ellipse(pos.x, pos.y, size.x, size.y);
	}

	/**
	 * Draw a circle on the screen
	 * @param pos		A StdDraw coord
	 * @param radius	A world radius
	 * @param c			A color
	 */
	public void drawFilledCircleAbsolute(Vec2 pos, double radius, Color c){
		Vec2 size = new Vec2(radius, radius);

		StdDraw.setPenColor(c);
		StdDraw.filledEllipse(pos.x, pos.y, size.x, size.y);
	}

	/**
	 * Draw a progress bar
	 * @param pos			A StdDraw position
	 * @param halfSize		A StdDraw size
	 * @param percent		The percent of the progress bar
	 * @param c				A color
	 * @note if the value is 1, the progress bar disappear
	 */
	public void drawProgressBarAbsolute(Vec2 pos, Vec2 halfSize, double percent, Color c){
		if(percent < 0)
			percent = 0;
		if(percent >= 1)
			return;
		drawFilledRectangleAbsolute(pos, halfSize, Color.gray);

		Vec2 rec = pos.sub(new Vec2(halfSize.x*(1-percent), 0));
		Vec2 size = halfSize.mul(new Vec2(percent, 1));
		drawFilledRectangleAbsolute(rec, size, c);
	}
	/**
	 * Draw a progress bar
	 * @param pos			A world position
	 * @param halfSize		A world size
	 * @param percent		The percent of the progress bar
	 * @param c				A color
	 * @note if the value is 1, the progress bar disappear
	 */
	public void drawProgressBar(Vec2 pos, Vec2 halfSize, double percent, Color c){
		pos = worldPosToScreen(pos);
		halfSize = worldScaleToScreen(halfSize);

		drawProgressBarAbsolute(pos, halfSize, percent, c);
	}

	/**
	 * Draw a text with an image at the left
	 * @param pos		A StdDraw coord
	 * @param fontSize	A absolute fontSize in px
	 * @param c			The color of the text
	 * @param msg		The text to show
	 * @param file		The file for the image
	 */
	public void drawTextImageRightAbsolute(Vec2 pos, int fontSize, Color c, String msg, String file){
		double size = (double)fontSize/getSize().y;
		Vec2 posImage = pos.sub(new Vec2(size, 0));
		Vec2 posText = pos.sub(new Vec2(size*1.5, 0));

		drawImageAbsolute(posImage, new Vec2(size/getScreenRatio(), size), file, 0);
		drawTextRightAbsolute(posText, fontSize, c, msg);
	}
	/**
	 * Draw a text with an image at the left
	 * @param pos		A world coord
	 * @param fontSize	A absolute fontSize in px
	 * @param c			The color of the text
	 * @param msg		The text to show
	 * @param file		The file for the image
	 */
	public void drawTextImageRight(Vec2 pos, int fontSize, Color c, String msg, String file){
		drawTextImageRightAbsolute(worldPosToScreen(pos), fontSize, c, msg, file);
	}
	/**
	 * Draw a text
	 * @param pos		A StdDraw coord
	 * @param fontSize	A absolute fontSize in px
	 * @param c			The color of the text
	 * @param msg		The text to show
	 */
	public void drawTextLeftAbsolute(Vec2 pos, int fontSize, Color c, String msg){
		if(msg == null)
			return;

		StdDraw.setFont(new Font("Roboto", Font.PLAIN, fontSize));
		StdDraw.setPenColor(c);
		StdDraw.textLeft(pos.x, pos.y, msg);
	}
	/**
	 * Draw a text
	 * @param pos		A StdDraw coord
	 * @param fontSize	A absolute fontSize in px
	 * @param c			The color of the text
	 * @param msg		The text to show
	 */
	public void drawTextRightAbsolute(Vec2 pos, int fontSize, Color c, String msg){
		StdDraw.setFont(new Font("Roboto", Font.PLAIN, fontSize));
		StdDraw.setPenColor(c);
		StdDraw.textRight(pos.x, pos.y, msg);
	}
	/**
	 * Draw a text
	 * @param pos		A world coord
	 * @param fontSize	A absolute fontSize in px
	 * @param c			The color of the text
	 * @param msg		The text to show
	 */
	public void drawTextLeft(Vec2 pos, int fontSize, Color c, String msg){
		drawTextLeftAbsolute(worldPosToScreen(pos), fontSize, c, msg);
	}
	/**
	 * Draw a text
	 * @param pos		A world coord
	 * @param fontSize	A absolute fontSize in px
	 * @param c			The color of the text
	 * @param msg		The text to show
	 */
	public void drawTextRight(Vec2 pos, int fontSize, Color c, String msg){
		drawTextRightAbsolute(worldPosToScreen(pos), fontSize, c, msg);
	}

	/**
	 * Clear the screen
	 */
	public void clear() {
		StdDraw.clear();
	}
}
