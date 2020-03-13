package com.example.schedulingtasks.domain.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "note", schema = "test")
@Getter
@Setter
public class Note {

    @Id
    @GeneratedValue
    private Long id;

    private String text;

}