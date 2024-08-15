# mm0824

This application generates and prints a tool rental agreement.

## Building

This application is built into an executable jar

`mvn package` places the executable jar in `./target`

## Testing

`mvn test`

## Usage

```shell
usage: java -jar mm0824-0.0.1-SNAPSHOT.jar
 -di,--discount <arg>      Percentage Discount (0..100)
 -dt,--rental-date <arg>   Rental Date (yyyy-MM-dd)
 -dy,--rental-days <arg>   Rental Days (0..100)
 -h,--help                 Print this message
 -l,--list                 List configuration
 -tc,--tool-code <arg>     Tool Code (^[A-Z]{4}$)
```

## Examples

### Generating a rental agreement without a discount

```shell
❯ java -jar ./target/mm0824-0.0.1-SNAPSHOT.jar -tc CHNS -dt 2024-08-01 -dy 5
```
```text
Tool code: CHNS
Tool type: CHAINSAW
Date: 08/01/2024
Days: 5
Return Date: 08/06/2024
Days charged: 3
Charge per Day: $1.49
Charge total: $4.47
Discount %: 0%
Discount amount: $0
Final charge: $4.47
```

### Generating a rental agreement with a discount
```shell
❯ java -jar ./target/mm0824-0.0.1-SNAPSHOT.jar -tc CHNS -dt 2024-08-01 -dy 5 -di 25
```
```text
Tool code: CHNS
Tool type: CHAINSAW
Date: 08/01/2024
Days: 5
Return Date: 08/06/2024
Days charged: 3
Charge per Day: $1.49
Charge total: $4.47
Discount %: 25%
Discount amount: $1.12
Final charge: $3.35
```

### List the inventory and configuration

```shell
❯ java -jar ./target/mm0824-0.0.1-SNAPSHOT.jar -l
```
```text
Loaded Tools
Tool(code=CHNS, type=CHAINSAW, brand=Stihl)
Tool(code=LADW, type=LADDER, brand=Werner)
Tool(code=JAKD, type=JACKHAMMER, brand=DeWalt)
Tool(code=JAKR, type=JACKHAMMER, brand=Rigid)
Loaded Tool Info
ToolTypeInfo(toolType=LADDER, dailyCharge=1.99, weekdayCharge=true, weekendCharge=true, holidayCharge=false)
ToolTypeInfo(toolType=CHAINSAW, dailyCharge=1.49, weekdayCharge=true, weekendCharge=false, holidayCharge=true)
ToolTypeInfo(toolType=JACKHAMMER, dailyCharge=2.99, weekdayCharge=true, weekendCharge=false, holidayCharge=false)
Loaded Holidays
FixedDateHoliday(holiday Independence Day: JUL 4 only if JUL 4 is Mon,Tue,Wed,Thu,Fri)
FixedDateHoliday(holiday Independence Day: JUL 3 only if JUL 4 is Sat)
FixedDateHoliday(holiday Independence Day: JUL 5 only if JUL 4 is Sun)
FloatingHoliday(holiday Labor Day: 1st Monday after SEP 1)
```
