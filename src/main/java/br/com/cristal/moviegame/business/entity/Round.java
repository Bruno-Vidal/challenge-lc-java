package br.com.cristal.moviegame.business.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Round {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

    @ManyToOne
    @JoinColumn(name = "first_movie")
    private Movie firstMovie;

    @ManyToOne
    @JoinColumn(name = "secund_movie")
    private Movie secundMovie;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private RoundStatus roundStatus = RoundStatus.WAITING_REPONSE;

    @Column(name = "round_order")
    private Integer order;

}