(require '[clojure.string :as str])

(def sums
  "Sum of each backpack

   Calculated by splitting input by pack
   then taking the sum for each line"
  (->> (slurp "input")
       (#(str/split % #"\n\n"))
       (map #(->> (str/split-lines %)
                  (map (fn [s] (Integer/parseInt s)))
                  (reduce +)))))

(apply max sums) ; Q1 Max kcals in a backpack
(->> sums sort reverse (take 3) (reduce +)) ; Q2 Sum of top 3
