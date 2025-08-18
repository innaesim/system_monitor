package com.monitor.utility.service;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HWDiskStore;
import oshi.hardware.NetworkIF;
import oshi.hardware.PowerSource;
import oshi.software.os.OperatingSystem;

@Slf4j
@Data
public class Service {

    public Service(){}

    public void showSystemResources(){
        SystemInfo si = new SystemInfo();
        OperatingSystem os = si.getOperatingSystem();
        CentralProcessor cpu = si.getHardware().getProcessor();
        GlobalMemory memory = si.getHardware().getMemory();

        System.out.println("=== System Resource Status ===");
        System.out.println("OS: " + os);

        long[] oldTicks = cpu.getSystemCpuLoadTicks();
        double cpuLoad = cpu.getSystemCpuLoadBetweenTicks(oldTicks) * 100;
        System.out.printf("CPU Load: %.2f %n - ", cpuLoad); 

        long totalMem = memory.getTotal() / (1024 * 1024);
        long availMem = memory.getAvailable() / (1024 * 1024);
        System.out.printf("Memory: %d MB total, %d MB available%n", totalMem, availMem);

        for (HWDiskStore disk : si.getHardware().getDiskStores()) {
            System.out.printf("Disk %s: %.2f GB read, %.2f GB written %n",
                    disk.getName(),
                    disk.getReadBytes() / 1e9,
                    disk.getWriteBytes() / 1e9);
        }

        for (NetworkIF net : si.getHardware().getNetworkIFs()) {
            net.updateAttributes();
            System.out.printf("Network %s (%s): rx=%.2f MB, tx=%.2f MB %n",
                    net.getName(), net.getDisplayName(),
                    net.getBytesRecv() / 1e6,
                    net.getBytesSent() / 1e6);
        }

        for (PowerSource ps : si.getHardware().getPowerSources()) {
            System.out.printf("Battery %s: %.1f%% remaining, %s %n",
                ps.getName(),
                ps.getRemainingCapacityPercent() * 100,
                ps.isCharging() ? "charging" : "discharging");
        }

    }

}