(require '[clojure.string :as str])

(defn parse-int [s]
  (Integer/parseInt s))

(defn distress-comp [a b]
  ;(println a b)
  (cond
    ;If both values are integers, the lower integer should come first.
    (and (integer? a) (integer? b)) (- a b)
    ;If both values are lists, compare the first value of each list,
    ;then the second value, and so on.
    (and (coll? a) (coll? b))
    (let [[x & xs] a
          [y & ys] b
          comp (distress-comp x y)]
      (if (zero? comp)
        (distress-comp xs ys)
        comp))
    (and (nil? a) (nil? b)) 0
    ;If the left list runs out of items first,
    ;the inputs are in the right order.
    (nil? a) -1
    (nil? b) 1
    ;If exactly one value is an integer,
    ;convert the integer to a list which
    ;contains that integer as its only value
    (integer? a) (distress-comp [a] b)
    (integer? b) (distress-comp a [b])))

(def input)

(->> (slurp "input_13") ; Q1
     (re-seq #"(.+?)\n(.+?)\n\n")
     (map rest)
     (map (partial map read-string))
     (map (partial apply distress-comp))
     (map-indexed #(vector (> 0 %2) (inc %1)))
     (filter first)
     (map second)
     (reduce +)) ; => 6369

(def sorted-idxs
  (->> (slurp "input_13")
       str/split-lines
       (filter (partial not= ""))
       (map (partial read-string))
       (#(conj % [[2]] [[6]]))
       (sort distress-comp)
       (map-indexed (fn [idx val] [idx val]))))

(* (inc (first (first (filter (fn [[idx val]] (= val [[2]])) sorted-idxs))))
   (inc (first (first (filter (fn [[idx val]] (= val [[6]])) sorted-idxs))))) ; => 25800
