#language:en
Feature: Property - Create a property

  Scenario Outline: Create a property - OK
    Given that the properties database has the records:
      | {"id": 1, "x": 10, "y": 10, "title": "Title 1", "price": 100000, "description": "Description 1", "beds": 1, "baths": 1, "squareMeters": 110 } |
    And that the provinces database has the records:
      | {"id": 1, "name": "ProvinceA", "x0": 0, "y0": 0, "x1": 420, "y1": 300 }     |
      | {"id": 2, "name": "ProvinceB", "x0": 0, "y0": 0, "x1": 140, "y1": 100 }     |
      | {"id": 3, "name": "ProvinceC", "x0": 280, "y0": 200, "x1": 420, "y1": 300 } |
    When a valid CreateProperty request is received with payload '<payload>'
    Then it should return the new property id <new_id>
    And this new property will belong to provinces '<provinces>'
    Examples:
      | payload                                                                                                                                                      | new_id | provinces           |
      | {"x": 10,  "y": 10,  "title": "Property at ProvinceA/ProvinceB", "price": 100000, "description": "Description", "beds": 1, "baths": 1, "squareMeters": 100 } | 2      | ProvinceA,ProvinceB |
      | {"x": 290, "y": 210, "title": "Property at ProvinceA/ProvinceC", "price": 100000, "description": "Description", "beds": 1, "baths": 1, "squareMeters": 100 } | 2      | ProvinceA,ProvinceC |
      | {"x": 150, "y": 110, "title": "Property at ProvinceA",           "price": 100000, "description": "Description", "beds": 1, "baths": 1, "squareMeters": 100 } | 2      | ProvinceA           |
      | {"x": 560, "y": 400, "title": "Property at No Mans Land",        "price": 100000, "description": "Description", "beds": 1, "baths": 1, "squareMeters": 100 } | 2      |                     |


  Scenario Outline: Create a property - BAD REQUEST
    When an invalid CreateProperty request is received with payload '<payload>'
    Then it should return a 400 status error
    Examples:
      | payload                                                                                                                                          |
      # Missing mandatory fields
      | {}                                                                                                                                               |
      | {            "y": 0,     "title": "Title", "price": 100000, "description": "Description", "beds": 1,     "baths": 1,     "squareMeters": 100 }   |
      | {"x": 0,                 "title": "Title", "price": 100000, "description": "Description", "beds": 1,     "baths": 1,     "squareMeters": 100 }   |
      | {"x": 0,     "y": 0,     "title": "Title",                  "description": "Description", "beds": 1,     "baths": 1,     "squareMeters": 100 }   |
      | {"x": 0,     "y": 0,     "title": "Title", "price": 100000, "description": "Description",                "baths": 1,     "squareMeters": 100 }   |
      | {"x": 0,     "y": 0,     "title": "Title", "price": 100000, "description": "Description", "beds": 1,     "baths": 1,                        }    |
      # Invalid values (type)
      | {"x": "abc", "y": 0,     "title": "Title", "price": 100000, "description": "Description", "beds": 1,     "baths": 1,     "squareMeters": 100 }   |
      | {"x": 0,     "y": "abc", "title": "Title", "price": 100000, "description": "Description", "beds": 1,     "baths": 1,     "squareMeters": 100 }   |
      | {"x": 0,     "y": 0,     "title": "Title", "price": "abc",  "description": "Description", "beds": 1,     "baths": 1,     "squareMeters": 100 }   |
      | {"x": 0,     "y": 0,     "title": "Title", "price": 100000, "description": "Description", "beds": "abc", "baths": 1,     "squareMeters": 100 }   |
      | {"x": 0,     "y": 0,     "title": "Title", "price": 100000, "description": "Description", "beds": 1,     "baths": "abc", "squareMeters": 100 }   |
      | {"x": 0,     "y": 0,     "title": "Title", "price": 100000, "description": "Description", "beds": 1,     "baths": 1,     "squareMeters": "abc" } |
      # Invalid values (constraints)
      | {"x": 0,     "y": 0,     "title": "",      "price": 100000, "description": "Description", "beds": 1,     "baths": 1,     "squareMeters": 100 }   |
      | {"x": 0,     "y": 0,     "title": "Title", "price": 100000, "description": "",            "beds": 1,     "baths": 1,     "squareMeters": 100 }   |
      | {"x": -1,    "y": 0,     "title": "Title", "price": 100000, "description": "Description", "beds": 1,     "baths": 1,     "squareMeters": 100 }   |
      | {"x": 0,     "y": -1,    "title": "Title", "price": 100000, "description": "Description", "beds": 1,     "baths": 1,     "squareMeters": 100 }   |
      | {"x": 1401,  "y": 0,     "title": "Title", "price": 100000, "description": "Description", "beds": 1,     "baths": 1,     "squareMeters": 100 }   |
      | {"x": 0,     "y": 1001,  "title": "Title", "price": 100000, "description": "Description", "beds": 1,     "baths": 1,     "squareMeters": 100 }   |
      | {"x": 0,     "y": 0,     "title": "Title", "price": 100000, "description": "Description", "beds": 0,     "baths": 1,     "squareMeters": 100 }   |
      | {"x": 0,     "y": 0,     "title": "Title", "price": 100000, "description": "Description", "beds": 6,     "baths": 1,     "squareMeters": 100 }   |
      | {"x": 0,     "y": 0,     "title": "Title", "price": 100000, "description": "Description", "beds": 1,     "baths": 0,     "squareMeters": 100 }   |
      | {"x": 0,     "y": 0,     "title": "Title", "price": 100000, "description": "Description", "beds": 1,     "baths": 5,     "squareMeters": 100 }   |
      | {"x": 0,     "y": 0,     "title": "Title", "price": 100000, "description": "Description", "beds": 1,     "baths": 1,     "squareMeters": 19 }    |
      | {"x": 0,     "y": 0,     "title": "Title", "price": 100000, "description": "Description", "beds": 1,     "baths": 1,     "squareMeters": 241 }   |
      # Should we reject those and be type-strict? More investigation is needed to reject it, anyway
#      | {"x": 0,     "y": 0,     "title": "Title", "price": 100.10, "description": "Description", "beds": 1,     "baths": 1,     "squareMeters": 100 }    |
#      | {"x": 0,     "y": 0,     "title": 123,     "price": 100000, "description": "Description", "beds": 1,     "baths": 1,     "squareMeters": 100 }    |
#      | {"x": 0,     "y": 0,     "title": "Title", "price": 100000, "description": 123,           "beds": 1,     "baths": 1,     "squareMeters": 100 }    |
