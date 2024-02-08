package org.example.Threadler;

import org.example.Nesneler.Masa;
import org.example.RestoranUygulamasi;

public class MusteriThread extends Thread {
    private final int musteriNo;
    private final int oncelik;
    public boolean dahaOncedenOturduMu=false;
    public boolean herhangibirMasadaOturuyorMu=false;
    public long olusturulmaZamani;
    public MusteriThread(int musteriNo, int oncelik) {
        this.musteriNo = musteriNo;
        this.oncelik = oncelik;

        if(oncelik==1){
            this.setPriority(Thread.MIN_PRIORITY);
        }
    }

    @Override
    public void run() {
        this.olusturulmaZamani = System.currentTimeMillis();
        System.out.println("Müşteri " + musteriNo + " - Öncelik: " + oncelik + " - Sırada Bekliyor");
        RestoranUygulamasi.dosyayaYaziEkle("Müşteri " + musteriNo + " - Öncelik: " + oncelik + " - Sırada Bekliyor");
    }

    public int getMusteriNo(){
        return this.musteriNo;
    }

    public int getOncelik(){
        return this.oncelik;
    }

    public void setPriorityN(){
        this.setPriority(Thread.NORM_PRIORITY);
    }

    public void setPriorityM(){
        this.setPriority(Thread.MIN_PRIORITY);
    }

    public boolean isDahaOncedenOturduMu() {
        return dahaOncedenOturduMu;
    }

    public void setDahaOncedenOturduMu(boolean dahaOncedenOturduMu) {
        this.dahaOncedenOturduMu = dahaOncedenOturduMu;
    }

    public boolean isHerhangibirMasadaOturuyorMu() {
        return herhangibirMasadaOturuyorMu;
    }

    public void setHerhangibirMasadaOturuyorMu(boolean herhangibirMasadaOturuyorMu) {
        this.herhangibirMasadaOturuyorMu = herhangibirMasadaOturuyorMu;
    }

    public long getOlusturulmaZamani() {
        return olusturulmaZamani;
    }

    public void setOlusturulmaZamani(long olusturulmaZamani) {
        this.olusturulmaZamani = olusturulmaZamani;
    }

    public boolean sureKontrol(long milisn) {
        long simdikiZaman = System.currentTimeMillis();
        long farkZamani = simdikiZaman - olusturulmaZamani;
        return farkZamani >= milisn;
    }


}