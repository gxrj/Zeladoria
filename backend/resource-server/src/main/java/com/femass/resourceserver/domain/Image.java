package com.femass.resourceserver.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter

@AllArgsConstructor
@NoArgsConstructor
public class Image implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private String identifier;
    private byte[] content;
}