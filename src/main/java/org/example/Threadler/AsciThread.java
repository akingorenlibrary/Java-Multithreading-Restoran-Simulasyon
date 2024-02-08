package org.example.Threadler;

import org.example.Nesneler.Masa;
import org.example.RestoranUygulamasi;

import java.util.ArrayList;

public class AsciThread extends Thread {
    private int asciNo;
    private int musteriNo;
    private int oncelik;
    private int masaNo;
    ArrayList<Masa> masalar=new ArrayList<>(6);
    ArrayList<AsciThread> asciThreadListesi = new ArrayList<>();

    ArrayList<MusteriThread> MusteriThreadListesi = new ArrayList<>();
    ArrayList<GarsonThread> garsonThreadListesi = new ArrayList<>();
    public AsciThread(int asciNo) {
        this.asciNo = asciNo;
    }

    public AsciThread(int asciNo, ArrayList<MusteriThread> MusteriThreadListesi, ArrayList<Masa> masalar, ArrayList<GarsonThread> garsonThreadListesi, ArrayList<AsciThread> asciThreadListesi) {
        this.MusteriThreadListesi=MusteriThreadListesi;
        this.masalar=masalar;
        this.asciNo=asciNo;
        this.garsonThreadListesi=garsonThreadListesi;
        this.asciThreadListesi=asciThreadListesi;
    }

    public synchronized void siparisHazirla(int musteriNo, int oncelik, int masaNo, int x) {
        this.musteriNo = musteriNo;
        this.oncelik = oncelik;
        this.masaNo = masaNo;

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Aşçı " + asciNo + " - Müşteri " + musteriNo + " - Öncelik " + oncelik + " - Masa " + masaNo + " siparişini hazırladı.");
        RestoranUygulamasi.asciPanel("Aşçı " + asciNo + " - Müşteri " + musteriNo + " - Öncelik " + oncelik + " - Masa " + masaNo + " siparişini hazırladı.");
        RestoranUygulamasi.asciSiparisHazirlandiMasaGuncelle(masalar);
        RestoranUygulamasi.dosyayaYaziEkle("Aşçı " + asciNo + " - Müşteri " + musteriNo + " - Öncelik " + oncelik + " - Masa " + masaNo + " siparişini hazırladı.");

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Müşteri " + musteriNo + " - Öncelik " + oncelik + " - Masa " + masaNo + " yemeğini yedi.");
        masalar.get(x).yemegiBittiMi=true;
        RestoranUygulamasi.asciMusteriYemeginiYediMasaGuncelle(masalar);
        RestoranUygulamasi.dosyayaYaziEkle("Müşteri " + musteriNo + " - Öncelik " + oncelik + " - Masa " + masaNo + " yemeğini yedi.");
        RestoranUygulamasi.kasaIslemleri(MusteriThreadListesi, masalar, garsonThreadListesi, asciThreadListesi);
    }

    @Override
    public void run() {
       for (int x = 0; x < masalar.size(); x++) {
            if (masalar.get(x) != null && masalar.get(x).masaDoluMu && masalar.get(x).siparisAlindiMi && !masalar.get(x).siparisHazirlandiMi) {
                masalar.get(x).siparisHazirlandiMi=true;
                siparisHazirla(masalar.get(x).getMusteriNo(), masalar.get(x).getOncelik(), masalar.get(x).getMasaNo(),x);
            }
        }
    }

}
