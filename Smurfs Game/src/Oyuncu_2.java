public class Oyuncu_2 extends Oyuncu{

    public Oyuncu_2(String ID, String AD, String playerkind) {
        super(ID, AD, playerkind);
    }

    public Oyuncu_2() {
        super("Oyuncu2", "Uykucu Sirin", "Oyuncu2");
    }

    @Override
    public void PuaniGoster() {
        System.out.println("Uykucu Sirin");
        super.PuaniGoster();
    }
}

