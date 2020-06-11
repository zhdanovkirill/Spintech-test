package com.spintech.testtask.entity;

import com.spintech.testtask.infrastructure.enums.TVShowStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="tvshow", uniqueConstraints=@UniqueConstraint(columnNames= {"tmdbId","userId"}))
public class TVShow {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "tmdbId")
    private Long tmdbId;

    @Column()
    private TVShowStatus status;

    @Column()
    private String originalTitle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", foreignKey = @ForeignKey(name = "user_tv_show_fkey"))
    private User user;

}