package com.khantech.ts.mapper;

public interface BaseMapper<REQ, RES, E> {

    RES toResponse(E e);
    E toEntity(REQ req);
}
