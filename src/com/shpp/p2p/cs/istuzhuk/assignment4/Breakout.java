package com.shpp.p2p.cs.istuzhuk.assignment4;

import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GOval;
import acm.graphics.GRect;
import acm.util.RandomGenerator;
import com.shpp.cs.a.graphics.WindowProgram;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * This program is an analogue of the game Breakout
 * The user plays until he breaks all the blocks. And for all this he has 3 attempts
 */
public class Breakout extends WindowProgram {

    /** Width and height of application window in pixels*/
    public static final int APPLICATION_WIDTH = 400;
    public static final int APPLICATION_HEIGHT = 600;

    /**Dimensions of game board (usually the same)*/
    private static final int WIDTH = APPLICATION_WIDTH;

    /**Dimensions of the paddle*/
    private static final int PADDLE_WIDTH = 60;
    private static final int PADDLE_HEIGHT = 10;

    /**Offset of the paddle up from the bottom*/
    private static final int PADDLE_Y_OFFSET = 30;

    /**Number of bricks per row*/
    private static final int NBRICKS_PER_ROW = 10;

    /** Number of rows of bricks*/
    private static final int NBRICK_ROWS = 10;

    /** Separation between bricks*/
    private static final int BRICK_SEP = 4;

    /**Width of a brick*/
    private static final int BRICK_WIDTH = (WIDTH - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / NBRICKS_PER_ROW;

    /**Height of a brick*/
    private static final int BRICK_HEIGHT = 8;

    /**Offset of the top brick row from the top*/
    private static final int BRICK_Y_OFFSET = 70;

    /**Radius of the ball in pixels*/
    private static final int BALL_RADIUS = 10;

    /**Diameter of the ball in pixels*/
    private static final int BALL_DIAMETER = BALL_RADIUS * 2;

    /**Width of the label with message*/
    private static final int MESSAGE_WIDTH = 100;

    /**The gravity who influence on the ball speed*/
    private static final double GRAVITY = 0.00225;

    /**Total number of blocks*/
    private int NUMBER_BRICKS = NBRICK_ROWS * NBRICKS_PER_ROW;

    /**The attempts of the game*/
    private int ATTEMPTS = 3;

    private double vx; //The X coordinate of the ball that changes the direction of flight
    private double vy = 3.0; //The Y coordinate of the ball that changes the flight speed

    GRect paddle;
    GOval ball;
    Color brickColor; //brick color. randomly changes every two lines
    ArrayList<GRect> bricks = new ArrayList<>(); //The array list which contains all the bricks

    /**
     * The run method calls the createGameField method.
     */
    public void run() {
        createGameField();
    }

    /**
     * The createGameField method calls buildBrockRows, madePaddle, madeBall, playGame methods and addMouseListeners
     * is executed until the ATTEMPTS of the game is less than zero.
     */
    private void createGameField() {
        buildBrickRows();
        while (ATTEMPTS > 0) {
            madePaddle();
            madeBall();
            addMouseListeners();
            playGame();
            //if the game loss then we delete paddle and ball,
            //and create them again in the start of the cycle
            remove(paddle);
            remove(ball);
            ATTEMPTS--;
        }
        //when there are no more attempts left, end the program
        exit();
    }

    /**
     * The buildBrickRows method draws rows of bricks
     */
    private void buildBrickRows() {
        RandomGenerator rgen = new RandomGenerator();
        //First "for" cycle is responsible for the rows
        for (int i = 0; i < NBRICK_ROWS; i++) {
            //Each even row change its color
            if (i % 2 == 0) {
                brickColor = rgen.nextColor();
            }
            //Second "for" cycle is responsible for bricks
            for (int j = 0; j < NBRICKS_PER_ROW; j++) {
                GRect b = new GRect(getWidth() - (j * (BRICK_WIDTH + BRICK_SEP) + BRICK_WIDTH + BRICK_SEP / 2.0),
                                    i * (BRICK_SEP + BRICK_HEIGHT) + BRICK_Y_OFFSET,
                                    BRICK_WIDTH,
                                    BRICK_HEIGHT);
                //add brick to the list of bricks
                bricks.add(b);
                b.setFilled(true);
                b.setFillColor(brickColor);
                add(b);
            }
        }
    }

    /**
     * The madePaddle method overrides the paddle object by calling a drawPaddle method
     */
    private void madePaddle() {
        paddle = drawPaddle(getWidth() / 2.0 - PADDLE_WIDTH / 2.0,
                            getHeight() - PADDLE_Y_OFFSET,
                            PADDLE_WIDTH,
                            PADDLE_HEIGHT);
    }

    /**
     * The prawPaddle method draws created paddle
     * @param x Starting X Coordinate of the paddle
     * @param y Starting Y Coordinate of the paddle
     * @param w Paddle width
     * @param h Paddle height
     * @return created paddle link
     */
    private GRect drawPaddle(double x, double y, double w, double h) {
        GRect paddle = new GRect(x, y, w, h);

        paddle.setFilled(true);
        paddle.setFillColor(Color.BLACK);
        add(paddle);
        return paddle;
    }

    /**
     * The madeBall method overrides the ball object by calling a drawBall method
     */
    private void madeBall() {
        ball = drawBall(getWidth() / 2.0 - BALL_RADIUS,
                        getHeight() / 2.0 - BALL_RADIUS,
                        BALL_DIAMETER,
                        BALL_DIAMETER);
    }

    /**
     * The drawBall method draws created ball
     * @param x Starting X Coordinate of the ball
     * @param y Starting Y Coordinate of the ball
     * @param w ball width
     * @param h ball height
     * @return created ball link
     */
    private GOval drawBall(double x, double y, double w, double h) {
        GOval ball = new GOval(x, y, w, h);

        ball.setFilled(true);
        ball.setFillColor(Color.BLACK);
        add(ball);
        return ball;
    }

    /**
     * The mouseMoved listener returns the x coordinate of the mouse
     * and moves the paddle depending on the mouse, making sure that the paddle does not go beyond the screen
     * @param me MouseEvent object
     */
    public void mouseMoved(MouseEvent me) {
        //right side of the screen
        if (me.getX() + PADDLE_WIDTH / 2 >= getWidth()) { //right side of the screen
            paddle.setLocation(getWidth() - PADDLE_WIDTH, getHeight() - PADDLE_Y_OFFSET);
            //left side of the screen
        } else if (me.getX() <= PADDLE_WIDTH / 2) {
            paddle.setLocation(0, getHeight() - PADDLE_Y_OFFSET);
            //between right and left side
        } else paddle.setLocation(me.getX() - PADDLE_WIDTH / 2.0, getHeight() - PADDLE_Y_OFFSET);
    }

    /**
     * This method starts game process after the user clicks.
     */
    private void playGame() {
        waitForClick();
        //The random generator sets the initial value for the X-coordinate of the ball
        RandomGenerator rgen = RandomGenerator.getInstance();
        vx = rgen.nextDouble(1.0, 3.0);
        if (rgen.nextBoolean(0.5))
            vx = -vx;
        //the game lasts until the ball leaves the bottom of the screen (losing)
        //or number of bricks will be zero
        while (ball.getY() + BALL_RADIUS < getHeight()) {
            if (NUMBER_BRICKS > 0)
                startBall();
            else break;
        }
        endGame();
    }

    /**
     * The startBall method starts the ball movement
     */
    private void startBall() {
        changeBallDirection();
        checkCollision();
        changeBallSpeed();
        moveBall();
    }

    private void changeBallDirection() {
        //change the direction of the ball flight along the X axis
        if (ball.getX() + BALL_RADIUS >= getWidth() || ball.getX() - BALL_RADIUS <= 0)
            vx = -vx;
        //change the direction of the ball flight along the Y axis
        if (ball.getY() - BALL_RADIUS <= 0)
            vy = -vy;
    }

    /**
     * The checkCollision method checks all the collisions ball with objects
     */
    private void checkCollision() {
        GObject collider1 = getElementAt(ball.getX(), ball.getY());
        GObject collider2 = getElementAt(ball.getX() + BALL_DIAMETER, ball.getY());
        GObject collider3 = getElementAt(ball.getX(), ball.getY() + BALL_DIAMETER);
        GObject collider4 = getElementAt(ball.getX() + BALL_DIAMETER, ball.getY() + BALL_DIAMETER);
        CheckCollisionWithPaddle(collider1,collider2,collider3,collider4);
        CheckCollisionWithBrick(collider1,collider2,collider3,collider4);
    }

    /**
     * This method checks for collisions with the paddle, and if there is
     * then changes the direction of the ball
     * @param collider1 Upper-left point of the ball
     * @param collider2 Upper-right point of the ball
     * @param collider3 Lower-left point of the ball
     * @param collider4 Lower-right point of the ball
     */
    private void CheckCollisionWithPaddle(GObject collider1,GObject collider2,GObject collider3,GObject collider4) {
        if (collider1 == paddle || collider2 == paddle || collider3 == paddle || collider4 == paddle) {
            vy = -vy;
        }
    }

    /**
     * This method checks for collisions with the brick, and if there is
     * then changes the direction of the ball and deletes this brick from the field
     * @param collider1 Upper-left point of the ball
     * @param collider2 Upper-right point of the ball
     * @param collider3 Lower-left point of the ball
     * @param collider4 Lower-right point of the ball
     */
    private void CheckCollisionWithBrick(GObject collider1,GObject collider2,GObject collider3,GObject collider4) {
        //Checks all the bricks from the list
        for (GRect b : bricks) {
            if (collider1 == b || collider2 == b || collider3 == b || collider4 == b) {
                vy = -vy;
                remove(b);//delete from field
                NUMBER_BRICKS--;
            }
        }
    }

    /**
     * This method makes the ball fall faster and slows down if it flies up
     */
    private void changeBallSpeed() {
        if (vy > 0)
            vy += GRAVITY / 4;
        else vy -= GRAVITY / 2;
    }

    /**
     * This method moves the ball across the field
     */
    private void moveBall() {
        ball.setLocation(ball.getX() + vx, ball.getY() + vy);
        pause(1000 / 60.0);
    }

    /**
     * This method is called when the game finishes
     * if number bricks is zero then user is won
     * otherwise loses
     */
    private void endGame() {
        if (NUMBER_BRICKS == 0) {
            userMessage("You won!", Color.GREEN, 10000);
            exit();
        } else userMessage("You lose!", Color.RED, 3000);
    }

    /**
     * This method prints on the screen message about win or loss
     * and after pause deletes it
     * @param message Message to user
     * @param color Message color
     * @param pauseTime Pause the program in milliseconds
     */
    private void userMessage(String message, Color color, int pauseTime) {
        GLabel l = new GLabel(message, getWidth() / 2.0 - MESSAGE_WIDTH / 2.0, getHeight() / 2.0);
        l.setColor(color);
        l.setFont("Sanserif-30");
        add(l);
        pause(pauseTime);
        remove(l);
    }
}