# Spring WebSockets and REST Api

Application uses an in-memory broker to 

## How to run

        gradlew.bat bootRun
        
        
  ##### Access the UI at: http://localhost:8080
        
  ##### REST API endpoint http://localhost:8080/api
        
        
### Get user channels [GET /api/channels]

+ Response (application/json)

 A JSON array containing the list of user's channels:
        
          [
            {
              "id": 1,
              "name": "PROTV",
              "description": "Best news"
            },
            {
              "id": 2,
              "name": "PRIMA",
              "description": "Focus news"
            }
          ]
        
### Add new channel [POST /api/channels]
                   
+ Request (application/json)
    
          {
            "name": "MTV",
            "description": "Music news"
          }
                           
+ Response (application/json)
    
          {
            "id": 3,
            "name": "MTV",
            "description": "Music news"
          }
                
### Update channel by id [PUT /api/channels/{id}]
                   
+ Request (application/json)
    
          {
            "name": "MTV",
            "description": "Music news"
          }
                           
+ Response (application/json)
    
          {
            "id": {id},
            "name": "MTV",
            "description": "Music news"
          }      
                    
### Update channel by name [PUT /api/channels/{name}]
                   
+ Request (application/json)
    
          {
            "name": "MTV",
            "description": "Music news"
          }
                           
+ Response (application/json)
    
          {
            "id": {id},
            "name": "MTV",
            "description": "Music news"
          }
                        
### Delete channel by name [DELETE /api/channels/{name}]
Deletes a channel identified by channel Name
                                           
+ Request (application/json) DELETE /api/channels/MTV
            
