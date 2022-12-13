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

(operation 20 "old * old")

(->> (slurp "input_11")
    (#(str/split % #"\n\n"))
    first
    (re-find #"Monkey (\d+):
  Starting items: (.+)?
  Operation: new = (.+)?
  Test: divisible by (\d+)?
    If true: throw to monkey (\d+)
    If false: throw to monkey (\d+)")
    (drop 2))
