(require '[clojure.string :as str])

(defn parse-int [s]
  (Integer/parseInt s))

(def plus-scan
  (->> (slurp "input_10")
       str/split-lines
       (map (comp second (partial re-find #" (.+)$")))
       (mapcat #(if (nil? %) [0] [ 0 (parse-int %)]))
       (cons 1)
       (reductions +)
       rest))

(->> (map #(* % (nth plus-scan (dec %))) [20 60 100 140 180 220])
     (reduce +))

(defn is-lit? [cursor-pos cycl]
  (>= 1 (abs (- cursor-pos (mod cycl 40)))))

(->> (map is-lit? plus-scan (range 1 (count plus-scan)))
     (map #(if % "#" " "))
     (#(concat % [0])) ; Fixes partion dropping last line
     (partition 40)
     (map str/join))
