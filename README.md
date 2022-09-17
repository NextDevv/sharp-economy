# sharp-economy
Sharp economy plugin, it's a private plugin from the server SharpPVP (Created by me :) )

For the implemetation in your project heres the dependency

## **MAVEN**
```
	<repositories>
		<repository>
		    <id>jitpack.io</id>
		    <url>https://jitpack.io</url>
		</repository>
	</repositories>
```

```
	<dependency>
	    <groupId>com.github.User</groupId>
	    <artifactId>Repo</artifactId>
	    <version>Tag</version>
	</dependency>
```

## **GRADLE**
```
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

```
        dependencies {
	        implementation 'com.github.SpreestDev:sharp-economy:Tag'
	}
```

## Main methods
One is the `getUserBalance(String uuid)`

source code:
```
    public static long getUserBalance(String uuid) {
        PlayerData data = DataBase.getPlayerFromUUID(uuid);
        return data == null? 0 : data.getBalance();
    }
```

example:
```
  player.sendMessage("Your balance is: "+ SharpEconomy.getUserBalance(player.getUniqueId().toString()));
```

The other method is `setUserBalance(String uuid, long balance)`

source code:
```
    public static boolean setUserBalance(String uuid, long balance) {
        PlayerData data = DataBase.getPlayerFromUUID(uuid);
        if(data!= null) {
            data.setBalance(balance);
            new DataBase().updatePlayerDatabase(data);
            return true;
        }
        return false;
    }
```

example:
```
   SharpEconomy.setUserBalance(player.getUniqueId().toString(), 100L);
   player.sendMessage("Your balance has been set to " + SharpEconomy.getUserBalance(player.getUniqueId().toString()));
```
