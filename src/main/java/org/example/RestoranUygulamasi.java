package org.example;

import org.example.Nesneler.Masa;
import org.example.Threadler.AsciThread;
import org.example.Threadler.GarsonThread;
import org.example.Threadler.KasaThread;
import org.example.Threadler.MusteriThread;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class RestoranUygulamasi extends JFrame {
    static ArrayList<GarsonThread> garsonThreadListesi = new ArrayList<>();
    static ArrayList<AsciThread> asciThreadListesi = new ArrayList<>();
    static ArrayList<MusteriThread> MusteriThreadListesi = new ArrayList<>();
    int musteriSayisiInt;
    int oncelikliMusteriSayisiInt;
    static ArrayList<Masa> masalar=new ArrayList<>(6);
    static ArrayList<Masa> problem2Masalar=new ArrayList<>();
    static ArrayList<JButton> masalarButon=new ArrayList<>();
    static JPanel genelPanel = new JPanel(new GridLayout(4, 3));
    public static boolean problem2Aktif=false;
    public static int yerBulamadiAyrildiSayac=0;

    JLabel musteriSayisiLabel=new JLabel("");
    JLabel oncelikliMusteriSayisiLabel=new JLabel("");
    static JLabel bekleyenMusteriSayisiLabel=new JLabel("Bekleyen müşteri sayısı: 0");
    static JLabel yerBulamayipAyrilanMusteriSayisiLabel=new JLabel("Yer bulamayıp ayrılan müşteri sayısı: 0");
    static JLabel aktifMusteriSayisiLabel=new JLabel("Aktif müşteri sayısı: 0");
    public static String musteriSayisi;
    String oncelikliMusteriSayisi;
    public static int bekleyenMusteriSayisi=0;
    public static int aktifMusteriSayisi=0;
    static DefaultListModel<String> garsonlistModel = new DefaultListModel<>();
    static DefaultListModel<String> ascilistModel = new DefaultListModel<>();
    static DefaultListModel<String> kasalistModel = new DefaultListModel<>();
    static JPanel garsonPanel = new JPanel();
    static JPanel kasaPanel = new JPanel();
    static JPanel asciPanel = new JPanel();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RestoranUygulamasi());
    }

    public RestoranUygulamasi() {
        setTitle("Restoran Uygulaması");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        File dosya = new File("./log.txt");
        try {
            FileWriter yazma = new FileWriter(dosya, false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        musteriSayisiLabel.setText("Müşteri sayısı: 0");
        oncelikliMusteriSayisiLabel.setText("Öncelikli müşteri sayısı: 0");
        JButton masa1=new JButton("Masa 0");
        JButton masa2=new JButton("Masa 1");
        JButton masa3=new JButton("Masa 2");
        JButton masa4=new JButton("Masa 3");
        JButton masa5=new JButton("Masa 4");
        JButton masa6=new JButton("Masa 5");
        masalarButon.add(masa1);
        masalarButon.add(masa2);
        masalarButon.add(masa3);
        masalarButon.add(masa4);
        masalarButon.add(masa5);
        masalarButon.add(masa6);

        JButton baslatBtn=new JButton("Başlat");
        baslatBtn.setBackground(Color.GRAY);
        baslatBtn.setForeground(Color.WHITE);

        baslatBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                musteriSayisi = JOptionPane.showInputDialog("Müşteri sayısını girin");
                oncelikliMusteriSayisi = JOptionPane.showInputDialog("Oncelikli sayısını girin");

                // Girilen müşteri sayısı kadar thread oluştur
                musteriSayisiInt = Integer.parseInt(musteriSayisi);
                oncelikliMusteriSayisiInt = Integer.parseInt(oncelikliMusteriSayisi);
                MusteriThreadListesi.clear();
                masalar.clear();
                musteriSayisiLabel.setText("Müşteri sayısı: "+musteriSayisi);
                oncelikliMusteriSayisiLabel.setText("Öncelikli müşteri sayısı: "+oncelikliMusteriSayisi);
                genelPanel.revalidate();
                genelPanel.repaint();
                try {
                    musterilerOluturulduveSirayaGirdi();
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });




        genelPanel.add(musteriSayisiLabel);
        genelPanel.add(oncelikliMusteriSayisiLabel);
        genelPanel.add(baslatBtn);
        genelPanel.add(bekleyenMusteriSayisiLabel);
        genelPanel.add(yerBulamayipAyrilanMusteriSayisiLabel);
        genelPanel.add(aktifMusteriSayisiLabel);


        for (JButton button : masalarButon) {
            button.setBackground(Color.GRAY);
            button.setForeground(Color.WHITE);
            genelPanel.add(button);
        }

        add(genelPanel);
        setVisible(true);
        setSize(800,600);

        //garson paneli
        JFrame garsonFrame = new JFrame("Garson Arayüz");
        garsonFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        garsonFrame.setPreferredSize(new Dimension(400, 200));

        JList<String> jList = new JList<>(garsonlistModel);
        JScrollPane scrollPane = new JScrollPane(jList);

        garsonPanel.add(scrollPane);
        garsonFrame.add(garsonPanel);
        garsonFrame.pack();
        garsonFrame.setLocationRelativeTo(null);
        garsonFrame.setVisible(true);

        //asci paneli
        JFrame asciFrame = new JFrame("Asçı Arayüz");
        asciFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        asciFrame.setPreferredSize(new Dimension(400, 200));

        JList<String> jListAsci = new JList<>(ascilistModel);
        JScrollPane scrollPaneAsci = new JScrollPane(jListAsci);

        asciPanel.add(scrollPaneAsci);
        asciFrame.add(asciPanel);
        asciFrame.pack();
        asciFrame.setLocationRelativeTo(null);
        asciFrame.setVisible(true);

        //kasa paneli
        JFrame kasaFrame = new JFrame("Kasa Arayüz");
        kasaFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        kasaFrame.setPreferredSize(new Dimension(400, 200));

        JList<String> jListKasa = new JList<>(kasalistModel);
        JScrollPane scrollPaneKasa = new JScrollPane(jListKasa);

        kasaPanel.add(scrollPaneKasa);
        kasaFrame.add(kasaPanel);
        kasaFrame.pack();
        kasaFrame.setLocationRelativeTo(null);
        kasaFrame.setVisible(true);
    }

    public static void garsonPanel(String metin){
        garsonlistModel.addElement(metin);
        garsonPanel.revalidate();
        garsonPanel.repaint();
    }

    public static void kasaPanel(String metin){
        kasalistModel.addElement(metin);
        kasaPanel.revalidate();
        kasaPanel.repaint();
    }

    public static void asciPanel(String metin){
        ascilistModel.addElement(metin);
        asciPanel.revalidate();
        asciPanel.repaint();
    }


    public void musterilerOluturulduveSirayaGirdi() throws InterruptedException {
        //müşteriler oluşturuldu ve sıraya girildi
        for (int i = 0; i < musteriSayisiInt; i++) {
            int oncelik = (i < oncelikliMusteriSayisiInt) ? 1 : 0;
            MusteriThread musteri = new MusteriThread(i + 1, oncelik);
            MusteriThreadListesi.add(musteri);
            new Thread(musteri).start();
        }

        int a=JOptionPane.showConfirmDialog(this,"Müşteriler masalara otursun mu?");
        if(a==JOptionPane.YES_OPTION){
            musterilerMasalaraOturuyor();
        }else if(a==JOptionPane.NO_OPTION){
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }
    }

    public void musterilerMasalaraOturuyor() throws InterruptedException {
        //müşteriler masalara oturuyor
        int masaSayac=0;
        for (MusteriThread musteri : MusteriThreadListesi) {
            if (masaSayac >= 0 && masaSayac<=5) {
                masalar.add(new Masa(masaSayac, musteri.getMusteriNo(), musteri.getOncelik()));
                masalarButon.get(masaSayac).setText("Masa "+masaSayac+" | Müşteri "+musteri.getMusteriNo()+" Öncelik "+musteri.getOncelik()+" oturdu.");
                masalar.get(masaSayac).MasayaOtur();
                musteri.setHerhangibirMasadaOturuyorMu(true);
                musteri.setDahaOncedenOturduMu(true);
                Masa.masaSayisi--;
                masaSayac++;
            } else {
                bekleyenMusteriSayisi++;
                bekleyenMusteriSayisiLabel.setText("Bekleyen müşteri sayısı: "+bekleyenMusteriSayisi);
                genelPanel.revalidate();
                genelPanel.repaint();
                System.out.println("Müşteri " + musteri.getMusteriNo() + " - Öncelik: " + musteri.getOncelik() + " - Boş masa kalmadı.");
            }
        }
        int a=JOptionPane.showConfirmDialog(this,"Garsonlar sipariş alsın mı?");
        if(a==JOptionPane.YES_OPTION){
            garsonlarSiparisAliyor();
        }else if(a==JOptionPane.NO_OPTION){
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }
    }

    public static void aktifMusteriSayisiArttir(){
        aktifMusteriSayisi++;
        aktifMusteriSayisiLabel.setText("Aktif müşteri sayısı: "+aktifMusteriSayisi);
        genelPanel.revalidate();
        genelPanel.repaint();
    }

    public static void aktifMusteriSayisiAzalt(){
        aktifMusteriSayisi--;
        aktifMusteriSayisiLabel.setText("Aktif müşteri sayısı: "+aktifMusteriSayisi);
        genelPanel.revalidate();
        genelPanel.repaint();
    }
    public static void bekleyenMusteriSayisiAzalt(){
        if(bekleyenMusteriSayisi>0){
            bekleyenMusteriSayisi--;
        }
        bekleyenMusteriSayisiLabel.setText("Bekleyen müşteri sayısı: "+bekleyenMusteriSayisi);
        genelPanel.revalidate();
        genelPanel.repaint();
    }
    public static void yeniBirKisiOtursun(ArrayList<Masa> masaListe, int masaNo, int musteriNo){
        masalar=masaListe;
        masalarButon.get(masaNo).setText("Masa "+masaNo+" | Müşteri "+musteriNo+" oturdu.");
    }

    public static void garsonSiparisMasaGuncelle(ArrayList<Masa> masalarListe){
        for(int i=0;i<masalarListe.size();i++){
            if(masalarListe.get(i).siparisAlindiMi){
                masalarButon.get(i).setText("Masa "+i+" sipariş alındı.");
                genelPanel.revalidate();
                genelPanel.repaint();
            }
        }
    }

    public static void asciSiparisHazirlandiMasaGuncelle(ArrayList<Masa> masalarListe){
        for(int i=0;i<masalarListe.size();i++){
            if(masalarListe.get(i).siparisAlindiMi && masalarListe.get(i).siparisHazirlandiMi){
                masalarButon.get(i).setText("Masa "+i+" siparişi hazırlandı.");
                genelPanel.revalidate();
                genelPanel.repaint();
            }
        }
    }

    public static void asciMusteriYemeginiYediMasaGuncelle(ArrayList<Masa> masalarListe){
        for(int i=0;i<masalarListe.size();i++){
            if(masalarListe.get(i).yemegiBittiMi){
                masalarButon.get(i).setText("Masa "+i+" yemeğini yedi.");
                genelPanel.revalidate();
                genelPanel.repaint();
            }
        }
    }

    public static void kasaOdemesiniYaptiveKalktiMasaGuncelle(ArrayList<Masa> masalarListe, ArrayList<MusteriThread> musteriListe){
        for(int i=0;i<masalarListe.size();i++){
            for (MusteriThread musteri:musteriListe){
                if(musteri.getMusteriNo()==masalarListe.get(i).getMusteriNo() && musteri.dahaOncedenOturduMu && !musteri.herhangibirMasadaOturuyorMu){
                    masalarButon.get(i).setText("Masa "+i+" ödemesini yaptı ve kalktı.");
                    Masa.masaSayisi++;
                    genelPanel.revalidate();
                    genelPanel.repaint();
                }
            }
        }
    }

    public void garsonlarSiparisAliyor() throws InterruptedException {
        for (int i = 0; i < 3; i++) {
            GarsonThread garson = new GarsonThread(i+1, MusteriThreadListesi, masalar);
            garsonThreadListesi.add(garson);
            new Thread(garson).start();
        }

        int a=JOptionPane.showConfirmDialog(this,"Aşçılar siparişleri hazırlasın mı?");
        if(a==JOptionPane.YES_OPTION){
            ascilarSiparisleriHazirliyor();
        }else if(a==JOptionPane.NO_OPTION){
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }
    }

    public static void ascilarSiparisleriHazirliyor(){
        for (int i = 0; i < 2; i++) {
            AsciThread asci = new AsciThread(i+1, MusteriThreadListesi, masalar, garsonThreadListesi, asciThreadListesi);
            asciThreadListesi.add(asci);
            new Thread(asci).start();
        }
    }


    public static void kasaIslemleri(ArrayList<MusteriThread> MusteriThreadListe, ArrayList<Masa> masalarListe, ArrayList<GarsonThread> garsonThreadListe, ArrayList<AsciThread> asciThreadListe ){
        KasaThread kasa = new KasaThread(1, MusteriThreadListe, masalarListe, garsonThreadListe, asciThreadListe);
        new Thread(kasa).start();
    }

    public static void YerBulamadiAyrildiFrameGuncelle(){
        yerBulamadiAyrildiSayac++;
        yerBulamayipAyrilanMusteriSayisiLabel.setText("Yer bulamayıp ayrılan müşteri sayısı: "+yerBulamadiAyrildiSayac);
        genelPanel.revalidate();
        genelPanel.repaint();
    }

    public static void dosyayaYaziEkle(String metin) {
        try {
            File dosya = new File("./log.txt");
            FileWriter yazma = new FileWriter(dosya, true);
            BufferedWriter yaz = new BufferedWriter(yazma);
            yaz.write(metin);
            yaz.newLine();
            yaz.close();
        } catch (Exception err) {
            System.out.println(err);
        }
    }
}


