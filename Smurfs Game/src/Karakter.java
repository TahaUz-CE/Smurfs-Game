abstract public class Karakter{

    public String ID;
    public String AD;
    public String PlayerKind;
    public Lokasyon lokasyon = new Lokasyon(0,0);


    public Karakter(String ID, String AD, String playerkind) {
        this.ID = ID;
        this.AD = AD;
        this.PlayerKind = playerkind;
    }

    public Karakter() {
        this.ID = "ID";
        this.AD = "AD";
        this.PlayerKind = "playerkind";

    }

    public String getID() {
        return ID;
    }

    public String getAD() {
        return AD;
    }

    public String getPlayerKind() {
        return PlayerKind;
    }

    public void LokasyonGoster() {
        System.out.println("Karaterin Lokasyonu  x = "+lokasyon.getX_ekseni()+" y = "+lokasyon.getY_ekseni());
    }

    public void LokasyonGir(int x,int y) {
        this.lokasyon.setX_ekseni(x);
        this.lokasyon.setY_ekseni(y);
        //System.out.println("Basariyla kordinatlar guncellendi .");
    }

    public int LokasyonX(){
        return lokasyon.getX_ekseni();
    }

    public int LokasyonY(){
        return lokasyon.getY_ekseni();
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setAD(String AD) {
        this.AD = AD;
    }

    public void setPlayerKind(String playerKind) {
        PlayerKind = playerKind;
    }

    abstract public int[][] enKisaYol(int x,int y);
}
