(ns adventure.core
  (:require [clojure.string :as str]))

;; map initialization
(def init-map
  {:Demacia {:desc "Within these lands live a proud and noble population who ardently live by their laws and sense of justice. Their greatest cities are fortified by sleek white walls of magic-resistant Petricite, a testament to their military tradition and history in their ancestral fight against mages and wizards."
             :title "The Kingdom of Demacia"
             :dir_print "Directions: (n, se, e)"
             :dir {:North :Freljord
                   :East :Noxus
                   :SouthEast :Piltover}
             :contents #{:garen_sword}}
   :Freljord {:desc "Within this frozen expanse lives rampant primal energies, ancient gods, powerful tribes, and daring explorers. Its hardened but heartful inhabitants are constantly writing their own tales of bravery, betrayal, and brotherhood – each hoping for their legacy to survive the endless snow and permafrost."
              :title "The Frigid Freljord"
              :dir_print "Directions: (s, se)"
              :dir {:South :Demacia
                    :SouthEast :Noxus}
              :contents #{:frozen_heart}}
   :Shurima {:desc "Where once stood the greatest civilization that ever graced Runeterra now only remains scattered settlements and buried ruins. However, beneaths the sands lie rumblings of magic and divinity that suggest the ancient ascended god-kings of Shurima have not faded for good."
             :title "The Deserts of Shurima"
             :dir_print "Directions: (n, e)"
             :dir {:North :Zaun
                   :East :Bilgewater}
             :contents #{}}
   :Piltover {:desc "This city-state’s inhabitants are harbingers of innovation and progress, a process spearheaded by Piltover’s numerous academies and research facilities. Passing through this metropolis and global shipping center are constant streams of airships, ideas, and dreams."
              :title "Piltover, the City of Progress"
              :dir_print "Directions: (n, nw, s)"
              :dir {:South :Zaun
                    :North :Noxus
                    :NorthWest :Demacia}
              :contents #{:apple_smartphone}}
   :Zaun {:desc "Although Zaun is a sister city to Piltover, its chemical leaks, crowded slums, and organized crime highlight its contrast from Piltover. However, despite these conditions, the people of Zaun are incredibly innovative and adaptive, creating new technologies to challenge Piltover and reverse decades of exploitation."
          :title "Zaun, the Undercity"
            :dir_print "Directions: (n, s, e)"
          :dir {:North :Piltover
                :South :Shurima
                :East :Bilgewater}
          :contents #{:silco_eye}}
   :Bilgewater {:desc "This expansive island port city knows no laws. The only system of order that controls its pirates, mercenaries, and cultists is money. Deep within its murky waters lie horrific sea creatures and the souls of travellers, merchants, and pirates whose luck ran out."
                :title "Bilgewater Bay"
                :dir_print "Directions: (n, w)"
                :dir {:West :Zaun
                      :North :Ionia}
                :contents #{:ak47}}
   :Noxus {:desc "Although the legions of Noxus wage brutal wars of conquest against neighboring kingdoms, conquered citizens are given equal opportunities to advance and lead the Noxian war machine. In the eyes of the Noxian doctrine, one’s race, religion, and nobility are irrelevant to one’s strength. This expansionist military society aims to free Runeterra from rule by weak kings and spread their ideals of strength."
           :title "The Empire of Noxus"
            :dir_print "Directions: (nw, s, e, w)"
           :dir {:West :Demacia
                 :NorthWest :Freljord
                 :East :Ionia
                 :South :Piltover}
           :contents #{:imperial_mandate}}
   :Ionia {:desc "These lush lands are blessed by nature, untouched by its inhabitants and rich with natural magic. Its people live in harmony with nature, training their bodies and minds to superhuman limits. However, these lands have recently been stained with blood – marked as the next target of Noxian conquest."
           :title "The Wilderness of Ionia"
           :dir_print "Directions: (s, w)"
           :dir {:West :Noxus
                 :South :Bilgewater}
           :contents #{:sprit_tree_branch :shard_xerath}}})


;; item initailization
(def init-items
  {:garen_sword {:desc "A polished greatsword sword made with a sleek silver alloy that seems to absorb any mana in its vicinity."
                 :name "[Garen's Sword]"}
   :frozen_heart {:desc "A frozen heart of some ancient and forgotten creature. The heart is cold to the touch and seems to have a dim glow."
                  :name "[Frozen Heart]"}
   :shard_xerath {:desc "A jagged purple shard of some translucent mineral. It seems to be alive, constantly releasing mana and faintly tugging those that hold it south"
                  :name "[Shard of Xerath]"}
   :apple_smartphone {:desc "A strange metal plate with a dark glass on one side. It has buttons on the side, but it appears to not have any power. There is a logo of an apple on the back, is this a new Piltover invention?"
                      :name "[Apple Smartphone]"}
   :silco_eye {:desc "A dull crystalized eyeball that is black with a bright orange pupil and purple iris."
               :name "[Silco's Eye]"}
   :ak47 {:desc "A gun with a wooden stock and handle, but appears too light to be useful. It does not seem like anything manufactured in Zaun or Bilgewater. There is a label that says \"ak47\""
          :name "[AK-47]"}
   :imperial_mandate {:desc "A lengthy document with Noxian runes, seals, and emblems. Although it is just a piece of parchment, it exudes a tremendously powerful aura"
                      :name "[Imperial Mandate]"}
   :sprit_tree_branch {:desc "A dark blue branch that holds mysterious power"
                       :name "[Spirit Tree Branch]"}})

;; spell initialization
(def init-spells
  {:arc_slash {:desc "Embues energy into the user's blade, releasing a powerful slash that can travel several meters"
               :name "[Arc Slash]"
               :type :energy
               :damage 30
               :cost 10}
   :meditate {:desc "Channels natural powers, recovering the user's energy"
              :name "[Meditate]"
              :type :energy
              :damage 0
              :cost -40}
   :soul_strike {:desc "Empties the user's internal energy and draws blood from the user's hand, dealing damage based on life energy expended"
                 :name "[Soul Strike]"
                 :type :sacrifice}
   :shoot {:desc "Unleashed a fast barrage of tiny cannonballs from this AK47"
           :name "[Shoot]"
           :type :energy
           :cost 0 
           :damage 100}
   :guard {:desc "Spread a layer of energy over the user's body, reducing impact from magical attacks until the battle is over"
           :name "[Qi Guard]"
           :type :defend
           :cost 50}
   })


;;adventurer initialization
(def init-adventurer
  {:location :Demacia
   :inventory #{}
   :hp_current 100
   :hp_max 100
   :hp_regen 10
   :energy_current 50
   :energy_max 50
   :energy_regen 5
   :damageMultiplier 1
   :seen #{}
   :spells #{}})

;;boss initialization
(def init-boss
  {:name "Xerath, The Ascended"
   :desc "A primoridal arcane power whose only goal is to destroy Runeterra"
   :hp 500
   :arcane_bolt 30})

;;parsing function (using the regex given from the lecture)
(defn canonicalize [input]
  (into [] (map str/lower-case (str/split input #"(\W+|[.?!])")))) ;converts the set into a vector so it can be indexed

;;helper method that quits the game
(defn quitGame [state input]
  (println input)
  (update state :play not))

;;helper method that goes in a direction
(defn go [state dir]
  (let [location (get-in state [:adventurer :location]) ; gets the current location of the adventurer from the state
        dest (get (get-in state [:map location :dir]) dir)] ; gets the map of outgoing directions/destinations from the adventurer's current location
    (if (nil? dest)  ; if the destination is invalid
      (do (println "You can't go that way.")
          state)
      (assoc-in state [:adventurer :location] dest))))

;;helper method that gets an item's name
(defn getItemName [state item]
  (get-in state [:items item :name]))

;;helper method that gets an item's description
(defn getItemDesc [state item]
  (get-in state [:items item :desc]))

;;helper method that prints the adventurer's item short names
(defn displayInventoryShort [state]
  (print "Inventory: ")
  (println (map (partial getItemName state) (get-in state [:adventurer :inventory])))
  state)

;;helper function that loops through vector of items, printing descriptions for each
(defn printItem [state item] 
  (loop [index 0]
    (when (< index (count item))
      (print (getItemName state (get item index)))
      (println ": ")
      (println (getItemDesc state (get item index)))
        (recur (inc index)))))

;;helper method that prints the adventurer's item full descriptions
(defn displayInventory [state]
  (println (apply str (repeat 130 "-")))
  (println "Inventory Item Descriptions: ")
  (printItem state (into [](get-in state [:adventurer :inventory])))
  state)

;;helper method that displays all items in a room in a short list
(defn displayItemShort [state location]
  (print "Room Items: ")
  (println (map (partial getItemName state) (get-in state [:map location :contents]))) ;;use partial b/c was having issues with too many arguments for map function
  state)

;;helper method that displays longer item descriptions in a room
(defn displayItems [state]
  (println (apply str (repeat 130 "-")))
  (println "Room Item Descriptions: ")
  (printItem state (into [](get-in state [:map (get-in state [:adventurer :location]) :contents])))
  state)

;;helper method that gets a spell's name
(defn getSpellName [state spell]
  (get-in state [:spells spell :name]))

;;helper method that gets a spell's description
(defn getSpellDesc [state spell]
  (get-in state [:spells spell :desc]))

;;helper method that loops through a vector of spells, printing their names and descriptions
(defn printSpell [state spells]
  (loop [index 0]
    (when (< index (count spells))
      (print (getSpellName state (get spells index)))
      (println ": ")
      (println (getSpellDesc state (get spells index)))
      (recur (inc index)))))

;;helper method that displays all of the adventurer's spells (just names)
(defn displaySpellsShort [state]
  (print "Spells: ")
  (println (map (partial getSpellName state) (get-in state [:adventurer :spells]))))

;;helper method that displasy all of the adventurer's spells (longer description)
(defn displaySpells [state]
  (println (apply str (repeat 130 "-")))
  (println "Spell Descriptions: ")
  (printSpell state (into [] (get-in state [:adventurer :spells])))
  state)

;;helper method that outputs a string and returns the state
(defn printAndState [state text]
  (println (apply str (repeat 130 "-")))
  (println text)
  state)

;;helper method that displays the long description of a location
(defn displayLocation [state location]
  (println (apply str (repeat 130 "-")))
  (println (get-in state [:map location :desc]))
  state)

;;helper method that checks if the player has met the conditions to summon Xerath
;;returns boolean value
(defn checkBossCondition [state]
  (let [location (get-in state [:adventurer :location])
        inventory (get-in state [:adventurer :inventory])]
    (and (= location :Shurima) (get inventory :shard_xerath)))) ;;true if player is at shurima and has the shard of xerath

;;helper method that adds a spell to the user's spells and removes an item from the player's inventory
(defn addSpell [state spell item]
  ;;removes the item first, then adds the spell
  (update-in (update-in state [:adventurer :inventory] #(disj % item)) [:adventurer :spells] #(conj % spell)))

;;helper method that handles using an item
(defn useItem [state itemIndex]
  ;;first checks if the itemIndex is valid
  (let [inventory (get-in state [:adventurer :inventory])
        invVector (into [] inventory)
        index (- (int (.charAt itemIndex 0)) 48)] ;;ascii value for '0' is 48, so subtract this result by 48
    (if (and (> (count invVector) 0) (and (> index -1) (< index (count inventory)))) ;; only consider these commands if there are items in inventory and index is valid
      (let [item (get invVector index)]
         (print "Attemping to use Item: ")
         (println (get-in state [:items item :name]))
         (if (= item :ak47) (printAndState (addSpell state :shoot :ak47) "New Spell [Shoot] Learned!")
         (if (= item :sprit_tree_branch) (printAndState (addSpell state :meditate :sprit_tree_branch) "New Spell [Meditate] Learned!")
         (if (= item :silco_eye) (printAndState (addSpell state :soul_strike :silco_eye) "New Spell [Soul Strike] Learned!")
         (if (= item :frozen_heart) (printAndState (addSpell state :guard :frozen_heart) "New Spell [Guard] learned")
         (if (= item :garen_sword) (printAndState (addSpell state :arc_slash :garen_sword) "New Spell [Arc Slash] learned")
         (if (= item :apple_smartphone) (printAndState (update-in state [:adventurer :items] #(disj % item)) "*ring* *ring* *ring* Hello? Who is thisss... *cuts out* *item explodes")
         (if (= item :shard_xerath) (printAndState state "Nothing happens, but you feel it this obsidian shard tug you towards the Deserts of Shurima...")
         (printAndState state "Nothing Happens")))))))))
      (printAndState state "Invalid Item Index/No Items To Use"))))

;; had issue with state not updating when all of this was done in the select item method
(defn executeItemTake [state index avail]
  ;;adds the item to the player inventory first, then uses that returned state to remove the same item from the room
  (update-in (update-in state [:adventurer :inventory] #(conj % (get avail index)))
             [:map (get-in state [:adventurer :location]) :contents] #(disj % (get avail index))))

(defn executeItemDrop [state index userItems]
  ;;adds the item to the room first, then uses that returned state to remove the same item from the player's inventory
  (update-in (update-in state [:map (get-in state[:adventurer :location]) :contents] #(conj % (get userItems index))) 
             [:adventurer :inventory] #(disj % (get userItems index))))

;;drops an item
(defn selectItem [state itemIndex]
  (let [currentLoc (get (get state :map) (get-in state [:adventurer :location]))
        availableItems (into [] (get currentLoc :contents)) ;;convert the set to a vector so I can be indexed
        index (- (int (.charAt itemIndex 0)) 48)] ;;ascii value for '0' is 48, so subtract this result by 48

    (if (and (> index -1) (< index (count availableItems)))
      (executeItemTake state index availableItems) ;; adds the item to the player's inventory and removes it from the room's contents
      (printAndState state "Please Select A Valid Item Index"))))

(defn selectItemDrop [state itemIndex]
  (let [userItems (into [] (get-in state [:adventurer :inventory])) ;;convert the set to a vector so I can be indexed
        index (- (int (.charAt itemIndex 0)) 48)] ;;ascii value for '0' is 48, so subtract this result by 48

    (if (and (> index -1) (< index (count userItems)))
      (executeItemDrop state index userItems) ;; adds the item to the rooms' contents and removes it from the player's inventory
      (printAndState state "Please Select A Valid Item Index"))))


;;helper method that handles item collection
(defn takeItem [state itemIndex]
  (let [location (get-in state [:adventurer :location])
        map (get state :map)
        availItems (get-in map [location :contents])]
    (if (not (= (count availItems) 0)) 
      (selectItem state itemIndex)
        (printAndState state "No Items to Take"))))

;;helper method that handles item dropping
(defn dropItem [state itemIndex]
  (let [userItems (get-in state [:adventurer :inventory])]
    (if (not (= (count userItems) 0))
      (selectItemDrop state itemIndex)
      (printAndState state "No Items to Drop"))))

;;helper method that prints the status of the adventurer
(defn displayAdventurer [state]
  (println (apply str (repeat 130 "-")))
  (displayInventoryShort state)
  (displaySpellsShort state)
  (println (apply str (repeat 130 "-"))))

(defn calculateImpact [state player energy damage]
  (let [currentEnergy (get player :energy_current)]
    (if (< currentEnergy energy) (printAndState state "Not Enough Energy")
        ;;subract energy from user first, then updates enemy hp
        (if (< (- (get-in state [:boss :hp]) damage) 1) ;;if enemy hp falls below 1, you WIN!!!
        (quitGame state "YOU DEFEATED XERATH!!!!!")
        (update-in (update-in state [:adventurer :energy_current] - energy) [:boss :hp] - damage)))))

(defn castSpell [state spellIndex]
  (let [player (get-in state [:adventurer])
        spells (get-in state [:adventurer :spells])
        index (- (int (.charAt spellIndex 0)) 48)] ;;ascii value for '0' is 48, so subtract this result by 48
    (if (> (count spells) 0)
      (let [currentSpell (get (into [] spells) index) ;;convert set of spells into vector for better indexing
            type (get-in state [:spells currentSpell :type])
            energy (get-in state [:spells currentSpell :cost])
            damage (get-in state [:spells currentSpell :damage])] 
        ;;energy type spell that costs the player's energy and does an amount of damage
        (if (and (= type :energy) (not (< (get player :energy_current) energy))) (calculateImpact state player energy damage)
            
        ;;guard spell reduces incoming damage by .25 (stacking)
        (if (= type :guard) (update-in state [:adventurer :damageMultiplier] * (/ 3 4))
        
        ;;sacrifice spell costs entire remaining energy
        (if (= type :sacrifice) (calculateImpact state player (get player :energy_current) (* (get player :energy_current) 6))
        
            (printAndState state "Invalid Spell")))))
      
    (printAndState state "You have no spells..."))))

(defn reactBoss [state input-vector]
  (let [first (get input-vector 0)
        arguments (count input-vector)]
    (if (= first "quit") (quitGame state "Quitting Game (Running from Boss)")
      (if (= arguments 2)
        (let [spellIndex (get input-vector 1)]
          (if (= first "cast") (castSpell state spellIndex) (printAndState state "Invalid Command")))
        (printAndState state "Invalid Command"))  )
    ))

(defn displayBoss [state]
  (println (apply str (repeat 130 "-")))
  (print "BOSSHP (")
  (print (get-in state [:boss :hp]))
  (print "): ")
  (println (apply str (repeat (get-in state [:boss :hp]) "╬"))) ; hp bar will be repeated ╬ characters
  (println (apply str (repeat 130 "-"))))

(defn bossFightStatus [state]
  (let [boss (get-in state [:boss])]
    (println (apply str (repeat 130 "-")))
    (print "HEALTH (")
    (print (get-in state [:adventurer :hp_current]))
    (print "/")
    (print (get-in state [:adventurer :hp_max]))
    (print "): ")
    (println (apply str (repeat (get-in state [:adventurer :hp_current]) "█"))) ; hp bar will be repeated █ characters
    (print "ENERGY (")
    (print (get-in state [:adventurer :energy_current]))
    (print "/")
    (print (get-in state [:adventurer :energy_max]))
    (print "):   ")
    (println (apply str (repeat (get-in state [:adventurer :energy_current]) "▓"))) ; energy bar will be repeated ▓ characters
    (displayAdventurer state)
    (displayBoss state)
    (if (< (get-in state [:adventurer :hp_current]) (get boss :arcane_bolt)) ;;if hp falls below 1, you lose!
      (quitGame state "You Were incinerated by Xerath. All of Runeterra will soon follow...")

      (update-in state [:adventurer :hp_current] - (get boss :arcane_bolt)))))
    

;;loop that initiates the bossfight

(defn beginBossFight [state]
  (println (apply str (repeat 130 "-")))
  (println "Xerath Emerges from the Sands of Shurima")
  (println "A towering humanoid golem constructed from arcane runes, it looks upon the world: testing its forgotten primorial powers.")
  (println "Luckily, attached around its neck are chains buried into the sands, slay this foe now before it ravages the rest of Runeterra")
  (loop [boss-state state]
    (println (get boss-state :play))
    (when (and (get boss-state :play) (and (> (get-in boss-state [:boss :hp]) 0) (> (get-in boss-state [:adventurer :hp_current]) 0))) ;;only continue if boss/player are both alive and the player wants to keep playing
      (let [bossAttack (bossFightStatus boss-state)
            _ (println "What skill do you use?")
            command (read-line)]
      (recur (reactBoss bossAttack (canonicalize command)))))))


;;given a state and an input vector, attempts to do the action/reacts to the input vector
;;tells the user if an action is invalid
(defn react [state input-vector] ;;now go through the input vector of instructions, checking the first
  (let [first (get input-vector 0)
        arguments (count input-vector)]
      (if (= first "quit") (quitGame state "Quitting Game") ;;when the input command is quit, adjust the :play value to 0 to signal the game to end)
      (if (and (or (= first "n") (= first "north")) (= arguments 1)) (go state :North)
      (if (and (or (= first "s") (= first "south")) (= arguments 1)) (go state :South)
      (if (and (or (= first "e") (= first "east")) (= arguments 1)) (go state :East)
      (if (and (or (= first "w") (= first "west")) (= arguments 1)) (go state :West)
      (if (and (or (= first "se") (= first "southeast")) (= arguments 1)) (go state :SouthEast)
      (if (and (or (= first "nw") (= first "northwest")) (= arguments 1)) (go state :NorthWest)
      (if (and (or (= first "i") (= first "inventory")) (= arguments 1)) (displayInventory state)
      (if (and (= first "search") (= arguments 1)) (displayItems state)
      (if (and (= first "look") (= arguments 1)) (displayLocation state (get-in state [:adventurer :location]))
      (if (and (= first "take") (= arguments 2)) (takeItem state (get input-vector 1))         
      (if (and (= first "drop") (= arguments 2)) (dropItem state (get input-vector 1))
      (if (and (= first "use") (= arguments 2)) (useItem state (get input-vector 1))  
      (if (and (= first "spells") (= arguments 1)) (displaySpells state)
          (printAndState state "Invalid Command")))))))))))))))))


(defn status [state]
  (if (checkBossCondition state) (beginBossFight state) ;;begins the bossfight if the conditions are met
  (let [location (get-in state [:adventurer :location])
        the-map (get state :map)]
        (displayAdventurer state)
        (println (str "You are in " (get-in the-map [location :title]) "."))
        (println (str (get-in the-map [location :dir_print]) "."))
        (displayItemShort state location)
    ; checks if the location has already been seen by the adventurer
        (when (not (contains? (get-in state [:adventurer :seen]) location)) (displayLocation state location)) ; prints out the initial longer description if not in seen
        (update-in state [:adventurer :seen] #(conj % location))))) ; adds current location to seen locations


(defn -main [& args]
  (loop [local-state {:items init-items :map init-map :adventurer init-adventurer :play (boolean 1) :boss init-boss :spells init-spells}]
    (when (get local-state :play)  ; only continue running if play is true and the player is still playing
      (let [pl (status local-state)]
        (when (get pl :play)
          (let
            [line (println (apply str (repeat 130 "-")))
            _  (println "What do you want to do?")
            command (read-line)]
          (recur (react pl (canonicalize command))))
      )))))



