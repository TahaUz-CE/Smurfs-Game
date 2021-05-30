import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Timer;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MazeBord extends JFrame {

    static int adim = 0, k = 0, k1 = 0, temp_k = 0, temp_k1 = 0, check_azman = 1, check_gargamel = 1, check_oyuncu = 1, bitti = 0;
    static int hamle = 0, eskiX = 0, eskiY = 0, sayacAltin = 5, sayacMantar = 1, secim = 0, path_secim = 0, dusman_hareket = 0;
    static int[][] bord = new int[11][13];
    static int random1, random2;
    static boolean game = true;
    static int[][] sifirla = new int[20][2];
    static int[][] sifirla2 = new int[20][2];
    static int scale = 50;
    static int[][] kordinat_kisayol = new int[1][2];
    static int first_X, first_Y, first_X_a, first_Y_a, mod = 0;
    public Image mantar, altin, gozluklu, uykucu, sirine, gargamell, azmann, background, gozluklu_bg, uykucu_bg;

    public MazeBord() {

        Oyuncu oyuncu1 = new Oyuncu_1();
        Oyuncu oyuncu2 = new Oyuncu_2();

        Obje mantar = new Mantar();
        Obje altin = new Altin();

        Timer myTimer = new Timer();

        oyuncu1.LokasyonGir(5, 6);
        oyuncu2.LokasyonGir(5, 6);

        String fileName = "harita.txt";
        List<String> satirList = new ArrayList<>();
        List<String> karakter = new ArrayList<>();
        List<String> kapi = new ArrayList<>();

        Frame();

        // Bu Kod Parçası "harita.txt" 'den Satır Satır Dosya Okuyor.
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            satirList = stream.collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Bu Kod Parçası Karakterlerin İsmini ve Kapı Çeşitlerini Alır.
        for (int i = 0; i < satirList.size(); i++) {
            if (satirList.get(i).charAt(0) == '1' || satirList.get(i).charAt(0) == '0') {
                break;
            }
            karakter.add(satirList.get(i).substring(satirList.get(i).indexOf(':') + 1, satirList.get(i).indexOf(',')));
            kapi.add(satirList.get(i).substring(satirList.get(i).indexOf('i') + 2));
        }

        // "Harita.txt" ' den 0 ile 1 leri okuyarak harita belirliyor.
        for (int i = karakter.size(); i < satirList.size(); i++) {
            // İlgili satırdaki TAB karakterlerini siliyor.
            String a = satirList.get(i).replace("\t", "");
            for (int j = 0; j < a.length(); j++) {
                bord[i - karakter.size()][j] = a.charAt(j) - 48;
            }
        }

        Dusman azman = new Dusman_1();
        Dusman gargamel = new Dusman_2();


        for (int i = 0; i < karakter.size(); i++) {
            if (karakter.get(i).toLowerCase().equals("azman")) {
                if (kapi.get(i).toUpperCase().equals("A")) {
                    azman.LokasyonGir(0, 3);
                } else if (kapi.get(i).toUpperCase().equals("B")) {
                    azman.LokasyonGir(0, 10);
                } else if (kapi.get(i).toUpperCase().equals("C")) {
                    azman.LokasyonGir(5, 0);
                } else if (kapi.get(i).toUpperCase().equals("D")) {
                    azman.LokasyonGir(10, 3);
                }
            } else if (karakter.get(i).toLowerCase().equals("gargamel")) {
                if (kapi.get(i).toUpperCase().equals("A")) {
                    gargamel.LokasyonGir(0, 3);
                } else if (kapi.get(i).toUpperCase().equals("B")) {
                    gargamel.LokasyonGir(0, 10);
                } else if (kapi.get(i).toUpperCase().equals("C")) {
                    gargamel.LokasyonGir(5, 0);
                } else if (kapi.get(i).toUpperCase().equals("D")) {
                    gargamel.LokasyonGir(10, 3);
                }
            } else {
                System.out.println("Dusman Karakter Bulunamadi");
            }
        }


        loadImage();

        addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {

                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    if (secim == 0) {
                        check_oyuncu = 1;
                        secim = 1;
                    } else if (bitti == 0) {
                        adim = 1;
                    }
                    dusman_hareket = 1;

                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {

                    if (secim == 0) {
                        check_oyuncu = 2;
                        secim = 1;
                    } else if (bitti == 0) {
                        adim = 3;
                    }
                    dusman_hareket = 1;

                } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                    if (bitti == 0) {

                        adim = 5;
                    }
                    dusman_hareket = 1;
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    if (bitti == 0) {

                        adim = 2;
                    }
                    dusman_hareket = 1;
                } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    adim = 4;
                } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    path_secim++;
                } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    mod++;
                }
            }

        });

        while (true) {
            if (secim == 0) {
                showIntro(getGraphics());
            } else {
                break;
            }
        }

        bord[7][12] = 7;

        first_X = gargamel.LokasyonX();
        first_Y = gargamel.LokasyonY();

        first_X_a = azman.LokasyonX();
        first_Y_a = azman.LokasyonY();

        if (gargamel.LokasyonX() == 0 & gargamel.LokasyonY() == 0) {
            check_gargamel = 0;
        } else {
            bord[gargamel.LokasyonX()][gargamel.LokasyonY()] = 6;
        }
        if (azman.LokasyonX() == 0 & azman.LokasyonY() == 0) {
            check_azman = 0;
        } else {
            bord[azman.LokasyonX()][azman.LokasyonY()] = 5;
        }

        TimerTask gorev = new TimerTask() {
            @Override
            public void run() {

                // Random Altin Uretir
                for (int i = 0; i < sayacAltin; i++) {
                    random1 = (int) (Math.random() * bord.length);
                    random2 = (int) (Math.random() * bord[0].length);
                    if (bord[random1][random2] == 1) {
                        sifirla[k][0] = random1;
                        sifirla[k][1] = random2;
                        bord[random1][random2] = 3;
                        k++;
                    } else {
                        sayacAltin++;
                    }
                }
                sayacAltin = 5;
                temp_k = k;
                k = 0;

                if (!game) {
                    myTimer.cancel();
                }
            }
        };

        myTimer.schedule(gorev, 0, 5050);

        TimerTask gorev2 = new TimerTask() {
            @Override
            public void run() {
                int a, b;

                // Altinlar Uretildigi yerden silindigi kod satiri

                for (int i = 0; i < temp_k; i++) {
                    a = sifirla[i][0];
                    b = sifirla[i][1];
                    bord[a][b] = 1;

                }
            }
        };

        myTimer.schedule(gorev2, 5000, 5000);


        TimerTask gorev3 = new TimerTask() {
            @Override
            public void run() {
                // Random Mantar Uretir
                for (int i = 0; i < sayacMantar; i++) {
                    random1 = (int) (Math.random() * bord.length);
                    random2 = (int) (Math.random() * bord[0].length);
                    if (bord[random1][random2] == 1) {
                        sifirla2[k1][0] = random1;
                        sifirla2[k1][1] = random2;
                        bord[random1][random2] = 4;
                        k1++;
                    } else {
                        sayacMantar++;
                    }
                }
                sayacMantar = 1;
                temp_k1 = k1;
                k1 = 0;
            }
        };

        myTimer.schedule(gorev3, 0, 7050);

        TimerTask gorev4 = new TimerTask() {
            @Override
            public void run() {
                int a, b;

                // Mantarlar Uretildigi yerden silindigi kod satiri
                for (int i = 0; i < temp_k1; i++) {
                    a = sifirla2[i][0];
                    b = sifirla2[i][1];
                    bord[a][b] = 1;

                }
            }
        };

        myTimer.schedule(gorev4, 7000, 7000);

        TimerTask gorev5 = new TimerTask() {
            @Override
            public void run() {

                if (check_gargamel == 1) {
                    if (bord[gargamel.LokasyonX()][gargamel.LokasyonY()] != 9) {
                        bord[gargamel.LokasyonX()][gargamel.LokasyonY()] = 6;
                    }
                }

                if (check_azman == 1) {
                    if (bord[azman.LokasyonX()][azman.LokasyonY()] != 9) {
                        bord[azman.LokasyonX()][azman.LokasyonY()] = 5;
                    }
                }

                if (check_oyuncu == 1) {
                    bord[oyuncu1.LokasyonX()][oyuncu1.LokasyonY()] = 9;
                } else if (check_oyuncu == 2) {
                    bord[oyuncu2.LokasyonX()][oyuncu2.LokasyonY()] = 9;
                }

                paint(getGraphics());
                if (check_oyuncu == 1) {
                    if (oyuncu1.getSkor() <= 0) {
                        myTimer.cancel();
                        scoreShow(getGraphics(), oyuncu1.getSkor());
                        gameOverShow(getGraphics(), oyuncu1.getSkor());
                    } else {
                        scoreShow(getGraphics(), oyuncu1.getSkor());
                    }
                } else if (check_oyuncu == 2) {
                    if (oyuncu2.getSkor() <= 0) {
                        myTimer.cancel();
                        scoreShow(getGraphics(), oyuncu2.getSkor());
                        gameOverShow(getGraphics(), oyuncu2.getSkor());

                    } else {
                        scoreShow(getGraphics(), oyuncu2.getSkor());
                    }
                }

                if (!game) {
                    myTimer.cancel();
                }
            }
        };

        myTimer.schedule(gorev5, 0, 500);

        if (check_gargamel == 1) {
            TimerTask gorev6 = new TimerTask() {
                @Override
                public void run() {

                    if (check_oyuncu == 1) {
                        kordinat_kisayol = gargamel.enKisaYol(oyuncu1.LokasyonX(), oyuncu1.LokasyonY());
                    } else if (check_oyuncu == 2) {
                        kordinat_kisayol = gargamel.enKisaYol(oyuncu2.LokasyonX(), oyuncu2.LokasyonY());
                    }

                    int x_kisa = kordinat_kisayol[0][0];
                    int y_kisa = kordinat_kisayol[0][1];

                    int sayac1 = 0, sayac2 = 0, sayac3 = 0, sayac4 = 0;
                    if (path_secim % 2 == 1) {
                        drawPath(getGraphics(), x_kisa, y_kisa, gargamel.LokasyonX(), gargamel.LokasyonY(), false);
                    }

                    if (kordinat_kisayol[0][0] == 0 & kordinat_kisayol[0][1] == 0) {
                        if (check_oyuncu == 1) {
                            oyuncu1.setSkor(oyuncu1.getSkor() - gargamel.puanKaybet());
                        } else if (check_oyuncu == 2) {
                            oyuncu2.setSkor(oyuncu2.getSkor() - gargamel.puanKaybet());
                        }
                        gargamel.LokasyonGir(first_X, first_Y);
                    }

                    if (dusman_hareket == 1) {


                        int random_r = (int) (Math.random() * 2);

                        if (random_r == 0) {
                            if (kordinat_kisayol[0][0] > 0) {

                                if (bord[0].length > gargamel.LokasyonY() + 2) {
                                    if (bord[gargamel.LokasyonX()][gargamel.LokasyonY() + 1] == 1 |
                                            bord[gargamel.LokasyonX()][gargamel.LokasyonY() + 1] == 9 |
                                            bord[gargamel.LokasyonX()][gargamel.LokasyonY() + 1] == 5) {
                                        if (bord[gargamel.LokasyonX()][gargamel.LokasyonY() + 2] == 1 | bord[gargamel.LokasyonX()][gargamel.LokasyonY() + 2] == 9) {
                                            gargamel.LokasyonGir(gargamel.LokasyonX(), gargamel.LokasyonY() + 2);
                                            bord[gargamel.LokasyonX()][gargamel.LokasyonY() - 2] = 1;
                                            sayac1++;
                                        }
                                    }
                                    if (bord.length > gargamel.LokasyonX() + 2 & sayac1 == 0) {
                                        if (bord[gargamel.LokasyonX() + 1][gargamel.LokasyonY()] == 1 | bord[gargamel.LokasyonX() + 1][gargamel.LokasyonY()] == 9 | bord[gargamel.LokasyonX() + 1][gargamel.LokasyonY()] == 5) {
                                            if (bord[gargamel.LokasyonX() + 2][gargamel.LokasyonY()] == 1 | bord[gargamel.LokasyonX() + 2][gargamel.LokasyonY()] == 9) {
                                                gargamel.LokasyonGir(gargamel.LokasyonX() + 2, gargamel.LokasyonY());
                                                bord[gargamel.LokasyonX() - 2][gargamel.LokasyonY()] = 1;
                                            }
                                        } else if (gargamel.LokasyonX() - 1 > 0) {
                                            if (bord[gargamel.LokasyonX() - 1][gargamel.LokasyonY()] == 1 | bord[gargamel.LokasyonX() - 1][gargamel.LokasyonY()] == 9 | bord[gargamel.LokasyonX() - 1][gargamel.LokasyonY()] == 5) {
                                                if (gargamel.LokasyonX() - 2 > 0) {
                                                    if (bord[gargamel.LokasyonX() - 2][gargamel.LokasyonY()] == 1 | bord[gargamel.LokasyonX() - 2][gargamel.LokasyonY()] == 9) {
                                                        gargamel.LokasyonGir(gargamel.LokasyonX() - 2, gargamel.LokasyonY());
                                                        bord[gargamel.LokasyonX() + 2][gargamel.LokasyonY()] = 1;
                                                    }
                                                } else if (bord[gargamel.LokasyonX()][gargamel.LokasyonY() - 1] == 1 | bord[gargamel.LokasyonX()][gargamel.LokasyonY() - 1] == 9 | bord[gargamel.LokasyonX()][gargamel.LokasyonY() - 1] == 5) {
                                                    if (gargamel.LokasyonY() - 2 > 0) {
                                                        if (bord[gargamel.LokasyonX()][gargamel.LokasyonY() - 2] == 1 | bord[gargamel.LokasyonX()][gargamel.LokasyonY() - 2] == 9) {
                                                            gargamel.LokasyonGir(gargamel.LokasyonX(), gargamel.LokasyonY() - 2);
                                                            bord[gargamel.LokasyonX()][gargamel.LokasyonY() + 2] = 1;
                                                        }
                                                    }
                                                }
                                            }

                                        }
                                    }

                                }

                            } else if (kordinat_kisayol[0][0] < 0) {

                                if (gargamel.LokasyonY() - 2 > 0) {
                                    if (bord[gargamel.LokasyonX()][gargamel.LokasyonY() - 1] == 1 | bord[gargamel.LokasyonX()][gargamel.LokasyonY() - 1] == 9 | bord[gargamel.LokasyonX()][gargamel.LokasyonY() - 1] == 5) {
                                        if (bord[gargamel.LokasyonX()][gargamel.LokasyonY() - 2] == 1 | bord[gargamel.LokasyonX()][gargamel.LokasyonY() - 2] == 9) {
                                            gargamel.LokasyonGir(gargamel.LokasyonX(), gargamel.LokasyonY() - 2);
                                            bord[gargamel.LokasyonX()][gargamel.LokasyonY() + 2] = 1;
                                            sayac2++;
                                        }
                                    }
                                    if (gargamel.LokasyonX() - 2 > 0 & sayac2 == 0) {
                                        if (bord[gargamel.LokasyonX() - 1][gargamel.LokasyonY()] == 1 | bord[gargamel.LokasyonX() - 1][gargamel.LokasyonY()] == 9 | bord[gargamel.LokasyonX() - 1][gargamel.LokasyonY()] == 5) {
                                            if (bord[gargamel.LokasyonX() - 2][gargamel.LokasyonY()] == 1 | bord[gargamel.LokasyonX() - 2][gargamel.LokasyonY()] == 9) {
                                                gargamel.LokasyonGir(gargamel.LokasyonX() - 2, gargamel.LokasyonY());
                                                bord[gargamel.LokasyonX() + 2][gargamel.LokasyonY()] = 1;
                                            }
                                        }
                                    }

                                }

                            }
                        } else if (random_r == 1) {
                            if (kordinat_kisayol[0][1] > 0) {
                                if (bord.length > gargamel.LokasyonX() + 2) {
                                    if (bord[gargamel.LokasyonX() + 1][gargamel.LokasyonY()] == 1 | bord[gargamel.LokasyonX() + 1][gargamel.LokasyonY()] == 9 | bord[gargamel.LokasyonX() + 1][gargamel.LokasyonY()] == 5) {
                                        if (bord[gargamel.LokasyonX() + 2][gargamel.LokasyonY()] == 1 | bord[gargamel.LokasyonX() + 2][gargamel.LokasyonY()] == 9) {
                                            gargamel.LokasyonGir(gargamel.LokasyonX() + 2, gargamel.LokasyonY());
                                            bord[gargamel.LokasyonX() - 2][gargamel.LokasyonY()] = 1;
                                            sayac3++;
                                        }
                                    }

                                    if (bord[0].length > gargamel.LokasyonY() + 2 & sayac3 == 0) {
                                        if (bord[gargamel.LokasyonX()][gargamel.LokasyonY() + 1] == 1 | bord[gargamel.LokasyonX()][gargamel.LokasyonY() + 1] == 9 | bord[gargamel.LokasyonX()][gargamel.LokasyonY() + 1] == 5) {
                                            if (bord[gargamel.LokasyonX()][gargamel.LokasyonY() + 2] == 1 | bord[gargamel.LokasyonX()][gargamel.LokasyonY() + 2] == 9) {
                                                gargamel.LokasyonGir(gargamel.LokasyonX(), gargamel.LokasyonY() + 2);
                                                bord[gargamel.LokasyonX()][gargamel.LokasyonY() - 2] = 1;
                                            }
                                        }
                                    }

                                }

                            } else if (kordinat_kisayol[0][1] < 0) {

                                if (gargamel.LokasyonX() - 2 > 0) {
                                    if (bord[gargamel.LokasyonX() - 1][gargamel.LokasyonY()] == 1 | bord[gargamel.LokasyonX() - 1][gargamel.LokasyonY()] == 9 | bord[gargamel.LokasyonX() - 1][gargamel.LokasyonY()] == 5) {
                                        if (bord[gargamel.LokasyonX() - 2][gargamel.LokasyonY()] == 1 | bord[gargamel.LokasyonX() - 2][gargamel.LokasyonY()] == 9) {
                                            gargamel.LokasyonGir(gargamel.LokasyonX() - 2, gargamel.LokasyonY());
                                            bord[gargamel.LokasyonX() + 2][gargamel.LokasyonY()] = 1;
                                            sayac4++;
                                        }
                                    }
                                }

                                if (gargamel.LokasyonY() - 2 > 0 & sayac4 == 0) {
                                    if (bord[gargamel.LokasyonX()][gargamel.LokasyonY() - 1] == 1 | bord[gargamel.LokasyonX()][gargamel.LokasyonY() - 1] == 9 | bord[gargamel.LokasyonX()][gargamel.LokasyonY() - 1] == 5) {
                                        if (bord[gargamel.LokasyonX()][gargamel.LokasyonY() - 2] == 1 | bord[gargamel.LokasyonX()][gargamel.LokasyonY() - 2] == 9) {
                                            gargamel.LokasyonGir(gargamel.LokasyonX(), gargamel.LokasyonY() - 2);
                                            bord[gargamel.LokasyonX()][gargamel.LokasyonY() + 2] = 1;
                                        }
                                    }
                                }


                            }
                        }


                        if (mod % 2 == 0) {
                            dusman_hareket = 0;
                        }

                    }


                }
            };

            myTimer.schedule(gorev6, 0, 500);
        }

        if (check_azman == 1) {
            TimerTask gorev7 = new TimerTask() {
                @Override
                public void run() {

                    if (check_oyuncu == 1) {
                        kordinat_kisayol = azman.enKisaYol(oyuncu1.LokasyonX(), oyuncu1.LokasyonY());
                    } else if (check_oyuncu == 2) {
                        kordinat_kisayol = azman.enKisaYol(oyuncu2.LokasyonX(), oyuncu2.LokasyonY());
                    }

                    int x_kisa = kordinat_kisayol[0][0];
                    int y_kisa = kordinat_kisayol[0][1];

                    int sayac1_a = 0, sayac2_a = 0, sayac3_a = 0, sayac4_a = 0;

                    if (path_secim % 2 == 1) {
                        drawPath(getGraphics(), x_kisa, y_kisa, azman.LokasyonX(), azman.LokasyonY(), true);
                    }

                    if (kordinat_kisayol[0][0] == 0 & kordinat_kisayol[0][1] == 0) {
                        if (check_oyuncu == 1) {
                            oyuncu1.setSkor(oyuncu1.getSkor() - azman.puanKaybet());
                        } else if (check_oyuncu == 2) {
                            oyuncu2.setSkor(oyuncu2.getSkor() - azman.puanKaybet());
                        }
                        azman.LokasyonGir(first_X_a, first_Y_a);
                    }

                    if (dusman_hareket == 1) {


                        int random_r = (int) (Math.random() * 2);

                        if (random_r == 0) {
                            if (kordinat_kisayol[0][0] > 0) {

                                if (bord[0].length > azman.LokasyonY() + 1) {
                                    if (bord[azman.LokasyonX()][azman.LokasyonY() + 1] == 1 | bord[azman.LokasyonX()][azman.LokasyonY() + 1] == 9) {
                                        azman.LokasyonGir(azman.LokasyonX(), azman.LokasyonY() + 1);
                                        bord[azman.LokasyonX()][azman.LokasyonY() - 1] = 1;
                                        sayac1_a++;
                                    }
                                    if (bord.length > azman.LokasyonX() + 1 & sayac1_a == 0) {
                                        if (bord[azman.LokasyonX() + 1][azman.LokasyonY()] == 1 | bord[azman.LokasyonX() + 1][azman.LokasyonY()] == 9) {
                                            azman.LokasyonGir(azman.LokasyonX() + 1, azman.LokasyonY());
                                            bord[azman.LokasyonX() - 1][azman.LokasyonY()] = 1;
                                        } else if (azman.LokasyonX() - 1 > 0) {
                                            if (bord[azman.LokasyonX() - 1][azman.LokasyonY()] == 1 | bord[azman.LokasyonX() - 1][azman.LokasyonY()] == 9) {
                                                azman.LokasyonGir(azman.LokasyonX() - 1, azman.LokasyonY());
                                                bord[azman.LokasyonX() + 1][azman.LokasyonY()] = 1;
                                            } else if (azman.LokasyonY() - 1 > 0) {
                                                if (bord[azman.LokasyonX()][azman.LokasyonY() - 1] == 1 | bord[azman.LokasyonX()][azman.LokasyonY() - 1] == 9) {
                                                    azman.LokasyonGir(azman.LokasyonX(), azman.LokasyonY() - 1);
                                                    bord[azman.LokasyonX()][azman.LokasyonY() + 1] = 1;
                                                }
                                            }
                                        }

                                    }

                                }

                            } else if (kordinat_kisayol[0][0] < 0) {

                                if (azman.LokasyonY() - 1 > 0) {
                                    if (bord[azman.LokasyonX()][azman.LokasyonY() - 1] == 1 | bord[azman.LokasyonX()][azman.LokasyonY() - 1] == 9) {
                                        azman.LokasyonGir(azman.LokasyonX(), azman.LokasyonY() - 1);
                                        bord[azman.LokasyonX()][azman.LokasyonY() + 1] = 1;
                                        sayac2_a++;
                                    }
                                    if (azman.LokasyonX() - 1 > 0 & sayac2_a == 0) {
                                        if (bord[azman.LokasyonX() - 1][azman.LokasyonY()] == 1 | bord[azman.LokasyonX() - 1][azman.LokasyonY()] == 9) {
                                            azman.LokasyonGir(azman.LokasyonX() - 1, azman.LokasyonY());
                                            bord[azman.LokasyonX() + 1][azman.LokasyonY()] = 1;
                                        }
                                    }

                                }

                            }
                        } else if (random_r == 1) {
                            if (kordinat_kisayol[0][1] > 0) {
                                if (bord.length > azman.LokasyonX() + 1) {
                                    if (bord[azman.LokasyonX() + 1][azman.LokasyonY()] == 1 | bord[azman.LokasyonX() + 1][azman.LokasyonY()] == 9) {
                                        azman.LokasyonGir(azman.LokasyonX() + 1, azman.LokasyonY());
                                        bord[azman.LokasyonX() - 1][azman.LokasyonY()] = 1;
                                        sayac3_a++;
                                    }

                                    if (bord[0].length > azman.LokasyonY() + 1 & sayac3_a == 0) {
                                        if (bord[azman.LokasyonX()][azman.LokasyonY() + 1] == 1 | bord[azman.LokasyonX()][azman.LokasyonY() + 1] == 9) {
                                            azman.LokasyonGir(azman.LokasyonX(), azman.LokasyonY() + 1);
                                            bord[azman.LokasyonX()][azman.LokasyonY() - 1] = 1;
                                        }
                                    }

                                }

                            } else if (kordinat_kisayol[0][1] < 0) {

                                if (azman.LokasyonX() - 1 > 0) {
                                    if (bord[azman.LokasyonX() - 1][azman.LokasyonY()] == 1 | bord[azman.LokasyonX() - 1][azman.LokasyonY()] == 9) {
                                        azman.LokasyonGir(azman.LokasyonX() - 1, azman.LokasyonY());
                                        bord[azman.LokasyonX() + 1][azman.LokasyonY()] = 1;
                                        sayac4_a++;
                                    }
                                }

                                if (azman.LokasyonY() - 1 > 0 & sayac4_a == 0) {
                                    if (bord[azman.LokasyonX()][azman.LokasyonY() - 1] == 1 | bord[azman.LokasyonX()][azman.LokasyonY() - 1] == 9) {
                                        azman.LokasyonGir(azman.LokasyonX(), azman.LokasyonY() - 1);
                                        bord[azman.LokasyonX()][azman.LokasyonY() + 1] = 1;
                                    }
                                }


                            }
                        }


                        if (mod % 2 == 0) {
                            dusman_hareket = 0;
                        }
                    }

                }
            };

            myTimer.schedule(gorev7, 0, 500);
        }


        if (check_oyuncu == 1) {
            while (true) {


                if (hamle == 0) {
                    bord[oyuncu1.LokasyonX()][oyuncu1.LokasyonY()] = 9;
                    eskiX = oyuncu1.LokasyonX();
                    eskiY = oyuncu1.LokasyonY();
                    paint(getGraphics());

                }
                if (hamle == 1) {
                    bord[eskiX][eskiY] = 1;
                }


                if (adim == 3) {
                    adim = 0;
                    if (bord[0].length > oyuncu1.LokasyonY() + 2) {
                        if (bord[oyuncu1.LokasyonX()][oyuncu1.LokasyonY() + 1] != 0 & bord[oyuncu1.LokasyonX()][oyuncu1.LokasyonY() + 2] != 0) {
                            if (bord[oyuncu1.LokasyonX()][oyuncu1.LokasyonY() + 1] == 3) {
                                oyuncu1.setSkor(oyuncu1.getSkor() + altin.PuanKazan());
                                bord[oyuncu1.LokasyonX()][oyuncu1.LokasyonY() + 1] = 1;
                            }
                            if (bord[oyuncu1.LokasyonX()][oyuncu1.LokasyonY() + 2] == 3) {
                                oyuncu1.setSkor(oyuncu1.getSkor() + altin.PuanKazan());
                            }
                            if (bord[oyuncu1.LokasyonX()][oyuncu1.LokasyonY() + 1] == 4) {
                                oyuncu1.setSkor(oyuncu1.getSkor() + mantar.PuanKazan());
                                bord[oyuncu1.LokasyonX()][oyuncu1.LokasyonY() + 1] = 1;
                            }
                            if (bord[oyuncu1.LokasyonX()][oyuncu1.LokasyonY() + 2] == 4) {
                                oyuncu1.setSkor(oyuncu1.getSkor() + mantar.PuanKazan());
                            }
                            if (bord[oyuncu1.LokasyonX()][oyuncu1.LokasyonY() + 1] == 7) {
                                bord[oyuncu1.LokasyonX()][oyuncu1.LokasyonY() + 1] = 9;
                                oyuncu1.LokasyonGir(oyuncu1.LokasyonX(), oyuncu1.LokasyonY() + 1);
                                bord[oyuncu1.LokasyonX()][oyuncu1.LokasyonY() - 1] = 1;

                                System.out.println("Game Over");
                                oyuncu1.PuaniGoster();
                                myTimer.cancel();
                                game = false;
                                paint(getGraphics());
                                scoreShow(getGraphics(), oyuncu1.getSkor());
                                gameOverShow(getGraphics(), oyuncu1.getSkor());
                                bitti = 1;
                                adim = 7;
                            }
                            if (bord[oyuncu1.LokasyonX()][oyuncu1.LokasyonY() + 2] == 7) {
                                bord[oyuncu1.LokasyonX()][oyuncu1.LokasyonY() + 2] = 9;
                                oyuncu1.LokasyonGir(oyuncu1.LokasyonX(), oyuncu1.LokasyonY() + 2);
                                bord[oyuncu1.LokasyonX()][oyuncu1.LokasyonY() - 2] = 1;

                                System.out.println("Game Over");
                                oyuncu1.PuaniGoster();
                                myTimer.cancel();
                                game = false;
                                paint(getGraphics());
                                scoreShow(getGraphics(), oyuncu1.getSkor());
                                gameOverShow(getGraphics(), oyuncu1.getSkor());
                                bitti = 1;
                                adim = 7;
                            }
                            if (adim != 7) {
                                bord[oyuncu1.LokasyonX()][oyuncu1.LokasyonY() + 2] = 9;
                                oyuncu1.LokasyonGir(oyuncu1.LokasyonX(), oyuncu1.LokasyonY() + 2);
                                if (hamle != 0) {
                                    bord[oyuncu1.LokasyonX()][oyuncu1.LokasyonY() - 2] = 1;
                                }
                            }
                        } else {
                            System.out.println("Duvara Carptin Adamim Dikkatli Ol !");
                        }
                    }
                } else if (adim == 1) {
                    adim = 0;
                    if (oyuncu1.LokasyonY() - 2 >= 0) {
                        if (bord[oyuncu1.LokasyonX()][oyuncu1.LokasyonY() - 1] != 0 & bord[oyuncu1.LokasyonX()][oyuncu1.LokasyonY() - 2] != 0) {
                            if (bord[oyuncu1.LokasyonX()][oyuncu1.LokasyonY() - 1] == 3) {
                                oyuncu1.setSkor(oyuncu1.getSkor() + altin.PuanKazan());
                                bord[oyuncu1.LokasyonX()][oyuncu1.LokasyonY() - 1] = 1;
                            }
                            if (bord[oyuncu1.LokasyonX()][oyuncu1.LokasyonY() - 2] == 3) {
                                oyuncu1.setSkor(oyuncu1.getSkor() + altin.PuanKazan());
                            }
                            if (bord[oyuncu1.LokasyonX()][oyuncu1.LokasyonY() - 1] == 4) {
                                oyuncu1.setSkor(oyuncu1.getSkor() + mantar.PuanKazan());
                                bord[oyuncu1.LokasyonX()][oyuncu1.LokasyonY() - 1] = 1;
                            }
                            if (bord[oyuncu1.LokasyonX()][oyuncu1.LokasyonY() - 2] == 4) {
                                oyuncu1.setSkor(oyuncu1.getSkor() + mantar.PuanKazan());
                            }
                            if (bord[oyuncu1.LokasyonX()][oyuncu1.LokasyonY() - 1] == 7) {
                                bord[oyuncu1.LokasyonX()][oyuncu1.LokasyonY() - 1] = 9;
                                oyuncu1.LokasyonGir(oyuncu1.LokasyonX(), oyuncu1.LokasyonY() - 1);
                                bord[oyuncu1.LokasyonX()][oyuncu1.LokasyonY() + 1] = 1;

                                System.out.println("Game Over");
                                oyuncu1.PuaniGoster();
                                myTimer.cancel();
                                game = false;
                                paint(getGraphics());
                                scoreShow(getGraphics(), oyuncu1.getSkor());
                                gameOverShow(getGraphics(), oyuncu1.getSkor());
                                bitti = 1;
                                adim = 7;
                            }
                            if (bord[oyuncu1.LokasyonX()][oyuncu1.LokasyonY() - 2] == 7) {
                                bord[oyuncu1.LokasyonX()][oyuncu1.LokasyonY() - 2] = 9;
                                oyuncu1.LokasyonGir(oyuncu1.LokasyonX(), oyuncu1.LokasyonY() - 2);
                                bord[oyuncu1.LokasyonX()][oyuncu1.LokasyonY() + 2] = 1;

                                System.out.println("Game Over");
                                oyuncu1.PuaniGoster();
                                myTimer.cancel();
                                game = false;
                                paint(getGraphics());
                                scoreShow(getGraphics(), oyuncu1.getSkor());
                                gameOverShow(getGraphics(), oyuncu1.getSkor());
                                bitti = 1;
                                adim = 7;
                            }
                            if (adim != 7) {
                                bord[oyuncu1.LokasyonX()][oyuncu1.LokasyonY() - 2] = 9;
                                oyuncu1.LokasyonGir(oyuncu1.LokasyonX(), oyuncu1.LokasyonY() - 2);
                                if (hamle != 0) {
                                    bord[oyuncu1.LokasyonX()][oyuncu1.LokasyonY() + 2] = 1;
                                }
                            }
                        } else {
                            System.out.println("Duvara Carptin Adamim Dikkatli Ol !");
                        }
                    }
                } else if (adim == 2) {
                    adim = 0;
                    if (bord.length > oyuncu1.LokasyonX() + 2) {
                        if (bord[oyuncu1.LokasyonX() + 1][oyuncu1.LokasyonY()] != 0 & bord[oyuncu1.LokasyonX() + 2][oyuncu1.LokasyonY()] != 0) {
                            if (bord[oyuncu1.LokasyonX() + 1][oyuncu1.LokasyonY()] == 3) {
                                oyuncu1.setSkor(oyuncu1.getSkor() + altin.PuanKazan());
                                bord[oyuncu1.LokasyonX() + 1][oyuncu1.LokasyonY()] = 1;
                            }
                            if (bord[oyuncu1.LokasyonX() + 2][oyuncu1.LokasyonY()] == 3) {
                                oyuncu1.setSkor(oyuncu1.getSkor() + altin.PuanKazan());
                            }
                            if (bord[oyuncu1.LokasyonX() + 1][oyuncu1.LokasyonY()] == 4) {
                                oyuncu1.setSkor(oyuncu1.getSkor() + mantar.PuanKazan());
                                bord[oyuncu1.LokasyonX() + 1][oyuncu1.LokasyonY()] = 1;
                            }
                            if (bord[oyuncu1.LokasyonX() + 2][oyuncu1.LokasyonY()] == 4) {
                                oyuncu1.setSkor(oyuncu1.getSkor() + mantar.PuanKazan());
                            }
                            if (bord[oyuncu1.LokasyonX() + 1][oyuncu1.LokasyonY()] == 7) {
                                bord[oyuncu1.LokasyonX() + 1][oyuncu1.LokasyonY()] = 9;
                                oyuncu1.LokasyonGir(oyuncu1.LokasyonX() + 1, oyuncu1.LokasyonY());
                                bord[oyuncu1.LokasyonX() - 1][oyuncu1.LokasyonY()] = 1;

                                System.out.println("Game Over");
                                oyuncu1.PuaniGoster();
                                myTimer.cancel();
                                game = false;
                                paint(getGraphics());
                                scoreShow(getGraphics(), oyuncu1.getSkor());
                                gameOverShow(getGraphics(), oyuncu1.getSkor());
                                bitti = 1;
                                adim = 7;

                            }
                            if (bord[oyuncu1.LokasyonX() + 2][oyuncu1.LokasyonY()] == 7) {
                                bord[oyuncu1.LokasyonX() + 2][oyuncu1.LokasyonY()] = 9;
                                oyuncu1.LokasyonGir(oyuncu1.LokasyonX() + 2, oyuncu1.LokasyonY());
                                bord[oyuncu1.LokasyonX() - 2][oyuncu1.LokasyonY()] = 1;

                                System.out.println("Game Over");
                                oyuncu1.PuaniGoster();
                                myTimer.cancel();
                                game = false;
                                paint(getGraphics());
                                scoreShow(getGraphics(), oyuncu1.getSkor());
                                gameOverShow(getGraphics(), oyuncu1.getSkor());
                                bitti = 1;
                                adim = 7;

                            }
                            if (adim != 7) {
                                bord[oyuncu1.LokasyonX() + 2][oyuncu1.LokasyonY()] = 9;
                                oyuncu1.LokasyonGir(oyuncu1.LokasyonX() + 2, oyuncu1.LokasyonY());
                                if (hamle != 0) {
                                    bord[oyuncu1.LokasyonX() - 2][oyuncu1.LokasyonY()] = 1;
                                }
                            }

                        } else {
                            System.out.println("Duvara Carptin Adamim Dikkatli Ol !");
                        }
                    }
                } else if (adim == 5) {
                    adim = 0;
                    if (oyuncu1.LokasyonX() - 2 >= 0) {
                        if (bord[oyuncu1.LokasyonX() - 1][oyuncu1.LokasyonY()] != 0 & bord[oyuncu1.LokasyonX() - 2][oyuncu1.LokasyonY()] != 0) {
                            if (bord[oyuncu1.LokasyonX() - 1][oyuncu1.LokasyonY()] == 3) {
                                oyuncu1.setSkor(oyuncu1.getSkor() + altin.PuanKazan());
                                bord[oyuncu1.LokasyonX() - 1][oyuncu1.LokasyonY()] = 1;
                            }
                            if (bord[oyuncu1.LokasyonX() - 2][oyuncu1.LokasyonY()] == 3) {
                                oyuncu1.setSkor(oyuncu1.getSkor() + altin.PuanKazan());
                            }
                            if (bord[oyuncu1.LokasyonX() - 1][oyuncu1.LokasyonY()] == 4) {
                                oyuncu1.setSkor(oyuncu1.getSkor() + mantar.PuanKazan());
                                bord[oyuncu1.LokasyonX() - 1][oyuncu1.LokasyonY()] = 1;
                            }
                            if (bord[oyuncu1.LokasyonX() - 2][oyuncu1.LokasyonY()] == 4) {
                                oyuncu1.setSkor(oyuncu1.getSkor() + mantar.PuanKazan());
                            }
                            if (bord[oyuncu1.LokasyonX() - 1][oyuncu1.LokasyonY()] == 7) {
                                bord[oyuncu1.LokasyonX() - 1][oyuncu1.LokasyonY()] = 9;
                                oyuncu1.LokasyonGir(oyuncu1.LokasyonX() - 1, oyuncu1.LokasyonY());
                                bord[oyuncu1.LokasyonX() + 1][oyuncu1.LokasyonY()] = 1;

                                System.out.println("Game Over");
                                oyuncu1.PuaniGoster();
                                myTimer.cancel();
                                game = false;
                                paint(getGraphics());
                                scoreShow(getGraphics(), oyuncu1.getSkor());
                                gameOverShow(getGraphics(), oyuncu1.getSkor());
                                bitti = 1;
                                adim = 7;
                            }
                            if (bord[oyuncu1.LokasyonX() - 2][oyuncu1.LokasyonY()] == 7) {
                                bord[oyuncu1.LokasyonX() - 2][oyuncu1.LokasyonY()] = 9;
                                oyuncu1.LokasyonGir(oyuncu1.LokasyonX() - 2, oyuncu1.LokasyonY());
                                bord[oyuncu1.LokasyonX() + 2][oyuncu1.LokasyonY()] = 1;

                                System.out.println("Game Over");
                                oyuncu1.PuaniGoster();
                                myTimer.cancel();
                                game = false;
                                paint(getGraphics());
                                scoreShow(getGraphics(), oyuncu1.getSkor());
                                gameOverShow(getGraphics(), oyuncu1.getSkor());
                                bitti = 1;
                                adim = 7;
                            }
                            if (adim != 7) {
                                bord[oyuncu1.LokasyonX() - 2][oyuncu1.LokasyonY()] = 9;
                                oyuncu1.LokasyonGir(oyuncu1.LokasyonX() - 2, oyuncu1.LokasyonY());
                                if (hamle != 0) {
                                    bord[oyuncu1.LokasyonX() + 2][oyuncu1.LokasyonY()] = 1;
                                }
                            }
                        } else {
                            System.out.println("Duvara Carptin Adamim Dikkatli Ol !");
                        }
                    }
                }

                hamle++;

                if (bitti == 1) {

                    scoreShow(getGraphics(), oyuncu1.getSkor());
                    gameOverShow(getGraphics(), oyuncu1.getSkor());
                }

                if (adim == 4) {
                    System.exit(0);
                }
            }

        } else {
            while (true) {


                if (hamle == 0) {
                    bord[oyuncu2.LokasyonX()][oyuncu2.LokasyonY()] = 9;
                    eskiX = oyuncu2.LokasyonX();
                    eskiY = oyuncu2.LokasyonY();
                    paint(getGraphics());
                }
                if (hamle == 1) {
                    bord[eskiX][eskiY] = 1;
                }


                if (adim == 3) {
                    adim = 0;
                    if (bord[0].length > oyuncu2.LokasyonY() + 1) {
                        if (bord[oyuncu2.LokasyonX()][oyuncu2.LokasyonY() + 1] != 0) {
                            if (bord[oyuncu2.LokasyonX()][oyuncu2.LokasyonY() + 1] == 3) {
                                oyuncu2.setSkor(oyuncu2.getSkor() + altin.PuanKazan());
                            }
                            if (bord[oyuncu2.LokasyonX()][oyuncu2.LokasyonY() + 1] == 4) {
                                oyuncu2.setSkor(oyuncu2.getSkor() + mantar.PuanKazan());
                            }
                            if (bord[oyuncu2.LokasyonX()][oyuncu2.LokasyonY() + 1] == 7) {
                                bord[oyuncu2.LokasyonX()][oyuncu2.LokasyonY() + 1] = 9;
                                oyuncu2.LokasyonGir(oyuncu2.LokasyonX(), oyuncu2.LokasyonY() + 1);
                                bord[oyuncu2.LokasyonX()][oyuncu2.LokasyonY() - 1] = 1;

                                System.out.println("Game Over");
                                oyuncu2.PuaniGoster();
                                myTimer.cancel();
                                game = false;
                                paint(getGraphics());
                                scoreShow(getGraphics(), oyuncu2.getSkor());
                                gameOverShow(getGraphics(), oyuncu2.getSkor());
                                bitti = 1;
                                adim = 7;
                            }
                            if (adim != 7) {
                                bord[oyuncu2.LokasyonX()][oyuncu2.LokasyonY() + 1] = 9;
                                oyuncu2.LokasyonGir(oyuncu2.LokasyonX(), oyuncu2.LokasyonY() + 1);
                                if (hamle != 0) {
                                    bord[oyuncu2.LokasyonX()][oyuncu2.LokasyonY() - 1] = 1;
                                }
                            }
                        } else {
                            System.out.println("Duvara Carptin Adamim Dikkatli Ol !");
                        }
                    }
                } else if (adim == 1) {
                    adim = 0;
                    if (oyuncu2.LokasyonY() - 1 >= 0) {
                        if (bord[oyuncu2.LokasyonX()][oyuncu2.LokasyonY() - 1] != 0) {
                            if (bord[oyuncu2.LokasyonX()][oyuncu2.LokasyonY() - 1] == 3) {
                                oyuncu2.setSkor(oyuncu2.getSkor() + altin.PuanKazan());
                            }
                            if (bord[oyuncu2.LokasyonX()][oyuncu2.LokasyonY() - 1] == 4) {
                                oyuncu2.setSkor(oyuncu2.getSkor() + mantar.PuanKazan());
                            }
                            if (bord[oyuncu2.LokasyonX()][oyuncu2.LokasyonY() - 1] == 7) {
                                bord[oyuncu2.LokasyonX()][oyuncu2.LokasyonY() - 1] = 9;
                                oyuncu2.LokasyonGir(oyuncu2.LokasyonX(), oyuncu2.LokasyonY() - 1);
                                bord[oyuncu2.LokasyonX()][oyuncu2.LokasyonY() + 1] = 1;

                                System.out.println("Game Over");
                                oyuncu2.PuaniGoster();
                                myTimer.cancel();
                                game = false;
                                paint(getGraphics());
                                scoreShow(getGraphics(), oyuncu2.getSkor());
                                gameOverShow(getGraphics(), oyuncu2.getSkor());
                                bitti = 1;
                                adim = 7;
                            }
                            if (adim != 7) {
                                bord[oyuncu2.LokasyonX()][oyuncu2.LokasyonY() - 1] = 9;
                                oyuncu2.LokasyonGir(oyuncu2.LokasyonX(), oyuncu2.LokasyonY() - 1);
                                if (hamle != 0) {
                                    bord[oyuncu2.LokasyonX()][oyuncu2.LokasyonY() + 1] = 1;
                                }
                            }
                        } else {
                            System.out.println("Duvara Carptin Adamim Dikkatli Ol !");
                        }
                    }
                } else if (adim == 2) {
                    adim = 0;
                    if (bord.length > oyuncu2.LokasyonX() + 1) {
                        if (bord[oyuncu2.LokasyonX() + 1][oyuncu2.LokasyonY()] != 0) {
                            if (bord[oyuncu2.LokasyonX() + 1][oyuncu2.LokasyonY()] == 3) {
                                oyuncu2.setSkor(oyuncu2.getSkor() + altin.PuanKazan());
                            }
                            if (bord[oyuncu2.LokasyonX() + 1][oyuncu2.LokasyonY()] == 4) {
                                oyuncu2.setSkor(oyuncu2.getSkor() + mantar.PuanKazan());
                            }
                            if (bord[oyuncu2.LokasyonX() + 1][oyuncu2.LokasyonY()] == 7) {
                                bord[oyuncu2.LokasyonX() + 1][oyuncu2.LokasyonY()] = 9;
                                oyuncu2.LokasyonGir(oyuncu2.LokasyonX() + 1, oyuncu2.LokasyonY());
                                bord[oyuncu2.LokasyonX() - 1][oyuncu2.LokasyonY()] = 1;

                                System.out.println("Game Over");
                                oyuncu2.PuaniGoster();
                                myTimer.cancel();
                                game = false;
                                paint(getGraphics());
                                scoreShow(getGraphics(), oyuncu2.getSkor());
                                gameOverShow(getGraphics(), oyuncu2.getSkor());
                                bitti = 1;
                                adim = 7;

                            }
                            if (adim != 7) {
                                bord[oyuncu2.LokasyonX() + 1][oyuncu2.LokasyonY()] = 9;
                                oyuncu2.LokasyonGir(oyuncu2.LokasyonX() + 1, oyuncu2.LokasyonY());
                                if (hamle != 0) {
                                    bord[oyuncu2.LokasyonX() - 1][oyuncu2.LokasyonY()] = 1;
                                }
                            }

                        } else {
                            System.out.println("Duvara Carptin Adamim Dikkatli Ol !");
                        }
                    }
                } else if (adim == 5) {
                    adim = 0;
                    if (oyuncu2.LokasyonX() - 1 >= 0) {
                        if (bord[oyuncu2.LokasyonX() - 1][oyuncu2.LokasyonY()] != 0) {
                            if (bord[oyuncu2.LokasyonX() - 1][oyuncu2.LokasyonY()] == 3) {
                                oyuncu2.setSkor(oyuncu2.getSkor() + altin.PuanKazan());
                            }
                            if (bord[oyuncu2.LokasyonX() - 1][oyuncu2.LokasyonY()] == 4) {
                                oyuncu2.setSkor(oyuncu2.getSkor() + mantar.PuanKazan());
                            }
                            if (bord[oyuncu2.LokasyonX() - 1][oyuncu2.LokasyonY()] == 7) {
                                bord[oyuncu2.LokasyonX() - 1][oyuncu2.LokasyonY()] = 9;
                                oyuncu2.LokasyonGir(oyuncu2.LokasyonX() - 1, oyuncu2.LokasyonY());
                                bord[oyuncu2.LokasyonX() + 1][oyuncu2.LokasyonY()] = 1;

                                System.out.println("Game Over");
                                oyuncu2.PuaniGoster();
                                myTimer.cancel();
                                game = false;
                                paint(getGraphics());
                                scoreShow(getGraphics(), oyuncu2.getSkor());
                                gameOverShow(getGraphics(), oyuncu2.getSkor());
                                bitti = 1;
                                adim = 7;
                            }
                            if (adim != 7) {
                                bord[oyuncu2.LokasyonX() - 1][oyuncu2.LokasyonY()] = 9;
                                oyuncu2.LokasyonGir(oyuncu2.LokasyonX() - 1, oyuncu2.LokasyonY());
                                if (hamle != 0) {
                                    bord[oyuncu2.LokasyonX() + 1][oyuncu2.LokasyonY()] = 1;
                                }
                            }
                        } else {
                            System.out.println("Duvara Carptin Adamim Dikkatli Ol !");
                        }
                    }
                }

                hamle++;
                if (bitti == 1) {

                    scoreShow(getGraphics(), oyuncu2.getSkor());
                    gameOverShow(getGraphics(), oyuncu2.getSkor());
                }
                if (adim == 4) {
                    System.exit(0);
                }
            }
        }


    }

    public void paint(Graphics g) {

        super.paint(g);

        for (int i = 0; i < bord.length; i++) {
            for (int j = 0; j < bord[0].length; j++) {
                if ((i == 0 & j == 3) | (i == 0 & j == 10) | (i == 5 & j == 0) | (i == 10 & j == 3)) {
                    g.setColor(Color.PINK);
                    g.fillRect((j * scale) + 20, (i * scale) + 32, scale, scale);
                }
                if (bord[i][j] == 1) {
                    g.setColor(Color.WHITE);
                } else if (bord[i][j] == 0) {
                    g.setColor(Color.GRAY);
                } else if (bord[i][j] == 3) {
                    g.setColor(Color.YELLOW);
                    g.drawImage(altin, (j * scale) + 20, (i * scale) + 32, this);
                } else if (bord[i][j] == 4) {
                    g.setColor(Color.RED);
                    g.drawImage(mantar, (j * scale) + 20, (i * scale) + 32, this);
                } else if (bord[i][j] == 9) {
                    g.setColor(Color.BLUE);
                    if (check_oyuncu == 1) {
                        g.drawImage(gozluklu, (j * scale) + 20, (i * scale) + 32, this);
                    } else {
                        g.drawImage(uykucu, (j * scale) + 20, (i * scale) + 32, this);
                    }

                } else if (bord[i][j] == 5) {
                    g.drawImage(azmann, (j * scale) + 20, (i * scale) + 32, this);
                } else if (bord[i][j] == 6) {
                    g.drawImage(gargamell, (j * scale) + 20, (i * scale) + 32, this);
                } else if (bord[i][j] == 7) {
                    g.drawImage(sirine, (j * scale) + 20, (i * scale) + 32, this);
                }


                if (bord[i][j] == 1 | bord[i][j] == 0) {
                    if ((i == 0 & j == 3) | (i == 0 & j == 10) | (i == 5 & j == 0) | (i == 10 & j == 3)) {

                    } else {
                        g.fillRect((j * scale) + 20, (i * scale) + 32, scale, scale);
                    }
                }
            }
        }

    }

    public void Frame() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize((bord[0].length * scale) + 32, (bord.length * scale) + 50);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public void loadImage() {
        mantar = new ImageIcon("mantar.jpg").getImage();
        altin = new ImageIcon("gold.png").getImage();
        gozluklu = new ImageIcon("gozluklu.jpg").getImage();
        gargamell = new ImageIcon("gargamel.jpg").getImage();
        azmann = new ImageIcon("azman.jpg").getImage();
        sirine = new ImageIcon("sirine.jpg").getImage();
        uykucu = new ImageIcon("uykucu.jpg").getImage();
        background = new ImageIcon("TheSmurfs.jpg").getImage();
        uykucu_bg = new ImageIcon("uykucu1.jpg").getImage();
        gozluklu_bg = new ImageIcon("gozluklu1.jpg").getImage();
    }

    public void scoreShow(Graphics g, int skor) {
        String score = "Score : " + skor;
        g.setColor(Color.YELLOW);
        g.setFont(new Font("Arial", Font.BOLD, 14));
        g.drawString(score, (bord[0].length) + 10, (bord.length * scale) + 20);

        String exit = "EXIT GAME ==> ESC ";
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString(exit, (bord[0].length * scale) - 200, (bord.length * scale) + 20);

        String info = "Space Tusuna Basarak Dusman Karakterlerinin Gidicegi Yolu Görebilirsiniz";
        g.setColor(Color.BLUE);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString(info, (bord[0].length * scale) - 600, (bord.length * scale) - 5);

        String info2 = "Real Time Mod ==> ENTER ";
        g.setColor(Color.YELLOW);
        g.setFont(new Font("Arial", Font.BOLD, 14));
        g.drawString(info2, (bord[0].length) + 230, (bord.length * scale) + 20);

    }

    public void gameOverShow(Graphics g, int skor) {
        if (skor > 0) {
            String score_ = "WINNER SMURFS !";
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.BOLD, 50));
            g.drawString(score_, ((bord.length * scale) / 2) - 150, ((bord[0].length * scale) / 2));
        } else {
            String score_ = "GAME OVER";
            bitti = 1;
            g.setColor(Color.red);
            g.setFont(new Font("Arial", Font.BOLD, 50));
            g.drawString(score_, ((bord.length * scale) / 2) - 80, ((bord[0].length * scale) / 2));
        }

    }

    public void showIntro(Graphics g) {
        g.drawImage(background, ((bord.length * scale) / 2) - 130, 40, this);
        String start = " Welcome The Smurfs Game";
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 40));
        g.drawString(start, ((bord.length * scale) / 2) - 220, ((bord[0].length * scale) / 2));

        String karakter = "Karakter Secimi Yapmak Icin Ok Tuslarini Kullanin";
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString(karakter, ((bord.length * scale) / 2) - 180, ((bord[0].length * scale) / 2) + 50);

        String karakter1 = "Gozluklu Sirin(Sol OK Tusu)              Uykucu Sirin(Sag Ok Tusu) ";
        g.setColor(Color.BLUE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString(karakter1, ((bord.length * scale) / 2) - 240, ((bord[0].length * scale) / 2) + 90);

        String karakter2 = "<===  ===>";
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 45));
        g.drawString(karakter2, ((bord.length * scale) / 2) - 50, ((bord[0].length * scale) / 2) + 170);

        g.drawImage(gozluklu_bg, ((bord.length * scale) / 2) - 150, ((bord[0].length * scale) / 2) + 107, this);
        g.drawImage(uykucu_bg, ((bord.length * scale) / 2) + 200, ((bord[0].length * scale) / 2) + 107, this);

    }

    public void drawPath(Graphics g, int x, int y, int dusman_x, int dusman_y, boolean select) {


        if (select) {
            g.setColor(Color.GREEN);
            if (x > 0) {
                for (int i = x; i > 0; i--) {
                    g.fillRect(((dusman_y + i) * scale) + 20, (dusman_x * scale) + 32, scale, scale);
                }
            } else if (x < 0) {
                for (int i = x; i < 0; i++) {
                    g.fillRect(((dusman_y + i) * scale) + 20, (dusman_x * scale) + 32, scale, scale);
                }
            }

            if (y > 0) {
                for (int i = y; i > 0; i--) {
                    g.fillRect(((dusman_y + x) * scale) + 20, ((dusman_x + i) * scale) + 32, scale, scale);
                }
            } else if (y < 0) {
                for (int i = y; i < 0; i++) {
                    g.fillRect(((dusman_y + x) * scale) + 20, ((dusman_x + i) * scale) + 32, scale, scale);
                }
            }
        }

        if (!select) {
            g.setColor(Color.RED);
            if (y > 0) {
                for (int i = y; i > 0; i--) {
                    g.fillRect(((dusman_y) * scale) + 20, ((dusman_x + i) * scale) + 32, scale, scale);
                }
            } else if (y < 0) {
                for (int i = y; i < 0; i++) {
                    g.fillRect(((dusman_y) * scale) + 20, ((dusman_x + i) * scale) + 32, scale, scale);
                }
            }

            if (x > 0) {
                for (int i = x; i > 0; i--) {
                    g.fillRect(((dusman_y + i) * scale) + 20, ((dusman_x + y) * scale) + 32, scale, scale);
                }
            } else if (x < 0) {
                for (int i = x; i < 0; i++) {
                    g.fillRect(((dusman_y + i) * scale) + 20, ((dusman_x + y) * scale) + 32, scale, scale);
                }
            }

        }

        if (check_oyuncu == 1) {
            g.drawImage(gozluklu, ((dusman_y + x) * scale) + 20, ((dusman_x + y) * scale) + 32, this);
        } else {
            g.drawImage(uykucu, ((dusman_y + x) * scale) + 20, ((dusman_x + y) * scale) + 32, this);
        }


    }
}
