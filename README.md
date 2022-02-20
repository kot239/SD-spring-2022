# SD-spring-2022

Сделали:

* Обрядина Александра
* Тарабонда Герман

## Запуск программы

Чтобы собрать программу нужно написать команду:

`./gradlew assemble`

Для дальнейшей работы с программой нужно
написать следующий код в консоль:

`java -jar build/libs/SD-spring-2022-1.0-SNAPSHOT.jar`

Чтобы запустить тесты, в консоле нужно написать следующую строчку:

`./gradlew test`

![Схема архитектуры](https://github.com/kot239/SD-spring-2022/blob/task-2-3/CLIArchitecture.png)

## Общий принцип работы

CLI - Command Line Interface, интерфейс, который позволяет выполнять комманды,
введенные в коммандную строку.

В течение работы нашего CLI мы всегда ожидаем ввода комманд. После ввода команды нужно нажать Enter.
После этого выполниться содержимое введенной строки. Введенная строка идет на обработку в класс Executor, где 
она собственно и будет обрабатываться. В Executor сначала строка попадет на обработку в класс Parser. 
Этот класс выдаст список комманд с аргументами (`List<RawArgs>` отвечает за одну команду). После этого произойдет
переменных, если это потребуется. И после подстановки мы выполняем команды с аргументами.

## Main

### Описание

В Main находится бесконечный цикл, где читаются входные строки и передаются в Executor.

### Методы

* `void main()` Функция, где происходит вход в программу.

## Memory 

### Описание 

`Memory` – класс, созданный для хранения переменных окружения. Заметим, что есть еще некоторые системные переменные окружения, например `$HOME`, которые «заранее» присвоены в любой сессии работы терминала.
Поэтому в конструкторе `Memory` проходит по списку переменных окружения и копирует их себе.

### Поля

*  `Map<String, String> storage` – ключом является имя переменной окружения, а значением – ее значение

### Методы

* `String get(String key)` – получение значение переменной по ключу.

* `void put(String key, String value)` – добавление в storage новой переменной окружения

* `void putAll(Map<String, String> m)` – добавление нескольких ключей за раз. Нужно для случая, который описывается дальше.


## Executor

### Описание

Executor отвечает за основную логику работы программы. Этому классу передают строку, которая будет передана парсеру.
После этого парсер выдает список не подставленных токенов (`RawArgs`). Далее происходит подстановка переменных и само выполнение команд.

### Поля

* `Memory memory` Хранилище данных
* `Command previousCommand` Предыдущая выполненная команда (необходима для выполнения пайпа)
* `String errorStream` Поток ошибок

### Методы

* `void execute(String input)` Выполняет команды, указанные в `input`, которые были переданы в командную строку.

## Parser

### Описание

У нас есть несколько важных случаев работы: 

1) В одинарных кавычках подстановка переменных не происходит
```console
% echo '$HOME'
$HOME
```

2) В двойных кавычках подставнока переменных происходит
```console
% echo "$HOME"
/Users/xbreathoflife
```

3) Если двойные кавычки внутри одинарных, то подстановка не произойдет
```console
% echo '"$HOME"'
"$HOME"
```

4) Если одинарные кавычки внутри двойных, то подстановка произойдет
```console
% echo "'$HOME'"
'/Users/xbreathoflife'
```

Поэтому нам нужно разбить нашу строку на элементы, подстроки.

* Все, что внутри кавычек, является одним элементом. Например, `"''$HOME''"` или `'""$HOME""'`. Причем важно знать, для каких элементов должна быть выполнена подстановка переменной, а для каких - нет. В примере выше для первого элемента выполнится подстановка, а для второго - нет.

* (Так как экранирование пока не поддержано, то одна одинарная кавычка не может являться подстрокой)

* Пробел не является элементом.

* Пайп (`|`) является элементом.

В результате получаем список подстрок. Причем часть из них помечена, что в них подстановка не требуется, другие же помечены, что требуется. Будем подставлять, пока не получим такой список, в котором никому подстановка не требуется. Причем заметим, что если строка отмечена, что подстановка требуется, но в ней нету знака `$`, то можем считать, что не требуется.

Именем переменной при подстановки считается все от символа `$` до символов `'`, `$`, `"` или конец подстроки.

После соединяем полученные подстроки и отдаем парсеру, чтобы он разбил строку на команды. 

### Методы

* `List<List<RawArgs>> parse(String line)` функция, которая преобразует строку в список аргументов

## RawArg

### Описание

Абстракция токена, в котором происходит подстановка или присваивание переменных. Она возникла из-за того, 
что не всегда нужно делать подстановку.

### Поля

`String arg` Сама строка, в которой будет происходит подстановка или будет происходить присваивание переменных.

`boolean can_substitute` Флаг, который говорит делать ли нам подстановку или нет.

### Методы

`String prepareArg(Memory memory)` Функция, которая выполняет подстановку или присваивание переменных, которые содержаться в данном токене

## Command

### Описание

У нас есть общий интерфейс `Command`, в котором есть следующие поля:

### Поля

* `String command` - имя комманды

* `InputStream inputStream` - поток ввода, из него читаем

* `OutputStream outputStream` - поток вывода, в него пишем

### Методы

* `ReturnCode execute()` - запрос на выполнения `command`. В зависимости от результата выполнения вернет `ReturnCode`, что все хорошо или плохо.

Команды, которые описаны далее, наследуются от этого интерфейса.

### CatCommand

Есть дополнительное поле:

`List<String> args`

Должно быть передано не более одного аргумента. Если один, то в поток вывода будет записано содержимое файла.

Если аргументов больше - ошибка.

Если аргументов нет, то содержимое `inputStream` будет записано  в `outputStream`.

### EchoCommand

Есть дополнительное поле:

`List<String> args`

Записывает в поток вывода переданные аргументы, разделенные пробелом.


### WcCommand

Есть дополнительное поле:

`List<String> args`

Выводит в поток вывода количество строк, слов и байт в файле для каждого переданного аргумента. Новая строка на каждый аргумент.

Если аргументов нет, то берет данные из потока чтения.

### PwdCommand

Нет аргументов.

Выводит путь к текущей директории в поток вывода.

### ExitCommand

Вызывает у основого класса метод `exit()`, тем самым завершая чтение команд в бесконечном цикле.

### OtherCommand

Есть дополнительное поле:

`List<String> args`

Вызывает внешнюю программу с переданными аргументами. Вывод записывается в поток вывода.
