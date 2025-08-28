package com.example.git.model;

import java.time.Instant;

public record Commit(String message,String author,Instant timestamp){}
