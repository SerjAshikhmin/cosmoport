package com.space.controller;

import com.space.model.Ship;
import com.space.model.ShipDto;
import com.space.model.ShipType;
import com.space.service.ShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("rest/ships")
public class ShipController {

    @Autowired
    private ShipService shipService;
    @Autowired
    private ShipValidator shipValidator;

    @GetMapping("")
    public ResponseEntity<List<Ship>> getAllShips(@RequestParam(name = "name", required = false) String name,
                                                  @RequestParam(name = "planet", required = false) String planet,
                                                  @RequestParam(name = "shipType", required = false) ShipType shipType,
                                                  @RequestParam(name = "after", required = false) Long after,
                                                  @RequestParam(name = "before", required = false) Long before,
                                                  @RequestParam(name = "isUsed", required = false) Boolean isUsed,
                                                  @RequestParam(name = "minSpeed", required = false) Double minSpeed,
                                                  @RequestParam(name = "maxSpeed", required = false) Double maxSpeed,
                                                  @RequestParam(name = "minCrewSize", required = false) Integer minCrewSize,
                                                  @RequestParam(name = "maxCrewSize", required = false) Integer maxCrewSize,
                                                  @RequestParam(name = "minRating", required = false) Double minRating,
                                                  @RequestParam(name = "maxRating", required = false) Double maxRating,
                                                  @RequestParam(name = "order", required = false) ShipOrder order,
                                                  @RequestParam(name = "pageNumber", required = false) Integer pageNumber,
                                                  @RequestParam(name = "pageSize", required = false) Integer pageSize) {
        final List<Ship> allShips = shipService.getAllShips();
        Map<Long, Ship> shipsMap = new LinkedHashMap<>();
        for (Ship ship : allShips) {
            shipsMap.put(ship.getId(), ship);
        }
        // filter by name
        if (name != null) {
            for (Ship ship : allShips) {
                if (!ship.getName().contains(name)) {
                    shipsMap.remove(ship.getId());
                }
            }
        }
        // filter by planet
        if (planet != null) {
            for (Ship ship : allShips) {
                if (!ship.getPlanet().contains(planet)) {
                    shipsMap.remove(ship.getId());
                }
            }
        }
        // filter by ShipType
        if (shipType != null) {
            for (Ship ship : allShips) {
                if (!ship.getShipType().equals(shipType)) {
                    shipsMap.remove(ship.getId());
                }
            }
        }
        // filter by after
        if (after != null) {
            for (Ship ship : allShips) {
                if (ship.getProdDate().getTime() < after) {
                    shipsMap.remove(ship.getId());
                }
            }
        }
        // filter by before
        if (after != null) {
            for (Ship ship : allShips) {
                if (ship.getProdDate().getTime() > before) {
                    shipsMap.remove(ship.getId());
                }
            }
        }
        // filter by using
        if (isUsed != null) {
            for (Ship ship : allShips) {
                if (ship.isUsed() != isUsed) {
                    shipsMap.remove(ship.getId());
                }
            }
        }
        // filter by minSpeed
        if (minSpeed != null) {
            for (Ship ship : allShips) {
                if (ship.getSpeed() < minSpeed) {
                    shipsMap.remove(ship.getId());
                }
            }
        }
        // filter by maxSpeed
        if (maxSpeed != null) {
            for (Ship ship : allShips) {
                if (ship.getSpeed() > maxSpeed) {
                    shipsMap.remove(ship.getId());
                }
            }
        }
        // filter by minCrewSize
        if (minCrewSize != null) {
            for (Ship ship : allShips) {
                if (ship.getCrewSize() < minCrewSize) {
                    shipsMap.remove(ship.getId());
                }
            }
        }
        // filter by maxCrewSize
        if (maxCrewSize != null) {
            for (Ship ship : allShips) {
                if (ship.getCrewSize() > maxCrewSize) {
                    shipsMap.remove(ship.getId());
                }
            }
        }
        // filter by minRating
        if (minRating != null) {
            for (Ship ship : allShips) {
                if (ship.getRating() < minRating) {
                    shipsMap.remove(ship.getId());
                }
            }
        }
        // filter by maxRating
        if (maxRating != null) {
            for (Ship ship : allShips) {
                if (ship.getRating() > maxRating) {
                    shipsMap.remove(ship.getId());
                }
            }
        }

        List<Ship> ships = new ArrayList<>();
        // filter by pageNumber and pageSize
        if (pageNumber == null) {
            pageNumber = 0;
        }

        if (pageSize == null) {
            pageSize = 3;
        }

        List<Ship> shipsFromMap = new ArrayList<>(shipsMap.values());

        for (int i = 1; i <= pageSize; i++) {
            if (pageNumber * pageSize + i - 1 < shipsFromMap.size()) {
                ships.add(shipsFromMap.get(pageNumber * pageSize + i - 1));
            } else {
                break;
            }
        }

        if (order != null) {
            switch (order) {
                case ID:
                    ships.sort(Comparator.comparing(Ship::getId));
                    break;
                case SPEED:
                    ships.sort(Comparator.comparing(Ship::getSpeed));
                    break;
                case DATE:
                    ships.sort(Comparator.comparing(Ship::getProdDate));
                    break;
                case RATING:
                    ships.sort(Comparator.comparing(Ship::getRating));
                    break;
            }
        }

        return ResponseEntity.ok(ships);
    }

