(require '[clojure.string :as str])

(defn parse-int [s]
  (Integer/parseInt s))

(defn operation [old s]
  (let [[_ a-str op-str b-str]
        (re-find #"(.+?) (.+?) (.+)$" s)
        a (if (= a-str "old") old (parse-int a-str))
        b (if (= b-str "old") old (parse-int b-str))
        op (case op-str "+" + "*" *)]
    (op a b)))

(defn parse-monkey [s]
  (let [[_1 _2 items-str op-str div-str
         throw-true-str throw-false-str]
        (re-find #"Monkey (\d+):
  Starting items: (.+)?
  Operation: new = (.+)?
  Test: divisible by (\d+)?
    If true: throw to monkey (\d+)
    If false: throw to monkey (\d+)" s)]
    [(->> (str/split items-str #",")
         (map (comp parse-int str/trim)))
     op-str
     (parse-int div-str)
     (parse-int throw-true-str)
     (parse-int throw-false-str)]))

(def input
  (->> (slurp "input_11")
      (#(str/split % #"\n\n"))
      (map parse-monkey)))

(def rules (map rest input))
(def items (map first input))
(def handles (map (fn [& x] 0) input))

(defn iter [[cur-rule & rules] items handles]
  ())
