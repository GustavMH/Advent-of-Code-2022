(require '[clojure.string :as str])

(defn parse-int [s]
  (Integer/parseInt s))

(def test "[1,1,3,1,1]
[1,1,5,1,1]

[[1],[2,3,4]]
[[1],4]

[9]
[[8,7,6]]

[[4,4],4,4]
[[4,4],4,4,4]

[7,7,7,7]
[7,7,7]

[]
[3]

[[[]]]
[[]]

[1,[2,[3,[4,[5,6,7]]]],8,9]
[1,[2,[3,[4,[5,6,0]]]],8,9]

")

(defn false-transpose [a b]
  (map-indexed (fn [idx item] (vector item (nth b idx false))) a))

(defn distress-comp [a b]
  (println a b)
  (cond (and (integer? a) (integer? b))
        (<= a b)
        (and (coll? a) (coll? b))
        (->> (false-transpose a b))
             ;(drop-while (partial apply distress-comp)))
             ;empty?)
        (integer? a)
        (distress-comp [a] b)
        (integer? b)
        (distress-comp a [b])))

(defn distress-comp
  ([] 0)
  ([a b]
   (println a b)
   (cond
     (false? b) 1
     (and (integer? a) (integer? b))
     (- a b)
     (and (coll? a) (coll? b))
     (->> (false-transpose a b)
          (drop-while (comp zero? (partial apply distress-comp)))
          (#(if (nil? %) true
                (apply distress-comp (first %)))))
     (integer? a)
     (distress-comp [a] b)
     (integer? b)
     (distress-comp a [b]))))


(distress-comp [2 3 4] 4)
(distress-comp [7 7 7 7] [7 7 7])
(distress-comp 1 2)
(distress-comp [7 7 7] [7 7 7])
(distress-comp [] [3])
(distress-comp [[[]]] [[]])
(distress-comp [1 1 3 1 1] [1 1 5 1 1])
(distress-comp [[1],[2,3,4]] [[1],4])

(false-transpose [7 7 7 7] [7 7 7])
(false-transpose [] [4])

(map list [[1],[2,3,4]] [[1],4])

(->> test
     (re-seq #"(.+?)\n(.+?)\n\n")
     (map rest)
     (map (partial map read-string))
     (map (partial apply distress-comp))
     (map (partial >= 0)))

; true true false true false true false false
