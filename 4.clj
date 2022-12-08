(require '[clojure.string :as str])

(defn parse-int [s]
  (Integer/parseInt s))

(defn full-overlap?
  "Does interval [x:y] fully overlap with [a:b]?"
  [[a b] [x y]]
  (or (and (<= a x) (>= b y))
      (and (>= a x) (<= b y))))

(defn any-overlap?
  [[a b] [x y]]
  (and (>= b x) (<= a y)))

(->> (slurp "input_4")
     str/split-lines
     (map #(->> %
                (re-find #"(\d+)-(\d+),(\d+)-(\d+)")
                rest
                (map parse-int)
                (partition 2)
                (apply any-overlap?)))
     (filter identity)
     count)
