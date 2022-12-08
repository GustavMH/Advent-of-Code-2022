(require '[clojure.string :as str])

(defn strat [opp you]
  (+
   (case you
    \X 1
    \Y 2
    \Z 3)
   (case [opp you]
     ; Loss
     [\A \Z] 0
     [\B \X] 0
     [\C \Y] 0
     ; Draw
     [\A \X] 3
     [\B \Y] 3
     [\C \Z] 3
     ; Win
     [\A \Y] 6
     [\B \Z] 6
     [\C \X] 6)))

(defn which-move [opp you]
  (case you
    ; Win
    \Z (case opp \A \Y \B \Z \C \X)
    ; Draw
    \Y (case opp \A \X \B \Y \C \Z)
    ; Loss
    \X (case opp \A \Z \B \X \C \Y)))

(defn strat-2 [opp you]
  (strat opp (which-move opp you)))

(strat-2 \A \Y)
(strat-2 \B \X)
(strat-2 \C \Z)

(->>
 ; Read / Parse
 (slurp "input_2")
 str/split-lines
 (map #(vector (first %) (last %)))
 ; Process
 (map (partial apply strat-2))
 (reduce +))
