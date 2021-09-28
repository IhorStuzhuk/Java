package com.shpp.p2p.cs.istuzhuk.assignment7.namesurfer;

/*
 * File: NameSurferGraph.java
 * ---------------------------
 * This class represents the canvas on which the graph of
 * names is drawn. This class is responsible for updating
 * (redrawing) the graphs whenever the list of entries changes
 * or the window is resized.
 */

import acm.graphics.*;

import java.awt.event.*;
import java.util.*;
import java.awt.*;

@SuppressWarnings("IntegerDivisionInFloatingPointContext")
public class NameSurferGraph extends GCanvas
        implements NameSurferConstants, ComponentListener {

    /**
     * Creates a new NameSurferGraph object that displays the data.
     */
    LinkedHashMap<String, ArrayList<Integer>> graphic = new LinkedHashMap<>();

    public NameSurferGraph() {
        addComponentListener(this);
    }


    /**
     * Clears the list of name surfer entries stored inside this class.
     */
    public void clear() {
        graphic.clear();
        update();
    }


    /* Method: addEntry(entry) */

    /**
     * Adds a new NameSurferEntry to the list of entries on the display.
     * Note that this method does not actually draw the graph, but
     * simply stores the entry; the graph is drawn by calling update.
     */
    public void addEntry(NameSurferEntry entry) {
        ArrayList<Integer> values = new ArrayList<>();
        for (int i = 0; i < NDECADES; i++)
            values.add(entry.getRank(i));

        graphic.put(entry.getName(), values);
        update();
    }


    /**
     * Updates the display image by deleting all the graphical objects
     * from the canvas and then reassembling the display according to
     * the list of entries. Your application must call update after
     * calling either clear or addEntry; update is also called whenever
     * the size of the canvas changes.
     */
    public void update() {
        removeAll();
        drawAGrid();
        drawAGraph();
    }

    /**
     * This method draws a grid for graphs
     */
    private void drawAGrid() {
        //drawing horizontal lines
        drawLine(0, GRAPH_MARGIN_SIZE, getWidth(), GRAPH_MARGIN_SIZE);
        drawLine(0, getHeight() - GRAPH_MARGIN_SIZE, getWidth(), getHeight() - GRAPH_MARGIN_SIZE);

        //drawing vertical lines and labels with decades
        int dec = START_DECADE;
        for (int i = NDECADES; i >= 1; i--) {
            drawLine(getWidth() - (i * (getWidth() / NDECADES)), 0,
                    getWidth() - (i * (getWidth() / NDECADES)), getHeight());

            GLabel decade = new GLabel(dec + "");
            decade.setLocation(getWidth() - (i * (getWidth() / NDECADES)),
                    getHeight() - decade.getDescent());
            decade.setFont("Arial-" + (int) (getWidth() * 0.017));
            add(decade);

            dec += 10;
        }
    }

    /**
     * This method draws the line
     *
     * @param x0 x-coordinate for the first point
     * @param y0 y-coordinate for the first point
     * @param x1 x-coordinate for the second point
     * @param y1 y-coordinate for the second point
     */
    private void drawLine(double x0, double y0, double x1, double y1) {
        add(new GLine(x0, y0, x1, y1));
    }

    /**
     * This method builds a graph from the values that are stored in the HashMap.
     */
    private void drawAGraph() {
        Color[] colors = {Color.BLUE, Color.RED, Color.MAGENTA, Color.BLACK}; //colors array
        int colorSwitcher = 0;

        // get values by key consisting of name
        for (Map.Entry<String, ArrayList<Integer>> entry : graphic.entrySet()) {
            String name = entry.getKey();
            ArrayList<Integer> values = entry.getValue();
            int indent = NDECADES; //X-axis offset
            if (colorSwitcher == colors.length)
                colorSwitcher = 0;

            //drawing cycle
            for (int i = 1; i <= values.size(); i++) {
                GLine graph;
                if (i != values.size()) {
                    double currentPoint = getHeight() * (double) values.get(i) / MAX_RANK;
                    double previousPoint = getHeight() * (double) values.get(i - 1) / MAX_RANK;

                    currentPoint = checkForZeroAndLowerAxisOut(currentPoint);
                    previousPoint = checkForZeroAndLowerAxisOut(previousPoint);

                    graph = new GLine(getWidth() - (indent * (getWidth() / NDECADES)), previousPoint,
                            getWidth() - ((indent - 1) * (getWidth() / NDECADES)), currentPoint);
                } else {
                    double lastPoint = getHeight() * (double) values.get(i - 1) / MAX_RANK;
                    double previousPoint = getHeight() * (double) values.get(i - 2) / MAX_RANK;
                    lastPoint = checkForZeroAndLowerAxisOut(lastPoint);
                    previousPoint = checkForZeroAndLowerAxisOut(previousPoint);

                    graph = new GLine(getWidth() - ((indent + 1) * (getWidth() / NDECADES)), previousPoint,
                            getWidth() - (indent * (getWidth() / NDECADES)), lastPoint);
                }
                graph.setVisible(true);
                graph.setColor(colors[colorSwitcher]);
                add(graph);

                drawAnInscription(indent, name, values.get(i - 1), colors[colorSwitcher]);
                indent--;
            }
            colorSwitcher++;
        }
    }

    /**
     * This method draws an inscription for each point of the graph.
     *
     * @param indent X-axis offset
     * @param name   name from file
     * @param value  value from array list with values for name
     * @param color  color of the graph
     */
    private void drawAnInscription(int indent, String name, int value, Color color) {
        GLabel inscription;
        double y = getHeight() * (double) value / MAX_RANK;

        if (y == 0) {
            y = checkForZeroAndLowerAxisOut(y);
            inscription = new GLabel(name + "*");
        } else {
            y = checkForZeroAndLowerAxisOut(y);
            inscription = new GLabel(name + value);
        }

        inscription.setLocation(getWidth() - (indent * (getWidth() / NDECADES)), y);
        inscription.setColor(color);
        inscription.setFont("Arial-" + (int) (getWidth() * 0.025) / 2);
        add(inscription);
    }

    /**
     * This method checks the location of the point.
     * If the value is 0, then the location of the point will be on the lower axis.
     * If the value goes beyond the lower axis,
     * then you need to subtract the lower offset so that the point lies within the coordinate axes
     *
     * @param pointLocation location of the point on the canvas
     * @return updated point location
     */
    private double checkForZeroAndLowerAxisOut(double pointLocation) {
        if (pointLocation == 0)
            pointLocation = getHeight() - GRAPH_MARGIN_SIZE;
        else {
            if (pointLocation > getHeight() - GRAPH_MARGIN_SIZE)
                pointLocation -= GRAPH_MARGIN_SIZE;
            else pointLocation += GRAPH_MARGIN_SIZE;
        }
        return pointLocation;
    }

    /* Implementation of the ComponentListener interface */
    public void componentHidden(ComponentEvent e) {
    }

    public void componentMoved(ComponentEvent e) {
    }

    public void componentResized(ComponentEvent e) {
        update();
    }

    public void componentShown(ComponentEvent e) {
    }
}
