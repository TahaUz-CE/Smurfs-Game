public class Dusman extends Karakter{


    public Dusman(String ID, String AD, String playerkind) {
        super(ID, AD, playerkind);
    }

    public int puanKaybet(){
        return 0;
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
