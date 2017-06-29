# poloniex-api-java
Java API client for the Poloniex exchange with focus on simplicity and usability. 

Using this client is as simple as instantiating a new PoloniexExchangeService with your Poloniex API Key and API Secret as constructor parameters:

```java
String apiKey = "foo";
String apiSecret = "bar";
PoloniexExchangeService service = new PoloniexExchangeService(foo, bar);
```

Passing in null (or invalid values) for either the API Key or API Secret will prevent you from successfully calling the Poloniex Trading API methods (trading, retrieving private info related to your account, etc), but public API methods will still work. 

The PoloniexExchangeService offers the following functionality:

## Public API Methods (does not require Poloniex account)  

### Return ticker  
```java
String apiKey = "foo";
String apiSecret = "bar";
PoloniexExchangeService service = new PoloniexExchangeService(foo, bar);
PoloniexTicker btcTicker = service.returnTicker("USDT_BTC");
```

### Return all market pairs
```java
String apiKey = "foo";
String apiSecret = "bar";
PoloniexExchangeService service = new PoloniexExchangeService(foo, bar);
List<String> marketsList = service.returnAllMarkets();
```

### Return chart data  
```java
String apiKey = "foo";
String apiSecret = "bar";
PoloniexExchangeService service = new PoloniexExchangeService(foo, bar);
List<PoloniexChartData> btcDailyChartDataStartingFromYesterday = 
  service.returnChartData("USDT_BTC", 
                        86400L, 
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

## Lending APIs
### Active loans  
```java
String apiKey = "foo";
String apiSecret = "bar";
PoloniexLendingService service = new PoloniexLendingService(apiKey, apiSecret);
PoloniexActiveLoanTypes activeLoans = service.returnActiveLoans();

```
### Create a loan offer  
```java
String apiKey = "foo";
String apiSecret = "bar";
PoloniexLendingService service = new PoloniexLendingService(apiKey, apiSecret);

BigDecimal amount = new BigDecimal("0.01"); 
BigDecimal lendingRate = new BigDecimal("0.0042"); // 0.42%
int duration = 3; // 2 ~ 60
PoloniexLendingResult result = service.createLoanOffer("BTC", amount, lendingRate, duration, true);
```
### Return open loan offers  
```java
String apiKey = "foo";
String apiSecret = "bar";
PoloniexLendingService service = new PoloniexLendingService(apiKey, apiSecret);
List<PoloniexLoanOffer> list = service.returnOpenLoanOffers("BTC");
```

### Create loan offer  
```java
String apiKey = "foo";
String apiSecret = "bar";
PoloniexLendingService service = new PoloniexLendingService(apiKey, apiSecret);

BigDecimal amount = new BigDecimal("0.01"); 
BigDecimal lendingRate = new BigDecimal("0.0042"); // 0.42%
int duration = 3; // 2 ~ 60
PoloniexLendingResult result = service.createLoanOffer("BTC", amount, lendingRate, duration, true);
```
### Cancel loan offer  
```java
String apiKey = "foo";
String apiSecret = "bar";
PoloniexLendingService service = new PoloniexLendingService(apiKey, apiSecret);

PoloniexLendingResult result = service.cancelLoanOffer("49375906");
```

### Toggle auto renew  
```java
String apiKey = "foo";
String apiSecret = "bar";
PoloniexLendingService service = new PoloniexLendingService(apiKey, apiSecret);

PoloniexLendingResult result = service.toggleAutoRenew("8654331");
```
### Lending Histories  
```java
String apiKey = "foo";
String apiSecret = "bar";
PoloniexLendingService service = new PoloniexLendingService(apiKey, apiSecret);

// Weekly Report
List<PoloniexLendingHistory> lendingHistories = service.returnLendingHistory(24 * 7, 5000);
BigDecimal sum = BigDecimal.ZERO;
for (PoloniexLendingHistory h : lendingHistories)
{
  BigDecimal earned = h.earned;
  sum = sum.add(earned);
}
```



## Websocket Interface

Using Poloniex's websocket interface is also very simple. 
The following example sets up subscriptions to the general ticker as well as the USDT_BTC order book / trades, and then runs for 60 seconds:

```java
try (WSSClient poloniexWSSClient = new WSSClient("wss://api.poloniex.com", "realm1")) {
    poloniexWSSClient.subscribe(PoloniexSubscription.TICKER);
    poloniexWSSClient.subscribe(new PoloniexSubscription("USDT_BTC"));
    poloniexWSSClient.run(60000);
}
```

To setup your own subscription handler, just extend the PoloniexSubscription class and override the `public void call(PubSubData event)` method. 


## Donations

Your support is always welcome!

BTC: 1FnpWiJ2Lo89E4x26w5jsYhmXJS9sUBR3b
ETH: 0x5F99D8DD2d504369657f15101e9a0cdF0fAbb799

