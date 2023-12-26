package ooproject;

public enum MapOrientation {
    N(new Vector2d(0, 1)),
    NE(new Vector2d(1, 1)),
    E(new Vector2d(1, 0)),
    SE(new Vector2d(1, -1)),
    S(new Vector2d(0, -1)),
    SW(new Vector2d(-1, -1)),
    W(new Vector2d(-1, 0)),
    NW(new Vector2d(-1, 1));

    private final Vector2d move;

    MapOrientation(Vector2d move) {
        this.move = move;
    }

    public Vector2d getMove() {
        return move;
    }

    public MapOrientation turn(int n) {
        MapOrientation[] orientations = values();
        int newIndex = (this.ordinal() + n + orientations.length) % orientations.length;
        return orientations[newIndex];
    }
}
