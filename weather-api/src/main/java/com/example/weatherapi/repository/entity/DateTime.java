package com.example.weatherapi.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder

@Entity
@Table(name= "date_dim")
public class DateTime implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    private String date;
    private int year;
    private String month;
    private int dayOfMonth;
    private int monthOfYear;
    private int dayOfYear;
    private String day;
    private int dayOfWeek;
    private String weekend;
    private int weekOfYear;
    private int quarter;
    private String previousDay;
    private String nextDay;

    @OneToMany(mappedBy = "dateTime")
    private List<Weather> weathers;
}
