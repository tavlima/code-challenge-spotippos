#language:en
@ignore
Feature: Property - Create a property

  Background:
    Given that the provinces database has the records:
      | {"id": 1, "name": "ProvinceA", "x0": 0, "y0": 0, "x1": 420, "y1": 300 }     |
      | {"id": 2, "name": "ProvinceB", "x0": 0, "y0": 0, "x1": 140, "y1": 100 }     |
      | {"id": 3, "name": "ProvinceC", "x0": 280, "y0": 200, "x1": 420, "y1": 300 } |


  Scenario Outline: Create a property - OK
    When a valid CreateProperty request is received with payload '<payload>'
    Then it should return the new property id <new_id>
    And this new property will belong to provinces '<provinces>'
    Examples:
      | payload                                                                                                                                                     | new_id | provinces           |
      | {"x": 10,  "y": 10,  "title": "Property at ProvinceA/ProvinceB", "price": 100000, "description": "Description", "beds": 1, "baths": 1, "squareMeter": 100 } | 4      | ProvinceA,ProvinceB |
      | {"x": 290, "y": 210, "title": "Property at ProvinceA/ProvinceC", "price": 100000, "description": "Description", "beds": 1, "baths": 1, "squareMeter": 100 } | 4      | ProvinceA,ProvinceC |
      | {"x": 150, "y": 110, "title": "Property at ProvinceA",           "price": 100000, "description": "Description", "beds": 1, "baths": 1, "squareMeter": 100 } | 4      | ProvinceA           |
      | {"x": 560, "y": 400, "title": "Property at No Mans Land",        "price": 100000, "description": "Description", "beds": 1, "baths": 1, "squareMeter": 100 } | 4      |                     |


  Scenario Outline: Create a property - BAD REQUEST
    When an invalid CreateProperty request is received with payload '<payload>'
    Then it should return a 400 status error
    Examples:
      | payload                                                                                                                                          |
      # Missing mandatory fields
      | {}                                                                                                                                               |
      | {            "y": 0,     "title": "Title", "price": 100000, "description": "Description", "beds": 1,     "baths": 1,     "squareMeter": 100 }    |
      | {"x": 0,                 "title": "Title", "price": 100000, "description": "Description", "beds": 1,     "baths": 1,     "squareMeter": 100 }    |
      | {"x": 0,     "y": 0,     "title": "Title",                  "description": "Description", "beds": 1,     "baths": 1,     "squareMeter": 100 }    |
      | {"x": 0,     "y": 0,     "title": "Title", "price": 100000, "description": "Description",                "baths": 1,     "squareMeter": 100 }    |
      | {"x": 0,     "y": 0,     "title": "Title", "price": 100000, "description": "Description", "beds": 1,     "baths": 1,                        }    |
      # Invalid values (type)
      | {"x": "abc", "y": 0,     "title": "Title", "price": 100000, "description": "Description", "beds": 1,     "baths": 1,     "squareMeter": 100 }    |
      | {"x": 0,     "y": "abc", "title": "Title", "price": 100000, "description": "Description", "beds": 1,     "baths": 1,     "squareMeter": 100 }    |
      | {"x": 0,     "y": 0,     "title": 123,     "price": 100000, "description": "Description", "beds": 1,     "baths": 1,     "squareMeter": 100 }    |
      | {"x": 0,     "y": 0,     "title": "Title", "price": "abc",  "description": "Description", "beds": 1,     "baths": 1,     "squareMeter": 100 }    |
      | {"x": 0,     "y": 0,     "title": "Title", "price": 100.00, "description": "Description", "beds": 1,     "baths": 1,     "squareMeter": 100 }    |
      | {"x": 0,     "y": 0,     "title": "Title", "price": 100000, "description": 123,           "beds": 1,     "baths": 1,     "squareMeter": 100 }    |
      | {"x": 0,     "y": 0,     "title": "Title", "price": 100000, "description": "Description", "beds": "abc", "baths": 1,     "squareMeter": 100 }    |
      | {"x": 0,     "y": 0,     "title": "Title", "price": 100000, "description": "Description", "beds": 1,     "baths": "abc", "squareMeter": 100 }    |
      | {"x": 0,     "y": 0,     "title": "Title", "price": 100000, "description": "Description", "beds": 1,     "baths": 1,     "squareMeter": "abc" }  |
      # Invalid values (constraints)
      | {"x": -1,    "y": 0,     "title": "Title", "price": 100000, "description": "Description", "beds": 1,     "baths": 1,     "squareMeter": 100 }   |
      | {"x": 0,     "y": -1,    "title": "Title", "price": 100000, "description": "Description", "beds": 1,     "baths": 1,     "squareMeter": 100 }   |
      | {"x": 1401,  "y": 0,     "title": "Title", "price": 100000, "description": "Description", "beds": 1,     "baths": 1,     "squareMeter": 100 } |
      | {"x": 0,     "y": 1001,  "title": "Title", "price": 100000, "description": "Description", "beds": 1,     "baths": 1,     "squareMeter": 100 } |
      | {"x": 0,     "y": 0,     "title": "Title", "price": 100000, "description": "Description", "beds": 0,     "baths": 1,     "squareMeter": 100 }    |
      | {"x": 0,     "y": 0,     "title": "Title", "price": 100000, "description": "Description", "beds": 6,     "baths": 1,     "squareMeter": 100 }    |
      | {"x": 0,     "y": 0,     "title": "Title", "price": 100000, "description": "Description", "beds": 1,     "baths": 0,     "squareMeter": 100 }    |
      | {"x": 0,     "y": 0,     "title": "Title", "price": 100000, "description": "Description", "beds": 1,     "baths": 5,     "squareMeter": 100 }    |
      | {"x": 0,     "y": 0,     "title": "Title", "price": 100000, "description": "Description", "beds": 1,     "baths": 1,     "squareMeter": 19 }     |
      | {"x": 0,     "y": 0,     "title": "Title", "price": 100000, "description": "Description", "beds": 1,     "baths": 1,     "squareMeter": 241 }    |