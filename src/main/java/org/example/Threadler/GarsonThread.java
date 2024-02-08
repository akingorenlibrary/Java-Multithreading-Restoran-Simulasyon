package org.example.Threadler;

import org.example.Nesneler.Masa;
import org.example.RestoranUygulamasi;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GarsonThread extends Thread {
    private int garsonNo;
    private int musteriNo;
    private int oncelik;
    private int masaNo;
    ArrayList<Masa> masalar=new ArrayList<>(6);
    java.util.List<MusteriThread> MusteriThreadListesi = new ArrayList<>();
    public GarsonThread(int garsonNo) {
        this.garsonNo = garsonNo;
    }

    public GarsonThread(int garsonNo, java.util.List<MusteriThread> MusteriThreadListesi, ArrayList<Masa> masalar) {
        this.MusteriThreadListesi=MusteriThreadListesi;
        this.masalar=masalar;
        this.garsonNo=garsonNo;
    }

    public GarsonThread(int musteriNo, int oncelik, int garsonNo, int masaNo, ArrayList<Masa> masalar) {
        this.musteriNo = musteriNo;
        this.oncelik = oncelik;
        this.garsonNo = garsonNo;
        this.masaNo = masaNo;
        this.masalar=masalar;
    }

    public synchronized void siparisAl(int musteriNo, int oncelik, int masaNo, int x) throws InterruptedException {
        this.musteriNo = musteriNo;
        this.oncelik = oncelik;
        this.masaNo = masaNo;
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Garson " + garsonNo + " - Müşteri " + musteriNo + " - Öncelik " + oncelik + " - Masa " + masaNo + " den sipariş aldı.");
        RestoranUygulamasi.garsonSiparisMasaGuncelle(masalar);
        RestoranUygulamasi.dosyayaYaziEkle("Garson " + garsonNo + " - Müşteri " + musteriNo + " - Öncelik " + oncelik + " - Masa " + masaNo + " den sipariş aldı.");
        RestoranUygulamasi.garsonPanel("Garson " + garsonNo + " - Müşteri " + musteriNo + " - Öncelik " + oncelik + " - Masa " + masaNo + " den sipariş aldı.");
        RestoranUygulamasi.asciPanel("Garson " + garsonNo + " - Müşteri " + musteriNo + " - Öncelik " + oncelik + " - Masa " + masaNo + " den sipariş aldı.");
    }

    @Override
    public void run(){
        for (int x = 0; x < masalar.size(); x++) {
            if (masalar.get(x) != null && masalar.get(x).masaDoluMu && !masalar.get(x).siparisAlindiMi) {
                masalar.get(x).siparisAlindiMi = true;
                try {
                    siparisAl(masalar.get(x).getMusteriNo(), masalar.get(x).getOncelik(), masalar.get(x).getMasaNo(),x);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public ArrayList<Masa> getMasalar() {
        return masalar;
    }
}
