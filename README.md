# poloniex-api-java
Java API client for the Poloniex exchange

Using this client is as simple as instantiating a new PoloniexExchangeService with your Poloniex API Key and API Secret as constructor parameters:

```java
String apiKey = "foo";
String apiSecret = "bar";
PoloniexExchangeService service = new PoloniexExchangeService(foo, bar);
```

Passing in null (or invalid values) for either the API Key or API Secret will prevent you from successfully calling the Poloniex Trading API methods (trading, retrieving private info related to your account, etc), but public API methods will still work. 

The PoloniexExchangeService offers the following functionality:

## Public API Methods (do not require Poloniex account)  

### Return ticker  
```java
String apiKey = "foo";
String apiSecret = "bar";
PoloniexExchangeService service = new PoloniexExchangeService(foo, bar);
PoloniexTicker btcTicker = service.returnTicker("USDT_BTC");
```

### Return chart data  
```java
String apiKey = "foo";
String apiSecret = "bar";
PoloniexExchangeService service = new PoloniexExchangeService(foo, bar);
List<PoloniexChartData> btcDailyChartDataStartingFromYesterday = 
  service.returnChartData("USDT_BTC", 
                        86400, 
                        ZonedDateTime.now(ZoneOffset.UTC).minusDays(1).toEpochSecond());
```

## Trading API Methods (Requires Poloniex account and API access enabled)  

### Return balance
```java
String apiKey = "foo";
String apiSecret = "bar";
PoloniexExchangeService service = new PoloniexExchangeService(foo, bar);
PoloniexCompleteBalance btcBalance = service.returnBalance("BTC");
```

### Return fee info/schedule
```java
String apiKey = "foo";
String apiSecret = "bar";
PoloniexExchangeService service = new PoloniexExchangeService(foo, bar);
PoloniexFeeInfo feeInfo = service.returnFeeInfo();
```

### Return open orders
```java
String apiKey = "foo";
String apiSecret = "bar";
PoloniexExchangeService service = new PoloniexExchangeService(foo, bar);
List<PoloniexOpenOrder> UsdtBtcOpenOrders = service.returnOpenOrders("USDT_BTC");
```

### Return trade history
```java
String apiKey = "foo";
String apiSecret = "bar";
PoloniexExchangeService service = new PoloniexExchangeService(foo, bar);
List<PoloniexTradeHistory> UsdtBtcTradeHistory = service.returnTradeHistory("USDT_BTC");
```

### Buy
```java
String apiKey = "foo";
String apiSecret = "bar";
PoloniexExchangeService service = new PoloniexExchangeService(foo, bar);

String currencyPair = "USDT_BTC";
BigDecimal buyPrice = BigDecimal.valueOf("1980");
BigDecimal amount = BigDecimal.ONE;
boolean fillOrKill = false;
boolean immediateOrCancel = false;
boolean postOnly = false;
PoloniexOrderResult buyOrderResult = 
      service.buy(currencyPair, buyPrice, amount, fillOrKill, immediateOrCancel, postOnly);
```

### Sell
```java
String apiKey = "foo";
String apiSecret = "bar";
PoloniexExchangeService service = new PoloniexExchangeService(foo, bar);

String currencyPair = "USDT_BTC";
BigDecimal sellPrice = BigDecimal.valueOf("1980");
BigDecimal amount = BigDecimal.ONE;
boolean fillOrKill = false;
boolean immediateOrCancel = false;
boolean postOnly = false;
PoloniexOrderResult buyOrderResult = 
      service.sell(currencyPair, buyPrice, amount, fillOrKill, immediateOrCancel, postOnly);
```

### Cancel order  
```java
String apiKey = "foo";
String apiSecret = "bar";
PoloniexExchangeService service = new PoloniexExchangeService(foo, bar);

String orderNumber = "123456789";
boolean success = service.cancelOrder(orderNumber);
```

### Move order  
```java
String apiKey = "foo";
String apiSecret = "bar";
PoloniexExchangeService service = new PoloniexExchangeService(foo, bar);

String orderNumber = "123456789";
boolean success = service.moveOrder(orderNumber);
```

