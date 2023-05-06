package uet.oop.bomberman.entities;

import uet.oop.bomberman.graphics.Screen;

import java.awt.*;

public class Notification extends Entity {

	protected String _notification;
	protected int _duration;
	protected Color _color;
	protected int _size;

	public Notification(String notification, double x, double y, int duration, Color color, int size) {
		_x =x;
		_y = y;
		_notification = notification;
		_duration = duration * 60; //seconds
		_color = color;
		_size = size;
	}

	public int getDuration() {
		return _duration;
	}

	public void setDuration(int _duration) {
		this._duration = _duration;
	}

	public String getNotification() {
		return _notification;
	}

	public Color getColor() {
		return _color;
	}

	public int getSize() {
		return _size;
	}

	@Override
	public void update() {
	}

	@Override
	public void render(Screen screen) {
	}

	@Override
	public boolean collide(Entity e) {
		return false;
	}
	
	
}
