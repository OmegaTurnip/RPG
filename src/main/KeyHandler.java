package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

public class KeyHandler implements KeyListener {

    private Map<Integer, Boolean> keyMaps = new HashMap<>();

    public KeyHandler() {
        keyMaps.put(KeyEvent.VK_W, false);
        keyMaps.put(KeyEvent.VK_A, false);
        keyMaps.put(KeyEvent.VK_S, false);
        keyMaps.put(KeyEvent.VK_D, false);
        keyMaps.put(KeyEvent.VK_SPACE, false);
    }
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        keyMaps.put(code, true);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        keyMaps.put(code, false);
    }

    public boolean getKey(int code) {
        return keyMaps.get(code);
    }
}
