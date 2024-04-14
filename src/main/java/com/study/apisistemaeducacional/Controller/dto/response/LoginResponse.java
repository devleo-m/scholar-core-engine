package com.study.apisistemaeducacional.Controller.dto.response;

import com.study.apisistemaeducacional.Entity.PapelEntity;

public record LoginResponse (String name, PapelEntity papel, String token) {}