    @GetMapping("/count")
    public ResponseEntity<Integer> getShipsCount(@RequestParam(name = "name", required = false) String name,
                                                    @RequestParam(name = "planet", required = false) String planet,
                                                    @RequestParam(name = "shipType", required = false) ShipType shipType,
                                                    @RequestParam(name = "after", required = false) Long after,
                                                    @RequestParam(name = "before", required = false) Long before,
                                                    @RequestParam(name = "isUsed", required = false) Boolean isUsed,
                                                    @RequestParam(name = "minSpeed", required = false) Double minSpeed,
                                                    @RequestParam(name = "maxSpeed", required = false) Double maxSpeed,
                                                    @RequestParam(name = "minCrewSize", required = false) Integer minCrewSize,
                                                    @RequestParam(name = "maxCrewSize", required = false) Integer maxCrewSize,
                                                    @RequestParam(name = "minRating", required = false) Double minRating,
                                                    @RequestParam(name = "maxRating", required = false) Double maxRating) {
        final List<Ship> allShips = shipService.getAllShips();
        Map<Long, Ship> shipsMap = new LinkedHashMap<>();
        for (Ship ship : allShips) {
            shipsMap.put(ship.getId(), ship);
        }
        // filter by name
        if (name != null) {
            for (Ship ship : allShips) {
                if (!ship.getName().contains(name)) {
                    shipsMap.remove(ship.getId());
                }
            }
        }
        // filter by planet
        if (planet != null) {
            for (Ship ship : allShips) {
                if (!ship.getPlanet().contains(planet)) {
                    shipsMap.remove(ship.getId());
                }
            }
        }
        // filter by ShipType
        if (shipType != null) {
            for (Ship ship : allShips) {
                if (!ship.getShipType().equals(shipType)) {
                    shipsMap.remove(ship.getId());
                }
            }
        }
        // filter by after
        if (after != null) {
            for (Ship ship : allShips) {
                if (ship.getProdDate().getTime() < after) {
                    shipsMap.remove(ship.getId());
                }
            }
        }
        // filter by before
        if (before != null) {
            for (Ship ship : allShips) {
                if (ship.getProdDate().getTime() > before) {
                    shipsMap.remove(ship.getId());
                }
            }
        }
        // filter by using
        if (isUsed != null) {
            for (Ship ship : allShips) {
                if (ship.isUsed() != isUsed) {
                    shipsMap.remove(ship.getId());
                }
            }
        }
        // filter by minSpeed
        if (minSpeed != null) {
            for (Ship ship : allShips) {
                if (ship.getSpeed() < minSpeed) {
                    shipsMap.remove(ship.getId());
                }
            }
        }
        // filter by maxSpeed
        if (maxSpeed != null) {
            for (Ship ship : allShips) {
                if (ship.getSpeed() > maxSpeed) {
                    shipsMap.remove(ship.getId());
                }
            }
        }
        // filter by minCrewSize
        if (minCrewSize != null) {
            for (Ship ship : allShips) {
                if (ship.getCrewSize() < minCrewSize) {
                    shipsMap.remove(ship.getId());
                }
            }
        }
        // filter by maxCrewSize
        if (maxCrewSize != null) {
            for (Ship ship : allShips) {
                if (ship.getCrewSize() > maxCrewSize) {
                    shipsMap.remove(ship.getId());
                }
            }
        }
        // filter by minRating
        if (minRating != null) {
            for (Ship ship : allShips) {
                if (ship.getRating() < minRating) {
                    shipsMap.remove(ship.getId());
                }
            }
        }
        // filter by maxRating
        if (maxRating != null) {
            for (Ship ship : allShips) {
                if (ship.getRating() > maxRating) {
                    shipsMap.remove(ship.getId());
                }
            }
        }

        List<Ship> ships = new ArrayList<>(shipsMap.values());

        return ResponseEntity.ok(ships.size());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getShipById(@PathVariable(name = "id") long id) {
        if (!shipValidator.validateShipId(id)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        final Ship ship = shipService.getShipById(id);
        return ship != null
                ? ResponseEntity.ok(ship)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> updateShip(@RequestBody ShipDto shipDto, @PathVariable(name = "id") long id) {
        if (!shipValidator.validateShipId(id)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Ship ship = shipService.getShipById(id);
        if (ship == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (!shipValidator.validateShipUpdatingData(shipDto)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        ship = shipService.updateShip(shipDto, ship);
        return ship != null
                ? ResponseEntity.ok(ship)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("")
    public ResponseEntity<?> createShip(@RequestBody ShipDto shipDto) {
        if (!shipValidator.validateShipCreatingData(shipDto)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Ship ship = shipService.createShip(shipDto);
        return ship != null
                ? ResponseEntity.ok(ship)
                : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteShipById(@PathVariable(name = "id") long id) {
        if (!shipValidator.validateShipId(id)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        boolean success = shipService.deleteShipById(id);
        return success
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
