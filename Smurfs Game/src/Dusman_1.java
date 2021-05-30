public class Dusman_1 extends Dusman{

    public Dusman_1(String ID, String AD, String playerkind) {
        super(ID, AD, playerkind);
    }

    public Dusman_1() {
        super("Dusman1", "Azman", "Dusman1");
    }

    @Override
    public int puanKaybet() {
        return 5;
    }
}
