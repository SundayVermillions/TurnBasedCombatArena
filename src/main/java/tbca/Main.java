package tbca;


import tbca.engine.Game;

public class    Main {
    public static void main(String[] args) {
        Game game = Game.getGameInstance();
        game.start();
    }
}
