package com.space.controller;

import com.space.model.ShipDto;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ShipValidator {

    public boolean validateShipId(Long id) {
        return (id > 0);
    }

    public boolean validateShipUpdatingData(ShipDto shipDto) {
        if (shipDto.getName() != null && (shipDto.getName().isEmpty() || shipDto.getName().length() > 50)) {
            return false;
        }
        if (shipDto.getPlanet() != null && (shipDto.getPlanet().isEmpty() || shipDto.getPlanet().length() > 50)) {
            return false;
        }
        if (!validateProdYearRange(shipDto.getProdDate())) {
            return false;
        }
        if (!validateSpeedRange(shipDto.getSpeed())) {
            return false;
        }
        if (!validateCrewSizeRange(shipDto.getCrewSize())) {
            return false;
        }
        return true;
    }

    public boolean validateShipCreatingData(ShipDto shipDto) {
        if (shipDto.getName() == null || shipDto.getPlanet() == null || shipDto.getShipType() == null
                || shipDto.getProdDate() == null || shipDto.getCrewSize() == null || shipDto.getSpeed() == null) {
            return false;
        }
        if (!validateShipUpdatingData(shipDto)) {
            return false;
        }
        if (shipDto.getProdDate().getTime() < 0) {
            return false;
        }
        return true;
    }

    private boolean validateSpeedRange(Double speed) {
        if (speed != null && (speed < 0.01 || speed > 0.99)) {
            return false;
        }
        return true;
    }

    private boolean validateCrewSizeRange(Integer crewSize) {
        if (crewSize != null && (crewSize < 1 || crewSize > 9999)) {
            return false;
        }
        return true;
    }

    private boolean validateProdYearRange(Date prodDate) {
        if (prodDate != null && (prodDate.getYear() < 2800 - 1900 || prodDate.getYear() > 3019 - 1900)) {
            return false;
        }
        return true;
    }
}
