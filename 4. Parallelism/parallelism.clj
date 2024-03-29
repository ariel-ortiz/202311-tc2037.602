(ns parallelism)

; Problem 1
;
; SPEED UP CALCULATION
;
; n = 100,000
; p = 8
;
; Run #1   T1 = 3298.514416    Tp = 170.713583
; Run #2   T1 = 3242.947208    Tp = 176.793
; Run #3   T1 = 3257.167042    Tp = 196.700583
; Run #4   T1 = 3253.873208    Tp = 187.150583
; Run #5   T1 = 3278.489542    Tp = 180.2875
; Average  T1 = 3266.198283    Tp = 182.3290498
;
; Sp = T1/Tp = 17.91375695

(defn bits
  [x]
  (.bitCount (biginteger x)))

(defn fact-seq
  [n]
  (bits (loop [i 2
               r 1]
          (if (> i n)
            r
            (recur (inc i) (*' r i))))))

(defn fact-ranges
  [n p]
  (partition 2 1 (concat (range 1 n (quot n p))
                         [(inc n)])))

(defn fact-partial
  [[start end]]
  (loop [i start
         r 1]
    (if (= i end)
      r
      (recur (inc i) (*' r i)))))

(defn fact-par
  [n]
  (as-> (.availableProcessors (Runtime/getRuntime)) RESULT
        (fact-ranges n RESULT)
        (pmap fact-partial RESULT)
        (reduce *' RESULT)
        (bits RESULT)))

(time (fact-seq 100000))

(time (fact-par 100000))

;--------------------------------------------------------------------
; Problem 4
;
; SPEED UP CALCULATION
;
; n = 200,000
; p = 8
;
; Run #1   T1 = 2026.441333    Tp = 879.694833
; Run #2   T1 = 1956.927209    Tp = 861.690375
; Run #3   T1 = 1955.825083    Tp = 940.015208
; Run #4   T1 = 1938.140875    Tp = 1022.106917
; Run #5   T1 = 1974.706958    Tp = 929.29625
; Average  T1 = 1970.408292    Tp = 926.5607166
;
; Sp = T1/Tp = 2.126583025

(defn create-random-data
  [n]
  (repeatedly n #(rand-int 1000)))

; (apply <= (sort (create-random-data 10000)))

(defn insertion-sort
  [s]
  (loop [s s
         r ()]
    (if (empty? s)
      r
      (let [x (first s)
            [before after] (split-with #(< % x) r)]
        (recur (rest s)
               (concat before [x] after))))))

; (apply <= (insertion-sort (create-random-data 100)))

(defn merge-algorithm
  [a b]
  (loop [a a
         b b
         r ()]
    (cond
      (empty? a)
      (concat (reverse r) b)

      (empty? b)
      (concat (reverse r) a)

      (< (first a) (first b))
      (recur (rest a)
             b
             (cons (first a) r))

      :else
      (recur a
             (rest b)
             (cons (first b) r)))))

; (merge-algorithm [1 2 5 6 8 9 10] [1 2 3 4 4 4 5 7 8])

(defn hybrid-sort-seq
  [s]
  (if (< (count s) 100)
    (insertion-sort s)
    (let [[a b] (split-at (quot (count s) 2) s)]
      (merge-algorithm (hybrid-sort-seq a)
                       (hybrid-sort-seq b)))))

(defn hybrid-sort-par
  [s]
  (if (< (count s) 100)
    (insertion-sort s)
    (let [splitted (split-at (quot (count s) 2) s)]
      (apply merge-algorithm
             (pmap hybrid-sort-par splitted)))))


(def n 200000)
(def random-data (create-random-data n))
(apply <= random-data)
(apply <= (time (hybrid-sort-seq random-data)))
(apply <= (time (hybrid-sort-par random-data)))
(apply <= (time (sort random-data)))
