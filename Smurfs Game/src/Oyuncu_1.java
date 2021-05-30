public class Oyuncu_1 extends Oyuncu{

    public Oyuncu_1(String ID, String AD, String playerkind) {
        super(ID, AD, playerkind);
    }

    public Oyuncu_1() {
        super("Oyuncu1", "Gozluklu Sirin", "Oyuncu1");
    }

    @Override
    public void PuaniGoster() {
        System.out.println("Gozluklu Sirin");
        super.PuaniGoster();
    }
}
