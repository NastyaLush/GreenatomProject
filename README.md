# Тестовое задание Java (ТЗ + описание решения)

Требуется создать простейший движок форума/доски объявлений (только backend).

## Суть задачи:

Есть топики (темы), в каждом топике может быть одно или более сообщений.
Движок должен обеспечивать хранение в БД (**IMDB**) и **CRUD** операции с топиками (темами) и сообщениями в топиках.
Топик должен содержать заголовок (название темы). Топик не может быть пустым, т.е. должен содержать как минимум одно сообщение.
Сообщение должно содержать имя (ник) автора, текст сообщения, дату создания.
Сообщение обязательно должно относиться к одному из топиков.

Необходимо реализовать клиентский **REST-API** позволяющий пользователю:
-   получать список топиков
-   получать сообщения в указанном топике
-   создать топик (с первым сообщением в нем)
-   создать сообщение в указанном топике
-   отредактировать свое сообщение
-   удалить свое сообщение

Язык – Java (Spring или Spring Boot - по желанию)
Автоматизация сборки – Maven (Gradle)
Хранилище – in-memory DB (скрипт по наполнению тестовыми данными приветствуется)
------------------------
В качестве фреймворка для решения данной задачи был выбран `Spring Boot`, система сборки `Maven`
Для настройки генерации тестовых данных используйте конфигурации в application.properties
- app.generateTestData - `true` если данные генерировать, `false` иначе (default false)
- app.countOfTopics - количество топиков, которые вы хотите сгенерировать (default 10)
- app.countOfMessagesInTopic - количество сообщений в каждом топике, которое будет сгенерировано (default 10)

Посмотреть доступные конечные точки можно на http://localhost:8080/swagger-ui/index.html#/. Точки разделены по типу запроса (topic, message), для удобства добавлены теги Admin API, Client API, Common API

За основу было взято предложенное API, но измененное в соответствии с задачами которые необходимо было выполнить. Обновленные yaml файл можно найти в папке `resources`.
Код писался как API First, поэтому все интерфейсы контроллером и модели были сгенерированы автоматически через плагин в maven
```xml
            <plugin>
                <groupId>org.openapitools</groupId>
                <artifactId>openapi-generator-maven-plugin</artifactId>
                <version>${openapi-generator-version}</version>
                <executions>
                    <execution>
                        <id>generate-api</id>
                        <goals>
                            <goal>generate</goal>
                        </goals>
                        <configuration>
                            <inputSpec>${project.basedir}/src/main/resources/api_example.yaml</inputSpec>
                            <generatorName>spring</generatorName>
                            <apiPackage>api</apiPackage>
                            <modelPackage>model</modelPackage>
                            <packageName>generated</packageName>
                            <configOptions>
                                <useSpringBoot3>true</useSpringBoot3>
                                <interfaceOnly>true</interfaceOnly>
                                <serializableModel>true</serializableModel>
                                <useBeanValidation>true</useBeanValidation>
                            </configOptions>
                        </configuration>
                    </execution>
                </executions>
            </plugin>
```

## Задания на дополнительный балл
- Реализовать пагинацию для топиков и сообщений 

Реализована с помощью skip и limit на уровне коллекций для запросов типа `get`. Запросы, включающие в себя возврат сообщений или топиков либо не обладают пагинацией, либо количество возвращаемых данных ограничено, возможность настроить это со стороны API отсутствует.
- Реализовать аутентификацию пользователей (т.е. для доступа к сервису необходимо сперва залогиниться)

Реализована регистрация, авторизация и аутентификация, тз было расширено для реализации функциональности в следуещем пункте. Используется аутентификаци на основе `JWT` токенов. Существует два типа пользователей: `user, admin` с правами в соответствии с ТЗ.
- Реализовать `REST-API` администратора. Администратор может редактировать и удалять любые сообщения и топики.
Реализованы дополнительные endpoints для осуществления данной функциональности, добавлены права.
