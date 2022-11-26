### Run axon server firstly

You need the Axon server to run this application.
You can download the Axon server from below url:
https://axoniq.io/download  


```

cd axon-server
java -jar axonserver-4.3.5.jar
```

axon server UI  
http://localhost:8024  

or You can run the axon server with docker:

```
docker run -d --name my-axon-server -p 8024:8024 -p 8124:8124 axoniq/axonserver
```

---
--

### 실행시 주의점.  
1. 템플릿 엔진으로 만들어진 파일로는 정상적으로 실행이 안됩니다.  
Aggregate 파일에서 Create 하는 Command 를 꼭 생성자로 커맨드를 발행하여 주세요.  

!! 업데이트,DELETE 쪽은 생성자로 하시면 에러가 발생함  

````java
@CommandHandler
public OrderAggregate(OrderPlacedCommand command){
    OrderPlacedEvent orderPlaced = new OrderPlacedEvent();
    orderPlaced.setId(command.getId());
    AggregateLifecycle.apply(orderPlaced);
}
````
