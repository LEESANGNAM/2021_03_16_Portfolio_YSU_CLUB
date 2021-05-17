package kr.ac.yeonsung.demo.controller;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookForm {

    private Long id;

    private String name;
    private int totalNumber;
    private String clubJang;
}
