package com.shop.ShopApplication.service.filialSevice;

import com.shop.ShopApplication.dto.filialDTO.FilialStatusDto;
import com.shop.ShopApplication.entity.Filial;
import com.shop.ShopApplication.entity.WorkingTime;
import com.shop.ShopApplication.repo.FilialRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class FilialStatusService {
    private final FilialRepository filialRepository;

    public List<FilialStatusDto> getFilialStatusForAllFiliales() {
        List<FilialStatusDto> filialStatusList = new ArrayList<>();
        List<Filial> filials = filialRepository.findAll();

        for (Filial filial : filials) {
            if (filial.getWorkingTime() != null) {
                DayOfWeek currentDay = LocalDateTime.now().getDayOfWeek();
                String todaysWorkingHours = getWorkingHoursForDay(currentDay, filial.getWorkingTime());
                FilialStatusDto filialStatus = calculateFilialStatus(filial, todaysWorkingHours);
                filialStatusList.add(filialStatus);
            }
        }

        return filialStatusList;
    }

    private String getWorkingHoursForDay(DayOfWeek day, WorkingTime workingTime) {
        switch (day) {
            case MONDAY: return workingTime.getMonday();
            case TUESDAY: return workingTime.getTuesday();
            case WEDNESDAY: return workingTime.getWednesday();
            case THURSDAY: return workingTime.getThursday();
            case FRIDAY: return workingTime.getFriday();
            case SATURDAY: return workingTime.getSaturday();
            case SUNDAY: return workingTime.getSunday();
            default: return null; // Handle the case when day is not recognized
        }
    }


    private FilialStatusDto calculateFilialStatus(Filial filial, String todaysWorkingHours) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime openingTime = LocalTime.parse(todaysWorkingHours.split("-")[0], formatter);
        LocalTime closingTime = LocalTime.parse(todaysWorkingHours.split("-")[1], formatter);
        LocalTime now = LocalTime.now();

        if (now.isBefore(openingTime)) {
            // The filial is not open yet
            long minutesLeft = now.until(openingTime, ChronoUnit.MINUTES);
            return FilialStatusDto.builder()
                    .filialId(filial.getFilial_id())
                    .isOpen(false)
                    .timeLeft(minutesLeft <= 40 ? String.format("Opens in %d minutes", minutesLeft) : null)
                    .filialInfo(filial)
                    .build();
        } else if (now.isBefore(closingTime)) {
            // The filial is currently open
            long minutesLeft = now.until(closingTime, ChronoUnit.MINUTES);
            return FilialStatusDto.builder()
                    .filialId(filial.getFilial_id())
                    .isOpen(true)
                    .timeLeft(null)
                    .filialInfo(filial)
                    .build();
        } else {
            // The filial is closing or closed
            long minutesPassed = closingTime.until(now, ChronoUnit.MINUTES);
            return FilialStatusDto.builder()
                    .filialId(filial.getFilial_id())
                    .isOpen(false)
                    .timeLeft(minutesPassed <= 40 ? String.format("Closed %d minutes ago", minutesPassed) : null)
                    .filialInfo(filial)
                    .build();
        }
    }
}
