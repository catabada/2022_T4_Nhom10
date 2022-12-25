package com.example.weatherapi.repository.entity;

import com.example.weatherapi.constant.StatusData;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder

@Entity
@Table(name= "fact")
public class Weather implements Serializable {
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "natural_key", nullable = false)
    private String naturalKey;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "date_time_id", referencedColumnName = "id")
    private DateTime dateTime;

    @Column(name = "time", nullable = false)
    private String time;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "province_id", referencedColumnName = "id")
    private Province province;

    private int temp;
    @Column(name = "cloud_description", nullable = false)
    private String cloudDescription;

    @Column(name = "min_temp", nullable = false)
    private int minTemp;

    @Column(name = "max_temp", nullable = false)
    private int maxTemp;

    @Column(name = "humidity", nullable = false)
    private double humidity;

    @Column(name = "vision", nullable = false)
    private double vision;

    @Column(name = "wind_spd", nullable = false)
    private double windSpd;

    @Column(name = "uv_index", nullable = false)
    private double uvIndex;

    @Column(name = "atmosphere_quality", nullable = false)
    private String atmosphereQuality;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusData status;

    @Column(name = "expired_date", nullable = false)
    private String expiredDate;
}
