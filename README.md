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

1. Public API Methods (do not require Poloniex account)  
Return ticker  
Return chart data  


2. Trading API Methods (Requires Poloniex account and API access enabled)  
Return balances  
Return complete balances   
Return fee info/schedule  
Return open orders  
Return trade history  
Buy    
Sell  
Cancel order  
Move order  


