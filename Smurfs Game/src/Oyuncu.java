public class Oyuncu extends Karakter{

    public int Skor;

    public Oyuncu(String ID, String AD, String playerkind ) {
        super(ID, AD, playerkind);
        this.Skor = 20;
    }

    public Oyuncu() {
        this.Skor = 20;
    }

    public int getSkor() {
        return Skor;
    }

    public void setSkor(int skor) {
        Skor = skor;
    }

    public void PuaniGoster(){
        System.out.println("Oyuncu Skoru : "+this.Skor);
    }

    public int[][] enKisaYol(int x,int y){
        int distx;
        int disty;

        int [][] kordinatlar = new int[1][2];

        distx = y-lokasyon.getY_ekseni();
        disty = x-lokasyon.getX_ekseni();

        kordinatlar[0][0] = distx;
        kordinatlar[0][1] = disty;


        return kordinatlar;
    }
}
