/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snake;

import java.awt.Point;
import java.util.LinkedList;
import java.util.Random;

/**
 *
 * @author tibor_horvath
 */
public class Snake {

    private final int MIN_SNAKE_LENGTH = 1;
    private LinkedList<Point> snake;
    private LinkedList<Direction> directions;
    private GamePanel gamePanel;
    private Random rand = new Random();

    public Snake(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.snake = new LinkedList<>();
        Point head = new Point(GamePanel.COL_COUNT / 2, GamePanel.ROW_COUNT / 2);
        snake.add(head);
        gamePanel.setTile(head, TileType.HEAD);

        this.directions = new LinkedList<>();
        directions.clear();
        directions.add(startingDirection());
    }

    public TileType updateSnake() {
        Direction direction = directions.peekFirst();

        Point head = new Point(snake.peekFirst());
        switch (direction) {
            case UP:
                head.y--;
                break;

            case DOWN:
                head.y++;
                break;

            case LEFT:
                head.x--;
                break;

            case RIGHT:
                head.x++;
                break;
        }

        if (head.x < 0 || head.x >= GamePanel.COL_COUNT
                || head.y < 0 || head.y >= gamePanel.ROW_COUNT) {
            return TileType.BLOCK;
        }

        TileType old = gamePanel.getTile(head.x, head.y);
        if (old != TileType.FRUIT && snake.size() > MIN_SNAKE_LENGTH) {
            Point tail = snake.removeLast();
            gamePanel.setTile(tail, TileType.EMPTY);
            old = gamePanel.getTile(head.x, head.y);
        }

        if (old != TileType.BODY) {
            gamePanel.setTile(snake.peekFirst(), TileType.BODY);
            snake.push(head);
            gamePanel.setTile(head, TileType.HEAD);
            if (directions.size() > 1) {
                directions.poll();
            }
        }
        return old;
    }

    private Direction startingDirection() {

        int n = rand.nextInt(4);
        Direction d = null;
        switch (n) {
            case 0:
                d = Direction.LEFT;
                break;
            case 1:
                d = Direction.DOWN;
                break;
            case 2:
                d = Direction.RIGHT;
                break;
            case 3:
                d = Direction.UP;
                break;
        }
        return d;
    }

    public void addDirection(Direction newDirection) {
        Direction oldDirection = directions.peekLast();
        switch (newDirection) {
            case UP:
                if (oldDirection != Direction.DOWN) {
                    directions.addLast(newDirection);
                }
                break;
            case DOWN:
                if (oldDirection != Direction.UP) {
                    directions.addLast(newDirection);
                }
                break;
            case LEFT:
                if (oldDirection != Direction.RIGHT) {
                    directions.addLast(newDirection);
                }
                break;
            case RIGHT:
                if (oldDirection != Direction.LEFT) {
                    directions.addLast(newDirection);
                }
                break;
        }
    }
}
