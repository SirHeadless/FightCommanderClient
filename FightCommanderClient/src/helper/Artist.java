package helper;

import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glVertex2f;
import static org.lwjgl.opengl.GL11.glBlendFunc;

import java.io.IOException;
import java.io.InputStream;

import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glTexCoord2f;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import background.Color;


public class Artist {
	
	public static final int WIDTH = 1280, HEIGHT = 960;
	
	public static void BeginSession() {
		Display.setTitle("ToatalFight");
		
		try{
			Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0,WIDTH,HEIGHT,0,1,-1);
		glMatrixMode(GL_MODELVIEW);
		glEnable(GL_TEXTURE_2D);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
	}
	
	public static void DrawQuad(float x, float y, float width, float height) {
		glBegin(GL_QUADS);
		glVertex2f(x,y);
		glVertex2f(x + width,y);
		glVertex2f(x + width,y + height);
		glVertex2f(x,y + height);
		glEnd();
	}
	
	public static void DrawTransQuad(float x, float y, float width, float height) {
		glBegin(GL_QUADS);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glVertex2f(x,y);
		glVertex2f(x + width,y);
		glVertex2f(x + width,y + height);
		glVertex2f(x,y + height);
		glDisable(GL_BLEND);
		glEnd();
	}
	
	public static void DrawQuadTex(Texture tex, Texture buildingTex, Texture figureTex,   float x, float y, float width, float height, Color color) {
		if (tex != null){DrawQuadTex(tex, x, y, width, height, color);}
		if (buildingTex != null) {DrawQuadTex(buildingTex, x, y, width, height, color);}
		if (figureTex != null) {DrawQuadTex(figureTex, x, y, width, height, color);}
//		float[] colorArray = color.getColor();
//		glTranslatef(x,y,0);
//		glBegin(GL_QUADS);
//		glEnable(GL_BLEND);
//		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
//		glColor3f(colorArray[0],colorArray[1],colorArray[2]);
//		glTexCoord2f(0,0);
//		glVertex2f(0,0);
//		glTexCoord2f(1,0);
//		glVertex2f(width,0);
//		glTexCoord2f(1,1);
//		glVertex2f(width,height);
//		glTexCoord2f(0,1);
//		glVertex2f(0,height);
//		glColor3f(1f, 1f, 1f);
//		glEnd();
//		glLoadIdentity();
	}
	
	public static void DrawQuadTex(Texture tex, float x, float y, float width, float height, Color color) {
		float[] colorArray = color.getColor();
		tex.bind();
		glTranslatef(x,y,0);
		glBegin(GL_QUADS);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glColor3f(colorArray[0],colorArray[1],colorArray[2]);
		glTexCoord2f(0,0);
		glVertex2f(0,0);
		glTexCoord2f(1,0);
		glVertex2f(width,0);
		glTexCoord2f(1,1);
		glVertex2f(width,height);
		glTexCoord2f(0,1);
		glVertex2f(0,height);
		glColor3f(1f, 1f, 1f);
		glEnd();
		glLoadIdentity();
	}
	
	public static void DrawQuadTex(Texture tex, float x, float y, float width, float height) {
		tex.bind();
		glTranslatef(x,y,0);
		glBegin(GL_QUADS);
		glTexCoord2f(0,0);
		glVertex2f(0,0);
		glTexCoord2f(1,0);
		glVertex2f(width,0);
		glTexCoord2f(1,1);
		glVertex2f(width,height);
		glTexCoord2f(0,1);
		glVertex2f(0,height);
		glEnd();
		glLoadIdentity();
	}
	
	public static Texture LoadTexture(String path, String fileType) {
		Texture tex = null;
		InputStream in = ResourceLoader.getResourceAsStream(path);
		try {
			tex = TextureLoader.getTexture(fileType, in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return tex;
	}
	
	public static Texture QuickLoad(String name) {
		Texture tex = null;
		tex = LoadTexture("res/" + name + ".png", "PNG");
		return tex;
	}

}
