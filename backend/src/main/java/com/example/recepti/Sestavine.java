package com.example.recepti;


import jakarta.persistence.Embeddable;

@Embeddable
public class Sestavine {
    private String ime;
    private String kolicina;
}
