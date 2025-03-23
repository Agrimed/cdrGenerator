## Запуск приложения
```courseignore
java -jar cdrGenerator-1.0.0.jar
```
### Задание 1. Генерация тестовых данных
Генерация 50 абонентов и от 10000 до 100000 CDR записей за текущий год. Номера абонентов начинаются с номера **79990000001**
```courseignore
POST http://<host>:8080/generate
```
### Задание 2. 
Получить UDR запись для абонента msisdn. msisdn должен быть в интервале от **79990000001**
```courseignore
GET http://<host>:8080/udr/{msisdn}
```
Получить UDR запись для абонента msisdn за определенный месяц. Месяц должен быть в интервале **1-12**
```courseignore
GET http://<host>:8080/udr/{msisdn}/{month}
```
### Задание 3.
Генерация CDR файла для абонента msisdn за заданный период. 
```courseignore
GET http://<host>:8080/file
```
Параметры запроса передаются в теле запроса в виде json следующего формата:
```courseignore
{
    "msisdn": "{msisdn}",
    "start": "{YYYY-MM-DD}",
    "end": "{YYYY-MM-DD}"
}
```
