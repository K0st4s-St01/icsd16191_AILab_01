package org.icsd16191;

import lombok.extern.slf4j.Slf4j;
import org.icsd16191.gui.Window;

@Slf4j
public class Main {
    public static void main(String[] args) {
        Window win = new Window();
        win.start();
    }
}