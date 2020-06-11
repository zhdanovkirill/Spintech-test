package com.spintech.testtask.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="actor", uniqueConstraints=@UniqueConstraint(columnNames= {"tmdbId","userId"}))
public class Actor {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "tmdbId")
    private Long tmdbId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", foreignKey = @ForeignKey(name = "user_favorit_actor_fkey"))
    private User user;

}