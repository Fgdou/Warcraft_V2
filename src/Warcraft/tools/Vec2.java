package Warcraft.tools;

public class Vec2 {
	public double x, y;
	
	public Vec2(double x, double y) {
		this.x = x;
		this.y = y;
	}
	public Vec2(){
		x=0; y=0;
	}
	public Vec2(Vec2 o){
		x = o.x; y = o.y;
	}
	public Vec2(Vec2i o){
		x = o.x; y = o.y;
	}
	
	public boolean equals(Vec2 v){
		return (x == v.x && y == v.y);
	}
	public boolean equals(Vec2 v, double tolerance){
		return (this.distance(v) < tolerance);
	}
	public double abs(){
		return Math.sqrt(x*x + y*y);
	}
	public double distance(Vec2 v){
		return Math.abs(this.sub(v).abs());
	}
	
	public Vec2 add(double n){
		return new Vec2(x+n, y+n);
	}
	public Vec2 sub(double n){
		return new Vec2(x-n, y-n);
	}
	public Vec2 mul(double n){
		return new Vec2(x*n, y*n);
	}
	public Vec2 div(double n){
		return new Vec2(x/n, y/n);
	}
	
	public Vec2 add(Vec2i v){
		return new Vec2(x+v.x, y+v.y);
	}
	public Vec2 sub(Vec2i v){
		return new Vec2(x-v.x, y-v.y);
	}
	public Vec2 mul(Vec2i v){
		return new Vec2(x*v.x, y*v.y);
	}
	public Vec2 div(Vec2i v){
		return new Vec2(x/v.x, y/v.y);
	}
	public Vec2 add(Vec2 v){
		return new Vec2(x+v.x, y+v.y);
	}
	public Vec2 sub(Vec2 v){
		return new Vec2(x-v.x, y-v.y);
	}
	public Vec2 mul(Vec2 v){
		return new Vec2(x*v.x, y*v.y);
	}
	public Vec2 div(Vec2 v){
		return new Vec2(x/v.x, y/v.y);
	}
	
	public Vec2 normalize(){
		double r = abs();
		return new Vec2(x/r, y/r);
	}
	
	public String toString(){
		return "{" + x + "," + y + "}";
	}

    public double getAngle() {
		double a = Math.atan2(y, x);
		return a*360/(2*Math.PI) - 90;
    }
}
