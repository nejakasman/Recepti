package com.example.recepti;


import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class Sestavine {
    private String ime;
    private String kolicina;
}
