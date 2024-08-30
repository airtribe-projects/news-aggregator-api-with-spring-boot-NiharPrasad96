MY CHANGES ARE IN https://github.com/airtribe-projects/news-aggregator-api-with-spring-boot-NiharPrasad96/tree/niharikaNewsAggregator branch.
All The APIs are working as expected , I am using the "https://api.thenewsapi.com/" for news. and DB is MYSQL.
In db I have created the schema "airtribe" and table name as "userinfo" with columns id(auto generated),name,password(encoded),username,preferred_category.
I am returning the fetched news as string for time being. 
I have checked if user is not present / not set preferences error handling and returning appropriate responses.
To run I have used maven and , using mvn install and "mvn spring-boot:run"
