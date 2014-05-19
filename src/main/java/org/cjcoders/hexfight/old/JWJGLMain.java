package org.cjcoders.hexfight.old;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

/**
 * Created by mrakr_000 on 2014-05-10.
 */
public class JWJGLMain {

    /**
     * is VSync Enabled
     */
    boolean vsync;

    /**
     * position of quad
     */
    float x = 400, y = 300;
    private int side = 200;
    /**
     * angle of quad rotation
     */
    float rotation = 0;
    /**
     * time at last frame
     */
    long lastFrame;

    /**
     * frames per second
     */
    int fps;
    /**
     * last fps time
     */
    long lastFPS;

    public void start() {
        try {
            //Display.setDisplayMode(new DisplayMode(1920, 1080));
            Display.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
            System.exit(0);
        }

        initGL(); // init OpenGL
        getDelta(); // call once before loop to initialise lastFrame
        lastFPS = getTime(); // call before loop to initialise fps timer


        while (!Display.isCloseRequested()) {
            pollInuput();
            int delta = getDelta();

            update(delta);
            renderGL();

            Display.update();
            Display.sync(60);
        }

        Display.destroy();

    }

    public void update(int delta) {
        // rotate quad
        rotation += 0.15f * delta;

        if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) x -= 0.35f * delta;
        if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) x += 0.35f * delta;

        if (Keyboard.isKeyDown(Keyboard.KEY_UP)) y += 0.35f * delta;
        if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) y -= 0.35f * delta;

        updateFPS(); // update FPS Counter
    }

    /**
     * Calculate how many milliseconds have passed
     * since last frame.
     *
     * @return milliseconds passed since last frame
     */
    public int getDelta() {
        long time = getTime();
        int delta = (int) (time - lastFrame);
        lastFrame = time;

        return delta;
    }

    /**
     * Get the accurate system time
     *
     * @return The system time in milliseconds
     */
    public long getTime() {
        return (Sys.getTime() * 1000) / Sys.getTimerResolution();
    }

    /**
     * Calculate the FPS and set it in the title bar
     */
    public void updateFPS() {
        if (getTime() - lastFPS > 1000) {
            Display.setTitle("FPS: " + fps);
            fps = 0;
            lastFPS += 1000;
        }
        fps++;
    }

    public void initGL() {
        DisplayMode dm = Display.getDisplayMode();
        GL11.glClearColor(255,255,255,0);
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0, dm.getWidth(), 0, dm.getHeight(), 1, -1);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
    }

    public void renderGL() {
        // Clear The Screen And The Depth Buffer
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

        // R,G,B,A Set The Color To Blue One Time Only
        GL11.glColor3f(0.5f, 0.5f, 1.0f);

        // draw quad
        GL11.glPushMatrix();
        GL11.glTranslatef(x, y, 0);
        GL11.glRotatef(rotation, 0f, 0f, 1f);
        GL11.glTranslatef(-x, -y, 0);

        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2f(x - side/2, y - side/2);
        GL11.glVertex2f(x + side/2, y - side/2);
        GL11.glVertex2f(x + side/2, y + side/2);
        GL11.glVertex2f(x - side/2, y + side/2);
        GL11.glEnd();
        GL11.glPopMatrix();
    }

    public void setDisplayMode(int width, int height, boolean fullscreen) {

        // return if requested DisplayMode is already set
        if ((Display.getDisplayMode().getWidth() == width) &&
                (Display.getDisplayMode().getHeight() == height) &&
                (Display.isFullscreen() == fullscreen)) {
            return;
        }

        try {
            DisplayMode targetDisplayMode = null;

            if (fullscreen) {
                DisplayMode[] modes = Display.getAvailableDisplayModes();
                int freq = 0;

                for (int i = 0; i < modes.length; i++) {
                    DisplayMode current = modes[i];

                    if ((current.getWidth() == width) && (current.getHeight() == height)) {
                        if ((targetDisplayMode == null) || (current.getFrequency() >= freq)) {
                            if ((targetDisplayMode == null) || (current.getBitsPerPixel() > targetDisplayMode.getBitsPerPixel())) {
                                targetDisplayMode = current;
                                freq = targetDisplayMode.getFrequency();
                            }
                        }

                        // if we've found a match for bpp and frequence against the
                        // original display mode then it's probably best to go for this one
                        // since it's most likely compatible with the monitor
                        if ((current.getBitsPerPixel() == Display.getDesktopDisplayMode().getBitsPerPixel()) &&
                                (current.getFrequency() == Display.getDesktopDisplayMode().getFrequency())) {
                            targetDisplayMode = current;
                            break;
                        }
                    }
                }
            } else {
                targetDisplayMode = new DisplayMode(width, height);
            }

            if (targetDisplayMode == null) {
                System.out.println("Failed to find value mode: " + width + "x" + height + " fs=" + fullscreen);
                return;
            }

            Display.setDisplayMode(targetDisplayMode);
            Display.setFullscreen(fullscreen);

        } catch (LWJGLException e) {
            System.out.println("Unable to setup mode " + width + "x" + height + " fullscreen=" + fullscreen + e);
        }
    }

    public void pollInuput() {
        while (Mouse.next()) {
            if (Mouse.getEventButton() == 0) {
                if (!Mouse.getEventButtonState()) {
                    int x = Mouse.getX();
                    int y = Mouse.getY();
                    this.x = x;
                    this.y = y;
                    System.out.println("MOUSE DOWN @ X: " + x + " Y: " + y);
                }
            }
        }

        int dWheel = Mouse.getDWheel();
        if (dWheel < 0) {
            side += 5;
        } else if (dWheel > 0){
            side -= 5;
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
            System.out.println("SPACE KEY IS DOWN");
        }

        while (Keyboard.next()) {
            if (Keyboard.getEventKeyState()) {
                if (Keyboard.getEventKey() == Keyboard.KEY_F) {
                    setDisplayMode(Display.getDisplayMode().getWidth(), Display.getDisplayMode().getHeight(), !Display.isFullscreen());
                } else if (Keyboard.getEventKey() == Keyboard.KEY_V) {
                    vsync = !vsync;
                    Display.setVSyncEnabled(vsync);
                }
                if (Keyboard.getEventKey() == Keyboard.KEY_A) {
                    System.out.println("A Key Pressed");
                }
                if (Keyboard.getEventKey() == Keyboard.KEY_S) {
                    System.out.println("S Key Pressed");
                }
                if (Keyboard.getEventKey() == Keyboard.KEY_D) {
                    System.out.println("D Key Pressed");
                }
            } else {
                if (Keyboard.getEventKey() == Keyboard.KEY_A) {
                    System.out.println("A Key Released");
                }
                if (Keyboard.getEventKey() == Keyboard.KEY_S) {
                    System.out.println("S Key Released");
                }
                if (Keyboard.getEventKey() == Keyboard.KEY_D) {
                    System.out.println("D Key Released");
                }
            }
        }
    }

    public static void main(String[] args) {
        JWJGLMain example = new JWJGLMain();
        example.start();
    }
}
