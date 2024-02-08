package org.example.Threadler;

import org.example.Nesneler.Masa;
import org.example.RestoranUygulamasi;

import java.util.ArrayList;

public class KasaThread extends Thread {
    private int kasaNo;
    private int musteriNo;
    private int oncelik;
    private int masaNo;
    ArrayList<Masa> masalar=new ArrayList<>();
    ArrayList<MusteriThread> MusteriThreadListesi = new ArrayList<>();
    ArrayList<GarsonThread> garsonThreadListesi = new ArrayList<>();
    ArrayList<AsciThread> asciThreadListesi = new ArrayList<>();
    public boolean dongudenCik=true;

    public KasaThread(int kasaNo, ArrayList<MusteriThread> MusteriThreadListesi, ArrayList<Masa> masalar, ArrayList<GarsonThread> garsonThreadListesi, ArrayList<AsciThread> asciThreadListesi) {
        this.MusteriThreadListesi=MusteriThreadListesi;
        this.masalar=masalar;
        this.kasaNo=kasaNo;
        this.garsonThreadListesi=garsonThreadListesi;
        this.asciThreadListesi=asciThreadListesi;
    }

    public synchronized void odemeyiAl(int musteriNo, int oncelik, int masaNo, int x) {
        masalar.get(x).MasadanKalk();

        for (MusteriThread musteri:MusteriThreadListesi){
            if(musteri.getMusteriNo()==musteriNo){
                musteri.setDahaOncedenOturduMu(true);
                musteri.setHerhangibirMasadaOturuyorMu(false);
            }
        }

        this.musteriNo = musteriNo;
        this.oncelik = oncelik;
        this.masaNo = masaNo;
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Kasa " + kasaNo + " - Müşteri " + musteriNo + " - Öncelik " + oncelik + " - Masa " + masaNo + " ödemesini yaptı ve kalktı.");
        RestoranUygulamasi.garsonPanel("Kasa " + kasaNo + " - Müşteri " + musteriNo + " - Öncelik " + oncelik + " - Masa " + masaNo + " ödemesini yaptı ve kalktı.");
        RestoranUygulamasi.kasaPanel("Kasa " + kasaNo + " - Müşteri " + musteriNo + " - Öncelik " + oncelik + " - Masa " + masaNo + " ödemesini yaptı ve kalktı.");
        RestoranUygulamasi.kasaOdemesiniYaptiveKalktiMasaGuncelle(masalar, MusteriThreadListesi);
        oturamayanMusterilerYerVarsaOtursun();
    }

    public synchronized void oturamayanMusterilerYerVarsaOtursun(){
        while(true) {
            for (int i = 0; i < MusteriThreadListesi.size(); i++) {
                for (int x = 0; x < masalar.size(); x++) {
                    if (masalar.get(x).masaDoluMu == false && MusteriThreadListesi.get(i).dahaOncedenOturduMu == false && MusteriThreadListesi.get(i).herhangibirMasadaOturuyorMu == false) {
                        MusteriThreadListesi.get(i).setHerhangibirMasadaOturuyorMu(true);
                        MusteriThreadListesi.get(i).setDahaOncedenOturduMu(true);
                        masalar.get(x).yeniBirKisiOtursun(MusteriThreadListesi.get(i).getMusteriNo(), MusteriThreadListesi.get(i).getOncelik());
                        RestoranUygulamasi.yeniBirKisiOtursun(masalar, x, MusteriThreadListesi.get(i).getMusteriNo());
                        RestoranUygulamasi.bekleyenMusteriSayisiAzalt();
                    }
                }
            }


            for (GarsonThread garson : garsonThreadListesi) {
                garson.run();
            }

            for (AsciThread asci : asciThreadListesi) {
                asci.run();
            }

            new KasaThread(kasaNo, MusteriThreadListesi, masalar, garsonThreadListesi, asciThreadListesi).start();


            for (MusteriThread musteri : MusteriThreadListesi) {
                if (musteri.dahaOncedenOturduMu == false) {
                    musteri.dahaOncedenOturduMu = true;
                    if (musteri.sureKontrol(2000)) {
                        //Thread 20 saniye geçti
                        System.out.println("Müşteri " + musteri.getMusteriNo() + " - Öncelik: " + musteri.getOncelik() + " - yer bulamadı ayrıldı.");
                        RestoranUygulamasi.dosyayaYaziEkle("Müşteri " + musteri.getMusteriNo() + " - Öncelik: " + musteri.getOncelik() + " - yer bulamadı ayrıldı.");
                        RestoranUygulamasi.YerBulamadiAyrildiFrameGuncelle();
                    }
                }
            }

            for(MusteriThread musteri:MusteriThreadListesi){
                if(musteri.dahaOncedenOturduMu==false){
                    dongudenCik=false;
                }
            }

            if(dongudenCik==true){
                break;
            }
        }
    }

    @Override
    public void run() {
        for (int x = 0; x < masalar.size(); x++) {
            if (masalar.get(x) != null && masalar.get(x).yemegiBittiMi && !masalar.get(x).odemeAlindiMi) {
                masalar.get(x).odemeAlindiMi=true;
                odemeyiAl(masalar.get(x).getMusteriNo(), masalar.get(x).getOncelik(), masalar.get(x).getMasaNo(), x);
            }
        }
    }
}
