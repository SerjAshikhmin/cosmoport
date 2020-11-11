package com.space.service;

import com.space.model.Ship;
import com.space.model.ShipDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ShipService {

    List<Ship> getAllShips();
    Ship getShipById(long id);
    boolean deleteShipById(long id);
    Ship updateShip(ShipDto shipDto, Ship ship);
    Ship createShip(ShipDto shipDto);
}
