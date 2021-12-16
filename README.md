# CS 199-225 Final Project: Runeterra Text Adventure
## Larry Peng - larrylp2

Using Clojure, explore the world of Runeterra through this text adventure.

Inspired by the world of League of Legends, this text adventure game focuses on the trials and tribulations of a Demacian squire with the dangerous mission to investigate a resurgence of ancient magic in Runeterra.

You win once you summon and defeat this threat to your homeland.

To launch the game, input `lein run` in the terminal.

## Controls

### Movement/General

|Text Input | Direction|
|-|-|
|`quit` | Exits Game|
|`n` / `north`|North|
|`s` / `south` | South|
|`e` / `east` | East
|`w` / `west` | West|
|`se` / `southeast` | South East|
|`nw` / `northwest` | North West|


### Information

|Text Input | Action|
|-|-|
|`look` |Room Description|
|`search`|Room Item Descriptions|
|`i` / `inventory` | Inventory Item Descriptions
|`spells` | Spell Descriptions|

### Items
|Text Input | Action|
|-|-|
|`drop [item index]` |Drops Inventory Item|
|`take [item index]`|Takes Room Item|
|`use` | Uses an Item (May Learn Spell)

### Boss Fight

|Text Input | Action|
|-|-|
|`cast [spell index]` |Casts Learned Spell|

