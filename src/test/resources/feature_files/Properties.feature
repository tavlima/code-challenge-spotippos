#language:en

Feature: Property retrieval

  Background:
    Given that the properties database has the records:
      | {"id": 1, "x": 1, "y": 1, "title": "Title 1", "price": 100000, "description": "Description 1", "beds": 1, "baths": 1, "squareMeters": 110 } |
      | {"id": 2, "x": 2, "y": 2, "title": "Title 2", "price": 200000, "description": "Description 2", "beds": 2, "baths": 2, "squareMeters": 120 } |
      | {"id": 3, "x": 3, "y": 3, "title": "Title 3", "price": 300000, "description": "Description 3", "beds": 3, "baths": 3, "squareMeters": 130 } |
      | {"id": 4, "x": 4, "y": 4, "title": "Title 4", "price": 400000, "description": "Description 4", "beds": 4, "baths": 4, "squareMeters": 140 } |
      | {"id": 5, "x": 5, "y": 5, "title": "Title 5", "price": 500000, "description": "Description 5", "beds": 1, "baths": 2, "squareMeters": 150 } |

  Scenario Outline: Get a property by id (existing)
    When a GET /property/'<id>' request is received
    Then it should return the property '<property>'
    Examples:
      | id | property                                                                                                                                    |
      | 1  | {"id": 1, "x": 1, "y": 1, "title": "Title 1", "price": 100000, "description": "Description 1", "beds": 1, "baths": 1, "squareMeters": 110 } |
      | 2  | {"id": 2, "x": 2, "y": 2, "title": "Title 2", "price": 200000, "description": "Description 2", "beds": 2, "baths": 2, "squareMeters": 120 } |
      | 3  | {"id": 3, "x": 3, "y": 3, "title": "Title 3", "price": 300000, "description": "Description 3", "beds": 3, "baths": 3, "squareMeters": 130 } |
      | 4  | {"id": 4, "x": 4, "y": 4, "title": "Title 4", "price": 400000, "description": "Description 4", "beds": 4, "baths": 4, "squareMeters": 140 } |

  Scenario Outline: Get a property by id (missing)
    When a GET /property/'<id>' request is received
    Then it should return a 404 status error
    Examples:
      | id |
      | 6  |