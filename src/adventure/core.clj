(ns adventure.core
  (:require [clojure.string :as str]))

;; map initialization
(def init-map
  {:Demacia {:desc "Within these lands live a proud and noble population who ardently live by their laws and sense of justice. Their greatest cities are fortified by sleek white walls of magic-resistant Petricite, a testament to their military tradition and history in their ancestral fight against mages and wizards."
             :title "The Kingdom of Demacia"
             :dir_print "Directions: [n, se, e]"
             :dir {:North :Freljord
                   :East :Noxus
                   :SouthEast :Piltover}
             :contents #{:garen_sword}}
   :Freljord {:desc "Within this frozen expanse lives rampant primal energies, ancient gods, powerful tribes, and daring explorers. Its hardened but heartful inhabitants are constantly writing their own tales of bravery, betrayal, and brotherhood – each hoping for their legacy to survive the endless snow and permafrost."
              :title "The Frigid Freljord"
              :dir_print "Directions: [s, se]"
              :dir {:South :Demacia
                    :SouthEast :Noxus}
              :contents #{:frozen_heart}}
   :Shurima {:desc "Where once stood the greatest civilization that ever graced Runeterra now only remains scattered settlements and buried ruins. However, beneaths the sands lie rumblings of magic and divinity that suggest the ancient ascended god-kings of Shurima have not faded for good."
             :title "The Deserts of Shurima"
             :dir_print "Directions: [n, e]"
             :dir {:North :Zaun
                   :East :Bilgewater}
             :contents #{:shard_xerath}}
   :Piltover {:desc "This city-state’s inhabitants are harbingers of innovation and progress, a process spearheaded by Piltover’s numerous academies and research facilities. Passing through this metropolis and global shipping center are constant streams of airships, ideas, and dreams."
              :title "Piltover, the City of Progress"
              :dir_print "Directions: [n, nw, s]"
              :dir {:South :Zaun
                    :North :Noxus
                    :NorthWest :Demacia}
              :contents #{:apple_smartphone}}
   :Zaun {:desc "Although Zaun is a sister city to Piltover, its chemical leaks, crowded slums, and organized crime highlight its contrast from Piltover. However, despite these conditions, the people of Zaun are incredibly innovative and adaptive, creating new technologies to challenge Piltover and reverse decades of exploitation."
          :title "Zaun, the Undercity"
            :dir_print "Directions: [n, s, e]"
          :dir {:North :Piltover
                :South :Shurima
                :East :Bilgewater}
          :contents #{:silco_eye}}
   :Bilgewater {:desc "This expansive island port city knows no laws. The only system of order that controls its pirates, mercenaries, and cultists is money. Deep within its murky waters lie horrific sea creatures and the souls of travellers, merchants, and pirates whose luck ran out."
                :title "Bilgewater Bay"
                :dir_print "Directions: [n, w]"
                :dir {:West :Zaun
                      :North :Ionia}
                :contents #{:ak47}}
   :Noxus {:desc "Although the legions of Noxus wage brutal wars of conquest against neighboring kingdoms, conquered citizens are given equal opportunities to advance and lead the Noxian war machine. In the eyes of the Noxian doctrine, one’s race, religion, and nobility are irrelevant to one’s strength. This expansionist military society aims to free Runeterra from rule by weak kings and spread their ideals of strength."
           :title "The Empire of Noxus"
            :dir_print "Directions: [nw, s, e, w]"
           :dir {:West :Demacia
                 :NorthWest :Freljord
                 :East :Ionia
                 :South :Piltover}
           :contents #{:imperial_mandate}}
   :Ionia {:desc "These lush lands are blessed by nature, untouched by its inhabitants and rich with natural magic. Its people live in harmony with nature, training their bodies and minds to superhuman limits. However, these lands have recently been stained with blood – marked as the next target of Noxian conquest."
           :title "The Wilderness of Ionia"
           :dir_print "Directions: [s, w]"
           :dir {:West :Noxus
                 :South :Bilgewater}
           :contents #{:sprit_tree_branch}}})


;; item initailization
(def init-items
  {:garen_sword {:desc "A polished greatsword sword made with a sleek silver alloy that seems to absorb any mana in its vicinity."
                 :name "Garen's Sword"}
   :frozen_heart {:desc "A frozen heart of some ancient and forgotten creature. The heart is cold to the touch and seems to have a dim glow."
                  :name "Frozen Heart"}
   :shard_xerath {:desc "A jagged purple shard of some translucent mineral. It seems to be alive, constantly releasing mana and faintly tugging those that hold it south"
                  :name "Shard of Xerath"}
   :apple_smartphone {:desc "A strange metal plate with a dark glass on one side. It has buttons on the side, but it appears to not have any power. There is a logo of an apple on the back, is this a new Piltover invention?"
                      :name "Apple Smartphone"}
   :silco_eye {:desc "A dull crystalized eyeball that is black with a bright orange pupil and purple iris."
               :name "Silco's Eye"}
   :ak47 {:desc "A gun with a wooden stock and handle, but appears too light to be useful. It does not seem like anything manufactured in Zaun or Bilgewater. There is a label that says \"ak47\""
          :name "AK-47"}
   :imperial_mandate {:desc "A lengthy document with Noxian runes, seals, and emblems. Although it is just a piece of parchment, it exudes a tremendously powerful aura"
                      :name "Imperial Mandate"}
   :sprit_tree_branch {:desc "A dark blue branch that holds mysterious power"
                       :name "Spirit Tree Branch"}})

;;adventuerer initialization
(def init-adventurer
  {:location :Demacia
   :inventory #{}
   :hp 100
   :hp_regen 10
   :energy 50
   :energy_regen 5
   :tick 0
   :seen #{}})

;;parsing function (using the regex given from the lecture)
(defn canonicalize [input]
  (into [] (map str/lower-case (str/split input #"(\W+|[.?!])")))) ;converts the set into a vector so it can be indexed

;;helper method that quits the game
(defn quitGame [state input]
  (println input)
  (update state :play not))

;;helper method that checks if the input direction is north


(defn go [state dir]
  (let [location (get-in state [:adventurer :location]) ; gets the current location of the adventurer from the state
        dest (get (get-in state [:map location :dir]) dir)] ; gets the map of outgoing directions/destinations from the adventurer's current location
    (if (nil? dest)  ; if the destination is invalid
      (do (println "You can't go that way.")
          state)
      (assoc-in state [:adventurer :location] dest))))

;;helper method that prints the adventurer's inventory
(defn displayInventory [state]
  (println (apply str (repeat 130 "-")))
  (print "Inventory: ")
  (println (apply str (get-in state [:adventurer :inventory])))
  state)

(defn printItem [state item]
  (println (get-in state [:items item :name]))
  (println (get-in state [:items item :desc]))
  )

;;helper method that displays item in a room
(defn displayItems [state]
  (println (apply str (repeat 130 "-")))
  (println "Room Items: ")
  (let [location (get-in state [:adventurer :location])]
    (apply printItem state (get-in state [:map location :contents])))
  state)


;;given a state and an input vector, attempts to do the action/reacts to the input vector
;;tells the user if an action is invalid
;;starts by looking at the first word in the input-vector to see if it is a recognized keyword
;; "n, north, go north"
(defn react [state input-vector] ;;now go through the input vector of instructions, checking the first
  (if (= 1 (count input-vector))
    (let [first (get input-vector 0)]
      (if (= first "quit") (quitGame state "Quitting Game") ;;when the input command is quit, adjust the :play value to 0 to signal the game to end)
      (if (or (= first "n") (= first "north")) (go state :North)
      (if (or (= first "s") (= first "south")) (go state :South)
      (if (or (= first "e") (= first "east")) (go state :East)
      (if (or (= first "w") (= first "west")) (go state :West)
      (if (or (= first "se") (= first "southeast")) (go state :SouthEast)
      (if (or (= first "nw") (= first "northwest")) (go state :NorthWest)
      (if (or (= first "i") (= first "inventory")) (displayInventory state)
      (if (= first "look") (displayItems state)
          ((println "Please Input a Valid Command")
          state)))))))))))
    ((println (apply str (repeat 130 "-")))
     (println "Please Input a Valid Command")
     state)))

;;helper method that prints the status of the adventurer
(defn displayAdventurer [state]
  (println (apply str (repeat 130 "-")))
  (print "HEALTH: ")
  (println (apply str (repeat (get-in state [:adventurer :hp]) "█"))) ; hp bar will be repeated █ characters
  (print "ENERGY: ")
  (println (apply str (repeat (get-in state [:adventurer :energy]) "▓"))) ; energy bar will be repeated ▓ characters
  (println (apply str (repeat 130 "-"))))


(defn status [state]
  (let [location (get-in state [:adventurer :location])
        the-map (get state :map)]
    (displayAdventurer state)

    (println (str "You are in " (-> the-map location :title) "."))
    (println (str (-> the-map location :dir_print) "."))
    (println (apply str (repeat 130 "-")))
    ; checks if the location has already been seen by the adventurer
    (when (not (contains? (get-in state [:adventurer :seen]) location)) (println (-> the-map location :desc))) ; prints out the initial longer description if not in seen
    (update-in state [:adventurer :seen] #(conj % location)))) ; adds current location to seen locations

(defn -main [& args]
  (loop [local-state {:items init-items :map init-map :adventurer init-adventurer :play (boolean 1)}]
    (when (= (boolean 1) (get local-state :play))  ; only continue running if play is true and the player is still playing
      (let [pl (status local-state)
            _  (println "What do you want to do?")
            command (read-line)]
      (recur (react pl (canonicalize command))))))) 



