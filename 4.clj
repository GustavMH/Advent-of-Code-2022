(require '[clojure.string :as str])

(defn full-overlap?
  "Does interval [x:y] fully overlap with [a:b]?"
  [[a b] [x y]]
  (or (and (<= a x) (>= b y))
      (and (>= a x) (<= b y))))

(defn any-overlap?
  "Does interval [x:y] have any overlap with [a:b]?"
  [[a b] [x y]]
  (and (>= b x) (<= a y)))

(def input
  "Split into a list of pairs of intervals"
  (->> (slurp "input_4")
       str/split-lines
       (map #(->> (rest (re-find #"(\d+)-(\d+),(\d+)-(\d+)" %))
                  (map (fn [s] (Integer/parseInt s)))
                  (partition 2)))))

(defn count-overlap [input overlap?]
  (->> (map (partial apply overlap?) input)
       (filter identity) count))

(count-overlap input full-overlap?) ; Q1 count of full interval pair overlap
(count-overlap input any-overlap?)  ; Q2 count of partial interval pair overlap
