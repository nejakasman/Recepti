package com.example.recepti;

import lombok.Getter;
import lombok.Setter;

public class KomentarRequest {

    private int uporabnikId;
    @Setter
    @Getter
    private int receptId;
    @Setter
    @Getter
    private String komentar;


}
