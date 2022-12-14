(require '[clojure.string :as str])

(defn parse-int [s]
  (Integer/parseInt s))

(def moves {"L" [-1 0] "R" [1 0] "U" [0 1] "D" [0 -1]})

(def steps
  (->> (slurp "input_9")
       str/split-lines
       (map (partial re-find #"(.) (\d+)"))
       (mapcat (fn [[_ d n]] (repeat (parse-int (str n)) d)))
       (mapv moves)))

(defn hi-diff [a b]
  (->> (mapv (comp (partial < 1) abs -) a b)
       (reduce #(or %1 %2))))

(defn move [[tails head] move]
  (println tails head move)
  (let [new-head (mapv + move head)]
    (if (hi-diff (last tails) new-head)
      [(conj tails head) new-head]
      [tails new-head])))

(->> (reduce move [[[0 0]] [0 0]] steps)
     first (into #{}) count)

(defn move-n [[tails knots] m]
  (move [tails (first knots)] m))
  ;(reduce #(move [%1 %2] m) tails knots))

(move-n [[[0 0]] (repeat 9 [0 0])] (first steps))
(move-n [[0 0] [[0 0]]] (first steps))
