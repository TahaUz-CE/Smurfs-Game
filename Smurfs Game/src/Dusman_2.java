public class Dusman_2 extends Dusman{

    public Dusman_2(String ID, String AD, String playerkind) {
        super(ID, AD, playerkind);
    }

    public Dusman_2() {
        super("Dusman2", "Gargamel", "Dusman2");
    }

    @Override
    public int puanKaybet() {
        return 15;
    }
}
