package com.example.recepti;

public class KomentarRequest {

    private int uporabnikId;
    private int receptId;
    private String komentar;
    private float ocena;

    // Getterji in setterji
    public long getUporabnikId() {
        return uporabnikId;
    }

    public void setUporabnikId(int uporabnikId) {
        this.uporabnikId = uporabnikId;
    }

    public int getReceptId() {
        return receptId;
    }

    public void setReceptId(int receptId) {
        this.receptId = receptId;
    }

    public String getKomentar() {
        return komentar;
    }

    public void setKomentar(String komentar) {
        this.komentar = komentar;
    }

    public float getOcena() {
        return ocena;
    }

    public void setOcena(float ocena) {
        this.ocena = ocena;
    }
}
