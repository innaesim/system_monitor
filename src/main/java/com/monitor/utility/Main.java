package com.monitor.utility;

import com.monitor.utility.service.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {
    public static void main(String[] args){
        try {
            Service service = new Service();
            service.showSystemResources();
        } catch (Exception e) {
            log.error("Error Retrieving System Resources {}", e.getMessage());
        }
    }
}