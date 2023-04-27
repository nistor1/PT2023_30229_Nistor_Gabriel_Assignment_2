package org.example.View;
import javax.swing.*;

public class View extends JFrame {
    private SimulationFrame view;
    public View() {
    }
    public View(String displayData) {
        view = new SimulationFrame();
        view.setString(displayData);
    }
    public SimulationFrame getView() {
        return view;
    }
    public void setStringView(String s) {
        this.view.setString(s);
    }
}

