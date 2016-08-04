#language:en

Feature: Property operations

  Background:
    Given that the properties database has the records:
      | {"id": 1, "x": 10, "y": 10, "title": "Title 1", "price": 100000, "description": "Description 1", "beds": 1, "baths": 1, "squareMeters": 110 } |
      | {"id": 2, "x": 20, "y": 20, "title": "Title 2", "price": 200000, "description": "Description 2", "beds": 2, "baths": 2, "squareMeters": 120 } |
      | {"id": 3, "x": 30, "y": 30, "title": "Title 3", "price": 300000, "description": "Description 3", "beds": 3, "baths": 3, "squareMeters": 130 } |
      | {"id": 4, "x": 40, "y": 40, "title": "Title 4", "price": 400000, "description": "Description 4", "beds": 4, "baths": 4, "squareMeters": 140 } |
      | {"id": 5, "x": 50, "y": 50, "title": "Title 5", "price": 500000, "description": "Description 5", "beds": 1, "baths": 2, "squareMeters": 150 } |

  Scenario Outline: Get a property by id - existing
    When a GET /properties/'<id>' request is received
    Then it should return the property '<property>'
    Examples:
      | id | property                                                                                                                                      |
      | 1  | {"id": 1, "x": 10, "y": 10, "title": "Title 1", "price": 100000, "description": "Description 1", "beds": 1, "baths": 1, "squareMeters": 110 } |
      | 2  | {"id": 2, "x": 20, "y": 20, "title": "Title 2", "price": 200000, "description": "Description 2", "beds": 2, "baths": 2, "squareMeters": 120 } |
      | 3  | {"id": 3, "x": 30, "y": 30, "title": "Title 3", "price": 300000, "description": "Description 3", "beds": 3, "baths": 3, "squareMeters": 130 } |
      | 4  | {"id": 4, "x": 40, "y": 40, "title": "Title 4", "price": 400000, "description": "Description 4", "beds": 4, "baths": 4, "squareMeters": 140 } |

  Scenario Outline: Get a property by id - missing
    When a GET /properties/'<id>' request is received
    Then it should return a 404 status error
    Examples:
      | id |
      | 0  |
      | 6  |
      | 10 |

  Scenario Outline: Find properties in region - valid
    When a GET /properties request with the parameters '<parameters>' is received
    Then it should return the properties with id '<property_ids>'
    Examples:
      | parameters                                     | property_ids |
      | {"ax": 0, "ay": 0, "bx": 20, "by": 20}         | 1,2          |
      | {"ax": 0, "ay": 0, "bx": 40, "by": 40}         | 1,2,3,4      |
      | {"ax": 25, "ay": 25, "bx": 50, "by": 50}       | 3,4,5        |
      | {"ax": 900, "ay": 900, "bx": 1000, "by": 1000} |              |