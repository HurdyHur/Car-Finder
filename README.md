# Car-Finder

Application for finding cars to buy. 

Written in kotlin using Jetpack Compose for UI.

Notes:

- Added mocked out repository for retrieving makes and models. I would most likely change the repository api to suspend functions if calling network,
but left as synchronous function calls for now.
- Used Compose DropDownMenu for display Query options. If I were to approach it again I would not use this due to undesired behaviour of dismissing dialog
on configuration changed. 
- No cacheing implemented but could easily be plugged into repositorys.
- Currently Disabling Search button until all queries filled due to mock api limitations. Ideally would allow the option of not filling and searching to allow random/recomended search results.  



![LightMode](https://user-images.githubusercontent.com/66484129/179122392-585eb2da-09c8-472f-b9b1-ea1933d80d17.gif)                

![DarkMode](https://user-images.githubusercontent.com/66484129/179122388-60d834b3-31f4-478c-a575-78e53f343278.gif) 

