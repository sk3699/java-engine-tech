## Steps to Run:
1. As mentioned in the README.md run:
```
mvn clean install -DskipTests
```
2. First thing is to start up the Server by using below:
```
java -jar Server\target\Server-1.0.1-jar-with-dependencies.jar
```
3. After the Server is started run the Client which has the ClientWebSocket configured. So to use the WebSockets api each time run below command:
```
java -jar Client\target\Client-1.0.1-jar-with-dependencies.jar
```

## Implementation
### Server:
1. Used Hibernate as ORM and configured in-memory H2 DB. For the data insertion and all the DB related stuff is configured in persistence.xml.
2. Defined Entities, Service, DAO, WebSocket, Wrapper classes.
3. All the business logic is available in BasicWeightService where it consists of getRandomValue() and getSpin() methods to handle /table and /spin requests respectively.
4. Also, added some tests in BasicWeightServiceTest for hitting the end points for 50 times and check the results.
5. ServerWebSocket class has the Websocket configured and will be hitting the above methods based on the message received.


### Client:
1. A main method is provided to initiate the WebSocket Client request.
2. ClientWebSocket class has the logic which hits the ServerWebSocket for 50 times and the last response for /table and /spin are printed with RTP values.

### Potential areas could be improved if more time is available:
1. The RTP values handling can be made more efficient by storing the value in DB.
2. The test case scenarios could have been widened and upgraded.
3. Automation scripts can be implemented to start up the client and server.
4. WebSocket ErrorHandling scenarios can be implemented.