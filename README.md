для запуска требуется gradle, tomcat(?), docker,
    база данных, подключение можно настроить в src/main/resources/database.properties

запуск:
    gradle clean bootRun

kafka:
    приложение работает со стандартными настройками kafk-и
    можно создать consumer для топика newUsers
    kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic newUsers --from-beginning
    он будет получать xml с новыми пользователями

    можно создать roducer для топика discount
    kafka-console-producer.bat --broker-list localhost:9092 --topic discount
    и посылать строки вида
    <user><login>login2</login><discount>20</discount></user>
    (тем самым к пользователю с логином login2 будет добавлена скидка 20%)