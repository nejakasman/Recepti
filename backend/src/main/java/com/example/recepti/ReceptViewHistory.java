package com.example.recepti;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "recept_view_history")
public class ReceptViewHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "recept_id")
    private Recept recept;

    private LocalDateTime ogledTimestamp;

    public ReceptViewHistory(Recept recept) {
        this.recept = recept;
        this.ogledTimestamp = LocalDateTime.now();
    }
}
