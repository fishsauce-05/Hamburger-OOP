package com.parking.config;

import com.parking.model.*;
import com.parking.repository.InMemoryRepositories;

import java.util.UUID;

public class DataLoader {

    public static void loadSampleParkingLot(InMemoryRepositories.ParkingLotRepository lotRepo,
                                            InMemoryRepositories.ParkingSpotRepository spotRepo) {
        // Giữ lại behavior cũ để backward compatibility
        ParkingLot lot = new ParkingLot(UUID.randomUUID().toString(), "Main Lot");
        Level level1 = new Level(UUID.randomUUID().toString(), "Level 1", 1);
        lot.addLevel(level1);
        lotRepo.save(lot);

        // create some spots
        for (int i = 1; i <= 10; i++) {
            ParkingSpot s = new ParkingSpot(UUID.randomUUID().toString(), level1.getId(),
                    SpotType.REGULAR, "L1-" + i);
            level1.addSpot(s);
            spotRepo.save(s);
        }
        // handicapped spots
        for (int i = 1; i <= 2; i++) {
            ParkingSpot s = new ParkingSpot(UUID.randomUUID().toString(), level1.getId(),
                    SpotType.HANDICAPPED, "L1-HC-" + i);
            level1.addSpot(s);
            spotRepo.save(s);
        }
    }

    public static void loadMultiLevelSample(InMemoryRepositories.ParkingLotRepository lotRepo,
                                            InMemoryRepositories.ParkingSpotRepository spotRepo,
                                            String lotName,
                                            int levelsCount,
                                            int regularPerLevel,
                                            int handicappedPerLevel,
                                            int monthlyPerLevel,
                                            int electricPerLevel) {
        ParkingLot lot = new ParkingLot(UUID.randomUUID().toString(), lotName);

        for (int lvl = 1; lvl <= levelsCount; lvl++) {
            Level level = new Level(UUID.randomUUID().toString(), "Level " + lvl, lvl);
            lot.addLevel(level);

            // regular spots
            for (int i = 1; i <= regularPerLevel; i++) {
                String label = String.format("L%d-R-%02d", lvl, i);
                ParkingSpot s = new ParkingSpot(UUID.randomUUID().toString(), level.getId(),
                        SpotType.REGULAR, label);
                level.addSpot(s);
                spotRepo.save(s);
            }
            // handicapped
            for (int i = 1; i <= handicappedPerLevel; i++) {
                String label = String.format("L%d-HC-%02d", lvl, i);
                ParkingSpot s = new ParkingSpot(UUID.randomUUID().toString(), level.getId(),
                        SpotType.HANDICAPPED, label);
                level.addSpot(s);
                spotRepo.save(s);
            }
            // monthly / reserved
            for (int i = 1; i <= monthlyPerLevel; i++) {
                String label = String.format("L%d-M-%02d", lvl, i);
                ParkingSpot s = new ParkingSpot(UUID.randomUUID().toString(), level.getId(),
                        SpotType.MONTHLY, label);
                level.addSpot(s);
                spotRepo.save(s);
            }
            // electric
            for (int i = 1; i <= electricPerLevel; i++) {
                String label = String.format("L%d-E-%02d", lvl, i);
                ParkingSpot s = new ParkingSpot(UUID.randomUUID().toString(), level.getId(),
                        SpotType.ELECTRIC, label);
                level.addSpot(s);
                spotRepo.save(s);
            }
        }

        lotRepo.save(lot);
    }
}