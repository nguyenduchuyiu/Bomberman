package uet.oop.bomberman.entities.character.enemy.ai;

public class PathFinding {
    double G; //khoang cach tu diem bat dau den dieu hien tai
    double H; //khoang cach tu o hien tai den diem dich
    double F; //gia tri G + H
    int direction;

    public PathFinding(double g, double h, double f, int direction) {
        G = g;
        H = h;
        F = f;
        this.direction = direction;
    }

    public double getG() {
        return G;
    }

    public void setG(double g) {
        G = g;
    }

    public double getH() {
        return H;
    }

    public void setH(double h) {
        H = h;
    }

    public double getF() {
        return F;
    }

    public void setF(double f) {
        F = f;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

}
