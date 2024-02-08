package org.example.Nesneler;

import org.example.RestoranUygulamasi;

public class Masa {

    public static int masaSayisi=6;
    private int musteriNo;
    private int oncelik;
    private final int masaNo;
    public boolean masaDoluMu=false;
    public boolean siparisAlindiMi=false;
    public boolean siparisHazirlandiMi=false;
    public boolean yemegiBittiMi=false;
    public boolean odemeAlindiMi=false;
    public Masa(int masaNo, int musteriNo, int oncelik) {
        this.masaNo=masaNo;
        this.musteriNo = musteriNo;
        this.oncelik = oncelik;
    }


    public void MasayaOtur(){
        RestoranUygulamasi.aktifMusteriSayisiArttir();
        this.masaDoluMu=true;
        System.out.println("Müşteri " + musteriNo + " - Öncelik: " + oncelik + " - Masaya "+masaNo+"'e oturdu.");
        RestoranUygulamasi.dosyayaYaziEkle("Müşteri " + musteriNo + " - Öncelik: " + oncelik + " - Masaya "+masaNo+"'e oturdu.");
        RestoranUygulamasi.garsonPanel("Müşteri " + musteriNo + " - Öncelik: " + oncelik + " - Masaya "+masaNo+"'e oturdu.");
    }

    public void MasadanKalk(){
        RestoranUygulamasi.aktifMusteriSayisiAzalt();
        this.masaDoluMu=false;
        this.odemeAlindiMi=false;
        this.siparisAlindiMi=false;
        this.siparisHazirlandiMi=false;
        this.yemegiBittiMi=false;
    }

    public static int getMasaSayisi() {
        return masaSayisi;
    }

    public int getMusteriNo() {
        return musteriNo;
    }

    public int getOncelik() {
        return oncelik;
    }

    public int getMasaNo() {
        return masaNo;
    }

    public boolean isMasaDoluMu() {
        return masaDoluMu;
    }

    public boolean isSiparisAlindiMi() {
        return siparisAlindiMi;
    }

    public void yeniBirKisiOtursun(int musteriNo, int oncelik){
       if(masaDoluMu==false){
           this.musteriNo=musteriNo;
           this.oncelik=oncelik;
           this.MasayaOtur();
       }
    }

}
