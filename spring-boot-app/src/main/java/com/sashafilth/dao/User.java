package com.sashafilth.dao;


import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User implements Serializable {

    private static final long serialVersionUID = -7799415185617798522L;

    private String name;
    private String lastName;
    private Integer age;
    private Integer uniqueNumber;
    private List<String> about;

}
