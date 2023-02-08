## Cola MQ
-Instalr docker con MQ para pruebas
1. 	Instalar docker

2. 	Descargamos la imagen  
docker pull ibmcom/mq

3. 	Validamos que la imagen quedó
docker images

4. 	Levantamos la imagen, desde la consola de docker (agregando de forma manual las variables env) o con el siguiente comando
docker run --env LICENSE=accept --env MQ_QMGR_NAME=QM1 --publish 1414:1414 --publish 9443:9443 --detach ibmcom/mq

5.	Abrimos la consola administrativa
https://localhost:9443/
User: admin
Password: passw0rd

-Publicamos cola desde microservicio, primero usamos el scafold para crear el adaptador
gradle generateDrivenAdapter --type=mq

–Se configura propiedades de cola en el application.yml

```html
commons:
  jms:
    output-concurrency: 10
    output-queue: "DEV.QUEUE.2" //Cola de salida
    input-queue: "DEV.QUEUE.1" //Cola de entrada
    producer-ttl: 60000 //Tiempo de vida de la cola
    reactive: true
ibm:
  mq:
    channel: "DEV.APP.SVRCONN"
    user: "app"
    conn-name: "localhost(1414)" //Donde esta expuesta
    listener_timeout: 6000
    sender_timeout: 6000
    queue-manager: QM1 //Gestor de colas

```
–Se implementa interface en el adaptador creado y desde el caso de uso se invoca la interface
```html
public interface ModelHolaRepository {

   Mono<String> send(String message);
}
```
-Caso de uso
```html
private final ModelHolaRepository modelGateway;
public Mono<String> execute(String request) {
    return modelGateway.send("soy un  mq");
}

```
-Se implementa la interface en la clase sender generada por el scafold
```html
@RequiredArgsConstructor
@Slf4j
public class SampleMQMessageSender  implements ModelHolaRepository {
   private final Long timeout;
   private final Destination inputDestination;
   private final MQMessageSender sender;
   private final MQMessageSelectorListener listener;
   private final Destination destinationQueue;
   @Override
   public Mono<String> send(String message) {
       return sender.send(destinationQueue,context ->
               {
                   Message textMessage;
                   textMessage = context.createTextMessage(message);
                   return textMessage;
               })
               .name("mq_send_message")
               .tag("operation", "my-operation")
               .metrics();
   }

```
-Se crea clase de configuración para indicar las colas de entrada y salida
```html
@Configuration
@EnableMQMessageSender
@EnableMQSelectorMessageListener
public class MQSenderConfig {

   @Bean
   public ModelHolaRepository modelGateway(@Value("${ibm.mq.listener_timeout}") Long listenerTimeout,
                                           MQMessageSender sender,
                                           @Value("${commons.jms.output-queue}") String outputQueue,
                                           MQQueueCustomizer customizer,
                                           MQMessageSelectorListener listener) throws JMSException {

       Destination destinationQueue = createDestinationQueue(outputQueue, customizer);
       return new SampleMQMessageSender(listenerTimeout,destinationQueue,sender,listener,destinationQueue);
   }

   private Destination createDestinationQueue(String outputQueue, MQQueueCustomizer customizer) throws JMSException {
       Queue queue = new MQQueue(outputQueue);
       customizer.customize(queue);
       return queue;
   }
}

```

--Se crea clase general de configuración
```html
@Slf4j
@Configuration
public class MQConfig {
   @Bean
   @Primary
   public MQProducerCustomizer mqProducerCustomizer() {
       return producer -> producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
   }
}

```

-En el mainAplication debemos indicar que paquetes se deben compilar, incluyendo los del scafolding
```html
@SpringBootApplication(scanBasePackages = {"co.com.nequi", "co.com.bancolombia"})

```

## Notas
compilar: gradle clean build
desplegar: gradle clean booRun

Para el error cannot resolve method 'run(class string )' SpringApplication.run
Cambiar el SDK, click derecho sobre el proyecto y escoger "Open Module Settings" -> Project y escoger SDK deseado (jdk 11)
