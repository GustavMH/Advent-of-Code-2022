(require '[clojure.string :as str])

(defn score
  "Score of sign combination"
  [opp you]
  (+ (case you \X 1 \Y 2 \Z 3)
     (case [opp you]
       ([\A \Z] [\B \X] [\C \Y]) 0    ; Loss
       ([\A \X] [\B \Y] [\C \Z]) 3    ; Draw
       ([\A \Y] [\B \Z] [\C \X]) 6))) ; win

(defn which-move
  "Choice to win Rock-paper-scissors"
  [opp you]
  (case you
    \Z (case opp \A \Y \B \Z \C \X)   ; Win
    \Y (case opp \A \X \B \Y \C \Z)   ; Draw
    \X (case opp \A \Z \B \X \C \Y))) ; Loss

(def input
 (->> (slurp "input_2")
      str/split-lines))

(->> input ; Q1 wins usings moves from input
     (map (fn [[opp _ you]] (strat opp you)))
     (reduce +))

(->> input ; Q2 wins usings outcomes from input
     (map (fn [[opp _ you]] (strat opp (which-move opp you))))
     (reduce +))
