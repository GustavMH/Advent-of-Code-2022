(require '[clojure.string :as str]
         '[clojure.set :as set])

(def input (str/split-lines (slurp "input3")))

(defn halve
  "Split string into two equal parts"
  [str]
  (let [l (count (into [] str))]
    (split-at (/ l 2) str)))

(defn score-char
  "Uses ascii values to score chars; a-z 1-26, A-Z 27-52"
  [ch]
  (- (int ch)
     (if (>= (int ch) (int \a)) 96 38)))

(defn score-bag
  "Score item that is in both half compartments"
  [bag]
  (->> bag halve
       (map set)
       (apply set/intersection)
       first score-char))

; Q1 the sum of scores for bag unique items
(reduce #(+ %1 (score-bag %2)) 0 input)

; Q2 Badge group score
(->> (map set input)
     (partition 3)
     (map #(->> (apply set/intersection %)
                first score-char))
     (reduce +))
