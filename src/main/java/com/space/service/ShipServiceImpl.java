package com.space.service;

import com.space.controller.ShipValidator;
import com.space.model.Ship;
import com.space.model.ShipDto;
import com.space.repository.ShipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ShipServiceImpl implements ShipService {

    @Autowired
    private ShipRepository shipRepository;
    @Autowired
    private ShipValidator shipValidator;

    @Override
    @Transactional
    public List<Ship> getAllShips() {
        List<Ship> ships = (List<Ship>) shipRepository.findAll();
        return ships;
    }

    @Override
    public Ship getShipById(long id) {
        Ship ship;
        try {
            ship = shipRepository.findById(id).get();
        } catch (Exception e) {
            return null;
        }
        return ship;
    }

    @Override
    public boolean deleteShipById(long id) {
        try {
            shipRepository.deleteById(id);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public Ship updateShip(ShipDto shipDto, Ship ship) {
        boolean isChanged = false;
        if (shipDto.getName() != null) {
            ship.setName(shipDto.getName());
            isChanged = true;
        }
        if (shipDto.getPlanet() != null) {
            ship.setPlanet(shipDto.getPlanet());
            isChanged = true;
        }
        if (shipDto.getShipType() != null) {
            ship.setShipType(shipDto.getShipType());
            isChanged = true;
        }
        if (shipDto.getProdDate() != null) {
            ship.setProdDate(shipDto.getProdDate());
            isChanged = true;
        }
        if (shipDto.isUsed() != null) {
            ship.setUsed(shipDto.isUsed());
            isChanged = true;
        }
        if (shipDto.getSpeed() != null) {
            ship.setSpeed(shipDto.getSpeed());
            isChanged = true;
        }
        if (shipDto.getCrewSize() != null) {
            ship.setCrewSize(shipDto.getCrewSize());
            isChanged = true;
        }
        if (isChanged) {
            ship.setRating(calculateRating(ship));
            return shipRepository.save(ship);
        } else {
            return ship;
        }
    }

    @Override
    public Ship createShip(ShipDto shipDto) {
        Ship ship = new Ship();
        ship.setName(shipDto.getName());
        ship.setPlanet(shipDto.getPlanet());
        ship.setShipType(shipDto.getShipType());
        ship.setProdDate(shipDto.getProdDate());
        if (shipDto.isUsed() != null) {
            ship.setUsed(shipDto.isUsed());
        } else {
            ship.setUsed(false);
        }
        ship.setSpeed(shipDto.getSpeed());
        ship.setCrewSize(shipDto.getCrewSize());
        ship.setRating(calculateRating(ship));
        return shipRepository.save(ship);
    }

    private Double calculateRating(Ship ship) {
        double k = 1;
        if (ship.isUsed()) {
            k = 0.5;
        }
        double rating = (80 * ship.getSpeed() * k) / (3019 - ship.getProdDate().getYear() - 1900 + 1);
        rating = Double.parseDouble(String.format("%(.2f", rating).replace(',', '.'));
        return rating;
    }
}
