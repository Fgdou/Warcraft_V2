package Warcraft.screen;

import Warcraft.tools.StdDraw;
import Warcraft.tools.Vec2;
import Warcraft.tools.Vec2i;

import java.awt.*;

public class Screen {
	private Vec2i size;
	private int nTileX;
	
	
	public Screen(Vec2i size, int nTileX){
		this.nTileX = nTileX;
		this.size = size;
		
		StdDraw.setCanvasSize(size.x, size.y);
		StdDraw.enableDoubleBuffering();
	}
	
	public Vec2i getnTiles() {
		return new Vec2i(nTileX, nTileX*size.y/size.x);
	}
	private double getSizeTile() {
		return 1.0 / nTileX;
	}
	public void render(){
		StdDraw.show();
	}


	public Vec2 worldPosToScreen(Vec2 pos){
		// (pos + 0.5) * tileSize * screenRatio
		Vec2 p = pos.add(0.5).mul(getSizeTile()).mul(new Vec2(1, getScreenRatio()));
		if(p.x < 0 || p.x > 1 || p.y < 0 || p.y > 1)
			return new Vec2();
		return p;
	}
	public Vec2 worldScaleToScreen(Vec2 pos){
		// pos * tileSize * screenRatio
		return pos.mul(getSizeTile()).mul(new Vec2(1, getScreenRatio()));
	}
	public Vec2 screenPosToWorld(Vec2 pos){
		// pos / ratio / tileSize - 0.5
		Vec2 p = pos.div(new Vec2(1, getScreenRatio())).div(getSizeTile()).sub(0.5);
		if(p.x < 0 || p.x >= size.x || p.y < 0 || p.y >= size.y)
			return new Vec2();
		return p;
	}
	public Vec2i screenPosToWorldTile(Vec2 pos) {
		return (new Vec2i(pos.div(new Vec2(1, getScreenRatio())).div(getSizeTile())));
	}
	public Vec2 screenScaleToWorld(Vec2 pos){
		// pos / screenRatio / tileSize
		return pos.div(getSizeTile()).div(new Vec2(1, getScreenRatio()));
	}

	public double getScreenRatio(){
		return (double)size.x/size.y;
	}
	public Vec2i getSize() {
		return size;
	}

	public void drawRectangle(Vec2 pos, Vec2 halfSize, Color c){
		pos = worldPosToScreen(pos);
		halfSize = worldScaleToScreen(halfSize);

		drawRectangleAbsolute(pos, halfSize, c);
	}
	public void drawRectangleAbsolute(Vec2 pos, Vec2 halfSize, Color c){
		StdDraw.setPenColor(c);
		StdDraw.rectangle(pos.x, pos.y, halfSize.x, halfSize.y);
	}
	public void drawFilledRectangle(Vec2 pos, Vec2 halfSize, Color c){
		pos = worldPosToScreen(pos);
		halfSize = worldScaleToScreen(halfSize);

		drawFilledRectangleAbsolute(pos, halfSize, c);
	}
	public void drawFilledRectangleAbsolute(Vec2 pos, Vec2 halfSize, Color c){
		StdDraw.setPenColor(c);
		StdDraw.filledRectangle(pos.x, pos.y, halfSize.x, halfSize.y);
	}
	public void drawImageAbsolute(Vec2 pos, Vec2 size, String file, double angle){
		StdDraw.picture(pos.x, pos.y, file, size.x, size.y, angle);
	}
	public void drawImage(Vec2 pos, Vec2 size, String file, double angle){
		pos = worldPosToScreen(pos);
		size = worldScaleToScreen(size);
		drawImageAbsolute(pos, size, file, angle);
	}
	public void drawCircle(Vec2 pos, double radius, Color c){
		pos = worldPosToScreen(pos);
		Vec2 size = worldScaleToScreen(new Vec2(radius, radius));

		StdDraw.setPenColor(c);
		StdDraw.ellipse(pos.x, pos.y, size.x, size.y);
	}

	public void drawProgressBarAbsolute(Vec2 pos, Vec2 halfSize, double percent, Color c){
		if(percent < 0)
			percent = 0;
		if(percent > 1)
			percent = 1;
		drawFilledRectangleAbsolute(pos, halfSize, Color.gray);

		Vec2 rec = pos.sub(new Vec2(halfSize.x*(1-percent), 0));
		Vec2 size = halfSize.mul(new Vec2(percent, 1));
		drawFilledRectangleAbsolute(rec, size, c);
	}
	public void drawProgressBar(Vec2 pos, Vec2 halfSize, double percent, Color c){
		pos = worldPosToScreen(pos);
		halfSize = worldScaleToScreen(halfSize);

		drawProgressBarAbsolute(pos, halfSize, percent, c);
	}

	public void drawTextImageRightAbsolute(Vec2 pos, int fontSize, Color c, String msg, String file){
		double size = (double)fontSize/getSize().y;
		Vec2 posImage = pos.sub(new Vec2(size, 0));
		Vec2 posText = pos.sub(new Vec2(size*1.5, 0));

		drawImageAbsolute(posImage, new Vec2(size/getScreenRatio(), size), file, 0);
		drawTextRightAbsolute(posText, fontSize, c, msg);
	}
	public void drawTextImageRight(Vec2 pos, int fontSize, Color c, String msg, String file){
		drawTextImageRightAbsolute(worldPosToScreen(pos), fontSize, c, msg, file);
	}

	public void drawTextLeftAbsolute(Vec2 pos, int fontSize, Color c, String msg){
		StdDraw.setFont(new Font("Roboto", Font.PLAIN, fontSize));
		StdDraw.setPenColor(c);
		StdDraw.textLeft(pos.x, pos.y, msg);
	}
	public void drawTextRightAbsolute(Vec2 pos, int fontSize, Color c, String msg){
		StdDraw.setFont(new Font("Roboto", Font.PLAIN, fontSize));
		StdDraw.setPenColor(c);
		StdDraw.textRight(pos.x, pos.y, msg);
	}
	public void drawTextLeft(Vec2 pos, int fontSize, Color c, String msg){
		drawTextLeftAbsolute(worldPosToScreen(pos), fontSize, c, msg);
	}
	public void drawTextRight(Vec2 pos, int fontSize, Color c, String msg){
		drawTextRightAbsolute(worldPosToScreen(pos), fontSize, c, msg);

	}

	public void clear() {
		StdDraw.clear();
	}
}
